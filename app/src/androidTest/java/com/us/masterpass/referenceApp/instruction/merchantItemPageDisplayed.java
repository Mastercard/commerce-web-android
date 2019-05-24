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

public class merchantItemPageDisplayed extends Instruction {

  @Override
  public String getDescription() {
    return "Expecting Merchant items page to be displayed";
  }

  @Override
  public boolean checkCondition() {
    try {
      ViewInteraction appCompatImageView = onView(
          allOf(withId(R.id.items_list_grid),
              isDisplayed()));
      appCompatImageView.check(matches(isDisplayed()));
      return true;
    } catch (NoMatchingViewException e) {
      return false;
    }
  }
}
