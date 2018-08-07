package com.mustang2207.productivitytimer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mustang2207.productivitytimer.R.layout.activity_main);

        Toolbar toolbar = findViewById(com.mustang2207.productivitytimer.R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        NavHostFragment finalHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(
                com.mustang2207.productivitytimer.R.id.my_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, finalHost.getNavController());

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
}
