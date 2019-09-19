package com.mastercard.mp.checkout;

import android.os.Parcel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.mastercard.mp.checkout.NetworkType.MASTER;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class NetworkTypeTest {

  @Test public void testAmount() {

    NetworkType networkType = new NetworkType(MASTER);
    assertEquals(MASTER, networkType.getNetworkType());

    Parcel parcel = Parcel.obtain();
    networkType.writeToParcel(parcel, networkType.describeContents());
    parcel.setDataPosition(0);

    NetworkType networkFromParcel = NetworkType.CREATOR.createFromParcel(parcel);

    assertEquals(networkType.toString(), networkFromParcel.toString());
  }
}
