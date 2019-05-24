package com.us.masterpass.referenceApp.testcase.story;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;
import com.us.masterpass.referenceApp.component.BaseComponent;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)

public class S701833 {
    private BaseComponent baseComponent;

    @Rule
    public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

    @Before
    public void before() throws IOException {
        baseComponent = BaseComponent.getInstance();

    }
    /**
     * This method is used to validate Cancel and Return to merchant button on Address List Screen
     * @throws Exception
     */

    @Test
    public void A_TC1245364() throws Exception {
        baseComponent.init()
                .addItemToCart()
                .waitTillProgressBarDisappear()
                .tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext())
                .tapOnContinueBtnEnrollScreen()
                .waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext())
                .enterEmailID()
                .enterMobileNo()
                .tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress()
                .cancelCheckoutOnAddressListScreen()
                .clickOnStay()
                .cancelCheckoutOnAddressListScreen()
                .clickOnReturn()
                .isSRCMarkVisible();
    }

    /**
     * This method is used to select address on Address List Screen
     * @throws Exception
     */

    @Test
    public void B_TC1245362() throws Exception {
        baseComponent.init()
                .addItemToCart()
                .waitTillProgressBarDisappear()
                .tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext())
                .tapOnContinueBtnEnrollScreen()
                .waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext())
                .enterEmailID()
                .enterMobileNo()
                .tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress()
                .selectAddressFromList();
    }

    /**
     * This method is used to verify links on T&C, Privacy Notice and Cookie Consent
     * @throws Exception
     */

    @Test
    public void C_TC1245372() throws Exception {
        baseComponent.init()
                .addItemToCart()
                .waitTillProgressBarDisappear()
                .tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext())
                .tapOnContinueBtnEnrollScreen()
                .waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext())
                .enterEmailID()
                .enterMobileNo()
                .tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress()
                .verifyLegalContentLinkOnAddressListScreen();
    }

}
