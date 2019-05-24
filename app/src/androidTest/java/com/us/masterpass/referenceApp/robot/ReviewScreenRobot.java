package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.us.masterpass.merchantapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.us.masterpass.referenceApp.utils.UiAutomatorUtil.scrollTillTextDisplay;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import com.us.masterpass.referenceApp.utils.TestData;

public class ReviewScreenRobot {
    TestData testData = new TestData();

  /**
   * This method is used to verify email id on review screen
   * @param email
   * @return
   */

  public ReviewScreenRobot verifyEmail(String email) {
    MiscellaneousRobot robot = new MiscellaneousRobot();
    String emailDisplayed = robot.getText(withId(R.id.textView_email));
    assertEquals(emailDisplayed, email);
    return this;
  }

    /**
     * This method is used to verify shipping address on review screen
     * @return
     * @param addressLine2
     * @param addressState
     * @param addressCity
     * @param zipCode
     */

    public ReviewScreenRobot verifyShippingAddress(String addressLine2, String addressCity, String addressState, String zipCode) {
        MiscellaneousRobot robot = new MiscellaneousRobot();
        String fullName = robot.getText(withId(R.id.textView_name));
        String line1 = robot.getText(withId(R.id.textView_shippingAddress_line1));
        String line2 = robot.getText(withId(R.id.textView_shippingAddress_line2));
        String cityStateZip = robot.getText(withId(R.id.textView_shippingAddress_city));
        /* String addressFullName = firstName + " " + lastName;*/
        String cityStateZipOnReviewScreen = addressCity + ", " + addressState + " " + zipCode;
        /*verifyShippingAddressFullNameOnReviewScreen(fullName,addressFullName);
        verifyShippingAddressLine1OnReviewScreen(line1,addressLine1);*/
        verifyShippingAddressLine2OnReviewScreen(line2,addressLine2);
        verifyShippingAddressCityStateZipOnReviewScreen(cityStateZipOnReviewScreen,cityStateZip);
        return this;
    }

  /**
   * This method is used to verify city, state and zip code of shipping address on review screen
   * @param cityStateZipOnReviewScreen
   * @param cityStateZip
   * @return
   */
  private ReviewScreenRobot verifyShippingAddressCityStateZipOnReviewScreen(String cityStateZipOnReviewScreen, String cityStateZip) {
    assertEquals(cityStateZipOnReviewScreen, cityStateZip);
    return this;
  }

  /**
   * This method is used to verify line2 of shipping address on review screen
   * @param line2
   * @param addressLine2
   * @return
   */
  private ReviewScreenRobot verifyShippingAddressLine2OnReviewScreen(String line2, String addressLine2) {
    assertEquals(line2, addressLine2);
    return this;
  }

  /**
   * This method is used to verify line2 of shipping address on review screen
   * @param line1
   * @param addressLine1
   * @return
   */
  private ReviewScreenRobot verifyShippingAddressLine1OnReviewScreen(String line1, String addressLine1) {
    assertEquals(line1, addressLine1);
    return this;
  }

  /**
   * This method is used to verify full name of shipping address on review screen
   * @param fullName
   * @param addressFullName
   * @return
   */
  private ReviewScreenRobot verifyShippingAddressFullNameOnReviewScreen(String fullName, String addressFullName) {
    assertEquals(fullName, addressFullName);
    return this;
  }

  /**
   * This method is used to verify mobile no. on review screen
   * @param mobile
   * @return
   */

  public ReviewScreenRobot verifyMobile(String mobile) {
    MiscellaneousRobot robot = new MiscellaneousRobot();
    String mobileDisplayed = robot.getText(withId(R.id.textView_phone));
    mobileDisplayed = mobileDisplayed.replaceAll("-|\\s","");
    assertEquals(mobileDisplayed, mobile);
    return this;
  }

  /**
   * This method is used to navigate back to merchant on review screen
   * @return
   */

  public ReviewScreenRobot cancelCheckout(Context context) {
    ViewInteraction textView = onView(
            allOf(withId(R.id.lbl_cancel), isDisplayed()));
    textView.perform(click());
    return this;
  }

  /**
   * This method is used to click on continue button on review screen
   * @return
   */

  public ReviewScreenRobot clickOnContinue() {
    ViewInteraction appCompatButton = onView(
            allOf(withId(R.id.payment_screen_continue_button), withText("Continue"),
                    childAtPosition(
                            allOf(withId(R.id.paymentButtonLayout),
                                    childAtPosition(
                                            withId(R.id.container),
                                            1)),
                            2)));
    appCompatButton.perform(scrollTo(), click());
    return this;
  }

  /**
   * This method is used to verify T&C link  on review screen
   * @return
   */

  public ReviewScreenRobot verifyTnCLink(Context context) throws UiObjectNotFoundException {
    closeSoftKeyboard();
    ViewInteraction textView = onView(
            allOf(withId(R.id.textview_terms_and_conditions), withText(testData.getStringsFromJson(context,"legal_content_tnc_text")),
                    isDisplayed()));
    textView.check(matches(withText(testData.getStringsFromJson(context,"legal_content_tnc_text"))));
    return this;
  }

  /**
   * This method is used to verify Privacy Notice link  on review screen
   * @return
   */

  public ReviewScreenRobot verifyPPLink(Context context) {
    ViewInteraction textView2 = onView(
            allOf(withId(R.id.textview_privacy_policy), withText(testData.getStringsFromJson(context, "legal_content_privacy_text")),
                    isDisplayed()));
    textView2.check(matches(withText(testData.getStringsFromJson(context, "legal_content_privacy_text"))));
    return this;
  }

  /**
   * This method is used to verify Cookie Consent link  on review screen
   * @return
   */

  public ReviewScreenRobot verifyCCLink(Context context) {
    ViewInteraction textView3 = onView(
            allOf(withId(R.id.textview_cookie_consent), withText(testData.getStringsFromJson(context, "legal_content_cookie_text")),
                    isDisplayed()));
    textView3.check(matches(withText(testData.getStringsFromJson(context, "legal_content_cookie_text"))));
    return this;
  }

  /**
   * This method is used to get total transaction amount on review user screen
   * @return
   */

  public String getAmountOnReviewScreen(){
    MiscellaneousRobot robot = new MiscellaneousRobot();
    String amount = robot.getText(withId(R.id.payment_screen_amount));
    return amount;
  }

  /**
   * This method is used to click on Edit Shipping address on Review Screen
   * @return
   */
  public ReviewScreenRobot clickOnEditIcon() {
    ViewInteraction appCompatImageView = onView(
            allOf(withId(R.id.img_edit), withContentDescription("Edit Address"),
                    childAtPosition(
                            allOf(withId(R.id.layout_selected_shipping_address),
                                    childAtPosition(
                                            withId(R.id.relative_parent),
                                            7)),
                            4)));
    appCompatImageView.perform(scrollTo(), click());
    return this;
  }

  /**
   * This method is used click on navigation icon of card on review screen
   *
   * @return
   */
  public ReviewScreenRobot clickOnNavigationIconOnCard() {
    ViewInteraction btn = onView(
            allOf(withId(R.id.payment_screen_pay_with_container),
                    isDisplayed()));
    btn.perform(click());
    return this;
  }

  private static Matcher<View> childAtPosition(
          final Matcher<View> parentMatcher, final int position) {

    return new TypeSafeMatcher<View>() {
      @Override
      public void describeTo(Description description) {
        description.appendText("Child at position " + position + " in parent ");
        parentMatcher.describeTo(description);
      }

      @Override
      public boolean matchesSafely(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches(parent)
                && view.equals(((ViewGroup) parent).getChildAt(position));
      }
    };
  }

}