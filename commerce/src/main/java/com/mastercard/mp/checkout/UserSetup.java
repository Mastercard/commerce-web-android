package com.mastercard.mp.checkout;

/**
 * This class represents user details provided by merchant application
 */

public class UserSetup {
  private String phoneNumber;
  private String email;

  String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * This API is used by the user to set the phone number for Mex Registration
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  String getEmail() {
    return email;
  }

  /**
   * This API is used by the user to set the email id for Mex Registration
   */
  public void setEmail(String email) {
    this.email = email;
  }
}