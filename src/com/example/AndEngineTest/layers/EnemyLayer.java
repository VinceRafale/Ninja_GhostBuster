package com.example.AndEngineTest.layers;

import com.example.AndEngineTest.MyActivity;
import com.example.AndEngineTest.pieces.Raiders;
import com.example.AndEngineTest.pools.EnemyPool;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/20/13
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnemyLayer extends Entity {
    private LinkedList<Raiders> enemies;
    public static EnemyLayer instance;
    public int enemyCount;


    public EnemyLayer(int x) {
        enemies = new LinkedList<Raiders>();
        instance = this;
        enemyCount = x;
        restart();

        setVisible(true);
        setPosition(50, 30);

        MoveXModifier movRight = new MoveXModifier(1, 50, 120);
        MoveXModifier movLeft = new MoveXModifier(1, 120, 50);
        MoveYModifier moveDown = new MoveYModifier(1, 30, 100);
        MoveYModifier moveUp = new MoveYModifier(1, 100, 30);

        restart();
        registerEntityModifier(new LoopEntityModifier(
                new SequenceEntityModifier(movRight, moveDown, movLeft, moveUp)));
    }

    public void restart() {
        enemies.clear();
        clearEntityModifiers();
        clearUpdateHandlers();

        for (int i = 0; i < enemyCount; i++) {
            Raiders e = (Raiders) EnemyPool.sharedEnemyPool().obtainPoolItem();
            float finalPosX = (i % 6) * 4 * e.sprite.getWidth();
            float finalPosY = ((int) (i / 6)) * e.sprite.getHeight() * 2;

            Random r = new Random();
            e.sprite.setPosition(r.nextInt(2) == 0 ? -e.sprite.getWidth() * 3
                    : MyActivity.CAMERA_WIDTH + e.sprite.getWidth() * 3,
                    (r.nextInt(5) + 1) * e.sprite.getHeight());
            e.sprite.setVisible(true);

            attachChild(e.sprite);
            e.sprite.registerEntityModifier(new MoveModifier(2,
                    e.sprite.getX(), finalPosX, e.sprite.getY(), finalPosY));

            enemies.add(e);
        }
    }

    public static EnemyLayer getSharedInstance() {
        return instance;
    }

    public static boolean isEmpty() {
        if (instance.enemies.size() == 0)
            return true;
        return false;
    }

    public static Iterator getIterator() {
        return instance.enemies.iterator();
    }


    public void purge() {
        detachChildren();
        for (Raiders e : enemies) {
            EnemyPool.sharedEnemyPool().recyclePoolItem(e);
        }
        enemies.clear();
    }

    public static void purgeAndRestart() {
        instance.purge();
        instance.restart();
    }

    @Override
    public void onDetached() {
        purge();
        clearUpdateHandlers();
        super.onDetached();
    }
}
