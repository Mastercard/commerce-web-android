package com.mastercard.mp.switchservices.checkout;

import java.util.List;

/**
 * Response object returned from fetching preCheckoutData from switch
 */

public class PreCheckoutData {
  private List<PreCheckoutCard> cards;
  private List<PreCheckoutShippingAddress> shippingAddresses;
  private Contact contactInfo;
  private String preCheckoutTransactionId;
  private String consumerWalletId;
  private String pairingId;
  private String walletName;

  public List<PreCheckoutCard> getCards() {
    return cards;
  }

  public List<PreCheckoutShippingAddress> getShippingAddresses() {
    return shippingAddresses;
  }

  public Contact getContactInfo() {
    return contactInfo;
  }

  public String getPreCheckoutTransactionId() {
    return preCheckoutTransactionId;
  }

  public String getConsumerWalletId() {
    return consumerWalletId;
  }

  public String getPairingId() {
    return pairingId;
  }

  public String getWalletName() {
    return walletName;
  }
}
