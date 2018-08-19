package com.mustang2207.productivitytimer.viewmodels;

import android.annotation.SuppressLint;
import android.os.Handler;

import com.mustang2207.productivitytimer.utilities.Constants;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * {@link TimerViewModel} all about timer belong here.
 */
public class TimerViewModel extends ViewModel {

    // TODO: 8/16/2018 cut timer into a separate class in utilities
    private final MutableLiveData<String> timer = new MutableLiveData<>();
    private final MutableLiveData<String> header = new MutableLiveData<>();

    private SettingsViewModel settingsViewModel;
    private Runnable timerRunnable;
    private Handler timerHandler;
    @Constants.TimerState private int timerState = Constants.WORK_SESSION_STATE;
    private boolean isTimerOn = false;
    private int interval;
    private int workSessionCounter = 0;
    private long startTime = -1;

    private String alertTitle;
    private String alertMessage;

    public interface TimerListener{
        void onTimerStatusChanged();
    }

    private TimerListener timerListener;

    public void setTimerLister(TimerListener timerLister) {
        this.timerListener = timerLister;
    }

    @SuppressWarnings("ConstantConditions")
    @SuppressLint("DefaultLocale")
    public void setTimerInterval(@NonNull SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;
        switch (timerState) {
            case Constants.WORK_SESSION_STATE:
                //noinspection ConstantConditions
                interval = settingsViewModel.getWorkSession().getValue();
                header.setValue("Work");
                break;
            case Constants.BREAK_INTERVAL_STATE:
                interval = settingsViewModel.getBreakInterval().getValue();
                header.setValue("Break");
                break;
            case Constants.LONG_BREAK_INTERVAL_STATE:
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
            Long i = interval * 60 * Constants.ONE_SECOND + startTime - System.currentTimeMillis();
            startTimer(i);
        }
    }

    public void updateTimerInterval() {
        setTimerInterval(settingsViewModel);

    }

    public void startTimer() {
        startTimer((long)(interval * 60 * Constants.ONE_SECOND));
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
                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    long millis = interval + startTime - System.currentTimeMillis();
                    if (millis > 0) {
                        int seconds = (int) (millis / Constants.ONE_SECOND);
                        int minutes = seconds / 60;
                        seconds = seconds % 60;
                        timer.postValue(String.format("%d:%02d", minutes, seconds));
                        timerHandler.postDelayed(this, Constants.ONE_SECOND);
                    } else {
                        stopTimer();
                        updateTimerState();
                    }
                }
            };
            timerHandler.postDelayed(timerRunnable, Constants.ONE_SECOND/2);
            this.timerRunnable = timerRunnable;
            this.timerHandler = timerHandler;
            this.startTime = startTime;
        }
    }

    @SuppressLint("SwitchIntDef")
    private void updateTimerState() {
        switch (timerState) {
            case Constants.WORK_SESSION_STATE:
                workSessionCounter++;
                alertTitle = "Work session completed!";
                if (workSessionCounter == Constants.WORK_SESSIONS_BEFORE_LONG_BREAK) {
                    timerState = Constants.LONG_BREAK_INTERVAL_STATE;
                    alertMessage = "Start long break?";
                } else {
                    timerState = Constants.BREAK_INTERVAL_STATE;
                    alertMessage = "Start break?";
                }
                break;
            default:
                timerState = Constants.WORK_SESSION_STATE;
                alertTitle = "Break completed!";
                alertMessage = "Start work session!";
                // TODO: 8/11/2018 move all strings to res
        }
        if (timerListener != null) timerListener.onTimerStatusChanged();
    }

    @NonNull
    public MutableLiveData<String> getTimer() {
        return timer;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    @NonNull
    public MutableLiveData<String> getHeader() {
        return header;
    }
}
