package com.mustang2207.productivitytimer.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * {@link SettingsViewModel} all LiveData from settingsFragment belong here.
 */
public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<Integer> workSession = new MutableLiveData<>();
    private final MutableLiveData<Integer> breakInterval = new MutableLiveData<>();
    private final MutableLiveData<Integer> longBreakInterval = new MutableLiveData<>();

    @NonNull
    public MutableLiveData<Integer> getWorkSession() {
        return workSession;
    }

    @NonNull
    public MutableLiveData<Integer> getBreakInterval() {
        return breakInterval;
    }

    @NonNull
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
