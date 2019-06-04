package com.mastercard.mp.checkout;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allowed NetworkType by merchant.
 * Valid types are: MASTER, VISA, AMEX, DISCOVER, DINERS
 */

public class NetworkType implements Parcelable {
  public static final Creator<NetworkType> CREATOR = new Creator<NetworkType>() {
    @Override public NetworkType createFromParcel(Parcel in) {
      return new NetworkType(in);
    }

    @Override public NetworkType[] newArray(int size) {
      return new NetworkType[size];
    }
  };
  public static final String MASTER = "MASTER";
  public static final String VISA = "VISA";
  public static final String AMEX = "AMEX";
  public static final String DISCOVER = "DISCOVER";
  public static final String DINERS = "DINERS";
  public static final String MAESTRO = "MAESTRO";
  public static final String JCB = "JCB";

  private String networkType;

  private NetworkType(Parcel in) {
    networkType = in.readString();
  }

  public NetworkType(@Type String networkType) {
    this.networkType = networkType;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(networkType);
  }

  public String getNetworkType() {
    return networkType;
  }

  @Override public String toString() {
    return "NetworkType{" + "networkType='" + networkType + '\'' + '}';
  }

  @Retention(RetentionPolicy.SOURCE) @StringDef({
      MASTER, VISA, AMEX, DISCOVER, DINERS, MAESTRO, JCB
  }) @interface Type {
  }
}
