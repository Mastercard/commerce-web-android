package com.mastercard.commerce;

import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CryptoOptionsTest {
  private final String expected = "{ cardType: master, format: [UCAF] }";

  @Test public void cryptoOptions_toString() {
    Set<Mastercard.MastercardFormat> mastercardFormatSet = new LinkedHashSet<>();
    mastercardFormatSet.add(Mastercard.MastercardFormat.UCAF);

    Mastercard mastercard = new Mastercard(mastercardFormatSet);
    String actual = mastercard.toString();

    assertEquals(expected, actual);
  }
}
