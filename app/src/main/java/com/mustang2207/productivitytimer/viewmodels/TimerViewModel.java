package com.mustang2207.productivitytimer.viewmodels;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {
    private final int ONE_SECOND = 1000;

    private MutableLiveData<String> timer = new MutableLiveData<>();

    private Runnable timerRunnable;
    private Handler timerHandler;
    private boolean isTimerOn = false;
    private long startTime = -1;

    public void startTimer(int interval) {
        startTimer((long)(interval * 60 * ONE_SECOND));
    }

    public void stopTimer() {
        if (isTimerOn) {
            timerHandler.removeCallbacks(timerRunnable);
            isTimerOn = false;
        }
    }

    public void setInterval(int interval) {
        if (!isTimerOn) {
            int seconds = 0;
            int minutes = interval;
            this.timer.setValue(String.format("%d:%02d", minutes, seconds));
        } else {
            setInterval((long)(interval * 60 * ONE_SECOND));
        }
    }

    private void startTimer(final long interval) {
        if (!isTimerOn) {
            isTimerOn = true;
            // Update the elapsed time every second.
            final long startTime = System.currentTimeMillis();
            //runs without a timer by reposting this handler at the end of the runnable
            final Handler timerHandler = new Handler();
            final Runnable timerRunnable = new Runnable() {
                @Override
                public void run() {
                    long millis =  interval + startTime - System.currentTimeMillis();
                    if (millis > 0) {
                        int seconds = (int) (millis / ONE_SECOND);
                        int minutes = seconds / 60;
                        seconds = seconds % 60;
                        timer.postValue(String.format("%d:%02d", minutes, seconds));
                        timerHandler.postDelayed(this, ONE_SECOND);
                    } else {
                        timerHandler.removeCallbacks(this);
                        isTimerOn = false;
                    }
                }
            };
            timerHandler.postDelayed(timerRunnable, ONE_SECOND/2);
            this.timerRunnable = timerRunnable;
            this.timerHandler = timerHandler;
            this.startTime = startTime;
        }
    }

    private void setInterval(long interval) {
        if (isTimerOn) {
            stopTimer();
            interval = interval + startTime - System.currentTimeMillis();
            startTimer(interval);
        }
    }

    public MutableLiveData<String> getTimer() {
        return timer;
    }
}
