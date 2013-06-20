package com.example.AndEngineTest.pools;

import com.example.AndEngineTest.pieces.Raiders;
import org.andengine.util.adt.pool.GenericPool;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/20/13
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnemyPool extends GenericPool {

    public static EnemyPool instance;

    public static EnemyPool sharedEnemyPool() {
        if (instance == null)
            instance = new EnemyPool();
        return instance;
    }

    private EnemyPool() {
        super();
    }

    @Override
    protected Raiders onAllocatePoolItem() {
        return new Raiders();
    }

    protected void onHandleRecycleItem(final Raiders raiders) {
        raiders.sprite.setVisible(false);
        raiders.sprite.detachSelf();
        raiders.clean();
    }

    protected void onHandleObtainItem(Raiders pItem) {
        pItem.init();
    }
}
