package com.mastercard.testapp.data.external;

import android.content.Context;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class EnvironmentConstants {

  private static final String[] environments = new String[]{"Stage", "Sandbox", "Production", "Masterpass"};
  public  static String currentEnvironment = environments[1]; // default environment set to Sandbox;
  private static EnvironmentConfiguration envConfig;
  private static HashMap<String, EnvironmentConfiguration> envMap;

  public static EnvironmentConfiguration environmentConfiguration(Context context, String env) {
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

    } catch (IOException e) {

    }

    return envMap.get(env);
  }

  public static EnvironmentConfiguration masterpassOrSrc(Boolean masterpass, String env){
    if(masterpass && env == environments[1]){
      return envMap.get(environments[3]);
    }
    return envMap.get(env);
  }

}

