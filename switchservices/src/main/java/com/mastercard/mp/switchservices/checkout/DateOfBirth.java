package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <p>Java class for DateOfBirth complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DateOfBirth">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Year">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1900"/>
 *               &lt;pattern value="\d{4}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Month" type="{}Month"/>
 *         &lt;element name="Day">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="31"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ExtensionPoint" type="{}ExtensionPoint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@Root public class DateOfBirth {

  @Element(name = "Year", required = false) private int year;
  @Element(name = "Month", required = false) private int month;
  @Element(name = "Day", required = false) private int day;
  @Element(name = "ExtensionPoint", required = false) private ExtensionPoint extensionPoint;

  /**
   * Gets the value of the year property.
   */
  public int getYear() {
    return year;
  }

  /**
   * Sets the value of the year property.
   */
  public void setYear(int value) {
    this.year = value;
  }

  /**
   * Gets the value of the month property.
   */
  public int getMonth() {
    return month;
  }

  /**
   * Sets the value of the month property.
   */
  public void setMonth(int value) {
    this.month = value;
  }

  /**
   * Gets the value of the day property.
   */
  public int getDay() {
    return day;
  }

  /**
   * Sets the value of the day property.
   */
  public void setDay(int value) {
    this.day = value;
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
