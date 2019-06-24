package com.us.masterpass.merchantapp.data.external;

import android.support.annotation.Nullable;
import android.util.Log;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassConstants;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ApiConnection class used to retrieve data from the cloud.
 * Implements {@link Callable} so when executed asynchronously can
 * return a value.
 */
class ApiConnection implements Callable<String> {

  private static final String TAG = ApiConnection.class.getSimpleName();
  private static final String CONTENT_TYPE_LABEL = "Content-Type";
  private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
  /**
   * The constant JSON.
   */
  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  /**
   * URL to manage the connection with the server
   */

  private URL url;
  private String response;
  private JSONObject responseJSON;

  /**
   * String used on POST requests
   */
  private String bodyString;

  /**
   * Connection with server
   *
   * @param url {@link MasterpassUrlConstants}
   * @throws MalformedURLException valid URL
   */
  private ApiConnection(String url) throws MalformedURLException {
    this.url = new URL(url);
  }

  /**
   * Connection with server
   *
   * @param url {@link MasterpassUrlConstants}
   * @param checkoutData data from checkout flow
   * @throws MalformedURLException valid URL
   */
  private ApiConnection(String url, HashMap<String, Object> checkoutData)
      throws MalformedURLException {
    this.url = new URL(url);
    if (checkoutData.containsKey(MasterpassConstants.PAIRING_WITHOUT_CHECKOUT_FLOW)) {
      this.bodyString = buildBodyStringPairingWihtoutCheckout(checkoutData);
    } else {
      this.bodyString = buildBodyStringConfimation(checkoutData, false);
    }
  }

  /**
   * Connection with server
   *
   * @param url {@link MasterpassUrlConstants}
   * @param expressCheckoutEnable flow with express checkout enable
   * @throws MalformedURLException valid URL
   */
  private ApiConnection(String url, boolean expressCheckoutEnable) throws MalformedURLException {
    this.url = new URL(url);
    this.bodyString = buildBodyStringConfimationPairing();
  }

  /**
   * Connection with server
   *
   * @param url {@link MasterpassUrlConstants}
   * @param masterpassConfirmationObject data from confirmation flow
   * @throws MalformedURLException valid URL
   */
  private ApiConnection(String url, MasterpassConfirmationObject masterpassConfirmationObject)
      throws MalformedURLException {
    this.url = new URL(url);
    if (masterpassConfirmationObject.getPreCheckoutTransactionId() != null
        && masterpassConfirmationObject.getPreCheckoutTransactionId().length() > 0) {
      this.bodyString = buildBodyStringCheckout(masterpassConfirmationObject);
    } else {
      this.bodyString = buildBodyString(masterpassConfirmationObject);
    }
  }

  /**
   * Create GET api connection.
   *
   * @param url {@link MasterpassUrlConstants}
   * @return the api connection
   * @throws MalformedURLException the malformed url exception
   */
  static ApiConnection createGET(String url) throws MalformedURLException {
    return new ApiConnection(url);
  }

  /**
   * Create POST api connection.
   *
   * @param url {@link MasterpassUrlConstants}
   * @param checkoutData data from checkout flow
   * @return the api connection
   * @throws MalformedURLException the malformed url exception
   */
  static ApiConnection createPOST(String url, HashMap<String, Object> checkoutData)
      throws MalformedURLException {
    return new ApiConnection(url, checkoutData);
  }

  /**
   * Create POST api connection.
   *
   * @param url {@link MasterpassUrlConstants}
   * @param checkoutData data from checkout flow
   * @param expressCheckoutEnable is express checkout enable
   * @return the api connection
   * @throws MalformedURLException the malformed url exception
   */
  static ApiConnection createPOST(String url, HashMap<String, Object> checkoutData,
      boolean expressCheckoutEnable) throws MalformedURLException {
    if (expressCheckoutEnable) {
      return new ApiConnection(url, expressCheckoutEnable);
    } else {
      return new ApiConnection(url, checkoutData);
    }
  }

  /**
   * Create POST api connection.
   *
   * @param url the url
   * @param masterpassConfirmationObject the masterpass confirmation object
   * @return the api connection
   * @throws MalformedURLException the malformed url exception
   */
  static ApiConnection createPOST(String url,
      MasterpassConfirmationObject masterpassConfirmationObject) throws MalformedURLException {
    return new ApiConnection(url, masterpassConfirmationObject);
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable String requestSyncCall() {
    connectToApi();
    return response;
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable String requestSyncCallWithStatus() {
    connectToApiWithStatus();
    return responseJSON.toString();
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable String requestSyncCallWithBody() {
    connectToApi(bodyString);
    return responseJSON.toString();
  }

  /**
   * Request sync call with body string.
   *
   * @param expressCheckoutEnable the express checkout enable
   * @return the string
   */
  @Nullable String requestSyncCallWithBody(boolean expressCheckoutEnable) {
    connectToApi(bodyString);
    return responseJSON.toString();
  }

  /**
   * Execute GET
   */
  private void connectToApi() {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder().url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .get()
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Execute GET with status
   */
  private void connectToApiWithStatus() {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder().url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .get()
        .build();

    try {
      //this.response = okHttpClient.newCall(request).execute().body().string();
      Response response = okHttpClient.newCall(request).execute();
      JSONObject jsonObject = new JSONObject();
      Log.d(TAG, "API RESPONSE CODE : " + response.code());
      Log.d(TAG, "API RESPONSE SUCCESSFUL : " + response.isSuccessful());

      try {
        jsonObject.put(MasterpassConstants.RESPONSE_API_CALL, response.body().string());
        jsonObject.put(MasterpassConstants.STATUS_API_CALL, response.isSuccessful());
      } catch (JSONException e) {
        e.printStackTrace();
      }

      this.responseJSON = jsonObject;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Execute POST
   *
   * @param bodyString body used on POST call
   */
  private void connectToApi(String bodyString) {
    RequestBody bodyPost = RequestBody.create(JSON, bodyString);
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder().url(this.url).post(bodyPost).build();

    try {
      Response response = okHttpClient.newCall(request).execute();
      JSONObject jsonObject = new JSONObject();
      try {
        Log.d(TAG, "API RESPONSE CODE : " + response.code());
        Log.d(TAG, "API RESPONSE SUCCESSFUL : " + response.isSuccessful());

        jsonObject.put(MasterpassConstants.RESPONSE_API_CALL, response.body().string());
        jsonObject.put(MasterpassConstants.STATUS_API_CALL, response.isSuccessful());
        this.responseJSON = jsonObject;
      } catch (JSONException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private OkHttpClient createClient() {
    final OkHttpClient okHttpClient = new OkHttpClient();
    okHttpClient.newBuilder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build();
    return okHttpClient;
  }

  @Override public String call() throws Exception {
    return requestSyncCall();
  }

  /**
   * Build body string confimation string.
   *
   * @param checkoutData the checkout data
   * @param expressCheckoutEnable the express checkout enable
   * @return the string
   */
  public String buildBodyStringConfimation(HashMap<String, Object> checkoutData,
      boolean expressCheckoutEnable) {

    JSONObject body = new JSONObject();
    JSONObject bodyPaymentData = new JSONObject();
    JSONObject bodyPairingData = new JSONObject();
    try {

      bodyPaymentData.put("cartId", MasterpassSdkCoordinator.getGeneratedCartId());
      bodyPaymentData.put("transactionId",
          checkoutData.get(MasterpassConstants.API_CALL_TRANSACTION_ID));
      bodyPaymentData.put("paymentDataUrl",
          MasterpassUrlConstants.URL_PAYMENT_DATA_PAIRING + checkoutData.get(
              MasterpassConstants.API_CALL_TRANSACTION_ID));

      body.put("environment", MasterpassConstants.ENVIRONMENT);
      body.put("checkoutIdentifier", MasterpassConstants.CHECKOUT_ID);
      body.put("PaymentDataInput", bodyPaymentData);

      if (checkoutData.get(MasterpassConstants.API_CALL_PAIRING_TRANSACTION_ID)
          != null) {
        bodyPairingData.put("pairingIdUrl",
            MasterpassUrlConstants.URL_PAIRING_ID + checkoutData.get(
                MasterpassConstants.API_CALL_PAIRING_TRANSACTION_ID));
        bodyPairingData.put("pairingTransactionId",
            checkoutData.get(MasterpassConstants.API_CALL_PAIRING_TRANSACTION_ID));
        bodyPairingData.put("userId", MasterpassSdkCoordinator.getUserId());

        body.put("PairingIdInput", bodyPairingData);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return body.toString();
  }

  /**
   * Build body string confimation pairing string.
   *
   * @return the string
   */
  public String buildBodyStringConfimationPairing() {

    JSONObject body = new JSONObject();
    JSONObject bodyPreCheckout = new JSONObject();
    try {
      String pairingId = MasterpassSdkCoordinator.getPairingId();
      bodyPreCheckout.put("pairingId", pairingId);
      bodyPreCheckout.put("preCheckoutUrl", MasterpassUrlConstants.URL_PRE_CHECKOUT + pairingId);

      body.put("environment", MasterpassConstants.ENVIRONMENT);
      body.put("checkoutIdentifier", MasterpassConstants.CHECKOUT_ID);
      body.put("PreCheckoutDataV7Input", bodyPreCheckout);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return body.toString();
  }

  /**
   * Build body string string.
   *
   * @param masterpassConfirmationObject the masterpass confirmation object
   * @return the string
   */
  public String buildBodyString(MasterpassConfirmationObject masterpassConfirmationObject) {

    JSONObject body = new JSONObject();
    JSONObject bodyPostBack = new JSONObject();

    try {
      bodyPostBack.put("transactionId", masterpassConfirmationObject.getCompleteTransactionId());
      bodyPostBack.put("postbackUrl", MasterpassUrlConstants.URL_POSTBACK_TRANSACTION);
      bodyPostBack.put("currency", masterpassConfirmationObject.getCompleteCurrency());
      bodyPostBack.put("paymentCode", "787824");
      bodyPostBack.put("amount", masterpassConfirmationObject.getCompleteAmount());
      bodyPostBack.put("paymentSuccessful", true);

      body.put("environment", MasterpassConstants.ENVIRONMENT);
      body.put("checkoutIdentifier", MasterpassConstants.CHECKOUT_ID);
      body.put("PostbackV7Input", bodyPostBack);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return body.toString();
  }

  /**
   * Build body string checkout string.
   *
   * @param masterpassConfirmationObject the masterpass confirmation object
   * @return the string
   */
  public String buildBodyStringCheckout(MasterpassConfirmationObject masterpassConfirmationObject) {

    JSONObject body = new JSONObject();
    JSONObject bodyExpressCheckoutInput = new JSONObject();
    JSONObject bodyExpressCheckout = new JSONObject();

    try {
      bodyExpressCheckout.put("pairingId", masterpassConfirmationObject.getPairingId());
      bodyExpressCheckout.put("amount", masterpassConfirmationObject.getDoCheckoutAmount());
      bodyExpressCheckout.put("cardId", masterpassConfirmationObject.getDoCheckoutCardId());
      bodyExpressCheckout.put("checkoutId", MasterpassConstants.CHECKOUT_ID);
      bodyExpressCheckout.put("currency", masterpassConfirmationObject.getCompleteCurrency());
      if (!masterpassConfirmationObject.isDoCheckoutSupressShipping()) {
        bodyExpressCheckout.put("digitalGoods", false);
      }
      bodyExpressCheckout.put("preCheckoutTransactionId",
          masterpassConfirmationObject.getPreCheckoutTransactionId());
      bodyExpressCheckout.put("shippingAddressId",
          masterpassConfirmationObject.getDoCheckoutShippingAddressId());

      bodyExpressCheckoutInput.put("expressCheckoutRequest", bodyExpressCheckout);
      bodyExpressCheckoutInput.put("expressCheckoutUrl",
          MasterpassUrlConstants.URL_EXPRESS_CHECKOUT);

      body.put("environment", MasterpassConstants.ENVIRONMENT);
      body.put("checkoutIdentifier", MasterpassConstants.CHECKOUT_ID);
      body.put("ExpressCheckoutV7Input", bodyExpressCheckoutInput);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return body.toString();
  }

  /**
   * Build body string pairing wihtout checkout string.
   *
   * @param checkoutData the checkout data
   * @return the string
   */
  public String buildBodyStringPairingWihtoutCheckout(HashMap<String, Object> checkoutData) {

    JSONObject body = new JSONObject();
    JSONObject bodyPairingData = new JSONObject();
    try {
      bodyPairingData.put("userId", MasterpassSdkCoordinator.getUserId());
      bodyPairingData.put("pairingTransactionId",
          checkoutData.get(MasterpassConstants.API_CALL_PAIRING_TRANSACTION_ID));
      bodyPairingData.put("pairingIdUrl", MasterpassUrlConstants.URL_PAIRING_ID);

      body.put("environment", MasterpassConstants.ENVIRONMENT);
      body.put("checkoutIdentifier", MasterpassConstants.CHECKOUT_ID);
      body.put("PairingIdInput", bodyPairingData);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return body.toString();
  }
}