package com.mastercard.mp.switchservices.checkout;

/**
 * Request object to perform express checkout
 */

public class ExpressCheckoutRequest {
  private final String checkoutId;
  private final String pairingId;
  private final String preCheckoutTransactionId;
  private final Double amount;
  private final String currency;
  private final String cardId;
  private final String shippingAddressId;
  private final boolean digitalGoods;

  private ExpressCheckoutRequest(Builder builder) {
    this.checkoutId = builder.checkoutId;
    this.pairingId = builder.pairingId;
    this.preCheckoutTransactionId = builder.preCheckoutTransactionId;
    this.amount = builder.amount;
    this.currency = builder.currency;
    this.cardId = builder.cardId;
    this.shippingAddressId = builder.shippingAddressId;
    this.digitalGoods = builder.digitalGoods;
  }

  public String getCheckoutId() {
    return checkoutId;
  }

  public String getPairingId() {
    return pairingId;
  }

  public String getPreCheckoutTransactionId() {
    return preCheckoutTransactionId;
  }

  public Double getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getCardId() {
    return cardId;
  }

  public String getShippingAddressId() {
    return shippingAddressId;
  }

  public static class Builder {
    private String checkoutId;
    private String pairingId;
    private String preCheckoutTransactionId;
    private Double amount;
    private String currency;
    private String cardId;
    private String shippingAddressId;
    private boolean digitalGoods;

    public Builder() {

    }

    public Builder(ExpressCheckoutRequest expressCheckoutRequest) {
      this.checkoutId = expressCheckoutRequest.checkoutId;
      this.pairingId = expressCheckoutRequest.pairingId;
      this.preCheckoutTransactionId = expressCheckoutRequest.preCheckoutTransactionId;
      this.amount = expressCheckoutRequest.amount;
      this.currency = expressCheckoutRequest.currency;
      this.cardId = expressCheckoutRequest.cardId;
      this.shippingAddressId = expressCheckoutRequest.shippingAddressId;
      this.digitalGoods = expressCheckoutRequest.digitalGoods;
    }

    public Builder setCheckoutId(String checkoutId) {
      this.checkoutId = checkoutId;

      return this;
    }

    public Builder setPairingId(String pairingId) {
      this.pairingId = pairingId;

      return this;
    }

    public Builder setPreCheckoutTransactionId(String preCheckoutTransactionId) {
      this.preCheckoutTransactionId = preCheckoutTransactionId;

      return this;
    }

    public Builder setAmount(Double amount) {
      this.amount = amount;

      return this;
    }

    public Builder setCurrency(String currency) {
      this.currency = currency;

      return this;
    }

    public Builder setCardId(String cardId) {
      this.cardId = cardId;

      return this;
    }

    public Builder setShippingAddressId(String shippingAddressId) {
      this.shippingAddressId = shippingAddressId;

      return this;
    }

    public Builder setDigitalGoods(boolean isDigitalGoods) {
      this.digitalGoods = isDigitalGoods;
      return this;
    }

    public ExpressCheckoutRequest build() {
      return new ExpressCheckoutRequest(this);
    }
  }
}
