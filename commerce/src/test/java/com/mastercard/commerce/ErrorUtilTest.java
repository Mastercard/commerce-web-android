package com.mastercard.commerce;

import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*"})
public class ErrorUtilTest {

  @Test
  public void errorUtil_isNetworkConnected() {
    assertTrue(ErrorUtil.isNetworkConnected(ApplicationProvider.getApplicationContext()));
  }
}