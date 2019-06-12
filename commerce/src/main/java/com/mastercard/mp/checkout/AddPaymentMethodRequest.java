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

import java.util.List;

/**
 * Merchant will provide this information while adding Payment method.
 */

public class AddPaymentMethodRequest {
  private final List<NetworkType> allowedNetworkTypes;
  private final String checkoutId;
  private final String merchantUserId;

  public AddPaymentMethodRequest(List<NetworkType> allowedNetworkTypes, String checkoutId,
      String merchantUserId) {
    this.allowedNetworkTypes = allowedNetworkTypes;
    this.checkoutId = checkoutId;
    this.merchantUserId = merchantUserId;
  }

  public List<NetworkType> getAllowedNetworkTypes() {
    return allowedNetworkTypes;
  }

  public String getCheckoutId() {
    return checkoutId;
  }

  public String getMerchantUserId() {
    return merchantUserId;
  }

  @Override public String toString() {
    return "AddPaymentMethodRequest{"
        + "allowedNetworkTypes="
        + allowedNetworkTypes
        + ", checkoutId='"
        + checkoutId
        + '\''
        + ", merchantUserId='"
        + merchantUserId
        + '\''
        + '}';
  }
}
