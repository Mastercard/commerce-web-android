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
 * This class will be used to listen for the {@link MasterpassPaymentMethod} object to be returned
 * by the server. This will contain paymentMethodId which will be used during checkout.
 */
public interface PaymentMethodCallback {

  /**
   * This method will be invoked once the SDK call finishes. It will include the paymentMethodId to
   * be used by the
   * merchant during mex flow.
   *
   * @param masterpassPaymentMethod This will contain information about what payment method was
   * selected. It will have the paymentId ,
   */
  void onPaymentMethodAdded(MasterpassPaymentMethod masterpassPaymentMethod);

  /**
   * Merchant will provide {@link AddPaymentMethodRequest} while adding Payment method through SDK.
   */
  AddPaymentMethodRequest getPaymentMethodRequest();

  /**
   * Notify Merchant on Add Payment Method failure.
   *
   * @param masterpassError add payment method error
   */
  void onFailure(MasterpassError masterpassError);
}
