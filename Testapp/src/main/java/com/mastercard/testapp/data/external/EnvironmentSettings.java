package com.mastercard.testapp.data.external;

import android.content.Context;
import com.mastercard.testapp.BuildConfig;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.data.pojo.EnvironmentConfiguration;
import com.mastercard.testapp.data.pojo.EnvironmentConfigurations;
import java.util.HashMap;
import java.util.Map;

/**
 * This class converts JSON file into a java object
 */
public class EnvironmentSettings {

  private static EnvironmentConfigurations environmentConfigurations;
  private static Context mContext;
  private static String name = "name";
  private static String flavor = "flavor";
  private static String checkoutUrl = "checkoutUrl";
  private static String checkoutSrcUrl = "checkoutSrcUrl";
  private static String clientId = "clientId";
  private static String keyAlias = "keyAlias";
  private static String password = "password";
  private static String merchantP12Certificate = "merchantP12Certificate";
  private static String checkoutId = "checkoutId";
  private static String middlewareDomain = "middlewareDomain";
  private static String srciDomain = "srciDomain";

  /**
   * The method is used to parse the JSON file for environment configuration
   *
   * @param context the context
   */
  public static void loadEnvironmentConfigurations(Context context) {
    mContext = context;
  }

  public static EnvironmentConfiguration getCurrentEnvironmentConfiguration() {
    String currentEnvironment = SettingsSaveConfigurationSdk.getInstance(mContext).getEnvironment();

    Map<String, String> environmentMap =
        (HashMap<String, String>) BuildConfig.ENVIRONMENTS_MAP.get(currentEnvironment);

    EnvironmentConfiguration.Builder builder = new EnvironmentConfiguration.Builder();

    return builder.setCheckoutId(environmentMap.get(checkoutId))
        .setName(environmentMap.get(name))
        .setFlavor(environmentMap.get(flavor))
        .setCheckoutUrl(environmentMap.get(checkoutUrl))
        .setCheckoutSrcUrl(environmentMap.get(checkoutSrcUrl))
        .setClientId(environmentMap.get(clientId))
        .setKeyAlias(environmentMap.get(keyAlias))
        .setPassword(environmentMap.get(password))
        .setMerchantP12Certificate(environmentMap.get(merchantP12Certificate))
        .setMiddlewareDomain(environmentMap.get(middlewareDomain))
        .setSrciDomain(environmentMap.get(srciDomain))
        .build();
  }
}

