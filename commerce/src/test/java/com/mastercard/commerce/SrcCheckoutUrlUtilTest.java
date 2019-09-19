package com.mastercard.commerce;

import android.net.Uri;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class) public class SrcCheckoutUrlUtilTest {
  /*private static final String ALLOWED_CARD_TYPES_KEY = "allowedCardTypes";
  private static final String CALLBACK_URL_KEY = "callbackUrl";
  private static final String CART_ID_KEY = "cartId";
  private static final String CURRENCY_KEY = "currency";
  private static final String AMOUNT_KEY = "amount";
  private static final String CHECKOUT_ID_KEY = "checkoutId";
  private static final String SUPPRESS_SHIPPING_KEY = "suppressShippingAddress";
  private static final String LOCALE_KEY = "locale";
  private static final String SUPPRESS_3DS_KEY = "suppress3DS";
  private static final String SHIPPING_LOCATION_PROFILES_KEY = "shippingLocationProfiles";
  private static final String CRYPTO_FORMAT_MASTER_KEY = "masterCryptoFormat";
  private static final String CRYPTO_FORMAT_VISA_KEY = "visaCryptoFormat";
  private static final String UNPREDICTABLE_NUMBER_KEY = "unpredictableNumber";
  private static final String CVC2_SUPPORT_KEY = "cvc2Support";
  private static final String VALIDITY_PERIOD_MINUTES_KEY = "validityPeriodMinutes";
  private static final String CHANNEL_KEY = "channel";
  private static final String CHANNEL_MOBILE = "mobile";

  private final TestUtils testUtils = new TestUtils();

  @Test public void srcWebUrl_urlParts_areFormed() {
    CommerceConfig config = testUtils.getCommerceConfig();
    CheckoutRequest request = testUtils.getCheckoutRequest();
    String preparedUrl = SrcCheckoutUrlUtil.getCheckoutUrl(config, request);

    Uri uri = Uri.parse(preparedUrl);

    String scheme = uri.getScheme();
    assertEquals(TestUtils.URL_PARTS_SCHEME, scheme);

    String authority = uri.getAuthority();
    assertEquals(TestUtils.URL_PARTS_AUTHORITY, authority);

    String path = uri.getPath();
    assertEquals(TestUtils.URL_PARTS_PATH, path);
  }

  @Test public void srcWebUrl_requiredParams_arePresent() {
    CommerceConfig config = testUtils.getCommerceConfig();
    CheckoutRequest request = testUtils.getCheckoutRequest();
    String preparedUrl = SrcCheckoutUrlUtil.getCheckoutUrl(config, request);

    Uri uri = Uri.parse(preparedUrl);

    String allowedCardTypes = uri.getQueryParameter(ALLOWED_CARD_TYPES_KEY);
    assertNotNull(allowedCardTypes);
    assertTrue(allowedCardTypes.contains("master"));
    assertTrue(allowedCardTypes.contains("visa"));

    String checkoutId = uri.getQueryParameter(CHECKOUT_ID_KEY);
    assertNotNull(checkoutId);
    assertEquals(TestUtils.CHECKOUT_ID, checkoutId);

    String locale = uri.getQueryParameter(LOCALE_KEY);
    assertNotNull(locale);
    assertEquals(TestUtils.LOCALE.toString(), locale);

    String amount = uri.getQueryParameter(AMOUNT_KEY);
    assertNotNull(amount);
    assertEquals(String.valueOf(TestUtils.AMOUNT), amount);

    String cartId = uri.getQueryParameter(CART_ID_KEY);
    assertNotNull(cartId);
    assertEquals(TestUtils.CART_ID, cartId);

    String currency = uri.getQueryParameter(CURRENCY_KEY);
    assertNotNull(currency);
    assertEquals(TestUtils.CURRENCY, currency);

    String channel = uri.getQueryParameter(CHANNEL_KEY);
    assertNotNull(channel);
    assertEquals(TestUtils.CHANNEL, channel);
  }

  @Test public void srcWebUrl_optionalParams_notSet_areNotPresent() {
    CommerceConfig config = testUtils.getCommerceConfig();
    CheckoutRequest request = testUtils.getCheckoutRequest();
    String preparedUrl = SrcCheckoutUrlUtil.getCheckoutUrl(config, request);

    Uri uri = Uri.parse(preparedUrl);

    String callbackUrl = uri.getQueryParameter(CALLBACK_URL_KEY);
    assertNull(callbackUrl);

    String masterCryptoFormat = uri.getQueryParameter(CRYPTO_FORMAT_MASTER_KEY);
    assertNull(masterCryptoFormat);

    String visaCryptoFormat = uri.getQueryParameter(CRYPTO_FORMAT_VISA_KEY);
    assertNull(visaCryptoFormat);

    String cvc2Support = uri.getQueryParameter(CVC2_SUPPORT_KEY);
    assertNull(cvc2Support);

    String shippingLocationProfile = uri.getQueryParameter(SHIPPING_LOCATION_PROFILES_KEY);
    assertNull(shippingLocationProfile);

    String suppress3Ds = uri.getQueryParameter(SUPPRESS_3DS_KEY);
    assertNull(suppress3Ds);

    String validityPeriodInMinutes = uri.getQueryParameter(VALIDITY_PERIOD_MINUTES_KEY);
    assertNull(validityPeriodInMinutes);
  }*/
}