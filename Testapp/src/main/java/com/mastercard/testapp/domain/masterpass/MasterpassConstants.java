package com.mastercard.testapp.domain.masterpass;

import com.mastercard.testapp.data.external.EnvironmentConstants;

/**
 * Constants used by masterpass
 * <p>
 * Created by Sebastian Farias on 10/29/17.
 */
public class MasterpassConstants {

  /**
   * The constant RESPONSE_API_CALL.
   */
  public static final String RESPONSE_API_CALL = "response";

  /**
   * The constant STATUS_API_CALL.
   */
  public static final String STATUS_API_CALL = "status";

  /**
   * The constant ENVIROMENT.
   */
  public static final String ENVIRONMENT = EnvironmentConstants.CURRENT_ENVIRONMENT_SDK;

  /**
   * The constant CHECKOUT_ID.
   */
  public static final String CHECKOUT_ID = EnvironmentConstants.CHECKOUT_ID;

  /**
   * The constant SIGNATURE.
   */
  //public static final String SIGNATURE = "LOCAL_TESTING";
  public static final String SIGNATURE =
      "gNLqiR5uuJN29lvf5N7rjNmpqbGtMJAMgqevVY0NTOXwjthewrjq3sKeoas/iMaIw/JoVUUPOnDaDh4QZdghKNMsZ49NJ7iTeeAE3j1PILRI28wL1GSO/YRdA1ZiSsLW3kPyzRkx4D3FEhIDk1lIG6u06q74bENSeoHP1idSvvWUqKe1XOiq+suj8Kj/aUz1IxFJXD/4JT/b7yPGMe5Le6PhI+S5s38wa6KXbXCv2Ey0kc9gDL3R7JAVT0VvV59XpwRJm/lOrs9ka7NCruSpizIFyrhjCCOGx2PQ7XrDn6vTTXyjxC6etH4et9zR/YwgXofOf6bJUWXbNEwPRj6jPQ==";

  /**
   * The constant PAIRING_WITHOUT_CHECKOUT_FLOW.
   */
  public static final String PAIRING_WITHOUT_CHECKOUT_FLOW = "PAIRING_WITHOUT_CHECKOUT";

  /**
   * The constant CONSUMER_KEY.
   */

  public static final String CONSUMER_KEY =
      "nFJ_8t6g4dJSV8SPDaOQpmEBCsOLr9iJA3e_ltxX8815574c!4ca14da0092a4ef082198ee9f071e93a0000000000000000";
  /**
   * The constant CART_ID.
   */
  public static final String CART_ID = "88448844";

  /**
   * The constant API_CALL_OAUTH_TOKEN.
   */
  public static final String API_CALL_OAUTH_TOKEN = "oauth_token";

  /**
   * The constant API_CALL_TRANSACTION_ID.
   */
  public static final String API_CALL_TRANSACTION_ID = "TransactionId";

  /**
   * The constant API_CALL_CHECKOUT_RESOURCE_URL.
   */
  public static final String API_CALL_CHECKOUT_RESOURCE_URL = "checkout_resource_url";

  /**
   * The constant API_CALL_PAIRING_TRANSACTION_ID.
   */
  public static final String API_CALL_PAIRING_TRANSACTION_ID = "PairingTransactionId";

  /**
   * The constant API_CALL_MASTERPASS_STATUS.
   */
  public static final String API_CALL_MASTERPASS_STATUS = "mpstatus";
}