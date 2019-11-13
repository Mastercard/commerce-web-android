package com.mastercard.testapp.data.external;

import com.mastercard.testapp.data.pojo.EnvironmentConfiguration;
import java.util.Map;

/**
 * This class provides a map that is used to store environment and its corresponding configuration object converted from JSON file.
 * The map may contain multiple environments and their respective configurations
 *
 * Created by Swrajit Paul
 */
public class EnvironmentConfigurations {

  private static EnvironmentConfigurations instance;
  private static Map<String, EnvironmentConfiguration> environmentConfiguration;

  /**
   * private constructor restricted to this class itself
   */
  private EnvironmentConfigurations(){

  }

  /**
   * @return the EnvironmentConfigurations object
   */
  public static synchronized EnvironmentConfigurations getInstance(){
      if(instance == null){
        instance = new EnvironmentConfigurations();
      }
      return instance;
  }

  /**
   * @return environment configuration map
   */
  public static Map<String, EnvironmentConfiguration> getEnvironmentConfiguration(){

    return environmentConfiguration;
  }
}
