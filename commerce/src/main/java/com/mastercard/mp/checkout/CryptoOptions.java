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
import java.util.List;

/**
 * Class which allows you to specify the cryptogram types for each network that are supported as
 * part of the {@link MasterpassCheckoutRequest} object.
 */
public class CryptoOptions implements Parcelable {

  public static final Creator<CryptoOptions> CREATOR = new Creator<CryptoOptions>() {
    @Override public CryptoOptions createFromParcel(Parcel in) {
      return new CryptoOptions(in);
    }

    @Override public CryptoOptions[] newArray(int size) {
      return new CryptoOptions[size];
    }
  };
  private Visa visa;
  private Mastercard mastercard;

  public CryptoOptions() {
  }

  private CryptoOptions(Parcel in) {
    visa = in.readParcelable(Visa.class.getClassLoader());
    mastercard = in.readParcelable(Mastercard.class.getClassLoader());
  }

  /**
   * Returns the Visa object holding the supported cryptogram types for this network.
   *
   * @return {@link Visa}
   */
  public Visa getVisa() {
    return visa;
  }

  /**
   * Setter for visa cryptogram types.
   *
   * @param visa object holding the cryptogram type supported formats.
   */
  public void setVisa(Visa visa) {
    this.visa = visa;
  }

  /**
   * Returns the Mastercard object holding the supported cryptogram types for this network.
   *
   * @return {@link Mastercard}
   */
  public Mastercard getMastercard() {
    return mastercard;
  }

  /**
   * Setter for mastercard cryptogram types.
   *
   * @param mastercard object holding the cryptogram type supported formats
   */
  public void setMastercard(Mastercard mastercard) {
    this.mastercard = mastercard;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(visa, flags);
    dest.writeParcelable(mastercard, flags);
  }

  @Override public String toString() {
    return "CryptoOptions{" + "visa=" + visa + ", mastercard=" + mastercard + '}';
  }

  /**
   * Specific class for Visa cryptogram types.
   */
  public static class Visa extends BaseCryptoOption implements Parcelable {
    public static final String TAVV = "TAVV";
    public static final Creator<Visa> CREATOR = new Creator<Visa>() {
      @Override public Visa createFromParcel(Parcel in) {
        return new Visa(in);
      }

      @Override public Visa[] newArray(int size) {
        return new Visa[size];
      }
    };

    public Visa() {
    }

    Visa(Parcel in) {
      format = in.createStringArrayList();
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeStringList(format);
    }

    @Override public String toString() {
      return "Visa{" + "format=" + format + '}';
    }
  }

  /**
   * Specific class for Mastercard cryptogram types.
   */
  public static class Mastercard extends BaseCryptoOption implements Parcelable {
    public static final String UCAF = "UCAF";
    public static final String ICC = "ICC";
    public static final Creator<Mastercard> CREATOR = new Creator<Mastercard>() {
      @Override public Mastercard createFromParcel(Parcel in) {
        return new Mastercard(in);
      }

      @Override public Mastercard[] newArray(int size) {
        return new Mastercard[size];
      }
    };

    public Mastercard() {
    }

    Mastercard(Parcel in) {
      format = in.createStringArrayList();
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeStringList(format);
    }

    @Override public String toString() {
      return "Mastercard {" + "format=" + format + '}';
    }
  }

  private static class BaseCryptoOption {
    protected List<String> format;

    private BaseCryptoOption() {
    }

    /**
     * Returns list containing the different supported cryptogram types.
     *
     * @return {@link List} of strings representing supported cryptograms
     */
    public List<String> getFormat() {
      return format;
    }

    /**
     * Setter for a list containing supported cryptograms.
     *
     * @param format list containing supported cryptograms
     */
    public void setFormat(List<String> format) {
      this.format = format;
    }
  }
}
