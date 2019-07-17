/* Copyright © 2019 Mastercard. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 =============================================================================*/

package com.mastercard.mp.checkout;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;
import com.mastercard.commerce.CommerceWebSdk;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allowed NetworkType by merchant.
 * Valid types are: MASTER, VISA, AMEX, DISCOVER, DINERS
 *
 * @deprecated You should migrate your code to use {@link CommerceWebSdk} instead. All APIs available
 * in this package will be deprecated in a future release.
 */

@Deprecated public class NetworkType implements Parcelable {
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
