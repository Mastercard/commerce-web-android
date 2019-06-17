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

import java.util.Set;

/**
 * Data class that holds the request data that is used for initialize and perform the checkout. Use
 * the builder to instantiate a CheckoutRequest object with the required parameters.
 * Required fields:
 * • amount
 * • cartId
 * • currency
 *
 * Optional fields can be set to override existing merchant configurations:
 * • callbackUrl
 * • cryptoOptions
 * • cvc2Support
 * • shippingLocationProfile
 * • suppress3Ds
 * • validityPeriodInMinutes
 *
 * {@code suppressShippingAddress} will default to {@code false} if not set.
 * {@code unpredictableNumber} must be set if cryptogram is expected.
 */
public final class CheckoutRequest {
  /** the total cost amount of this transaction **/
  private final double amount;
  /** unique identifier for this shopping cart **/
  private final String cartId;
  /** currency format for which this transaction amount is calculated **/
  private final String currency;
  /** optional: set this property to override merchant configuration **/
  private final String callbackUrl;
  /** optional: set this property to override merchant configuration **/
  private final Set<CryptoOptions> cryptoOptions;
  /** optional: setBooleanValue to override merchant configuration **/
  private final Boolean cvc2Support;
  /** optional: set this property to override merchant configuration **/
  private final String shippingLocationProfile;
  /** optional: setBooleanValue to override merchant configuration **/
  private final Boolean suppress3Ds;
  /** boolean flag indicating if shipping is required for this transaction **/
  private final boolean suppressShippingAddress;
  /** optional: unpredictable number used to generate cryptogram **/
  private final String unpredictableNumber;
  /** optional: set this property to override merchant configuration **/
  private final Integer validityPeriodMinutes;

  private CheckoutRequest(Builder builder) {
    this.amount = builder.amount;
    this.callbackUrl = builder.callbackUrl;
    this.cartId = builder.cartId;
    this.cryptoOptions = builder.cryptoOptions;
    this.currency = builder.currency;
    this.cvc2Support = builder.cvc2Support;
    this.shippingLocationProfile = builder.shippingLocationProfile;
    this.suppress3Ds = builder.suppress3Ds;
    this.suppressShippingAddress = builder.suppressShippingAddress;
    this.unpredictableNumber = builder.unpredictableNumber;
    this.validityPeriodMinutes = builder.validityPeriodMinutes;
  }

  public double getAmount() {
    return amount;
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }

  public String getCartId() {
    return cartId;
  }

  public String getCurrency() {
    return currency;
  }

  public Boolean isCvc2Support() {
    return cvc2Support;
  }

  public String getShippingLocationProfile() {
    return shippingLocationProfile;
  }

  public Boolean isSuppress3Ds() {
    return suppress3Ds;
  }

  public boolean isSuppressShippingAddress() {
    return suppressShippingAddress;
  }

  public String getUnpredictableNumber() {
    return unpredictableNumber;
  }

  public Integer getValidityPeriodMinutes() {
    return validityPeriodMinutes;
  }


  public Set<CryptoOptions> getCryptoOptions() {
    return cryptoOptions;
  }

  public static class Builder {
    private double amount;
    private String callbackUrl;
    private String cartId;
    private Set<CryptoOptions> cryptoOptions;
    private String currency;
    private Boolean cvc2Support;
    private String shippingLocationProfile;
    private Boolean suppress3Ds;
    private boolean suppressShippingAddress;
    private String unpredictableNumber;
    private Integer validityPeriodMinutes;

    public Builder amount(double amount) {
      this.amount = amount;
      return this;
    }

    public Builder callbackUrl(String callbackUrl) {
      this.callbackUrl = callbackUrl;
      return this;
    }

    public Builder cartId(String cartId) {
      this.cartId = cartId;
      return this;
    }

    public Builder cryptoOptions(Set<CryptoOptions> cryptoOptions) {
      this.cryptoOptions = cryptoOptions;
      return this;
    }

    public Builder currency(String currency) {
      this.currency = currency;
      return this;
    }

    public Builder cvc2Support(Boolean cvc2Support) {
      this.cvc2Support = cvc2Support;
      return this;
    }

    public Builder shippingLocationProfile(String shippingLocationProfile) {
      this.shippingLocationProfile = shippingLocationProfile;
      return this;
    }

    public Builder suppress3ds(Boolean suppress3Ds) {
      this.suppress3Ds = suppress3Ds;
      return this;
    }

    public Builder suppressShippingAddress(boolean suppressShippingAddress) {
      this.suppressShippingAddress = suppressShippingAddress;
      return this;
    }

    public Builder unpredictableNumber(String unpredictableNumber) {
      this.unpredictableNumber = unpredictableNumber;
      return this;
    }

    public Builder validityPeriodMinutes(Integer validityPeriodMinutes) {
      this.validityPeriodMinutes = validityPeriodMinutes;
      return this;
    }

    public CheckoutRequest build() {
      Validate.validMinimum("amount", amount, 0);
      Validate.notNull("cartId", cartId);
      Validate.notNull("currency", currency);

      return new CheckoutRequest(this);
    }
  }
}
