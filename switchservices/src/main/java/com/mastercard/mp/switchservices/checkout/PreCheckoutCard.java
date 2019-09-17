package com.mastercard.mp.switchservices.checkout;

import com.mastercard.mp.switchservices.SerializedName;

/**
 * Card used specifically for PreCheckoutData response
 */

public class PreCheckoutCard {
  private String brandName;
  private String lastFour;
  private String cardHolderName;
  private String expiryMonth;
  private String expiryYear;
  private String cardId;
  @SerializedName(name = "default") private boolean isDefault;

  public String getBrandName() {
    return brandName;
  }

  public String getLastFour() {
    return lastFour;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public String getExpiryMonth() {
    return expiryMonth;
  }

  public String getExpiryYear() {
    return expiryYear;
  }

  public String getCardId() {
    return cardId;
  }
}
