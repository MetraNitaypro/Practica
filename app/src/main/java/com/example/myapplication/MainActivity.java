package com.example.myapplication;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.databinding.FragmentDashboardBinding;
import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BroadcastReceiver br;
    public final static String brodcast = "Start";
    public final static String param_1 = "Param_1";
    public final static String param_2 = "Param_2";
    public final static String param_3 = "Param_3";
    TextView tex;
    public final static String BROADCAST_ACTION = "ru.startandroid.develop.p0961servicebackbroadcast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_general)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        // TextView abb = findViewById(R.id.textView);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // создаем фильтр для BroadcastReceiver

        // регистрируем (включаем) BroadcastReceiver
        //    registerReceiver(br,intFilt);


    }

    public void onClickStart(View v) {


        //tex = binding_1.textView;

        //Fragment abb = getFragmentManager().findFragmentById(R.id.fr_1);
        // Создаем Intent для вызова сервиса,
        // кладем туда параметр времени и код задачи
        //Intent intent;
        //intent = new Intent(this, MyService.class).putExtra(param_1, 7)
        //   .putExtra(param_2, 1);
        // стартуем сервис
        //startService(intent);
    }


}