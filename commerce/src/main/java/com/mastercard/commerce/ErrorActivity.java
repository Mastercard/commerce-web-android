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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.mastercard.mp.checkout.MasterpassError;
import com.mastercard.mp.checkout.MasterpassMerchant;

/**
 * Activity used to show error popup in no network scenario.
 */
public final class ErrorActivity extends AppCompatActivity {

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    showConnectivityErrorDialog();
  }

  private void showConnectivityErrorDialog() {
    AlertDialog.Builder alert = new AlertDialog.Builder(this);

    alert.setTitle(getResources().getString(R.string.error_dialog_connectivity_title));
    alert.setMessage(getResources().getString(R.string.error_dialog_connectivity_message));
    alert.setPositiveButton(getResources().getString(R.string.ok),

        new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            if (MasterpassMerchant.getCheckoutCallback() != null
                && MasterpassMerchant.isMerchantInitiated()) {
              MasterpassMerchant.getCheckoutCallback().onCheckoutError(new MasterpassError(
                  MasterpassError.ERROR_CODE_CANCEL_WALLET, "User selected cancel wallet"));
            }

            finish();
          }
        }).show();
  }

  public interface checkoutListener {
    public void onError(MasterpassError error);
  }
}
