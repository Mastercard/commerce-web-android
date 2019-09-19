package com.mastercard.mp.checkout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class UserSetupTest {

  private String phoneNumber = "phoneNumber";
  private String email = "email";

  @Test public void testSetup() {

    UserSetup userSetup = new UserSetup();
    userSetup.setEmail(email);
    userSetup.setPhoneNumber(phoneNumber);

    assertEquals(phoneNumber, userSetup.getPhoneNumber());
    assertEquals(email, userSetup.getEmail());
  }
}
