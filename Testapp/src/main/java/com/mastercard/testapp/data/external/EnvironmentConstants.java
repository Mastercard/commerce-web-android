package com.mastercard.testapp.data.external;

import com.mastercard.testapp.BuildConfig;


public class EnvironmentConstants {


  public static String DEFAULT_ENVIRONMENT_SDK = BuildConfig.ENVIRONMENT;

  public static final String MASTERPASS_NAME = "Sandbox";

  public static final String MASTERPASS_FLAVOR = "MasterpassSandbox";

  public static final String MASTERPASS_CHECKOUT_ID  = "56ba329519194adf802fe7ba97a9ef3d";

  public static final String MASTERPASS_CHECKOUT_URL  = "https://sandbox.masterpass.com/routing/v2/mobileapi/web-checkout";

  public static final String MASTERPASS_CHECKOUT_SRC_URL = "https://sandbox.src.mastercard.com/srci/";

  public static final String MASTERPASS_SRCI_DOMAIN  = "https://sandbox.src.mastercard.com/api";

  public static final String MASTERPASS_MIDDLEWARE_DOMAIN = "https://sandbox.src.mastercard.com/srci/api";

  public static final String MASTERPASS_KEY_ALIAS = "sandbox_fancyshop";

  public static final String MASTERPASS_PASSWORD = "sbxfancyshop";

  public static final String MASTERPASS_CLIENT_ID  = "ph3MHQtvmb7eCUOutflY7rRgsxpl8KDs_lovZ7O_f4a513bf!0c55e219ce3e467eaeb8418ac49edd950000000000000000";

  public static final String MASTERPASS_MERCHANT_P12_CERTIFICATE = "sandbox_fancyshop.p12";

  public static final String SRC_SANDBOX_NAME = "Sandbox";

  public static final String SRC_SANDBOX_FLAVOR = "SrcSandbox" ;

  public static final String SRC_SANDBOX_CHECKOUT_ID  = "56ba329519194adf802fe7ba97a9ef3d";

  public static final String SRC_SANDBOX_CHECKOUT_URL  = "https://sandbox.src.mastercard.com/srci/";

  public static final String SRC_SANDBOX_CHECKOUT_SRC_URL = "https://sandbox.src.mastercard.com/srci/";

  public static final String SRC_SANDBOX_SRCI_DOMAIN  = "https://sandbox.src.mastercard.com/api";

  public static final String SRC_SANDBOX_MIDDLEWARE_DOMAIN = "https://sandbox.src.mastercard.com/srci/api";

  public static final String SRC_SANDBOX_KEY_ALIAS = "sandbox_fancyshop";

  public static final String SRC_SANDBOX_PASSWORD = "sbxfancyshop";

  public static final String SRC_SANDBOX_CLIENT_ID = "ph3MHQtvmb7eCUOutflY7rRgsxpl8KDs_lovZ7O_f4a513bf!0c55e219ce3e467eaeb8418ac49edd950000000000000000";

  public static final String SRC_SANDBOX_MERCHANT_P12_CERTIFICATE = "sandbox_fancyshop.p12";

  public static final String SRC_STAGE_NAME = "Stage";

  public static final String SRC_STAGE_FLAVOR = "SrcStage";

  public static final String SRC_STAGE_CHECKOUT_ID  = "f224616707ed44e6abb0455ccfc7e2f3";

  public static final String SRC_STAGE_CHECKOUT_URL  = "https://stage.src.mastercard.com/srci/";

  public static final String SRC_STAGE_CHECKOUT_SRC_URL = "https://stage.src.mastercard.com/srci/";

  public static final String SRC_STAGE_SRCI_DOMAIN  = "https://stage.src.mastercard.com/api";

  public static final String SRC_STAGE_MIDDLEWARE_DOMAIN = "https://stage.src.mastercard.com/srci/api";

  public static final String SRC_STAGE_KEY_ALIAS = "stage_fancyshop";

  public static final String SRC_STAGE_PASSWORD = "stagefancyshop";

  public static final String SRC_STAGE_CLIENT_ID  = "glUeBf8D89VCEvjIF5egwDZHlniOd-K3ILtoW4jvda3b3be3!b03759657b194309bfab316e7d442f260000000000000000";

  public static final String SRC_STAGE_MERCHANT_P12_CERTIFICATE= "stage_fancyshop.p12";

  public static final String SRC_PROD_NAME = "Production";

  public static final String SRC_PROD_FLAVOR = "SrcProd";

  public static final String SRC_PROD_CHECKOUT_ID  = "56ba329519194adf802fe7ba97a9ef3d";

  public static final String SRC_PROD_CHECKOUT_URL  = "https://src.mastercard.com/srci/";

  public static final String SRC_PROD_CHECKOUT_SRC_URL  = "https://src.mastercard.com/srci/";

  public static final String SRC_PROD_SRCI_DOMAIN  = "https://src.mastercard.com/api";

  public static final String SRC_PROD_MIDDLEWARE_DOMAIN = "https://src.mastercard.com/srci/api";

  public static final String SRC_PROD_KEY_ALIAS = "prod_fancyshop";

  public static final String SRC_PROD_PASSWORD = "prodfancyshop";

  public static final String SRC_PROD_CLIENT_ID  = "vlg-xtTMeiQfmaFzFx3Ffg9JHc395c0KVRjdfLQs1efc70fb!dba4e21bdb344e88baf54099b67369c20000000000000000";

  public static final String SRC_PROD_MERCHANT_P12_CERTIFICATE= "prod_fancyshop.p12";

  public static String getValue(String environment, Boolean masterpass, String value){
    String env = environment;

    if(environment != null) {
      DEFAULT_ENVIRONMENT_SDK = env;
    }

    if(env == null){
      env = DEFAULT_ENVIRONMENT_SDK;
    }

    if( env.equalsIgnoreCase("sandbox")){
      if(masterpass){
        return getMasterpass(value);
      }
      return getSrcSandbox(value);
    }
    else if(env.equalsIgnoreCase("production")){
      return getSrcProd(value);
    }

    return getSrcStage(value);
  }

  public static String getValue(String variable){
    if(DEFAULT_ENVIRONMENT_SDK.equalsIgnoreCase("sandbox")){
      return getSrcSandbox(variable);
    }
    else if (DEFAULT_ENVIRONMENT_SDK.equalsIgnoreCase("production")){
      return getSrcProd(variable);
    }
    return getSrcStage(variable);
  }

  public static String getSrcStage(String value){

    String val = value;

    switch (val) {
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

    return val;
  }

  public static String getSrcSandbox(String value){

    String val = value;

    switch (val) {
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
        return SRC_SANDBOX_MIDDLEWARE_DOMAIN;

      case "KEY_ALIAS":
        return SRC_SANDBOX_KEY_ALIAS;

      case "PASSWORD":
        return SRC_SANDBOX_PASSWORD;

      case "CLIENT_ID":
        return SRC_SANDBOX_CLIENT_ID;

      case "MERCHANT_P12_CERTIFICATE":
        return SRC_SANDBOX_MERCHANT_P12_CERTIFICATE;

    }

    return  val;
  }

  public static String getMasterpass(String value){

    String val = value;

    switch (val) {
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

    return val;
  }

  public static String getSrcProd(String value){

    String val = value;

    switch (val) {
      case "NAME":
        return SRC_PROD_NAME;

      case "FLAVOR":
        return SRC_PROD_FLAVOR;

      case "CHECKOUT_ID":
        return SRC_PROD_CHECKOUT_ID;

      case "CHECKOUT_URL":
        return SRC_PROD_CHECKOUT_URL;

      case "CHECKOUT_SRC_URL":
        return SRC_PROD_CHECKOUT_SRC_URL;

      case "SRCI_DOMAIN":
        return SRC_PROD_SRCI_DOMAIN;

      case "MIDDLEWARE_DOMAIN":
        return SRC_PROD_MIDDLEWARE_DOMAIN;

      case "KEY_ALIAS":
        return SRC_PROD_KEY_ALIAS;

      case "PASSWORD":
        return SRC_PROD_PASSWORD;

      case "CLIENT_ID":
        return SRC_PROD_CLIENT_ID;

      case "MERCHANT_P12_CERTIFICATE":
        return SRC_PROD_MERCHANT_P12_CERTIFICATE;

    }

    return val;
  }
}
