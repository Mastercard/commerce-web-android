package com.mastercard.testapp.data.external;

import android.content.Context;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class EnvironmentConstants {

  public static HashMap<String, EnvironmentConfiguration> envConfigMap;

  private static EnvironmentConfigurations envConfig;

  public static EnvironmentConfiguration getEnvironment(String env, Context context) {
    StringBuffer sb = new StringBuffer();
    int ch;
    Gson gson = new Gson();

    try (InputStream inputFile = context.getAssets().open("environments.json")) {
      while ((ch = inputFile.read()) != -1) {
        sb.append((char) ch);
      }

      String s = sb.toString();
      envConfig = gson.fromJson(s, EnvironmentConfigurations.class);
    } catch (FileNotFoundException e) {

    } catch (IOException e) {

    }

    return envConfig.getEnvironmentConfiguration().get(env);
  }
}
