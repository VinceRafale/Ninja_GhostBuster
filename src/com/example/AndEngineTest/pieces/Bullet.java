package com.example.AndEngineTest.pieces;

import com.example.AndEngineTest.MyActivity;
import org.andengine.entity.sprite.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/20/13
 * Time: 9:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bullet {
    public Sprite sprite;

    public Bullet() {
        sprite = new Sprite(0, 0, MyActivity.getBulletImageTextureRegion(), MyActivity.getSharedInstance()
                .getVertexBufferObjectManager());
    }
}
