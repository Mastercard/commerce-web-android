package com.mastercard.mp.checkout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.PictureDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.support.annotation.IntDef;
import com.mastercard.commerce.R;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * {@code ImageButton} subclass that shows the Buy With Masterpass image. While this class is
 * public, construction should be left to the SDK. {@code MasterpassButton} should be not be
 * included in an XML Layout, as it will not be possible to return the click event to any class
 * instance.
 */

@SuppressLint({ "ViewConstructor", "AppCompatCustomView" }) public final class MasterpassButton
    extends ImageButton implements View.OnClickListener {
  public static final int PAIRING_FLOW_ENABLED = 0;
  public static final int PAIRING_CHECKOUT_FLOW_ENABLED = 1;
  static final int NO_FLOWS_SET = -1;
  private static final String TAG = MasterpassButton.class.getSimpleName();
  MasterpassButtonClickListener clickListener;

  MasterpassButton(Context context, MasterpassButtonClickListener clickListener) {
    super(context);

    this.clickListener = clickListener;
    super.setOnClickListener(this);

    setMinimumHeight(0);
    setMinimumWidth(0);
    setBackground(null);
    setImageResource(R.drawable.btn_src);
    setScaleType(ScaleType.FIT_CENTER);
    setLayoutParams(new ViewGroup.LayoutParams(
        getResources().getDimensionPixelSize(R.dimen.masterpass_button_width),
        ViewGroup.LayoutParams.MATCH_PARENT));
  }

  private void loadDefaultButton(Context context) {
    Bitmap buttonImage = BitmapFactory.decodeResource(context.getResources(), context.getResources()
        .getIdentifier("button_masterpass", "drawable", context.getPackageName()));
    setImageBitmap(buttonImage);
  }

  @Override public void onClick(View v) {
    clickListener.onClick();
  }

  @Override public void setOnClickListener(OnClickListener l) {
    //Prevent caller from setting a click listener for this button
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

  @Retention(SOURCE) @IntDef({
      PAIRING_FLOW_ENABLED, PAIRING_CHECKOUT_FLOW_ENABLED, NO_FLOWS_SET
  }) @interface Behavior {
  }

  /**
   * Listener interface to be notified when a click event occurs on this button
   */
  interface MasterpassButtonClickListener {
    /**
     * Notifies the listener that this button has been clicked
     */
    void onClick();
  }
}
