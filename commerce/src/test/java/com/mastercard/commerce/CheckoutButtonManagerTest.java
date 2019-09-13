package com.mastercard.commerce;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*"})
@PrepareForTest({ConfigurationManager.class, Log.class, Uri.Builder.class})
public class CheckoutButtonManagerTest {

  @Rule public PowerMockRule rule = new PowerMockRule();

  @Test
  public void checkoutButtonManager_getInstance() {
    ConfigurationManager configurationManager = Mockito.mock(ConfigurationManager.class);
    mockStatic(ConfigurationManager.class);
    mockStatic(Log.class);

    CommerceConfig config = Mockito.mock(CommerceConfig.class);
    Context context = Mockito.mock(Context.class);
    Uri.Builder uriBuilder = Mockito.mock(Uri.Builder.class);
    when(ConfigurationManager.getInstance()).thenReturn(configurationManager);
    when(configurationManager.getContext()).thenReturn(context);
    when(configurationManager.getConfiguration()).thenReturn(config);
    when(config.getCheckoutId()).thenReturn("checkoutId");
    try {
      PowerMockito.whenNew(Uri.Builder.class).withAnyArguments().thenReturn(uriBuilder);
    } catch (Exception e) {
      e.printStackTrace();
    }

    when(uriBuilder.encodedPath(anyString())).thenReturn(uriBuilder);

    CheckoutButtonManager manager = CheckoutButtonManager.getInstance();

    assertNotNull(manager.getCheckoutButton(Mockito.mock(CheckoutButton.CheckoutButtonClickListener.class)));

  }

  @Test
  public void checkoutButtonManager_getCheckoutButton() {
  }

  @Test
  public void checkoutButtonManager_checkoutButtonDownloadSuccess() {
  }

  @Test
  public void checkoutButtonManager_checkoutButtonDownloadError() {
  }
}