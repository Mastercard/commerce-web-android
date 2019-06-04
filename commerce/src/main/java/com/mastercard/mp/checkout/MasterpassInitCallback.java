package com.mastercard.mp.checkout;

/**
 * Listener interface to be notified of initialization updates (i.e. {@code onInitSuccess} and
 * {@code onFailure}
 */

public interface MasterpassInitCallback {
  /**
   * Notifies the listener that initialization has completed successfully. At this point the SDK is
   * in a stable state and all APIs should work as expected. Trying to perform any operations with
   * the SDK before receiving this notification will result in unexpected behavior.
   */
  void onInitSuccess();

  /**
   * Notifies the listener that an error has been encountered during initialization. After receiving
   * this, trying to use the SDK will result in unexpected behavior.
   *
   * @param error details of the cause of the error and potentially steps that can be taken to fix
   * the issue and reinitialize.
   */
  void onInitError(MasterpassError error);
}
