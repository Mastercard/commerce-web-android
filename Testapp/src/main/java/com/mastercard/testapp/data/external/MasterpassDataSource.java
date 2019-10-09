package com.mastercard.testapp.data.external;

import com.mastercard.mp.switchservices.checkout.ExpressCheckoutRequest;
import com.mastercard.testapp.domain.model.LoginObject;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import java.security.PrivateKey;
import java.util.Map;

/**
 * Interface used by {@link MasterpassExternalDataSource}
 * <p>
 * Created by Sebastian Farias on 10/29/17.
 */
public interface MasterpassDataSource {

  /**
   * Get data confirmation info.
   *
   * @param checkoutData checkout data HashMap
   * @param expressCheckoutEnable is express checkout enable
   * @param callback callback {@link LoadDataConfirmationCallback}
   */
  void getDataConfirmation(Map<String, Object> checkoutData, boolean expressCheckoutEnable,
      LoadDataConfirmationCallback callback);

  /**
   * Send confirmation.
   *
   * @param masterpassConfirmationObject masterpass object {@link MasterpassConfirmationObject}
   * @param callback callback {@link LoadDataConfirmationCallback}
   */
  void sendConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
      LoadDataConfirmationCallback callback);

  /**
   * Express checkout.
   *
   * @param expressCheckoutRequest masterpass object {@link MasterpassConfirmationObject}
   * @param callback callback {@link LoadDataConfirmationCallback}
   */
  void expressCheckout(ExpressCheckoutRequest expressCheckoutRequest,
      LoadDataConfirmationCallback callback, PrivateKey privateKey);

  /**
   * Do login.
   *
   * @param username username for login
   * @param password password for login
   * @param callback callback {@link LoadDataLoginCallback}
   */
  void doLogin(String username, String password, LoadDataLoginCallback callback);

  /**
   * Get pairing id.
   *
   * @param checkoutData checkout data HashMap
   * @param callback callback {@link PairingIdCallback}
   */
  void getPairingId(Map<String, Object> checkoutData, PairingIdCallback callback);

  /**
   * The interface used as callback, called on load data confirmation.
   */
  interface LoadDataConfirmationCallback {
    /**
     * Called if the HTTP request was successful.
     *
     * @param masterpassConfirmationObject the masterpass confirmation object
     * @param expressCheckoutEnable if express checkout enable
     */
    void onDataConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
        boolean expressCheckoutEnable);

    /**
     * Called if the HTTP request has failed.
     */
    void onDataNotAvailable();
  }

  /**
   * The interface used as callback, called on login action.
   */
  interface LoadDataLoginCallback {
    /**
     * Called if the HTTP request was successful.
     *
     * @param loginObject login object
     */
    void onDataLogin(LoginObject loginObject);

    /**
     * Called if the HTTP request has failed.
     */
    void onDataNotAvailable();
  }

  /**
   * The interface used as callback, called on pairing flow.
   */
  interface PairingIdCallback {
    /**
     * On pairing successful.
     */
    void onPairing();

    /**
     * On pairing error.
     */
    void onPairingError();
  }
}
