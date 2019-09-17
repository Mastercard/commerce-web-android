package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <p>Java class for Contact complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Contact">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FirstName" type="{}NonEmptyString"/>
 *         &lt;element name="MiddleName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="150"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LastName" type="{}NonEmptyString"/>
 *         &lt;element name="Gender" type="{}Gender" minOccurs="0"/>
 *         &lt;element name="DateOfBirth" type="{}DateOfBirth" minOccurs="0"/>
 *         &lt;element name="NationalID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="150"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Country" type="{}Country"/>
 *         &lt;element name="EmailAddress" type="{}EmailAddress"/>
 *         &lt;element name="PhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExtensionPoint" type="{}ExtensionPoint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@Root public class Contact {

  @Element(name = "FirstName") private String firstName;
  @Element(name = "MiddleName", required = false) private String middleName;
  @Element(name = "LastName") private String lastName;
  @Element(name = "Gender", required = false) private Gender gender;
  @Element(name = "DateOfBirth", required = false) private DateOfBirth dateOfBirth;
  @Element(name = "NationalID", required = false) private String nationalID;
  @Element(name = "Country") private String country;
  @Element(name = "EmailAddress") private String emailAddress;
  @Element(name = "PhoneNumber") private String phoneNumber;
  @Element(name = "ExtensionPoint", required = false) private ExtensionPoint extensionPoint;

  /**
   * Gets the value of the firstName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the value of the firstName property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setFirstName(String value) {
    this.firstName = value;
  }

  /**
   * Gets the value of the middleName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * Sets the value of the middleName property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setMiddleName(String value) {
    this.middleName = value;
  }

  /**
   * Gets the value of the lastName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the value of the lastName property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setLastName(String value) {
    this.lastName = value;
  }

  /**
   * Gets the value of the gender property.
   *
   * @return possible object is
   * {@link Gender }
   */
  public Gender getGender() {
    return gender;
  }

  /**
   * Sets the value of the gender property.
   *
   * @param value allowed object is
   * {@link Gender }
   */
  public void setGender(Gender value) {
    this.gender = value;
  }

  /**
   * Gets the value of the dateOfBirth property.
   *
   * @return possible object is
   * {@link DateOfBirth }
   */
  public DateOfBirth getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Sets the value of the dateOfBirth property.
   *
   * @param value allowed object is
   * {@link DateOfBirth }
   */
  public void setDateOfBirth(DateOfBirth value) {
    this.dateOfBirth = value;
  }

  /**
   * Gets the value of the nationalID property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getNationalID() {
    return nationalID;
  }

  /**
   * Sets the value of the nationalID property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setNationalID(String value) {
    this.nationalID = value;
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
   * Gets the value of the emailAddress property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * Sets the value of the emailAddress property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setEmailAddress(String value) {
    this.emailAddress = value;
  }

  /**
   * Gets the value of the phoneNumber property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the value of the phoneNumber property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setPhoneNumber(String value) {
    this.phoneNumber = value;
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
