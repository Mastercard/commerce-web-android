package com.us.masterpass.referenceApp.component;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.us.masterpass.referenceApp.Pojo.Address;
import com.us.masterpass.referenceApp.Pojo.PaymentCard;
import com.us.masterpass.referenceApp.Pojo.UserData;
import com.us.masterpass.referenceApp.instruction.confirmOrderScreenDisplayed;
import com.us.masterpass.referenceApp.robot.AddNewCardScreenRobot;
import com.us.masterpass.referenceApp.robot.AddPaymentMethodScreenRobot;
import com.us.masterpass.referenceApp.robot.CardListScreenRobot;
import com.us.masterpass.referenceApp.instruction.merchantItemPageDisplayed;
import com.us.masterpass.referenceApp.instruction.reviewUserScreenDisplayed;
import com.us.masterpass.referenceApp.instruction.srcMarkDisplayed;
import com.us.masterpass.referenceApp.instruction.associateScreenDisplayed;
import com.us.masterpass.referenceApp.instruction.enrollScreenDisplayed;
import com.us.masterpass.referenceApp.instruction.otpScreenDisplayed;
import com.us.masterpass.referenceApp.instruction.waitTillProgressBarGetDisappear;
import com.us.masterpass.referenceApp.robot.AddShippingAddressScreenRobot;
import com.us.masterpass.referenceApp.robot.AssociateScreenRobot;
import com.us.masterpass.referenceApp.robot.CancelTransactionScreenRobot;
import com.us.masterpass.referenceApp.robot.EnrollCardScreenRobot;
import com.us.masterpass.referenceApp.robot.MerchantAppConfigurationRobot;
import com.us.masterpass.referenceApp.robot.MerchantCheckoutAppRobot;
import com.us.masterpass.referenceApp.robot.MiscellaneousRobot;
import com.us.masterpass.referenceApp.robot.OTPScreenRobot;
import com.us.masterpass.referenceApp.robot.RememberMeScreenRobot;
import com.us.masterpass.referenceApp.robot.ReviewScreenRobot;
import com.us.masterpass.referenceApp.robot.ShippingAddressListScreenRobot;
import com.us.masterpass.referenceApp.utils.ConditionWatcher;
import com.us.masterpass.referenceApp.utils.RandomGenerator;
import com.us.masterpass.referenceApp.utils.UiAutomatorUtil;
import com.us.masterpass.referenceApp.utils.TestData;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

public class BaseComponent {

  private static BaseComponent instance;
  private String mobileNo;
  private String email;
  private String mockOTP = "123456";
  private String totalAmount;
  private String invalidOTP = "543211";

  public static BaseComponent getInstance() {
    if (instance == null) {
      instance = new BaseComponent();
    }
    return instance;
  }

  /**
   * This Method is used to open Merchant Checkout App
   *
   * @throws Exception
   */

  public BaseComponent init() throws Exception {

    MiscellaneousRobot miscellaneousRobot = new MiscellaneousRobot();
    miscellaneousRobot.wakeUp().closeKeyboard();
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new merchantItemPageDisplayed());
    return this;
  }

  /**
   * This method is used to click on masterpass button once it appears
   *
   * @throws Exception
   */

  public BaseComponent tapOnSRCMark() throws Exception {
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    appRobot.tapOnSRCButton();
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new enrollScreenDisplayed());
    return this;
  }

  /**
   * This method is used to select locale
   */

  public BaseComponent selectLocale(String locale) {
    MerchantAppConfigurationRobot config = new MerchantAppConfigurationRobot();
    openCheckoutConfiguration();
    config.openLanguageSelectionScreen();
    if (locale.equalsIgnoreCase("US")) {
      config.tapOnEnglishUS();
    } else if (locale.equalsIgnoreCase("BR")) {
      config.tapOnPortugueseBR();
    } else if (locale.equalsIgnoreCase("MX")) {
      config.tapOnSpanishMX();
    } else if (locale.equalsIgnoreCase("UK")) {
      config.tapOnEnglishUK();
    } else if (locale.equalsIgnoreCase("CA")) {
      config.tapOnEnglishCA();
    }
    UiAutomatorUtil.pressBackButton();
    return this;
  }

  /**
   * This method is used to add MDES card into SRC flow
   *
   * @throws Exception
   */

  public BaseComponent addMastercardExceptSelectedField(Context context, String field)
      throws Exception {
    TestData testData = new TestData();
    PaymentCard paymentCard = testData.getFirstCard(context);
    addCardExceptSelectedField(paymentCard, field);
    return this;
  }

  /**
   * This method is used to add MDES card into SRC flow
   *
   * @throws Exception
   */

  public BaseComponent addMastercardForSelectedField(Context context, String field)
      throws Exception {
    TestData testData = new TestData();
    PaymentCard paymentCard = testData.getFirstCard(context);
    addCardForSelectedField(paymentCard, field);
    return this;
  }

  /**
   * This method is used to add MDES card into SRC flow
   *
   * @throws Exception
   */

  public BaseComponent clearMastercardExceptSelectedField(Context context, String field)
      throws Exception {
    TestData testData = new TestData();
    PaymentCard paymentCard = testData.getSecondCard(context);
    clearCardExceptSelectedField(paymentCard, field);
    return this;
  }

  /**
   * This method is used to click on navigation icon of card in review screen
   */
  public BaseComponent clickNavigationIconOnCard() {
    ReviewScreenRobot reviewScreenRobot = new ReviewScreenRobot();
    reviewScreenRobot.clickOnNavigationIconOnCard();
    return this;
  }

  /**
   * This method is used to add new card from Routing sheet.
   */
  public BaseComponent selectNewCard() {
    AddNewCardScreenRobot addNewCardScreenRobot = new AddNewCardScreenRobot();
    addNewCardScreenRobot.addNewCard();
    return this;
  }

  /**
   * This method is used to add MDES card into SRC flow
   *
   * @throws Exception
   */

  public BaseComponent addNewMastercard(Context context) throws Exception {
    TestData testData = new TestData();
    PaymentCard paymentCard = testData.getSecondCard(context);
    addCard(paymentCard);
    return this;
  }

  /**
   * This method is used verify user is on card list screen
   */
  public BaseComponent validateCardListScreen(Context context) {
    CardListScreenRobot cardListScreenRobot = new CardListScreenRobot();
    cardListScreenRobot.verifyCardListScreen(context);
    return this;
  }

  /**
   * This method is used verify legal docs link on card list screen
   */
  public BaseComponent verifyLegalDocs() throws UiObjectNotFoundException {
    CardListScreenRobot cardListScreenRobot = new CardListScreenRobot();
    cardListScreenRobot.verifyTnCLink(InstrumentationRegistry.getContext())
        .verifyPPLink(InstrumentationRegistry.getContext())
        .verifyCCLink(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is used click on Add a card button
   */
  public BaseComponent clickOnAddBtnOnCarListScreen(Context context) {
    CardListScreenRobot cardListScreenRobot = new CardListScreenRobot();
    cardListScreenRobot.clickOnAddBtnOnCardListScreen(context);
    return this;
  }

  /**
   * This method is used verify Add a payment method screen
   */
  public BaseComponent validateAddPaymentMethodScreen(Context context) {
    AddPaymentMethodScreenRobot addPaymentMethodScreenRobot = new AddPaymentMethodScreenRobot();
    addPaymentMethodScreenRobot.verifyAddPaymentMethodScreen(context);
    return this;
  }

  /**
   * This method is used verify continue button not enabled on payment method screen
   */
  public BaseComponent validateContinueBtnEnabledAddPaymentMethodScreen() {
    AddPaymentMethodScreenRobot addPaymentMethodScreenRobot = new AddPaymentMethodScreenRobot();
    addPaymentMethodScreenRobot.verifyContinueBtnEnabledAddPaymentMethodScreen();
    return this;
  }

  /**
   * This method is used verify continue button not enabled on payment method screen
   */
  public BaseComponent validateContinueBtnNotEnabledAddPaymentMethodScreen() {
    AddPaymentMethodScreenRobot addPaymentMethodScreenRobot = new AddPaymentMethodScreenRobot();
    addPaymentMethodScreenRobot.verifyContinueBtnNotEnabledAddPaymentMethodScreen();
    return this;
  }

  /**
   * This method is used verify continue button not enabled on payment method screen
   */
  public BaseComponent validateContinueBtnNotEnabledAddNewCardScreen() {
    AddNewCardScreenRobot addPaymentMethodScreenRobot = new AddNewCardScreenRobot();
    addPaymentMethodScreenRobot.validateContinueBtnNotEnabled();
    return this;
  }

  /**
   * This method is used verify continue button not enabled on payment method screen
   */
  public BaseComponent validateContinueBtnEnabledAddNewCardScreen() {
    AddNewCardScreenRobot addPaymentMethodScreenRobot = new AddNewCardScreenRobot();
    addPaymentMethodScreenRobot.validateContinueBtnEnabled();
    return this;
  }

  /**
   * This method is used select a card on Card list screen
   */
  public BaseComponent selectCardOnCardListScreen() {
    CardListScreenRobot cardListScreenRobot = new CardListScreenRobot();
    cardListScreenRobot.selectCardFromList();
    return this;
  }

  /**
   * This method is used verify last four digit of card number  on Card list screen
   */
  public BaseComponent validateLastFourDigitOfCardNumber(Context context) {
    TestData testData = new TestData();
    PaymentCard paymentCard = testData.getFirstCard(context);
    String actualCardNumber = paymentCard.getCardNumber().substring(13);
    CardListScreenRobot cardListScreenRobot = new CardListScreenRobot();
    String expectedCardNumber = cardListScreenRobot.verifyLastFourDigitOfCardNumber();
    assertEquals(actualCardNumber, expectedCardNumber);
    return this;
  }

  /**
   * This Method is used to enter card data
   */

  public BaseComponent addCardExceptSelectedField(PaymentCard paymentCard, String field)
      throws Exception {
    EnrollCardScreenRobot enrollCardScreenRobot = new EnrollCardScreenRobot();
    switch (field) {
      case "cardHolderName":
        enrollCardScreenRobot
            .enterCardNumber(paymentCard.getCardNumber())
            .enterCardExpiry(paymentCard.getExpDate())
            .enterCVC(paymentCard.getCvc());
        break;
      case "cardNumber":
        enrollCardScreenRobot.enterCardHolderName(paymentCard.getCardHolderName())
            .enterCardExpiry(paymentCard.getExpDate())
            .enterCVC(paymentCard.getCvc());
        break;
      case "expiry":
        enrollCardScreenRobot.enterCardHolderName(paymentCard.getCardHolderName())
            .enterCardNumber(paymentCard.getCardNumber())
            .enterCVC(paymentCard.getCvc());
        break;
      case "cvc":
        enrollCardScreenRobot.enterCardHolderName(paymentCard.getCardHolderName())
            .enterCardNumber(paymentCard.getCardNumber())
            .enterCardExpiry(paymentCard.getExpDate());
        break;
    }
    return this;
  }

  /**
   * This Method is used to enter card data
   */

  public BaseComponent addCardForSelectedField(PaymentCard paymentCard, String field)
      throws Exception {
    EnrollCardScreenRobot enrollCardScreenRobot = new EnrollCardScreenRobot();
    switch (field) {
      case "cardHolderName":
        enrollCardScreenRobot
            .enterCardHolderName(paymentCard.getCardHolderName());
        break;
      case "cardNumber":
        enrollCardScreenRobot.enterCardNumber(paymentCard.getCardNumber());
        break;
      case "expiry":
        enrollCardScreenRobot.enterCardExpiry(paymentCard.getExpDate());
        break;
      case "cvc":
        enrollCardScreenRobot.enterCVC(paymentCard.getCvc());
        break;
    }
    return this;
  }

  /**
   * This Method is used to enter card data
   */

  public BaseComponent clearCardExceptSelectedField(PaymentCard paymentCard, String field)
      throws Exception {
    EnrollCardScreenRobot enrollCardScreenRobot = new EnrollCardScreenRobot();
    switch (field) {
      case "cardHolderName":
        enrollCardScreenRobot
            .clearCardHolderName();
        break;
      case "cardNumber":
        enrollCardScreenRobot.clearCardNumber();
        break;
      case "expiry":
        enrollCardScreenRobot.clearCardExpiry();
        break;
      case "cvc":
        enrollCardScreenRobot.clearCVC();
        break;
    }
    return this;
  }

  /**
   * This method is used to add item to card and tap on masterpass button and tap on merchant
   * configuration link if available
   *
   * @throws Exception
   */

  public BaseComponent addItemToCart() throws Exception {
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new merchantItemPageDisplayed());
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    appRobot.addItemOneToCart()
        .tapOnAddToCart();
    return this;
  }

  /**
   * This method is used to click on cart icon
   */

  public BaseComponent goToCart() {
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    appRobot.tapOnAddToCart();
    return this;
  }

  /**
   * This method is used open setting menu from merchant app
   */
  public BaseComponent openCheckoutConfiguration() {
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    appRobot.openSideMenu().openConfigurationMenu();
    return this;
  }

  /**
   * This method is used to validate if Masterpass button is visible or not
   */

  public BaseComponent isSRCMarkVisible() {
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    appRobot.isSRCButtonVisible();
    return this;
  }

  /*
   */
  /**
   * This method is to validate checkout failure screen message
   * @return
   */  /*


  public BaseComponent validateFailureScreen() {
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    appRobot.checkoutFailureMessage();
    return this;
  }
  */

  /*
   */
  /**
   * This method is to tap on retry checkout button from checkout failure screen
   * @return
   */  /*


  public BaseComponent tapOnRetryButton() {
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    appRobot.tapOnRetryCheckout();
    return this;
  }
  */

  /**
   * This method is used to login to merchant app
   *
   * @throws Exception
   */

  public BaseComponent loginToApp() throws Exception {
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    appRobot.openSideMenu().openLoginScreen().loginIntoApp();
    return this;
  }

  /**
   * This method is used to wait for otp screen to be displayed
   *
   * @throws Exception
   */

  public BaseComponent waitForOTPScreen() throws Exception {
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new otpScreenDisplayed());
    return this;
  }

  /**
   * This method is used to click on Stay button on Cancel Transaction Screen
   *
   * @throws Exception
   */

  public BaseComponent clickOnStay() throws Exception {
    CancelTransactionScreenRobot robot = new CancelTransactionScreenRobot();
    robot.verifyMessageOnCancelTransaction(InstrumentationRegistry.getContext());
    robot.clickOnStay();
    return this;
  }

  /**
   * This method is used to click on Return button on Cancel Transaction Screen
   *
   * @throws Exception
   */

  public BaseComponent clickOnReturn() throws Exception {
    CancelTransactionScreenRobot robot = new CancelTransactionScreenRobot();
    robot.clickOnReturn();
    return this;
  }

  /**
   * This method is used to wait for DCF associate screen to be displayed
   *
   * @throws Exception
   */

  public BaseComponent waitForAssociateScreen() throws Exception {
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new associateScreenDisplayed());
    return this;
  }

  /**
   * This method is used to wait for SRC mark to be displayed
   *
   * @throws Exception
   */

  public BaseComponent waitForSRCMark() throws Exception {
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new srcMarkDisplayed());
    return this;
  }

  /**
   * This method is used to wait for Review User screen to be displayed
   *
   * @throws Exception
   */

  public BaseComponent waitForReviewUserScreen() throws Exception {
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new reviewUserScreenDisplayed());
    return this;
  }

  /*
   *//**
   * This method is used to tap on done button from checkout success screen
   * @return
   * @throws Exception
   *//*

  public BaseComponent tapOnDoneButton() throws Exception {
    MerchantCheckoutAppRobot appRobot = new MerchantCheckoutAppRobot();
    Thread.sleep(4000);
    appRobot.tapDoneButton();
    return this;
  }*/

  /**
   * This method is to enter existing user email address on returning user screen
   */

  public BaseComponent enterReturningUserEmail(Context context) {
    TestData testData = new TestData();
    UserData userData = testData.getDuplicateUser(context);
    enterEmailAddress(context, userData.getEmail());
    return this;
  }

  /**
   * This method is to enter invalid email address on returning user's screen
   */

  public BaseComponent enterInvalidEmail(Context context) {
    enterEmailAddress(context, "wrong email");
    return this;
  }

  /**
   * This method is to enter text in email address field on returning user's screen
   */

  public BaseComponent enterEmailAddress(Context context, String email) {
    EnrollCardScreenRobot robot = new EnrollCardScreenRobot();
    robot.enterEmailAddress(email);
    return this;
  }

  /**
   * This method is used to add MDES card into SRC flow
   *
   * @throws Exception
   */

  public BaseComponent addMastercard(Context context) throws Exception {
    TestData testData = new TestData();
    PaymentCard paymentCard = testData.getFirstCard(context);
    addCard(paymentCard);
    return this;
  }

  /**
   * This method is used to add FPAN card into SRC flow
   *
   * @throws Exception
   */

  public BaseComponent addFpan(Context context) throws Exception {
    TestData testData = new TestData();
    PaymentCard paymentCard = testData.getMasterCardFPAN(context);
    addCard(paymentCard);
    return this;
  }

  /**
   * This method is used to enter first test address
   *
   * @throws Exception
   */

  public BaseComponent addFirstAddress(Context context) throws Exception {
    TestData testData = new TestData();
    Address address = testData.getFistAddress(context);
    addAddress(address);
    return this;
  }

  /**
   * This method is used to enter second test address
   *
   * @throws Exception
   */

  public BaseComponent addSecondAddress(Context context) throws Exception {
    TestData testData = new TestData();
    Address address = testData.getFistAddress(context);
    addAddress(address);
    return this;
  }

  /**
   * This method is used to enter Shipping address
   *
   * @param context ,field
   * @throws Exception
   */
  public BaseComponent addShippingAddressExpectSelectedField(Context context, String field)
      throws Exception {
    TestData testData = new TestData();
    Address address = testData.getFistAddress(context);
    addAddressExpectSelectedField(address, field);
    return this;
  }

  /**
   * This method is used to add a shipping address
   *
   * @throws Exception
   */

  public BaseComponent addShippingAddress(Context context) throws Exception {
    TestData testData = new TestData();
    Address address = testData.getFistShippingAddress(context);
    addShippingAddress(address);
    return this;
  }

  /**
   * This method is used to add a shipping address on Add Shipping Address Screen
   *
   * @throws Exception
   */
  public BaseComponent addShippingAddressOnAddShippingAddressScreen(Context context)
      throws Exception {
    TestData testData = new TestData();
    Address address = testData.getFistShippingAddress(context);
    addShippingAddressOnAddShippingAddressScreen(address);
    return this;
  }

  /**
   * This method is used to enter address without optional fields
   *
   * @throws Exception
   */

  public BaseComponent addAddressWithoutOptionalField(Context context) throws Exception {
    TestData testData = new TestData();
    Address address = testData.getAddressWithoutOptionalField(context);
    addAddress(address);
    return this;
  }

  /**
   * This method is used to check billing as shipping checkbox on associate screen
   *
   * @throws Exception
   */

  public BaseComponent billingAsShippingCheck() throws Exception {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.checkBillingAsShipping();
    return this;
  }

  /**
   * This method is used to tap on continue button on enroll card screen
   *
   * @throws Exception
   */

  public BaseComponent tapOnContinueBtnEnrollScreen() throws Exception {
    EnrollCardScreenRobot enrollCardScreenRobot = new EnrollCardScreenRobot();
    enrollCardScreenRobot.tapOnContinue();
    return this;
  }

  /**
   * This method is used to tap on continue button on otp screen
   *
   * @throws Exception
   */

  public BaseComponent tapOnContinueBtnOTPScreen() throws Exception {
    OTPScreenRobot otpScreenRobot = new OTPScreenRobot();
    otpScreenRobot.tapOnContinue();
    return this;
  }

  /**
   * This method is used to tap on continue button on associate screen
   *
   * @throws Exception
   */

  public BaseComponent tapOnContinueBtnAssociateScreen() throws Exception {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.tapOnContinueButton();
    return this;
  }

  /**
   * This method is used to tap on continue button on Review User screen
   *
   * @throws Exception
   */

  public BaseComponent tapOnContinueBtnReviewScreen() throws Exception {
    ReviewScreenRobot reviewUserRobot = new ReviewScreenRobot();
    reviewUserRobot.clickOnContinue();
    return this;
  }

  /**
   * This method is used to click on Edit Shipping address on User Review screen
   *
   * @throws Exception
   */

  public BaseComponent clickOnEditAddress() throws Exception {
    ReviewScreenRobot robot = new ReviewScreenRobot();
    robot.clickOnEditIcon();
    return this;
  }

  /**
   * This method is used to click on Add button on Address List Screen
   *
   * @throws Exception
   */

  public BaseComponent clickOnAddBtn(Context context) throws Exception {
    ShippingAddressListScreenRobot robot = new ShippingAddressListScreenRobot();
    robot.clickOnAddButton(context);
    return this;
  }

  /**
   * This method is used to click on cancel and return to merchant on address list screen
   *
   * @throws Exception
   */

  public BaseComponent cancelCheckoutOnAddressListScreen() throws Exception {
    ShippingAddressListScreenRobot robot = new ShippingAddressListScreenRobot();
    robot.cancelCheckout();
    return this;
  }

  /**
   * This method is used to select Shipping address from address list
   *
   * @throws Exception
   */

  public BaseComponent selectAddressFromList() throws Exception {
    ShippingAddressListScreenRobot robot = new ShippingAddressListScreenRobot();
    robot.selectFirstShippingAddress();
    return this;
  }

  /**
   * This method is used to verify all legal content link on Address list screen
   *
   * @throws Exception
   */

  public BaseComponent verifyLegalContentLinkOnAddressListScreen() throws Exception {
    ShippingAddressListScreenRobot robot = new ShippingAddressListScreenRobot();
    robot.tncLink()
        .privacyPolicyLink()
        .tncLink();
    return this;
  }

  /**
   * This method is used to verify all legal content link on Associate screen
   *
   * @throws Exception
   */

  public BaseComponent verifyLegalContentLinkOnAssociateScreen() throws Exception {
    AssociateScreenRobot robot = new AssociateScreenRobot();
    robot.tncLink(InstrumentationRegistry.getContext())
        .privacyPolicyLink(InstrumentationRegistry.getContext())
        .cookieConsentLink(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is used to verify all legal content link on Associate screen
   *
   * @throws Exception
   */

  public BaseComponent verifyLegalContentLinkOnScreen() throws Exception {
    ReviewScreenRobot robot = new ReviewScreenRobot();
    robot.verifyTnCLink(InstrumentationRegistry.getContext())
        .verifyPPLink(InstrumentationRegistry.getContext())
        .verifyCCLink(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is used to verify all legal content link on OTP screen
   *
   * @throws Exception
   */

  public BaseComponent verifyLegalContentLinkOnOTPScreen() throws Exception {
    OTPScreenRobot robot = new OTPScreenRobot();
    robot.validateCookieContent(InstrumentationRegistry.getContext())
        .validatePrivacy(InstrumentationRegistry.getContext())
        .validateTnCLink(InstrumentationRegistry.getContext())
        .validateTradeMarkText(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is used to verify email and phone no. on review screen
   *
   * @throws Exception
   */

  public BaseComponent reviewEmailAndMobile() throws Exception {
    ReviewScreenRobot reviewScreenRobot = new ReviewScreenRobot();
    reviewScreenRobot.verifyEmail(email);
    reviewScreenRobot.verifyMobile(mobileNo);
    return this;
  }

  /**
   * This method is used to verify amount on review screen
   *
   * @throws Exception
   */

  public BaseComponent reviewAmountOnReviewScreen() throws Exception {
    ReviewScreenRobot robot = new ReviewScreenRobot();
    String amount = robot.getAmountOnReviewScreen();
    assertEquals(amount, totalAmount);
    return this;
  }

  /**
   * This method is used to verify shipping address on review screen
   *
   * @throws Exception
   */

  public BaseComponent reviewShippingAddressOnReviewScreen(Context context) throws Exception {
    TestData testData = new TestData();
    Address address = testData.getFistAddress(context);
    reviewShippingAddressOnUserReviewScreen(address);
    return this;
  }

  /**
   * This method is to review shipping address added by user on user's review screen
   */

  private BaseComponent reviewShippingAddressOnUserReviewScreen(Address address) {
    ReviewScreenRobot robot = new ReviewScreenRobot();
    robot.verifyShippingAddress(address.getAddressLine2(), address.getCity(), address.getState(),
        address.getZipCode());
    return this;
  }

  /**
   * This method is used to enter address
   *
   * @throws Exception
   */

  public BaseComponent addAddress(Address address) throws Exception {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.enterFistName(address.getFirstName())
        .enterLastName(address.getLastName())
        .enterAddressLine1(address.getAddressLine1())
        .enterAddressLine2(address.getAddressLine2())
        .enterCity(address.getCity())
        .enterState(address.getState())
        .enterZipCode(address.getZipCode());
    return this;
  }

  /**
   * This method is used to enter address
   *
   * @throws Exception
   */
  public BaseComponent addAddressExpectSelectedField(Address address, String field)
      throws Exception {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    switch (field) {
      case "firstName":
        associateScreenRobot.enterLastName(address.getLastName())
            .enterAddressLine1(address.getAddressLine1())
            .enterAddressLine2(address.getAddressLine2())
            .enterCity(address.getCity())
            .enterState(address.getState())
            .enterZipCodeforShippingAddress(address.getZipCode());
        break;
      case "lastName":
        associateScreenRobot.enterFistName(address.getFirstName())
            .enterAddressLine1(address.getAddressLine1())
            .enterAddressLine2(address.getAddressLine2())
            .enterCity(address.getCity())
            .enterState(address.getState())
            .enterZipCodeforShippingAddress(address.getZipCode());
        break;
      case "addressLine1":
        associateScreenRobot.enterFistName(address.getFirstName())
            .enterLastName(address.getLastName())
            .enterAddressLine2(address.getAddressLine2())
            .enterCity(address.getCity())
            .enterState(address.getState())
            .enterZipCodeforShippingAddress(address.getZipCode());
        break;
      case "addressLine2":
        associateScreenRobot.enterFistName(address.getFirstName())
            .enterLastName(address.getLastName())
            .enterAddressLine1(address.getAddressLine1())
            .enterCity(address.getCity())
            .enterState(address.getState())
            .enterZipCodeforShippingAddress(address.getZipCode());
        break;
      case "city":
        associateScreenRobot.enterFistName(address.getFirstName())
            .enterLastName(address.getLastName())
            .enterAddressLine1(address.getAddressLine1())
            .enterAddressLine2(address.getAddressLine2())
            .enterState(address.getState())
            .enterZipCodeforShippingAddress(address.getZipCode());
        break;
      case "state":
        associateScreenRobot.enterFistName(address.getFirstName())
            .enterLastName(address.getLastName())
            .enterAddressLine1(address.getAddressLine1())
            .enterAddressLine2(address.getAddressLine2())
            .enterCity(address.getCity())
            .enterZipCodeforShippingAddress(address.getZipCode());
        break;
      case "zipCode":
        associateScreenRobot.enterFistName(address.getFirstName())
            .enterLastName(address.getLastName())
            .enterAddressLine1(address.getAddressLine1())
            .enterAddressLine2(address.getAddressLine2())
            .enterCity(address.getCity())
            .enterState(address.getState());
        break;
    }
    return this;
  }

  /**
   * This method is used to enter shipping address
   *
   * @throws Exception
   */

  public BaseComponent addShippingAddress(Address address) throws Exception {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.enterFistNameForShippingAddress(address.getFirstName())
        .enterLastNameforShippingAddress(address.getLastName())
        .enterAddressLine1forShippingAddress(address.getAddressLine1())
        .enterAddressLine2forShippingAddress(address.getAddressLine2())
        .enterCityforShippingAddress(address.getCity())
        .enterStateforShippingAddress(address.getState())
        .enterZipCodeforShippingAddress(address.getZipCode());
    return this;
  }

  /**
   * This method is used to enter shipping address on Add Shipping Address Screen
   *
   * @throws Exception
   */
  public BaseComponent addShippingAddressOnAddShippingAddressScreen(Address address)
      throws Exception {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.enterFistName(address.getFirstName()).enterLastName(address.getLastName())
        .enterAddressLine1(address.getAddressLine1())
        .enterAddressLine2(address.getAddressLine2())
        .enterCity(address.getCity())
        .enterState(address.getState())
        .enterZipCodeforShippingAddress(address.getZipCode());
    return this;
  }

  /**
   * This Method is used to enter card data
   */

  public BaseComponent addCard(PaymentCard paymentCard) throws Exception {
    EnrollCardScreenRobot enrollCardScreenRobot = new EnrollCardScreenRobot();
    enrollCardScreenRobot.enterCardHolderName(paymentCard.getCardHolderName())
        .enterCardNumber(paymentCard.getCardNumber())
        .enterCardExpiry(paymentCard.getExpDate())
        .enterCVC(paymentCard.getCvc());
    return this;
  }

  /**
   * This method is used to return to merchant app from enroll card screen
   */

  public BaseComponent returnToMerchantFromEnrollCardScreen() throws Exception {
    EnrollCardScreenRobot enrollCardScreenRobot = new EnrollCardScreenRobot();
    enrollCardScreenRobot.tapOnCancelCheckout();
    return this;
  }

  /**
   * This method is used to return to merchant app from  Associate screen
   */

  public BaseComponent returnToMerchantOnAssociateScreen() throws UiObjectNotFoundException {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.cancelCheckout(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is used to return to merchant app from Review Screen
   */

  public BaseComponent returnToMerchantOnReviewScreen() throws UiObjectNotFoundException {
    ReviewScreenRobot reviewScreenRobot = new ReviewScreenRobot();
    reviewScreenRobot.cancelCheckout(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is to flow of adding FPAN card and validating error message
   *
   * @throws Exception
   */

  public BaseComponent validateFPANErrorMessage() throws Exception {
    EnrollCardScreenRobot enrollCardScreenRobot = new EnrollCardScreenRobot();
    addFpan(InstrumentationRegistry.getContext());
    tapOnContinueBtnEnrollScreen();
    waitTillProgressBarDisappear();
    enrollCardScreenRobot.validateFPANErrorMessage(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is used to accept  network error message pop-up in review Screen
   *
   * @throws Exception
   */
  public BaseComponent acceptPopUpNetworkErrorMessage() throws Exception {
    MiscellaneousRobot robot = new MiscellaneousRobot();
    Thread.sleep(3000);
    robot.tapOnNetworkErrorPopUpBtn(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is used to validate duplicate user error message when user adding card
   *
   * @throws Exception
   */

  public BaseComponent validateDuplicateUserErrorMessage() throws Exception {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.verifyDuplicateError(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is used to validate network error message.
   *
   * @throws Exception
   */

  public BaseComponent verifyNetworkErrorMessage() throws Exception {
    MiscellaneousRobot robot = new MiscellaneousRobot();
    robot.validateNetworkError(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is enter email ID on associate screen
   */

  public BaseComponent enterEmailID() throws UiObjectNotFoundException {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    email = RandomGenerator.randomEmailAddress();
    associateScreenRobot.enterEmailID(email);
    return this;
  }

  /**
   * This method is enter duplicate email ID on associate screen
   */

  public BaseComponent enterDuplicateEmailID(Context context) throws UiObjectNotFoundException {
    TestData testData = new TestData();
    UserData userData = testData.getDuplicateUser(context);
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.enterEmailID(userData.getEmail());
    return this;
  }

  /**
   * This method is enter mobile number on associate screen
   */

  public BaseComponent enterMobileNo() {
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    mobileNo = RandomGenerator.randomPhoneNumber();
    associateScreenRobot.enterMobileNo(mobileNo);
    return this;
  }

  /**
   * This method is enter duplicate mobile number on associate screen
   */

  public BaseComponent enterDuplicateMobileNo(Context context) {
    TestData testData = new TestData();
    UserData userData = testData.getDuplicateUser(context);
    AssociateScreenRobot associateScreenRobot = new AssociateScreenRobot();
    associateScreenRobot.enterMobileNo(userData.getPhoneNumber());
    return this;
  }

  /**
   * This method is used to get total transaction amount on cart screen
   */

  public BaseComponent getTotalAmount() throws InterruptedException {
    MerchantCheckoutAppRobot robot = new MerchantCheckoutAppRobot();
    totalAmount = robot.getTotalAmount();
    return this;
  }

  /**
   * This method is to validate total amount on enroll screen is matches to transaction amount
   */

  public BaseComponent validateTotalAmountOnEnrollScreen() {
    EnrollCardScreenRobot robot = new EnrollCardScreenRobot();
    String amount = robot.getTotalAmount();
    amount.matches(totalAmount);
    return this;
  }

  /**
   * This method is to validate total amount on associate screen screen is matches to transaction
   * amount
   */

  public BaseComponent validateTotalAmountOnAssociateScreen() {
    AssociateScreenRobot robot = new AssociateScreenRobot();
    String amount = robot.getTotalAmount();
    assertEquals(amount, totalAmount);
    return this;
  }

  /**
   * This method is to verify RememberMe Screen
   */
  public BaseComponent verifyRememberMeScreen() {
    RememberMeScreenRobot rememberMeScreenRobot = new RememberMeScreenRobot();
    rememberMeScreenRobot.validateRememberBtn(InstrumentationRegistry.getContext());
    rememberMeScreenRobot.validateNotNowBtn(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is to click on Not Now Button on RememberMe Screen
   *
   * @throws Exception
   */

  public BaseComponent tapNotNowBtnOnRememberMeScreen() throws Exception {
    RememberMeScreenRobot rememberMeScreenRobot = new RememberMeScreenRobot();
    rememberMeScreenRobot.clickNotNowBtn();
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new confirmOrderScreenDisplayed());
    return this;
  }

  /**
   * This method is to click on Remember Me Button on RememberMe Screen
   */
  public BaseComponent tapRememberMeBanOnRememberMeScreen() {
    RememberMeScreenRobot rememberMeScreenRobot = new RememberMeScreenRobot();
    rememberMeScreenRobot.clickRememberBtn();
    return this;
  }

  /**
   * This method is to verify Transition Screen
   */
  public BaseComponent verifyTransitionScreen() {
    MiscellaneousRobot miscellaneousRobot = new MiscellaneousRobot();
    miscellaneousRobot.validateTranisitionScreenMesg();
    return this;
  }

  /**
   * This method is to verify Confirm Order Screen
   */
  public BaseComponent verifyConfirmOrderScreen() {
    MiscellaneousRobot miscellaneousRobot = new MiscellaneousRobot();
    miscellaneousRobot.validateCartConfirmScreen();
    return this;
  }

  /**
   * This method is to verify save button not enabled on Add a Shipping Address screen
   */
  public BaseComponent validateSaveBtnNotEnabled() {
    AddShippingAddressScreenRobot addShippingAddressScreenRobot =
        new AddShippingAddressScreenRobot();
    addShippingAddressScreenRobot.verifySaveBtnNotEnabled();
    return this;
  }

  /**
   * This method is to verify save button enabled on Add a Shipping Address screen
   */
  public BaseComponent validateSaveBtnEnabled() {
    AddShippingAddressScreenRobot addShippingAddressScreenRobot =
        new AddShippingAddressScreenRobot();
    addShippingAddressScreenRobot.verifySaveBtnEnabled();
    return this;
  }

  /**
   * This method is to click on save button on Add a Shipping Address screen
   */
  public BaseComponent clickSaveBtn() {
    AddShippingAddressScreenRobot addShippingAddressScreenRobot =
        new AddShippingAddressScreenRobot();
    addShippingAddressScreenRobot.clickSaveBtn();
    return this;
  }

  /**
   * This method is to verify user navigates to Add a Shipping Address screen
   */
  public BaseComponent validateAddShippingAddressScreen(Context context) {
    AddShippingAddressScreenRobot addShippingAddressScreenRobot =
        new AddShippingAddressScreenRobot();
    addShippingAddressScreenRobot.verifyAddShippingAddressScreen(context);
    return this;
  }

  /**
   * This method is to verify all links on Add a Shipping Address screen
   */
  public BaseComponent validateAllLinks(Context context) {
    AddShippingAddressScreenRobot addShippingAddressScreenRobot =
        new AddShippingAddressScreenRobot();
    addShippingAddressScreenRobot.verifyAllLinks(context);
    return this;
  }

  /**
   * This method is to click on Cancel and return to merchant on Add shipping address screen
   */
  public BaseComponent cancelCheckoutOnAddShippingAddressScreen() {
    AddShippingAddressScreenRobot addShippingAddressScreenRobot =
        new AddShippingAddressScreenRobot();
    addShippingAddressScreenRobot.clickOnCancel();
    return this;
  }

  /**
   * This method is to change tab between new user and retuning user on enroll card screen
   */

  public BaseComponent changeTabOnEnrollCardScreen(String flow) {
    EnrollCardScreenRobot robot = new EnrollCardScreenRobot();
    if (flow.contains("returning user")) {
      robot.tapOnReturningUser(InstrumentationRegistry.getContext());
    } else {
      robot.tapOnEnrollCardTab(InstrumentationRegistry.getContext());
    }
    return this;
  }

  /**
   * This method is to validate that continue button on enroll card screen remain disabled for wrong
   * user's date
   */
  public BaseComponent validateContinueBtnIsDisabledOnEnrollScreen() {
    EnrollCardScreenRobot robot = new EnrollCardScreenRobot();
    robot.continueBtnDisabled();
    return this;
  }

  /**
   * This method is to validate email field get empty when user switch between new user and
   * returning user tab
   */

  public BaseComponent validateEmailFieldIsEmpty() {
    EnrollCardScreenRobot robot = new EnrollCardScreenRobot();
    robot.validateEmailFieldIsEmpty(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is to enter opt after email verification for returning user
   */

  public BaseComponent enterOTP() {
    OTPScreenRobot robot = new OTPScreenRobot();
    robot.enterOTP(mockOTP);
    return this;
  }

  /**
   * This method is to click on cancel and return to merchant on returning user screen
   */

  public BaseComponent cancelCheckout() {
    OTPScreenRobot robot = new OTPScreenRobot();
    robot.tapOnCancelAndReturnLink();
    return this;
  }

  /**
   * This method is to validate invalid OTP message
   */
  public BaseComponent verifyInvalidOTPMessage() throws Exception {
    OTPScreenRobot otpScreenRobot = new OTPScreenRobot();
    otpScreenRobot.enterOTP(invalidOTP);
    tapOnContinueBtnOTPScreen();
    waitTillProgressBarDisappear();
    otpScreenRobot.validateIncorrectOTPMessage(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is to validate account lock message on OTP screen
   */
  public BaseComponent verifyAccountLockMessage() {
    OTPScreenRobot robot = new OTPScreenRobot();
    robot.validateAccountLockMessage(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is to lock account with wrong otp
   */

  public BaseComponent lockAccountThroughWrongOTP() throws Exception {
    OTPScreenRobot robot = new OTPScreenRobot();
    for (int i = 0; i <= 3; i++) {
      robot.enterOTP(invalidOTP)
          .tapOnContinue();
      waitTillProgressBarDisappear();
    }
    return this;
  }

  /**
   * This method is to tap on ok button of any popup
   */
  public BaseComponent tapOnOK() {
    OTPScreenRobot robot = new OTPScreenRobot();
    robot.tapOnOk(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is to validate account lock message on email verification screen
   */

  public BaseComponent validateAccountLockMessageOnEmailVerificationScreen() {
    EnrollCardScreenRobot robot = new EnrollCardScreenRobot();
    robot.validateAccountLockMessage(InstrumentationRegistry.getContext());
    return this;
  }

  /**
   * This method is to wait till progress bar disappear
   *
   * @throws Exception
   */

  public BaseComponent waitTillProgressBarDisappear() throws Exception {
    ConditionWatcher.setTimeoutLimit(1000 * 100);
    ConditionWatcher.setWatchInterval(250);
    ConditionWatcher.waitForCondition(new waitTillProgressBarGetDisappear());
    return this;
  }
}