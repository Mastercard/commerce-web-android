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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class
 */
public class ErrorUtil {

  private ErrorUtil() {
  }

  /**
   * Utility method to check if device is connected to network or not.
   * @param context Activity context
   * @return true if network is connected otherwise false
   */
  public static boolean isNetworkConnected(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

    return ((activeNetworkInfo != null)
        && ((activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
        || (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)));
  }
}
