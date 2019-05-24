package com.us.masterpass.referenceApp.testcase.story;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;
import com.us.masterpass.referenceApp.component.BaseComponent;
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
public class S661037 {
  private BaseComponent baseComponent;

  @Rule
  public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

  @Before
  public void before() throws IOException {
    baseComponent = BaseComponent.getInstance();
  }



  /**
   * This method is used to validate Cancel and Return to merchant button
   * @throws Exception
   */

  @Test
  public void A_TC1199527() throws Exception {
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
            .returnToMerchantOnReviewScreen()
            .clickOnStay()
            .returnToMerchantOnReviewScreen()
            .clickOnReturn()
            .isSRCMarkVisible();
  }

  /**
   * This method is used to validate email ID and Phone no.
   * @throws Exception
   */

  @Test
  public void B_TC1335570() throws Exception {
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
            .reviewEmailAndMobile();
  }

  /**
   * This method is used to validate amount.
   * @throws Exception
   */

  @Test
  public void C_TC1335601() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .getTotalAmount()
            .tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .addFirstAddress(InstrumentationRegistry.getContext())
            .enterEmailID()
            .enterMobileNo()
            .tapOnContinueBtnAssociateScreen()
            .waitTillProgressBarDisappear()
            .reviewAmountOnReviewScreen();
  }

  /**
   * This method is used to verify links on T&C, Privacy Notice and Cookie Consent
   * @throws Exception
   */

  @Test
  public void D_TC1187264() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .getTotalAmount()
            .tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .addFirstAddress(InstrumentationRegistry.getContext())
            .enterEmailID()
            .enterMobileNo()
            .tapOnContinueBtnAssociateScreen()
            .waitTillProgressBarDisappear()
            .verifyLegalContentLinkOnScreen();
  }

  /**
   * This method is used to validate shipping address on Review screen.
   * @throws Exception
   */

  @Test
  public void E_TC1187020() throws Exception {
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
            .reviewShippingAddressOnReviewScreen(InstrumentationRegistry.getContext());
  }

  /**
   * This method is used to validate network error message
   *
   * @throws Exception
   */

  // @Test
  public void F_TC1203856() throws Exception {
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
            .waitTillProgressBarDisappear();
    UiAutomatorUtil.WifiOff(InstrumentationRegistry.getContext());
    baseComponent.tapOnContinueBtnReviewScreen().verifyNetworkErrorMessage();
    UiAutomatorUtil.WifiOn(InstrumentationRegistry.getContext());

  }

  //Todo: TC for suppress shipping option is pending
}
