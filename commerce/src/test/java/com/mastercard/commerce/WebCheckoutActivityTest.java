package com.mastercard.commerce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import androidx.test.core.app.ApplicationProvider;
import com.mastercard.mp.checkout.MasterpassCheckoutCallback;
import com.mastercard.mp.checkout.MasterpassMerchant;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(RobolectricTestRunner.class) @Config(sdk = Build.VERSION_CODES.O_MR1) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*",
})
@PrepareForTest({MasterpassMerchant.class})
public class WebCheckoutActivityTest {
  private WebResourceRequest webResourceRequest;
  private WebView mockedWebView;
  private WebView.HitTestResult hitTestResult;
  private Message message;

  private final TestUtils testUtils = new TestUtils();
  private String checkoutUrl = "checkoutUrl";
  private String url = "url";
  private String preparedUrl = "intent://scan/#Intent;scheme=zxing;package=com.google.zxing.client.android;end";

  @Rule public PowerMockRule rule = new PowerMockRule();

  @Before
  public void setUp(){
    webResourceRequest = mock(WebResourceRequest.class);
    mockedWebView = mock(WebView.class);
    hitTestResult = mock(WebView.HitTestResult.class);
    message = mock(Message.class);
    message.obj = mock(WebView.WebViewTransport.class);

    when(mockedWebView.getHitTestResult()).thenReturn(hitTestResult);
    when(hitTestResult.getType()).thenReturn(0);
    doNothing().when(message).sendToTarget();
  }


  @Test public void lunchWebCheckoutActivity() {
    CommerceConfig config = testUtils.getCommerceConfig();
    CheckoutRequest request = testUtils.getCheckoutRequestWAllArguments();
    String preparedUrl2 = SrcCheckoutUrlUtil.getCheckoutUrl(config, request);
    Uri uri = Uri.parse(preparedUrl2);

    when(webResourceRequest.getUrl()).thenReturn(uri);

    Intent intent =
        new Intent(ApplicationProvider.getApplicationContext(), WebCheckoutActivity.class)
            .putExtra(WebCheckoutActivity.CHECKOUT_URL_EXTRA, checkoutUrl)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    WebCheckoutActivity activity =
        Robolectric.buildActivity(WebCheckoutActivity.class, intent).setup().get();

    WebView webView = Whitebox.getInternalState(activity, "sRCiWebView");
    webView.getWebViewClient().shouldOverrideUrlLoading(mockedWebView, webResourceRequest);
    webView.getWebViewClient().onPageStarted(mockedWebView, url, mock(Bitmap.class));
    webView.getWebViewClient().onReceivedSslError(mockedWebView, mock(SslErrorHandler.class), mock(
        SslError.class));
    webView.getWebChromeClient().onCreateWindow(mockedWebView, false, false, message);

    assertEquals(webView.getVisibility(), View.VISIBLE);

    activity.onStop();

    activity.onDestroy();

    assertEquals(0, webView.getChildCount());
  }


  @Test (expected = IllegalStateException.class)
  public void intent_IllegalStateException() {
    Uri uri = Uri.parse(preparedUrl);

    when(webResourceRequest.getUrl()).thenReturn(uri);

    Intent intent =
        new Intent(ApplicationProvider.getApplicationContext(), WebCheckoutActivity.class)
            .putExtra(WebCheckoutActivity.CHECKOUT_URL_EXTRA, checkoutUrl)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    WebCheckoutActivity activity =
        Robolectric.buildActivity(WebCheckoutActivity.class, intent).setup().get();

    WebView webView = Whitebox.getInternalState(activity, "sRCiWebView");

    webView.getWebViewClient().shouldOverrideUrlLoading(mockedWebView, webResourceRequest);
  }


  @Test
  public void intent_handleMasterpassCallback() {
    mockStatic(MasterpassMerchant.class);
    Uri uri = Uri.parse(preparedUrl);

    when(webResourceRequest.getUrl()).thenReturn(uri);
    when(MasterpassMerchant.getCheckoutCallback()).thenReturn(mock(MasterpassCheckoutCallback.class));

    Intent intent =
        new Intent(ApplicationProvider.getApplicationContext(), WebCheckoutActivity.class)
            .putExtra(WebCheckoutActivity.CHECKOUT_URL_EXTRA, checkoutUrl)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    WebCheckoutActivity activity =
        Robolectric.buildActivity(WebCheckoutActivity.class, intent).setup().get();

    WebView webView = Whitebox.getInternalState(activity, "sRCiWebView");

    webView.getWebViewClient().shouldOverrideUrlLoading(mockedWebView, webResourceRequest);

    PowerMockito.verifyStatic(VerificationModeFactory.times(1));
    MasterpassMerchant.isMerchantInitiated();
  }
}