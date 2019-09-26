package com.mastercard.commerce;

import android.os.Parcel;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class CryptoOptionsTest {
  private final String expected = "{ cardType: master, format: [UCAF] }";
  private final String expectedVisa = "{ cardType: visa, format: [TVV] }";

  @Test public void cryptoOptions_toString() {
    Set<Mastercard.MastercardFormat> mastercardFormatSet = new LinkedHashSet<>();
    mastercardFormatSet.add(Mastercard.MastercardFormat.UCAF);

    Mastercard mastercard = new Mastercard(mastercardFormatSet);
    String actual = mastercard.toString();

    mastercard.describeContents();

    mastercard.writeToParcel(Mockito.mock(Parcel.class), 1);

    assertEquals(expected, actual);
  }

  @Test public void cryptoOptionsVisa_toString() {
    Set<Visa.VisaFormat> visaFormatSet = new LinkedHashSet<>();
    visaFormatSet.add(Visa.VisaFormat.TVV);

    Visa visa = new Visa(visaFormatSet);
    String actual = visa.toString();

    visa.describeContents();

    visa.writeToParcel(Mockito.mock(Parcel.class), 1);

    assertEquals(expectedVisa, actual);
  }
}
