package com.mastercard.mp.checkout;

/**
 * Static fields used to get response values from {@code checkout response bundle}
 */

public final class CheckoutResponseConstants {
  public static final String TRANSACTION_ID = "TransactionId";
  public static final String PAIRING_TRANSACTION_ID = "PairingTransactionId";
  public static final String CHECKOUT_RESOURCE_URL_ID = "CheckoutResourceUrl";
  static final String WALLET_ID = "WalletId";
  static final String WALLET_LOCALE = "WalletLocale";
  static final String TRANSACTION_CARD_BRAND = "TransactionCardBrand";
  static final String CANCEL_CHECKOUT = "cancelCheckout";
  static final String CANCEL_CHECKOUT_WITH_ERROR = "cancelCheckoutWithError";
  static final String FLOW = "flow";
  public static final int APP_TO_WEB = 0x001;
  public static final int PAIRING = 0x004;

  private CheckoutResponseConstants() {
  }
}
