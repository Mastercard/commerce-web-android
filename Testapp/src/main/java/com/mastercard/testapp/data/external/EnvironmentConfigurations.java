package com.mastercard.testapp.data.external;

import java.util.HashMap;

public class EnvironmentConfigurations {

  private static HashMap<String, EnvironmentConfiguration> environmentConfiguration;

  public static HashMap<String, EnvironmentConfiguration> getEnvironmentConfiguration(){
    return environmentConfiguration;
  }
}
