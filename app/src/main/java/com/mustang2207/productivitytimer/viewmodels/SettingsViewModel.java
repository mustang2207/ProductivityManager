package com.mustang2207.productivitytimer.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<Integer> workSession = new MutableLiveData<>();
    private MutableLiveData<Integer> breakInterval = new MutableLiveData<>();
    private MutableLiveData<Integer> longBreakInterval = new MutableLiveData<>();

    public MutableLiveData<Integer> getWorkSession() {
        return workSession;
    }

    public MutableLiveData<Integer> getBreakInterval() {
        return breakInterval;
    }

    public MutableLiveData<Integer> getLongBreakInterval() {
        return longBreakInterval;
    }

    public void setWorkSession(int workSession) {
        this.workSession.setValue(workSession);
    }

    public void setBreakInterval(int breakInterval) {
        this.breakInterval.setValue(breakInterval);
    }

    public void setLongBreakInterval(int longBreakInterval) {
        this.longBreakInterval.setValue(longBreakInterval);
    }
}
