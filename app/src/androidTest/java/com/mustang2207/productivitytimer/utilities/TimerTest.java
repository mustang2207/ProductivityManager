package com.mustang2207.productivitytimer.utilities;

import android.os.Handler;
import android.os.Looper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TimerTest {

    long interval = 5*Constants.ONE_SECOND;
    static long i;
    @Before
    public void setUp() throws Exception {
        i = interval/Constants.ONE_SECOND;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(timeout = 10_000)
    public void startAndStop() {
        Looper.prepare();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                class MyTimer extends Timer{
                    @Override
                    public void stop() {
                        super.stop();
                        Looper.myLooper().quit();
                    }
                }
                Timer timer = new MyTimer();
                timer.start(interval, new Timer.TickListener() {
                    @Override
                    public void onTick(long intervalLeft) {
                        i--;
                        assertEquals(i, intervalLeft/Constants.ONE_SECOND);
                    }
                });
                assertThat(timer.isTimerOn(), is(true));
            }
        });
        Looper.loop();
    }
}