package com.mastercard.commerce;

import android.os.Build;
import android.util.Log;
import androidx.test.core.app.ApplicationProvider;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class) @Config(sdk = Build.VERSION_CODES.O_MR1) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*",
})
public class DownloadCheckoutButtonTest {

  @Test
  public void downloadCheckoutButton_test() {
    String image_url =
        "https://src.mastercard.com/assets/img/btn/src_chk_btn_376x088px.svg";
    final String[] result = new String[1];
    String checkoutId = "checkoutId";
    Set<CardType> allowedCardTypes = new HashSet<>();

    String dynamicButtonUrl =
        SrcCheckoutUrlUtil.getDynamicButtonUrl(image_url, checkoutId,
            allowedCardTypes);

    new DownloadCheckoutButton(dynamicButtonUrl,
        new DownloadCheckoutButton.CheckoutButtonDownloadedListener() {
          @Override public void checkoutButtonDownloadSuccess(String responseData) {
            result[0] = responseData;
          }

          @Override public void checkoutButtonDownloadError() {
          }
        }).execute();

    assertFalse(result[0].isEmpty());
  }


}