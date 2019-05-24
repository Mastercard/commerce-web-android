package com.us.masterpass.referenceApp.instruction;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.Instruction;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class waitTillProgressBarGetDisappear extends Instruction{
  @Override public String getDescription() {

    return "Progress bar is still displaying";
  }

  @Override public boolean checkCondition() {
    try{
      onView(allOf(withClassName(is("android.widget.ProgressBar")))).check(matches((isEnabled())));
      return false;
    } catch (NoMatchingViewException e){
    return true;
    }
  }
}
