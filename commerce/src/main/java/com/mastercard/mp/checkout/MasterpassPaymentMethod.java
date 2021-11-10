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

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import com.mastercard.commerce.CommerceWebSdk;

/**
 * When user invokes Add Payment method. SDK will return MasterpassPaymentMethod which will contain
 * the paymentMethodName , paymentWalletId, paymentMethodLogo , and paymentMethodName.  This class
 * will be passed as a param to SDK method.
 * Then merchant app will listen for onPaymentAdded to be invoked.
 *
 * @deprecated You should migrate your code to use {@link CommerceWebSdk} instead. All APIs available
 * in this package will be deprecated in a future release.
 */
@Deprecated public final class MasterpassPaymentMethod {

  private final Bitmap paymentMethodLogo;
  private final String paymentWalletId;
  private final String paymentMethodName;
  private final String pairingTransactionId;
  private final String paymentMethodLastFourDigits;

  /**
   * Constructor for MasterpassPaymentMethod.
   *
   * @param paymentMethodLogo returns PaymentMethod Icon as {@link Bitmap}.
   * @param paymentWalletId returns AppId of PaymentMethod selected by user.
   * @param paymentMethodName returns AppName of PaymentMethod selected by user.
   * @param pairingTransactionId optional field if express pairing is enabled
   */
  public MasterpassPaymentMethod(Bitmap paymentMethodLogo, String paymentWalletId,
      String paymentMethodName, String pairingTransactionId, String paymentMethodLastFourDigits) {
    this.paymentMethodLogo = paymentMethodLogo;
    this.paymentWalletId = paymentWalletId;
    this.paymentMethodName = paymentMethodName;
    this.pairingTransactionId = pairingTransactionId;
    this.paymentMethodLastFourDigits = paymentMethodLastFourDigits;
  }

  @Nullable public Bitmap getPaymentMethodLogo() {
    return paymentMethodLogo;
  }

  public String getPaymentWalletId() {
    return paymentWalletId;
  }

  public String getPaymentMethodName() {
    return paymentMethodName;
  }

  public String getPairingTransactionId() {
    return pairingTransactionId;
  }

  public String getPaymentMethodLastFourDigits() {
    return paymentMethodLastFourDigits;
  }
}
