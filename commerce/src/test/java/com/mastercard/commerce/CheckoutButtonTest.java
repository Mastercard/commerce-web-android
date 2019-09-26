package com.mastercard.commerce;

import android.graphics.Bitmap;
import android.view.View;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*"})
public class CheckoutButtonTest {

  @Test
  public void checkoutButton_onClick() {
    CheckoutButton.CheckoutButtonClickListener listener = mock(
        CheckoutButton.CheckoutButtonClickListener.class);
    Bitmap bitmap = mock(Bitmap.class);

    CheckoutButton checkoutButton = new CheckoutButton(ApplicationProvider.getApplicationContext(),
        listener, bitmap);

    checkoutButton.setOnClickListener(mock(View.OnClickListener.class));

    checkoutButton.performClick();

    verify(listener).onClick();

  }

}