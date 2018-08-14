package com.mustang2207.productivitytimer;

import android.content.Context;
import android.content.SharedPreferences;

import com.mustang2207.productivitytimer.utilities.Constants;
import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
/**
 * {@link PreferencesHelper} all about SharedPreferences use belong here.
 */
class PreferencesHelper {

    private final SharedPreferences sharedPreferences;
    private final SettingsViewModel settingsViewModel;

    PreferencesHelper(FragmentActivity mainActivity) {
        sharedPreferences = mainActivity.getSharedPreferences(Constants.PREFERENCE_ID, Context.MODE_PRIVATE);
        settingsViewModel = ViewModelProviders.of(mainActivity).get(SettingsViewModel.class);
    }

    void load() {
        int workSession = sharedPreferences.getInt(Constants.WORK_SESSION, Constants.WORK_SESSION_DEFAULT_VALUE);
        int breakInterval = sharedPreferences.getInt(Constants.BREAK_INTERVAL, Constants.BREAK_INTERVAL_DEFAULT_VALUE);
        int longBreakInterval = sharedPreferences.getInt(Constants.LONG_BREAK_INTERVAL, Constants.LONG_BREAK_INTERVAL_DEFAULT_VALUE);
        settingsViewModel.setWorkSession(workSession);
        settingsViewModel.setBreakInterval(breakInterval);
        settingsViewModel.setLongBreakInterval(longBreakInterval);
    }

    @SuppressWarnings("ConstantConditions")
    void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.WORK_SESSION,  settingsViewModel.getWorkSession().getValue());
        editor.putInt(Constants.BREAK_INTERVAL, settingsViewModel.getBreakInterval().getValue());
        editor.putInt(Constants.LONG_BREAK_INTERVAL, settingsViewModel.getLongBreakInterval().getValue());
        editor.apply();
    }
}