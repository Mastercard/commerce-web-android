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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.PictureDrawable;
import android.util.Log;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.Set;

/**
 * This class returns the instance of the checkout button.
 *
 * It is responsible for downloading the {@code CheckoutButton} based on the configurations
 * provided by the merchants.
 * Upon successful download of the button image it is converted to Bitmap and saved in the
 * local cache.
 */

public class CheckoutButtonManager
    implements DownloadCheckoutButton.CheckoutButtonDownloadedListener {
  private static final String TAG = CheckoutButtonManager.class.getSimpleName();
  private static final String DYNAMIC_BUTTON_IMAGE_URL =
      "https://src.mastercard.com/assets/img/btn/src_chk_btn_376x088px.svg";
  private static volatile CheckoutButtonManager instance;
  private Context context;
  private String checkoutId;
  private Set<CardType> allowedCardTypes;
  private Bitmap checkoutButtonBitmap;
  private CheckoutButton checkoutButton;
  private DataStore dataStore;
  private Locale locale;
  private CheckoutButton.CheckoutButtonClickListener buttonClickListener;

  public synchronized static CheckoutButtonManager getInstance() {
    if (instance == null) {
      ConfigurationManager configurationManager = ConfigurationManager.getInstance();
      Context context = configurationManager.getContext();
      String checkoutId = configurationManager.getConfiguration().getCheckoutId();
      Set<CardType> allowedCardTypes =
          configurationManager.getConfiguration().getAllowedCardTypes();
      DataStore dataStore = DataStore.getInstance();
      Locale locale = configurationManager.getConfiguration().getLocale();

      instance = new CheckoutButtonManager(context, checkoutId, allowedCardTypes, dataStore, locale);
      instance.initialize();
    }

    return instance;
  }

  private void initialize() {
    Log.d(TAG, "initialize of CheckoutButtonManager");
    downloadCheckoutButton();
  }

  private CheckoutButtonManager(Context context, String checkoutId, Set<CardType> allowedCardTypes,
      DataStore dataStore, Locale locale) {
    this.context = context;
    this.checkoutId = checkoutId;
    this.allowedCardTypes = allowedCardTypes;
    this.dataStore = dataStore;
    this.locale = locale;
  }

  public CheckoutButton getCheckoutButton(
      CheckoutButton.CheckoutButtonClickListener clickListener) {
    buttonClickListener = clickListener;

    checkoutButton = new CheckoutButton(context, clickListener, checkoutButtonBitmap);
    checkoutButton.setContentDescription(
        context.getString(R.string.checkoutButton_contentDescription));

    return getButtonImage();
  }

  private CheckoutButton getButtonImage() {
    String checkoutButtonData = readCheckoutButtonFromCache();

    if (checkoutButtonData != null && !checkoutButtonData.isEmpty()) {
      Log.d(TAG, "checkout button data is found in cache");
      convertButtonDataToBitmap(checkoutButtonData);

      Log.d(TAG, "set checkout button image from cache");
      checkoutButton.setImageBitmap(checkoutButtonBitmap);
    } else {
      loadDefaultButton();
    }

    return checkoutButton;
  }

  private void downloadCheckoutButton() {
    Log.d(TAG, "downloadCheckoutButton started");
    String dynamicButtonUrl =
        SrcCheckoutUrlUtil.getDynamicButtonUrl(DYNAMIC_BUTTON_IMAGE_URL, checkoutId,
            allowedCardTypes, locale);

    new DownloadCheckoutButton(dynamicButtonUrl, this).execute();
  }

  private void convertButtonDataToBitmap(String response) {
    Log.d(TAG, "convertButtonDataToBitmap");

    if (response == null || response.isEmpty()) return;

    try {
      byte[] data = response.getBytes();
      InputStream stream = new ByteArrayInputStream(data);
      SVG svg = SVG.getFromInputStream(stream);
      PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());
      checkoutButtonBitmap = pictureDrawableToBitmap(drawable);
    } catch (SVGParseException e) {
      Log.d(TAG, "parsing of SVG failed: " + e.getMessage(), e);
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
    long hashOfFile = checkoutId.hashCode() + allowedCardTypes.hashCode();

    return (Math.abs(hashOfFile) + ".txt");
  }

  private String readCheckoutButtonFromCache() {
    Log.d(TAG, "readCheckoutButtonFromCache");
    File file = new File(context.getCacheDir(), getFileName());

    return dataStore.readFromFileInputStream(file);
  }

  private void writeButtonImageToCache(String checkoutButtonData) {
    Log.d(TAG, "writeButtonImageToCache");
    File file = new File(context.getCacheDir(), getFileName());

    dataStore.writeDataToFile(file, checkoutButtonData);
  }

  @Override public void checkoutButtonDownloadSuccess(String responseData) {
    Log.d(TAG, "checkoutButtonDownloadSuccess");

    writeButtonImageToCache(responseData);
    convertButtonDataToBitmap(responseData);

    if (checkoutButton == null) {
      checkoutButton = new CheckoutButton(context, buttonClickListener, checkoutButtonBitmap);

      Log.d(TAG, "set checkout button image after downloading from server");
      checkoutButton.setImageBitmap(checkoutButtonBitmap);
    }
  }

  @Override public void checkoutButtonDownloadError() {
    //TODO : need to discuss about error scenario
  }

  private void loadDefaultButton() {
    Log.d(TAG, "loadDefaultButton");
    Bitmap buttonImage;
    if(locale.equals(Locale.US)){
      buttonImage = BitmapFactory.decodeResource(context.getResources(), context.getResources()
          .getIdentifier("button_src", "drawable", context.getPackageName()));
    } else {
      buttonImage = BitmapFactory.decodeResource(context.getResources(), context.getResources()
          .getIdentifier("button_masterpass", "drawable", context.getPackageName()));
    }

    checkoutButton.setImageBitmap(buttonImage);
  }
}
