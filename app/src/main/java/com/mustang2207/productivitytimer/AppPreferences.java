package com.mustang2207.productivitytimer;

import android.content.Context;
import android.content.SharedPreferences;

import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
/**
 * {@link AppPreferences} all about SharedPreferences use belong here.
 */
class AppPreferences {
    private final static String PREFERENCE_ID = "prefId";
    private final static String WORK_SESSION = "workDuration";
    private final static String BREAK_INTERVAL = "shortBreakInterval";
    private final static String LONG_BREAK_INTERVAL = "longBreakInterval";
    private final static int WORK_SESSION_DEFAULT_VALUE = 60;
    private final static int BREAK_INTERVAL_DEFAULT_VALUE = 5;
    private final static int LONG_BREAK_INTERVAL_DEFAULT_VALUE = 20;

    private final SharedPreferences sharedPreferences;
    private final SettingsViewModel settingsViewModel;

    AppPreferences(FragmentActivity mainActivity) {
        sharedPreferences = mainActivity.getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE);
        settingsViewModel = ViewModelProviders.of(mainActivity).get(SettingsViewModel.class);
    }

    void load() {
        int workSession = sharedPreferences.getInt(WORK_SESSION, WORK_SESSION_DEFAULT_VALUE);
        int breakInterval = sharedPreferences.getInt(BREAK_INTERVAL, BREAK_INTERVAL_DEFAULT_VALUE);
        int longBreakInterval = sharedPreferences.getInt(LONG_BREAK_INTERVAL, LONG_BREAK_INTERVAL_DEFAULT_VALUE);
        settingsViewModel.setWorkSession(workSession);
        settingsViewModel.setBreakInterval(breakInterval);
        settingsViewModel.setLongBreakInterval(longBreakInterval);
    }

    @SuppressWarnings("ConstantConditions")
    void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(WORK_SESSION,  settingsViewModel.getWorkSession().getValue());
        editor.putInt(BREAK_INTERVAL, settingsViewModel.getBreakInterval().getValue());
        editor.putInt(LONG_BREAK_INTERVAL, settingsViewModel.getLongBreakInterval().getValue());
        editor.apply();
    }
}
