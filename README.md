### Table of Contents

- [Overview](#overview)
- [Configuration on Merchant Portal](#configuration-on-merchant-portal)
- [Installation](#installation)
- [Configuration](#configuration)
- [Checkout Button](#checkout-button)
	- [Checkout Request](#checkout-request)
- [Transaction Result](#transaction-result)
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

### <a name="Configuration on Merchant Portal">Configuration on Merchant Portal</a>
It is very important to configure these values properly on the portal. If these values are not
configured in proper format, merchant application will not be able to do successful checkout.

`callbackUrl` must be configured with an `Intent` URI. Below is an example format of `callbackUrl` for a sample merchant application named *FancyShop* 
* `Example format of callbackUrl`: intent://commerce/#Intent;scheme=fancyshop;package=com.mastercard.fancyshop;end
* `Channel`: Android


### <a name="installation">Installation</a>

#### Gradle

To include `commerce-web` in your Android project, include the following
in `build.gradle`:

```groovy
dependencies {
  implementation 'com.mastercard.commerce:commerce-web:1.0.4'
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
* `allowedCardTypes`:  The payment networks supported by this merchant (e.g. master, visa, amex, discover, maestro, diner). Currently SRC supports only
three cards: master, visa, amex.

Below is a sample code snippet to initialize Sdk for Sandbox environment :

```java
Locale locale = Locale.US;
String checkoutId = "1d45705100044e14b52e71730e71cc5a";
String checkoutUrl = "https://sandbox.src.mastercard.com/srci/";
Set<CardType> allowedCardTypes = new HashSet<>();
allowedCardTypes.add(CardType.MASTER);
allowedCardTypes.add(CardType.VISA);
allowedCardTypes.add(CardType.AMEX);

CommerceConfig commerceConfig = new CommerceConfig(locale, checkoutId, checkoutUrl, allowedCardTypes);
CommerceWebSdk.getInstance().initialize(commerceConfig, context);
```

### <a name="checkout-button">Checkout Button</a>

Transactions are initiated using the `CheckoutButton` 
object. Below is a method to get the `CheckoutButton` object.

```java
//CommerceWebSdk.java
public CheckoutButton getCheckoutButton(final CheckoutCallback checkoutCallback);
```

Add this to your layout with `LayoutParams.WRAP_CONTENT` for `width` and `height`.

`CheckoutCallback` is an interface in the SDK, designed to receive `CheckoutRequest` from the merchant. CheckoutRequest is required from the merchant in order to initiate the checkout experience. Below is the method of CheckoutCallback interface, where SDK requests for CheckoutRequest object via `CheckoutRequestListener`.

```java
//CheckoutCallback.java
void getCheckoutRequest(CheckoutRequestListener listener);
```
`CheckoutRequestListener` is a Listener interface to set the `CheckoutRequest`. Below is a method to set the CheckoutRequest.

```java
//CheckoutCallback.java
void setRequest(CheckoutRequest request);
```

Here is the sample code snippet to get `CheckoutButton` object, using `getCheckoutRequest` method of CheckoutCallback, by setting the CheckoutRequest via `setRequest` method of CheckoutRequestListener:

```java
CommerceWebSdk.getInstance().getCheckoutButton(new CheckoutCallback() {
        @Override public void getCheckoutRequest(CheckoutRequestListener listener) {
          listener.setRequest(getSrcCheckoutRequest());
        }
      }));
```
The implementation of the checkout can be like this:

```java
private CheckoutRequest getSrcCheckoutRequest() {
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
#### <a name="checkout-request">Checkout Request</a>


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


### <a name="transaction-result">Transaction Result</a>

Once the checkout is complete, the result of transaction is returned to the application via an `Intent` containing the `transactionId`.
In order to receive the result, the application must declare an `intent-filter` in the merchant application.

This intent filter is declared in `AndroidManifest.xml` for the `Activity` receiving the `Intent`. As mentioned in Configuration on Merchant Portal section above,
if the `callbackUrl` is configured like `intent://commerce/#Intent;scheme=fancyshop;package=com.mastercard.fancyshop;end`, then value of `android:host` will be `commerce` and 
value of `android:scheme` will be `fancyshop`. Following is an example: 

```xml
<activity
    android:name=".CheckoutActivity">
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <data
			android:host="commerce"
			android:path="/"
			android:scheme="fancyshop"/>
    </intent-filter>
</activity>
```

In `CheckoutActivity` you would override `onCreate()` to parse the Intent for the transaction data. The activity is started with the `Intent.FLAG_ACTIVITY_CLEAR_TOP` flag.

When the `Intent` is received, the `transactionId` is returned by the `stringExtra` `CommerceWebSdk.COMMERCE_TRANSACTION_ID`

```java
Intent intent = getIntent();
String transactionId = intent.getStringExtra(CommerceWebSdk.COMMERCE_TRANSACTION_ID);

//complete transaction
```

### <a name="migrating-from-masterpass-merchant">Migrating from `masterpass-merchant:2.8.x`</a>

Please update the configurations on merchant portal as specified in `Configuration on Merchant Portal` section.

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
https://sandbox.src.mastercard.com/srci/?checkoutId=asdfghjk123456&
cartId=123ABC&amount=11.22&currency=USD&allowedCardTypes=master%2Camex%2Cvisa
&suppressShippingAddress=false&locale=en_US&channel=mobile
&masterCryptoFormat=UCAF%2CICC 
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

In the `Activity`, we can configure the `WebView` using `WebViewManager`. Below is a snippet code to get webView from `WebViewManager`:
```java
//WebCheckoutActivity.java

@Override protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  
    WebView.setWebContentsDebuggingEnabled(true);
    WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);

    webViewManager = new WebViewManager(this, this);
    RelativeLayout containerLayout = findViewById(R.id.webview_container);
    sRCiWebView = webViewManager.getFirstWebView();
    sRCiWebView.resumeTimers();
    sRCiWebView.loadUrl(url);
    receiver = getReceiver();
    containerLayout.addView(sRCiWebView);
}
```

`WebViewManager` requires `WebViewManagerCallback` and `Context` as parameters to be initialized, as shown below

```java
//WebViewManager.java

WebViewManager(WebViewManagerCallback webViewManagerCallback, Context context) {
    this.webViewManagerCallback = webViewManagerCallback;
    this.context = context;
    this.webViewList = new ArrayList<>();
  }
```

`WebViewManagerCallback` interface has methods as shown below: 
```java
//WebViewManagerCallback.java

public interface WebViewManagerCallback {

  void showProgressDialog();

  void dismissProgressDialog();

  void startActivity(Intent intent);

  void handleIntent(String intentUriString);
}
```

First webView is added from the `Activity` as shown below: 

```java
//WebViewManager.java

 WebView getFirstWebView() {
    return addWebView(true);
  }
```

```java
//WebViewManager.java

private WebView addWebView(final boolean isSRCi) {
    webViewManagerCallback.showProgressDialog();
    final WebView webView = new WebView(context);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setSupportMultipleWindows(true);
    webView.getSettings().setSupportZoom(false);
    webView.getSettings().setBuiltInZoomControls(true);
    webView.getSettings().setDisplayZoomControls(false);
    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    webView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));

    webViewList.add(webView);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
    }

    //This webViewClient will override an intent loading action to startActivity
    webView.setWebViewClient(new WebViewClient() {
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
      }

      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String urlScheme = URI.create(url).getScheme();

        Log.d(TAG, "Navigating to: " + url);

        if (urlScheme.equals(INTENT_SCHEME)) {
          webViewManagerCallback.handleIntent(url);

          return true;
        } else {

          return false;
        }
      }

      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (isSRCi) {
          webViewManagerCallback.dismissProgressDialog();
        }
        super.onPageStarted(view, url, favicon);
      }

      @Override public void onPageFinished(WebView view, String url) {
        if (!isSRCi) {
          webView.setBackgroundColor(Color.WHITE);
          webViewManagerCallback.dismissProgressDialog();
        }
        super.onPageFinished(view, url);
      }

      @Override
      public void onReceivedSslError(final WebView view, final SslErrorHandler handler,
          final SslError error) {
        if (BuildConfig.DEBUG) {
          handler.proceed();
        } else {
          handler.cancel();
        }
      }
    });

    webView.setWebChromeClient(new WebChromeClient() {

      @Override public void onCloseWindow(WebView window) {
        Log.d(TAG, "onCloseWindow webview --------------------");
        if (webViewList.size() > 1) {
          WebView webViewContainer = webViewList.get(webViewList.size() - 2);
          WebView webViewClosed = webViewList.get(webViewList.size() - 1);
          webViewContainer.removeView(webViewClosed);
          webViewClosed.destroy();
          webViewList.remove(webViewClosed);
        } else {
          Log.d(TAG, "Closing window... maybe?");
        }
      }

      @SuppressLint("SetJavaScriptEnabled") @Override
      public boolean onCreateWindow(final WebView view, boolean isDialog, boolean isUserGesture,
          Message resultMsg) {
        WebView.HitTestResult result = view.getHitTestResult();

        if(result.getExtra() != null) {
          if (result.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE && !result.getExtra()
              .endsWith(HASH)) {
            //If the user has selected an anchor link, open in browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getExtra()));
            webViewManagerCallback.startActivity(browserIntent);

            return false;
          }
        }

        WebView webView2 = addWebView(false);

        view.addView(webView2);

        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(webView2);
        resultMsg.sendToTarget();

        if (webViewList.size() > 1) {
          webViewList.get(webViewList.size() - 2).scrollTo(0, 0);
        }

        return true;
      }
    });

    return webView;
  }
```

Few points to be noted from above code snippet:

* `WebChromeClient` must be set in order to enable popup windows from the host web view. This step also requires adding another WebView to the ContentView. 
* N number of webViews can be added using `addWebView(final boolean isSRCi)` method. Value of `isSRCi` is true only for first webView. This value should be false
for subsequent webViews.
* WebView requires `WebViewClient` to override URL handling and page updates. `WebViewClient` must be set in order to return the transaction response to the configured `callbackUrl`.
Below is a code snippet to handle the intent which is being called from `shouldOverrideUrlLoading(WebView view, String url)` method.

```java
//WebCheckoutActivity.java

@Override public void handleIntent(String intentUriString) {
    try {
      Intent intent = Intent.parseUri(intentUriString, Intent.URI_INTENT_SCHEME);
      String intentApplicationPackage = intent.getPackage();
      String currentApplicationPackage = getApplication().getApplicationInfo().packageName;
      Uri intentUri = Uri.parse(intentUriString);
      String transactionId = intentUri.getQueryParameter(QUERY_PARAM_MASTERPASS_TRANSACTION_ID);
      String status = intentUri.getQueryParameter(QUERY_PARAM_MASTERPASS_STATUS);

      if (MasterpassMerchant.getCheckoutCallback() != null) {
        //We're in a v7 flow
        handleMasterpassCallback(status, transactionId);
      } else if (STATUS_CANCEL.equals(status)) {
        finish();
      } else if (intentApplicationPackage != null &&
          intentApplicationPackage.equals(currentApplicationPackage)) {
        intent.putExtra(COMMERCE_TRANSACTION_ID, transactionId);
        intent.putExtra(COMMERCE_STATUS, status);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
      } else {
        throw new IllegalStateException(
            "The Intent is not valid for this application: " + intentUriString);
      }
    } catch (URISyntaxException e) {
      Log.e(TAG,
          "Unable to parse Intent URI. You must provide a valid Intent URI or checkout will never work.",
          e);

      throw new IllegalStateException(e);
    }
  }
```