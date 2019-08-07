package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <p>Java class for Address complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Address">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="City" type="{}NonEmptyString"/>
 *         &lt;element name="Country" type="{}Country"/>
 *         &lt;element name="CountrySubdivision" type="{}NonEmptyString" minOccurs="0"/>
 *         &lt;element name="Line1" type="{}NonEmptyString"/>
 *         &lt;element name="Line2" type="{}NonEmptyString" minOccurs="0"/>
 *         &lt;element name="Line3" type="{}NonEmptyString" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{}NonEmptyString" minOccurs="0"/>
 *         &lt;element name="ExtensionPoint" type="{}ExtensionPoint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@Root public class Address {

  @Element(name = "City", required = false) private String city;
  @Element(name = "Country", required = false) private String country;
  @Element(name = "CountrySubdivision", required = false) private String countrySubdivision;
  @Element(name = "Line1", required = false) private String line1;
  @Element(name = "Line2", required = false) private String line2;
  @Element(name = "Line3", required = false) private String line3;
  @Element(name = "PostalCode", required = false) private String postalCode;
  @Element(name = "ExtensionPoint", required = false) private ExtensionPoint extensionPoint;

  /**
   * Gets the value of the city property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets the value of the city property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setCity(String value) {
    this.city = value;
  }

  /**
   * Gets the value of the country property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getCountry() {
    return country;
  }

  /**
   * Sets the value of the country property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setCountry(String value) {
    this.country = value;
  }

  /**
   * Gets the value of the countrySubdivision property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getCountrySubdivision() {
    return countrySubdivision;
  }

  /**
   * Sets the value of the countrySubdivision property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setCountrySubdivision(String value) {
    this.countrySubdivision = value;
  }

  /**
   * Gets the value of the line1 property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getLine1() {
    return line1;
  }

  /**
   * Sets the value of the line1 property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setLine1(String value) {
    this.line1 = value;
  }

  /**
   * Gets the value of the line2 property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getLine2() {
    return line2;
  }

  /**
   * Sets the value of the line2 property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setLine2(String value) {
    this.line2 = value;
  }

  /**
   * Gets the value of the line3 property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getLine3() {
    return line3;
  }

  /**
   * Sets the value of the line3 property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setLine3(String value) {
    this.line3 = value;
  }

  /**
   * Gets the value of the postalCode property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Sets the value of the postalCode property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setPostalCode(String value) {
    this.postalCode = value;
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
