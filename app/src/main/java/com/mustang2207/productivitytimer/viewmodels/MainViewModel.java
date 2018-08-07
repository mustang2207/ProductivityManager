package com.mustang2207.productivitytimer.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Integer> workDuration = new MutableLiveData<>();
    private MutableLiveData<Integer> shortBreakInterval = new MutableLiveData<>();
    private MutableLiveData<Integer> longBreakInterval = new MutableLiveData<>();

    public MutableLiveData<Integer> getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(int workDuration) {

        this.workDuration.setValue(workDuration);
    }

    public MutableLiveData<Integer> getShortBreakInterval() {
        return shortBreakInterval;
    }

    public void setShortBreakInterval(int shortBreakInterval) {
        this.shortBreakInterval.setValue(shortBreakInterval);
    }

    public MutableLiveData<Integer> getLongBreakInterval() {
        return longBreakInterval;
    }

    public void setLongBreakInterval(int longBreakInterval) {
        this.longBreakInterval.setValue(longBreakInterval);
    }
}
