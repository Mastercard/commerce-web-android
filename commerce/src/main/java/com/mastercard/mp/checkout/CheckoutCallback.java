package com.mastercard.mp.checkout;

/**
 * Interface to notify when checkout has completed with a specific status, either successfully, with
 * a failure, or by the user cancelling
 */

public interface CheckoutCallback {
  void onSuccess(PaymentData paymentData);

  void onFail(Error error);

  void onCancel();
}
