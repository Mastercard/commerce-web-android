package com.mastercard.testapp.domain.masterpass;

import com.mastercard.mp.checkout.MasterpassMerchantConfiguration;

/**
 * Created by e076827 on 1/25/18.
 */

public class MasterpassApiConfiguration {

  private MasterpassApiConfiguration() {

  }

  public static String getApiBaseUrl() {
    String baseUrl = "";
    String temp = "DEV";
    switch (temp) {

      case MasterpassMerchantConfiguration.DEV:
        baseUrl = "http://ech-10-157-128-182.devcloud.mastercard.com";
        break;

      case MasterpassMerchantConfiguration.ITF:
        baseUrl = "https://stage.mastercard.int";
        break;

      case MasterpassMerchantConfiguration.STAGE1:
        baseUrl = "https://stage1.mastercard.int";
        break;

      case MasterpassMerchantConfiguration.STAGE2:
        baseUrl = "https://stage2.mastercard.int";
        break;

      case MasterpassMerchantConfiguration.STAGE3:
        baseUrl = "https://stage3.mastercard.int";
        break;

      case MasterpassMerchantConfiguration.SANDBOX:
        baseUrl = "https://sandbox.mastercard.int";
        break;

      default:

        break;
    }
    return baseUrl;
  }

  public static String getBaseUrl() {
    String baseUrl = "";
    switch (MasterpassConstants.ENVIRONMENT.toUpperCase()) {
      case MasterpassMerchantConfiguration.ITF:
        baseUrl = "https://stage.api.mastercard.com/masterpass";
        break;

      case MasterpassMerchantConfiguration.STAGE1:
        baseUrl = "https://stage1.api.mastercard.com/masterpass";
        break;

      case MasterpassMerchantConfiguration.STAGE2:
        baseUrl = "https://stage2.api.mastercard.com/masterpass";
        break;

      case MasterpassMerchantConfiguration.STAGE3:
        baseUrl = "https://stage3.api.mastercard.com/masterpass";
        break;

      case MasterpassMerchantConfiguration.SANDBOX:
        baseUrl = "https://sandbox.api.mastercard.com/masterpass";
        break;

      default:
        baseUrl = "https://api.mastercard.com/masterpass";
        break;
    }
    return baseUrl;
  }
}
