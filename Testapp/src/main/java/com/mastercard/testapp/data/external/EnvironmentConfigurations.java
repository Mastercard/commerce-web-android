package com.mastercard.testapp.data.external;

import java.util.HashMap;

public class EnvironmentConfigurations {
  private static EnvironmentConfigurations instance;
  private static HashMap<String, EnvironmentConfiguration> environmentConfiguration;

  private EnvironmentConfigurations(){

  }

  public static synchronized EnvironmentConfigurations getInstance(){
      if(instance == null){
        instance = new EnvironmentConfigurations();
      }
      return instance;
  }

  public static HashMap<String, EnvironmentConfiguration> getEnvironmentConfiguration(){

    return environmentConfiguration;
  }
}
