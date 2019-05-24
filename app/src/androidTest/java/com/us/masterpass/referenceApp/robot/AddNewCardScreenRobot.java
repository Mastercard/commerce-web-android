package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

public class AddNewCardScreenRobot {
  TestData testData = new TestData();

  /**
   * This method is used to add new card
   */
  public AddNewCardScreenRobot addNewCard() {
    try {
      scrollTillTextDisplay("Add");
    } catch (UiObjectNotFoundException e) {
      e.printStackTrace();
    }

    ViewInteraction addCard = onView(allOf(withId(R.id.add_new_card_layout)));
    addCard.perform(click());
    return this;
  }

  public AddNewCardScreenRobot validateContinueBtnEnabled() {
    ViewInteraction btn = onView(allOf(withId(R.id.button_continue), isDisplayed()));
    btn.check(matches(isEnabled()));
    return this;
  }

  public AddNewCardScreenRobot validateContinueBtnNotEnabled() {
    ViewInteraction btn = onView(allOf(withId(R.id.button_continue), isDisplayed()));
    btn.check(matches(not(isEnabled())));
    return this;
  }

  /**
   * This method is used to scroll till a certain Text is within display
   *
   * @param text
   * @throws UiObjectNotFoundException
   */
  public static void scrollTillTextDisplay(String text) throws UiObjectNotFoundException {
    UiScrollable scrollDown = new UiScrollable(new UiSelector().scrollable(true));
    scrollDown.setAsVerticalList();
    UiObject checkText = scrollDown.getChildByText(new UiSelector().text(text), text);
  }

}