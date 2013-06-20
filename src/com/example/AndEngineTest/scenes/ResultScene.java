package com.example.AndEngineTest.scenes;

import com.example.AndEngineTest.MyActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/20/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultScene extends CameraScene implements IOnSceneTouchListener {

    boolean done;
    MyActivity activity;

    public ResultScene(Camera mCamera) {
        super(mCamera);
        activity = MyActivity.getSharedInstance();
        setBackgroundEnabled(false);
        GameScene scene = (GameScene) activity.mCurrentScene;
        float accuracy = 1 - (float) scene.missCount / scene.bulletCount;
        float score = scene.totalPoint;
        if (Float.isNaN(accuracy))
            accuracy = 0;
        accuracy *= 100;
        Text result = new Text(0, 0, activity.mFont,
                "Accuracy : "
                        + String.format("%.2f", accuracy) + "% \n Score :" + score + "", MyActivity
                .getSharedInstance().getVertexBufferObjectManager());

        final int x = (int) (mCamera.getWidth() / 2 - result.getWidth() / 2);
        final int y = (int) (mCamera.getHeight() / 2 - result.getHeight() / 2);

        done = false;
        result.setPosition(x, mCamera.getHeight() + result.getHeight());
        MoveYModifier mod = new MoveYModifier(5, result.getY(), y) {
            @Override
            protected void onModifierFinished(IEntity pItem) {
                done = true;
            }
        };
        attachChild(result);
        result.registerEntityModifier(mod);
        setOnSceneTouchListener(this);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (!done)
            return true;
        ((GameScene) activity.mCurrentScene).resetValues();
        return false;
    }
}
