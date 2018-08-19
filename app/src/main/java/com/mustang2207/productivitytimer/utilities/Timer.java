package com.mustang2207.productivitytimer.utilities;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

public class Timer {

    private boolean isTimerOn = false;
    private Handler timerHandler;
    private Runnable timerRunnable;

    public void start(final long interval, final TimerListener timerListener) {
        final long startTime = System.currentTimeMillis();
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = interval + startTime - System.currentTimeMillis();
                if (millis >= 0) {
                    Log.d("Inside timer - ",
                            String.valueOf(millis) + " " + String.valueOf(millis / 1000));
                    timerListener.onTimeTick(millis);
                    timerHandler.postDelayed(this, Constants.ONE_SECOND);
                } else {
                    stop();
                }
            }
        };
        isTimerOn = timerHandler.postDelayed(timerRunnable, 0);
    }

    public void stop() {
        if(isTimerOn && timerHandler != null && timerRunnable != null) {
            Log.d("Stop", "1");
            timerHandler.removeCallbacks(timerRunnable);
            isTimerOn = false;
        }
    }

    public boolean isTimerOn() {
        return isTimerOn;
    }

    public interface TimerListener {
        public void onTimeTick(long intervalLeft);
    }

}
