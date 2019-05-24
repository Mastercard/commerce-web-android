package com.us.masterpass.referenceApp.testcase.smoke;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
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

public class T001_UnrecognizedFlow {

  private BaseComponent baseComponent;

  @Rule
  public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

  @Before
  public void before() throws IOException{
    baseComponent = BaseComponent.getInstance();
  }

  /**
   * This is end to end test case to validate SRC unrecognize checkout
   * @throws Exception
   */

  @Test
  public void unrecognizedCheckout() throws Exception{
    baseComponent.init()
        .addItemToCart()
        .waitForSRCMark()
        .tapOnSRCMark()
        .addMastercard(InstrumentationRegistry.getContext())
        .tapOnContinueBtnEnrollScreen()
        .waitForAssociateScreen()
        .addFirstAddress(InstrumentationRegistry.getContext())
        .billingAsShippingCheck()
        .addShippingAddress(InstrumentationRegistry.getContext())
        .enterEmailID()
        .enterMobileNo()
        .tapOnContinueBtnAssociateScreen()
        .waitForReviewUserScreen()
            .tapOnContinueBtnReviewScreen()
            .waitTillProgressBarDisappear()
            .tapNotNowBtnOnRememberMeScreen()
            .waitTillProgressBarDisappear();
  }

}
