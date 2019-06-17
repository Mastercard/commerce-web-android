package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class CardListScreenRobot {
    TestData testData = new TestData();

    /**
     * This method is used select a card from list of card on Card list screen
     *
     * @return
     */
    public CardListScreenRobot selectCardFromList() {

        ViewInteraction selectCard = onView(allOf(withId(R.id.img_right_arrow)));
        selectCard.perform(click());
        return this;
    }

    /**
     * This method is used verify Card list screen
     *
     * @return
     * @param context
     */
    public CardListScreenRobot verifyCardListScreen(Context context) {

        ViewInteraction cardListScreenHeader = onView(allOf(withId(R.id.display_label)));
        cardListScreenHeader.check(matches(withText(testData.getStringsFromJson(context, "CardListLabel"))));
        return this;
    }

    /**
     * This method is used click on  add card button on Card list screen
     *
     * @return
     */
    public CardListScreenRobot clickOnAddBtnOnCardListScreen(Context context) {

        ViewInteraction addBtn = onView(allOf(withText(testData.getStringsFromJson(context, "AddButton"))));
        addBtn.perform(click());
        return this;
    }

    /**
     * This method is used verify last four digit of card number  on Card list screen
     *
     * @return
     */
    public String verifyLastFourDigitOfCardNumber() {
        MiscellaneousRobot robot = new MiscellaneousRobot();
        //Replace with  the actual id in below line
        String expectedCardNumber = robot.getText(withId(R.id.routingSheet_card_lastFourDigits)).substring(6);
        return expectedCardNumber;
    }

    /**
     * This method is used to verify T&C link  on review screen
     *
     * @return
     */

    public CardListScreenRobot verifyTnCLink(Context context) throws UiObjectNotFoundException {
        closeSoftKeyboard();
        ViewInteraction textView = onView(
                allOf(withId(R.id.textview_terms_and_conditions), withText(testData.getStringsFromJson(context, "legal_content_tnc_text")),
                        isDisplayed()));
        textView.check(matches(withText(testData.getStringsFromJson(context, "legal_content_tnc_text"))));
        return this;
    }

    /**
     * This method is used to verify Privacy Notice link  on review screen
     *
     * @return
     */

    public CardListScreenRobot verifyPPLink(Context context) {
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textview_privacy_policy), withText(testData.getStringsFromJson(context, "legal_content_privacy_text")),
                        isDisplayed()));
        textView2.check(matches(withText(testData.getStringsFromJson(context, "legal_content_privacy_text"))));
        return this;
    }

    /**
     * This method is used to verify Cookie Consent link  on review screen
     *
     * @return
     */

    public CardListScreenRobot verifyCCLink(Context context) {
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textview_cookie_consent), withText(testData.getStringsFromJson(context, "legal_content_cookie_text")),
                        isDisplayed()));
        textView3.check(matches(withText(testData.getStringsFromJson(context, "legal_content_cookie_text"))));
        return this;
    }
}