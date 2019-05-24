package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import android.view.View;

import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;

import org.hamcrest.core.IsInstanceOf;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class EnrollCardScreenRobot {
  TestData testData = new TestData();

  /**
   * This method is used to enter card number on enroll card screen
   */

  public EnrollCardScreenRobot enterCardNumber(String cardNumber) {
    ViewInteraction editText = onView(allOf(withId(R.id.editText_card_number), isDisplayed()));
    editText.perform(replaceText(cardNumber));
    return this;
  }

  /**
   * This method is used to enter cardHolder name enroll card screen
   */

  public EnrollCardScreenRobot enterCardHolderName(String cardHolderName) {
    ViewInteraction editText = onView(allOf(withId(R.id.editText_cardholder_name), isDisplayed()));
    editText.perform(replaceText(cardHolderName));
    return this;
  }

  /**
   * This method is used to enter expiryDate on enroll card screen
   */

  public EnrollCardScreenRobot enterCardExpiry(String expiry) {
    ViewInteraction editText = onView(allOf(withId(R.id.editText_expiry), isDisplayed()));
    editText.perform(replaceText(expiry));
    return this;
  }

  /**
   * This method is used to enter cvc on enroll card screen
   */

  public EnrollCardScreenRobot enterCVC(String cvc) {
    ViewInteraction editText = onView(allOf(withId(R.id.editText_cvc), isDisplayed()));
    editText.perform(replaceText(cvc));
    return this;
  }

  /**
   * This method is to enter email address of returning user
   */
  public EnrollCardScreenRobot enterEmailAddress(String email) {
    ViewInteraction editText =
        onView(allOf(withId(R.id.editText_email_or_mobile_number), isDisplayed()));
    editText.perform(replaceText(email), closeSoftKeyboard());
    return this;
  }

  /**
   * This method is used to clear card number on enroll card screen
   */

  public EnrollCardScreenRobot clearCardNumber() {
    ViewInteraction editText = onView(allOf(withId(R.id.editText_card_number), isDisplayed()));
    editText.perform(clearText());
    return this;
  }

  /**
   * This method is used to clear cardHolder name enroll card screen
   */

  public EnrollCardScreenRobot clearCardHolderName() {
    ViewInteraction editText = onView(allOf(withId(R.id.editText_cardholder_name), isDisplayed()));
    editText.perform(clearText());
    return this;
  }

  /**
   * This method is used to clear expiryDate on enroll card screen
   */

  public EnrollCardScreenRobot clearCardExpiry() {
    ViewInteraction editText = onView(allOf(withId(R.id.editText_expiry), isDisplayed()));
    editText.perform(clearText());
    return this;
  }

  /**
   * This method is used to clear cvc on enroll card screen
   */

  public EnrollCardScreenRobot clearCVC() {
    ViewInteraction editText = onView(allOf(withId(R.id.editText_cvc), isDisplayed()));
    editText.perform(clearText());
    return this;
  }

  /**
   * This method is to clear email address of returning user
   */
  public EnrollCardScreenRobot clearEmailAddress() {
    ViewInteraction editText =
        onView(allOf(withId(R.id.editText_email_or_mobile_number), isDisplayed()));
    editText.perform(clearText());
    return this;
  }

  /**
   * This method is used to tap on continue button on enroll card screen
   */

  public EnrollCardScreenRobot tapOnContinue() {
    ViewInteraction button = onView(allOf(withId(R.id.button_continue), isEnabled()));
    button.perform(scrollTo(), click());
    return this;
  }

  public EnrollCardScreenRobot continueBtnDisabled() {
    ViewInteraction button = onView(allOf(withId(R.id.button_continue), isDisplayed()));
    button.check(matches(not(isEnabled())));
    return this;
  }

  /**
   * This method is used to tap on cancel button on enroll card screen to return back to merchant app
   */

  public EnrollCardScreenRobot tapOnCancelCheckout() {
    closeSoftKeyboard();
    ViewInteraction textView = onView(
            allOf(withId(R.id.lbl_cancel), isDisplayed()));
    textView.perform(click());
    return this;
  }

  /**
   * This method is used to validate error message when user add FPAN card
   */

  public EnrollCardScreenRobot validateFPANErrorMessage(Context context) {
    ViewInteraction message = onView(
        allOf(withText(testData.getStringsFromJson(context, "error_message_for_fpan")),
            isDisplayed()));
    message.check(matches(isDisplayed()));
    return this;
  }

  /**
   * This method is used to validate network error
   */

  public EnrollCardScreenRobot validateNetworkError(Context context) {
    //TODO remove blank error message with correct message once it finilized
    ViewInteraction message =
        onView(allOf(withText(testData.getStringsFromJson(context, "")), isDisplayed()));
    message.check(matches(isDisplayed()));
    return this;
  }

  /**
   * This method is used to tap on returning user tab to enter secure pay ID for existing user
   */

  public EnrollCardScreenRobot tapOnReturningUser(Context context) {
    ViewInteraction tab = onView(
        allOf(withText(testData.getStringsFromJson(context, "returning_user_tab")), isDisplayed()));
    tab.perform(click());
    return this;
  }

  /**
   * This method is used to tap on enroll card tab to get enter card form
   */

  public EnrollCardScreenRobot tapOnEnrollCardTab(Context context) {
    ViewInteraction tab = onView(
        allOf(withText(testData.getStringsFromJson(context, "new_user_tab")), isDisplayed()));
    tab.perform(click(), closeSoftKeyboard());
    return this;
  }

  /**
   * This method is used to get total transaction amount on enroll card screen
   */

  public String getTotalAmount() {
    MiscellaneousRobot robot = new MiscellaneousRobot();
    String amount = robot.getText(withId(R.id.textView_amount));
    return amount;
  }

  /**
   * This method is to validate that email field is empty
   */

  public EnrollCardScreenRobot validateEmailFieldIsEmpty(Context context) {
    MiscellaneousRobot robot = new MiscellaneousRobot();
    String text = robot.getText(withId(R.id.editText_email_or_mobile_number));
    text.equals(testData.getStringsFromJson(context, "enroll_email_field_placeholder"));
    return this;
  }

  /**
   * This method is to check account lock error message on user email address entry screen
   */
  public EnrollCardScreenRobot validateAccountLockMessage(Context context) {
    ViewInteraction editText =
        onView(allOf(withId(R.id.editText_email_or_mobile_number), isDisplayed()));
    editText.check(matches(hasErrorText(testData.getStringsFromJson(context, ""))));
    return this;
  }

}