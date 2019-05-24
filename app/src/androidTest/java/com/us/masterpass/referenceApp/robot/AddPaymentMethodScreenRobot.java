package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;

import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class AddPaymentMethodScreenRobot {
    TestData testData = new TestData();

    /**
     * This method is used to verify Add a payment method screen
     *
     * @return
     * @param context
     */
    public AddPaymentMethodScreenRobot verifyAddPaymentMethodScreen(Context context) {

        ViewInteraction AddAPaymentMethodHeader = onView(allOf(withId(R.id.textView_add_payment_method)));
        AddAPaymentMethodHeader.check(matches(withText("Add a payment method")));
        AddAPaymentMethodHeader.check(matches(withText(testData.getStringsFromJson(context, "AddCardLabel"))));
        return this;
    }

    /**
     * This method is used to verify continue button is not enabled on Add a payment method screen
     *
     * @return
     */
    public AddPaymentMethodScreenRobot verifyContinueBtnNotEnabledAddPaymentMethodScreen() {
        ViewInteraction btn = onView(allOf(withId(R.id.button_continue), isDisplayed()));
        btn.check(matches(not(isEnabled())));
        return this;
    }

    /**
     * This method is used to verify continue button enabled on Add a payment method screen
     *
     * @return
     */
    public AddPaymentMethodScreenRobot verifyContinueBtnEnabledAddPaymentMethodScreen() {
        ViewInteraction btn = onView(allOf(withId(R.id.button_continue), isDisplayed()));
        btn.check(matches(isEnabled()));
        return this;
    }

}