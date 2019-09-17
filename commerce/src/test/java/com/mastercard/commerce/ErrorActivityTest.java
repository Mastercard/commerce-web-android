package com.mastercard.commerce;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import androidx.test.core.app.ApplicationProvider;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*"})
public class ErrorActivityTest {

  @Test
  public void errorActivity_onCreate() {

    Intent errorIntent = new Intent(ApplicationProvider.getApplicationContext(), ErrorActivity.class)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    ErrorActivity activity = Robolectric.buildActivity(ErrorActivity.class, errorIntent).setup().get();

    assertTrue(((android.app.AlertDialog) ShadowAlertDialog.getLatestDialog())
    .getButton(AlertDialog.BUTTON_POSITIVE)
        .performClick());

  }
}