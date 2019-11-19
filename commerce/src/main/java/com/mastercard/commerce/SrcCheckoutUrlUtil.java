/* Copyright © 2019 Mastercard. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 =============================================================================*/

package com.mastercard.commerce;

import android.net.Uri;
import android.text.TextUtils;
import java.util.Locale;
import java.util.Set;

/**
 * Utility class to prepare SRC web checkout baseUrl.
 */
class SrcCheckoutUrlUtil {
  private static final String TAG = SrcCheckoutUrlUtil.class.getSimpleName();
  private static final String ALLOWED_CARD_TYPES_KEY = "allowedCardTypes";
  private static final String CALLBACK_URL_KEY = "callbackUrl";
  private static final String CART_ID_KEY = "cartId";
  private static final String CURRENCY_KEY = "currency";
  private static final String AMOUNT_KEY = "amount";
  private static final String CHECKOUT_ID_KEY = "checkoutid";
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
  private static final String CHANNEL_PLATFORM_KEY = "platform";
  private static final String CHANNEL_PLATFORM_ANDROID = "android";
  private static final String SDK_VERSION_KEY = "sdkVersion";
  private static final String PAYMENT_METHOD_KEY = "paymentmethod";

  private SrcCheckoutUrlUtil() {
  }

  /**
   * Returns the SRC checkout baseUrl to perform web checkout.
   *
   * @param commerceConfig configuration that holds necessary parameters to construct baseUrl
   * @param checkoutRequest checkout request that holds necessary parameters to construct baseUrl
   * @return string baseUrl that initiates SRC web checkout
   */
  static String getCheckoutUrl(CommerceConfig commerceConfig, CheckoutRequest checkoutRequest) {

    Uri.Builder uriBuilder = new Uri.Builder().encodedPath(commerceConfig.getCheckoutUrl());

    appendQueryParameter(uriBuilder, CHECKOUT_ID_KEY, commerceConfig.getCheckoutId());
    appendQueryParameter(uriBuilder, CART_ID_KEY, checkoutRequest.getCartId());
    appendQueryParameter(uriBuilder, AMOUNT_KEY, Double.toString(checkoutRequest.getAmount()));
    appendQueryParameter(uriBuilder, CURRENCY_KEY, checkoutRequest.getCurrency());
    appendQueryParameter(uriBuilder, CALLBACK_URL_KEY, checkoutRequest.getCallbackUrl());
    appendQueryParameter(uriBuilder, ALLOWED_CARD_TYPES_KEY, commerceConfig.getAllowedCardTypes());
    appendQueryParameter(uriBuilder, SHIPPING_LOCATION_PROFILES_KEY,
        checkoutRequest.getShippingLocationProfile());
    appendQueryParameter(uriBuilder, SUPPRESS_3DS_KEY, checkoutRequest.isSuppress3Ds());
    appendQueryParameter(uriBuilder, SUPPRESS_SHIPPING_KEY,
        String.valueOf(checkoutRequest.isSuppressShippingAddress()));
    appendQueryParameter(uriBuilder, LOCALE_KEY, commerceConfig.getLocale().toString());
    appendQueryParameter(uriBuilder, UNPREDICTABLE_NUMBER_KEY,
        checkoutRequest.getUnpredictableNumber());
    appendQueryParameter(uriBuilder, CVC2_SUPPORT_KEY, checkoutRequest.isCvc2Support());
    appendQueryParameter(uriBuilder, VALIDITY_PERIOD_MINUTES_KEY,
        checkoutRequest.getValidityPeriodMinutes());
    appendQueryParameter(uriBuilder, CHANNEL_KEY, CHANNEL_MOBILE);
    appendQueryParameter(uriBuilder, CHANNEL_PLATFORM_KEY, CHANNEL_PLATFORM_ANDROID);
    appendQueryParameter(uriBuilder, SDK_VERSION_KEY, BuildConfig.VERSION_NAME);

    if (null != checkoutRequest.getCryptoOptions() && !checkoutRequest.getCryptoOptions()
        .isEmpty()) {
      for (CryptoOptions cryptoOptions : checkoutRequest.getCryptoOptions()) {
        if (Mastercard.CARD_TYPE.equals(cryptoOptions.getCardType()) && !cryptoOptions.getFormat()
            .isEmpty()) {
          appendQueryParameter(uriBuilder, CRYPTO_FORMAT_MASTER_KEY,
              TextUtils.join(",", cryptoOptions.getFormat()));
        } else if (Visa.CARD_TYPE.equals(cryptoOptions.getCardType()) && !cryptoOptions.getFormat()
            .isEmpty()) {
          appendQueryParameter(uriBuilder, CRYPTO_FORMAT_VISA_KEY,
              TextUtils.join(",", cryptoOptions.getFormat()));
        }
      }
    }

    return uriBuilder.build().toString();
  }

  static String getDynamicButtonUrl(String imageUrl, String checkoutId,
      Set<CardType> allowedCardTypes, Locale locale) {
    Uri.Builder uriBuilder = new Uri.Builder().encodedPath(imageUrl);
    appendQueryParameter(uriBuilder, LOCALE_KEY, locale.toString());
    appendQueryParameter(uriBuilder, PAYMENT_METHOD_KEY, allowedCardTypes);
    appendQueryParameter(uriBuilder, CHECKOUT_ID_KEY, checkoutId);
    return uriBuilder.build().toString();
  }

  /**
   * Appends the provided query parameter to Uri builder if query parameter value is not null or
   * empty.
   */
  private static void appendQueryParameter(Uri.Builder builder, String key, String value) {
    if (!TextUtils.isEmpty(value)) {
      builder.appendQueryParameter(key, value);
    }
  }

  private static void appendQueryParameter(Uri.Builder builder, String key, Integer value) {
    if (value == null) {
      return;
    }

    appendQueryParameter(builder, key, String.valueOf(value));
  }

  private static void appendQueryParameter(Uri.Builder builder, String key, Boolean value) {
    if (value == null) {
      return;
    }

    appendQueryParameter(builder, key, String.valueOf(value));
  }

  private static void appendQueryParameter(Uri.Builder builder, String key, Iterable values) {
    if (values == null || !values.iterator().hasNext()) {
      return;
    }

    appendQueryParameter(builder, key, TextUtils.join(",", values));
  }
}
