package com.mastercard.mp.checkout;

import android.os.Parcel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class AmountTest {

  private long currencyAmount = 11;
  private String currencyCode = "USD";
  private String currencyNumber = "2";

  @Test public void testAmount() {
    Amount amount = new Amount(currencyAmount, currencyCode);

    amount.setCurrencyCode(currencyCode);
    amount.setCurrencyNumber(currencyNumber);
    amount.setTotal(currencyAmount);

    assertEquals(currencyAmount, amount.getTotal());
    assertEquals(currencyCode, amount.getCurrencyCode());
    assertEquals(currencyNumber, amount.getCurrencyNumber());

    Parcel parcel = Parcel.obtain();
    amount.writeToParcel(parcel, amount.describeContents());
    parcel.setDataPosition(0);

    Amount amountFromParcel = Amount.CREATOR.createFromParcel(parcel);

    assertEquals(amount.toString(), amountFromParcel.toString());
  }
}
