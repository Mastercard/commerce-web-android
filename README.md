### Table of Contents

- [Overview](#overview)
- [Installation](#installation)
- [Configuration](#configuration)
- [Checkout Button](#checkout-button)
- [Checkout](#checkout)
- [Transaction Result](#transaction-result)
    - [Custom URL Scheme](#custom-url-scheme)
    - [Intent Scheme](#intent-scheme)
- [Migrating from masterpass-merchant](#migrating-from-masterpass-merchant)

### Overview

`commerce-web` is a lightweight library used to integrate Merchants with 
[**EMV Secure Remote Commerce**](https://www.emvco.com/emv-technologies/src/) 
and  Mastercard's web-based SRC-Initiator with backward 
compatibility for existing Masterpass integrations. `commerce-web` 
facilitates the initiation of the checkout experience and returns the 
transaction result to the Merchant after completion.

***Note: currently, this library is only recommended for existing U.S.
Masterpass merchants.***

### Installation

#### Gradle

To include `commerce-web` in your Android project, include the following
in `build.gradle`:

```groovy
dependencies {
  implementation 'com.mastercard:commerce-web:1.0.0-SNAPSHOT'
}
```

Note: For SNAPSHOT releases, please include the following Maven 
Repository in your build script:

```groovy
repositories {
  maven {
	 url "https://oss.sonatype.org/content/repositories/snapshots/" 
  }
}
```

### Configuration

When initializing `CommerceWebSdk`, a `CommerceConfig` and `Context` 
object need to be provided.

`CommerceConfig` requires the following parameters:

* `locale`: This is the locale in which the transaction is processing
* `checkoutId`: The unique identifier assigned to the merchant during 
onboarding
* `checkoutUrl`: The URL used to load the checkout experience. *Note: 
when testing in the Sandbox environment, use 
`https://sandbox.masterpass.com/routing/v2/mobileapi/web-checkout`. For 
Production, use `https://masterpass.com/routing/v2/mobileapi/web-checkout`.*

```java
Locale locale = Locale.US;
String checkoutId = "1d45705100044e14b52e71730e71cc5a";
String checkoutUrl = "https://masterpass.com/routing/v2/mobileapi/web-checkout";
Set<CardType> allowedCardTypes = Collections.emptySet();
allowedCardTypes.add(CardType.MASTER);
allowedCardTypes.add(CardType.VISA);

CommerceConfig config = new CommerceConfig(locale, checkoutId, checkoutUrl, allowedCardTypes);
CommerceWebSdk sdk = CommerceWebSdk.getInstance();
sdk.initialize(config, context);
```

### Checkout Button

Transactions are initiated using the `CheckoutButton` 
object returned by

```java
//CommerceWebSdk.java
public CheckoutButton getCheckoutButton(final CheckoutCallback checkoutCallback);
```

Add this to your layout with `LayoutParams.WRAP_CONTENT` for `width` and `height`.

When the user touches up on `CheckoutButton`, 
`checkoutCallback.getCheckoutRequest()` is called to return the 
`CheckoutRequest` for this transaction.

#### Checkout Request

```java
//CheckoutCallback.java
CheckoutRequest getCheckoutRequest();
```

`checkoutRequest`: Data object with transaction-specific parameters 
needed to complete checkout. This request can also override existing 
merchant configurations.

* Required fields:
	*  `amount`: The transaction total to be authorized
	*  `cartId`: Merchant's unique identifier for this transaction
	*  `currency`: Currency used for the current transaction
* Optional Fields: These fields can be assigned to override the 
default values configured by the merchant during onboarding.
	* `callbackUrl`: URL used to communicate back to the merchant 
	application
	* `cryptoOptions`: Cryptogram formats accepted by this merchant
	* `cvc2Support`: Enable or disable support for CVC2 card security
	* `shippingLocationProfile`: Shipping locations available for 
	this merchant
	* `suppress3Ds`: Enable or disable 3DS verification
	* `suppressShippingAddress`: Enable or disable shipping options. 
	Typically for digital goods or services, this is set to `true`.
	* `unpredictableNumber`: For tokenized transactions, 
	`unpredictableNumber` is required for cryptogram generation
	* `validityPeriodMinutes`: the expiration time of a generated 
	cryptogram, in minutes

```java
Set<Mastercard.MastercardFormat> mastercardFormatSet = new HashSet<>();
mastercardFormatSet.add(Mastercard.MastercardFormat.ICC);
mastercardFormatSet.add(Mastercard.MastercardFormat.UCAF);

CryptoOptions mastercard = new Mastercard(mastercardFormatSet);
CryptoOptions visa = new Visa(visaFormatSet);

Set<CryptoOptions> cryptoOptionsSet = new HashSet<>();
cryptoOptionsSet.add(mastercard);
cryptoOptionsSet.add(visa);

CheckoutRequest request = new CheckoutRequest.Builder()
	.amount(totalAmount)
	.cartId(UUID.randomUUID().toString())
	.currency("USD")
	.cryptoOptions(cryptoOptionsSet)
	.suppressShippingAddress(isShippingRequired())
	.build();

commerceWebSdk.checkout(request, shoppingCartFragment.getActivity());
```

### Transaction Result

The result of a transaction is returned to the application via an `Intent` containing the `transactionId`.

##### Intent Scheme

`callbackUrl` must be configured with an `Intent` URI. The transaction result is returned to the activity configured to receive the `Intent`. *FancyShop* 
would define a URI similar to

`intent://fancyshop/#Intent;package=com.fancyshop;scheme=fancyshop;end`

In order to receive the result, the application must declare an `intent-filter` for the `Activity` receiving the `Intent`

In the application `AndroidManifest.xml`, the following `intent-filter` is required to receive the above `Intent` URI:

```xml
<activity
    android:name=".CheckoutActivity">
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <data
			android:host="fancyshop"
			android:path="/"
			android:scheme="fancyshop"/>
    </intent-filter>
</activity>
```

In `CheckoutActivity` you would override either `onCreate()` or 
`onNewIntent()` to parse the Intent for the transaction data. The activity is started with the `Intent.FLAG_ACTIVITY_CLEAR_TOP` flag.

When the `Intent` is received, the `transactionId` is returned by the `stringExtra` `CommerceWebSdk.COMMERCE_TRANSACTION_ID`

```java
Intent intent = getIntent();
String transactionId = intent.getStringExtra(CommerceWebSdk.COMMERCE_TRANSACTION_ID);

//complete transaction
```

### Migrating from `masterpass-merchant:2.8.x`

***Note: `masterpass-merchant` APIs are deprecated in `commerce-web` and will be removed in subsequent versions. It is encouraged to migrate to the APIs above.***

`commerce-web` provides API compatibility for `masterpass-merchant:2.8.x`. Existing applications using `masterpass-merchant` can easily migrate 
to `commerce-web` with minimal changes. Consider the following when 
migrating from `masterpass-merchant` to `commerce-web`.

#### Interfaces and Classes

All classes and interfaces used with `masterpass-merchant` are 
packaged with `commerce-web`. This means that when switching to 
`commerce-web`, existing class imports can remain unchanged. There are changes to the functionality of some APIs in order to support future payment platforms.

##### Merchant Configuration

`MasterpassMerchantConfiguration` has 2 new required fields

* `allowedNetworkTypes` : The payment networks supported by this 
merchant (e.g. `master`, `visa`, `amex`). *Note: this is moved from 
`MasterpassCheckoutRequest`*
* `checkoutUrl`         : The URL used to load the checkout experience. 
This parameter replaces `environment` from `masterpass-merchant`. 

| Environment           | URL                                                              |
|-----------------------|------------------------------------------------------------------|
| Masterpass Sandbox    | https://sandbox.masterpass.com/routing/v2/mobileapi/web-checkout |
| Masterpass Production | https://masterpass.com/routing/v2/mobileapi/web-checkout         |

##### Add Payment Method

```java
//MasterpassMerchant.java
public static void addMasterpassPaymentMethod(PaymentMethodCallback paymentMethodCallback);
```

For `commerce-web`, this method provides a single 
`MasterpassPaymentMethod` object which can be displayed and used for `paymentMethodCheckout()`. It is 
configured with the following fields

* `paymentMethodName` : `Masterpass`
* `paymentMethodId` : `101`
* `paymentMethodLogo` The masterpass logo as `Bitmap`
* `pairingTransactionId` : ""
* `paymentMethodLastFourDigits` : ""


##### Payment Method Checkout

```java
//MasterpassMerchant.java
public static void paymentMethodCheckout(String paymentMethodId,
      MasterpassCheckoutCallback masterpassCheckoutCallback);
```

`paymentMethodCheckout()` initiates the standard checkout flow using 
`masterpassCheckoutCallback.getCheckoutRequest()`.

##### Pairing With Checkout

```java
//MasterpassMerchant.java
public static void pairing(boolean isCheckoutWithPairingEnabled, MasterpassCheckoutCallback masterpassCheckoutCallback);
```

`pairing(isCheckoutWithPairingEnabled, callback)` initiates the 
standard checkout flow if `isCheckoutWithPairingEnabled` is `true`. 
Otherwise, `checkoutCallback.onCheckoutError(MasterpassError)` is 
called with `MasterpassError.ERROR_CODE_NOT_SUPPORTED`.
