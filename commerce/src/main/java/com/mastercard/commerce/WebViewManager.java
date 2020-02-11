package com.mastercard.commerce;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class WebViewManager {
  private static final String INTENT_SCHEME = "intent";
  private static final String TAG = WebViewManager.class.getSimpleName();
  private WebViewManagerCallback webViewManagerCallback;
  private Context context;
  private List<WebView> webViewList;
  private static final String HASH = "#";

  WebViewManager(WebViewManagerCallback webViewManagerCallback, Context context) {
    this.webViewManagerCallback = webViewManagerCallback;
    this.context = context;
    this.webViewList = new ArrayList<>();
  }

  /**
   * returns the first Web View to be added to the layout
   */
  WebView getFirstWebView() {
    return addWebView(true);
  }

  void destroyWebviews() {
    for (WebView webView : webViewList) {
      webView.clearHistory();

      // NOTE: clears RAM cache, if you pass true, it will also clear the disk cache.
      // Probably not a great idea to pass true if you have other WebViews still alive.
      webView.clearCache(true);

      // Loading a blank page is optional, but will ensure that the WebView isn't doing anything when you destroy it.
      webView.loadUrl("about:blank");

      webView.onPause();
      webView.removeAllViews();
      webView.destroyDrawingCache();

      // NOTE: This pauses JavaScript execution for ALL WebViews,
      // do not use if you have other WebViews still alive.
      // If you create another WebView after calling this,
      // make sure to call mWebView.resumeTimers().
      webView.pauseTimers();

      // NOTE: This can occasionally cause a segfault below API 17 (4.2)
      webView.destroy();

      // Null out the reference so that you don't end up re-using it.
      webView = null;
      //}
    }
  }

  private WebView addWebView(final boolean isSRCi) {
    webViewManagerCallback.showProgressDialog();
    final WebView webView = new WebView(context);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setSupportMultipleWindows(true);
    webView.getSettings().setSupportZoom(false);
    webView.getSettings().setBuiltInZoomControls(true);
    webView.getSettings().setDisplayZoomControls(false);
    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    webView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));

    webViewList.add(webView);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
    }

    //This webViewClient will override an intent loading action to startActivity
    webView.setWebViewClient(new WebViewClient() {
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
      }

      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String urlScheme = URI.create(url).getScheme();

        Log.d(TAG, "Navigating to: " + url);

        if (urlScheme.equals(INTENT_SCHEME)) {
          webViewManagerCallback.handleIntent(url);

          return true;
        } else {

          return false;
        }
      }

      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (isSRCi) {
          webViewManagerCallback.dismissProgressDialog();
        }
        super.onPageStarted(view, url, favicon);
      }

      @Override public void onPageFinished(WebView view, String url) {
        if (!isSRCi) {
          webView.setBackgroundColor(Color.WHITE);
          webViewManagerCallback.dismissProgressDialog();
        }
        super.onPageFinished(view, url);
      }

      @Override
      public void onReceivedSslError(final WebView view, final SslErrorHandler handler,
          final SslError error) {
        if (BuildConfig.DEBUG) {
          handler.proceed();
        } else {
          handler.cancel();
        }
      }
    });

    webView.setWebChromeClient(new WebChromeClient() {

      @Override public void onCloseWindow(WebView window) {
        Log.d(TAG, "onCloseWindow webview --------------------");
        if (webViewList.size() > 1) {
          WebView webViewContainer = webViewList.get(webViewList.size() - 2);
          WebView webViewClosed = webViewList.get(webViewList.size() - 1);
          webViewContainer.removeView(webViewClosed);
          webViewClosed.destroy();
          webViewList.remove(webViewClosed);
        } else {
          Log.d(TAG, "Closing window... maybe?");
        }
      }

      @SuppressLint("SetJavaScriptEnabled") @Override
      public boolean onCreateWindow(final WebView view, boolean isDialog, boolean isUserGesture,
          Message resultMsg) {
        WebView.HitTestResult result = view.getHitTestResult();

        if(result.getExtra() != null) {
          if (result.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE && !result.getExtra()
              .endsWith(HASH)) {
            //If the user has selected an anchor link, open in browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getExtra()));
            webViewManagerCallback.startActivity(browserIntent);

            return false;
          }
        }

        WebView webView2 = addWebView(false);

        view.addView(webView2);

        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(webView2);
        resultMsg.sendToTarget();

        if (webViewList.size() > 1) {
          webViewList.get(webViewList.size() - 2).scrollTo(0, 0);
        }

        return true;
      }
    });

    return webView;
  }
}
