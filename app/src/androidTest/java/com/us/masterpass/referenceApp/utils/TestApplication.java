package com.us.masterpass.referenceApp.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;

/**
 *
 */
public class TestApplication extends ItemsActivity implements Application.ActivityLifecycleCallbacks {

    private Activity currentActivity;


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }
}
