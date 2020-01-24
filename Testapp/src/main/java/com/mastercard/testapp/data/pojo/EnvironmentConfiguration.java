package com.mastercard.testapp.data.pojo;

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

  private EnvironmentConfiguration(Builder builder) {
    this.name = builder.name;
    this.flavor = builder.flavor;
    this.checkoutId = builder.checkoutId;
    this.checkoutUrl = builder.checkoutUrl;
    this.checkoutSrcUrl = builder.checkoutSrcUrl;
    this.srciDomain = builder.srciDomain;
    this.middlewareDomain = builder.middlewareDomain;
    this.keyAlias = builder.keyAlias;
    this.password = builder.password;
    this.clientId = builder.clientId;
    this.merchantP12Certificate = builder.merchantP12Certificate;
  }

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

  public static final class Builder {
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

    public Builder() {
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setFlavor(String flavor) {
      this.flavor = flavor;
      return this;
    }

    public Builder setCheckoutId(String checkoutId) {
      this.checkoutId = checkoutId;
      return this;
    }

    public Builder setCheckoutUrl(String checkoutUrl) {
      this.checkoutUrl = checkoutUrl;
      return this;
    }

    public Builder setCheckoutSrcUrl(String checkoutSrcUrl) {
      this.checkoutSrcUrl = checkoutSrcUrl;
      return this;
    }

    public Builder setSrciDomain(String srciDomain) {
      this.srciDomain = srciDomain;
      return this;
    }

    public Builder setMiddlewareDomain(String middlewareDomain) {
      this.middlewareDomain = middlewareDomain;
      return this;
    }

    public Builder setKeyAlias(String keyAlias) {
      this.keyAlias = keyAlias;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder setClientId(String clientId) {
      this.clientId = clientId;
      return this;
    }

    public Builder setMerchantP12Certificate(String merchantP12Certificate) {
      this.merchantP12Certificate = merchantP12Certificate;
      return this;
    }

    public EnvironmentConfiguration build() {
      return new EnvironmentConfiguration(this);
    }
  }
}
