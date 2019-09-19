package com.mastercard.mp.checkout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.mastercard.mp.checkout.MasterpassError.ERROR_CODE_REQUEST_TIMEOUT;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class MasterpassErrorTest {

  private String errorMessage = "error";

  @Test public void testError() {
    MasterpassError error = new MasterpassError(ERROR_CODE_REQUEST_TIMEOUT, errorMessage);

    assertEquals(ERROR_CODE_REQUEST_TIMEOUT, error.code());
    assertEquals(errorMessage, error.message());
  }
}
