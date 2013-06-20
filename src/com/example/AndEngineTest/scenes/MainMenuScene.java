package com.example.AndEngineTest.scenes;

import com.example.AndEngineTest.MyActivity;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/19/13
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuScene extends MenuScene implements MenuScene.IOnMenuItemClickListener {

    MyActivity activity;
    final int MENU_START = 0;

    public MainMenuScene() {
        super(MyActivity.getSharedInstance().mCamera);
        activity = MyActivity.getSharedInstance();

        setBackground(new Background(0.09804f, 0.6274f, 0));
        IMenuItem startButton = new TextMenuItem(MENU_START, activity.mFont, "Start", activity.getVertexBufferObjectManager());
        startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth() / 2, mCamera.getHeight() / 2 - startButton.getHeight() / 2);
        startButton.setColor(new Color(0.09804f, 0.7274f, 0.8f));
        addMenuItem(startButton);

        setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case MENU_START:
                activity.setCurrentScene(new GameScene());
                return true;
            default:
                break;
        }
        return false;
    }
}
