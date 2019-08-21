package com.mastercard.mp.switchservices.paymentData;

import java.util.Objects;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This class contains card details for available cards.
 **/
@Root(name = "Card") public class Card {

  @Element(name = "brandId") private String brandId = null;

  @Element(name = "brandName") private String brandName = null;

  @Element(name = "accountNumber") private String accountNumber = null;

  @Element(name = "cardHolderName") private String cardHolderName = null;

  @Element(name = "expiryMonth", required = false) private Integer expiryMonth = null;

  @Element(name = "expiryYear", required = false) private Integer expiryYear = null;

  @Element(name = "billingAddress") private Address billingAddress = null;

  /**
   * Gets the card's brand id; for example, master for MasterCard.
   *
   * @return the card's brand id; for example, master for MasterCard.
   **/

  public String getBrandId() {
    return brandId;
  }

  /**
   * Sets the card's brand id; for example, master for MasterCard.
   *
   * @param brandId the card's brand id; for example, master for MasterCard.
   */
  public Card brandId(String brandId) {
    this.brandId = brandId;
    return this;
  }

  /**
   * Gets the card's brand name; for example, MasterCard.
   *
   * @return the card's brand name; for example, MasterCard.
   **/

  public String getBrandName() {
    return brandName;
  }

  /**
   * Sets the card's brand name; for example, MasterCard.
   *
   * @param brandName the card's brand name; for example, MasterCard.
   */
  public Card brandName(String brandName) {
    this.brandName = brandName;
    return this;
  }

  /**
   * Gets the PAN.
   *
   * @return the PAN.
   **/

  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Sets the PAN.
   *
   * @param accountNumber the PAN.
   */
  public Card accountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  /**
   * Gets the cardholder's name.
   *
   * @return the cardholder's name.
   **/

  public String getCardHolderName() {
    return cardHolderName;
  }

  /**
   * Sets the cardholder's name.
   *
   * @param cardHolderName the cardholder's name.
   */
  public Card cardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
    return this;
  }

  /**
   * Gets the expiration month displayed on the payment card.
   *
   * @return the expiration month displayed on the payment card.
   **/

  public Integer getExpiryMonth() {
    return expiryMonth;
  }

  /**
   * Sets the expiration month displayed on the payment card.
   *
   * @param expiryMonth the expiration month displayed on the payment card.
   */
  public Card expiryMonth(Integer expiryMonth) {
    this.expiryMonth = expiryMonth;
    return this;
  }

  /**
   * Gets the expiration year displayed on the payment card.
   *
   * @return the expiration year displayed on the payment card.
   **/

  public Integer getExpiryYear() {
    return expiryYear;
  }

  /**
   * Sets the expiration year displayed on the payment card.
   *
   * @param expiryYear the expiration year displayed on the payment card.
   */
  public Card expiryYear(Integer expiryYear) {
    this.expiryYear = expiryYear;
    return this;
  }

  /**
   * Gets the card's billing details.
   *
   * @return the card's billing details.
   **/

  public Address getBillingAddress() {
    return billingAddress;
  }

  /**
   * Sets the card's billing details.
   *
   * @param billingAddress the card's billing details.
   */
  public Card billingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
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
    Card card = (Card) o;
    return Objects.equals(brandId, card.brandId)
        && Objects.equals(brandName, card.brandName)
        && Objects.equals(accountNumber, card.accountNumber)
        && Objects.equals(cardHolderName, card.cardHolderName)
        && Objects.equals(expiryMonth, card.expiryMonth)
        && Objects.equals(expiryYear, card.expiryYear)
        && Objects.equals(billingAddress, card.billingAddress);
  }

  /**
   * Generates a hash code for a sequence of input values.
   */
  @Override public int hashCode() {
    return Objects.hash(brandId, brandName, accountNumber, cardHolderName, expiryMonth, expiryYear,
        billingAddress);
  }

  /**
   * Returns the result of calling toString for a non-null argument and "null" for a null argument.
   */
  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Card {\n");

    sb.append("    brandId: ").append(toIndentedString(brandId)).append("\n");
    sb.append("    brandName: ").append(toIndentedString(brandName)).append("\n");
    sb.append("    accountNumber: ").append(toIndentedString(accountNumber)).append("\n");
    sb.append("    cardHolderName: ").append(toIndentedString(cardHolderName)).append("\n");
    sb.append("    expiryMonth: ").append(toIndentedString(expiryMonth)).append("\n");
    sb.append("    expiryYear: ").append(toIndentedString(expiryYear)).append("\n");
    sb.append("    billingAddress: ").append(toIndentedString(billingAddress)).append("\n");
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
