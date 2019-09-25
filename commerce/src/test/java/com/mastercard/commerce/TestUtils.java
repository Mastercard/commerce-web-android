package com.mastercard.commerce;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

class TestUtils {
  static final double AMOUNT = 100.25;
  static final String SHIPPING_PROFILE = "shippingLocationProfile";
  static final String CHECKOUT_ID = "checkoutId";
  static final String CURRENCY = "CURRENCY";
  static final String CART_ID = "cartId";
  static final String URL = "https://stage.src.mastercard.com/srci";
  static final String SCHEME = "merchant";
  static final Locale LOCALE = new Locale("en", "US");
  static final String CALLBACK_URL = "callbackUrl";
  static final String URL_PARTS_SCHEME = "https";
  static final String URL_PARTS_AUTHORITY = "stage.src.mastercard.com";
  static final String URL_PARTS_PATH = "/srci";
  static final String CHANNEL = "mobile";
  static final Set<CardType> ALLOWED_CARD_TYPES;

  static {
    ALLOWED_CARD_TYPES = new HashSet<>();
    ALLOWED_CARD_TYPES.add(CardType.MASTER);
    ALLOWED_CARD_TYPES.add(CardType.VISA);
  }

  static final TestCheckoutRequestConfiguration configuration =
      new TestCheckoutRequestConfiguration();

  CommerceConfig getCommerceConfig() {
    return new CommerceConfig(LOCALE, CHECKOUT_ID, URL, ALLOWED_CARD_TYPES);
  }

  CheckoutRequest getCheckoutRequest() {
    CheckoutRequest.Builder requestBuilder = getRequestBuilder();

    if (configuration.hasSuppress3Ds) {
      requestBuilder.suppress3ds(Boolean.TRUE);
    }

    if (configuration.hasCvc2Support) {
      requestBuilder.cvc2Support(Boolean.TRUE);
    }

    if (configuration.hasShippingLocationProfile) {
      requestBuilder.shippingLocationProfile(SHIPPING_PROFILE);
    }

    if (configuration.hasValidityPeriodInMinutes) {
      requestBuilder.validityPeriodMinutes(15);
    }

    if (configuration.hasCallbackUrl) {
      requestBuilder.callbackUrl(CALLBACK_URL);
    }

    return requestBuilder
        .amount(AMOUNT)
        .cartId(CART_ID)
        .currency(CURRENCY)
        .build();
  }

  CheckoutRequest getCheckoutRequestWAllArguments() {
    CheckoutRequest.Builder requestBuilder = getRequestBuilder();

    requestBuilder.suppress3ds(Boolean.TRUE);
    requestBuilder.cvc2Support(Boolean.TRUE);
    requestBuilder.shippingLocationProfile(SHIPPING_PROFILE);
    requestBuilder.validityPeriodMinutes(15);
    requestBuilder.callbackUrl(CALLBACK_URL);

    return requestBuilder
        .amount(AMOUNT)
        .cartId(CART_ID)
        .currency(CURRENCY)
        .build();
  }


  private CheckoutRequest.Builder getRequestBuilder(){
    CheckoutRequest.Builder requestBuilder = new CheckoutRequest.Builder();

    Set<CardType> cardTypes = new LinkedHashSet<>();
    cardTypes.add(CardType.VISA);
    cardTypes.add(CardType.MASTER);

    //requestBuilder.allowedCardTypes(cardTypes);

    if (configuration.hasCryptoOptions) {
      Set<CryptoOptions> cryptoOptions = new LinkedHashSet<>();

      Set<Mastercard.MastercardFormat> mastercardFormatSet = new LinkedHashSet<>();
      mastercardFormatSet.add(Mastercard.MastercardFormat.UCAF);
      mastercardFormatSet.add(Mastercard.MastercardFormat.ICC);
      Mastercard mastercard = new Mastercard(mastercardFormatSet);
      cryptoOptions.add(mastercard);

      Set<Visa.VisaFormat> visaFormatSet = new LinkedHashSet<>();
      visaFormatSet.add(Visa.VisaFormat.TVV);
      Visa visa = new Visa(visaFormatSet);
      cryptoOptions.add(visa);

      requestBuilder.cryptoOptions(cryptoOptions);
    }
    return requestBuilder;
  }

  static class TestCheckoutRequestConfiguration {
    boolean hasCallbackUrl;
    boolean hasCryptoOptions;
    boolean hasCvc2Support;
    boolean hasShippingLocationProfile;
    boolean hasSuppress3Ds;
    boolean hasValidityPeriodInMinutes;

    TestCheckoutRequestConfiguration setHasCallbackUrl() {
      this.hasCallbackUrl = true;
      return this;
    }

    TestCheckoutRequestConfiguration setHasCryptoOptions() {
      this.hasCryptoOptions = true;
      return this;
    }

    TestCheckoutRequestConfiguration setHasCvc2Support() {
      this.hasCvc2Support = true;
      return this;
    }

    TestCheckoutRequestConfiguration setHasShippingLocationProfile() {
      this.hasShippingLocationProfile = true;
      return this;
    }

    TestCheckoutRequestConfiguration setHasSuppress3Ds() {
      this.hasSuppress3Ds = true;
      return this;
    }

    TestCheckoutRequestConfiguration setHasValidityPeriodInMinutes() {
      this.hasValidityPeriodInMinutes = true;
      return this;
    }
  }
}
