package com.mastercard.mp.switchservices.paymentData;

import com.mastercard.mp.switchservices.SerializedName;
import java.io.Serializable;

/**
 * Created by e069162 on 3/30/17.
 */

public class PaymentData implements Serializable {

  /**
   * card : {"brandId":"master","brandName":"MasterCard","accountNumber":"5105105105105100","billingAddress":{"city":"Beverly
   * Hills","country":"US","subdivision":"US-CA","line1":"123 Nowhere
   * Street","line2":"","line3":"","postalCode":"90210"},"cardHolderName":"Lee
   * Cardholder","expiryMonth":12,"expiryYear":2017}
   * shippingAddress : {"city":"Beverly Hills","country":"US","subdivision":"US-CA","line1":"123
   * Nowhere Street","line2":"","line3":"","postalCode":"90210"}
   * personalInfo : {"recipientName":"Lee Cardholder","recipientPhone":"555-555-5555"}
   * walletId : 102
   * authenticationOptions : {"eciFlag":"","scEnrollmentStatus":"U"}
   */
  @SerializedName(name = "card") private CardEntity card;
  @SerializedName(name = "shippingAddress") private ShippingAddressEntity shippingAddress;
  @SerializedName(name = "personalInfo") private PersonalInfoEntity personalInfo;
  @SerializedName(name = "walletId") private String walletId;
  @SerializedName(name = "authenticationOptions") private AuthenticationOptionsEntity
      authenticationOptions;
  @SerializedName(name = "tokenization") private TokenizationEntity tokenization;
  @SerializedName(name = "cryptogram") private CryptogramEntity cryptogram;
  @SerializedName(name = "pairingId") private String pairingId;

  public CardEntity getCard() {
    return card;
  }

  public void setCard(CardEntity card) {
    this.card = card;
  }

  public ShippingAddressEntity getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(ShippingAddressEntity shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public PersonalInfoEntity getPersonalInfo() {
    return personalInfo;
  }

  public void setPersonalInfo(PersonalInfoEntity personalInfo) {
    this.personalInfo = personalInfo;
  }

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public AuthenticationOptionsEntity getAuthenticationOptions() {
    return authenticationOptions;
  }

  public void setAuthenticationOptions(AuthenticationOptionsEntity authenticationOptions) {
    this.authenticationOptions = authenticationOptions;
  }

  public TokenizationEntity getTokenization() {
    return tokenization;
  }

  public void setTokenization(TokenizationEntity tokenization) {
    this.tokenization = tokenization;
  }

  public CryptogramEntity getCryptogram() {
    return cryptogram;
  }

  public void setCryptogram(CryptogramEntity cryptogram) {
    this.cryptogram = cryptogram;
  }

  public String getPairingId() {
    return pairingId;
  }

  public void setPairingId(String pairingId) {
    this.pairingId = pairingId;
  }

  @Override public String toString() {
    return "PaymentData{"
        + "card="
        + card
        + ", shippingAddress="
        + shippingAddress
        + ", personalInfo="
        + personalInfo
        + ", walletId='"
        + walletId
        + '\''
        + ", authenticationOptions="
        + authenticationOptions
        + '}';
  }

  public static class CardEntity {
    /**
     * brandId : master
     * brandName : MasterCard
     * accountNumber : 5105105105105100
     * billingAddress : {"city":"Beverly Hills","country":"US","subdivision":"US-CA","line1":"123
     * Nowhere Street","line2":"","line3":"","postalCode":"90210"}
     * cardHolderName : Lee Cardholder
     * expiryMonth : 12
     * expiryYear : 2017
     */

    @SerializedName(name = "brandId") private String brandId;
    @SerializedName(name = "brandName") private String brandName;
    @SerializedName(name = "accountNumber") private String accountNumber;
    @SerializedName(name = "billingAddress") private BillingAddressEntity billingAddress;
    @SerializedName(name = "cardHolderName") private String cardHolderName;
    @SerializedName(name = "expiryMonth") private int expiryMonth;
    @SerializedName(name = "expiryYear") private int expiryYear;

    public String getBrandId() {
      return brandId;
    }

    public void setBrandId(String brandId) {
      this.brandId = brandId;
    }

    public String getBrandName() {
      return brandName;
    }

    public void setBrandName(String brandName) {
      this.brandName = brandName;
    }

    public String getAccountNumber() {
      return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
    }

    public BillingAddressEntity getBillingAddress() {
      return billingAddress;
    }

    public void setBillingAddress(BillingAddressEntity billingAddress) {
      this.billingAddress = billingAddress;
    }

    public String getCardHolderName() {
      return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
      this.cardHolderName = cardHolderName;
    }

    public int getExpiryMonth() {
      return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
      this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
      return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
      this.expiryYear = expiryYear;
    }

    public static class BillingAddressEntity {
      /**
       * city : Beverly Hills
       * country : US
       * subdivision : US-CA
       * line1 : 123 Nowhere Street
       * line2 :
       * line3 :
       * postalCode : 90210
       */

      @SerializedName(name = "city") private String city;
      @SerializedName(name = "country") private String country;
      @SerializedName(name = "subdivision") private String subdivision;
      @SerializedName(name = "line1") private String line1;
      @SerializedName(name = "line2") private String line2;
      @SerializedName(name = "line3") private String line3;
      @SerializedName(name = "postalCode") private String postalCode;

      public String getCity() {
        return city;
      }

      public void setCity(String city) {
        this.city = city;
      }

      public String getCountry() {
        return country;
      }

      public void setCountry(String country) {
        this.country = country;
      }

      public String getSubdivision() {
        return subdivision;
      }

      public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
      }

      public String getLine1() {
        return line1;
      }

      public void setLine1(String line1) {
        this.line1 = line1;
      }

      public String getLine2() {
        return line2;
      }

      public void setLine2(String line2) {
        this.line2 = line2;
      }

      public String getLine3() {
        return line3;
      }

      public void setLine3(String line3) {
        this.line3 = line3;
      }

      public String getPostalCode() {
        return postalCode;
      }

      public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
      }
    }
  }

  public static class ShippingAddressEntity {
    /**
     * city : Beverly Hills
     * country : US
     * subdivision : US-CA
     * line1 : 123 Nowhere Street
     * line2 :
     * line3 :
     * postalCode : 90210
     */
    @SerializedName(name = "city") private String city;
    @SerializedName(name = "country") private String country;
    @SerializedName(name = "subdivision") private String subdivision;
    @SerializedName(name = "line1") private String line1;
    @SerializedName(name = "line2") private String line2;
    @SerializedName(name = "line3") private String line3;
    @SerializedName(name = "postalCode") private String postalCode;

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getCountry() {
      return country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public String getSubdivision() {
      return subdivision;
    }

    public void setSubdivision(String subdivision) {
      this.subdivision = subdivision;
    }

    public String getLine1() {
      return line1;
    }

    public void setLine1(String line1) {
      this.line1 = line1;
    }

    public String getLine2() {
      return line2;
    }

    public void setLine2(String line2) {
      this.line2 = line2;
    }

    public String getLine3() {
      return line3;
    }

    public void setLine3(String line3) {
      this.line3 = line3;
    }

    public String getPostalCode() {
      return postalCode;
    }

    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }
  }

  public static class PersonalInfoEntity {
    /**
     * recipientName : Lee Cardholder
     * recipientPhone : 555-555-5555
     */

    @SerializedName(name = "recipientName") private String recipientName;
    @SerializedName(name = "recipientPhone") private String recipientPhone;

    public String getRecipientName() {
      return recipientName;
    }

    public void setRecipientName(String recipientName) {
      this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
      return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
      this.recipientPhone = recipientPhone;
    }
  }

  public static class AuthenticationOptionsEntity {
    /**
     * eciFlag :
     * scEnrollmentStatus : U
     */

    @SerializedName(name = "authenticateMethod") private String authenticateMethod;

    public String getAuthenticateMethod() {
      return authenticateMethod;
    }

    public void setAuthenticateMethod(String authenticateMethod) {
      this.authenticateMethod = authenticateMethod;
    }
  }

  public static class TokenizationEntity {

    @SerializedName(name = "cryptogram") private CryptogramEntity cryptogram;

    @SerializedName(name = "paymentAccountReference") private String paymentAccountReference;

    @SerializedName(name = "tokenRequestorId") private String tokenRequestorId;

    public CryptogramEntity getCryptogram() {
      return cryptogram;
    }

    public void setCryptogram(CryptogramEntity cryptogram) {
      this.cryptogram = cryptogram;
    }

    public String getPaymentAccountReference() {
      return paymentAccountReference;
    }

    public void setPaymentAccountReference(String paymentAccountReference) {
      this.paymentAccountReference = paymentAccountReference;
    }

    public String getTokenRequestorId() {
      return tokenRequestorId;
    }

    public void setTokenRequestorId(String tokenRequestorId) {
      this.tokenRequestorId = tokenRequestorId;
    }
  }

  public static class CryptogramEntity {

    @SerializedName(name = "cryptoValue") private String cryptoValue;

    @SerializedName(name = "cryptoType") private String cryptoType;

    @SerializedName(name = "eci") private String eci;

    @SerializedName(name = "unpredictableNumber") private String unpredictableNumber;

    public String getCryptoValue() {
      return cryptoValue;
    }

    public void setCryptoValue(String cryptoValue) {
      this.cryptoValue = cryptoValue;
    }

    public String getCryptoType() {
      return cryptoType;
    }

    public void setCryptoType(String cryptoType) {
      this.cryptoType = cryptoType;
    }

    public String getEci() {
      return eci;
    }

    public void setEci(String eci) {
      this.eci = eci;
    }

    public String getUnpredictableNumber() {
      return unpredictableNumber;
    }

    public void setUnpredictableNumber(String unpredictableNumber) {
      this.unpredictableNumber = unpredictableNumber;
    }
  }
}
