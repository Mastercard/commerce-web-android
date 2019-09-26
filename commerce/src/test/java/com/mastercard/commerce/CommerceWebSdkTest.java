package com.mastercard.commerce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "androidx.*"})
@PrepareForTest({ CheckoutButtonManager.class, ErrorUtil.class, SrcCheckoutUrlUtil.class })
public class CommerceWebSdkTest {
  private final TestUtils testUtils = new TestUtils();
  private CommerceConfig config;
  private CheckoutRequest request;
  private Activity activity;
  private Intent intent;
  private CheckoutButtonManager checkoutButtonManager;
  private DownloadCheckoutButton downloadCheckoutButton;
  //private Context context;
  private CommerceWebSdk commerceWebSdk;
  private ConfigurationManager configManager;

  @Rule
  public PowerMockRule rule = new PowerMockRule();


  @Before
  public void setUp() {
    config = testUtils.getCommerceConfig();
    request = testUtils.getCheckoutRequest();
    checkoutButtonManager = mock(CheckoutButtonManager.class);
    activity = mock(Activity.class);
    intent = mock(Intent.class);
    downloadCheckoutButton = mock(DownloadCheckoutButton.class);
    commerceWebSdk = CommerceWebSdk.getInstance();
    configManager = ConfigurationManager.getInstance();

    try {
      PowerMockito.whenNew(DownloadCheckoutButton.class)
          .withAnyArguments()
          .thenReturn(downloadCheckoutButton);
      PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }

    mockStatic(CheckoutButtonManager.class);
    when(CheckoutButtonManager.getInstance()).thenReturn(checkoutButtonManager);
    when(checkoutButtonManager.getCheckoutButton(any(CheckoutButton.CheckoutButtonClickListener.class))).thenReturn(mock(CheckoutButton.class));

    mockStatic(ErrorUtil.class);
    when(ErrorUtil.isNetworkConnected(any(Context.class))).thenReturn(true);

    mockStatic(SrcCheckoutUrlUtil.class);
    when(SrcCheckoutUrlUtil.getCheckoutUrl(any(CommerceConfig.class),
        any(CheckoutRequest.class))).thenReturn("URL");

  }


  //@Test public void initiate_checkout() {
  //
  //  PowerMockito.mockStatic(CheckoutButtonManager.class);
  //  when(CheckoutButtonManager.getInstance()).thenReturn(checkoutButtonManager);
  //  when(checkoutButtonManager.getCheckoutButton(any(CheckoutButton.CheckoutButtonClickListener.class))).thenReturn(mock(CheckoutButton.class));
  //
  //  PowerMockito.mockStatic(ErrorUtil.class);
  //  when(ErrorUtil.isNetworkConnected(any(Context.class))).thenReturn(true);
  //
  //  PowerMockito.mockStatic(SrcCheckoutUrlUtil.class);
  //  when(SrcCheckoutUrlUtil.getCheckoutUrl(any(CommerceConfig.class),
  //      any(CheckoutRequest.class))).thenReturn("URL");
  //
  //  CommerceWebSdk commerceWebSdk = CommerceWebSdk.getInstance();
  //  commerceWebSdk.initialize(activity, config);
  //  ConfigurationManager configManager = ConfigurationManager.getInstance();
  //
  //  assertEquals(configManager.getConfiguration(), config);
  //
  //  commerceWebSdk.checkout(request);
  //
  //  assertNotNull(commerceWebSdk.getCheckoutButton(new CheckoutCallback() {
  //    @Override public void getCheckoutRequest(CheckoutRequestListener listener) {
  //    }
  //  }));
  //}


  @Test public void commerceWebSdk_initialize_checkout() {
    commerceWebSdk.initialize(activity, config);
    assertEquals(configManager.getConfiguration(), config);

  }

  @Test public void commerceWebSdk_checkout_startCheckoutActivity(){
    ArgumentCaptor<CheckoutButton.CheckoutButtonClickListener> clickListenerArgumentCaptor = ArgumentCaptor.forClass(
        CheckoutButton.CheckoutButtonClickListener.class);
    ArgumentCaptor<CheckoutCallback.CheckoutRequestListener> requestListenerArgumentCaptor = ArgumentCaptor.forClass(
        CheckoutCallback.CheckoutRequestListener.class);
    CheckoutCallback checkoutCallback = mock(CheckoutCallback.class);

    commerceWebSdk.initialize(activity, config);

    commerceWebSdk.getCheckoutButton(checkoutCallback);

    verify(checkoutButtonManager).getCheckoutButton(clickListenerArgumentCaptor.capture());

    CheckoutButton.CheckoutButtonClickListener clickListener = clickListenerArgumentCaptor.getValue();
    clickListener.onClick();

    verify(checkoutCallback).getCheckoutRequest(requestListenerArgumentCaptor.capture());

    CheckoutCallback.CheckoutRequestListener requestListener = requestListenerArgumentCaptor.getValue();
    requestListener.setRequest(request);

    verify(activity).startActivity(any(Intent.class));
  }

  @Test public void commerceWebSdk_checkout_launchErrorActivity(){
    mockStatic(ErrorUtil.class);
    ArgumentCaptor<CheckoutButton.CheckoutButtonClickListener> clickListenerArgumentCaptor = ArgumentCaptor.forClass(
        CheckoutButton.CheckoutButtonClickListener.class);
    ArgumentCaptor<CheckoutCallback.CheckoutRequestListener> requestListenerArgumentCaptor = ArgumentCaptor.forClass(
        CheckoutCallback.CheckoutRequestListener.class);
    CheckoutCallback checkoutCallback = mock(CheckoutCallback.class);

    when(ErrorUtil.isNetworkConnected(any(Context.class))).thenReturn(false);

    commerceWebSdk.initialize(activity, config);

    commerceWebSdk.getCheckoutButton(checkoutCallback);

    verify(checkoutButtonManager).getCheckoutButton(clickListenerArgumentCaptor.capture());

    CheckoutButton.CheckoutButtonClickListener clickListener = clickListenerArgumentCaptor.getValue();
    clickListener.onClick();

    verify(checkoutCallback).getCheckoutRequest(requestListenerArgumentCaptor.capture());

    CheckoutCallback.CheckoutRequestListener requestListener = requestListenerArgumentCaptor.getValue();
    requestListener.setRequest(request);

    verify(activity).startActivity(any(Intent.class));
  }
}