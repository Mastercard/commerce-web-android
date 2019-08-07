package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <p>Java class for AuthorizeCheckoutResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AuthorizeCheckoutResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MerchantCallbackURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StepupPending" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="OAuthVerifier" type="{http://www.w3.org/2001/XMLSchema}string"
 *         &lt;element name="CheckoutResourceUrl" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@Root(name = "AuthorizeCheckoutResponse", strict = false) public class AuthorizeCheckoutResponse {

  @Element(name = "MerchantCallbackURL") private String merchantCallbackURL;
  @Element(name = "StepupPending") private boolean stepupPending;
  @Element(name = "OAuthVerifier", required = false) private String oAuthVerifier;
  @Element(name = "PreCheckoutTransactionId", required = false) private String
      preCheckoutTransactionId;
  @Element(name = "ExtensionPoint", required = false) private String extensionPoint;
  @Element(name = "CheckoutResourceUrl", required = false) private String checkoutResourceUrl;

  /**
   * Gets the value of the merchantCallbackURL property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getMerchantCallbackURL() {
    return merchantCallbackURL;
  }

  /**
   * Sets the value of the merchantCallbackURL property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setMerchantCallbackURL(String value) {
    this.merchantCallbackURL = value;
  }

  /**
   * Gets the value of the stepupPending property.
   */
  public boolean isStepupPending() {
    return stepupPending;
  }

  /**
   * Sets the value of the stepupPending property.
   */
  public void setStepupPending(boolean value) {
    this.stepupPending = value;
  }

  /**
   * Gets the value of the oAuthVerifier property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getOAuthVerifier() {
    return oAuthVerifier;
  }

  /**
   * Sets the value of the oAuthVerifier property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setOAuthVerifier(String value) {
    this.oAuthVerifier = value;
  }

  public String getPreCheckoutTransactionId() {
    return preCheckoutTransactionId;
  }

  public void setPreCheckoutTransactionId(String preCheckoutTransactionId) {
    this.preCheckoutTransactionId = preCheckoutTransactionId;
  }

  public String getExtensionPoint() {
    return extensionPoint;
  }

  public void setExtensionPoint(String extensionPoint) {
    this.extensionPoint = extensionPoint;
  }

  public String getCheckoutResourceUrl() {
    return checkoutResourceUrl;
  }

  public void setCheckoutResourceUrl(String checkoutResourceUrl) {
    this.checkoutResourceUrl = checkoutResourceUrl;
  }
}
