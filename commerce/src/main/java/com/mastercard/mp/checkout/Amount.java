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
import java.util.Currency;

/**
 * Amount charged by Merchant. Pass the Locale of currency as String.
 * The Wallet SDK will require the Currency Locale to format the Amount.
 *
 * @deprecated You should migrate your code to use {@link CommerceWebSdk} instead. All APIs available
 * in this package will be deprecated in a future release.
 */

@Deprecated public class Amount implements Parcelable {

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
