package com.mastercard.testapp.domain.masterpass;

import com.mastercard.mp.checkout.MasterpassButton;
import com.mastercard.mp.checkout.MasterpassError;
import com.mastercard.testapp.data.device.MerchantPaymentMethod;
import java.util.List;

/**
 * Created by sfarias on 13-10-17.
 */
public interface MasterpassSdkInterface {

  /**
   * The interface Get from masterpass sdk.
   */
  interface GetFromMasterpassSdk {
    /**
     * Sdk response success.
     */
    void sdkResponseSuccess();

    /**
     * Sdk response error.
     *
     * @param error the error
     */
    void sdkResponseError(String error);
  }

  /**
   * The interface Get from masterpass sdk button.
   */
  interface GetFromMasterpassSdkButton {
    /**
     * Sdk response success.
     *
     * @param masterpassButton the masterpass button
     */
    void sdkResponseSuccess(MasterpassButton masterpassButton);

    /**
     * Sdk response error.
     */
    void sdkResponseError();
  }

  /**
   * Interface to get payment method callback
   */
  interface GetMasterpassPaymentMethod {

    /**
     * Sdk response success
     */
    void sdkResponseSuccess(List<MerchantPaymentMethod> paymentMethodList);

    /**
     * Sdk response error
     */
    void sdkResponseError(MasterpassError masterpassError);
  }

  /**
   * Interface for delete payment method callback
   */
  interface DeleteMasterpassPaymentMethod {

    /**
     * Success callback
     */
    void sdkResponseSuccess();

    /**
     * Error callback
     */
    void sdkResponseError();
  }

  interface SaveSelectedPaymentMethod {

    void sdkResponseSuccess(String msg);

    void sdkResponseError(String error);
  }
}
