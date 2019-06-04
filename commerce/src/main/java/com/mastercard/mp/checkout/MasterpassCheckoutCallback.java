package com.mastercard.mp.checkout;

import android.os.Bundle;

/**
 * Listener to receives updates related to checkout/pairing. When a checkout is initiated, a
 * CheckoutRequest
 * is requested from this listener. After checkout begins, this listener is used to notify of the
 * results of that checkout. If checkout/pairing is successfully completed, {@link
 * #onCheckoutComplete(Bundle)} is called with the results, otherwise {@link
 * #onCheckoutError(MasterpassError)} is called with the appropriate error causing checkout to
 * fail.
 */

public interface MasterpassCheckoutCallback {
  /**
   * When checkout is initiated, the SDK will request a {@link MasterpassCheckoutRequest} object in
   * order to
   * complete the transaction.
   *
   * @return {@link MasterpassCheckoutRequest} for this transaction
   */
  MasterpassCheckoutRequest getCheckoutRequest();

  /**
   * Upon successful completion of this transaction, this method will be called to notify the
   * listener that checkout/pairing is completed. This method gives the application the opportunity
   * to
   * update the user with their transaction status.
   *
   * @param arguments results of the transaction that has just completed
   */
  void onCheckoutComplete(Bundle arguments);

  /**
   * If an error occurs while this checkout/pairing is being completed, this method is called with
   * the
   * specific error associated with the cause of the failure.
   *
   * @param error the reason for the checkout failure
   */
  void onCheckoutError(MasterpassError error);
}
