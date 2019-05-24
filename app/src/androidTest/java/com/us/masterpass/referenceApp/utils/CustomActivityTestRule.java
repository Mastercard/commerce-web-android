package com.us.masterpass.referenceApp.utils;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.base.DefaultFailureHandler;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.us.masterpass.merchantapp.BuildConfig;
import com.mastercard.rally.Rally;
import com.mastercard.rally.RallyCreate;
import com.mastercard.rally.RallyFetchTestCaseOID;

import org.hamcrest.Matcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 */

public class CustomActivityTestRule<T extends Activity> extends ActivityTestRule<T> {
    public CustomActivityTestRule(Class<T> activityClass) {
        super(activityClass);
    }

    RallyFetchTestCaseOID rallyObj1 = new RallyFetchTestCaseOID();
    RallyCreate rallyCreate = new RallyCreate();
    //rally api key : for now it is hardcoded to Nikhita's api key will be changed later on to be handled dynamcially
    String sessionid = "_AxBKPQ8qTLes9vU72ub8sn7ljxFuEMeqVWbdFcGkLE";

    @Override
    public Statement apply(final Statement base,final Description description) {
        final String testClassName = description.getClassName();
        final String testMethodName = description.getMethodName();
        final Context context = InstrumentationRegistry.getTargetContext();
        Rally rally = description.getAnnotation(Rally.class);
        if (rally!=null) {
            String[] testCase = rally.testcase();
            Log.i("the string length" + testCase.length, "tests");
            for (int i = 0; i < testCase.length; i++) {

                final String testCaseOID = rallyObj1.getRallyTCOID(testCase[i], sessionid);
                Log.e("testOID", testCaseOID);
                Log.e("the test case is", testCase[i]);
                Log.e("Rally", testCase[i]);
                rallyCreate.createRally("Pass", testCaseOID, testCase[i], sessionid, BuildConfig.FLAVOR,"Logs");
            }
        }

        Espresso.setFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable throwable, Matcher<View> matcher) {

                Rally rally = description.getAnnotation(Rally.class);
                if (rally!=null) {
                    String[] testCase = rally.testcase();
                    for (int i = 0; i < testCase.length; i++) {

                        final String testCaseID = rallyObj1.getRallyTCOID(testCase[i], sessionid);
                        Log.e("testOID", testCaseID);
                        Log.e("the test case is", testCase[i]);
                        Log.e("Rally", testCase[i]);
                        rallyCreate.createRally("Fail", testCaseID, testCase[i], sessionid, BuildConfig.FLAVOR, "Logs");

                    }
                }
                if (!matcher.toString().contains("typefaceTextView_AlertDialog_title")
                        && !matcher.toString().contains("imageview_masterpass_welcome_button")) {
//                    Screenshot.capture("testFailed", testClassName, testMethodName);
                }
                new DefaultFailureHandler(context).handle(throwable, matcher);
            }
        });
        return super.apply(base, description);
    }
}