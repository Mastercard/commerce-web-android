package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Data message sent from wallet library to wallet proxy.
 */
@Root(name = "AuthorizeMobileCheckoutRequest") public class AuthorizeMobileCheckoutRequest {
  @Element(name = "AuthorizeCheckoutRequest") private String authorizeCheckoutRequest;
  @Element(name = "WalletId") private String walletId;
  @Element(name = "AppId") private String appId;
  @Element(name = "AppVersion") private String appVersion;
  @Element(name = "EncryptionAlgorithm") private String encryptionAlgorithm;
  @Element(name = "WalletDataEncryptionKey") private String walletDataEncryptionKey;
  @Element(name = "WalletAppEncryptionPublicKey") private String walletAppEncryptionPublicKey;
  @Element(name = "DataEncryptionIV") private String dataEncryptionIV;

  /**
   * @return
   */
  public String getAuthorizeCheckoutRequest() {
    return authorizeCheckoutRequest;
  }

  public void setAuthorizeCheckoutRequest(String authorizeCheckoutRequest) {
    this.authorizeCheckoutRequest = authorizeCheckoutRequest;
  }

  /**
   * @return AES key generated while performing authorize checkout. This is generated for every
   * request.
   */
  public String getWalletDataEncryptionKey() {
    return walletDataEncryptionKey;
  }

  public void setWalletDataEncryptionKey(String walletDataEncryptionKey) {
    this.walletDataEncryptionKey = walletDataEncryptionKey;
  }

  /**
   * @return random 8 bytes generated for every request.
   */
  public String getDataEncryptionIV() {
    return dataEncryptionIV;
  }

  public void setDataEncryptionIV(String dataEncryptionIV) {
    this.dataEncryptionIV = dataEncryptionIV;
  }

  /**
   * @param walletId Application Id associated with the Wallet Application
   */
  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  /**
   * @return Application Id associated with the Wallet Application
   */
  public String getWalletId() {
    return walletId;
  }

  /**
   * @param walletAppEncryptionPublicKey Public key used to encrypt data received by Wallet
   */
  public void setWalletAppEncryptionPublicKey(String walletAppEncryptionPublicKey) {
    this.walletAppEncryptionPublicKey = walletAppEncryptionPublicKey;
  }

  /**
   * @return Public key used to encrypt data received by Wallet
   */
  public String getWalletAppEncryptionPublicKey() {
    return walletAppEncryptionPublicKey;
  }

  /**
   * @return Encryption Algorithm
   */
  public String getEncryptionAlgorithm() {
    return encryptionAlgorithm;
  }

  /**
   * @param encryptionAlgorithm Encryption Algorithm used to encrypt and decrypt message
   */
  public void setEncryptionAlgorithm(String encryptionAlgorithm) {
    this.encryptionAlgorithm = encryptionAlgorithm;
  }

  /**
   * @return id from wallet application used for transaction.
   */
  public String getAppId() {
    return appId;
  }

  /**
   * Setter for wallet application id.
   */
  public void setAppId(String appId) {
    this.appId = appId;
  }

  /**
   * @return wallet application version.
   */
  public String getAppVersion() {
    return appVersion;
  }

  /**
   * Setter for wallet application version.
   */
  public void setAppVersion(String walletAppVersion) {
    this.appVersion = walletAppVersion;
  }
}
