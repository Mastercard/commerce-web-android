package com.us.masterpass.referenceApp.robot;

import android.content.Context;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.uiautomator.UiDevice;
import android.view.View;
import android.widget.TextView;

import com.us.masterpass.merchantapp.R;
import com.us.masterpass.referenceApp.utils.TestData;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.allOf;

/**
 *
 */

public class MiscellaneousRobot {
    public MiscellaneousRobot wakeUp() {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try {
            if (!uiDevice.isScreenOn()) {
                uiDevice.wakeUp();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return this;
    }

    TestData testData = new TestData();

    public MiscellaneousRobot closeKeyboard() {
        closeSoftKeyboard();
        return this;
    }

    /**
     * This method is used to validate network error
     *
     * @param context
     * @return
     */

    public MiscellaneousRobot validateNetworkError(Context context) {
        //TODO remove blank error message with correct message once it finilized
        ViewInteraction message = onView(
                allOf(withId(android.R.id.message),
                        isDisplayed()));
        message.check(matches(withText(containsString(testData.getStringsFromJson(context, "network_error_message")))));
        return this;
    }
    /**
     * This method is used tap on POp-up Message Button due to network error
     *
     * @param context
     * @return
     */

    public MiscellaneousRobot tapOnNetworkErrorPopUpBtn(Context context) {

        ViewInteraction message = onView(
                allOf(withId(android.R.id.button1),
                        isDisplayed()));
        message.perform(click());
        return this;
    }
    /**
     * This method is used verify trasition screen lable text
     * @return
     */
    public MiscellaneousRobot validateTranisitionScreenMesg() {
        ViewInteraction traditionScreenMeg = onView(withId(R.id.textView9));
        traditionScreenMeg.check(matches(isDisplayed()));
        return this;
    }
    /**
     * This method is used verify confirm Button in confirm Screen
     * @return
     */
    public MiscellaneousRobot validateCartConfirmScreen() {
        ViewInteraction confirmScreenBtn = onView(withId(R.id.cart_confirm_btn));
        confirmScreenBtn.check(matches(isDisplayed()));
        return this;
    }


    public String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }
}
