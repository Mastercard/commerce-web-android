package com.mastercard.mp.checkout;

/**
 * This class will be used to listen for the {@link MasterpassPaymentMethod} object to be returned
 * by the server. This will contain paymentMethodId which will be used during checkout.
 */
public interface PaymentMethodCallback {

  /**
   * This method will be invoked once the SDK call finishes. It will include the paymentMethodId to
   * be used by the
   * merchant during mex flow.
   *
   * @param masterpassPaymentMethod This will contain information about what payment method was
   * selected. It will have the paymentId ,
   */
  void onPaymentMethodAdded(MasterpassPaymentMethod masterpassPaymentMethod);

  /**
   * Merchant will provide {@link AddPaymentMethodRequest} while adding Payment method through SDK.
   */
  AddPaymentMethodRequest getPaymentMethodRequest();

  /**
   * Notify Merchant on Add Payment Method failure.
   *
   * @param masterpassError add payment method error
   */
  void onFailure(MasterpassError masterpassError);
}
