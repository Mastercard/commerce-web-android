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
 * Interface to return a {@link CheckoutRequest} from the Merchant. {@link
 * #getCheckoutRequest(CheckoutRequestListener)} is called when the user has selected the {@link
 * CheckoutButton}. The implementer of this interface must call {@link
 * CheckoutRequestListener#setRequest(CheckoutRequest)} when the {@code CheckoutRequest} is ready.
 */

public interface CheckoutCallback {
  /**
   * The SDK will request a {@link CheckoutRequest} object in order to initiate the checkout
   * experience.
   *
   * @param listener interface to receive the checkout request to initiate the checkout experience
   */
  void getCheckoutRequest(CheckoutRequestListener listener);

  /**
   * Listener interface to receive the {@link CheckoutRequest} for this transaction
   */
  interface CheckoutRequestListener {

    /**
     * Set the checkout request to initiate the checkout experience
     *
     * @param request parameters required to initiate the checkout experience
     */
    void setRequest(CheckoutRequest request);
  }
}
