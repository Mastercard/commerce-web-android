package com.us.masterpass.referenceApp.instruction;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;

import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.Instruction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class confirmOrderScreenDisplayed extends Instruction{
    /**
     * This method is used to wait till Confirm Order screen displayed
     * @return
     */
    @Override public String getDescription() {
        return "Expecting Confirm Order screen";
    }

    public boolean checkCondition() {
        try {
            ViewInteraction editText = onView(allOf(withId(R.id.cart_confirm_btn), isDisplayed()));
            editText.check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException e) {
            return false;
        }
    }
}
