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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import com.mastercard.commerce.Validate;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Representing the merchant configuration for the SDK, the parameters used here will determine
 * future behavior of the SDK in addition to enabling use by the merchant application.
 */

public final class MasterpassMerchantConfiguration {
  public static final String DEV = "DEV";
  public static final String STAGE = "STAGE";
  public static final String STAGE1 = "STAGE1";
  public static final String STAGE2 = "STAGE2";
  public static final String STAGE3 = "STAGE3";
  public static final String ITF = "ITF";
  public static final String SANDBOX = "SANDBOX";
  public static final String PRODUCTION = "PRODUCTION";
  public static final String INT = "INT";
  static final String VALUE_CANNOT_BE_NULL = "Value cannot be null!";
  private final Context context;
  private final Locale locale;
  private final String checkoutUrl;
  private final String merchantName;
  private final boolean expressCheckoutEnabled;
  private final String checkoutId;
  private final String merchantCountryCode;
  private final List<NetworkType> allowedNetworkTypes;

  private MasterpassMerchantConfiguration(Builder builder) {
    this.context = builder.context;
    this.locale = builder.locale;
    this.checkoutUrl = builder.checkoutUrl;
    this.merchantName = builder.merchantName;
    this.expressCheckoutEnabled = builder.expressCheckoutEnabled;
    this.checkoutId = builder.checkoutId;
    this.merchantCountryCode = builder.merchantCountryCode;
    this.allowedNetworkTypes = builder.allowedNetworkTypes;
  }

  public Context getContext() {
    return context;
  }

  public Locale getLocale() {
    return locale;
  }

  @Environment public String getEnvironment() {
    return checkoutUrl;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public boolean isExpressCheckoutEnabled() {
    return expressCheckoutEnabled;
  }

  public String getCheckoutId() {
    return checkoutId;
  }

  public String getMerchantCountryCode() {
    return merchantCountryCode;
  }

  public List<NetworkType> getAllowedNetworkTypes() {
    return allowedNetworkTypes;
  }

  @Retention(RetentionPolicy.SOURCE) @StringDef({
      DEV, STAGE, STAGE1, STAGE2, STAGE3, ITF, SANDBOX, PRODUCTION, INT
  }) @interface Environment {
  }

  public static final class Builder {
    private String merchantName;
    private Context context;
    private Locale locale;
    private String checkoutUrl;
    private boolean expressCheckoutEnabled;
    private String checkoutId;
    private String merchantCountryCode;
    private List<NetworkType> allowedNetworkTypes;

    public Builder() {
      //default constructor for builder.
    }

    public Builder setContext(Context context) {
      this.context = context.getApplicationContext();
      return this;
    }

    public Builder setLocale(Locale locale) {
      this.locale = locale;
      return this;
    }

    public Builder setEnvironment(String checkoutUrl) {
      this.checkoutUrl = checkoutUrl;
      return this;
    }

    public Builder setMerchantName(String merchantName) {
      this.merchantName = merchantName;
      return this;
    }

    public Builder setExpressCheckoutEnabled(boolean expressCheckoutEnabled) {
      this.expressCheckoutEnabled = expressCheckoutEnabled;
      return this;
    }

    public Builder setCheckoutId(@NonNull String checkoutId) {
      this.checkoutId = checkoutId;
      return this;
    }

    public Builder setMerchantCountryCode(String merchantCountryCode) {
      this.merchantCountryCode = merchantCountryCode;
      return this;
    }

    public Builder setAllowedNetworkTypes(List<NetworkType> allowedNetworkTypes) {
      this.allowedNetworkTypes = allowedNetworkTypes;
      return this;
    }

    public MasterpassMerchantConfiguration build() {
      Validate.notNull(VALUE_CANNOT_BE_NULL, this.context);
      Validate.notNull(VALUE_CANNOT_BE_NULL, this.locale);
      Validate.notNull(VALUE_CANNOT_BE_NULL, this.checkoutUrl);

      return new MasterpassMerchantConfiguration(this);
    }
  }
}
