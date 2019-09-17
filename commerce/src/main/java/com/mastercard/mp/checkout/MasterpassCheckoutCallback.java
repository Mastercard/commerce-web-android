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

import android.os.Bundle;
import com.mastercard.commerce.CommerceWebSdk;

/**
 * Listener to receives updates related to checkout/pairing. When a checkout is initiated, a
 * CheckoutRequest
 * is requested from this listener. After checkout begins, this listener is used to notify of the
 * results of that checkout. If checkout/pairing is successfully completed, {@link
 * #onCheckoutComplete(Bundle)} is called with the results, otherwise {@link
 * #onCheckoutError(MasterpassError)} is called with the appropriate error causing checkout to
 * fail.
 *
 * @deprecated You should migrate your code to use {@link CommerceWebSdk} instead. All APIs available
 * in this package will be deprecated in a future release.
 */

@Deprecated public interface MasterpassCheckoutCallback {
  /**
   * When checkout is initiated, the SDK will request a {@link MasterpassCheckoutRequest} object in
   * order to
   * complete the transaction.
   *
   * @return {@link MasterpassCheckoutRequest} for this transaction
   */
  MasterpassCheckoutRequest getCheckoutRequest();

  /**
   * Upon successful completion of this transaction, this method will be called to notify the
   * listener that checkout/pairing is completed. This method gives the application the opportunity
   * to
   * update the user with their transaction status.
   *
   * @param arguments results of the transaction that has just completed
   */
  void onCheckoutComplete(Bundle arguments);

  /**
   * If an error occurs while this checkout/pairing is being completed, this method is called with
   * the
   * specific error associated with the cause of the failure.
   *
   * @param error the reason for the checkout failure
   */
  void onCheckoutError(MasterpassError error);
}
