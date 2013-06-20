package com.example.AndEngineTest.pools;

import com.example.AndEngineTest.pieces.Bullet;
import org.andengine.util.adt.pool.GenericPool;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/20/13
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class BulletPool extends GenericPool {

    public static BulletPool instance;

    public static BulletPool sharedBulletPool() {
        if (instance == null)
            instance = new BulletPool();
        return instance;
    }

    private BulletPool() {
        super();
    }

    @Override
    protected Bullet onAllocatePoolItem() {
        return new Bullet();
    }

    protected void onHandleRecycleItem(final Bullet b) {
        b.sprite.clearEntityModifiers();
        b.sprite.clearUpdateHandlers();
        b.sprite.setVisible(false);
        b.sprite.detachSelf();
    }
}
