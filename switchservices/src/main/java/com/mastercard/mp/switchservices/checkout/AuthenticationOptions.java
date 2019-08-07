package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <p>Java class for AuthenticationOptions complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AuthenticationOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AuthenticateMethod" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/>
 *         &lt;element name="CardEnrollmentMethod" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/>
 *         &lt;element name="CAvv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EciFlag" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/>
 *         &lt;element name="MasterCardAssignedID" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/>
 *         &lt;element name="PaResStatus" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/>
 *         &lt;element name="SCEnrollmentStatus" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/>
 *         &lt;element name="SignatureVerification" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/>
 *         &lt;element name="Xid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExtensionPoint" type="{}ExtensionPoint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root public class AuthenticationOptions {

  @Element(name = "AuthenticateMethod", required = false) private String authenticateMethod;
  @Element(name = "CardEnrollmentMethod", required = false) private String cardEnrollmentMethod;
  @Element(name = "CAvv", required = false) private String cAvv;
  @Element(name = "EciFlag", required = false) private String eciFlag;
  @Element(name = "MasterCardAssignedID", required = false) private String masterCardAssignedID;
  @Element(name = "PaResStatus", required = false) private String paResStatus;
  @Element(name = "SCEnrollmentStatus", required = false) private String scEnrollmentStatus;
  @Element(name = "SignatureVerification", required = false) private String signatureVerification;
  @Element(name = "Xid", required = false) private String xid;
  @Element(name = "ExtensionPoint", required = false) private ExtensionPoint extensionPoint;

  /**
   * Gets the value of the authenticateMethod property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getAuthenticateMethod() {
    return authenticateMethod;
  }

  /**
   * Sets the value of the authenticateMethod property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setAuthenticateMethod(String value) {
    this.authenticateMethod = value;
  }

  /**
   * Gets the value of the cardEnrollmentMethod property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getCardEnrollmentMethod() {
    return cardEnrollmentMethod;
  }

  /**
   * Sets the value of the cardEnrollmentMethod property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setCardEnrollmentMethod(String value) {
    this.cardEnrollmentMethod = value;
  }

  /**
   * Gets the value of the cAvv property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getCAvv() {
    return cAvv;
  }

  /**
   * Sets the value of the cAvv property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setCAvv(String value) {
    this.cAvv = value;
  }

  /**
   * Gets the value of the eciFlag property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getEciFlag() {
    return eciFlag;
  }

  /**
   * Sets the value of the eciFlag property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setEciFlag(String value) {
    this.eciFlag = value;
  }

  /**
   * Gets the value of the masterCardAssignedID property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getMasterCardAssignedID() {
    return masterCardAssignedID;
  }

  /**
   * Sets the value of the masterCardAssignedID property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setMasterCardAssignedID(String value) {
    this.masterCardAssignedID = value;
  }

  /**
   * Gets the value of the paResStatus property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getPaResStatus() {
    return paResStatus;
  }

  /**
   * Sets the value of the paResStatus property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setPaResStatus(String value) {
    this.paResStatus = value;
  }

  /**
   * Gets the value of the scEnrollmentStatus property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getSCEnrollmentStatus() {
    return scEnrollmentStatus;
  }

  /**
   * Sets the value of the scEnrollmentStatus property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setSCEnrollmentStatus(String value) {
    this.scEnrollmentStatus = value;
  }

  /**
   * Gets the value of the signatureVerification property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getSignatureVerification() {
    return signatureVerification;
  }

  /**
   * Sets the value of the signatureVerification property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setSignatureVerification(String value) {
    this.signatureVerification = value;
  }

  /**
   * Gets the value of the xid property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getXid() {
    return xid;
  }

  /**
   * Sets the value of the xid property.
   *
   * @param value allowed object is
   * {@link String }
   */
  public void setXid(String value) {
    this.xid = value;
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
