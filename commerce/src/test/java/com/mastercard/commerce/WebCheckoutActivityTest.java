package com.mastercard.commerce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class) @Config(sdk = Build.VERSION_CODES.O_MR1) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*",
})
public class WebCheckoutActivityTest {

  private final TestUtils testUtils = new TestUtils();

  @Test public void lunchWebCheckoutActivity() {

    WebResourceRequest webResourceRequest = Mockito.mock(WebResourceRequest.class);
    CommerceConfig config = testUtils.getCommerceConfig();
    CheckoutRequest request = testUtils.getCheckoutRequest();
    String preparedUrl = SrcCheckoutUrlUtil.getCheckoutUrl(config, request);
    Uri uri = Uri.parse(preparedUrl);
    WebView mockedWebView = Mockito.mock(WebView.class);
    WebView.HitTestResult hitTestResult = Mockito.mock(WebView.HitTestResult.class);
    Message message = Mockito.mock(Message.class);
    message.obj = Mockito.mock(WebView.WebViewTransport.class);

    Mockito.when(webResourceRequest.getUrl()).thenReturn(uri);
    Mockito.when(mockedWebView.getHitTestResult()).thenReturn(hitTestResult);
    Mockito.when(hitTestResult.getType()).thenReturn(0);
    Mockito.doNothing().when(message).sendToTarget();

    Intent intent =
        new Intent(ApplicationProvider.getApplicationContext(), WebCheckoutActivity.class)
            .putExtra(WebCheckoutActivity.CHECKOUT_URL_EXTRA, "checkoutUrl")
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    WebCheckoutActivity activity =
        Robolectric.buildActivity(WebCheckoutActivity.class, intent).setup().get();

    WebView webView = activity.findViewById(R.id.webview);

    webView.getWebViewClient().shouldOverrideUrlLoading(mockedWebView, webResourceRequest);

    webView.getWebViewClient().onPageStarted(mockedWebView, "url", Mockito.mock(Bitmap.class));

    webView.getWebChromeClient().onCreateWindow(mockedWebView, false, false, message);

    assertTrue(webView.getVisibility() == View.VISIBLE);
  }
}