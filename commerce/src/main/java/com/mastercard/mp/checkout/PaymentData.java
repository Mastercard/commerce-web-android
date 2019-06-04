package com.mastercard.mp.checkout;

/**
 * Wrapper object to send returned stored payment data confirmation from SRC to the merchant app
 */
public class PaymentData {

  private String transactionId;
  private String callbackUrl;

  private PaymentData() {
    // Empty constructor to hide default constructor
  }

  private PaymentData(Builder builder) {
    this.transactionId = builder.transactionId;
    this.callbackUrl = builder.callbackUrl;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }

  public static class Builder {
    private String transactionId;
    private String callbackUrl;

    public Builder transactionId(String transactionId) {
      this.transactionId = transactionId;
      return this;
    }

    public Builder callbackUrl(String callbackUrl) {
      this.callbackUrl = callbackUrl;
      return this;
    }

    public PaymentData build() {
      return new PaymentData(this);
    }
  }
}
