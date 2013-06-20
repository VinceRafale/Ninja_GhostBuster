package com.example.AndEngineTest;

import com.example.AndEngineTest.scenes.GameScene;
import org.andengine.engine.handler.IUpdateHandler;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/20/13
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameLoopUpdateHandler implements IUpdateHandler {

    @Override
    public void onUpdate(float pSecondsElapsed) {
        GameScene gameScene = (GameScene) MyActivity.getSharedInstance().mCurrentScene;
        gameScene.moveShip();
        gameScene.cleaner();
    }

    @Override
    public void reset() {

    }
}
