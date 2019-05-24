package com.us.masterpass.referenceApp.robot;

import android.support.test.espresso.ViewInteraction;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.us.masterpass.merchantapp.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;

public class MerchantAppConfigurationRobot {

  /**
   * This method is used to open allowed card brand selection screen in merchant checkout app
   */

  public MerchantAppConfigurationRobot openCardBrandSelectionScreen() {
    ViewInteraction linearLayout = onView(
        allOf(childAtPosition(
            withId(R.id.settings_list),
            0),
            isDisplayed()));
    linearLayout.perform(click());
    return this;
  }

  /**
   * This method is used to open language selection screen in merchant checkout app
   */

  public MerchantAppConfigurationRobot openLanguageSelectionScreen() {
    ViewInteraction linearLayout = onView(
        allOf(childAtPosition(
            withId(R.id.settings_list),
            1),
            isDisplayed()));
    linearLayout.perform(click());
    return this;
  }

  /**
   * This method is used to change suppress shipping setting (on/off) in merchant checkout app
   */

  public MerchantAppConfigurationRobot changeSuppressShippingSwitch() {

    ViewInteraction switch_shipping = onView(
        allOf(withId(R.id.settings_switch),
            withParent(childAtPosition(
                withId(R.id.settings_list),
                4)),
            isDisplayed()));
    switch_shipping.perform(click());
    return this;
  }

  /**
   * This method is used to enable/disable merchant as express
   */

  public MerchantAppConfigurationRobot changeExpressCheckoutSwitch() {

    ViewInteraction switch_express = onView(
        allOf(withId(R.id.settings_switch),
            withParent(childAtPosition(
                withId(R.id.settings_list),
                5)),
            isDisplayed()));
    switch_express.perform(click());
    return this;
  }

  /**
   * This method is used to enable/disable payment method
   */

  public MerchantAppConfigurationRobot enablePaymentMethod() {
    ViewInteraction switch_4 = onView(
        allOf(withId(R.id.settings_switch),
            withParent(childAtPosition(
                withId(R.id.settings_list),
                6)),
            isDisplayed()));
    switch_4.perform(click());
    return this;
  }

  /**
   * This method is used to open DSRP cryptogram selection screen
   */

  public MerchantAppConfigurationRobot openCryptoSelectionScreen() {

    ViewInteraction linearLayout = onView(
        allOf(childAtPosition(
            withId(R.id.settings_list),
            7),
            isDisplayed()));
    linearLayout.perform(click());
    return this;
  }

  /**
   * This method is used to select EnglishUK locale from language menu
   */

  public MerchantAppConfigurationRobot tapOnEnglishUK() {
    tapOnCheckboxByPositionForSelectionMenu(0);
    return this;
  }

  /**
   * This method is used to select EnglishUS locale from language menu
   */

  public MerchantAppConfigurationRobot tapOnEnglishUS() {
    tapOnCheckboxByPositionForSelectionMenu(1);
    return this;
  }

  /**
   * This method is used to select Portuguese locale from language menu
   */

  public MerchantAppConfigurationRobot tapOnPortugueseBR() {
    tapOnCheckboxByPositionForSelectionMenu(2);
    return this;
  }

  /**
   * This method is used to select spanish locale from language menu
   */

  public MerchantAppConfigurationRobot tapOnSpanishMX() {
    tapOnCheckboxByPositionForSelectionMenu(3);
    return this;
  }

  /**
   * This method is used to select spanish locale from language menu
   */

  public MerchantAppConfigurationRobot tapOnEnglishCA() {
    tapOnCheckboxByPositionForSelectionMenu(4);
    return this;
  }

  /**
   * This method is used to select AMEX card brand from allowed card type menu
   */

  public MerchantAppConfigurationRobot tapOnAmericanExpress() {
    tapOnCheckboxByPositionForSelectionMenu(0);
    return this;
  }

  /**
   * This method is used to tap on checkbox for any configuration having selection option i.e cards,
   * locale
   */

  public MerchantAppConfigurationRobot tapOnCheckboxByPositionForSelectionMenu(int i) {
    onData(anything()).
        inAdapterView(withId(R.id.settings_list)).
        onChildView(withId(R.id.settings_check)).
        atPosition(i).
        perform(click()).check(matches(isDisplayed()));
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
