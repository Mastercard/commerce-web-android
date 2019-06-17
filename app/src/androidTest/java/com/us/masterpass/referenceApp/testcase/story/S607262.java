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
public class S607262 {
  private BaseComponent baseComponent;

  @Rule
  public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

  @Before
  public void before() throws IOException {
    baseComponent = BaseComponent.getInstance();
  }

  /**
   * This method is used to validate Billing as shipping
   * @throws Exception
   */

  @Test
  public void A_TC1199558() throws Exception {
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
            .tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear();
  }

  /**
   * This method is used to verify links on T&C, Privacy Notice and Cookie Consent
   * @throws Exception
   */

  @Test
  public void B_TC1201490() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .verifyLegalContentLinkOnAssociateScreen();
  }

  /**
   * This method is used to validate Mandatory and Optional fields
   * @throws Exception
   */

  @Test
  public void C_TC1199540() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .addAddressWithoutOptionalField(InstrumentationRegistry.getContext())
            .enterEmailID()
            .enterMobileNo()
            .tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear();
  }

  /**
   * This method is used to validate Cancel and Return to merchant button
   * @throws Exception
   */

  @Test
  public void D_TC1200824() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .returnToMerchantOnAssociateScreen()
            .clickOnStay()
            .returnToMerchantOnAssociateScreen()
            .clickOnReturn()
            .isSRCMarkVisible();
  }

  /**
   * This method is used to duplicate email id error on associate screen
   * @throws Exception
   */

  @Test
  public void E_TC1199557() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .addFirstAddress(InstrumentationRegistry.getContext())
            .enterDuplicateEmailID(InstrumentationRegistry.getContext())
            .enterMobileNo()
            .tapOnContinueBtnAssociateScreen()
            .waitTillProgressBarDisappear()
            .validateDuplicateUserErrorMessage();
  }

  /**
   * This method is used to duplicate mobile no. error on associate screen
   * @throws Exception
   */

  @Test
  public void F_TC1199557() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .addFirstAddress(InstrumentationRegistry.getContext())
            .enterEmailID()
            .enterDuplicateMobileNo(InstrumentationRegistry.getContext())
            .tapOnContinueBtnAssociateScreen()
            .waitTillProgressBarDisappear()
            .validateDuplicateUserErrorMessage();
  }

  /**
   * This method is used to validate amount on associate screen
   *
   * @throws Exception
   */

  @Test
  public void H_TC1200821() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .getTotalAmount()
            .tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext())
            .tapOnContinueBtnEnrollScreen()
            .waitTillProgressBarDisappear()
            .validateTotalAmountOnAssociateScreen();
  }

  /**
   * This testcase is to validate Error Message pop-up when WiFi is OFF user taps on continue button
   *
   * @throws Exception
   */
  @Test
  public void I_TC1199553() throws Exception {

    baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
            .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen()
            .waitForAssociateScreen().addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo();
    UiAutomatorUtil.WifiOff(InstrumentationRegistry.getContext());
    baseComponent.tapOnContinueBtnAssociateScreen().waitTillProgressBarDisappear().verifyNetworkErrorMessage();
    UiAutomatorUtil.WifiOn(InstrumentationRegistry.getContext());

  }

    //Todo: TC for suppress shipping option is pending
}