package com.us.masterpass.merchantapp.data.external;

import com.us.masterpass.merchantapp.domain.masterpass.MasterpassApiConfiguration;

/**
 * Constants with URL used on application
 * <p>
 * Created by Sebastian Farias on 09-10-17.
 */
public class MasterpassUrlConstants {

  /**
   * Url to get items from API.
   */
  static final String URL_API_GET_ITEMS =
      MasterpassApiConfiguration.getApiBaseUrl() + "/mtt/product/list";
  /**
   * Url image repo domain.
   */
  static final String URL_IMAGE_REPO = MasterpassApiConfiguration.getApiBaseUrl();
  /**
   * Url to api post confirmation.
   */
  static final String URL_API_POST_CONFIRMATION =
      MasterpassApiConfiguration.getApiBaseUrl() + "/mtt/masterpass/checkout/standard/callback";
  /**
   * Url api post complete.
   */
  static final String URL_API_POST_COMPLETE =
      MasterpassApiConfiguration.getApiBaseUrl() + "/mtt/masterpass/transaction/postback";
  /**
   * Url to do login.
   */
  static final String URL_API_GET_LOGIN =
      MasterpassApiConfiguration.getApiBaseUrl() + "/mtt/user/auth?";
  /**
   * Url to do post precheckout.
   */
  static final String URL_API_POST_PRECHECKOUT =
      MasterpassApiConfiguration.getApiBaseUrl() + "/mtt/masterpass/checkout/precheckout";
  /**
   * Url to do post checkout.
   */
  static final String URL_API_POST_CHECKOUT =
      MasterpassApiConfiguration.getApiBaseUrl() + "/mtt/masterpass/checkout/express";
  /**
   * Url to get post pairing.
   */
  static final String URL_API_POST_PAIRING =
      MasterpassApiConfiguration.getApiBaseUrl() + "/mtt/masterpass/pairing/connect/callback";

  static final String URL_PRE_CHECKOUT =
      MasterpassApiConfiguration.getBaseUrl() + "/precheckoutdata/";

  static final String URL_POSTBACK_TRANSACTION =
      MasterpassApiConfiguration.getBaseUrl() + "/postback";

  static final String URL_PAYMENT_DATA_PAIRING =
      MasterpassApiConfiguration.getBaseUrl() + "/paymentdata/";

  static final String URL_PAYMENT_DATA_ENCRYPTED =
      MasterpassApiConfiguration.getBaseUrl() + "/encrypted-paymentdata/";

  static final String URL_EXPRESS_CHECKOUT =
      MasterpassApiConfiguration.getBaseUrl() + "/expresscheckout/";

  static final String URL_PAIRING_ID = MasterpassApiConfiguration.getBaseUrl() + "/pairingid";

  private MasterpassUrlConstants() {

  }
}