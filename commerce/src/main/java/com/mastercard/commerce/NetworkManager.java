package com.mastercard.commerce;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to perform network requests and return a response
 */
public class NetworkManager {
  private static final String TAG = NetworkManager.class.getSimpleName();
  private static final String DOWNLOAD_ERROR = "Error while downloading";
  private final Scheduler scheduler;

  public NetworkManager(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  void execute(final String buttonUrl, final String requestType, final HttpCallback callback) {
    scheduler.execute(new Runnable() {
      @Override
      public void run() {
        try {
          URL url = new URL(buttonUrl);
          HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
          httpURLConnection.setRequestMethod(requestType);
          httpURLConnection.connect();

          if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String response = getResponse(httpURLConnection.getInputStream());

            Log.d(TAG, "response = " + response);
            scheduler.notifyResponse(response, callback);
          } else {
            scheduler.notifyError(DOWNLOAD_ERROR, callback);
          }
        } catch (MalformedURLException e) {
          Log.e(TAG, e.getLocalizedMessage(), e);
          scheduler.notifyError(DOWNLOAD_ERROR, callback);
        } catch (IOException e) {
          Log.e(TAG, e.getLocalizedMessage(), e);
          scheduler.notifyError(DOWNLOAD_ERROR, callback);
        }
      }
    });
  }

  private String getResponse(InputStream in) {
    BufferedReader reader = null;
    StringBuffer response = new StringBuffer();
    try {
      reader = new BufferedReader(new InputStreamReader(in));
      String line = "";
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
    } catch (IOException e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          Log.e(TAG, e.getLocalizedMessage(), e);
          e.printStackTrace();
        }
      }
    }
    return response.toString();
  }
}
