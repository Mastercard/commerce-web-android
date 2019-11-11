package com.mastercard.testapp.data.external;

/**
 * EnvironmentConfiguration POJO class to store configuration from JSON file
 */
public class EnvironmentConfiguration {

  private String name;
  private String flavor;
  private String checkoutId;
  private String checkoutUrl;
  private String checkoutSrcUrl;
  private String srciDomain;
  private String middlewareDomain;
  private String keyAlias;
  private String password;
  private String clientId;
  private String merchantP12Certificate;

  public String getName()  { return name;}

  public String getFlavor()  { return flavor;}

  public String getCheckoutId()  { return checkoutId;}

  public String getCheckoutURL()  { return checkoutUrl;}

  public String getCheckoutSrcUrl()  { return checkoutSrcUrl;}

  public String getSrciDomain()  { return srciDomain;}

  public String getMiddlewareDomain()  { return middlewareDomain;}

  public String getKeyAlias()  { return keyAlias;}

  public String getPassword()  { return password;}

  public String getClientId()  { return clientId;}

  public String getMerchantP12Certificate()  { return merchantP12Certificate;}
  
}
