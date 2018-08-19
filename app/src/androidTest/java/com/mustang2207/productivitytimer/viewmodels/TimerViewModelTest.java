package com.mustang2207.productivitytimer.viewmodels;

import com.mustang2207.productivitytimer.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class TimerViewModelTest {

    TimerViewModel timerViewModel;
    SettingsViewModel settingsViewModel;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        settingsViewModel = ViewModelProviders.of(activityTestRule.getActivity()).get(SettingsViewModel.class);
        settingsViewModel.setBreakInterval(5);
        settingsViewModel.setLongBreakInterval(20);
        settingsViewModel.setWorkSession(50);
        timerViewModel = ViewModelProviders.of(activityTestRule.getActivity()).get(TimerViewModel.class);

    }

    @Test
    public void start() {
        timerViewModel.set(settingsViewModel);
        timerViewModel.start();

    }
}