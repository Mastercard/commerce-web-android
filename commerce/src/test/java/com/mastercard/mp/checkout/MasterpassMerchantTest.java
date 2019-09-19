package com.mastercard.mp.checkout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mastercard.commerce.CardType;
import com.mastercard.commerce.CheckoutButton;
import com.mastercard.commerce.CheckoutButtonManager;
import com.mastercard.commerce.CheckoutRequest;
import com.mastercard.commerce.CommerceConfig;
import com.mastercard.commerce.CommerceWebSdk;
import java.util.Collections;
import java.util.Locale;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.mastercard.mp.checkout.MasterpassError.ERROR_CODE_NOT_SUPPORTED;
import static com.mastercard.mp.checkout.NetworkType.JCB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27) @PrepareForTest({
    CommerceWebSdk.class, MasterpassMerchantConfiguration.class, BitmapFactory.class, Context.class,
    MasterpassPaymentMethod.class, MasterpassMerchant.class, CheckoutButtonManager.class,
    Tokenization.class
}) @PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "org.powermock.*" })
public class MasterpassMerchantTest {

  @Rule public PowerMockRule rule = new PowerMockRule();
  private String checkoutID = "checkoutID";
  private String checkoutUrl = "checkoutUrl";
  private String cartId = "cartId";
  private boolean cvcSupport = false;
  private String shippingProfileId = "shippingProfileId";
  private boolean suppress3ds = true;
  private boolean shippingRequired = false;
  private int periodMinutes = 1;
  private String unpredictableNumber = "unpredictableNumber";
  private String format = "format";
  private String paymentMethodId = "paymentMethodId";
  private Amount amount = new Amount(10, "USD");

  @Mock MasterpassMerchantConfiguration masterpassMerchantConfiguration;
  @Mock MasterpassInitCallback masterpassInitCallback;
  @Mock MasterpassCheckoutCallback masterpassCheckoutCallback;
  @Mock NetworkType networkType;
  @Mock Context context;
  @Mock CommerceWebSdk commerceWebSdk;
  @Mock MasterpassCheckoutRequest masterpassCheckoutRequest;
  @Mock Tokenization tokenization;
  @Mock CryptoOptions cryptoOptions;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);

    setupCheckoutRequest();

    when(masterpassCheckoutCallback.getCheckoutRequest()).thenReturn(masterpassCheckoutRequest);

    when(networkType.getNetworkType()).thenReturn("");
    when(masterpassMerchantConfiguration.getContext()).thenReturn(context);
    when(masterpassMerchantConfiguration.getAllowedNetworkTypes()).thenReturn(
        Collections.singletonList(networkType));
    when(masterpassMerchantConfiguration.getLocale()).thenReturn(Locale.US);
    when(masterpassMerchantConfiguration.getCheckoutId()).thenReturn(checkoutID);
    when(masterpassMerchantConfiguration.getEnvironment()).thenReturn(checkoutUrl);
    mockStatic(CommerceWebSdk.class);
    when(CommerceWebSdk.getInstance()).thenReturn(commerceWebSdk);
    doNothing().when(commerceWebSdk).initialize(any(Context.class), any(CommerceConfig.class));
    doNothing().when(commerceWebSdk).checkout(any(CheckoutRequest.class));

    MasterpassMerchant.initialize(masterpassMerchantConfiguration, masterpassInitCallback);
  }

  private void setupCheckoutRequest() {
    com.mastercard.mp.checkout.CryptoOptions.Mastercard mastercard =
        new com.mastercard.mp.checkout.CryptoOptions.Mastercard();
    mastercard.setFormat(Collections.singletonList(format));
    com.mastercard.mp.checkout.CryptoOptions.Visa visa =
        new com.mastercard.mp.checkout.CryptoOptions.Visa();
    visa.setFormat(Collections.singletonList(format));

    when(cryptoOptions.getMastercard()).thenReturn(mastercard);
    when(cryptoOptions.getVisa()).thenReturn(visa);

    when(tokenization.getCryptoOptions()).thenReturn(cryptoOptions);
    when(tokenization.getUnpredictableNumber()).thenReturn(unpredictableNumber);

    when(masterpassCheckoutRequest.getAmount()).thenReturn(amount);

    when(masterpassCheckoutRequest.getCartId()).thenReturn(cartId);
    when(masterpassCheckoutRequest.isCvc2support()).thenReturn(cvcSupport);
    when(masterpassCheckoutRequest.getShippingProfileId()).thenReturn(shippingProfileId);
    when(masterpassCheckoutRequest.isSuppress3Ds()).thenReturn(suppress3ds);
    when(masterpassCheckoutRequest.isShippingRequired()).thenReturn(shippingRequired);
    when(masterpassCheckoutRequest.getValidityPeriodMinutes()).thenReturn(periodMinutes);
    when(masterpassCheckoutRequest.getTokenization()).thenReturn(tokenization);
  }

  @Test public void testInitialize() {
    verify(masterpassInitCallback).onInitSuccess();
  }

  @Test public void testAddMasterpassPaymentMethod() {
    Bitmap bitmap = mock(Bitmap.class);
    mockStatic(BitmapFactory.class);
    when(BitmapFactory.decodeResource(any(Resources.class), anyInt())).thenReturn(bitmap);
    String value = "Masterpass";
    PaymentMethodCallback paymentMethodCallback = mock(PaymentMethodCallback.class);

    MasterpassMerchant.addMasterpassPaymentMethod(paymentMethodCallback);

    ArgumentCaptor<MasterpassPaymentMethod> masterpassPaymentMethodArgumentCaptor =
        ArgumentCaptor.forClass(MasterpassPaymentMethod.class);
    verify(paymentMethodCallback).onPaymentMethodAdded(
        masterpassPaymentMethodArgumentCaptor.capture());
    MasterpassPaymentMethod paymentMethodFromAC = masterpassPaymentMethodArgumentCaptor.getValue();

    assertEquals(bitmap, paymentMethodFromAC.getPaymentMethodLogo());
    assertEquals(value, paymentMethodFromAC.getPaymentWalletId());
    assertEquals(value, paymentMethodFromAC.getPaymentMethodName());
  }

  @Test public void testGetMasterpassButton() {
    CheckoutButtonManager checkoutButtonManager = mock(CheckoutButtonManager.class);
    mockStatic(CheckoutButtonManager.class);
    when(CheckoutButtonManager.getInstance()).thenReturn(checkoutButtonManager);

    ArgumentCaptor<CheckoutButton.CheckoutButtonClickListener> argumentCaptor =
        ArgumentCaptor.forClass(CheckoutButton.CheckoutButtonClickListener.class);

    MasterpassMerchant.getMasterpassButton(masterpassCheckoutCallback);

    verify(checkoutButtonManager).getCheckoutButton(argumentCaptor.capture());

    CheckoutButton.CheckoutButtonClickListener checkoutButtonClickListener =
        argumentCaptor.getValue();

    checkoutButtonClickListener.onClick();

    verify(masterpassCheckoutCallback).getCheckoutRequest();
    assertEquals(masterpassCheckoutCallback, MasterpassMerchant.getCheckoutCallback());
  }

  @Test public void testMasterpassCheckout() {
    MasterpassMerchant.masterpassCheckout(masterpassCheckoutCallback);

    assertTrue(MasterpassMerchant.isMerchantInitiated());
    verify(masterpassCheckoutCallback).getCheckoutRequest();
  }

  @Test public void testPairingTrue() {
    MasterpassMerchant.pairing(true, masterpassCheckoutCallback);
    assertTrue(MasterpassMerchant.isMerchantInitiated());
    verify(masterpassCheckoutCallback).getCheckoutRequest();
  }

  @Test public void testPairingFalse() {
    MasterpassMerchant.pairing(false, masterpassCheckoutCallback);

    ArgumentCaptor<MasterpassError> masterpassErrorArgumentCaptor =
        ArgumentCaptor.forClass(MasterpassError.class);
    verify(masterpassCheckoutCallback).onCheckoutError(masterpassErrorArgumentCaptor.capture());
    MasterpassError masterpassError = masterpassErrorArgumentCaptor.getValue();

    assertEquals(ERROR_CODE_NOT_SUPPORTED, masterpassError.code());
  }

  @Test public void testPaymentMethodCheckout() {
    MasterpassMerchant.paymentMethodCheckout(paymentMethodId, masterpassCheckoutCallback);
    verify(masterpassCheckoutCallback).getCheckoutRequest();
  }
}