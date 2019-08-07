package com.mastercard.mp.switchservices.paymentData;

import java.util.Objects;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This class contains methods to get the consumer&#39;s address details.
 **/
@Root(name = "Address") public class Address {

  @Element(name = "city") private String city = null;

  @Element(name = "country") private String country = null;

  @Element(name = "subdivision", required = false) private String subdivision = null;

  @Element(name = "line1") private String line1 = null;

  @Element(name = "line2", required = false) private String line2 = null;

  @Element(name = "line3", required = false) private String line3 = null;

  @Element(name = "line4", required = false) private String line4 = null;

  @Element(name = "line5", required = false) private String line5 = null;

  @Element(name = "postalCode", required = false) private String postalCode = null;

  /**
   * Gets the cardholder's city.
   *
   * @return the cardholder's city.
   **/

  public String getCity() {
    return city;
  }

  /**
   * Sets the cardholder's city.
   *
   * @param city the cardholder's city.
   */
  public Address city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Gets the cardholder's country as defined by ISO 3166-1 alpha-2 digit country codes; for
   * example, US is the United States, AU is Australia, CA is Canada, GB is the United Kingdom, and
   * so on.
   *
   * @return the cardholder's country as defined by ISO 3166-1 alpha-2 digit country codes; for
   * example, US is the United States, AU is Australia, CA is Canada, GB is the United Kingdom, and
   * so on.
   **/

  public String getCountry() {
    return country;
  }

  /**
   * Sets the cardholder's country as defined by ISO 3166-1 alpha-2 digit country codes; for
   * example, US is the United States, AU is Australia, CA is Canada, GB is the United Kingdom, and
   * so on.
   *
   * @param country the cardholder's country as defined by ISO 3166-1 alpha-2 digit country codes;
   * for example, US is the United States, AU is Australia, CA is Canada, GB is the United Kingdom,
   * and so on.
   */
  public Address country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Gets the cardholder's country's subdivision as defined by ISO 3166-1 alpha-2 digit code; for
   * example, US-VA is Virginia, US-OH is Ohio, and so on.
   *
   * @return the cardholder's country's subdivision as defined by ISO 3166-1 alpha-2 digit code; for
   * example, US-VA is Virginia, US-OH is Ohio, and so on.
   **/

  public String getSubdivision() {
    return subdivision;
  }

  /**
   * Sets the cardholder's country's subdivision as defined by ISO 3166-1 alpha-2 digit code; for
   * example, US-VA is Virginia, US-OH is Ohio, and so on.
   *
   * @param subdivision the cardholder's country's subdivision as defined by ISO 3166-1 alpha-2
   * digit code; for example, US-VA is Virginia, US-OH is Ohio, and so on.
   */
  public Address subdivision(String subdivision) {
    this.subdivision = subdivision;
    return this;
  }

  /**
   * Gets the address in line 1 is used for the street number and the street name.
   *
   * @return the address in line 1 is used for the street number and the street name.
   **/

  public String getLine1() {
    return line1;
  }

  /**
   * Sets the address in line 1 is used for the street number and the street name.
   *
   * @param line1 the address in line 1 is used for the street number and the street name.
   */
  public Address line1(String line1) {
    this.line1 = line1;
    return this;
  }

  /**
   * Gets the address in line 2 is used for the apartment number, suite Number, and so on.
   *
   * @return the address in line 2 is used for the apartment number, suite Number, and so on.
   **/

  public String getLine2() {
    return line2;
  }

  /**
   * Sets the address in line 2 is used for the apartment number, suite Number, and so on.
   *
   * @param line2 the address in line 2 is used for the apartment number, suite Number, and so on.
   */
  public Address line2(String line2) {
    this.line2 = line2;
    return this;
  }

  /**
   * Gets the address in line 3 is used to enter the remaining address information if it does not
   * fit in lines 1 and 2.
   *
   * @return the address in line 3 is used to enter the remaining address information if it does not
   * fit in lines 1 and 2.
   **/

  public String getLine3() {
    return line3;
  }

  /**
   * Sets the address in line 3 is used to enter the remaining address information if it does not
   * fit in lines 1 and 2.
   *
   * @param line3 the address in line 3 is used to enter the remaining address information if it
   * does not fit in lines 1 and 2.
   */
  public Address line3(String line3) {
    this.line3 = line3;
    return this;
  }

  /**
   * Gets the address in line 4 is used to enter the remaining address information if it does not
   * fit in lines 1, 2 and 3.
   *
   * @return the address in line 4 is used to enter the remaining address information if it does not
   * fit in lines 1, 2 and 3.
   **/

  public String getLine4() {
    return line4;
  }

  /**
   * Sets the address in line 4 is used to enter the remaining address information if it does not
   * fit in lines 1, 2 and 3.
   *
   * @param line4 the address in line 4 is used to enter the remaining address information if it
   * does not fit in lines 1, 2 and 3.
   */
  public Address line4(String line4) {
    this.line4 = line4;
    return this;
  }

  /**
   * Gets the address in line 5 is used to enter the remaining address information if it does not
   * fit in line 1,2,3 and 4.
   *
   * @return the address in line 5 is used to enter the remaining address information if it does not
   * fit in line 1,2,3 and 4.
   **/

  public String getLine5() {
    return line5;
  }

  /**
   * Sets the address in line 5 is used to enter the remaining address information if it does not
   * fit in line 1,2,3 and 4.
   *
   * @param line5 the address in line 5 is used to enter the remaining address information if it
   * does not fit in line 1,2,3 and 4.
   */
  public Address line5(String line5) {
    this.line5 = line5;
    return this;
  }

  /**
   * Gets the postal code or zip code appended to the mailing address for the purpose of sorting
   * mail.
   *
   * @return the postal code or zip code appended to the mailing address for the purpose of sorting
   * mail.
   **/

  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Sets the postal code or zip code appended to the mailing address for the purpose of sorting
   * mail.
   *
   * @param postalCode the postal code or zip code appended to the mailing address for the purpose
   * of sorting mail.
   */
  public Address postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Returns true if the arguments are equal to each other and false
   * otherwise. Consequently, if both arguments are null, true is returned and
   * if exactly one argument is null, false is returned. Otherwise, equality
   * is determined by using the equals method of the first argument.
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(city, address.city) && Objects.equals(country, address.country) && Objects
        .equals(subdivision, address.subdivision) && Objects.equals(line1, address.line1) && Objects
        .equals(line2, address.line2) && Objects.equals(line3, address.line3) && Objects.equals(
        line4, address.line4) && Objects.equals(line5, address.line5) && Objects.equals(postalCode,
        address.postalCode);
  }

  /**
   * Generates a hash code for a sequence of input values.
   */
  @Override public int hashCode() {
    return Objects.hash(city, country, subdivision, line1, line2, line3, line4, line5, postalCode);
  }

  /**
   * Returns the result of calling toString for a non-null argument and "null" for a null argument.
   */
  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Address {\n");

    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    subdivision: ").append(toIndentedString(subdivision)).append("\n");
    sb.append("    line1: ").append(toIndentedString(line1)).append("\n");
    sb.append("    line2: ").append(toIndentedString(line2)).append("\n");
    sb.append("    line3: ").append(toIndentedString(line3)).append("\n");
    sb.append("    line4: ").append(toIndentedString(line4)).append("\n");
    sb.append("    line5: ").append(toIndentedString(line5)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
