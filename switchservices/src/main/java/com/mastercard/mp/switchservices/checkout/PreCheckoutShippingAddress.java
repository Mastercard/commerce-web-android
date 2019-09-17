package com.mastercard.mp.switchservices.checkout;

import com.mastercard.mp.switchservices.SerializedName;

/**
 * ShippingAddress response object returned within PreCheckoutData
 */

public class PreCheckoutShippingAddress {
  private String addressId;
  private String country;
  private String subdivision;
  private String line1;
  private String line2;
  private String line3;
  private String line4;
  private String line5;
  private String city;

  private String postalCode;
  private RecipientInfo recipientInfo;
  @SerializedName private boolean isDefault;

  public String getCountry() {
    return country;
  }

  public String getSubdivision() {
    return subdivision;
  }

  public String getLine1() {
    return line1;
  }

  public String getLine2() {
    return line2;
  }

  public String getLine3() {
    return line3;
  }

  public String getLine4() {
    return line4;
  }

  public String getLine5() {
    return line5;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getAddressId() {
    return addressId;
  }

  public RecipientInfo getRecipientInfo() {
    return recipientInfo;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
