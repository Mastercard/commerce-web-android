package com.mastercard.mp.checkout;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

/**
 * When user invokes Add Payment method. SDK will return MasterpassPaymentMethod which will contain
 * the paymentMethodName , paymentWalletId, paymentMethodLogo , and paymentMethodName.  This class
 * will be passed as a param to SDK method.
 * Then merchant app will listen for onPaymentAdded to be invoked.
 */
public final class MasterpassPaymentMethod {

  private final Bitmap paymentMethodLogo;
  private final String paymentWalletId;
  private final String paymentMethodName;
  private final String pairingTransactionId;
  private final String paymentMethodLastFourDigits;

  /**
   * Constructor for MasterpassPaymentMethod.
   *
   * @param paymentMethodLogo returns PaymentMethod Icon as {@link Bitmap}.
   * @param paymentWalletId returns AppId of PaymentMethod selected by user.
   * @param paymentMethodName returns AppName of PaymentMethod selected by user.
   * @param pairingTransactionId optional field if express pairing is enabled
   */
  public MasterpassPaymentMethod(Bitmap paymentMethodLogo, String paymentWalletId,
      String paymentMethodName, String pairingTransactionId, String paymentMethodLastFourDigits) {
    this.paymentMethodLogo = paymentMethodLogo;
    this.paymentWalletId = paymentWalletId;
    this.paymentMethodName = paymentMethodName;
    this.pairingTransactionId = pairingTransactionId;
    this.paymentMethodLastFourDigits = paymentMethodLastFourDigits;
  }

  @Nullable public Bitmap getPaymentMethodLogo() {
    return paymentMethodLogo;
  }

  public String getPaymentWalletId() {
    return paymentWalletId;
  }

  public String getPaymentMethodName() {
    return paymentMethodName;
  }

  public String getPairingTransactionId() {
    return pairingTransactionId;
  }

  public String getPaymentMethodLastFourDigits() {
    return paymentMethodLastFourDigits;
  }
}
