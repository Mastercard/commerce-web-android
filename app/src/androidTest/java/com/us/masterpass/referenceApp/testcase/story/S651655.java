package com.us.masterpass.referenceApp.testcase.story;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;
import com.us.masterpass.referenceApp.component.BaseComponent;
import com.us.masterpass.referenceApp.utils.UiAutomatorUtil;

import java.io.IOException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class S651655 {
  private BaseComponent baseComponent;

  @Rule
  public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

  @Before
  public void before() throws IOException {
    baseComponent = BaseComponent.getInstance();
  }

  /**
   * This test case is to validate otp screen is displayed and user enter otp
   * @throws Exception
   */
  @Test
  public void A_TC1279819() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark()
            .changeTabOnEnrollCardScreen("returning user")
            .enterReturningUserEmail(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitForOTPScreen()
            .enterOTP()
            .tapOnContinueBtnOTPScreen()
            .waitTillProgressBarDisappear();
  }

  /**
   * This test case is to verify cancel and return to merchant on returning user screen
   *
   * @throws Exception
   */
  @Test
  public void B_TC1279822() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark()
            .changeTabOnEnrollCardScreen("returning user")
            .enterReturningUserEmail(InstrumentationRegistry.getContext())
            .returnToMerchantFromEnrollCardScreen()
            .clickOnStay()
            .returnToMerchantFromEnrollCardScreen()
            .clickOnReturn()
            .isSRCMarkVisible();
  }

  /**
   * This test case is to verify cancel and return to merchant on otp screen
   *
   * @throws Exception
   */
  @Test
  public void C_TC12792819() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark()
            .changeTabOnEnrollCardScreen("returning user")
            .enterReturningUserEmail(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .cancelCheckout()
            .clickOnStay()
            .cancelCheckout()
            .clickOnReturn()
            .isSRCMarkVisible();
  }

  /**
   * This test case it to check continue button remain disabled for invalid email address
   * @throws Exception
   */

  @Test
  public void D_TC1279830() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitTillProgressBarDisappear()
        .tapOnSRCMark()
        .changeTabOnEnrollCardScreen("returning user")
        .enterInvalidEmail(InstrumentationRegistry.getContext())
        .validateContinueBtnIsDisabledOnEnrollScreen();
  }

  /**
   * This test case is to validate email field get clear user's entered email ID when user switch tab
   * @throws Exception
   */
  @Test
  public void E_TC1279907() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitTillProgressBarDisappear()
        .tapOnSRCMark()
        .changeTabOnEnrollCardScreen("returning user")
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .changeTabOnEnrollCardScreen("new user")
        .changeTabOnEnrollCardScreen("returning user")
        .validateEmailFieldIsEmpty();
  }

  /**
   * This test case is to validate invalid otp message on otp screen
   * @throws Exception
   */
  @Test
  public void F_TC1279986() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitTillProgressBarDisappear()
        .tapOnSRCMark()
        .changeTabOnEnrollCardScreen("returning user")
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitTillProgressBarDisappear()
        .verifyInvalidOTPMessage();
  }

  /**
   * This test case is to validate account lock message and user is navigate back to merchant screen
   * @throws Exception
   */
  @Test
  public void G_TC1279987nTC1280006() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitTillProgressBarDisappear()
        .tapOnSRCMark()
        .changeTabOnEnrollCardScreen("returning user")
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitTillProgressBarDisappear()
        .lockAccountThroughWrongOTP()
        .verifyAccountLockMessage()
        .tapOnOK();
  }

  /**
   * This test case is to validate account lock message on email verification screen
   * @throws Exception
   */
  @Test
  public void H_TC1280006() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitTillProgressBarDisappear()
        .tapOnSRCMark()
        .changeTabOnEnrollCardScreen("returning user")
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .lockAccountThroughWrongOTP()
        .tapOnOK()
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitTillProgressBarDisappear()
        .validateAccountLockMessageOnEmailVerificationScreen();
  }

  /**
   * This test case is to validate all legal doc link text and trademark text
   * @throws Exception
   */

  @Test
  public void I_TC1279979() throws Exception {
    baseComponent.init()
        .addItemToCart()
        .waitTillProgressBarDisappear()
        .tapOnSRCMark()
        .changeTabOnEnrollCardScreen("returning user")
        .enterReturningUserEmail(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitTillProgressBarDisappear()
        .verifyLegalContentLinkOnOTPScreen();
    //TODO : Implement code once issue related to same id has been resolved
  }

    /**
     * This test case is to validate network error msg on returning user screen
     *
     * @throws Exception
     */
    @Test
    public void J_TC1279824() throws Exception {
        baseComponent.init()
                .addItemToCart()
                .waitTillProgressBarDisappear()
                .tapOnSRCMark()
                .changeTabOnEnrollCardScreen("returning user")
                .enterReturningUserEmail(InstrumentationRegistry.getContext());
        UiAutomatorUtil.WifiOff(InstrumentationRegistry.getContext());
        baseComponent.tapOnContinueBtnEnrollScreen().verifyNetworkErrorMessage();
        UiAutomatorUtil.WifiOn(InstrumentationRegistry.getContext());
    }

    /**
     * This test case is to validate network error msg on otp screen
     *
     * @throws Exception
     */
    @Test
    public void K_TC1217819() throws Exception {
        baseComponent.init()
                .addItemToCart()
                .waitTillProgressBarDisappear()
                .tapOnSRCMark()
                .changeTabOnEnrollCardScreen("returning user")
                .enterReturningUserEmail(InstrumentationRegistry.getContext())
                .tapOnContinueBtnEnrollScreen()
                .waitForOTPScreen()
                .enterOTP();
        UiAutomatorUtil.WifiOff(InstrumentationRegistry.getContext());
        baseComponent.tapOnContinueBtnOTPScreen().verifyNetworkErrorMessage();
        UiAutomatorUtil.WifiOn(InstrumentationRegistry.getContext());
    }

}
