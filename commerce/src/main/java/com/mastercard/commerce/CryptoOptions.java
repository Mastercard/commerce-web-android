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

package com.mastercard.commerce;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.util.List;

/**
 * CryptoOptions is used to determine the compatible Mastercard and
 * Visa formats for cryptograms for tokenized transactions.
 *
 * This class is implemented by {@link Mastercard} and {@link Visa}
 */
public abstract class CryptoOptions implements Parcelable {

  public CryptoOptions() {
  }

  public CryptoOptions(Parcel in) {
  }

  /**
   * Returns type of card {master, visa, etc.}
   *
   * @return cardType
   */
  public abstract String getCardType();

  /**
   * @return format List of cryptogram formats supported for the specific {@code cardType}
   * (e.g., master will have format as ["UCAF", "ICC"] visa will have ["TVV"])
   */
  public abstract List<String> getFormat();

  @NonNull @Override public String toString() {
    return "{ " + "cardType: " + getCardType() + ", format: " + getFormat() + " }";
  }
}