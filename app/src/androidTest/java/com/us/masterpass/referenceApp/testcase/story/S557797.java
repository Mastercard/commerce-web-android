package com.us.masterpass.referenceApp.testcase.story;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;
import com.us.masterpass.referenceApp.component.BaseComponent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class S557797 {

    private BaseComponent baseComponent;

    @Rule
    public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

    @Before
    public void before() throws IOException {
        baseComponent = BaseComponent.getInstance();

    }
    @Test
    /*
     * This testcase is to verify confirm  order screen
     * @throws Exception
     */

    public void TC1201555() throws  Exception{
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
        .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen()
                .waitTillProgressBarDisappear()
        .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
        .tapOnContinueBtnReviewScreen()
                .waitTillProgressBarDisappear()
        .tapNotNowBtnOnRememberMeScreen()
        .verifyConfirmOrderScreen();
        
    }
}
