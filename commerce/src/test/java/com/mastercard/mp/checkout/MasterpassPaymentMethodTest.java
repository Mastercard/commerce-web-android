package com.mastercard.mp.checkout;

import android.graphics.Bitmap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class MasterpassPaymentMethodTest {

  @Mock private Bitmap paymentMethodLogo;
  private String paymentWalletId = "paymentWalletId";
  private String paymentMethodName = "paymentMethodName";
  private String pairingTransactionId = "pairingTransactionId";
  private String paymentMethodLastFourDigits = "paymentMethodLastFourDigits";

  @Test public void testPaymentMethod() {
    MasterpassPaymentMethod masterpassPaymentMethod =
        new MasterpassPaymentMethod(paymentMethodLogo, paymentWalletId, paymentMethodName,
            pairingTransactionId, paymentMethodLastFourDigits);

    assertEquals(paymentMethodLogo, masterpassPaymentMethod.getPaymentMethodLogo());
    assertEquals(paymentWalletId, masterpassPaymentMethod.getPaymentWalletId());
    assertEquals(paymentMethodName, masterpassPaymentMethod.getPaymentMethodName());
    assertEquals(pairingTransactionId, masterpassPaymentMethod.getPairingTransactionId());
    assertEquals(paymentMethodLastFourDigits,
        masterpassPaymentMethod.getPaymentMethodLastFourDigits());
  }
}
