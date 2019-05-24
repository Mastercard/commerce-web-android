package com.us.masterpass.referenceApp.testcase.story;

import android.content.Context;
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

public class S717397 {

    private BaseComponent baseComponent;

    @Rule
    public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

    @Before
    public void before() throws IOException {
        baseComponent = BaseComponent.getInstance();

    }

    /**
     * This Testcase is to verify user navigates to Add Shipping Address Screen
     */

    @Test
    public void A_TC1245394() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext()).validateAddShippingAddressScreen(InstrumentationRegistry.getContext());
    }
    /**
     * This Testcase is to verify Save button enabled when user fills all the fields on Add Shipping Address Screen
     */

    @Test
    public void B_TC1245403() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressOnAddShippingAddressScreen(InstrumentationRegistry.getContext())
                .validateSaveBtnEnabled();
    }
    /**
     * This Testcase is to verify Save Button should not  Enable when user fills all field except FirstName on Add Shipping Address Screen
     */
    @Test
    public void C_TC1245409() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressExpectSelectedField(InstrumentationRegistry.getContext(),"firstName")
                .validateSaveBtnNotEnabled();

    }

    /**
     * This Testcase is to verify Save Button should not  Enable when user fills all field except LastName on Add Shipping Address Screen
     */
    @Test
    public void D_TC1249412() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressExpectSelectedField(InstrumentationRegistry.getContext(),"lasName")
                .validateSaveBtnNotEnabled();
    }
    /**
     * This Testcase is to verify Save Button should not  Enable when user fills all field except Addressline 1 on Add Shipping Address Screen
     */

    @Test
    public void E_TC1245414() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressExpectSelectedField(InstrumentationRegistry.getContext(),"addressLine1")
                .validateSaveBtnNotEnabled();
    }
    /**
     * This Testcase is to verify Save Button should Enable when user fills all field except Addressline 2 on Add Shipping Address Screen
     */

    @Test
    public void F_TC1245417() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressExpectSelectedField(InstrumentationRegistry.getContext(),"addressLine2")
                .validateSaveBtnEnabled();
    }
    /**
     * This Testcase is to verify Save Button should not  Enable when user fills all field except City on Add Shipping Address Screen
     */

    @Test
    public void G_TC1245424() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressExpectSelectedField(InstrumentationRegistry.getContext(),"city")
                .validateSaveBtnNotEnabled();
    }
    /**
     * This Testcase is to verify Save Button should not  Enable when user fills all field except State on Add Shipping Address Screen
     */

    @Test
    public void H_TC1245427() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressExpectSelectedField(InstrumentationRegistry.getContext(),"state")
                .validateSaveBtnNotEnabled();
    }
    /**
     * This Testcase is to verify Save Button should not  Enable when user fills all field except ZipCode on Add Shipping Address Screen
     */

    @Test
    public void I_TC1245429() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressExpectSelectedField(InstrumentationRegistry.getContext(),"zipCode")
                .validateSaveBtnNotEnabled();
    }
    /**
     * This Testcase is to verify Cancel & return to merchant , T&C ,Cookies & consent link on Add Shipping Address Screen
     */

    @Test
    public void J_TC1245431() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .validateAllLinks(InstrumentationRegistry.getContext());

    }

    /**
     * This Testcase is to verify Cancel & return to merchant on Add Shipping Address Screen
     */

    @Test
    public void K_TC12454311() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .cancelCheckoutOnAddShippingAddressScreen()
                .clickOnStay()
                .cancelCheckoutOnAddShippingAddressScreen()
                .clickOnReturn()
                .isSRCMarkVisible();

    }

    /**
     * This Testcase is to verify network error message on Add shipping address screen
     */

    @Test
    public void L_TC1245311() throws Exception {
        baseComponent.init().addItemToCart().waitForSRCMark().tapOnSRCMark().waitTillProgressBarDisappear()
                .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen().waitTillProgressBarDisappear()
                .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
                .clickOnEditAddress().clickOnAddBtn(InstrumentationRegistry.getContext())
                .addShippingAddressOnAddShippingAddressScreen(InstrumentationRegistry.getContext());
        UiAutomatorUtil.WifiOff(InstrumentationRegistry.getContext());
        baseComponent.clickSaveBtn().verifyNetworkErrorMessage();
        UiAutomatorUtil.WifiOn(InstrumentationRegistry.getContext());

    }
}
