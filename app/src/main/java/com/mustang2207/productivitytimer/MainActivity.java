package com.mustang2207.productivitytimer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mustang2207.productivitytimer.R.layout.activity_main);
        initPreferences();
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.mustang2207.productivitytimer.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Have the NavHelper look for an action or destination matching the menu
        // item id and navigate there if found.
        // Otherwise, bubble up to the parent.
        NavController navController = Navigation.findNavController(
                this, com.mustang2207.productivitytimer.R.id.my_nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(null, Navigation.findNavController(
                this, com.mustang2207.productivitytimer.R.id.my_nav_host_fragment));
    }

    private void initUI() {
        NavHostFragment finalHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(
                com.mustang2207.productivitytimer.R.id.my_nav_host_fragment);
        Toolbar toolbar = findViewById(com.mustang2207.productivitytimer.R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, Objects.requireNonNull(finalHost).getNavController());
    }

    private void initPreferences() {
        SettingsViewModel settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        //MainActivity lifecycle-aware class that handles loading and saving SharedPreferences
        PreferencesHelper.getInstance(this,this, settingsViewModel);
    }
}
