package com.mastercard.commerce;

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
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.reflect.Whitebox;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class) @Config(sdk = Build.VERSION_CODES.O_MR1) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*",
})
public class WebViewManagerTest {
  private WebResourceRequest webResourceRequest;
  private WebView mockedWebView;
  private WebView.HitTestResult hitTestResult;
  private Message message;

  private final TestUtils testUtils = new TestUtils();
  //private String checkoutUrl = "checkoutUrl";
  private String url = "url";
  //private String preparedUrl = "intent://scan/#Intent;scheme=zxing;package=com.google.zxing.client.android;end";

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

    CommerceConfig config = testUtils.getCommerceConfig();
    CheckoutRequest request = testUtils.getCheckoutRequestWAllArguments();
    String preparedUrl2 = SrcCheckoutUrlUtil.getCheckoutUrl(config, request);
    Uri uri = Uri.parse(preparedUrl2);

    when(webResourceRequest.getUrl()).thenReturn(uri);
  }

  @Test public void getFirstWebViewTest() {
    WebViewManager webViewManager = new WebViewManager(mock(WebViewManagerCallback.class),
        ApplicationProvider.getApplicationContext());
    WebView firstWebView = webViewManager.getFirstWebView();

    firstWebView.getWebViewClient().shouldOverrideUrlLoading(mockedWebView, webResourceRequest);
    firstWebView.getWebViewClient().onPageStarted(mockedWebView, url, mock(Bitmap.class));
    firstWebView.getWebViewClient().onReceivedSslError(mockedWebView, mock(SslErrorHandler.class), mock(
        SslError.class));
    firstWebView.getWebChromeClient().onCreateWindow(mockedWebView, false, false, message);

    List<WebView> webViewList = Whitebox.getInternalState(webViewManager, "webViewList");
    WebView secondWebView = webViewList.get(1);
    secondWebView.getWebViewClient().shouldOverrideUrlLoading(mockedWebView, webResourceRequest);
    secondWebView.getWebViewClient().onReceivedSslError(mockedWebView, mock(SslErrorHandler.class), mock(
        SslError.class));
    secondWebView.getWebViewClient().onPageFinished(mockedWebView, url);
    secondWebView.getWebChromeClient().onCreateWindow(mockedWebView, false, false, message);
    secondWebView.getWebChromeClient().onCloseWindow(mockedWebView);

    assertEquals(secondWebView.getVisibility(), View.VISIBLE);

    webViewManager.destroyWebviews();

    assertEquals(0, firstWebView.getChildCount());
    assertEquals(0, secondWebView.getChildCount());

  }

}