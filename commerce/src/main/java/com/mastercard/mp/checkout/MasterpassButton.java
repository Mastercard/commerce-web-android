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

package com.mastercard.mp.checkout;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.IntDef;
import android.view.View;
import android.widget.ImageButton;
import com.mastercard.commerce.CommerceWebSdk;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * {@code ImageButton} subclass that shows the Buy With Masterpass image. While this class is
 * public, construction should be left to the SDK. {@code MasterpassButton} should be not be
 * included in an XML Layout, as it will not be possible to return the click event to any class
 * instance.
 *
 * @deprecated You should migrate your code to use {@link CommerceWebSdk} instead. All APIs available
 * in this package will be deprecated in a future release.
 */

@Deprecated @SuppressLint({ "ViewConstructor", "AppCompatCustomView" }) public class MasterpassButton
    extends ImageButton implements View.OnClickListener {
  public static final int PAIRING_FLOW_ENABLED = 0;
  public static final int PAIRING_CHECKOUT_FLOW_ENABLED = 1;
  static final int NO_FLOWS_SET = -1;
  private static final String TAG = MasterpassButton.class.getSimpleName();
  MasterpassButtonClickListener clickListener;

  public MasterpassButton(Context context, MasterpassButtonClickListener clickListener) {
    super(context);
    this.clickListener = clickListener;
    super.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    clickListener.onClick();
  }

  @Override public void setOnClickListener(OnClickListener l) {
    //Prevent caller from setting a click listener for this button
  }

  @Retention(SOURCE) @IntDef({
      PAIRING_FLOW_ENABLED, PAIRING_CHECKOUT_FLOW_ENABLED, NO_FLOWS_SET
  }) @interface Behavior {
  }

  /**
   * Listener interface to be notified when a click event occurs on this button
   */
  public interface MasterpassButtonClickListener {
    /**
     * Notifies the listener that this button has been clicked
     */
    void onClick();
  }
}
