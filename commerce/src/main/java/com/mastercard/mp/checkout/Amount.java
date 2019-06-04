package com.mastercard.mp.checkout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Currency;

/**
 * Amount charged by Merchant. Pass the Locale of currency as String.
 * The Wallet SDK will require the Currency Locale to format the Amount.
 */

public class Amount implements Parcelable {

  private long total;
  private String currencyCode;
  private String currencyNumber;

  public Amount(long paymentAmount, String currencyCode) {
    Currency.getInstance(currencyCode);
    this.total = paymentAmount;
    this.currencyCode = currencyCode;
  }

  private Amount() {
    //Added for JsonSerializer conversion
  }

  protected Amount(Parcel in) {
    total = in.readLong();
    currencyCode = in.readString();
  }

  public static final Creator<Amount> CREATOR = new Creator<Amount>() {
    @Override public Amount createFromParcel(Parcel in) {
      return new Amount(in);
    }

    @Override public Amount[] newArray(int size) {
      return new Amount[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(total);
    dest.writeString(currencyCode);
  }

  @Override public String toString() {
    return "Amount{" + "total=" + total + ", currencyCode='" + currencyCode + '\'' + '}';
  }

  public String getCurrencyNumber() {
    return currencyNumber;
  }

  public void setCurrencyNumber(String currencyNumber) {
    this.currencyNumber = currencyNumber;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }
}
