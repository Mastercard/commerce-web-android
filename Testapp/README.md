### Table of Contents

- [Overview](#overview)
- [Configuration](#configuration)

### <a name="overview">Overview</a>

`TestApp` is a sample Merchant application which shows the integration and usage of `CommerceWebSdk`
Merchant application developers can reference this app to integrate `CommerceWebSdk`

### <a name="configuration">Configuration</a>

#### Gradle

The mandatory values: ClientId, KeyAlias, KeyPassword and P12Certificate are set on `local.properties`
at project root level. The values are provided for different backend environments and can be 
configured on this file.

```properties
sdk.dir=ANDROID_SDK_LOCATION
mvn.url=MAVEN_URL
stageCheckoutId=MERCHANT_CHECKOUT_ID
stageKeyAlias=P12CERTIFICATE_KEY_ALIAS
stagePassword=P12CERTIFICATE_PASSWORD
stageClientId=SRC_CLIENT_ID
stageCertificate=P12CERTIFICATE_NAME
```
