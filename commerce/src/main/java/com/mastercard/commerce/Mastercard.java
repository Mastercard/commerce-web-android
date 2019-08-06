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
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Mastercard card type with the supported cryptogram formats.
 */
public class Mastercard extends CryptoOptions {
  static final String CARD_TYPE = "master";
  private final List<String> format;

  protected Mastercard(Parcel in) {
    super(in);
    format = in.createStringArrayList();
  }

  public static final Creator<Mastercard> CREATOR = new Creator<Mastercard>() {
    @Override public Mastercard createFromParcel(Parcel in) {
      return new Mastercard(in);
    }

    @Override public Mastercard[] newArray(int size) {
      return new Mastercard[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeStringList(format);
  }

  public Mastercard(@NonNull Set<MastercardFormat> formats) {
    Validate.notEmpty(formats);
    this.format = new ArrayList<>();
    for (MastercardFormat mastercardFormat : formats) {
      format.add(mastercardFormat.toString());
    }
  }

  @Override public String getCardType() {
    return CARD_TYPE;
  }

  @Override public List<String> getFormat() {
    return format;
  }

  public enum MastercardFormat {
    UCAF("UCAF"), ICC("ICC");

    private final String format;

    MastercardFormat(String format) {
      this.format = format;
    }

    @NonNull @Override public String toString() {
      return format;
    }
  }
}
