package com.mastercard.mp.switchservices.paymentData;

import java.util.Date;
import java.util.Objects;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This class contains different methods to send transaction details to Masterpass using
 * PostbackApi.
 **/
@Root(name = "Postback") public class Postback {

  @Element(name = "transactionId") private String transactionId = null;

  @Element(name = "currencyCode") private String currencyCode = null;

  @Element(name = "subtotal") private Double subtotal = null;

  @Element(name = "paymentSuccessful") private Boolean paymentSuccessful = null;

  @Element(name = "paymentCode") private String paymentCode = null;

  @Element(name = "paymentDate") private Date paymentDate = null;

  /**
   * Gets the transaction Id.
   *
   * @return the transaction Id.
   **/

  public String getTransactionId() {
    return transactionId;
  }

  /**
   * Sets the transaction Id.
   *
   * @param transactionId the transaction Id.
   */
  public Postback transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Gets the currencyCode for the transaction; for example, USD.
   *
   * @return the currencyCode for the transaction; for example, USD.
   **/

  public String getCurrencyCode() {
    return currencyCode;
  }

  /**
   * Sets the currencyCode for the transaction; for example, USD.
   *
   * @param currencyCode the currencyCode for the transaction; for example, USD.
   */
  public Postback currencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
    return this;
  }

  /**
   * Gets the transaction order amount.
   *
   * @return the transaction order amount.
   **/

  public Double getSubtotal() {
    return subtotal;
  }

  /**
   * Sets the transaction order amount.
   *
   * @param subtotal the transaction order amount.
   */
  public Postback subtotal(Double subtotal) {
    this.subtotal = subtotal;
    return this;
  }

  /**
   * Gets the payment status indicator. It is set to true if payment sucessful with PSP else false.
   *
   * @return the payment status indicator. It is set to true if payment sucessful with PSP else
   * false.
   **/

  public Boolean getPaymentSuccessful() {
    return paymentSuccessful;
  }

  /**
   * Sets the payment status indicator. It is set to true if payment sucessful with PSP else false.
   *
   * @param paymentSuccessful the payment status indicator. It is set to true if payment sucessful
   * with PSP else false.
   */
  public Postback paymentSuccessful(Boolean paymentSuccessful) {
    this.paymentSuccessful = paymentSuccessful;
    return this;
  }

  /**
   * Gets the six-digit approval code returned by payment API.
   *
   * @return the six-digit approval code returned by payment API.
   **/

  public String getPaymentCode() {
    return paymentCode;
  }

  /**
   * Sets the six-digit approval code returned by payment API.
   *
   * @param paymentCode the six-digit approval code returned by payment API.
   */
  public Postback paymentCode(String paymentCode) {
    this.paymentCode = paymentCode;
    return this;
  }

  /**
   * Gets the date of purchase.
   *
   * @return the date of purchase.
   **/

  public Date getPaymentDate() {
    return paymentDate;
  }

  /**
   * Sets the date of purchase.
   *
   * @param paymentDate the date of purchase.
   */
  public Postback paymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
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
    Postback postback = (Postback) o;
    return Objects.equals(transactionId, postback.transactionId) && Objects.equals(currencyCode,
        postback.currencyCode) && Objects.equals(subtotal, postback.subtotal) && Objects.equals(
        paymentSuccessful, postback.paymentSuccessful) && Objects.equals(paymentCode,
        postback.paymentCode) && Objects.equals(paymentDate, postback.paymentDate);
  }

  /**
   * Generates a hash code for a sequence of input values.
   */
  @Override public int hashCode() {
    return Objects.hash(transactionId, currencyCode, subtotal, paymentSuccessful, paymentCode,
        paymentDate);
  }

  /**
   * Returns the result of calling toString for a non-null argument and "null" for a null argument.
   */
  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Postback {\n");

    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    currencyCode: ").append(toIndentedString(currencyCode)).append("\n");
    sb.append("    subtotal: ").append(toIndentedString(subtotal)).append("\n");
    sb.append("    paymentSuccessful: ").append(toIndentedString(paymentSuccessful)).append("\n");
    sb.append("    paymentCode: ").append(toIndentedString(paymentCode)).append("\n");
    sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
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
