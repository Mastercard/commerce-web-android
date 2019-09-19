package com.mastercard.mp.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class MasterpassCheckoutRequestTest {

  private MasterpassCheckoutRequest masterpassCheckoutRequest;
  private String merchantName = "merchantName";
  private Amount amount;
  private Tokenization tokenization;
  private String cartId = "cartId";
  private List<NetworkType> allowedNetworkTypes;
  private boolean isShippingRequired = false;
  private String merchantLocale = "merchantLocale";
  private String checkoutId = "checkoutId";
  private Map<String, Object> extensionPoint;
  private String merchantUserId = "merchantUserId";
  private boolean suppress3Ds = false;
  private String shippingProfileId = "shippingProfileId";
  private String callBackUrl = "callBackUrl";
  private boolean cvc2support = false;
  private int validityPeriodMinutes = 1;

  @Test public void testRequest() {
    amount = new Amount(11, "USD");
    tokenization = new Tokenization("unpredictableNumber", new CryptoOptions());
    extensionPoint = new HashMap<>();
    allowedNetworkTypes = new ArrayList<>();
    allowedNetworkTypes.add(new NetworkType("networkType"));
    masterpassCheckoutRequest =
        new MasterpassCheckoutRequest.Builder().setMerchantName(merchantName)
            .setAmount(amount)
            .setTokenization(tokenization)
            .setCartId(cartId)
            .setAllowedNetworkTypes(allowedNetworkTypes)
            .isShippingRequired(isShippingRequired)
            .setMerchantLocale(merchantLocale)
            .setCheckoutId(checkoutId)
            .setExtensionPoint(extensionPoint)
            .setMerchantUserId(merchantUserId)
            .setSuppress3Ds(suppress3Ds)
            .setShippingProfileId(shippingProfileId)
            .setCallBackUrl(callBackUrl)
            .setCvc2Support(cvc2support)
            .setValidityPeriodMinutes(validityPeriodMinutes)
            .build();

    assertEquals(merchantName, masterpassCheckoutRequest.getMerchantName());
    assertEquals(amount, masterpassCheckoutRequest.getAmount());
    assertEquals(tokenization, masterpassCheckoutRequest.getTokenization());
    assertEquals(cartId, masterpassCheckoutRequest.getCartId());
    assertEquals(allowedNetworkTypes, masterpassCheckoutRequest.getAllowedNetworkTypes());
    assertEquals(isShippingRequired, masterpassCheckoutRequest.isShippingRequired());
    assertEquals(merchantLocale, masterpassCheckoutRequest.getMerchantLocale());
    assertEquals(checkoutId, masterpassCheckoutRequest.getCheckoutId());
    assertEquals(extensionPoint, masterpassCheckoutRequest.getExtensionPoint());
    assertEquals(merchantUserId, masterpassCheckoutRequest.getMerchantUserId());
    assertEquals(suppress3Ds, masterpassCheckoutRequest.isSuppress3Ds());
    assertEquals(shippingProfileId, masterpassCheckoutRequest.getShippingProfileId());
    assertEquals(callBackUrl, masterpassCheckoutRequest.getCallBackUrl());
    assertEquals(cvc2support, masterpassCheckoutRequest.isCvc2support());
    assertEquals(validityPeriodMinutes, masterpassCheckoutRequest.getValidityPeriodMinutes());
  }
}
