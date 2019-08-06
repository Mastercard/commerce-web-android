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

/**
 * Listener to receives updates related to checkout/pairing. When a checkout is initiated, a
 * CheckoutRequest
 * is requested from this listener. After checkout begins, this listener is used to notify of the
 * results of that checkout. If checkout/pairing is successfully completed
 */

public interface CheckoutCallback {
  /**
   * When checkout is initiated, the SDK will request a {@link CheckoutRequest} object in
   * order to
   * complete the transaction.
   *
   * @return {@link CheckoutRequest} for this transaction
   */
  CheckoutRequest getCheckoutRequest();
}
