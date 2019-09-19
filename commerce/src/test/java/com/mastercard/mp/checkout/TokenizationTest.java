package com.mastercard.mp.checkout;

import android.os.Parcel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class TokenizationTest {

  private String unpredictableNumber = "unpredictableNumber";

  @Test public void testTokenization() {

    CryptoOptions cryptoOptions = new CryptoOptions();
    CryptoOptions.Mastercard mastercard = new CryptoOptions.Mastercard();
    CryptoOptions.Visa visa = new CryptoOptions.Visa();
    cryptoOptions.setMastercard(mastercard);
    cryptoOptions.setVisa(visa);

    Tokenization tokenization = new Tokenization(unpredictableNumber, cryptoOptions);

    assertEquals(unpredictableNumber, tokenization.getUnpredictableNumber());
    assertEquals(cryptoOptions, tokenization.getCryptoOptions());

    Parcel parcel = Parcel.obtain();
    tokenization.writeToParcel(parcel, tokenization.describeContents());
    parcel.setDataPosition(0);

    Tokenization tokenizationFromParcel = Tokenization.CREATOR.createFromParcel(parcel);

    assertEquals(tokenization.toString(), tokenizationFromParcel.toString());
  }
}
