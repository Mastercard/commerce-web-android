package com.mastercard.mp.switchservices.paymentData;

import java.util.Objects;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This class contains various methods to get paymentData response parameters returned by
 * paymentDataApi.
 **/
@Root(name = "PaymentDataResponse") public class PaymentDataResponse {

  @Element(name = "card", required = false) private Card card = null;

  @Element(name = "shippingAddress", required = false) private Address shippingAddress = null;

  @Element(name = "personalInfo", required = false) private PersonalInfo personalInfo = null;

  @Element(name = "tokenizedCard", required = false) private TokenizedCard tokenizedCard = null;

  @Element(name = "walletId", required = false) private String walletId = null;

  @Element(name = "authenticationOptions", required = false) private AuthenticationOptions
      authenticationOptions = null;

  /**
   * Gets the card details.
   *
   * @return the card details.
   **/

  public Card getCard() {
    return card;
  }

  /**
   * Sets the card details.
   *
   * @param card the card details.
   */
  public PaymentDataResponse card(Card card) {
    this.card = card;
    return this;
  }

  /**
   * Gets the shipping address details.
   *
   * @return the shipping address details.
   **/

  public Address getShippingAddress() {
    return shippingAddress;
  }

  /**
   * Sets the shipping address details.
   *
   * @param shippingAddress the shipping address details.
   */
  public PaymentDataResponse shippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
    return this;
  }

  /**
   * Gets the recipient's personal information.
   *
   * @return the recipient's personal information.
   **/

  public PersonalInfo getPersonalInfo() {
    return personalInfo;
  }

  /**
   * Sets the recipient's personal information.
   *
   * @param personalInfo the recipient's personal information.
   */
  public PaymentDataResponse personalInfo(PersonalInfo personalInfo) {
    this.personalInfo = personalInfo;
    return this;
  }

  /**
   * Gets the tokenized card information.
   *
   * @return the tokenized card information.
   **/

  public TokenizedCard getTokenizedCard() {
    return tokenizedCard;
  }

  /**
   * Sets the tokenized card information.
   *
   * @param tokenizedCard the tokenized card information.
   */
  public PaymentDataResponse tokenizedCard(TokenizedCard tokenizedCard) {
    this.tokenizedCard = tokenizedCard;
    return this;
  }

  /**
   * Gets the value which helps to identify origin wallet.
   *
   * @return the value which helps to identify origin wallet.
   **/

  public String getWalletId() {
    return walletId;
  }

  /**
   * Sets the value which helps to identify origin wallet.
   *
   * @param walletId the value which helps to identify origin wallet.
   */
  public PaymentDataResponse walletId(String walletId) {
    this.walletId = walletId;
    return this;
  }

  /**
   * Gets the tokenized card information.
   *
   * @return the tokenized card information.
   **/

  public AuthenticationOptions getAuthenticationOptions() {
    return authenticationOptions;
  }

  /**
   * Sets the tokenized card information.
   *
   * @param authenticationOptions the tokenized card information.
   */
  public PaymentDataResponse authenticationOptions(AuthenticationOptions authenticationOptions) {
    this.authenticationOptions = authenticationOptions;
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
    PaymentDataResponse paymentData = (PaymentDataResponse) o;
    return Objects.equals(card, paymentData.card)
        && Objects.equals(shippingAddress, paymentData.shippingAddress)
        && Objects.equals(personalInfo, paymentData.personalInfo)
        && Objects.equals(tokenizedCard, paymentData.tokenizedCard)
        && Objects.equals(walletId, paymentData.walletId)
        && Objects.equals(authenticationOptions, paymentData.authenticationOptions);
  }

  /**
   * Generates a hash code for a sequence of input values.
   */
  @Override public int hashCode() {
    return Objects.hash(card, shippingAddress, personalInfo, tokenizedCard, walletId,
        authenticationOptions);
  }

  /**
   * Returns the result of calling toString for a non-null argument and "null" for a null argument.
   */
  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentDataResponse {\n");

    sb.append("    card: ").append(toIndentedString(card)).append("\n");
    sb.append("    shippingAddress: ").append(toIndentedString(shippingAddress)).append("\n");
    sb.append("    personalInfo: ").append(toIndentedString(personalInfo)).append("\n");
    sb.append("    tokenizedCard: ").append(toIndentedString(tokenizedCard)).append("\n");
    sb.append("    walletId: ").append(toIndentedString(walletId)).append("\n");
    sb.append("    authenticationOptions: ")
        .append(toIndentedString(authenticationOptions))
        .append("\n");
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
