package com.mastercard.testapp.domain.model;

import java.io.Serializable;

/**
 * Created by Sebastian Farias on 11/16/17.
 * <p>
 * Object used on pre checkout flow with shipping data
 */
public class MasterpassPreCheckoutShippingObject implements Serializable {

  private static final long serialVersionUID = 258813574421098214L;
  private String preRecipientInfo;
  private String preRecipientName;
  private String preRecipientPhone;
  private String preAddressId;
  private String preCity;
  private String preCountry;
  private String preSubdivision;
  private String preLine1;
  private String preLine2;
  private String preLine3;
  private String preLine4;
  private String preLine5;
  private String prePostalCode;
  private String preDefault;

  /**
   * Gets pre recipient info.
   *
   * @return the pre recipient info
   */
  public String getPreRecipientInfo() {
    return preRecipientInfo;
  }

  /**
   * Sets pre recipient info.
   *
   * @param preRecipientInfo the pre recipient info
   */
  public void setPreRecipientInfo(String preRecipientInfo) {
    this.preRecipientInfo = preRecipientInfo;
  }

  /**
   * Gets pre recipient name.
   *
   * @return the pre recipient name
   */
  public String getPreRecipientName() {
    return preRecipientName;
  }

  /**
   * Sets pre recipient name.
   *
   * @param preRecipientName the pre recipient name
   */
  public void setPreRecipientName(String preRecipientName) {
    this.preRecipientName = preRecipientName;
  }

  /**
   * Gets pre recipient phone.
   *
   * @return the pre recipient phone
   */
  public String getPreRecipientPhone() {
    return preRecipientPhone;
  }

  /**
   * Sets pre recipient phone.
   *
   * @param preRecipientPhone the pre recipient phone
   */
  public void setPreRecipientPhone(String preRecipientPhone) {
    this.preRecipientPhone = preRecipientPhone;
  }

  /**
   * Gets pre address id.
   *
   * @return the pre address id
   */
  public String getPreAddressId() {
    return preAddressId;
  }

  /**
   * Sets pre address id.
   *
   * @param preAddressId the pre address id
   */
  public void setPreAddressId(String preAddressId) {
    this.preAddressId = preAddressId;
  }

  /**
   * Gets pre city.
   *
   * @return the pre city
   */
  public String getPreCity() {
    return preCity;
  }

  /**
   * Sets pre city.
   *
   * @param preCity the pre city
   */
  public void setPreCity(String preCity) {
    this.preCity = preCity;
  }

  /**
   * Gets pre country.
   *
   * @return the pre country
   */
  public String getPreCountry() {
    return preCountry;
  }

  /**
   * Sets pre country.
   *
   * @param preCountry the pre country
   */
  public void setPreCountry(String preCountry) {
    this.preCountry = preCountry;
  }

  /**
   * Gets pre subdivision.
   *
   * @return the pre subdivision
   */
  public String getPreSubdivision() {
    return preSubdivision;
  }

  /**
   * Sets pre subdivision.
   *
   * @param preSubdivision the pre subdivision
   */
  public void setPreSubdivision(String preSubdivision) {
    this.preSubdivision = preSubdivision;
  }

  /**
   * Gets pre line 1.
   *
   * @return the pre line 1
   */
  public String getPreLine1() {
    return preLine1;
  }

  /**
   * Sets pre line 1.
   *
   * @param preLine1 the pre line 1
   */
  public void setPreLine1(String preLine1) {
    this.preLine1 = preLine1;
  }

  /**
   * Gets pre line 2.
   *
   * @return the pre line 2
   */
  public String getPreLine2() {
    return preLine2;
  }

  /**
   * Sets pre line 2.
   *
   * @param preLine2 the pre line 2
   */
  public void setPreLine2(String preLine2) {
    this.preLine2 = preLine2;
  }

  /**
   * Gets pre line 3.
   *
   * @return the pre line 3
   */
  public String getPreLine3() {
    return preLine3;
  }

  /**
   * Sets pre line 3.
   *
   * @param preLine3 the pre line 3
   */
  public void setPreLine3(String preLine3) {
    this.preLine3 = preLine3;
  }

  /**
   * Gets pre line 4.
   *
   * @return the pre line 4
   */
  public String getPreLine4() {
    return preLine4;
  }

  /**
   * Sets pre line 4.
   *
   * @param preLine4 the pre line 4
   */
  public void setPreLine4(String preLine4) {
    this.preLine4 = preLine4;
  }

  /**
   * Gets pre line 5.
   *
   * @return the pre line 5
   */
  public String getPreLine5() {
    return preLine5;
  }

  /**
   * Sets pre line 5.
   *
   * @param preLine5 the pre line 5
   */
  public void setPreLine5(String preLine5) {
    this.preLine5 = preLine5;
  }

  /**
   * Gets pre postal code.
   *
   * @return the pre postal code
   */
  public String getPrePostalCode() {
    return prePostalCode;
  }

  /**
   * Sets pre postal code.
   *
   * @param prePostalCode the pre postal code
   */
  public void setPrePostalCode(String prePostalCode) {
    this.prePostalCode = prePostalCode;
  }

  /**
   * Gets pre default.
   *
   * @return the pre default
   */
  public String getPreDefault() {
    return preDefault;
  }

  /**
   * Sets pre default.
   *
   * @param preDefault the pre default
   */
  public void setPreDefault(String preDefault) {
    this.preDefault = preDefault;
  }
}
