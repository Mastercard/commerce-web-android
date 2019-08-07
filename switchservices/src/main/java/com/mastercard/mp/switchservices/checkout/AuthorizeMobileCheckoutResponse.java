package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Data message to send to Merchant application when checkout transaction is completed.
 */

@Root(name = "AuthorizeMobileCheckoutResponse") public class AuthorizeMobileCheckoutResponse {
  @Element(name = "AppId") private String appId;
  @Element(name = "AppVersion") private String appVersion;
  @Element(name = "AuthorizeCheckoutResponse") private String authorizeCheckoutResponse;
  @Element(name = "DataEncryptionIV") private String dataEncryptionIV;
  @Element(name = "DataEncryptionKey") private String dataEncryptionKey;

  /**
   * @return wallet app ID
   */
  public String getAppId() {
    return appId;
  }

  /**
   * Setter for wallet app ID
   */
  public void setAppId(String appId) {
    this.appId = appId;
  }

  /**
   * @return wallet app version
   */
  public String getAppVersion() {
    return appVersion;
  }

  /**
   * Setter for wallet app version
   */
  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  /**
   * @return data encryption IV
   */
  public String getDataEncryptionIV() {
    return dataEncryptionIV;
  }

  /**
   * Setter for data encryption IV
   */
  public void setDataEncryptionIV(String dataEncryptionIV) {
    this.dataEncryptionIV = dataEncryptionIV;
  }

  /**
   * @return the encrypted response from server as Base64 string.
   */
  public String getAuthorizeCheckoutResponse() {
    return authorizeCheckoutResponse;
  }

  /**
   * Setter for authorize checkout response.
   */
  public void setAuthorizeCheckoutResponse(String authorizeCheckoutResponse) {
    this.authorizeCheckoutResponse = authorizeCheckoutResponse;
  }

  public String getDataEncryptionKey() {
    return dataEncryptionKey;
  }

  public void setDataEncryptionKey(String dataEncryptionKey) {
    this.dataEncryptionKey = dataEncryptionKey;
  }
}
