package com.us.masterpass.referenceApp.instruction;

import android.support.test.espresso.NoMatchingViewException;
import com.us.masterpass.referenceApp.utils.Instruction;
import android.support.test.espresso.ViewInteraction;
import com.us.masterpass.merchantapp.R;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class otpScreenDisplayed extends Instruction{

  @Override public String getDescription() {
    return null;
  }

  @Override public boolean checkCondition() {
    try {
       ViewInteraction editText = onView(allOf(withId(R.id.verify_otp_code_edit_text), isDisplayed()));
       editText.check(matches(isDisplayed()));
      return true;
    } catch (NoMatchingViewException e) {
      return false;
    }
  }

}
