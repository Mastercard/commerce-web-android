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

import android.support.annotation.NonNull;
import com.mastercard.commerce.Validate;
import java.util.List;
import java.util.Map;

/**
 * When checkout is initiated, the SDK will request a object in order to
 * complete the transaction. This request object will be used to create a bundle and pass to Wallet
 * SDK
 */

public class MasterpassCheckoutRequest {

  private final Amount amount;
  private final Tokenization tokenization;
  private final String cartId;
  private final List<NetworkType> allowedNetworkTypes;
  private final boolean isShippingRequired;
  private String merchantName;
  private String merchantLocale;
  private String merchantUserId;
  private String checkoutId;
  private boolean suppress3Ds;
  private Map<String, Object> extensionPoint;
  private String shippingProfileId;
  private String callBackUrl;
  private boolean cvc2support;
  private int validityPeriodMinutes;

  private MasterpassCheckoutRequest(Builder builder) {
    this.merchantName = builder.merchantName;
    this.amount = builder.amount;
    this.tokenization = builder.tokenization;
    this.cartId = builder.cartId;
    this.allowedNetworkTypes = builder.allowedNetworkTypes;
    this.isShippingRequired = builder.isShippingRequired;
    this.merchantLocale = builder.merchantLocale;
    this.merchantUserId = builder.merchantUserId;
    this.extensionPoint = builder.extensionPoint;
    this.checkoutId = builder.checkoutId;
    this.suppress3Ds = builder.suppress3Ds;
    this.shippingProfileId = builder.shippingProfileId;
    this.callBackUrl = builder.callBackUrl;
    this.cvc2support = builder.cvc2support;
    this.validityPeriodMinutes = builder.validityPeriodMinutes;
  }

  @Override public String toString() {
    return "MasterpassCheckoutRequest{"
        + "amount="
        + amount
        + ", cartId='"
        + cartId
        + '\''
        + ", allowedNetworkTypes="
        + allowedNetworkTypes
        + ", isShippingRequired="
        + isShippingRequired
        + ", merchantName='"
        + merchantName
        + '\''
        + ", merchantLocale='"
        + merchantLocale
        + '\''
        + ", merchantUserId='"
        + merchantUserId
        + '\''
        + ", checkoutId='"
        + checkoutId
        + '\''
        + ", suppress3Ds="
        + suppress3Ds
        + ", extensionPoint="
        + extensionPoint
        + ", shippingProfileId='"
        + shippingProfileId
        + '\''
        + ", callBackUrl='"
        + callBackUrl
        + '\''
        + '}';
  }

  public Amount getAmount() {
    return amount;
  }

  public Tokenization getTokenization() {
    return tokenization;
  }

  public String getCartId() {
    return cartId;
  }

  public List<NetworkType> getAllowedNetworkTypes() {
    return allowedNetworkTypes;
  }

  public boolean isShippingRequired() {
    return isShippingRequired;
  }

  public String getMerchantLocale() {
    return merchantLocale;
  }

  public String getCheckoutId() {
    return checkoutId;
  }

  public Map<String, Object> getExtensionPoint() {
    return extensionPoint;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public boolean isSuppress3Ds() {
    return suppress3Ds;
  }

  public String getShippingProfileId() {
    return shippingProfileId;
  }

  public String getCallBackUrl() {
    return callBackUrl;
  }

  public String getMerchantUserId() {
    return merchantUserId;
  }

  public boolean isCvc2support() {
    return cvc2support;
  }

  public int getValidityPeriodMinutes() {
    return validityPeriodMinutes;
  }

  public static final class Builder {
    private String merchantName;
    private Amount amount;
    private Tokenization tokenization;
    private String cartId;
    private List<NetworkType> allowedNetworkTypes;
    private boolean isShippingRequired;
    private String merchantLocale;
    private String checkoutId;
    private Map<String, Object> extensionPoint;
    private String merchantUserId;
    private boolean suppress3Ds;
    private String shippingProfileId;
    private String callBackUrl;
    private boolean cvc2support;
    private int validityPeriodMinutes;

    public Builder() {
      //Empty default constructor
    }

    /**
     * @param amount {@link Amount} specifies total amount charged by merchant and currency code
     * @return {@link Builder} object
     */
    public Builder setAmount(Amount amount) {
      this.amount = amount;
      return this;
    }

    /**
     * @param tokenization {@link Tokenization } Tokenization provides the ability of passing
     * unpredictable number
     * @return {@link Builder} object
     */
    public Builder setTokenization(Tokenization tokenization) {
      this.tokenization = tokenization;
      return this;
    }

    /**
     * @param cartId {@link String } provides randomly generated cartId while checkout request
     * @return {@link Builder} object
     */
    public Builder setCartId(String cartId) {
      this.cartId = cartId;
      return this;
    }

    /**
     * @param checkoutId {@link String } based on user checkout environment and configuration,
     * checkoutId will be generated
     * @return {@link Builder} object
     */

    public Builder setCheckoutId(String checkoutId) {
      this.checkoutId = checkoutId;
      return this;
    }

    /**
     * @param allowedCardList {@link List<NetworkType>} This provides list of allowed CardTypes
     * by
     * merchant and the valid types are: MASTER, VISA, AMEX, DISCOVER, DINERS
     * @return {@link Builder} object
     */

    public Builder setAllowedNetworkTypes(@NonNull List<NetworkType> allowedCardList) {
      this.allowedNetworkTypes = allowedCardList;
      return this;
    }

    /**
     * @param isShippingDestinationRequired {@link Boolean } this provides the requirement of the
     * shipping which user specifies
     * @return {@link Builder} object
     */

    public Builder isShippingRequired(boolean isShippingDestinationRequired) {
      this.isShippingRequired = isShippingDestinationRequired;
      return this;
    }

    /**
     * @param merchantLocale {@link String} this specifies the merchant locale
     * @return {@link Builder} object
     */

    public Builder setMerchantLocale(String merchantLocale) {
      this.merchantLocale = merchantLocale;
      return this;
    }

    /**
     * @param extensionPoint {@link Map} this provides the extensionpoint
     * @return {@link Builder} object
     */
    public Builder setExtensionPoint(Map<String, Object> extensionPoint) {
      this.extensionPoint = extensionPoint;
      return this;
    }

    /**
     * @param merchantName {@link String } This specifies the name of a merchant
     * @return {@link Builder} object
     */
    public Builder setMerchantName(String merchantName) {
      this.merchantName = merchantName;
      return this;
    }

    public Builder setMerchantUserId(String merchantUserId) {
      this.merchantUserId = merchantUserId;

      return this;
    }

    /**
     * @param suppress3Ds {@link Map} this provides the suppress3Ds property
     * @return {@link Builder} object
     */
    public Builder setSuppress3Ds(boolean suppress3Ds) {
      this.suppress3Ds = suppress3Ds;
      return this;
    }

    /**
     * @param shippingProfileId for shipping location profile
     * @return {@link Builder} object
     */
    public Builder setShippingProfileId(String shippingProfileId) {
      this.shippingProfileId = shippingProfileId;
      return this;
    }

    /**
     * @return {@link Builder} object
     */
    public Builder setCallBackUrl(String callBackUrl) {
      this.callBackUrl = callBackUrl;
      return this;
    }

    /**
     * @return {@link Builder} object
     */
    public Builder setCvc2Support(boolean cvc2Support) {
      this.cvc2support = cvc2Support;
      return this;
    }

    /**
     * @return {@link Builder} object
     */
    public Builder setValidityPeriodMinutes(int validityPeriodMinutes) {
      this.validityPeriodMinutes = validityPeriodMinutes;
      return this;
    }

    public MasterpassCheckoutRequest build() {
      Validate.notEmpty(allowedNetworkTypes);
      Validate.notNull(MasterpassMerchantConfiguration.VALUE_CANNOT_BE_NULL, this.amount);
      Validate.notNull(MasterpassMerchantConfiguration.VALUE_CANNOT_BE_NULL, this.cartId);
      Validate.notNull(MasterpassMerchantConfiguration.VALUE_CANNOT_BE_NULL, this.checkoutId);
      Validate.notEmpty(allowedNetworkTypes);

      return new MasterpassCheckoutRequest(this);
    }
  }
}
