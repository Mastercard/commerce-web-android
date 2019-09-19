package com.mastercard.mp.checkout;

import android.os.Parcel;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class CryptoOptionsTest {

  private final String format = "Format";
  private CryptoOptions.Mastercard mastercard;
  private CryptoOptions.Visa visa;

  @Before public void setUp() {
    mastercard = new CryptoOptions.Mastercard();
    mastercard.setFormat(Collections.singletonList(format));
    visa = new CryptoOptions.Visa();
    visa.setFormat(Collections.singletonList(format));
  }

  @Test public void testCryptoOptions() {
    CryptoOptions cryptoOptions = new CryptoOptions();
    cryptoOptions.setMastercard(mastercard);
    cryptoOptions.setVisa(visa);

    assertEquals(mastercard, cryptoOptions.getMastercard());
    assertEquals(visa, cryptoOptions.getVisa());
    assertEquals(format, mastercard.getFormat().get(0));
    assertEquals(format, visa.getFormat().get(0));

    Parcel parcel = Parcel.obtain();
    cryptoOptions.writeToParcel(parcel, cryptoOptions.describeContents());
    parcel.setDataPosition(0);

    CryptoOptions coFromParcel = CryptoOptions.CREATOR.createFromParcel(parcel);

    assertEquals(cryptoOptions.toString(), coFromParcel.toString());
  }

  @Test public void testParcelables() {
    Parcel parcel = Parcel.obtain();
    mastercard.writeToParcel(parcel, mastercard.describeContents());
    visa.writeToParcel(parcel, visa.describeContents());
    parcel.setDataPosition(0);

    CryptoOptions.Mastercard mcFromParcel =
        CryptoOptions.Mastercard.CREATOR.createFromParcel(parcel);
    CryptoOptions.Visa visaFromParcel = CryptoOptions.Visa.CREATOR.createFromParcel(parcel);

    assertEquals(mastercard.toString(), mcFromParcel.toString());
    assertEquals(visa.toString(), visaFromParcel.toString());
  }
}
