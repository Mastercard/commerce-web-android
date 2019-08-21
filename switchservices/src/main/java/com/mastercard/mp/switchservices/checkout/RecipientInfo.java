package com.mastercard.mp.switchservices.checkout;

/**
 * Recipient Info response data found in PreCheckoutData
 */

public class RecipientInfo {
  private String recipientName;
  private String recipientPhone;
  private String recipientEmailAddress;

  public String getRecipientName() {
    return recipientName;
  }

  public String getRecipientPhone() {
    return recipientPhone;
  }

  public String getRecipientEmailAddress() {
    return recipientEmailAddress;
  }
}
