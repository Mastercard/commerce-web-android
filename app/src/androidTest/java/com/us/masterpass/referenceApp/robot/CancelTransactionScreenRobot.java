package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class CancelTransactionScreenRobot {
    TestData testData = new TestData();
    @Rule
    public ActivityTestRule<DcfActivity> mActivityTestRule = new ActivityTestRule<>(DcfActivity.class);


    /**
     * This method is used to click on Stay button on Cancel Transaction
     *
     * @return
     */
    public CancelTransactionScreenRobot clickOnStay() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.alert_dialog_button_positive), withText("STAY"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());
        return this;
    }

    /**
     * This method is used to click on Return button on Cancel Transaction
     *
     * @return
     */
    public CancelTransactionScreenRobot clickOnReturn() {
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.alert_dialog_button_negative), withText("RETURN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());
        return this;
    }

    /**
     * This method is used to verify message on Cancel Transaction
     *
     * @return
     */
    public CancelTransactionScreenRobot verifyMessageOnCancelTransaction(Context context) {
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.alert_dialog_msg), withText(containsString(testData.getStringsFromJson(context, "CancelCheckoutMessage"))), isDisplayed()));
        textView2.check(matches(withText(containsString(testData.getStringsFromJson(context, "CancelCheckoutMessage")))));
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
