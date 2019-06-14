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
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import com.mastercard.mp.checkout.MasterpassButton;

/**
 * {@code ImageButton} subclass that shows the Buy With Masterpass image. While this class is
 * public, construction should be left to the SDK. {@code MasterpassButton} should be not be
 * included in an XML Layout, as it will not be possible to return the click event to any class
 * instance.
 */

@SuppressLint({ "ViewConstructor", "AppCompatCustomView" }) public class CheckoutButton
    extends MasterpassButton implements View.OnClickListener {
  CheckoutButtonClickListener clickListener;

  public CheckoutButton(Context context, CheckoutButtonClickListener clickListener,
      Bitmap checkoutButtonBitmap) {
    super(context, clickListener);
    this.clickListener = clickListener;
    super.setOnClickListener(this);

    setMinimumHeight(800);
    setMinimumWidth(800);
    setBackground(null);
    setImageBitmap(checkoutButtonBitmap);
    setScaleType(ScaleType.FIT_CENTER);
    setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));
  }

  @Override public void onClick(View v) {
    clickListener.onClick();
  }

  @Override public void setOnClickListener(OnClickListener l) {
    //Prevent caller from setting a click listener for this button
  }

  /**
   * Listener interface to be notified when a click event occurs on this button
   */
  public interface CheckoutButtonClickListener
      extends MasterpassButton.MasterpassButtonClickListener {
    /**
     * Notifies the listener that this button has been clicked
     */
    void onClick();
  }
}
