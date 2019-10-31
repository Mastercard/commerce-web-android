package com.mastercard.testapp.data.external;

import com.mastercard.testapp.BuildConfig;


public class EnvironmentConstants {

  /**
   * The variable DEFAULT_ENVIRONMENT_SDK
   */
  public static String DEFAULT_ENVIRONMENT_SDK = BuildConfig.ENVIRONMENT;
  /**
   * The constant MASTERPASS_NAME.
   */
  public static final String MASTERPASS_NAME = "Sandbox";
  /**
   * The constant MASTERPASS_FLAVOR.
   */
  public static final String MASTERPASS_FLAVOR = "MasterpassSandbox";
  /**
   * The constant MASTERPASS_CHECKOUT_ID.
   */
  public static final String MASTERPASS_CHECKOUT_ID  = "56ba329519194adf802fe7ba97a9ef3d";
  /**
   * The constant MASTERPASS_CHECKOUT_URL.
   */
  public static final String MASTERPASS_CHECKOUT_URL  = "https://sandbox.masterpass.com/routing/v2/mobileapi/web-checkout";
  /**
   * The constant MASTERPASS_CHECKOUT_SRC_URL.
   */
  public static final String MASTERPASS_CHECKOUT_SRC_URL = "https://sandbox.src.mastercard.com/srci/";
  /**
   * The constant MASTERPASS_SRCI_DOMAIN.
   */
  public static final String MASTERPASS_SRCI_DOMAIN  = "https://sandbox.src.mastercard.com/api";
  /**
   * The constant MASTERPASS_MIDDLEWARE_DOMAIN.
   */
  public static final String MASTERPASS_MIDDLEWARE_DOMAIN = "https://sandbox.src.mastercard.com/srci/api";
  /**
   * The constant MASTERPASS_KEY_ALIAS.
   */
  public static final String MASTERPASS_KEY_ALIAS = "sandbox_fancyshop";
  /**
   * The constant MASTERPASS_PASSWORD.
   */
  public static final String MASTERPASS_PASSWORD = "sbxfancyshop";
  /**
   * The constant MASTERPASS_CLIENT_ID.
   */
  public static final String MASTERPASS_CLIENT_ID  = "ph3MHQtvmb7eCUOutflY7rRgsxpl8KDs_lovZ7O_f4a513bf!0c55e219ce3e467eaeb8418ac49edd950000000000000000";
  /**
   * The constant MASTERPASS_MERCHANT_P12_CERTIFICATE.
   */
  public static final String MASTERPASS_MERCHANT_P12_CERTIFICATE = "sandbox_fancyshop.p12";

  /**
   * The constant SRC_SANDBOX_NAME.
   */
  public static final String SRC_SANDBOX_NAME = "Sandbox";
  /**
   * The constant SRC_SANDBOX_FLAVOR.
   */
  public static final String SRC_SANDBOX_FLAVOR = "SrcSandbox" ;
  /**
   * The constant SRC_SANDBOX_CHECKOUTID.
   */
  public static final String SRC_SANDBOX_CHECKOUT_ID  = "56ba329519194adf802fe7ba97a9ef3d";
  /**
   * The constant SRC_SANDBOX_CHECKOUTURL.
   */
  public static final String SRC_SANDBOX_CHECKOUT_URL  = "https://sandbox.src.mastercard.com/srci/";
  /**
   * The constant SRC_SANDBOX_CHECKOUT_SRC_URL.
   */
  public static final String SRC_SANDBOX_CHECKOUT_SRC_URL = "https://sandbox.src.mastercard.com/srci/";
  /**
   * The constant SRC_SANDBOX_SRCI_DOMAIN.
   */
  public static final String SRC_SANDBOX_SRCI_DOMAIN  = "https://sandbox.src.mastercard.com/api";
  /**
   * The constant SRC_SANDBOX_MIDDLEWARE_DOMAIN.
   */
  public static final String SRC_SANDBOX_MIDDLEWARE_DOMAIN = "https://sandbox.src.mastercard.com/srci/api";
  /**
   * The constant SRC_SANDBOX_KEY_ALIAS.
   */
  public static final String SRC_SANDBOX_KEY_ALIAS = "sandbox_fancyshop";
  /**
   * The constant SRC_SANDBOX_PASSWORD.
   */
  public static final String SRC_SANDBOX_PASSWORD = "sbxfancyshop";
  /**
   * The constant SRC_SANDBOX_CLIENT_ID.
   */
  public static final String SRC_SANDBOX_CLIENT_ID = "ph3MHQtvmb7eCUOutflY7rRgsxpl8KDs_lovZ7O_f4a513bf!0c55e219ce3e467eaeb8418ac49edd950000000000000000";
  /**
   * The constant SRC_SANDBOX_MERCHANT_P12_CERTIFICATE.
   */
  public static final String SRC_SANDBOX_MERCHANT_P12_CERTIFICATE = "sandbox_fancyshop.p12";

  /**
   * The constant SRC_STAGE_NAME.
   */
  public static final String SRC_STAGE_NAME = "Stage";
  /**
   * The constant SRC_STAGE_FLAVOR.
   */
  public static final String SRC_STAGE_FLAVOR = "SrcStage";
  /**
   * The constant SRC_STAGE_CHECKOUT_ID.
   */
  public static final String SRC_STAGE_CHECKOUT_ID  = "f224616707ed44e6abb0455ccfc7e2f3";
  /**
   * The constant SRC_STAGE_CHECKOUT_URL.
   */
  public static final String SRC_STAGE_CHECKOUT_URL  = "https://stage.src.mastercard.com/srci/";
  /**
   * The constant SRC_STAGE_CHECKOUT_SRC_URL.
   */
  public static final String SRC_STAGE_CHECKOUT_SRC_URL = "https://stage.src.mastercard.com/srci/";
  /**
   * The constant SRC_STAGE_SRCI_DOMAIN.
   */
  public static final String SRC_STAGE_SRCI_DOMAIN  = "https://stage.src.mastercard.com/api";
  /**
   * The constant SRC_STAGE_MIDDLEWARE_DOMAIN.
   */
  public static final String SRC_STAGE_MIDDLEWARE_DOMAIN = "https://stage.src.mastercard.com/srci/api";
  /**
   * The constant SRC_STAGE_KEY_ALIAS.
   */
  public static final String SRC_STAGE_KEY_ALIAS = "stage_fancyshop";
  /**
   * The constant SRC_STAGE.
   */
  public static final String SRC_STAGE_PASSWORD = "stagefancyshop";
  /**
   * The constant SRC_STAGE_CLIENT_ID.
   */
  public static final String SRC_STAGE_CLIENT_ID  = "glUeBf8D89VCEvjIF5egwDZHlniOd-K3ILtoW4jvda3b3be3!b03759657b194309bfab316e7d442f260000000000000000";
  /**
   * The constant SRC_STAGE_Merchant_P12_CERTIFICATE.
   */
  public static final String SRC_STAGE_MERCHANT_P12_CERTIFICATE= "stage_fancyshop.p12";


  public static String getValue(String environment, Boolean masterpass, String variable){
    String val = "";
    if(environment != null) {
      DEFAULT_ENVIRONMENT_SDK = environment;
    }
    if(environment == null){
      environment = DEFAULT_ENVIRONMENT_SDK;
    }
    if(environment.equalsIgnoreCase("sandbox")){
      if(masterpass){
        return getMasterpass(variable);
      }
      return getSrcSandbox(variable);
    }
    return getSrcStage(variable);
  }

  public static String getValue(String variable){
    if(DEFAULT_ENVIRONMENT_SDK.equalsIgnoreCase("sandbox")){
      return getSrcSandbox(variable);
    }
    return getSrcStage(variable);
  }

  public static String getSrcStage(String value){

    switch (value) {
      case "NAME":
        return SRC_STAGE_NAME;

      case "FLAVOR":
        return SRC_STAGE_FLAVOR;

      case "CHECKOUT_ID":
        return SRC_STAGE_CHECKOUT_ID;

      case "CHECKOUT_URL":
        return SRC_STAGE_CHECKOUT_URL;

      case "CHECKOUT_SRC_URL":
        return SRC_STAGE_CHECKOUT_SRC_URL;

      case "SRCI_DOMAIN":
        return SRC_STAGE_SRCI_DOMAIN;

      case "MIDDLEWARE_DOMAIN":
        return SRC_STAGE_MIDDLEWARE_DOMAIN;

      case "KEY_ALIAS":
        return SRC_STAGE_KEY_ALIAS;

      case "PASSWORD":
        return SRC_STAGE_PASSWORD;

      case "CLIENT_ID":
        return SRC_STAGE_CLIENT_ID;

      case "MERCHANT_P12_CERTIFICATE":
        return SRC_STAGE_MERCHANT_P12_CERTIFICATE;

    }

    return " ";
  }

  public static String getSrcSandbox(String value){

    switch (value) {
      case "NAME":
        return SRC_SANDBOX_NAME;

      case "FLAVOR":
        return SRC_SANDBOX_FLAVOR;

      case "CHECKOUT_ID":
        return SRC_SANDBOX_CHECKOUT_ID;

      case "CHECKOUT_URL":
        return SRC_SANDBOX_CHECKOUT_URL;

      case "CHECKOUT_SRC_URL":
        return SRC_SANDBOX_CHECKOUT_SRC_URL;

      case "SRCI_DOMAIN":
        return SRC_SANDBOX_SRCI_DOMAIN;

      case "MIDDLEWARE_DOMAIN":
        return MASTERPASS_MIDDLEWARE_DOMAIN;

      case "KEY_ALIAS":
        return SRC_SANDBOX_KEY_ALIAS;

      case "PASSWORD":
        return SRC_SANDBOX_PASSWORD;

      case "CLIENT_ID":
        return SRC_SANDBOX_CLIENT_ID;

      case "MERCHANT_P12_CERTIFICATE":
        return SRC_SANDBOX_MERCHANT_P12_CERTIFICATE;

    }

    return  " ";
  }

  public static String getMasterpass(String value){

    switch (value) {
      case "NAME":
        return MASTERPASS_NAME;

      case "FLAVOR":
        return MASTERPASS_FLAVOR;

      case "CHECKOUT_ID":
        return MASTERPASS_CHECKOUT_ID;

      case "CHECKOUT_URL":
        return MASTERPASS_CHECKOUT_URL;

      case "CHECKOUT_SRC_URL":
        return MASTERPASS_CHECKOUT_SRC_URL;

      case "SRCI_DOMAIN":
        return MASTERPASS_SRCI_DOMAIN;

      case "MIDDLEWARE_DOMAIN":
        return MASTERPASS_MIDDLEWARE_DOMAIN;

      case "KEY_ALIAS":
        return MASTERPASS_KEY_ALIAS;

      case "PASSWORD":
        return MASTERPASS_PASSWORD;

      case "CLIENT_ID":
        return MASTERPASS_CLIENT_ID;

      case "MERCHANT_P12_CERTIFICATE":
        return MASTERPASS_MERCHANT_P12_CERTIFICATE;

    }

    return " ";
  }
}
