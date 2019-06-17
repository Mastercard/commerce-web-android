package com.us.masterpass.merchantapp.data.external;

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
     */
    void onDataConfirmation(MasterpassConfirmationObject masterpassConfirmationObject);

    /**
     * On data not available.
     */
    void onDataNotAvailable();
  }

  /**
   * Gets data confirmation.
   *
   * @param checkoutData the checkout data
   * @param callback the callback
   */
  void getDataConfirmation(Map<String, Object> checkoutData, LoadDataConfirmationCallback callback);

  /**
   * Send confirmation.
   *
   * @param masterpassConfirmationObject the masterpass confirmation object
   * @param callback the callback
   */
  void sendConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
      LoadDataConfirmationCallback callback);
}
