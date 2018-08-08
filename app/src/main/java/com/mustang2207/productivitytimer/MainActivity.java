package com.mustang2207.productivitytimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private final static String PREFERENCE_ID = "prefId";
    private final static String WORK_SESSION = "workDuration";
    private final static String BREAK_INTERVAL = "shortBreakInterval";
    private final static String LONG_BREAK_INTERVAL = "longBreakInterval";
    private final static int WORK_SESSION_DEFAULT_VALUE = 60;
    private final static int BREAK_INTERVAL_DEFAULT_VALUE = 5;
    private final static int LONG_BREAK_INTERVAL_DEFAULT_VALUE = 20;
    private SettingsViewModel mSettingsViewModel;
    private SharedPreferences mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mustang2207.productivitytimer.R.layout.activity_main);

        initSettingsViewModel();
        initUI();
    }

    @Override
    protected void onDestroy() {
        saveSettingsViewModelData();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.mustang2207.productivitytimer.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Have the NavHelper look for an action or destination matching the menu
        // item id and navigate there if found.
        // Otherwise, bubble up to the parent.
        NavController navController = Navigation.findNavController(this, com.mustang2207.productivitytimer.R.id.my_nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(null, Navigation.findNavController(
                this, com.mustang2207.productivitytimer.R.id.my_nav_host_fragment));
    }

    private void initSettingsViewModel() {
        mSettingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        mSharedPreference = getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE);
        int workSession = mSharedPreference.getInt(WORK_SESSION, WORK_SESSION_DEFAULT_VALUE);
        int breakInterval = mSharedPreference.getInt(BREAK_INTERVAL, BREAK_INTERVAL_DEFAULT_VALUE);
        int longBreakInterval = mSharedPreference.getInt(LONG_BREAK_INTERVAL, LONG_BREAK_INTERVAL_DEFAULT_VALUE);
        mSettingsViewModel.setWorkSession(workSession);
        mSettingsViewModel.setBreakInterval(breakInterval);
        mSettingsViewModel.setLongBreakInterval(longBreakInterval);
    }

    private void saveSettingsViewModelData() {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putInt(WORK_SESSION, mSettingsViewModel.getWorkSession().getValue());
        editor.putInt(BREAK_INTERVAL, mSettingsViewModel.getBreakInterval().getValue());
        editor.putInt(LONG_BREAK_INTERVAL, mSettingsViewModel.getLongBreakInterval().getValue());
        editor.apply();
    }

    private void initUI() {
        NavHostFragment finalHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(
                com.mustang2207.productivitytimer.R.id.my_nav_host_fragment);
        Toolbar toolbar = findViewById(com.mustang2207.productivitytimer.R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, finalHost.getNavController());
    }
}
