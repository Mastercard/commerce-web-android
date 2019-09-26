package com.mastercard.mp.checkout;

import android.view.View;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class)
public class MasterpassButtonTest {

  @Mock View view;

  @Test public void testMasterpassButton() {

    MasterpassButton.MasterpassButtonClickListener clickListener =
        new MasterpassButton.MasterpassButtonClickListener() {
          @Override public void onClick() {

          }
        };
    MasterpassButton masterpassButton =
        new MasterpassButton(ApplicationProvider.getApplicationContext(), clickListener);
    masterpassButton.onClick(view);
  }
}
