package com.example.trackermvc.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.trackermvc.arrivals.controller.ArrivalsActivity;
import com.example.trackermvc.arrivals.model.Arrival;
import com.example.trackermvc.stops.controller.StopsActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(StopsActivity.launch(this));
    }
}
