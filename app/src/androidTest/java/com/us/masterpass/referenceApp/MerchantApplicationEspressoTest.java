package com.us.masterpass.referenceApp;/*
    package com.us.masterpass.referenceApp;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.NoMatchingRootException;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Random;

import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;
import tools.fastlane.screengrab.locale.LocaleTestRule;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.us.masterpass.referenceApp.ListViewItemCount.count;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

*/
/**
 * Created by akhildixit on 11/1/17.
 *//*

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MerchantApplicationEspressoTest {
    private boolean isSuppress;
    private int addItems = 5;
    private int cartViewItems = 2;
    private int dsrpItems = 2;
    private int cards = 5;
    private int languageItems = 2;
   private  int currencyItems = 4;
//    @ClassRule
//    public static final LocaleTestRule localeTestRule = new LocaleTestRule();
    @Rule
    public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<ItemsActivity>(ItemsActivity.class);
    private static UiDevice device;

    @Before
    public void setActivity() throws Exception {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Thread.sleep(2000);
    }
//    @BeforeClass
//    public static void beforeAll() {
//        Screengrab.setDefaultScreenshotStrategy(new UiAutomatorScreenshotStrategy());
//    }
    @Test
        public void testAcartActivity()throws Exception {
        onView(withId(R.id.toolbar_checkout)).check(matches(isDisplayed())).perform(click());
//       Screengrab.screenshot("After_checkout_click");
         Thread.sleep(2000);
            onView((withText("Ok"))).check(matches(isDisplayed()));
            Thread.sleep(2000);
            onView(withText("Ok")).perform(click());
            for (int i = 0; i < addItems; i++) {
                onData(anything()).
                        inAdapterView(withId(R.id.items_list_grid)).
                        onChildView(withId(R.id.item_cell_add)).
                        atPosition(new Random().nextInt(i + 1)).
                        check(matches(isDisplayed()))
                        .perform(click());
            }
            Thread.sleep(2000);
            onView(withId(R.id.toolbar_checkout)).check(matches(isDisplayed())).perform(click());
        // Screengrab.screenshot("After_toolbar_click");
            Thread.sleep(2000);
            ListViewItemCount.getCountFromListView(R.id.items_list_view);
            for (int i = 0; i < count; i++) {

                onData(anything()).
                        inAdapterView(withId(R.id.items_list_view)).
                        onChildView(withId(R.id.item_cell_remove_all)).
                        atPosition(0).
                        check(matches(isDisplayed())).
                        perform(click());
                Thread.sleep(2000);

            }
            for (int i = 0; i < addItems; i++) {
                onData(anything()).
                        inAdapterView(withId(R.id.items_list_grid)).
                        onChildView(withId(R.id.item_cell_add)).
                        atPosition(new Random().nextInt(i + 1)).
                        check(matches(isDisplayed())).
                        perform(click());
                Thread.sleep(2000);
            }

            onView(withId(R.id.toolbar_checkout)).perform(click());

            for (int i = 0; i < cartViewItems; i++) {
                onData(anything()).
                        inAdapterView(withId(R.id.items_list_view)).
                        onChildView(withId(R.id.item_cell_remove)).
                        atPosition(new Random().nextInt(i + 1)).
                        perform(click());
                Thread.sleep(2000);
            }
            Thread.sleep(2000);
            for (int i = 0; i < cartViewItems; i++) {
                onData(anything()).
                        inAdapterView(withId(R.id.items_list_view)).
                        onChildView(withId(R.id.item_cell_add)).
                        atPosition(new Random().nextInt(i + 1)).
                        check(matches(isDisplayed())).
                        perform(click());
                Thread.sleep(2000);
            }
            ListViewItemCount.getCountFromListView(R.id.items_list_view);
            for (int i = 0; i < count; i++) {
                onData(anything()).
                        inAdapterView(withId(R.id.items_list_view)).
                        onChildView(withId(R.id.item_cell_remove_all)).
                        atPosition(0).
                        check(matches(isDisplayed())).
                        perform(click());
                Thread.sleep(2000);
            }
        }
@Test
    public void testBsettingsActivity() throws Exception {
        onView(withId(R.id.toolbar_settings)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.menu_settings)).check(matches(isDisplayed())).perform(click());
        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.settings_list)).
                onChildView(withId(R.id.settings_cell_name)).
                atPosition(0).
                check(matches(isDisplayed())).
                perform(click());
        for (int i = 0; i < cards; i++) {
            onData(anything()).
                    inAdapterView(withId(R.id.settings_list)).
                    onChildView(withId(R.id.settings_check)).
                    atPosition(new Random().
                            nextInt(i + 1)).check(matches(isDisplayed())).perform(click());
            Thread.sleep(2000);
        }
        onView(isRoot()).perform(pressBack());
        Thread.sleep(2000);
        onData(anything()).
                inAdapterView(withId(R.id.settings_list)).
                onChildView(withId(R.id.settings_cell_name)).
                atPosition(1).
                perform(click()).
                check(matches(isDisplayed()));
        //for(int i = 0; i<languageItems; i++) {
        onData(anything()).
                inAdapterView(withId(R.id.settings_list)).
                onChildView(withId(R.id.settings_check)).
                atPosition(0).
                perform(click()).check(matches(isDisplayed()));
        Thread.sleep(2000);
        // }
        onView(isRoot()).perform(pressBack());
        Thread.sleep(2000);
        onData(anything()).
                inAdapterView(withId(R.id.settings_list)).
                onChildView(withId(R.id.settings_cell_name)).
                atPosition(2).
                check(matches(isDisplayed())).
                perform(click());
        // for(int i=0;i<currencyItems;i++){
        onData(anything()).
                inAdapterView(withId(R.id.settings_list)).
                onChildView(withId(R.id.settings_check)).
                atPosition(0).
                perform(click()).check(matches(isDisplayed()));
        Thread.sleep(2000);
        // }
        onView(isRoot()).perform(pressBack());
        onData(anything()).
                inAdapterView(withId(R.id.settings_list)).
                onChildView(withId(R.id.settings_switch)).
                atPosition(4).perform(ToogleCheck.setChecked(isSuppress));
        onData(anything()).
                inAdapterView(withId(R.id.settings_list)).
                onChildView(withId(R.id.settings_switch)).
                atPosition(5).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onData(anything()).
                inAdapterView(withId(R.id.settings_list)).
                onChildView(withId(R.id.settings_cell_name)).
                atPosition(6).
                check(matches(isDisplayed())).
                perform(click());
        for (int i = 0; i < dsrpItems; i++) {
            onData(anything()).
                    inAdapterView(withId(R.id.settings_list)).
                    onChildView(withId(R.id.settings_check)).
                    atPosition(new Random().
                            nextInt(i + 1)).
                    perform(click()).check(matches(isDisplayed()));
            Thread.sleep(2000);
        }
        onView(isRoot()).perform(pressBack());
        Thread.sleep(2000);
        onView(isRoot()).perform(pressBack());
        Thread.sleep(2000);
    }
@Test
    public void testCAppWithoutSupress() throws Exception {
        isSuppress = false;
        testBsettingsActivity();
        onView(withId(R.id.toolbar_checkout)).check(matches(isDisplayed())).perform(click());
        Thread.sleep(2000);
        onView((withText("Ok"))).check(matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withText("Ok")).perform(click());
        for (int i = 0; i < addItems; i++) {
            onData(anything()).
                    inAdapterView(withId(R.id.items_list_grid)).
                    onChildView(withId(R.id.item_cell_add)).
                    atPosition(new Random().nextInt(i + 1)).
                    check(matches(isDisplayed()))
                    .perform(click());
        }
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_checkout)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.ll_bottom_button)).perform(click());
        Thread.sleep(15000);
        UiObject parentView = new UiObject(new UiSelector().className("android.view.View").index(2));
        UiObject eachItem = parentView.getChild(new UiSelector().
                className("android.view.View").
                index(0)).
                getChild(new UiSelector().className("android.widget.EditText").index(4));
        eachItem.waitForExists(2000);
        eachItem.legacySetText("moofwd123");
        device.pressBack();
        Thread.sleep(2000);
        UiObject signinButton = parentView.getChild(new UiSelector().
                className("android.widget.Button").
                description("Sign In").
                index(2));
        signinButton.waitForExists(2000);
        signinButton.clickAndWaitForNewWindow();
        UiScrollable view = new UiScrollable(new UiSelector().scrollable(true));
        view.scrollForward();
        Thread.sleep(2000);
        UiObject enableContinueButton = new UiObject(new UiSelector().
                className("android.widget.Button").
                description("Enable & Continue"));
        enableContinueButton.waitForExists(2000);
        enableContinueButton.clickAndWaitForNewWindow();
        Thread.sleep(8000);
        onView(withId(R.id.cart_confirm_btn)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withText("SHIP TO")).check(matches(isDisplayed()));
        onView(withId(R.id.cart_complete_btn)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        for (int i = 0; i < addItems; i++) {
            onData(anything()).
                    inAdapterView(withId(R.id.items_list_grid)).
                    onChildView(withId(R.id.item_cell_add)).
                    atPosition(new Random().nextInt(i + 1)).
                    check(matches(isDisplayed()))
                    .perform(click());
        }
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_checkout)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.ll_bottom_button)).perform(click());
        Thread.sleep(15000);
        parentView = new UiObject(new UiSelector().className("android.view.View").index(2));
        eachItem = parentView.getChild(new UiSelector().
                className("android.view.View").
                index(0)).
                getChild(new UiSelector().className("android.widget.EditText").index(4));
        eachItem.waitForExists(2000);
        eachItem.legacySetText("moofwd123");
        device.pressBack();
        Thread.sleep(2000);
        signinButton = parentView.getChild(new UiSelector().
                className("android.widget.Button").
                description("Sign In").
                index(2));
        signinButton.waitForExists(2000);
        signinButton.clickAndWaitForNewWindow();
        Thread.sleep(2000);
        UiObject cancelButton = new UiObject(new UiSelector().
                className("android.view.View").
                index(0)).
                getChild(new UiSelector().
                        className("android.widget.Button").
                        index(1));
        cancelButton.waitForExists(2000);
        cancelButton.clickAndWaitForNewWindow();
        Thread.sleep(2000);
        onView(withId(R.id.ll_bottom_button)).perform(click());
        Thread.sleep(15000);
        parentView = new UiObject(new UiSelector().className("android.view.View").index(2));
        eachItem = parentView.getChild(new UiSelector().
                className("android.view.View").
                index(0)).
                getChild(new UiSelector().className("android.widget.EditText").index(4));
        eachItem.waitForExists(2000);
        eachItem.legacySetText("moofwd123");
        device.pressBack();
        Thread.sleep(2000);
        signinButton = parentView.getChild(new UiSelector().
                className("android.widget.Button").
                description("Sign In").
                index(2));
        signinButton.waitForExists(2000);
        signinButton.clickAndWaitForNewWindow();
        Thread.sleep(2000);
        view = new UiScrollable(new UiSelector().scrollable(true));
        view.scrollForward();
        Thread.sleep(2000);
        enableContinueButton = new UiObject(new UiSelector().
                className("android.widget.Button").
                description("Enable & Continue"));
        enableContinueButton.waitForExists(2000);
        enableContinueButton.clickAndWaitForNewWindow();
        Thread.sleep(10000);
        onView(withId(R.id.cart_cancel_btn)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_checkout)).perform(click());
        Thread.sleep(2000);
        ListViewItemCount.getCountFromListView(R.id.items_list_view);
        for (int i = 0; i < count; i++) {
            onData(anything()).
                    inAdapterView(withId(R.id.items_list_view)).
                    onChildView(withId(R.id.item_cell_remove_all)).
                    atPosition(0).
                    check(matches(isDisplayed())).
                    perform(click());
            Thread.sleep(2000);

        }
    }
@Test
    public void testDAppWithSuppress() throws Exception {
        isSuppress=true;
        testBsettingsActivity();
        onView(withId(R.id.toolbar_checkout)).check(matches(isDisplayed())).perform(click());
        Thread.sleep(2000);
        onView((withText("Ok"))).check(matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withText("Ok")).perform(click());
        for (int i = 0; i < addItems; i++) {
            onData(anything()).
                    inAdapterView(withId(R.id.items_list_grid)).
                    onChildView(withId(R.id.item_cell_add)).
                    atPosition(new Random().nextInt(i + 1)).
                    check(matches(isDisplayed()))
                    .perform(click());
        }
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_checkout)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.ll_bottom_button)).perform(click());
        Thread.sleep(15000);
        UiObject parentView = new UiObject(new UiSelector().className("android.view.View").index(2));
        UiObject eachItem = parentView.getChild(new UiSelector().
                className("android.view.View").
                index(0)).
                getChild(new UiSelector().className("android.widget.EditText").index(4));
        eachItem.waitForExists(2000);
        eachItem.legacySetText("moofwd123");
        device.pressBack();
        Thread.sleep(2000);
        UiObject signinButton = parentView.getChild(new UiSelector().
                className("android.widget.Button").
                description("Sign In").
                index(2));
        signinButton.waitForExists(2000);
        signinButton.clickAndWaitForNewWindow();
        Thread.sleep(2000);
        UiObject enableContinueButton = new UiObject(new UiSelector().
                className("android.widget.Button").
                description("Enable & Continue"));
        enableContinueButton.waitForExists(2000);
        enableContinueButton.clickAndWaitForNewWindow();
        Thread.sleep(10000);
        onView(withText("SHIP TO")).check(matches(not(isDisplayed())));
        onView(withId(R.id.cart_confirm_btn)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withText("SHIP TO")).check(matches(not(isDisplayed())));
        onView(withId(R.id.cart_complete_btn)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        for (int i = 0; i < addItems; i++) {
            onData(anything()).
                    inAdapterView(withId(R.id.items_list_grid)).
                    onChildView(withId(R.id.item_cell_add)).
                    atPosition(new Random().nextInt(i + 1)).
                    check(matches(isDisplayed()))
                    .perform(click());
        }
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_checkout)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.ll_bottom_button)).perform(click());
        Thread.sleep(15000);
        parentView = new UiObject(new UiSelector().className("android.view.View").index(2));
        eachItem = parentView.getChild(new UiSelector().
                className("android.view.View").
                index(0)).
                getChild(new UiSelector().className("android.widget.EditText").index(4));
        eachItem.waitForExists(2000);
        eachItem.legacySetText("moofwd123");
        device.pressBack();
        Thread.sleep(2000);
        signinButton = parentView.getChild(new UiSelector().
                className("android.widget.Button").
                description("Sign In").
                index(2));
        signinButton.waitForExists(2000);
        signinButton.clickAndWaitForNewWindow();
        Thread.sleep(2000);
        UiObject cancelButton = new UiObject(new UiSelector().
                className("android.view.View").
                index(0)).
                getChild(new UiSelector().
                        className("android.widget.Button").
                        index(1));
        cancelButton.waitForExists(2000);
        cancelButton.clickAndWaitForNewWindow();
        Thread.sleep(2000);
        onView(withId(R.id.ll_bottom_button)).perform(click());
        Thread.sleep(15000);
        parentView = new UiObject(new UiSelector().className("android.view.View").index(2));
        eachItem = parentView.getChild(new UiSelector().
                className("android.view.View").
                index(0)).
                getChild(new UiSelector().className("android.widget.EditText").index(4));
        eachItem.waitForExists(2000);
        eachItem.legacySetText("moofwd123");
        device.pressBack();
        Thread.sleep(2000);
        signinButton = parentView.getChild(new UiSelector().
                className("android.widget.Button").
                description("Sign In").
                index(2));
        signinButton.waitForExists(2000);
        signinButton.clickAndWaitForNewWindow();
        Thread.sleep(2000);
        enableContinueButton = new UiObject(new UiSelector().
                className("android.widget.Button").
                description("Enable & Continue"));
        enableContinueButton.waitForExists(2000);
        enableContinueButton.clickAndWaitForNewWindow();
        Thread.sleep(10000);
        onView(withText("SHIP TO")).check(matches(not(isDisplayed())));
        onView(withId(R.id.cart_cancel_btn)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_checkout)).perform(click());
        Thread.sleep(2000);
    Thread.sleep(2000);
    ListViewItemCount.getCountFromListView(R.id.items_list_view);
    for (int i = 0; i < count; i++) {
        onData(anything()).
                inAdapterView(withId(R.id.items_list_view)).
                onChildView(withId(R.id.item_cell_remove_all)).
                atPosition(0).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
    }
}
    @Test
    public void testELoginActivity() throws Exception {
        onView(withId(R.id.toolbar_settings)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.menu_login)).check(matches(isDisplayed())).perform(click());
        Thread.sleep(2000);
        if(LogInCheck.isLoggedIn(onView(withText("Cancel")))){
            onView(withText("Cancel")).perform(click());
        } else {
            onView(withId(R.id.username_edit_text)).check(matches(isDisplayed())).perform(clearText(), typeText("jsmith"));
            Thread.sleep(2000);
            onView(withId(R.id.password_edit_text)).check(matches(isDisplayed())).perform(clearText(), typeText("password"), ViewActions.closeSoftKeyboard());
            Thread.sleep(2000);
            onView(withId(R.id.login_login)).check(matches(isDisplayed())).perform(click());
            Thread.sleep(2000);
        }
    }
    @Test
    public void testFLogoutActivity() throws Exception{
        onView(withId(R.id.toolbar_settings)).
                check(matches(isDisplayed())).
                perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.menu_login)).check(matches(isDisplayed())).perform(click());
        Thread.sleep(2000);
        onView(withText("Ok")).check(matches(isDisplayed())).perform(click());
        onView(isRoot()).perform(pressBack());
    }
}



*/
