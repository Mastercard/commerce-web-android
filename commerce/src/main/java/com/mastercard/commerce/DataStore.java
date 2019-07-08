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

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataStore {
  private static String TAG = DataStore.class.getSimpleName();
  private static DataStore dataStore;

  public static synchronized DataStore getInstance() {
    if (dataStore == null) {
      dataStore = new DataStore();
    }
    return dataStore;
  }

  public void writeDataToFile(File file, String data) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      writeDataToFile(fileOutputStream, data);
      fileOutputStream.close();
    } catch (FileNotFoundException e) {
      Log.e(TAG, e.getMessage());
    } catch (IOException e) {
      Log.e(TAG, e.getMessage());
    }
  }

  public String readFromFileInputStream(File cacheFileDir) {
    StringBuffer retBuf = new StringBuffer();
    try {
      FileInputStream fileInputStream = new FileInputStream(cacheFileDir);
      if (fileInputStream != null) {
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String lineData = bufferedReader.readLine();
        while (lineData != null) {
          retBuf.append(lineData);
          lineData = bufferedReader.readLine();
        }
      }
    } catch (IOException e) {
      Log.d(TAG, "reading of file failed" +e.getMessage());
    } finally {
      return retBuf.toString();
    }
  }

  private void writeDataToFile(FileOutputStream fileOutputStream, String data) {
    try {
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
      BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
      bufferedWriter.write(data);
      bufferedWriter.flush();
      bufferedWriter.close();
      outputStreamWriter.close();
    } catch (FileNotFoundException e) {
      Log.d(TAG, "write Data To File failed" +e.getMessage());
    } catch (IOException e) {
      Log.d(TAG, "write Data To File failed" +e.getMessage());
    }
  }
}
