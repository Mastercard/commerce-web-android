package com.us.masterpass.merchantapp.data.external;

import com.us.masterpass.merchantapp.domain.model.LoginObject;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import java.util.Map;

/**
 * Created by Sebastian Farias on 10/29/17.
 */
public interface MasterpassDataSource {
    /**
     * The interface Load data confirmation callback.
     */
    interface LoadDataConfirmationCallback {
        /**
         * On data confirmation.
         *
         * @param masterpassConfirmationObject the masterpass confirmation object
         * @param expressCheckoutEnable        the express checkout enable
         */
        void onDataConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
            boolean expressCheckoutEnable);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface Load data login callback.
     */
    interface LoadDataLoginCallback {
        /**
         * On data login.
         *
         * @param loginObject the login object
         */
        void onDataLogin(LoginObject loginObject);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface Pairing id callback.
     */
    interface PairingIdCallback {
        /**
         * On pairing.
         */
        void onPairing();

        /**
         * On pairing error.
         */
        void onPairingError();
    }

    /**
     * Gets data confirmation.
     *
     * @param checkoutData          the checkout data
     * @param expressCheckoutEnable the express checkout enable
     * @param callback              the callback
     */
    void getDataConfirmation(Map<String, Object> checkoutData, boolean expressCheckoutEnable,
        LoadDataConfirmationCallback callback);

    /**
     * Send confirmation.
     *
     * @param masterpassConfirmationObject the masterpass confirmation object
     * @param callback                     the callback
     */
    void sendConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
        LoadDataConfirmationCallback callback);

    /**
     * Express checkout.
     *
     * @param masterpassConfirmationObject the masterpass confirmation object
     * @param callback                     the callback
     */
    void expressCheckout(MasterpassConfirmationObject masterpassConfirmationObject,
        LoadDataConfirmationCallback callback);

    /**
     * Do login.
     *
     * @param username the username
     * @param password the password
     * @param callback the callback
     */
    void doLogin(String username, String password, LoadDataLoginCallback callback);

    /**
     * Gets pairing id.
     *
     * @param checkoutData the checkout data
     * @param callback     the callback
     */
    void getPairingId(Map<String, Object> checkoutData, PairingIdCallback callback);
}
