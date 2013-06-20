package com.example.AndEngineTest.pieces;

import com.example.AndEngineTest.MyActivity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/20/13
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class Raiders {

    public Sprite sprite;
    public int hp;
    //the max health for each enemy
    protected final int MAX_HEALTH = 2;


    public Raiders() {
        sprite = new Sprite(0, 0, MyActivity.getRaiderImageTextureRegion(), MyActivity.getSharedInstance().getVertexBufferObjectManager());
        //sprite.setColor(0.09904f, 0.8574f, 0.1786f);
        init();
    }

    // method for initializing the Enemy object , used by the constructor and
    // the EnemyPool class
    public void init() {
        hp = MAX_HEALTH;
        sprite.registerEntityModifier(new LoopEntityModifier(
                new RotationModifier(5, 0, 360)));
    }

    public void clean() {
        sprite.clearEntityModifiers();
        sprite.clearUpdateHandlers();
    }

    // method for applying hit and checking if enemy died or not
// returns false if enemy died
    public boolean gotHit() {
        synchronized (this) {
            hp--;
            if (hp <= 0)
                return false;
            else
                return true;
        }
    }
}
