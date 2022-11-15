package vn.edu.grenwich.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    protected NavHostFragment navHostFragment;
    protected NavController navController;
    protected BottomNavigationView bottomNavigationView;
    protected AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host);
        navController = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.main_nav_bottom);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.tripListFragment, R.id.tripRegisterFragment, R.id.aboutUsFragment
        ).build();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_in_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                navController.navigateUp();
                return true;
            case R.id.Setting:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }

        return false;
        //return false;
    }


}