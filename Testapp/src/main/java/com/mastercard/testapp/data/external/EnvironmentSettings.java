package com.mastercard.testapp.data.external;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.data.pojo.EnvironmentConfiguration;
import com.mastercard.testapp.data.pojo.EnvironmentConfigurations;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class converts JSON file into a java object
 */
public class EnvironmentSettings {

  private static EnvironmentConfigurations environmentConfigurations;
  private static Context mContext;

  /**
   * The method is used to parse the JSON file for environment configuration
   *
   * @param context the context
   * @return the environment configuration of current environment
   */
  public static void loadEnvironmentConfigurations(Context context) {
    mContext = context;

    if(environmentConfigurations == null) {
      StringBuffer sb = new StringBuffer();
      int ch;
      Gson gson = new Gson();

      try(InputStream inputFile = context.getAssets().open("environments.json")) {
        while ((ch = inputFile.read()) != -1) {
          sb.append((char) ch);
        }
        String s = sb.toString();
        environmentConfigurations = gson.fromJson(s, EnvironmentConfigurations.class);
      } catch(FileNotFoundException e) {
        Log.e(EnvironmentSettings.class.getSimpleName(), "environments.json file not found", e);
      } catch(IOException e) {
        Log.e("Unable to read .json", e.getMessage());
      }
    }

  }

  public static EnvironmentConfiguration getCurrentEnvironmentConfiguration(){
    String currentEnvironment = SettingsSaveConfigurationSdk.getInstance(mContext).getEnvironment();
    
    return environmentConfigurations.getEnvironmentConfiguration().get(currentEnvironment);
  }
}

