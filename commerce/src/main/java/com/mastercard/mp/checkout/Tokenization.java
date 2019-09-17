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
import com.mastercard.commerce.CommerceWebSdk;

/**
 * Provides merchants Tokenization information
 *
 * @deprecated You should migrate your code to use {@link CommerceWebSdk} instead. All APIs available
 * in this package will be deprecated in a future release.
 */

@Deprecated public final class Tokenization implements Parcelable {
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
