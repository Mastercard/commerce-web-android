package com.mastercard.mp.switchservices.paymentData;

import java.util.Objects;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This class contains methods to get the recipient&#39;s personal details.
 **/
@Root(name = "PersonalInfo") public class PersonalInfo {

  @Element(name = "recipientName") private String recipientName = null;

  @Element(name = "recipientPhone") private String recipientPhone = null;

  /**
   * Gets the recipient's name.
   *
   * @return the recipient's name.
   **/

  public String getRecipientName() {
    return recipientName;
  }

  /**
   * Sets the recipient's name.
   *
   * @param recipientName the recipient's name.
   */
  public PersonalInfo recipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  /**
   * Gets the recipient's phone number.
   *
   * @return the recipient's phone number.
   **/

  public String getRecipientPhone() {
    return recipientPhone;
  }

  /**
   * Sets the recipient's phone number.
   *
   * @param recipientPhone the recipient's phone number.
   */
  public PersonalInfo recipientPhone(String recipientPhone) {
    this.recipientPhone = recipientPhone;
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
    PersonalInfo personalInfo = (PersonalInfo) o;
    return Objects.equals(recipientName, personalInfo.recipientName) && Objects.equals(
        recipientPhone, personalInfo.recipientPhone);
  }

  /**
   * Generates a hash code for a sequence of input values.
   */
  @Override public int hashCode() {
    return Objects.hash(recipientName, recipientPhone);
  }

  /**
   * Returns the result of calling toString for a non-null argument and "null" for a null argument.
   */
  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalInfo {\n");

    sb.append("    recipientName: ").append(toIndentedString(recipientName)).append("\n");
    sb.append("    recipientPhone: ").append(toIndentedString(recipientPhone)).append("\n");
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
