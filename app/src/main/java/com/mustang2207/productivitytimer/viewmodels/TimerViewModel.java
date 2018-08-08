package com.mustang2207.productivitytimer.viewmodels;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {
    private final int ONE_SECOND = 1000;

    private MutableLiveData<String> timer = new MutableLiveData<>();

    private boolean isTimerOn = false;

    public void startTimer(final int interval) {
        if (!isTimerOn) {
            isTimerOn = true;
            // Update the elapsed time every second.
            final long startTime = System.currentTimeMillis();
            //runs without a timer by reposting this handler at the end of the runnable
            final Handler timerHandler = new Handler();
            final Runnable timerRunnable = new Runnable() {
                @Override
                public void run() {
                    long millis =  interval * 60 * ONE_SECOND + startTime - System.currentTimeMillis();
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
        }
    }

    public MutableLiveData<String> getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        if (isTimerOn) {
            // TODO: 8/8/2018 adjust new interval value
        }
        int seconds = 0;
        int minutes = timer;
        this.timer.setValue(String.format("%d:%02d", minutes, seconds));
    }
}
