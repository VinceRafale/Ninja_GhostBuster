package com.example.AndEngineTest.scenes;

import com.example.AndEngineTest.MyActivity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.IModifier;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/19/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplashScene extends Scene {

    MyActivity activity;

    public SplashScene() {
        setBackground(new Background(0.09804f, 0.6274f, 0));
        activity = MyActivity.getSharedInstance();
        Text title1 = new Text(0, 0, activity.mFont, "Ninja", activity.getVertexBufferObjectManager());
        Text title2 = new Text(0, 0, activity.mFont, "GhostBuster", activity.getVertexBufferObjectManager());

        title1.setPosition(-title1.getWidth(), activity.mCamera.getHeight() / 2);
        title2.setPosition(activity.mCamera.getWidth(), activity.mCamera.getHeight() / 2);

        attachChild(title1);
        attachChild(title2);

        title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), activity.mCamera.getWidth() / 2 - title1.getWidth()));
        title2.registerEntityModifier(new MoveXModifier(1, title2.getX(), activity.mCamera.getWidth() / 2));
        loadResources();
    }

    public void loadResources() {
        DelayModifier delayModifier = new DelayModifier(2, new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                activity.setCurrentScene(new MainMenuScene());
            }
        });
        registerEntityModifier(delayModifier);
    }
}


