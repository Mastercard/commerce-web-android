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
  private static volatile CommerceWebSdk instance;
  private ConfigurationManager configurationManager;

  synchronized public static CommerceWebSdk getInstance() {
    if (instance == null) {
      instance = new CommerceWebSdk();
    }

    return instance;
  }

  private CommerceWebSdk() {
    //private constructor to prevent instantiation
  }

  public void initialize(Context context, CommerceConfig configuration) {
    configurationManager = ConfigurationManager.getInstance();
    configurationManager.setContext(context);
    configurationManager.setConfiguration(configuration);
    CheckoutButtonManager.getInstance();
  }

  public CheckoutButton getCheckoutButton(final CheckoutCallback checkoutCallback) {
    CheckoutButtonManager checkoutButtonManager = CheckoutButtonManager.getInstance();

    return checkoutButtonManager.getCheckoutButton(
        new CheckoutButton.CheckoutButtonClickListener() {
          @Override public void onClick() {
            checkout(checkoutCallback.getCheckoutRequest());
          }
        });
  }

  /**
   * Initiates checkout with commerce web sdk implementation with provided
   * {@code CheckoutRequest} and upon completion the result will be received by {@code Activity}.
   *
   * @param request request data to perform checkout
   * @deprecated This method will be deprecated; please use {@link #getCheckoutButton(CheckoutCallback)}}.
   */
  public void checkout(@NonNull CheckoutRequest request) {
    configurationManager.setCheckoutRequest(request);
    Context context = configurationManager.getContext();

    if (ErrorUtil.isNetworkConnected(context)) {
      String url =
          SrcCheckoutUrlUtil.getCheckoutUrl(configurationManager.getConfiguration(), request);
      launchWebCheckoutActivity(context, url);
    } else {
      launchErrorActivity(context);
    }
  }

  private void launchErrorActivity(Context context) {
    Intent errorIntent = new Intent(context, ErrorActivity.class);

    context.startActivity(errorIntent);
  }

  private void launchWebCheckoutActivity(Context context, String checkoutUrl) {
    Intent checkoutIntent = new Intent(context, WebCheckoutActivity.class)
        .putExtra(WebCheckoutActivity.CHECKOUT_URL_EXTRA, checkoutUrl)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    context.startActivity(checkoutIntent);
  }
}
