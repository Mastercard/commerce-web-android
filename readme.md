# CommerceWeb SDK

### Table of Contents

- [Overview](#overview)
- [Configuration](#configuration)
- [Checkout](#checkout)
- [Checkout Result](#checkout-result)
    - [Custom URL Scheme](#custom-url-scheme)
    - [Intent Scheme](#intent-scheme)

### Overview

CommerceWeb SDK is a lightweight component used to integrate Merchants with [**EMV Secure Remote Commerce**](https://www.emvco.com/emv-technologies/src/) and  Mastercard's web-based SRC-Initiator. CommerceWeb facilitates the initiation of the checkout experience as well as returning the transaction result to the Merchant after completion.

### Configuration

When instantiating `CommerceWebSDK`, a `CommerceConfig` object needs to be provided.

`CommerceConfig` requires the following parameters:

* `locale`: This is the locale in which the transaction is processing
* `checkoutId`: The unique identifier assigned to the merchant during onboarding
* `baseUrl`: The base URL of the SRCi to load, e.g. `src.mastercard.com`
* `callbackScheme`: This must match the scheme component of the `callbackUrl` configured for this merchant. This value is used to verify the callback redirect from SRCi.
	* **Note**: If your `callbackUrl` is defined as an `Intent`, use the custom scheme provided in the `scheme=` segment of the `callbackUrl`


```java
Locale locale = Locale.US;
String checkoutId = "1d45705100044e14b52e71730e71cc5a";
String baseUrl = "src.mastercard.com";
String callbackScheme = "customscheme";

CommerceConfig config = new CommerceConfig(locale, checkoutId, baseUrl, callbackScheme);
CommerceWebSdk sdk = new CommerceWebSdk(config);
```


### Checkout

Calling `checkout(Activity, CheckoutRequest)` on the `CommerceWebSdk` object will initiate the checkout experience.

* `Activity`: `startActivityForResult()` is called on this activity in order to present the SRCi. The result from checkout is returned to this activity by `onActivityResult()`
* `checkoutRequest`: Data object with transaction-specific parameters needed to complete checkout. This request can also override existing merchant configurations.
	* Required fields:
		*  `allowedCardTypes`: Set of all card types accepted for this transaction
		*  `amount`: The transaction total to be authorized
		*  `cartId`: Merchant's unique identifier for this transaction
	* Optional Fields: These fields can be assigned to override the default values configured by the merchant.
		* `callbackUrl`: URL used to communicate back to the merchant application
		* `cryptoOptions`: Cryptogram formats accepted by this merchant
		* `cvc2Support`: Enable or disable support for CVC2 card security
		* `shippingLocationProfile`: Shipping locations available for this merchant
		* `suppress3Ds`: Enable or disable 3DS verification
		* `suppressShippingAddress`: Enable or disable shipping options. Typically for digital goods or services, this will be set to `true`.
		* `unpredictableNumber`: For tokenized transactions, `unpredictableNumber` is required for cryptogram generation
		* `validityPeriodMinutes`: the expiration time of a generated cryptogram, in minutes

```java
Set<Mastercard.MastercardFormat> mastercardFormatSet = new HashSet<>();
mastercardFormatSet.add(Mastercard.MastercardFormat.ICC);
mastercardFormatSet.add(Mastercard.MastercardFormat.UCAF);

Set<Visa.VisaFormat> visaFormatSet = new HashSet<>();
visaFormatSet.add(Visa.VisaFormat.TAVV);

CryptoOptions mastercard = new Mastercard(mastercardFormatSet);
CryptoOptions visa = new Visa(visaFormatSet);

Set<CryptoOptions> cryptoOptionsSet = new HashSet<>();
cryptoOptionsSet.add(mastercard);
cryptoOptionsSet.add(visa);

Set<CardType> cardTypes = new HashSet<>();
cardTypes.add(CardType.MASTER);
cardTypes.add(CardType.VISA);

CheckoutRequest request = new CheckoutRequest.Builder()
	.amount(totalAmount)
	.cartId(UUID.randomUUID().toString())
	.currency("USD")
	.allowedCardTypes(cardTypes)
	.cryptoOptions(cryptoOptionsSet)
	.suppressShippingAddress(response.isSuppressShipping())
	.build();

commerceWebSdk.checkout(request, mCartListView.getActivity());
```

### Checkout Result

The result of a checkout is returned to the application one of two ways, depending on the configured callback URL.

##### Custom URL Scheme

This is defined by a callback URL using a non-standard scheme or protocol. For example, a merchant named *FancyShop* may configure their callback URL simply as `fancyshop://`. This URL will be invoked with the transaction result parameters `status` and `transactionId`, as

`fancyshop:///?status=success&transactionId=xxxx`

Using a custom URL scheme, the checkout result will be returned to the activity provided at checkout by

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

Using an Intent URI, the checkout result will be returned to the activity configured to handle the Intent. *FancyShop* would define an Intent URI similar to

`intent://fancyshop/#Intent;package=com.fancyshop;scheme=fancyshop;end`

In order to receive the result, an activity must be present in the application which can handle this intent.

In your application `AndroidManifest.xml`, you can specify such an activity with something like

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

In `CheckoutActivity` you would override either `onCreate()` or `onNewIntent()` to parse the Intent for the transaction data.

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