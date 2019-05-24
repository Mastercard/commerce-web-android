package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.mastercard.alberta.dcf.presentation.DcfActivity;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.us.masterpass.referenceApp.utils.UiAutomatorUtil.scrollTillTextDisplay;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.allOf;

public class AssociateScreenRobot {
  TestData testData = new TestData();
  @Rule
  public ActivityTestRule<DcfActivity> mActivityTestRule = new ActivityTestRule<>(DcfActivity.class);

  /**
   * This method is used to enter first name on add billing address screen
   * @param firstName
   * @return
   */

  public AssociateScreenRobot enterFistName(String firstName) {
    ViewInteraction editText = onView(allOf(withId(R.id.edittext_card_billingaddress_firstname), isDisplayed()));
    editText.perform(replaceText(firstName));
    return this;
  }

  /**
   * This method is used to enter first name on add shipping address screen
   * @param firstName
   * @return
   */

  public AssociateScreenRobot enterFistNameForShippingAddress(String firstName) throws UiObjectNotFoundException {
    scrollTillTextDisplay("Shipping Address");
    ViewInteraction textInputEditText = onView(
            allOf(withId(R.id.edittext_card_billingaddress_firstname),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textinputlayout_card_billingaddress_firstname),
                                    0),
                            0),
                    isDisplayed()));
    textInputEditText.perform(replaceText(firstName));
    return this;
  }

  /**
   * This method is used to enter last name on add billing address screen
   * @param lastName
   * @return
   */

  public AssociateScreenRobot enterLastName(String lastName) {
    ViewInteraction editText = onView(allOf(withId(R.id.edittext_card_billingaddress_lastname), isDisplayed()));
    editText.perform(replaceText(lastName));
    closeSoftKeyboard();
    return this;
  }

  /**
   * This method is used to enter last name on add shipping address screen
   * @param lastName
   * @return
   */

  public AssociateScreenRobot enterLastNameforShippingAddress(String lastName) {
    ViewInteraction textInputEditText = onView(
            allOf(withId(R.id.edittext_card_billingaddress_lastname),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textinputlayout_card_billingaddress_lastname),
                                    0),
                            0),
                    isDisplayed()));
     textInputEditText.perform(replaceText(lastName));
    return this;
  }

  /**
   * This method is used to enter address line1 name on add billing address screen
   * @param addressLine1
   * @return
   */

  public AssociateScreenRobot enterAddressLine1(String addressLine1) {
    ViewInteraction editText = onView(allOf(withId(R.id.edittext_card_billingaddress_line1), isDisplayed()));
    editText.perform(replaceText(addressLine1));
    closeSoftKeyboard();
    return this;
  }

  /**
   * This method is used to enter address line1 name on add shipping address screen
   * @param addressLine1
   * @return
   */

  public AssociateScreenRobot enterAddressLine1forShippingAddress(String addressLine1) {
    ViewInteraction textInputEditText2 = onView(
            allOf(withId(R.id.edittext_card_billingaddress_line1),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textinputlayout_card_billingaddress_line1),
                                    0),
                            0),
                    isDisplayed()));
    textInputEditText2.perform(replaceText(addressLine1), ViewActions.closeSoftKeyboard());
    return this;
  }

  /**
   * This method is used to enter address line2 on add billing address screen
   * @param addressLine2
   * @return
   */

  public AssociateScreenRobot enterAddressLine2(String addressLine2) {
    ViewInteraction editText = onView(allOf(withId(R.id.edittext_card_billingaddress_line2), isDisplayed()));
    editText.perform(replaceText(addressLine2));
    closeSoftKeyboard();
    return this;
  }

  /**
   * This method is used to enter address line2 on add shipping address screen
   * @param addressLine2
   * @return
   */

  public AssociateScreenRobot enterAddressLine2forShippingAddress(String addressLine2) {
    ViewInteraction textInputEditText3 = onView(
            allOf(withId(R.id.edittext_card_billingaddress_line2),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textinputlayout_card_billingaddress_line2),
                                    0),
                            0),
                    isDisplayed()));
    textInputEditText3.perform(replaceText(addressLine2), ViewActions.closeSoftKeyboard());
    return this;
  }

  /**
   * This method is used to enter city on add billing address screen
   * @param city
   * @return
   */

  public AssociateScreenRobot enterCity(String city) {
    ViewInteraction editText = onView(allOf(withId(R.id.edittext_card_billingaddress_city), isDisplayed()));
    editText.perform(replaceText(city));
    closeSoftKeyboard();
    return this;
  }

  /**
   * This method is used to enter city on add shipping address screen
   * @param city
   * @return
   */

  public AssociateScreenRobot enterCityforShippingAddress(String city) throws Exception {
    //Todo: Need to fix the swipeUp method and replace with better solution

    for(int i=0;i<=5;i++){
      onView(withText("Create your SRC profile")).perform(swipeUp());
    }
    ViewInteraction textInputEditText4 = onView(
            allOf(withId(R.id.edittext_card_billingaddress_city),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textinputlayout_card_billingaddress_city),
                                    0),
                            0),
                    isDisplayed()));
    textInputEditText4.perform(replaceText(city), ViewActions.closeSoftKeyboard());
    return this;
  }

  /**
   * This method is used to enter state on add billing address screen
   * @param state
   * @return
   */

  public AssociateScreenRobot enterState(String state) {
    ViewInteraction editText = onView(allOf(withId(R.id.edittext_card_billingaddress_state), isDisplayed()));
    editText.perform(replaceText(state),ViewActions.closeSoftKeyboard());
    return this;
  }

  /**
   * This method is used to enter state on add shipping address screen
   * @param state
   * @return
   */

  public AssociateScreenRobot enterStateforShippingAddress(String state) throws UiObjectNotFoundException {

    ViewInteraction textInputEditText = onView(
            allOf(withId(R.id.edittext_card_billingaddress_state),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textinputlayout_card_billingaddress_state),
                                    0),
                            0),
                    isDisplayed()));
    textInputEditText.perform(replaceText(state));
    closeSoftKeyboard();
    return this;
  }

  /**
   * This method is used to enter ZipCode on add billing address screen
   * @param zipCode
   * @return
   */

  public AssociateScreenRobot enterZipCode(String zipCode) throws UiObjectNotFoundException {
    ViewInteraction editText = onView(allOf(withId(R.id.edittext_card_billingaddress_zipcode), isDisplayed()));
    editText.perform(replaceText(zipCode));
    closeSoftKeyboard();
    scrollTillTextDisplay("Billing Address");
    return this;
  }

  /**
   * This method is used to enter ZipCode on add shipping address screen
   * @param zipCode
   * @return
   */

  public AssociateScreenRobot enterZipCodeforShippingAddress(String zipCode) {
    ViewInteraction textInputEditText3 = onView(
            allOf(withId(R.id.edittext_card_billingaddress_zipcode),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textinputlayout_card_billingaddress_zipcode),
                                    0),
                            0),
                    isDisplayed()));
    textInputEditText3.perform(click(), replaceText(zipCode));
    closeSoftKeyboard();
    return this;
  }

  /**
   * This method is used to click billing as shipping address checkbox
   */

  public AssociateScreenRobot checkBillingAsShipping() {
    ViewInteraction btn = onView(allOf(withId(R.id.checkbox_shipping_address), isDisplayed()));
    btn.perform(scrollTo(), click());
    return this;
  }

  /**
   * This method is used to tap on continue button on associate screen
   */

  public AssociateScreenRobot tapOnContinueButton(){
    ViewInteraction btn = onView(allOf(withId(R.id.payment_screen_continue_button), isDisplayed()));
    btn.perform(scrollTo(), click());
    return this;
  }

  /**
   * This method is used to verify duplicate user error on associate screen
   */

  public AssociateScreenRobot verifyDuplicateError(Context context) {
    ViewInteraction appCompatButton3 = onView(
            allOf(withId(android.R.id.button1), withText(testData.getStringsFromJson(context, "Popup_dismiss_btn_text"))));
    appCompatButton3.perform(scrollTo(), click());
    return this;
  }

  /**
   * This method is used to get total transaction amount on associate card screen
   * @return
   */

  public String getTotalAmount(){
    MiscellaneousRobot robot = new MiscellaneousRobot();
    String amount = robot.getText(withId(R.id.payment_screen_amount));
    return amount;
  }

  /**
   * This method is used to verify terms and conditions link on associate screen
   */

  public AssociateScreenRobot tncLink(Context context) throws UiObjectNotFoundException {
    closeSoftKeyboard();
    ViewInteraction textView = onView(
            allOf(withId(R.id.textview_terms_and_conditions), withText(testData.getStringsFromJson(context,"legal_content_tnc_text")),
                    isDisplayed()));
    scrollTillTextDisplay("Terms and Conditions");
    textView.check(matches(withText(testData.getStringsFromJson(context,"legal_content_tnc_text"))));
    return this;
  }

  /**
   * This method is used to verify privacy policy link on associate screen
   */

  public AssociateScreenRobot privacyPolicyLink(Context context){
    ViewInteraction textView2 = onView(
            allOf(withId(R.id.textview_privacy_policy), withText(testData.getStringsFromJson(context, "legal_content_privacy_text")),
                    isDisplayed()));
    textView2.check(matches(withText(testData.getStringsFromJson(context, "legal_content_privacy_text"))));
    return this;
  }

  /**
   * This method is used to verify cookie consent link on associate screen
   */

  public AssociateScreenRobot cookieConsentLink(Context context){
    ViewInteraction textView3 = onView(
            allOf(withId(R.id.textview_cookie_consent), withText(testData.getStringsFromJson(context, "legal_content_cookie_text")),
                    isDisplayed()));
    textView3.check(matches(withText(testData.getStringsFromJson(context, "legal_content_cookie_text"))));

    return this;
  }

  /**
   * This method is used to cancel checkout from associate screen
   */

  public AssociateScreenRobot cancelCheckout(Context context) throws UiObjectNotFoundException {
    closeSoftKeyboard();
    scrollTillTextDisplay("Terms and Conditions");
    ViewInteraction textView = onView(
            allOf(withId(R.id.lbl_cancel), isDisplayed()));
    textView.perform(click());
    return this;
  }

  /**
   * This method is used to enter email ID on associate screen
   * @param email
   * @return
   */
  public AssociateScreenRobot enterEmailID(String email) throws UiObjectNotFoundException {
    scrollTillTextDisplay("Terms and Conditions");
    ViewInteraction editText14 = onView(
            allOf(withId(R.id.editText_securePayId_email),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textInputLayout_securePayId_email),
                                    0),
                            0),
                    isDisplayed()));
    editText14.perform(replaceText(email));
    closeSoftKeyboard();
    return this;
  }

  /**
   * This method is used to enter mobile no. on associate screen
   * @param mobileNo
   * @return
   */
  public AssociateScreenRobot enterMobileNo(String mobileNo) {
    ViewInteraction editText15 = onView(
            allOf(withId(R.id.editText_securePayId_phone),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.textInputLayout_securePayId_phone),
                                    0),
                            0),
                    isDisplayed()));
    editText15.perform(replaceText(mobileNo));
    closeSoftKeyboard();
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
