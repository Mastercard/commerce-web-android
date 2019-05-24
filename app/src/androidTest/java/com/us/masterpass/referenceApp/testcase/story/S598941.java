package com.us.masterpass.referenceApp.testcase.story;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;
import com.us.masterpass.referenceApp.Pojo.Address;
import com.us.masterpass.referenceApp.component.BaseComponent;
import com.us.masterpass.referenceApp.robot.MiscellaneousRobot;
import com.us.masterpass.referenceApp.utils.UiAutomatorUtil;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)

public class S598941 {
    private BaseComponent baseComponent;

    @Rule
    public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

    @Before
    public void before() throws IOException {
        baseComponent = BaseComponent.getInstance();
    }

    /**
     * This testcase is to validate when user taps on continue button on review if should navigate to Remember Me Screen
     * @throws Exception
     */
@Test
public void A_TC1200830() throws Exception {
    baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
    .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
    .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
            .waitTillProgressBarDisappear()
    .tapOnContinueBtnReviewScreen()
            .waitTillProgressBarDisappear()
    .verifyRememberMeScreen();
}

    /**
     * This testcase is to validate Error Message pop-up when WiFi is OFF user taps on continue button on review screen
     * @throws Exception
     */
    @Test
    public void B_TC1201016() throws Exception {

        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
    .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen()
                .waitForAssociateScreen().addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear();
        UiAutomatorUtil.WifiOff(InstrumentationRegistry.getContext());
        baseComponent.tapOnContinueBtnReviewScreen().verifyNetworkErrorMessage();
    UiAutomatorUtil.WifiOn(InstrumentationRegistry.getContext());

}

    /**
     * This testcase is to validate retry checkout when WiFi is on after Error Message pop-up when WiFi is OFF user taps on continue button on review screen
     * @throws Exception
     */
    @Test
    public void C_TC1201040() throws Exception {
        baseComponent.init().addItemToCart().tapOnSRCMark()
        .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen()
        .waitForAssociateScreen()
        .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen();
        UiAutomatorUtil.WifiOff(InstrumentationRegistry.getContext());
        baseComponent.verifyNetworkErrorMessage()
        .acceptPopUpNetworkErrorMessage();
        UiAutomatorUtil.WifiOn(InstrumentationRegistry.getContext());
        Thread.sleep(2000);
        baseComponent.tapOnContinueBtnReviewScreen()
        .verifyRememberMeScreen();

    }

}
