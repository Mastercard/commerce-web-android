package com.us.masterpass.referenceApp.testcase.story;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;
import com.us.masterpass.referenceApp.component.BaseComponent;
import java.io.IOException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class S706177 {
  private BaseComponent baseComponent;

  @Rule
  public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

  @Before
  public void before() throws IOException {
    baseComponent = BaseComponent.getInstance();
  }

  /**
   * This Testcase is to add new card on new user flow.
   */
  @Test
  public void A_TC1252039() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitForSRCMark()
        .tapOnSRCMark()
        .waitTillProgressBarDisappear()
        .addMastercard(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitTillProgressBarDisappear()
        .addFirstAddress(InstrumentationRegistry.getContext())
        .enterEmailID()
        .enterMobileNo()
        .tapOnContinueBtnAssociateScreen()
        .waitTillProgressBarDisappear()
        .clickNavigationIconOnCard()
        .selectNewCard()
        .addNewMastercard(InstrumentationRegistry.getContext());
  }

  /**
   * This Testcase is to add new card on recognized flow.
   */
  @Test
  public void B_TC1252039() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitForSRCMark()
        .tapOnSRCMark()
        .waitTillProgressBarDisappear()
        .changeTabOnEnrollCardScreen("returning user")
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitForOTPScreen()
        .enterOTP()
        .tapOnContinueBtnOTPScreen()
        .waitTillProgressBarDisappear()
        .selectNewCard()
        .addNewMastercard(InstrumentationRegistry.getContext());
  }

  /**
   * This Testcase is verify error message when existing card is added again.
   */
  @Test
  public void C_TC1254133() throws Exception {
    //TODO: Add error verification logic once backend API throws error for adding existing card.
    baseComponent.init()
        .addItemToCart()
        .waitForSRCMark()
        .tapOnSRCMark()
        .waitTillProgressBarDisappear()
        .changeTabOnEnrollCardScreen("returning user")
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitForOTPScreen()
        .enterOTP()
        .tapOnContinueBtnOTPScreen()
        .waitTillProgressBarDisappear()
        .selectNewCard()
        .addMastercard(InstrumentationRegistry.getContext());
  }

  /**
   * This Testcase is verify button enabled/disabled based on Add card fields validation.
   */
  @Test
  public void D_TC1254133() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitForSRCMark()
        .tapOnSRCMark()
        .waitTillProgressBarDisappear()
        .changeTabOnEnrollCardScreen("returning user")
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitForOTPScreen()
        .enterOTP()
        .tapOnContinueBtnOTPScreen()
        .waitTillProgressBarDisappear()
        .selectNewCard()
        .addNewMastercard(InstrumentationRegistry.getContext())
        .validateContinueBtnEnabledAddNewCardScreen()
        .clearMastercardExceptSelectedField(InstrumentationRegistry.getContext(), "cardHolderName")
        .validateContinueBtnNotEnabledAddNewCardScreen()
        .addMastercardForSelectedField(InstrumentationRegistry.getContext(), "cardHolderName")
        .validateContinueBtnEnabledAddNewCardScreen()
        .clearMastercardExceptSelectedField(InstrumentationRegistry.getContext(), "cardNumber")
        .validateContinueBtnNotEnabledAddNewCardScreen()
        .addMastercardForSelectedField(InstrumentationRegistry.getContext(), "cardNumber")
        .validateContinueBtnEnabledAddNewCardScreen()
        .clearMastercardExceptSelectedField(InstrumentationRegistry.getContext(), "expiry")
        .validateContinueBtnNotEnabledAddNewCardScreen()
        .addMastercardForSelectedField(InstrumentationRegistry.getContext(), "expiry")
        .validateContinueBtnEnabledAddNewCardScreen();
  }
}