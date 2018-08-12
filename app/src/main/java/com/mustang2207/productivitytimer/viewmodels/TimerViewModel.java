package com.mustang2207.productivitytimer.viewmodels;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * {@link TimerViewModel} all about timer belong here.
 */
public class TimerViewModel extends ViewModel {
    private final int WORK_SESSION_STATE = 1;
    private final int BREAK_INTERVAL_STATE = 2;
    private final int LONG_BREAK_INTERVAL_STATE = 3;
    private final int WORK_SESSIONS_BEFORE_LONG_BREAK = 4;
    private final int ONE_SECOND = 1000;

    private MutableLiveData<String> timer = new MutableLiveData<>();
    private MutableLiveData<String> header = new MutableLiveData<>();
    private MutableLiveData<Boolean> alertSignal = new MutableLiveData<>();

    private SettingsViewModel settingsViewModel;
    private Runnable timerRunnable;
    private Handler timerHandler;
    private int timerState = WORK_SESSION_STATE;
    private boolean isTimerOn = false;
    private int interval;
    private int workSessionCounter = 0;
    private long startTime = -1;

    private String alertTitle;
    private String alertMessage;

    public void setTimerInterval(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;
        switch (timerState) {
            case WORK_SESSION_STATE:
                interval = settingsViewModel.getWorkSession().getValue();
                header.setValue("Work");
                break;
            case BREAK_INTERVAL_STATE:
                interval = settingsViewModel.getBreakInterval().getValue();
                header.setValue("Break");
                break;
            case LONG_BREAK_INTERVAL_STATE:
                interval = settingsViewModel.getLongBreakInterval().getValue();
                header.setValue("Long break");
                break;
        }
        if (!isTimerOn) {
            int seconds = 0;
            int minutes = interval;
            this.timer.setValue(String.format("%d:%02d", minutes, seconds));
        } else {
            stopTimer();
            Long i = interval * 60 * ONE_SECOND + startTime - System.currentTimeMillis();
            startTimer(i);
        }
    }

    public void updateTimerInterval() {
        setTimerInterval(settingsViewModel);
    }

    public void startTimer() {
        startTimer((long)(interval * 60 * ONE_SECOND));
    }

    private void stopTimer() {
        if (isTimerOn) {
            timerHandler.removeCallbacks(timerRunnable);
            isTimerOn = false;
        }
    }

    /**
     * Starts the timer and updates LiveData component value.
     *
     * @param interval   The interval in milliseconds.
     */
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
                    long millis =  /*interval*/ 4000 + startTime - System.currentTimeMillis();
                    if (millis > 0) {
                        int seconds = (int) (millis / ONE_SECOND);
                        int minutes = seconds / 60;
                        seconds = seconds % 60;
                        timer.postValue(String.format("%d:%02d", minutes, seconds));
                        timerHandler.postDelayed(this, ONE_SECOND);
                    } else {
                        stopTimer();
                        updateTimerState();
                    }
                }
            };
            timerHandler.postDelayed(timerRunnable, ONE_SECOND/2);
            this.timerRunnable = timerRunnable;
            this.timerHandler = timerHandler;
            this.startTime = startTime;
        }
    }

    private void updateTimerState() {
        switch (timerState) {
            case WORK_SESSION_STATE:
                workSessionCounter++;
                alertTitle = "Work session completed!";
                if (workSessionCounter == WORK_SESSIONS_BEFORE_LONG_BREAK) {
                    timerState = LONG_BREAK_INTERVAL_STATE;
                    alertMessage = "Start long break?";
                } else {
                    timerState = BREAK_INTERVAL_STATE;
                    alertMessage = "Start break?";
                }
                break;
            default:
                timerState = WORK_SESSION_STATE;
                alertTitle = "Break completed!";
                alertMessage = "Start work session!";
                // TODO: 8/11/2018 move all strings to res
        }
        alertSignal.setValue(true);
    }

    public MutableLiveData<String> getTimer() {
        return timer;
    }

    public MutableLiveData<Boolean> getAlertSignal() {
        return alertSignal;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public MutableLiveData<String> getHeader() {
        return header;
    }
}
