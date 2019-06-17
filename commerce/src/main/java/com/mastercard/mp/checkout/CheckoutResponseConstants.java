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

package com.mastercard.mp.checkout;

/**
 * Static fields used to get response values from {@code checkout response bundle}
 */

public final class CheckoutResponseConstants {
  public static final String TRANSACTION_ID = "TransactionId";
  public static final String PAIRING_TRANSACTION_ID = "PairingTransactionId";
  public static final String CHECKOUT_RESOURCE_URL_ID = "CheckoutResourceUrl";
  static final String WALLET_ID = "WalletId";
  static final String WALLET_LOCALE = "WalletLocale";
  static final String TRANSACTION_CARD_BRAND = "TransactionCardBrand";
  static final String CANCEL_CHECKOUT = "cancelCheckout";
  static final String CANCEL_CHECKOUT_WITH_ERROR = "cancelCheckoutWithError";
  static final String FLOW = "flow";
  public static final int APP_TO_WEB = 0x001;
  public static final int PAIRING = 0x004;

  private CheckoutResponseConstants() {
  }
}
