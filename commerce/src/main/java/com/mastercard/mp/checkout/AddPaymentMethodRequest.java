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
