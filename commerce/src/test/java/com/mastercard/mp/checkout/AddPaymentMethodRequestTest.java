package com.mastercard.mp.checkout;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class) public class AddPaymentMethodRequestTest {

  @Mock NetworkType networkType;
  private final String checkoutId = "checkoutId";
  private final String merchantUserId = "merchantUserId";

  @Test public void testRequest() {
    AddPaymentMethodRequest request =
        new AddPaymentMethodRequest(createListNetworkType(), checkoutId, merchantUserId);

    assertTrue(request.getAllowedNetworkTypes().contains(networkType));
    assertEquals(checkoutId, request.getCheckoutId());
    assertEquals(merchantUserId, request.getMerchantUserId());
  }

  private List<NetworkType> createListNetworkType() {
    List<NetworkType> list = new ArrayList<>();
    list.add(networkType);
    return list;
  }
}
