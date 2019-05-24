package com.us.masterpass.merchantapp.domain.masterpass;


/**
 * Created by Sebastian Farias on 10/29/17.
 */
public class CommerceConstants {

  private CommerceConstants() {
    }


  /**
     * URL to pass to SDK in order to render the SRCi web view
     */
    public static final String SRC_URL = "https://stage.src.mastercard.com";

    /**
     * URL scheme used by the SRCi web to callback to merchant application
     */
    public static final String CALLBACK_SCHEME = "com.mastercard.merchant";

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
    public static final String ENVIROMENT = "";
    /**
     * The constant SIGNATURE.
     */
    public static final String SIGNATURE = "LOCAL_TESTING";

    /**
     * The constant PAIRING_WITHOUT_CHECKOUT_FLOW.
     */
    public static final String PAIRING_WITHOUT_CHECKOUT_FLOW = "PAIRING_WITHOUT_CHECKOUT";

    /**
     * The constant MERCHANT_ID
     */
    public static final String MERCHANT_ID = "e19e5f516e3c45ad991e13c8af924f62";

    //checkout ID Perf
    // public static final String CHECKOUT_ID = "33483965e1d84b33a12834ce480b9d4f";
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
     * The constant API_PAIRING_URL.
     */
//API CALLS
    public static final String API_PAIRING_URL = "https://sandbox.api.mastercard.com/int/masterpass/";
    /**
     * The constant API_PAYMENT_DATA_PAIRING.
     */
    public static final String API_PAYMENT_DATA_PAIRING = "https://stage.api.mastercard.com/itf/masterpass/paymentdata";


    /**
     * The constant API_CALL_MASTERPASS_STATUS.
     */
    public static final String API_CALL_MASTERPASS_STATUS = "mpstatus";
}
