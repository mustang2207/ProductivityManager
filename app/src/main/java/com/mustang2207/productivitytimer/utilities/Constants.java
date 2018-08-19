package com.mustang2207.productivitytimer.utilities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

public class Constants{
    //just default value
    public static final int WORK_SESSION_DEFAULT_VALUE = 60;
    public static final int BREAK_INTERVAL_DEFAULT_VALUE = 5;
    public static final int LONG_BREAK_INTERVAL_DEFAULT_VALUE = 20;

    //base value for settings seekbars
    public static final int WORK_SESSION_BASE_VALUE = 15;
    public static final int BREAK_INTERVAL_BASE_VALUE = 5;
    public static final int LONG_BREAK_INTERVAL_BASE_VALUE = 5;

    //increment for settings seekbars
    public static final int WORK_SESSION_INCREMENT = 5;
    public static final int BREAK_INTERVAL_INCREMENT = 1;
    public static final int LONG_BREAK_INTERVAL_INCREMENT = 5;

    public static final int WORK_SESSIONS_BEFORE_LONG_BREAK = 4;
    public static final int ONE_SECOND = 1000;
    public static final int VIBRATION_DURATION = 2000;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WORK_SESSION_STATE, BREAK_INTERVAL_STATE, LONG_BREAK_INTERVAL_STATE})
    public @interface TimerState{}
    public static final int WORK_SESSION_STATE = 1;
    public static final int BREAK_INTERVAL_STATE = 2;
    public static final int LONG_BREAK_INTERVAL_STATE = 3;

    // tags for SharedPreferences fields
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({PREFERENCE_ID, WORK_SESSION, BREAK_INTERVAL, LONG_BREAK_INTERVAL})
    public @interface AppPreferencesFields{}
    public static final String PREFERENCE_ID = "prefId";
    public static final String WORK_SESSION = "workDuration";
    public static final String BREAK_INTERVAL = "shortBreakInterval";
    public static final String LONG_BREAK_INTERVAL = "longBreakInterval";
}
