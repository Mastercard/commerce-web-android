package com.mastercard.testapp.data.external;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mastercard.testapp.BuildConfig;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class EnvironmentConstants {

  public static EnvironmentConfigurations envConfig;
  public static HashMap<String, EnvironmentConfiguration> envConfigMap;
  public static EnvironmentConfiguration environmentConfiguration;
  public static String CURRENT_ENVIRONMENT_SDK = BuildConfig.ENVIRONMENT;
  public static String SANDBOX_ENV = "Sandbox";
  public static String STAGE_ENV = "Stage";
  public static String PROD_ENV = "Prod";
  public static String NAME = BuildConfig.ENVIRONMENT;
  public static String FLAVOR = "flavor";
  public static String CHECKOUT_ID = BuildConfig.CHECKOUT_ID;
  public static String CHECKOUT_URL = BuildConfig.CHECKOUT_URL;
  public static String CHECKOUT_SRC_URL = BuildConfig.CHECKOUT_SRC_URL;
  public static String SRCI_DOMAIN = "SrciDomain";
  public static String MIDDLEWARE_DOMAIN = "middlewareDomain";
  public static String KEY_ALIAS = BuildConfig.KEY_ALIAS;
  public static String PASSWORD = BuildConfig.PASSWORD;
  public static String CLIENT_ID = BuildConfig.CLIENT_ID;
  public static String MERCHANT_P12_CERTIFICATE = BuildConfig.MERCHANT_P12_CERTIFICATE;

  public static void getEnvironment(String env, Context context)  {

    //String jsonFromFile = new Scanner(new File(env)).useDelimiter("\\Z").next();
    //JSONObject jsonString = new JSONObject(jsonFromFile);


    InputStream inputFile = null;
    StringBuffer sb = new StringBuffer();
    int ch;
    Gson gson = new Gson();

    try {
      inputFile = context.getAssets().open("environments.json");
      while((ch = inputFile.read()) != -1){
        sb.append((char)ch);
      }

      String s = sb.toString();
      envConfigMap = envConfig.getEnvironmentConfiguration();
      envConfigMap = gson.fromJson(s, new TypeToken<HashMap<String, EnvironmentConfiguration>>() {}.getType());

      for(String environmentMap: envConfigMap.keySet()){
        if(env.equalsIgnoreCase(SANDBOX_ENV)){
          environmentConfiguration = envConfigMap.get(environmentMap);
        } else if(env.equalsIgnoreCase(STAGE_ENV)){
          environmentConfiguration = envConfigMap.get(environmentMap);
        } else if(env.equalsIgnoreCase(PROD_ENV)){
          environmentConfiguration = envConfigMap.get(environmentMap);
        }
      }

      getConfigValue();

    } catch (FileNotFoundException e){

    } catch (IOException ioe){

    } finally {
      try{
        if(inputFile != null){
          inputFile.close();
        }
      } catch (IOException ioe){

      }
    }

  }
  public static String getConfig(@NonNull String environment, Boolean masterpass, String value, Context context){

    if(environment != null) {
      CURRENT_ENVIRONMENT_SDK = environment;
    } else if(environment == null){
      return value;
    }

    if(SANDBOX_ENV.equalsIgnoreCase(environment) && masterpass){
      getEnvironment(environment, context);
      return value;
    } else if(SANDBOX_ENV.equalsIgnoreCase(environment)){
      getEnvironment(environment, context);
      return value;
    } else if(PROD_ENV.equalsIgnoreCase(environment)){

    } else if(STAGE_ENV.equalsIgnoreCase(environment)){
      getEnvironment(environment, context);
      return value;
    }

    return null;
  }

  //public static String getConfig(String variable){
  //  if(CURRENT_ENVIRONMENT_SDK.equalsIgnoreCase("sandbox")){
  //    return getConfigValue(variable);
  //  }
  //  //else if (CURRENT_ENVIRONMENT_SDK.equalsIgnoreCase("production")){
  //  //  return getSrcProd(variable);
  //  //}
  //  //return getSrcStage(variable);
  //  return "";
  //}

  public static void getConfigValue(){

        NAME = environmentConfiguration.getName();
        FLAVOR = environmentConfiguration.getFlavor();
        CHECKOUT_ID = environmentConfiguration.getCheckoutId();
        CHECKOUT_URL = environmentConfiguration.getCheckoutURL();
        CHECKOUT_SRC_URL = environmentConfiguration.getCheckoutSrcUrl();
        SRCI_DOMAIN = environmentConfiguration.getSrciDomain();
        MIDDLEWARE_DOMAIN = environmentConfiguration.getMiddlewareDomain();
        KEY_ALIAS = environmentConfiguration.getKeyAlias();
        PASSWORD = environmentConfiguration.getPassword();
        CLIENT_ID = environmentConfiguration.getClientId();
        MERCHANT_P12_CERTIFICATE = environmentConfiguration.getMerchantP12Certificate();
  }
}
