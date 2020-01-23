package com.mastercard.commerce;

import android.content.Context;
import android.content.Intent;

public interface WebViewActivityInterface {

  Context getContext();

  void showProgressDialog();

  void dismissProgressDialog();

  void startActivity(Intent intent);

  void handleIntent(String intentUriString);
}
