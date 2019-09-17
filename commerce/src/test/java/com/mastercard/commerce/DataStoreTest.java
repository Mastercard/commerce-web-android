package com.mastercard.commerce;

import androidx.test.core.app.ApplicationProvider;
import java.io.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;


@RunWith(RobolectricTestRunner.class) @Config(sdk = 27) @PowerMockIgnore({
    "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*"})
public class DataStoreTest {

  @Test
  public void dataStore_Write_Read_Test() {
    String fileInput = "Testing Data Store";

    DataStore dataStore = DataStore.getInstance();
    File file = new File(ApplicationProvider.getApplicationContext().getCacheDir(), "/test.txt");

    dataStore.writeDataToFile(file, fileInput);

    String fileOutput = dataStore.readFromFileInputStream(file);

    assertEquals(fileInput, fileOutput);

  }

}