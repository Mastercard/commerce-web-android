package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;



public class RememberMeScreenRobot {

TestData testData=new TestData();
    /**
     * This method is used to validate RememberMe Button
     * @return
     */
    public RememberMeScreenRobot validateRememberBtn(Context context){
        ViewInteraction rememberMeBtn = onView(allOf(withId(R.id.remember_me_button), isDisplayed()));
        rememberMeBtn.check(matches(withText(testData.getStringsFromJson(context,"RememberMe_btn"))));
        return this;
    }
    /**
     * This method is used to validate RememberMe Button
     * @return
     */

    public RememberMeScreenRobot clickRememberBtn(){
        ViewInteraction rememberMeBtn = onView(allOf(withId(R.id.remember_me_button), isDisplayed()));
        rememberMeBtn.perform(click());
        return this;
    }
    /**
     * This method is used to validate Not Now Button
     * @return
     */
    public RememberMeScreenRobot validateNotNowBtn(Context context){
        ViewInteraction notNowBtn = onView(allOf(withId(R.id.not_now_button), isDisplayed()));
        notNowBtn.check(matches(withText(testData.getStringsFromJson(context,"NotNow_btn"))));
        return this;
    }
    /**
     * This method is used to click on Not Now Button
     * @return
     */
    public RememberMeScreenRobot clickNotNowBtn(){
        ViewInteraction notNowBtn = onView(allOf(withId(R.id.not_now_button), isDisplayed()));
        notNowBtn.perform(click());
        return this;
    }

}
