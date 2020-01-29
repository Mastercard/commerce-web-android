package com.mastercard.commerce;

import android.content.Intent;

public interface WebViewManagerCallback {

  void showProgressDialog();

  void dismissProgressDialog();

  void startActivity(Intent intent);

  void handleIntent(String intentUriString);
}
