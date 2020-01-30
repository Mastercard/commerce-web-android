package com.mastercard.commerce;

import android.graphics.Picture;
import android.util.Log;
import androidx.test.core.app.ApplicationProvider;
import com.caverock.androidsvg.SVG;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*"})
@PrepareForTest({ConfigurationManager.class, Log.class, SrcCheckoutUrlUtil.class, SVG.class})
public class CheckoutButtonManagerTest {

  private static final String DYNAMIC_BUTTON_IMAGE_URL =
      "https://src.mastercard.com/assets/img/btn/src_chk_btn_376x088px.svg";

  @Rule public PowerMockRule rule = new PowerMockRule();

  @Test
  public void checkoutButtonManager_getInstance() {
    ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    configurationManager.setContext(ApplicationProvider.getApplicationContext());
    CommerceConfig config = mock(CommerceConfig.class);
    configurationManager.setConfiguration(config);
    mockStatic(ConfigurationManager.class);
    mockStatic(SrcCheckoutUrlUtil.class);
    mockStatic(Log.class);
    mockStatic(SVG.class);
    SVG svg = mock(SVG.class);
    Picture picture = mock(Picture.class);


    when(ConfigurationManager.getInstance()).thenReturn(configurationManager);
    when(config.getCheckoutId()).thenReturn("checkoutId");
    when(SrcCheckoutUrlUtil.getDynamicButtonUrl(anyString(), anyString(), any(HashSet.class), any(
        Locale.class))).thenReturn(DYNAMIC_BUTTON_IMAGE_URL);
    try {
      when(SVG.getFromInputStream(any(InputStream.class))).thenReturn(svg);
    } catch (Exception e) {
      e.printStackTrace();
    }
    when(svg.renderToPicture()).thenReturn(picture);
    when(picture.getHeight()).thenReturn(100);
    when(picture.getWidth()).thenReturn(100);

    CheckoutButtonManager manager = CheckoutButtonManager.getInstance();

    assertNotNull(manager.getCheckoutButton(mock(CheckoutButton.CheckoutButtonClickListener.class)));

  }


}