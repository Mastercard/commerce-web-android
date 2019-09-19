package com.mastercard.commerce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "androidx.*"})
@PrepareForTest({ CheckoutButtonManager.class, ErrorUtil.class, SrcCheckoutUrlUtil.class })
public class CommerceWebSdkTest {
  private final TestUtils testUtils = new TestUtils();

  @Rule
  public PowerMockRule rule = new PowerMockRule();

  @Test public void initiate_checkout() {
    CommerceConfig config = testUtils.getCommerceConfig();
    CheckoutRequest request = testUtils.getCheckoutRequest();
    Activity activity = mock(Activity.class);
    CheckoutButtonManager checkoutButtonManager = mock(CheckoutButtonManager.class);
    DownloadCheckoutButton downloadCheckoutButton = mock(DownloadCheckoutButton.class);
    Intent intent = mock(Intent.class);

    try {
      PowerMockito.whenNew(DownloadCheckoutButton.class)
          .withAnyArguments()
          .thenReturn(downloadCheckoutButton);
      PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }

    PowerMockito.mockStatic(CheckoutButtonManager.class);
    when(CheckoutButtonManager.getInstance()).thenReturn(checkoutButtonManager);
    when(checkoutButtonManager.getCheckoutButton(any(CheckoutButton.CheckoutButtonClickListener.class))).thenReturn(mock(CheckoutButton.class));

    PowerMockito.mockStatic(ErrorUtil.class);
    when(ErrorUtil.isNetworkConnected(any(Context.class))).thenReturn(true);

    PowerMockito.mockStatic(SrcCheckoutUrlUtil.class);
    when(SrcCheckoutUrlUtil.getCheckoutUrl(any(CommerceConfig.class),
        any(CheckoutRequest.class))).thenReturn("URL");

    CommerceWebSdk commerceWebSdk = CommerceWebSdk.getInstance();
    commerceWebSdk.initialize(activity, config);
    commerceWebSdk.checkout(request);

    assertNotNull(commerceWebSdk.getCheckoutButton(new CheckoutCallback() {
      @Override public void getCheckoutRequest(CheckoutRequestListener listener) {
      }
    }));
  }
}