package com.mastercard.testapp.data.external;

import android.content.Context;
import com.google.gson.Gson;
import com.mastercard.testapp.data.pojo.EnvironmentConfiguration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class converts JSON file into a java object
 */
public class EnvironmentSettings {

  private static final String[] environments = new String[]{"Stage", "Sandbox", "Production", "Masterpass"};
  private static String currentEnvironment = environments[1]; // default environment set to Sandbox;
  private static EnvironmentConfiguration envConfig;
  private static Map<String, EnvironmentConfiguration> envMap;

  /**
   * The method is used to parse the JSON file for environment configuration
   *
   * @param context the context
   * @return the environment configuration of current environment
   */
  public static EnvironmentConfiguration environmentConfiguration(Context context) {

    StringBuffer sb = new StringBuffer();
    int ch;
    Gson gson = new Gson();

    try (InputStream inputFile = context.getAssets().open("environments.json")) {
      while ((ch = inputFile.read()) != -1) {
        sb.append((char) ch);
      }
      String s = sb.toString();
      EnvironmentConfigurations envConfiguration = null;
      envMap = envConfiguration.getInstance().getEnvironmentConfiguration();
      envMap = new HashMap<>();

      try {
        JSONObject testUserObject = new JSONObject(s);
        String environment;
        for (String e: environments) {
          environment = testUserObject.getString(e);
          envConfig = gson.fromJson(environment, EnvironmentConfiguration.class);
          envMap.put(e,envConfig);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return envMap.get(currentEnvironment);
  }

  /**
   * The method is for masterpass configuration
   *
   * @param masterpass masterpass selected
   * @return masterpass configuration if true, else current environment configuration
   */
  public static EnvironmentConfiguration masterpassOrCurrentEnvironment(Boolean masterpass){
    if(masterpass && currentEnvironment == environments[1]){
      return envMap.get(environments[3]);
    }
    return envMap.get(currentEnvironment);
  }

  /**
   * @return the current environment
   */
  public static String getCurrentEnvironment(){
    return currentEnvironment;
  }

  /**
   * @param environment current environment
   */
  public static void setCurrentEnvironment(String environment){
    currentEnvironment = environment;
  }

}

