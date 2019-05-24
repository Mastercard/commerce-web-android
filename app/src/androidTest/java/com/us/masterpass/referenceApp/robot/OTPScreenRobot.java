package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class OTPScreenRobot {

  TestData testData = new TestData();

  /**
   * This method is used to Enter OTP on OTP screen
   */

  public OTPScreenRobot enterOTP(String otp) {
    ViewInteraction editText = onView(allOf(withId(R.id.verify_otp_code_edit_text), isDisplayed()));
    editText.perform(replaceText(otp), ViewActions.closeSoftKeyboard());
    return this;
  }

  /**
   * This method is to verify incorrect OTP message
   */
  public OTPScreenRobot validateIncorrectOTPMessage(Context context) {
    ViewInteraction editText = onView(allOf(withId(R.id.verify_otp_code_edit_text), isDisplayed()));
    editText.perform(click());
    editText.check(matches(hasErrorText(testData.getStringsFromJson(context,"invalid_otp_message"))));
    return this;
  }

  /**
   * This method is to verify account lock message
   */
  public OTPScreenRobot validateAccountLockMessage(Context context) {
    ViewInteraction message = onView(allOf(withId(R.id.message), isDisplayed()));
    message.check(matches(withText(testData.getStringsFromJson(context, "account_lock_message"))));
    return this;
  }

  /**
   * This method is to tap on ok button of error pop up
   * @param context
   * @return
   */

  public OTPScreenRobot tapOnOk(Context context){
    ViewInteraction popUp = onView(allOf(withText(testData.getStringsFromJson(context, "ok_btn_text"))));
    popUp.perform(click());
    return this;
  }

  /**
   * This method is to tap on continue button on otp screen
   * @return
   */

  public OTPScreenRobot tapOnContinue(){
      ViewInteraction button = onView(allOf(withId(R.id.button_continue), isEnabled()));
      button.perform(click());
    return this;
  }

  /**
   * This method is to validate terms and condition on otp screen
   * @param context
   * @return
   */

  public OTPScreenRobot validateTnCLink(Context context){
    //TODO : Implement code once issue related to same id has been resolved
    return this;
  }

  /**
   * This method is to validate privacy on otp screen
   * @param context
   * @return
   */


  public OTPScreenRobot validatePrivacy(Context context){
    //TODO : Implement code once issue related to same id has been resolved
    return this;
  }

  /**
   * This method is to validate cookie content on otp screen
   * @param context
   * @return
   */


  public OTPScreenRobot validateCookieContent(Context context){
    //TODO : Implement code once issue related to same id has been resolved
    return this;
  }

  /**
   * This method is to validate trade mark test on otp screen
   * @param context
   * @return
   */


  public OTPScreenRobot validateTradeMarkText(Context context){
    //TODO : Implement code once issue related to same id has been resolved
    return this;
  }

  /**
   * This method is to tap on cancel and return merchant link
   * @return
   */

  public OTPScreenRobot tapOnCancelAndReturnLink(){
    onView(withIndex(withId(R.id.lbl_cancel), 1)).perform(click());
    return this;
  }

  public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
    return new TypeSafeMatcher<View>() {
      int currentIndex = 0;

      @Override
      public void describeTo(Description description) {
        description.appendText("with index: ");
        description.appendValue(index);
        matcher.describeTo(description);
      }

      @Override
      public boolean matchesSafely(View view) {
        return matcher.matches(view) && currentIndex++ == index;
      }
    };
  }
}