package com.us.masterpass.referenceApp.robot;

import android.support.test.espresso.ViewInteraction;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.instruction.merchantLoginPageDisplayed;
import com.us.masterpass.referenceApp.utils.ConditionWatcher;
import com.us.masterpass.referenceApp.utils.TestData;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class MerchantCheckoutAppRobot {

  TestData testData = new TestData();

  /**
   * This method is used to add item from product screen to card
   */

  public MerchantCheckoutAppRobot addItemOneToCart() {
    onData(anything()).
        inAdapterView(withId(R.id.items_list_grid)).
        onChildView(withId(R.id.item_cell_add)).
        atPosition(0).
        check(matches(isDisplayed()))
        .perform(click());
    return this;
  }

  /**
   * This method is used to go to cart
   */

  public MerchantCheckoutAppRobot tapOnAddToCart() {
    ViewInteraction relativeLayout = onView(
        allOf(withId(R.id.toolbar_checkout), isDisplayed()));
    relativeLayout.perform(click());
    return this;
  }

  /*
  This method is used to tap on src button from cart confirmation screen
   */

  public MerchantCheckoutAppRobot tapOnSRCButton() {
    ViewInteraction appCompatTextView = onView(
        allOf(withId(R.id.tv_src_mark), withText("SRC-MARK"),
            childAtPosition(
                allOf(withId(R.id.ll_bottom_button),
                    childAtPosition(
                        withClassName(is("android.widget.RelativeLayout")),
                        2)),
                0),
            isDisplayed()));
    appCompatTextView.perform(click());
    return this;
  }

  /**
   * This method is used to tap on side menu to open login and merchant configuration
   */

  public MerchantCheckoutAppRobot openSideMenu() {

    ViewInteraction linearLayout = onView(
        allOf(withId(R.id.toolbar_settings),
            withParent(withId(R.id.ll_toolbar)),
            isDisplayed()));
    linearLayout.perform(click());

    return this;
  }

  /**
   * This method is used to go to merchant configuration screen
   */

  public MerchantCheckoutAppRobot openConfigurationMenu() {
    ViewInteraction appCompatImageView = onView(
        allOf(withId(R.id.menu_settings),
            withParent(withId(R.id.menu_item)),
            isDisplayed()));
    appCompatImageView.perform(click());
    return this;
  }

  /**
   * This method is used to go to login screen from main screen
   */

  public MerchantCheckoutAppRobot openLoginScreen() {
    ViewInteraction appCompatImageView = onView(
        allOf(withId(R.id.menu_login),
            withParent(withId(R.id.menu_item)),
            isDisplayed()));
    appCompatImageView.perform(click());
    return this;
  }

  /**
   * This method is used to add same item in checkout screen
   */

  public MerchantCheckoutAppRobot addItemOnCheckoutScreen() {
    ViewInteraction appCompatImageView3 = onView(
        allOf(withId(R.id.item_cell_add), isDisplayed()));
    appCompatImageView3.perform(click());
    return this;
  }

  /**
   * This method is used to remove item from cart screen
   */

  public MerchantCheckoutAppRobot removeItemOnCheckoutScreen() {
    ViewInteraction appCompatImageView3 = onView(
        allOf(withId(R.id.item_cell_add), isDisplayed()));
    appCompatImageView3.perform(click());
    return this;
  }

  /**
   * This method is used to do login into merchant app
   *
   * @throws Exception
   */

  public MerchantCheckoutAppRobot loginIntoApp() throws Exception {
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new merchantLoginPageDisplayed());
    ViewInteraction appCompatEditText4 = onView(
        allOf(withId(R.id.username_edit_text), isDisplayed()));
    appCompatEditText4.perform(replaceText("jsmith"), closeSoftKeyboard());

    ViewInteraction appCompatEditText5 = onView(
        allOf(withId(R.id.password_edit_text), isDisplayed()));
    appCompatEditText5.perform(replaceText("password"), closeSoftKeyboard());

    ViewInteraction appCompatTextView = onView(
        allOf(withId(R.id.login_login), withText("Login"), isDisplayed()));
    appCompatTextView.perform(click());
    return this;
  }

  /*
    */
/**
   * This method is to validate checkout failure screen message
   */  /*


  public MerchantCheckoutAppRobot checkoutFailureMessage() {
    ViewInteraction message =
        onView(allOf(withId(R.id.txt_faildesc), withText("There was an issue with your payment \n"
            + "information. You have not been charged.\n"
            + "The order has not been completed.\n"
            + "\n"
            + "Please go through checkout to try again.\n"
            + "\n"), isDisplayed()));
    message.check(matches(isDisplayed()));
    return this;
  }
  */

  /**
   * This method is used to validate src button is displaying or not
   */

  public MerchantCheckoutAppRobot isSRCButtonVisible() {
    ViewInteraction appCompatTextView = onView(
        allOf(withId(R.id.tv_src_mark), withText("SRC-MARK"),
            childAtPosition(
                allOf(withId(R.id.ll_bottom_button),
                    childAtPosition(
                        withClassName(is("android.widget.RelativeLayout")),
                        2)),
                0),
            isDisplayed()));
    appCompatTextView.check(matches(isDisplayed()));
    return this;
  }

  public String getTotalAmount(){
    MiscellaneousRobot robot = new MiscellaneousRobot();
    String amount = robot.getText(withId(R.id.cart_total_text));
    return amount;
  }

/*
  *//**
   * This method is to tap on retry checkout button from checkout failure screen
   *//*

  public MerchantCheckoutAppRobot tapOnRetryCheckout() {
    ViewInteraction button =
        onView(allOf(withId(R.id.btn_retry_checkout), withText("Retry Checkout"), isDisplayed()));
    button.perform(click());
    return this;
  }

  *//**
   * This method is to tap on done button from checkout success screen
   *//*

  public MerchantCheckoutAppRobot tapDoneButton() {
    ViewInteraction button =
        onView(allOf(withId(R.id.btn_done), withText("Done"), isDisplayed()));
    button.perform(click());
    return this;
  }*/

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

