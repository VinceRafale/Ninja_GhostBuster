package com.example.AndEngineTest;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/20/13
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class CoolDown {

    private boolean valid;
    private Timer timer;
    private long delay = 400;
    private static CoolDown instance = null;

    public static CoolDown getSharedInstance() {
        if (instance == null) {
            instance = new CoolDown();
        }
        return instance;
    }

    private CoolDown() {
        timer = new Timer();
        valid = true;
    }

    public boolean checkValidity() {
        if (valid) {
            valid = false;
            timer.schedule(new Task(), delay);
            return true;
        }
        return false;
    }

    class Task extends TimerTask {

        public void run() {
            valid = true;
        }

    }

}
