### Table of Contents

- [Overview](#overview)
- [Installation](#installation)
- [Configuration](#configuration)
- [Checkout Button](#checkout-button)
	- [Checkout Request](#checkout-request)
- [Transaction Result](#transaction-result)
	- [Intent Scheme](#intent-scheme)
- [Migrating from masterpass-merchant](#migrating-from-masterpass-merchant)
	- [Interfaces and classes](#interfaces-and-classes)
	- [Add Payment Method](#add-payment-method)
	- [Payment Method Checkout](#payment-method-checkout)
	- [Pairing With Checkout](#pairing-with-checkout)
- [Direct Integration](#direct-integration)
	- [Checkout URL Building](#url-building)
	- [WebView Configuration](#webview-configuration) 
	- [WebviewClient](#webviewclient)
	- [WebChromeClient](#webchromeclient)

### <a name="overview">Overview</a>

`commerce-web` is a lightweight library used to integrate Merchants with 
[**EMV Secure Remote Commerce**](https://www.emvco.com/emv-technologies/src/) 
and  Mastercard's web-based SRC-Initiator with backward 
compatibility for existing Masterpass integrations. `commerce-web` 
facilitates the initiation of the checkout experience and returns the 
transaction result to the Merchant after completion.

***Note: currently, this library is only recommended for existing U.S. Masterpass merchants. Merchants using version 2.8 or older must follow the steps in the migration section.***

### <a name="installation">Installation</a>

#### Gradle

To include `commerce-web` in your Android project, include the following
in `build.gradle`:

```groovy
dependencies {
  implementation 'com.mastercard.commerce:commerce-web:1.0.0'
}
```

### <a name="configuration">Configuration</a>

When initializing `CommerceWebSdk`, a `CommerceConfig` and `Context` 
object need to be provided.

`CommerceConfig` requires the following parameters:

* `locale`: This is the locale in which the transaction is processing
* `checkoutId`: The unique identifier assigned to the merchant during 
onboarding
* `checkoutUrl`: The URL used to load the checkout experience. Note: 
when testing in the Sandbox environment, use 
**`https://sandbox.src.mastercard.com/srci/`**. For 
Production, use **`https://src.mastercard.com/srci/`**
* `allowedCardTypes`:  The payment networks supported by this merchant (e.g. master, visa, amex)

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

### <a name="checkout-button">Checkout Button</a>

Transactions are initiated using the `CheckoutButton` 
object returned by

```java
//CommerceWebSdk.java
public CheckoutButton getCheckoutButton(final CheckoutCallback checkoutCallback);
```

Add this to your layout with `LayoutParams.WRAP_CONTENT` for `width` and `height`.

When the user touches up on `CheckoutButton`, 
`checkoutCallback.getCheckoutRequest(CheckoutRequestListener listener)` is called to return the
`CheckoutRequest` for this transaction.
`CheckoutRequestListener` is Listener interface to set the `CheckoutRequest`

#### <a name="checkout-request">Checkout Request</a>

```java
//CheckoutCallback.java
CheckoutRequest getCheckoutRequest();
```

`checkoutRequest`: Data object with transaction-specific parameters 
needed to complete checkout. This request can also override existing 
merchant configurations.

Here are the required and optional fields:

| Parameter                | Type           | Required   | Description
|--------------------------|------------|:----------:|---------------------------------------------------------------------------------------------------------|
| amount                   | Double     | Yes        | The transaction total to be authorized
| cartId                   | String     | Yes        | Randomly generated UUID used as a transaction id
| currency                 | String     | Yes         | Currency of the transaction
| callbackUrl              | String     | No         | URL used to communicate back to the merchant application
| cryptoOptions            | Set\<CryptoOptions>     | No         | Cryptogram formats accepted by this merchant
| cvc2Support              | Boolean     | No         | Enable or disable support for CVC2 card security
| shippingLocationProfile  | String     | No         | Shipping locations available for this merchant
| suppress3Ds              | Boolean     | No         | Enable or disable 3DS verification
| suppressShippingAddress  | Boolean     | No         | Enable or disable shipping options. Typically for digital goods or services, this will be set to true
| unpredictableNumber      | String     | No         | For tokenized transactions, unpredictableNumber is required for cryptogram generation
| validityPeriodMinutes    | Integer     | No         | The expiration time of a generated cryptogram, in minutes

The implementation of the checkout with these parameters:

```java
@Override public CheckoutRequest getCheckoutRequest() {
	Set<Mastercard.MastercardFormat> mastercardFormatSet = new HashSet<>();
	mastercardFormatSet.add(Mastercard.MastercardFormat.ICC);
	mastercardFormatSet.add(Mastercard.MastercardFormat.UCAF);

	CryptoOptions mastercard = new Mastercard(mastercardFormatSet);
	
	Set<CryptoOptions> cryptoOptionsSet = new HashSet<>();
	cryptoOptionsSet.add(mastercard);
	
	CheckoutRequest request = new CheckoutRequest.Builder()
		.amount(totalAmount)
		.cartId(UUID.randomUUID().toString())
		.currency("USD")
		.cryptoOptions(cryptoOptionsSet)
		.suppressShippingAddress(isShippingRequired())
		.build();

	return request;
}
```

### <a name="transaction-result">Transaction Result</a>

The result of a transaction is returned to the application via an `Intent` containing the `transactionId`.

##### <a name="intent-scheme">Intent Scheme</a>

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

### <a name="migrating-from-masterpass-merchant">Migrating from `masterpass-merchant:2.8.x`</a>

***Note: `masterpass-merchant` APIs are deprecated in `commerce-web` and will be removed in subsequent versions. It is encouraged to migrate to the APIs above.***

`commerce-web` provides API compatibility for `masterpass-merchant:2.8.x`. Existing applications using `masterpass-merchant` can easily migrate 
to `commerce-web` with minimal changes. Consider the following when 
migrating from `masterpass-merchant` to `commerce-web`.

#### <a name="interfaces-and-classes">Interfaces and Classes</a>

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
| Masterpass Sandbox    | **https://sandbox.src.mastercard.com/srci/** |
| Masterpass Production | **https://src.mastercard.com/srci/**        |

#### **If using an older version of Masterpass than 2.8.x, please start over the integration [here](#installation)**

##### <a name="add-payment-method">Add Payment Method</a>

***Note: this method is deprecated***

```java
//MasterpassMerchant.java
public static void addMasterpassPaymentMethod(PaymentMethodCallback paymentMethodCallback);
```

For `commerce-web`, this method provides a single 
`MasterpassPaymentMethod` object which can be displayed and used for `paymentMethodCheckout()`. It is 
configured with the following fields

* `paymentMethodLogo` The masterpass logo as `Bitmap`
* `pairingTransactionId` : ""
* `paymentMethodLastFourDigits` : ""

##### <a name="payment-method-checkout">Payment Method Checkout</a>

***Note: this method is deprecated***

```java
//MasterpassMerchant.java
public static void paymentMethodCheckout(String paymentMethodId,
      MasterpassCheckoutCallback masterpassCheckoutCallback);
```

`paymentMethodCheckout()` initiates the standard checkout flow using 
`masterpassCheckoutCallback.getCheckoutRequest()`.


##### <a name="pairing-with-checkout">Pairing With Checkout</a>

***Note: this method is deprecated***

```java
//MasterpassMerchant.java
public static void pairing(boolean isCheckoutWithPairingEnabled, MasterpassCheckoutCallback masterpassCheckoutCallback);
```

`pairing(isCheckoutWithPairingEnabled, callback)` initiates the 
standard checkout flow if `isCheckoutWithPairingEnabled` is `true`. 
Otherwise, `checkoutCallback.onCheckoutError(MasterpassError)` is 
called with `MasterpassError.ERROR_CODE_NOT_SUPPORTED`.

### <a name="direct-integration">Direct Integration</a>

Integrating with the web checkout experience is possible without this SDK. Include `com.android.support:webkit:${VERSION}` as a project dependency in `build.gradle`. 

**Refer to the Android developer documentation for `WebView` [here](https://developer.android.com/guide/webapps/webview).**

#### <a name="url-building">Checkout URL Building</a>

For the `WebView`, we need to build a url with required and optional parameters to load the webView itself. The checkout URL sample and parameters can be found below:

```
https://stage.src.mastercard.com/srci/?checkoutId=asdfghjk123456&
cartId=111111-2222-3333-aaaa-qwerqwerqwer&
amount=11.22&currency=USD&allowedCardTypes=master%2Camex%2Cvisa&
suppressShippingAddress=false&locale=en_US&channel=mobile&
masterCryptoFormat=UCAF%2CICC 
```
 
| Parameter                | Required   | Description
|--------------------------|:----------:|---------------------------------------------------------------------------------------------------------|
| allowedCardTypes 		    | Yes        | The cards the merchant supports (Mastercard/Visa/Amex) |
| amount                   | Yes        | The transaction total to be authorized
| cartId                   | Yes        | Randomly generated UUID used as a transaction id
| channel 				    | Yes        | Default should be set to mobile |
| checkoutID				    | Yes        | This value is provided from the merchant onboarding |
| currency                 | Yes        | Currency of the transaction
| callbackUrl              | No         | URL used to communicate back to the merchant application
| cryptoOptions            | No         | Cryptogram formats accepted by this merchant
| cvc2Support              | No         | Enable or disable support for CVC2 card security
| locale 					    | No         | The language Masterpass should load |
| masterCryptoFormat 	    | No         | Default should be set to UCAF%2CICC |
| shippingLocationProfile  | No         | Shipping locations available for this merchant
| suppress3Ds              | No         | Enable or disable 3DS verification
| suppressShippingAddress  | No         | Enable or disable shipping options. Typically for digital goods or services, this will be set to true
| unpredictableNumber      | No         | For tokenized transactions, unpredictableNumber is required for cryptogram generation
| validityPeriodMinutes    | No         | The expiration time of a generated cryptogram, in minutes 

#### <a name="webview-configuration">WebView Configuration</a>

In the AndroidManifest.xml file, add the following XML attribute to the activity you want to handle callbacks from the Web activity implementation

```XML
<activity android:name="{Activity used for callback processing}">
  <intent-filter>
    <action android:name="android.intent.action.VIEW"/>
    <category android:name="android.intent.category.DEFAULT"/>
    <data
      android:host="commerce"
      android:path="/"
      android:scheme="{merchant name}"/>
  </intent-filter>
</activity>
```

In the `Activity`, configure the `WebView` similar to the following:

```java
@Override protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  
  WebView.setWebContentsDebuggingEnabled(true);
  
  WebView srciWebView = new WebView(this);
  srciWebView.getSettings().setJavaScriptEnabled(true);
  srciWebView.getSettings().setDomStorageEnabled(true);
  srciWebView.getSettings().setSupportMultipleWindows(true);
  srciWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
  srciWebView.setWebViewClient(new CustomWebViewClient(activity));
  srciWebView.setWebChromeClient(new CustomWebChromeClient(activity));

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    CookieManager.getInstance().setAcceptThirdPartyCookies(srciWebView, true);
  }

  setContentView(srciWebView);
  
  // URL should be loaded after all setup of web clients is done.
  srciWebView.loadUrl(url);
}
```

`srciWebView` requires `WebViewClient` to override URL handling and page updates and `WebChromeClient` to handle the creation of popups. 

#### <a name="webviewclient">WebViewClient</a>

`WebViewClient` must be set in order to return the transaction response to the configured `callbackUrl`

```java
//WebViewClient sample implementation

@Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
  if (!("intent".equals(URI.create(url).getScheme())) {
  	//We only want to override handling of Intent URIs
  	return false;
  }
  
  try {
    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
    String intentApplicationPackage = intent.getPackage();
    String currentApplicationPackage =
        activity.getApplication().getApplicationInfo().packageName;
    Uri intentUri = Uri.parse(url);
    String transactionId = intentUri.getQueryParameter(QUERY_PARAM_MASTERPASS_TRANSACTION_ID);
    String status = intentUri.getQueryParameter(QUERY_PARAM_MASTERPASS_STATUS);

    if (STATUS_CANCEL.equals(status)) {
      //The user has canceled the transaction, close the activity
      activity.finish();
    } else if (intentApplicationPackage != null &&
        intentApplicationPackage.equals(currentApplicationPackage)) {
      //Verify intent belongs to this application
      //Start activity with transaction response parameters

      /* 
      * These parameters are used in this implementation of handling SRC's intent response. The merchant may use any type of
      * implementation they prefer.
      */
      intent.putExtra(COMMERCE_TRANSACTION_ID, transactionId);
      intent.putExtra(COMMERCE_STATUS, status);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

      activity.startActivity(intent);
    } else {
      throw new IllegalStateException(
          "The Intent is not valid for this application: " + url);
    }
  } catch (URISyntaxException e) {
    Log.e(TAG,
          "Unable to parse Intent URI. You must provide a valid Intent URI or checkout will never work.",
          e);

    throw new IllegalStateException(e);
  }
}

//The constants used are:
String QUERY_PARAM_MASTERPASS_TRANSACTION_ID = "oauth_token";
String QUERY_PARAM_MASTERPASS_STATUS = "mpstatus";
String COMMERCE_TRANSACTION_ID = "transactionId";
String COMMERCE_STATUS = "status";
String STATUS_CANCEL = "cancel";
String STATUS_SUCCESS = "success";
```

#### <a name="webchromeclient">WebChromeClient</a>

`WebChromeClient` must be set in order to enable popup windows from the host web view. This step also requires adding another WebView to the ContentView for support of DCF, either MC or external ones.

```java
//WebChromeClient sample implementation

srciWebView.setWebChromeClient(new WebChromeClient() {
  @Override public boolean onCreateWindow(final WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
    final WebView dcfWebView = new WebView(activity);
    dcfWebView.getSettings().setJavaScriptEnabled(true);
    dcfWebView.getSettings().setSupportZoom(true);
    dcfWebView.getSettings().setBuiltInZoomControls(true);
    dcfWebView.getSettings().setSupportMultipleWindows(true);
    dcfWebView.setLayoutParams(
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      CookieManager.getInstance().setAcceptThirdPartyCookies(dcfWebView, true);
    }

    view.addView(dcfWebView);
    
    WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
    transport.setWebView(dcfWebView);
    resultMsg.sendToTarget();
    
    // Set DCF webView logic for WebView
    dcfWebView.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // This should be the same shouldOverrideUrlLoading used in the srci WebView
        return WebCheckoutActivity.this.shouldOverrideUrlLoading(url);
      }

      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        // This should be the same shouldOverrideUrlLoading used in the srci WebView
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
      }
    });
    
    // DCF WebChromeClient should be a simple way to handle all popups using HitTestResult
    dcfWebView.setWebChromeClient(new WebChromeClient() {
      @Override public void onCloseWindow(WebView window) {
        // This should remove the view from the Activity's views when closing
        view.removeView(dcfWebView);
      }

      public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture,
          Message resultMsg) {
        WebView.HitTestResult result = view.getHitTestResult();

        if (result.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
          //If the user has selected an anchor link, open in browser
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getExtra()));
          startActivity(browserIntent);
          return false;
        }
        return true;
      }
    });
    return true;
  }
}
```


