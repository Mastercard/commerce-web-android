package com.mastercard.commerce;

import android.app.Activity;
import android.content.Intent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class) public class CommerceWebSdkTest {
  /*private final TestUtils testUtils = new TestUtils();

  @Test public void initiate_checkout() {
    CommerceConfig config = testUtils.getCommerceConfig();
    CheckoutRequest request = testUtils.getCheckoutRequest();
    Activity activity = mock(Activity.class);
    CommerceWebSdk commerceWebSdk = CommerceWebSdk.getInstance();
    commerceWebSdk.initialize(config);
    commerceWebSdk.checkout(activity, request);

    verify(activity).startActivityForResult(any(Intent.class),
        eq(CommerceWebSdk.COMMERCE_REQUEST_CODE));
  }*/
}