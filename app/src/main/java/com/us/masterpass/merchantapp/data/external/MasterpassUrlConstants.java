package com.us.masterpass.merchantapp.data.external;

/**
 * Created by Sebastian Farias on 09-10-17.
 */
public class MasterpassUrlConstants {

    private MasterpassUrlConstants() {
    }

    /**
     * The Url api get items.
     */
    static String URL_API_GET_ITEMS =
            "http://java.mpass.moofwd.com/api/java/product/list";
    /**
     * The Url image repo.
     */
    static String URL_IMAGE_REPO =
            "http://java.mpass.moofwd.com";
    /**
     * The Url api post confirmation.
     */
    static String URL_API_POST_CONFIRMATION =
            "http://java.mpass.moofwd.com/api/java/masterpass/checkout/standard/callback";
    /**
     * The Url api post complete.
     */
    static String URL_API_POST_COMPLETE =
            "http://java.mpass.moofwd.com/api/java/masterpass/transaction/postback";
    /**
     * The Url api get login.
     */
    static String URL_API_GET_LOGIN =
            "http://java.mpass.moofwd.com/api/java/user/auth?";
    /**
     * The Url api post precheckout.
     */
    static String URL_API_POST_PRECHECKOUT =
            "http://java.mpass.moofwd.com/api/java/masterpass/checkout/precheckout";
    /**
     * The Url api post checkout.
     */
    static String URL_API_POST_CHECKOUT =
            "http://java.mpass.moofwd.com/api/java/masterpass/checkout/express";
    /**
     * The Url api post pairing.
     */
    static String URL_API_POST_PAIRING =
            "http://java.mpass.moofwd.com/api/java/masterpass/pairing/connect/callback";
}
