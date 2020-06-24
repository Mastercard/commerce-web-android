/* Copyright © 2019 Mastercard. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 =============================================================================*/

package com.mastercard.commerce;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mastercard.mp.checkout.CheckoutResponseConstants;
import com.mastercard.mp.checkout.MasterpassError;
import com.mastercard.mp.checkout.MasterpassMerchant;
import java.net.URISyntaxException;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static com.mastercard.commerce.CommerceWebSdk.COMMERCE_STATUS;
import static com.mastercard.commerce.CommerceWebSdk.COMMERCE_TRANSACTION_ID;

/**
 * Activity used to initiate WebView with the SRCi URL. This Activity will handle the callback to
 * the application when SRCi loads {@code callbackUrl}.
 */
public final class WebCheckoutActivity extends AppCompatActivity
    implements WebViewManagerCallback {
  public static final String CHECKOUT_URL_EXTRA = "CHECKOUT_URL_EXTRA";
  private static final String TAG = WebCheckoutActivity.class.getSimpleName();
  private static final String QUERY_PARAM_MASTERPASS_TRANSACTION_ID = "oauth_token";
  private static final String QUERY_PARAM_MASTERPASS_STATUS = "mpstatus";
  private static final String STATUS_SUCCESS = "success";
  private static final String STATUS_CANCEL = "cancel";
  private BroadcastReceiver receiver;
  private ProgressDialog progressdialog;
  private Snackbar snackBar;
  private WebViewManager webViewManager;
  private WebView sRCiWebView;

  @SuppressLint("SetJavaScriptEnabled") @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    progressdialog = new ProgressDialog(this);
    progressdialog.setMessage(getResources().getString(R.string.loading_web_view));
    progressdialog.setCancelable(true);
    progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    String url = getIntent().getStringExtra(CHECKOUT_URL_EXTRA);

    Log.d(TAG, "URL to load: " + url);

    WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);

    webViewManager = new WebViewManager(this, this);
    RelativeLayout containerLayout = findViewById(R.id.webview_container);
    sRCiWebView = webViewManager.getFirstWebView();
    sRCiWebView.resumeTimers();
    sRCiWebView.loadUrl(url);
    receiver = getReceiver();
    containerLayout.addView(sRCiWebView);
  }

  @Override protected void onStart() {
    super.onStart();
    IntentFilter filter = new IntentFilter();
    filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    registerReceiver(receiver, filter);
  }

  @Override protected void onStop() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      CookieManager.getInstance().flush();
    } else {
      CookieSyncManager.getInstance().sync();
    }
    unregisterReceiver(receiver);
    super.onStop();
  }

  @Override protected void onDestroy() {
    destroyWebviews();
    super.onDestroy();
  }

  private void destroyWebviews() {
    // Make sure you remove the WebView from its parent view before doing anything.
    ViewGroup webviewContainer = findViewById(R.id.webview_container);
    webviewContainer.removeAllViews();
    webViewManager.destroyWebviews();
  }

  private BroadcastReceiver getReceiver() {
    return new BroadcastReceiver() {
      @Override public void onReceive(Context context, Intent intent) {

        ConnectivityManager manager =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          NetworkRequest networkRequest = new NetworkRequest.Builder()
              .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
              .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
              .build();

          manager.registerNetworkCallback(networkRequest,
              new ConnectivityManager.NetworkCallback() {
                @Override public void onAvailable(Network network) {
                  super.onAvailable(network);
                  if (snackBar != null) {
                    snackBar.dismiss();
                  }
                }

                @Override public void onLost(Network network) {
                  super.onLost(network);
                  showSnackBar();
                }
              });
        } else {
          NetworkInfo networkInfo = manager.getActiveNetworkInfo();
          if (networkInfo != null
              && networkInfo.isConnected()) {
            if (snackBar != null) {
              snackBar.dismiss();
            }
          } else {
            showSnackBar();
          }
        }
      }
    };
  }

  private void showSnackBar() {
    View view = findViewById(R.id.webview_container);
      snackBar =
          Snackbar.make(view, getString(R.string.error_dialog_connectivity_title),
              Snackbar.LENGTH_INDEFINITE);
      View snackBarView = snackBar.getView();
      TextView snackBarText =
          snackBarView.findViewById(android.support.design.R.id.snackbar_text);
      snackBarText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
      snackBarView.setBackgroundColor(
          ContextCompat.getColor(WebCheckoutActivity.this, R.color.color_snackbar_error));
      snackBar.show();
  }

  @Override public void showProgressDialog() {
    progressdialog.show();
  }

  @Override public void dismissProgressDialog() {
    progressdialog.dismiss();
  }

  @Override public void handleIntent(String intentUriString) {
    try {
      Intent intent = Intent.parseUri(intentUriString, Intent.URI_INTENT_SCHEME);
      String intentApplicationPackage = intent.getPackage();
      String currentApplicationPackage = getApplication().getApplicationInfo().packageName;
      Uri intentUri = Uri.parse(intentUriString);
      String transactionId = intentUri.getQueryParameter(QUERY_PARAM_MASTERPASS_TRANSACTION_ID);
      String status = intentUri.getQueryParameter(QUERY_PARAM_MASTERPASS_STATUS);

      if (MasterpassMerchant.getCheckoutCallback() != null) {
        //We're in a v7 flow
        handleMasterpassCallback(status, transactionId);
      } else if (STATUS_CANCEL.equals(status)) {
        finish();
      } else if (intentApplicationPackage != null &&
          intentApplicationPackage.equals(currentApplicationPackage)) {
        intent.putExtra(COMMERCE_TRANSACTION_ID, transactionId);
        intent.putExtra(COMMERCE_STATUS, status);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
      } else {
        throw new IllegalStateException(
            "The Intent is not valid for this application: " + intentUriString);
      }
    } catch (URISyntaxException e) {
      Log.e(TAG,
          "Unable to parse Intent URI. You must provide a valid Intent URI or checkout will never work.",
          e);

      throw new IllegalStateException(e);
    }
  }

  private void handleMasterpassCallback(String status, String transactionId) {
    Bundle responseBundle = new Bundle();

    if (STATUS_SUCCESS.equals(status) && transactionId != null) {
      responseBundle.putString(CheckoutResponseConstants.TRANSACTION_ID, transactionId);

      MasterpassMerchant.getCheckoutCallback().onCheckoutComplete(responseBundle);
    } else if (MasterpassMerchant.isMerchantInitiated() && STATUS_CANCEL.equals(status)) {
      MasterpassMerchant.getCheckoutCallback().onCheckoutError(new MasterpassError(
          MasterpassError.ERROR_CODE_CANCEL_WALLET, "User selected cancel wallet"));
    }

    finish();
  }
}