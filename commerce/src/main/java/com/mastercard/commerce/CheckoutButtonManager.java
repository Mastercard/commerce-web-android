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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PictureDrawable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Set;

class CheckoutButtonManager implements DownloadCheckoutButton.CheckoutButtonDownloadedListener {
  private static final String DYNAMIC_BUTTON_URL =
      "https://src.mastercard.com/assets/img/btn/src_chk_btn_376x088px.svg";
  private Context context;
  private String checkoutId;
  private Set<CardType> allowedCardTypes;
  private Bitmap checkoutButtonBitmap;
  private CheckoutButton checkoutButton;
  private DataStore dataStore;

  public CheckoutButtonManager(Context context, String checkoutId, Set<CardType> allowedCardTypes,
      DataStore dataStore) {
    this.context = context;
    this.checkoutId = checkoutId;
    this.allowedCardTypes = allowedCardTypes;
    this.dataStore = dataStore;
    new DownloadCheckoutButton(DYNAMIC_BUTTON_URL, this).execute();
  }

  public CheckoutButton getCheckoutButton(
      CheckoutButton.CheckoutButtonClickListener clickListener) {
    if (checkoutButtonBitmap == null) {
      String checkoutButtonData = readCheckoutButtonFromCache();
      convertButtonDataToBitmap(checkoutButtonData);
    }
    checkoutButton = new CheckoutButton(context, clickListener, checkoutButtonBitmap);
    return checkoutButton;
  }

  private void convertButtonDataToBitmap(String response) {
    if (response == null) return;
    SVG svg = null;
    try {
      byte[] data = response.getBytes();
      InputStream stream = new ByteArrayInputStream(data);
      svg = SVG.getFromInputStream(stream);
    } catch (SVGParseException e) {
      e.printStackTrace();
    }
    PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());
    checkoutButtonBitmap = pictureDrawableToBitmap(drawable);

    if (checkoutButton != null) {
      checkoutButton.setBackground(new BitmapDrawable(checkoutButtonBitmap));
    }
  }

  /**
   * This is used to convert the {@link PictureDrawable} to {@link Bitmap}. SVG lib is noticed to
   * have some issue converting to PictureDrawable.
   */
  private Bitmap pictureDrawableToBitmap(PictureDrawable pictureDrawable) {
    Bitmap bmp = Bitmap.createBitmap(pictureDrawable.getIntrinsicWidth(),
        pictureDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bmp);
    canvas.drawPicture(pictureDrawable.getPicture());
    return bmp;
  }

  private String getFileName() {
    int hashOfFile = checkoutId.hashCode() + allowedCardTypes.hashCode();
    return (String.valueOf(hashOfFile) + ".txt");
  }

  private String readCheckoutButtonFromCache() {
    File file = new File(context.getCacheDir(), getFileName());
    return dataStore.readFromFileInputStream(file);
  }

  private void writeCheckoutButtonInCache(String checkoutButtonData) {
    File file = new File(context.getCacheDir(), getFileName());
    dataStore.writeDataToFile(file, checkoutButtonData);
  }

  @Override public void checkoutButtonDownloadSuccess(String responseData) {
    writeCheckoutButtonInCache(responseData);
    convertButtonDataToBitmap(responseData);
  }

  @Override public void checkoutButtonDownloadError() {
    //TODO : need to discuss about error scenario
  }
}
