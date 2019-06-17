package com.us.masterpass.referenceApp.robot;

import android.support.test.espresso.ViewInteraction;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class SecureIDRobot {

  TestData testData = new TestData();

  SecureIDRobot enterExistingUserID(String userID) {
    ViewInteraction editTextBox = onView(allOf(withId(R.id.editText_email_or_mobile_number),isDisplayed()));
    editTextBox.perform(replaceText(userID));
    return this;
  }

}
