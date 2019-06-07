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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * This class initiates the checkout experience using the web-based SRCi implementation.
 *
 * Merchant-specific parameters are required in order to successfully complete checkout.
 * These parameters are provided in the {@link CommerceConfig} object. If invalid values are
 * provided in this configuration, checkout will likely fail.
 *
 * Transaction-specific parameters are required when initiating the checkout, such as the
 * amount and currency of the transaction.
 *
 * Existing configurations which were set when onboarding with SRC can be overridden during
 * checkout by setting these parameters as part of the {@link CheckoutRequest}. This can include
 * overriding the supported cryptogram types or whether or not CVC2 is supported.
 *
 * When checkout is completed, the {@code Activity} provided at checkout will receive a result with
 * the {@code status} and {@code transactionId} for this transaction.
 *
 * @author Bret Deasy
 */
public class CommerceWebSdk {
  public static final int COMMERCE_REQUEST_CODE = 0x100;
  public static final String COMMERCE_TRANSACTION_ID = "transactionId";
  public static final String COMMERCE_STATUS = "status";
  private final CommerceConfig commerceConfig;

  public CommerceWebSdk(CommerceConfig configuration) {
    this.commerceConfig = configuration;
  }

  /**
   * Initiates checkout with commerce web sdk implementation with provided
   * {@code CheckoutRequest} and upon completion the result will be received by {@code Activity}.
   *
   * @param request request data to perform checkout
   * @param context activity to receive result from SDK
   */
  public void checkout(@NonNull CheckoutRequest request, Context context) {
    String url = SrcCheckoutUrlUtil.getCheckoutUrl(commerceConfig, request);

    Intent checkoutIntent = new Intent(context, WebCheckoutActivity.class).putExtra(
        WebCheckoutActivity.CHECKOUT_URL_EXTRA, url)
        .putExtra(WebCheckoutActivity.CALLBACK_SCHEME_EXTRA, commerceConfig.getScheme());

    if (context instanceof Activity) {
      ((Activity) context).startActivityForResult(checkoutIntent, COMMERCE_REQUEST_CODE);
    } else {
      context.startActivity(checkoutIntent);
    }
  }
}
