package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <p>Java class for Card complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Card">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BrandId" type="{}NonEmptyString"/>
 *         &lt;element name="BrandName" type="{}NonEmptyString"/>
 *         &lt;element name="AccountNumber" type="{}NonEmptyString"/>
 *         &lt;element name="BillingAddress" type="{}Address"/>
 *         &lt;element name="CardHolderName" type="{}NonEmptyString"/>
 *         &lt;element name="ExpiryMonth" type="{}Month" minOccurs="0"/>
 *         &lt;element name="ExpiryYear" type="{}Year" minOccurs="0"/>
 *         &lt;element name="ExtensionPoint" type="{}ExtensionPoint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@Root public class Card {

  @Element(name = "BrandId") private String brandId;
  @Element(name = "BrandName") private String brandName;
  @Element(name = "AccountNumber") private String accountNumber;
  @Element(name = "BillingAddress", required = false) private Address billingAddress;
  @Element(name = "CardHolderName") private String cardHolderName;
  @Element(name = "ExpiryMonth", required = false) private Integer expiryMonth;
  @Element(name = "ExpiryYear", required = false) private Integer expiryYear;
  @Element(name = "ExtensionPoint", required = false) private ExtensionPoint extensionPoint;

  /**
   * Gets the value of the brandId property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getBrandId() {
    return brandId;
  }

  /**
   * Sets the value of the brandId property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setBrandId(String value) {
    this.brandId = value;
  }

  /**
   * Gets the value of the brandName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getBrandName() {
    return brandName;
  }

  /**
   * Sets the value of the brandName property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setBrandName(String value) {
    this.brandName = value;
  }

  /**
   * Gets the value of the accountNumber property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Sets the value of the accountNumber property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setAccountNumber(String value) {
    this.accountNumber = value;
  }

  /**
   * Gets the value of the billingAddress property.
   *
   * @return possible object is
   * {@link Address }
   */
  public Address getBillingAddress() {
    return billingAddress;
  }

  /**
   * Sets the value of the billingAddress property.
   *
   * @param value allowed object is
   * {@link Address }
   */
  public void setBillingAddress(Address value) {
    this.billingAddress = value;
  }

  /**
   * Gets the value of the cardHolderName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getCardHolderName() {
    return cardHolderName;
  }

  /**
   * Sets the value of the cardHolderName property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setCardHolderName(String value) {
    this.cardHolderName = value;
  }

  /**
   * Gets the value of the expiryMonth property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getExpiryMonth() {
    return expiryMonth;
  }

  /**
   * Sets the value of the expiryMonth property.
   *
   * @param value allowed object is
   * {@link Integer }
   */
  public void setExpiryMonth(Integer value) {
    this.expiryMonth = value;
  }

  /**
   * Gets the value of the expiryYear property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getExpiryYear() {
    return expiryYear;
  }

  /**
   * Sets the value of the expiryYear property.
   *
   * @param value allowed object is
   * {@link Integer }
   */
  public void setExpiryYear(Integer value) {
    this.expiryYear = value;
  }

  /**
   * Gets the value of the extensionPoint property.
   *
   * @return possible object is
   * {@link ExtensionPoint }
   */
  public ExtensionPoint getExtensionPoint() {
    return extensionPoint;
  }

  /**
   * Sets the value of the extensionPoint property.
   *
   * @param value allowed object is
   * {@link ExtensionPoint }
   */
  public void setExtensionPoint(ExtensionPoint value) {
    this.extensionPoint = value;
  }
}