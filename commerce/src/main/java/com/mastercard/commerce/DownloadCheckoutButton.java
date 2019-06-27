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

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadCheckoutButton extends AsyncTask<String, Void, String> {
  private String dynamicButtonUrl;
  private CheckoutButtonDownloadedListener checkoutButtonDownloadedListener;

  public DownloadCheckoutButton(String dynamicButtonUrl,
      CheckoutButtonDownloadedListener checkoutButtonDownloadedListener) {
    this.dynamicButtonUrl = dynamicButtonUrl;
    this.checkoutButtonDownloadedListener = checkoutButtonDownloadedListener;
  }

  @Override protected String doInBackground(String... strings) {
    String serverResponse = null;
    try {
      URL url = new URL(dynamicButtonUrl);
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");
      httpURLConnection.connect();

      int responseCode = httpURLConnection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        serverResponse = readStream(httpURLConnection.getInputStream());
        Log.d("DownloadCheckoutButton", "doInBackground = " +serverResponse);

      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return serverResponse;
  }

  @Override protected void onPostExecute(String responseString) {
    super.onPostExecute(responseString);
    if (checkoutButtonDownloadedListener != null && responseString != null) {
      checkoutButtonDownloadedListener.checkoutButtonDownloadSuccess(responseString);
    } else {
      checkoutButtonDownloadedListener.checkoutButtonDownloadError();
    }
  }

  // Converting InputStream to String
  private String readStream(InputStream in) {
    BufferedReader reader = null;
    StringBuffer response = new StringBuffer();
    try {
      reader = new BufferedReader(new InputStreamReader(in));
      String line = "";
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return response.toString();
  }

  public interface CheckoutButtonDownloadedListener {
    void checkoutButtonDownloadSuccess(String responseData);

    void checkoutButtonDownloadError();
  }
}
