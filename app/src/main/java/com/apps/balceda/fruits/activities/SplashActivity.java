package com.apps.balceda.fruits.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
      }
    };

    Timer timer = new Timer();
    timer.schedule(timerTask,2000);
  }
}
