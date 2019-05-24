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
public class S587675 {
  private BaseComponent baseComponent;

  @Rule
  public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

  @Before
  public void before() throws IOException {
    baseComponent = BaseComponent.getInstance();
  }

  /**
   * This testcase is to validate if user returns back to merchant app on click of return to merchant button
   * @throws Exception
   */

  @Test
  public void A_TC1184033() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark().waitTillProgressBarDisappear()
            .returnToMerchantFromEnrollCardScreen()
            .clickOnStay()
            .returnToMerchantFromEnrollCardScreen()
            .clickOnReturn()
            .isSRCMarkVisible();
  }

  /**
   * This method is used to validate server error message if user add FPAN card
   * @throws Exception
   */

  @Test
  public void B_TC1184052() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark().waitTillProgressBarDisappear().validateFPANErrorMessage();

  }

  /**
   * This method is used to validate network error message
   * @throws Exception
   */

  @Test
  public void C_TC1184048() throws Exception {
    baseComponent.init()
            .addItemToCart()
            .waitTillProgressBarDisappear()
            .tapOnSRCMark();
    UiAutomatorUtil.WifiOff(InstrumentationRegistry.getContext());
    baseComponent.addFpan(InstrumentationRegistry.getContext()).
            tapOnContinueBtnEnrollScreen()
            .verifyNetworkErrorMessage();
    UiAutomatorUtil.WifiOn(InstrumentationRegistry.getContext());
  }

}
