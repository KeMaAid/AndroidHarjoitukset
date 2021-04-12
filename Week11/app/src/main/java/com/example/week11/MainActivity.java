package com.example.week11;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.week11.logic.Langs;
import com.example.week11.logic.SettingStorage;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SettingStorage settingStorage;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settingStorage = SettingStorage.getInstance();
        setAppLocale(this, settingStorage.getSelectedLang());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setAppLocale(Activity activity, Langs lang){
        Locale locale;
        switch(lang){
            default:
            case ENGLISH:
                locale = new Locale("en".toLowerCase());
                break;
            case FINNISH:
                locale = new Locale("fi".toLowerCase());
                break;
            case SWEDISH:
                locale = new Locale("sv".toLowerCase());
                break;
        }
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        System.out.println("###################### "+settingStorage.getSelectedLang()+" ##################");
    }
}