### Table of Contents

- [Overview](#overview)
- [Installation](#installation)
- [Configuration](#configuration)
- [Checkout Button](#checkout-button)
- [Checkout](#checkout)
- [Checkout Result](#checkout-result)
    - [Custom URL Scheme](#custom-url-scheme)
    - [Intent Scheme](#intent-scheme)
- [Migrating from `masterpass-merchant`](#migrating-from-masterpass-merchant)

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
* `checkoutUrl`: The URL used to load the checkout experience. Note: 
when testing in the Sandbox environment, use 
`https://sandbox.masterpass.com/routing/v2/mobileapi/web-checkout`. For 
Production, use `https://masterpass.com/routing/v2/mobileapi/web-checkout`. 
* `callbackScheme`: This must match the scheme component of the 
`callbackUrl` configured for this merchant. This value is used to verify
the callback redirect from SRCi. ***Note***: If your `callbackUrl` is 
defined as an `Intent`, use the custom scheme provided in the `scheme=` 
segment of the `callbackUrl`


```java
Locale locale = Locale.US;
String checkoutId = "1d45705100044e14b52e71730e71cc5a";
String checkoutUrl = "https://src.mastercard.com/srci";
String callbackScheme = "fancyshop";
Set<CardType> allowedCardTypes = Collections.emptySet();
allowedCardTypes.add(CardType.MASTER);
allowedCardTypes.add(CardType.VISA);

CommerceConfig config = new CommerceConfig(locale, checkoutId, checkoutUrl, callbackScheme, allowedCardTypes);
CommerceWebSdk sdk = CommerceWebSdk.getInstance();
sdk.initialize(config, context);
```

### Checkout Button

One option for initiating a transaction is to use the `CheckoutButton` 
object returned by

```java
//CommerceWebSdk.java
public CheckoutButton getCheckoutButton(final CheckoutCallback checkoutCallback);
```

Add this to your layout with `LayoutParams.WRAP_CONTENT` for `width` and `height`.

When the user touches up on `CheckoutButton`, 
`checkoutCallback.getCheckoutRequest()` is called to return the 
`CheckoutRequest` for this transaction.

### Checkout

The second option for initiating a transaction is to use the 
`commerceWebSdk.checkout(Context, CheckoutRequest)` method directly.

Calling `checkout(Context, CheckoutRequest)` on the `CommerceWebSdk` 
object initiates the checkout experience.

* `Context`: This is used to start the checkout experience. 
***Note***: if this Context is an instance of Activity, 
`startActivityForResult()` is used. The result from checkout is returned 
to this activity by `onActivityResult()`.
* `checkoutRequest`: Data object with transaction-specific parameters 
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

### Checkout Result

The result of a checkout is returned to the application one of two ways, 
depending on the configured callback URL.

##### Custom URL Scheme

This is defined by a callback URL using a non-standard scheme or protocol. 
For example, a merchant named *FancyShop* may configure their callback 
URL simply as `fancyshop://`. This URL is invoked with the transaction 
result parameters `status` and `transactionId`, as

`fancyshop:///?status=success&transactionId=xxxx`

If using a custom URL scheme, an `Activity` must be given in the 
`checkout(Context, CheckoutRequest)` call. The checkout result is 
returned to the activity provided at checkout by

```java
@Override protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
	//verify the request code
	if (requestCode == CommerceWebSdk.COMMERCE_REQUEST_CODE) {
		//verify the resultCode
		if (resultCode == Activity.RESULT_OK) { //transaction was successful
			String transactionId = resultIntent.getStringExtra(CommerceWebSdk.COMMERCE_TRANSACTION_ID);
			//complete the transaction
		} else { //resultCode == Activity.RESULT_CANCEL
			//transaction canceled
		}
	}
}
```

##### Intent Scheme

If using an Intent URI as the `callbackUrl`, the checkout result is 
returned to the activity configured to handle the Intent. *FancyShop* 
would define an Intent URI similar to

`intent://fancyshop/#Intent;package=com.fancyshop;scheme=fancyshop;end`

In order to receive the result, an activity must be present in the 
application which can handle this intent.

In your application `AndroidManifest.xml`, you can specify such an 
activity with something like

```xml
<activity
    android:name=".CheckoutActivity">
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <category android:name="android.intent.category.BROWSABLE"/>
        <data
			android:host="fancyshop"
			android:path="/"
			android:scheme="fancyshop"/>
    </intent-filter>
</activity>
```

In `CheckoutActivity` you would override either `onCreate()` or 
`onNewIntent()` to parse the Intent for the transaction data.

```java
Intent intent = getIntent();
String status = intent.getStringExtra(CommerceWebSdk.COMMERCE_STATUS);
String transactionId = intent.getStringExtra(CommerceWebSdk.COMMERCE_TRANSACTION_ID);

if (status.equals(CommerceWebSdk.STATUS_SUCCESS)) {
	String transactionId = intent.getStringExtra(CommerceWebSdk.COMMERCE_TRANSACTION_ID);
	//complete transaction
} else {
	//handle canceled transaction
}
```

### Migrating from `masterpass-merchant`

Existing applications using `masterpass-merchant` today easily migrate 
to `commerce-web` with minimal changes. Consider the following when 
migrating from `masterpass-merchant` to `commerce-web`.

#### Interfaces and Classes

All of the classes and interfaces used with `masterpass-merchant` are 
packaged with `commerce-web`. This means that when switching to 
`commerce-web`, existing class imports can remain unchanged. It is, 
however, recommended to use classes within the `com.mastercard.commerce`
domain. Classes in the `com.mastercard.mp` domain are `deprecated`. 
There are some minor changes, noted below, made to existing classes.

##### Merchant Configuration

`MasterpassMerchantConfiguration` has 2 new required fields

* `allowedNetworkTypes` : The payment networks supported by this 
merchant (e.g. master, visa, amex). *Note: this is moved from 
`MasterpassCheckoutRequest`*
* `checkoutUrl`         : The URL used to load the checkout experience. 
This parameter replaces `environment` from `masterpass-merchant`. 
*Note: if you are migrating to `commerce-web`, but still plan to 
checkout with `Masterpass`, this URL still needs to be provided.*

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
`MasterpassPaymentMethod` object which can be displayed. It is 
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
called with `ERROR_CODE_NOT_SUPPORTED` error.
