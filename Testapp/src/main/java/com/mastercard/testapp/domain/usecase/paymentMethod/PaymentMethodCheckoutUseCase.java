package com.mastercard.testapp.domain.usecase.paymentMethod;

import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkInterface;

public class PaymentMethodCheckoutUseCase {

  /**
   * Usecase to complete checkout
   *
   * @param paymentMethodName selected payment method
   * @param callback {@link MasterpassSdkInterface.GetFromMasterpassSdk}
   */
  public static void completeCheckout(String paymentMethodName,
      final MasterpassSdkInterface.GetFromMasterpassSdk callback) {
    MasterpassSdkCoordinator.getInstance()
        .checkout(paymentMethodName, new MasterpassSdkInterface.GetFromMasterpassSdk() {
          @Override public void sdkResponseSuccess() {
            //not needed yet
          }

          @Override public void sdkResponseError(String error) {
            callback.sdkResponseError(error);
          }
        });
  }
}
