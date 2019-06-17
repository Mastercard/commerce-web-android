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
public class S733113 {
    private BaseComponent baseComponent;

    @Rule
    public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

    @Before
    public void before() throws IOException {
        baseComponent = BaseComponent.getInstance();

    }

    /**
     * This Testcase is to verify user navigates to  card list Screen
     */

    @Test
    public void A_TC1278644() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().validateCardListScreen(InstrumentationRegistry.getContext());

    }

    /**
     * This Testcase is to verify user navigates to review screen when select any card on card list Screen
     */
    @Test
    public void B_TC1278875() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().selectCardOnCardListScreen().waitForReviewUserScreen();

    }

    /**
     * This Testcase is to verify last four digit of CardNumber which is enrolled for checkout on card list Screen
     */
    @Test
    public void C_TC1278876() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().validateLastFourDigitOfCardNumber(InstrumentationRegistry.getContext());

    }

    /**
     * This Testcase is to verify all the links i.e Add button, cancel and return , T&c,Privacy policy and cookie consent on card list Screen
     */
    @Test
    public void D_TC1278878() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear()
                .clickNavigationIconOnCard().verifyLegalDocs();

    }

}