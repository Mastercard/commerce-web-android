package com.mastercard.testapp.presentation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.mastercard.testapp.R;

/**
 * Created by Sebastian Farias on 11-10-17.
 *
 * Splash screen
 */
public class SplashScreenActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_screen);

    Thread logoTimer = new Thread() {
      public void run() {
        try {
          int logoTimer = 0;
          while (logoTimer < 500) {
            sleep(100);
            logoTimer = logoTimer + 100;
          }
          Intent intent = new Intent(SplashScreenActivity.this, ItemsActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
          startActivity(intent);
          finish();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          finish();
        }
      }
    };
    logoTimer.start();
  }
}
