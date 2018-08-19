package com.mustang2207.productivitytimer.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import com.mustang2207.productivitytimer.utilities.Constants;
import com.mustang2207.productivitytimer.utilities.Timer;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.mustang2207.productivitytimer.utilities.Constants.*;

/**
 * {@link TimerViewModel} all about timerText belong here.
 */
@SuppressLint("DefaultLocale")
public class TimerViewModel extends ViewModel {

    @TimerState private int timerState = WORK_SESSION_STATE;
    private MutableLiveData<String> timerText = new MutableLiveData<>();
    private MutableLiveData<String> headerText = new MutableLiveData<>();
    private Timer timer;
    private Listener listener;
    private String alertTitle;
    private String alertMessage;
    private long interval;
    private int workSessionCounter = 0;

    public TimerViewModel() {
        super();
        initTimer();
    }

    @SuppressWarnings("ConstantConditions")
    public void set(@NonNull SettingsViewModel settingsViewModel) {
        setHeaderText();
        int intervalMin = 0;
        switch (timerState) {
            case WORK_SESSION_STATE:
                intervalMin = settingsViewModel.getWorkSession().getValue();
                break;
            case BREAK_INTERVAL_STATE:
                intervalMin = settingsViewModel.getBreakInterval().getValue();
                break;
            case LONG_BREAK_INTERVAL_STATE:
                intervalMin = settingsViewModel.getLongBreakInterval().getValue();
                break;
        }
        interval = intervalMin * 60 * ONE_SECOND;
        //interval = 4_000;
        if (!timer.isTimerOn()) {
            int seconds = 0;
            int minutes = intervalMin;
            seconds = seconds % 60;
            timerText.setValue(String.format("%d:%02d", minutes, seconds));
        } else {
            timer.stop();
            timer.start(interval);
        }
    }

    public void start() {
        if(!timer.isTimerOn()) {
            setHeaderText();
            timer.start(interval);
        }
    }

    @SuppressLint("SwitchIntDef")
    private void updateState() {
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
        if (listener != null) listener.onStatusChanged();
    }

    private void initTimer() {
        timer = new Timer();
        timer.setListener(new Timer.Listener() {
            @Override
            public void onTick(long intervalLeft) {
                int seconds = (int) (intervalLeft / ONE_SECOND);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timerText.setValue(String.format("%d:%02d", minutes, seconds));
                Log.d("timer:", timerText.getValue());
            }

            @Override
            public void onStop() {
                updateState();
            }
        });
    }

    public void setHeaderText() {
        switch (timerState) {
            case WORK_SESSION_STATE:
                headerText.setValue("Work");
                break;
            case BREAK_INTERVAL_STATE:
                headerText.setValue("Break");
                break;
            case LONG_BREAK_INTERVAL_STATE:
                headerText.setValue("Long break");
                break;
        }
    }

    public interface Listener {
        void onStatusChanged();
    }

    public void setLister(Listener lister) {
        this.listener = lister;
    }


    public String getAlertTitle() {
        return alertTitle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    @NonNull
    public MutableLiveData<String> getTimerText() {
        return timerText;
    }

    @NonNull
    public MutableLiveData<String> getHeaderText() {
        return headerText;
    }
}
