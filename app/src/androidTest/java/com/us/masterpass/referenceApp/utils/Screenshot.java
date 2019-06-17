package com.us.masterpass.referenceApp.utils;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import com.squareup.spoon.Spoon;
import java.io.File;




public class Screenshot {
    public static void capture(String title){
        Activity activity = ((TestApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
        File outputFile = Spoon.screenshot(activity, title);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).takeScreenshot(outputFile);
    }
    public static void capture(String title, String testClass, String testMethod) {
        Activity activity = ((TestApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
        File outputFile = Spoon.screenshot(activity, title, testClass, testMethod);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).takeScreenshot(outputFile);
    }
}
