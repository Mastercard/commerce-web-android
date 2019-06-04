package com.mastercard.mp.checkout;

import android.os.Parcel;
import android.os.Parcelable;
import com.mastercard.commerce.CryptoOptions;

/**
 * Provides merchants Tokenization information
 */

public final class Tokenization implements Parcelable {
  private String unpredictableNumber;
  private CryptoOptions cryptoOptions;

  public String getUnpredictableNumber() {
    return unpredictableNumber;
  }

  public CryptoOptions getCryptoOptions() {
    return cryptoOptions;
  }

  public Tokenization(String unpredictableNumber, CryptoOptions cryptoOptions) {
    this.unpredictableNumber = unpredictableNumber;
    this.cryptoOptions = cryptoOptions;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.unpredictableNumber);
    dest.writeParcelable(this.cryptoOptions, 0);
  }

  Tokenization(Parcel in) {
    this.unpredictableNumber = in.readString();
    this.cryptoOptions = in.readParcelable(CryptoOptions.class.getClassLoader());
  }

  public static final Creator<Tokenization> CREATOR = new Creator<Tokenization>() {
    @Override public Tokenization createFromParcel(Parcel source) {
      return new Tokenization(source);
    }

    @Override public Tokenization[] newArray(int size) {
      return new Tokenization[size];
    }
  };

  @Override public String toString() {
    return "Tokenization{"
        + "unpredictableNumber='"
        + unpredictableNumber
        + '\''
        + ", cryptoOptions="
        + cryptoOptions
        + '}';
  }
}
