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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.us.masterpass.referenceApp.utils.UiAutomatorUtil.scrollTillTextDisplay;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

public class ShippingAddressListScreenRobot {
  TestData testData = new TestData();

  /**
   * This method is used to click on Select Shipping address on Address List Screen
   * @return
   */
  public ShippingAddressListScreenRobot selectFirstShippingAddress() {
    ViewInteraction linearLayout = onView(
            allOf(withId(R.id.layout_shipping_address), isDisplayed()));
    linearLayout.perform(scrollTo(), click());
    return this;
  }

  /**
   * This method is used to verify terms and conditions link on address list screen
   */

  public ShippingAddressListScreenRobot tncLink() throws UiObjectNotFoundException {
    /* FIXME: Need to fix terms and condition link. */
    return this;
  }

  /**
   * This method is used to verify privacy policy link on address list screen
   */

  public ShippingAddressListScreenRobot privacyPolicyLink(){
    /* FIXME: Need to fix privacy policy link. */
    return this;
  }

  /**
   * This method is used to verify cookie consent link on address list screen
   */

  public ShippingAddressListScreenRobot cookieConsentLink(){
    /* FIXME: Need to fix cookie consent link. */
    return this;
  }

  /**
   * This method is used to click on Add Shipping address button
   */

  public ShippingAddressListScreenRobot clickOnAddButton(Context context){
    ViewInteraction textView5 = onView(
            allOf(withId(R.id.textview_add_shipping_address), withText(testData.getStringsFromJson(context,"AddButton"))));
    textView5.perform(scrollTo(), click());
    return this;
  }

  /**
   * This method is used to cancel checkout from address list screen
   */

  public ShippingAddressListScreenRobot cancelCheckout() throws UiObjectNotFoundException {
    ViewInteraction textView = onView(
            allOf(withId(R.id.lbl_cancel), isDisplayed()));
    textView.perform(click());
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
