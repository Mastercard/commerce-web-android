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
public class S700173 {
    private BaseComponent baseComponent;

    @Rule
    public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

    @Before
    public void before() throws IOException {
        baseComponent = BaseComponent.getInstance();

    }

    /**
     * This Testcase is to verify user navigates to  Add a Payment Method  Screen
     */

    @Test
    public void A_TC1249708() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().clickOnAddBtnOnCarListScreen(InstrumentationRegistry.getContext()).validateAddPaymentMethodScreen(InstrumentationRegistry.getContext());
    }

    /**
     * This Testcase is to verify continue button should  enabled when user full all the field on  Add a Payment Method  Screen
     */
    @Test
    public void B_TC1249710() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().clickOnAddBtnOnCarListScreen(InstrumentationRegistry.getContext()).addMastercard(InstrumentationRegistry.getContext()).validateContinueBtnEnabledAddPaymentMethodScreen();
    }

    /**
     * This Testcase is to verify continue button should not enabled when user full all the field except cardholder name on  Add a Payment Method  Screen
     */
    @Test
    public void C_TC1249712() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().clickOnAddBtnOnCarListScreen(InstrumentationRegistry.getContext()).addMastercardExceptSelectedField(InstrumentationRegistry.getContext(), "cardHolderName").validateContinueBtnNotEnabledAddPaymentMethodScreen();
    }

    /**
     * This Testcase is to verify continue button should not enabled when user full all the field except card number on  Add a Payment Method  Screen
     */
    @Test
    public void D_TC1273594() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().clickOnAddBtnOnCarListScreen(InstrumentationRegistry.getContext()).addMastercardExceptSelectedField(InstrumentationRegistry.getContext(), "cardNumber").validateContinueBtnNotEnabledAddPaymentMethodScreen();
    }

    /**
     * This Testcase is to verify continue button should not enabled when user full all the field except expiry on  Add a Payment Method  Screen
     */
    @Test
    public void E_TC1243610() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().clickOnAddBtnOnCarListScreen(InstrumentationRegistry.getContext()).addMastercardExceptSelectedField(InstrumentationRegistry.getContext(), "expiry").validateContinueBtnNotEnabledAddPaymentMethodScreen();
    }

    /**
     * This Testcase is to verify continue button should not enabled when user full all the field except cvc on  Add a Payment Method  Screen
     */
    @Test
    public void F_TC1243611() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().clickOnAddBtnOnCarListScreen(InstrumentationRegistry.getContext()).addMastercardExceptSelectedField(InstrumentationRegistry.getContext(), "cvc").validateContinueBtnNotEnabledAddPaymentMethodScreen();
    }

    /**
     * This Testcase is to verify all the legal(Terms & Condition, Privacy & Cookie Consent) docs on Add a payment method screen
     */
    @Test
    public void G_TC1283149() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().clickOnAddBtnOnCarListScreen(InstrumentationRegistry.getContext()).verifyLegalDocs();
    }
}