package com.example.AndEngineTest;


import android.graphics.Typeface;
import com.example.AndEngineTest.listeners.SensorListener;
import com.example.AndEngineTest.scenes.GameScene;
import com.example.AndEngineTest.scenes.SplashScene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class MyActivity extends SimpleBaseGameActivity {
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    public Font mFont;
    public Camera mCamera;

    //A reference to the current scene
    public Scene mCurrentScene;
    public static MyActivity instance;

    private BitmapTextureAtlas bitmapTextureAtlas;
    private static ITextureRegion shipImageTextureRegion;
    private static ITextureRegion bulletImageTextureRegion;
    private static ITextureRegion raiderImageTextureRegion;

    private void loadGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        bitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.DEFAULT);
        shipImageTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "Player.png", 0, 0);
        bitmapTextureAtlas.load();
        bulletImageTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "Projectile.png", 36, 0);
        bitmapTextureAtlas.load();
        raiderImageTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "Target.png", 64, 0);
        bitmapTextureAtlas.load();
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        instance = this;
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }

    @Override
    protected void onCreateResources() {
        mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        mFont.load();

        loadGraphics();
    }

    @Override
    protected Scene onCreateScene() {
        getEngine().registerUpdateHandler(new FPSLogger());
        mCurrentScene = new SplashScene();
        return mCurrentScene;
    }

    public static MyActivity getSharedInstance() {
        return instance;
    }

    // to change the current main scene
    public void setCurrentScene(Scene scene) {
        mCurrentScene = scene;
        getEngine().setScene(mCurrentScene);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentScene instanceof GameScene)
            ((GameScene) mCurrentScene).detach();

        mCurrentScene = null;
        SensorListener.instance = null;
        super.onBackPressed();
    }

    public static ITextureRegion getShipImage() {
        return shipImageTextureRegion;
    }

    public static ITextureRegion getBulletImageTextureRegion() {
        return bulletImageTextureRegion;
    }

    public static ITextureRegion getRaiderImageTextureRegion() {
        return raiderImageTextureRegion;
    }
}