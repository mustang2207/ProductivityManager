package com.mustang2207.productivitytimer.utilities;

import android.os.Handler;
import android.util.Log;

public class Timer {

    private boolean isTimerOn = false;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private Listener listener;

    public void start(final long interval) {
        final long startTime = System.currentTimeMillis();
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = interval + startTime - System.currentTimeMillis();
                if (millis >= 0) {
                    Log.d("Inside timer - ",
                            String.valueOf(millis) + " " + String.valueOf(millis / 1000));
                    listener.onTick(millis);
                    timerHandler.postDelayed(this, Constants.ONE_SECOND);
                } else {
                    stop();
                    listener.onStop();
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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {

        void onTick(long intervalLeft);

        void onStop();
    }

}
