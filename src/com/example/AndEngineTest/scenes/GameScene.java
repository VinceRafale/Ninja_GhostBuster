package com.example.AndEngineTest.scenes;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import com.example.AndEngineTest.CoolDown;
import com.example.AndEngineTest.GameLoopUpdateHandler;
import com.example.AndEngineTest.MyActivity;
import com.example.AndEngineTest.layers.EnemyLayer;
import com.example.AndEngineTest.listeners.SensorListener;
import com.example.AndEngineTest.pieces.Bullet;
import com.example.AndEngineTest.pieces.Raiders;
import com.example.AndEngineTest.pieces.Ship;
import com.example.AndEngineTest.pools.BulletPool;
import com.example.AndEngineTest.pools.EnemyPool;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/19/13
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameScene extends Scene implements IOnSceneTouchListener {
    public Ship ship;
    Camera mCamera;

    public float accelerometerSpeedX;

    SensorManager sensorManager;
    public LinkedList<Bullet> bulletList;
    public int bulletCount = 0;
    public int missCount = 0;
    public int totalPoint = 0;

    int mTimePart = 2;

    public GameScene() {
        setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
        mCamera = MyActivity.getSharedInstance().mCamera;
        ship = Ship.getSharedInstance();
        attachChild(ship.sprite);


        MyActivity.getSharedInstance().setCurrentScene(this);
        sensorManager = (SensorManager) MyActivity.getSharedInstance()
                .getSystemService(MyActivity.SENSOR_SERVICE);

        sensorManager.registerListener(SensorListener.getSharedInstance(),
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

        //registerUpdateHandler(new GameLoopUpdateHandler());

        bulletList = new LinkedList<Bullet>();

        setOnSceneTouchListener(this);

        attachChild(new EnemyLayer(12));

        resetValues();
    }

    public void moveShip() {
        ship.moveShip(accelerometerSpeedX);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (!CoolDown.getSharedInstance().checkValidity())
            return false;

        synchronized (this) {

            ship.shoot();
        }
        return true;
    }

    public void cleaner() {
        if (EnemyLayer.isEmpty()) {
            setChildScene(new ResultScene(mCamera));
            clearUpdateHandlers();
        }

        synchronized (this) {
            Iterator eIt = EnemyLayer.getIterator();
            while (eIt.hasNext()) {
                Raiders e = (Raiders) eIt.next();

                Iterator it = bulletList.iterator();

                while (it.hasNext()) {
                    Bullet b = (Bullet) it.next();

                    if (b.sprite.getY() <= -b.sprite.getHeight()) {
                        BulletPool.sharedBulletPool().recyclePoolItem(b);
                        it.remove();
                        missCount++;
                        continue;
                    }

                    if (b.sprite.collidesWith(e.sprite)) {
                        if (!e.gotHit()) {
                            createExplosion(e.sprite.getX(), e.sprite.getY(), e.sprite.getParent(), MyActivity.getSharedInstance());
                            showPoints(e.sprite.getX(), e.sprite.getY(), e.sprite.getParent());
                            totalPoint += 10;

                            EnemyPool.sharedEnemyPool().recyclePoolItem(e);
                            eIt.remove();
                            e.sprite.setVisible(false);
                        }
                        BulletPool.sharedBulletPool().recyclePoolItem(b);
                        it.remove();
                        break;
                    }
                }
            }
        }
    }

    // method to reset values and restart the game
    public void resetValues() {
        missCount = 0;
        totalPoint = 0;
        bulletCount = 0;
        ship.restart();
        EnemyLayer.purgeAndRestart();
        clearChildScene();
        registerUpdateHandler(new GameLoopUpdateHandler());
    }

    public void detach() {
        Log.v("CyrilRaides", "GameScene onDetached()");
        clearUpdateHandlers();
        for (Bullet b : bulletList) {
            BulletPool.sharedBulletPool().recyclePoolItem(b);
        }
        bulletList.clear();
        detachChildren();
        Ship.instance = null;
        EnemyPool.instance = null;
        BulletPool.instance = null;
    }


    private void showPoints(final float posX, final float posY, final IEntity target) {
        Text result = new Text(posX, posY, MyActivity.getSharedInstance().mFont, "+ 10", MyActivity
                .getSharedInstance().getVertexBufferObjectManager());
        result.setColor(Color.RED);

        result.registerEntityModifier(new FadeOutModifier(2));

        target.attachChild(result);
        target.registerUpdateHandler(new TimerHandler(mTimePart, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                target.sortChildren();
                target.unregisterUpdateHandler(pTimerHandler);
            }
        }));
    }

    private void createExplosion(final float posX, final float posY, final IEntity target, final SimpleBaseGameActivity activity) {
        int mNumPart = 15;


        PointParticleEmitter particleEmitter = new PointParticleEmitter(posX, posY);
        IEntityFactory recFact = new IEntityFactory() {
            @Override
            public Rectangle create(float pX, float pY) {
                Rectangle rect = new Rectangle(posX, posY, 10, 10, activity.getVertexBufferObjectManager());
                rect.setColor(Color.GREEN);
                return rect;
            }
        };
        final ParticleSystem particleSystem = new ParticleSystem(recFact, particleEmitter, 500, 500, mNumPart);
        particleSystem.addParticleInitializer(new VelocityParticleInitializer(-50, 50, -50, 50));

        particleSystem.addParticleModifier(new AlphaParticleModifier(0, 0.6f * mTimePart, 1, 0));
        particleSystem.addParticleModifier(new RotationParticleModifier(0, mTimePart, 0, 360));

        target.attachChild(particleSystem);

        target.registerUpdateHandler(new TimerHandler(mTimePart, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                particleSystem.detachSelf();
                target.sortChildren();
                target.unregisterUpdateHandler(pTimerHandler);
            }
        }));


    }
}
