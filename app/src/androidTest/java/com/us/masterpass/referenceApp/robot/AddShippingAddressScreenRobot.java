package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;

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

public class AddShippingAddressScreenRobot {
    TestData testData = new TestData();
    /**
     * This method is used verify Save button enabled on Add a Shipping Address Screen
     * @return
     */
    public AddShippingAddressScreenRobot verifySaveBtnEnabled(){
        ViewInteraction btn = onView(allOf(withId(R.id.shipping_address_save_button), isDisplayed()));
        btn.check(matches(isEnabled()));
        return this;

    }

    /**
     * This method is used verify Save button enabled on Add a Shipping Address Screen
     *
     * @return
     */
    public AddShippingAddressScreenRobot clickSaveBtn() {
        ViewInteraction btn = onView(allOf(withId(R.id.shipping_address_save_button), isDisplayed()));
        btn.perform(click());
        return this;

    }
    /**
     * This method is used verify Save button  not enabled on Add a Shipping Address Screen
     * @return
     */
    public AddShippingAddressScreenRobot verifySaveBtnNotEnabled(){
        ViewInteraction btn = onView(allOf(withId(R.id.shipping_address_save_button), isDisplayed()));
        btn.check(matches(not(isEnabled())));
        return this;

    }
    /**
     * This method is used verify user navigates to Add a Shipping Address Screen
     * @return
     */
    public AddShippingAddressScreenRobot verifyAddShippingAddressScreen(Context context){

        ViewInteraction lbl = onView(allOf(withId(R.id.textview_title), isDisplayed()));
        lbl.check(matches(withText(testData.getStringsFromJson(context,"AddShippingAddressHeader"))));
        return this;

    }
    /**
     * This method is used verify all links on Add a Shipping Address Screen
     * @return
     */
    public AddShippingAddressScreenRobot verifyAllLinks(Context context) {

//        ViewInteraction link = onView(allOf(withId(R.id.payment_screen_cancel_link)));
//        link.check(matches(withText(containsString(testData.getStringsFromJson(context,"CancelLink")))));

        ViewInteraction text1 = onView(allOf(withId(R.id.textview_terms_and_conditions)));
        text1.check(matches(withText(testData.getStringsFromJson(context,"legal_content_tnc_text"))));

        ViewInteraction text2 = onView(allOf(withId(R.id.textview_privacy_policy)));
        text2.check(matches(withText(testData.getStringsFromJson(context,"legal_content_privacy_text"))));

        ViewInteraction text3 = onView(allOf(withId(R.id.textview_cookie_consent)));
        text3.check(matches(withText(testData.getStringsFromJson(context,"legal_content_cookie_text"))));

        ViewInteraction text4 = onView(allOf(withText(testData.getStringsFromJson(context,"CopyRightText"))));
        text4.check(matches(isDisplayed()));
        return  this;
    }

    /**
     * This method is used click on cancel and return to merchant on Add a Shipping Address Screen
     *
     * @return
     */
    public AddShippingAddressScreenRobot clickOnCancel() {
//        ViewInteraction textView = onView(
//                allOf(withId(R.id.payment_screen_cancel_link), isDisplayed()));
//        textView.perform(click());
        return this;

    }
}
