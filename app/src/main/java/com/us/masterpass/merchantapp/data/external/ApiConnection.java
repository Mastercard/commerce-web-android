package com.us.masterpass.merchantapp.data.external;

import androidx.annotation.Nullable;
import android.util.Log;
import com.us.masterpass.merchantapp.BuildConfig;
import com.us.masterpass.merchantapp.domain.masterpass.CommerceConstants;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
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
    private static final String SANDBOX = "SANDBOX";
    private static final String ENV_SANDBOX = "Sandbox";
    private static final String ENVIRONMENT = "environment";
    private static final String CHECKOUT_IDENTIFIER = "checkoutIdentifier";
    /**
     * The constant JSON.
     */
    public static final MediaType JSON = MediaType.parse(CONTENT_TYPE_VALUE_JSON);

    private URL url;
    private String response;
    private JSONObject responseJSON;
    private String bodyString;

    private ApiConnection(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    private ApiConnection(String url, Map<String, Object> checkoutData) throws MalformedURLException {
        this.url = new URL(url);
        if (checkoutData.containsKey(CommerceConstants.PAIRING_WITHOUT_CHECKOUT_FLOW)) {
            this.bodyString = buildBodyStringPairingWihtoutCheckout(checkoutData);
        } else {
            this.bodyString = buildBodyStringConfimation(checkoutData, false);
        }
    }

    private ApiConnection(String url, MasterpassConfirmationObject masterpassConfirmationObject) throws MalformedURLException {
        this.url = new URL(url);
        if (masterpassConfirmationObject.getPreCheckoutTransactionId() != null &&
                masterpassConfirmationObject.getPreCheckoutTransactionId().length() > 0) {
            this.bodyString = buildBodyStringCheckout(masterpassConfirmationObject);
        } else {
            this.bodyString = buildBodyString(masterpassConfirmationObject);
        }
    }

    /**
     * Create get api connection.
     *
     * @param url the baseUrl
     * @return the api connection
     * @throws MalformedURLException the malformed baseUrl exception
     */
    static ApiConnection createGET(String url) throws MalformedURLException {
        return new ApiConnection(url);
    }

    /**
     * Create post api connection.
     *
     * @param url          the baseUrl
     * @param checkoutData the checkout data
     * @return the api connection
     * @throws MalformedURLException the malformed baseUrl exception
     */
    static ApiConnection createPOST(String url, Map<String, Object> checkoutData) throws MalformedURLException {
        return new ApiConnection(url, checkoutData);
    }

    /**
     * Create post api connection.
     *
     * @param url                          the baseUrl
     * @param masterpassConfirmationObject the masterpass confirmation object
     * @return the api connection
     * @throws MalformedURLException the malformed baseUrl exception
     */
    static ApiConnection createPOST(String url, MasterpassConfirmationObject masterpassConfirmationObject) throws MalformedURLException {
        return new ApiConnection(url, masterpassConfirmationObject);
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response
     */
    @Nullable
    String requestSyncCall() {
        connectToApi();
        return response;
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response
     */
    @Nullable
    String requestSyncCallWithStatus() {
        connectToApiWithStatus();
        return responseJSON.toString();
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response
     */
    @Nullable
    String requestSyncCallWithBody() {
        connectToApi(bodyString);
        return responseJSON.toString();
    }

    /**
     * Request sync call with body string.
     *
     * @param expressCheckoutEnable the express checkout enable
     * @return the string
     */
    @Nullable
    String requestSyncCallWithBody(boolean expressCheckoutEnable) {
        connectToApi(bodyString);
        return responseJSON.toString();
    }

    private void connectToApi() {
        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .get()
                .build();

        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void connectToApiWithStatus() {
        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .get()
                .build();

        try {
            Response responseData = okHttpClient.newCall(request).execute();
            JSONObject jsonObject = new JSONObject();
            Log.d(TAG, "API RESPONSE CODE : " + responseData.code());
            Log.d(TAG, "API RESPONSE SUCCESSFUL : " + responseData.isSuccessful());

            try {
                jsonObject.put(CommerceConstants.RESPONSE_API_CALL, responseData.body().string());
                jsonObject.put(CommerceConstants.STATUS_API_CALL, responseData.isSuccessful());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.responseJSON = jsonObject;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void connectToApi(String bodyString) {
        RequestBody bodyPost = RequestBody.create(JSON, bodyString);
        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url(this.url)
                .post(bodyPost)
                .build();

        try {
            Response responseData = okHttpClient.newCall(request).execute();
            JSONObject jsonObject = new JSONObject();
            try {
                Log.d(TAG, "API RESPONSE CODE : " + responseData.code());
                Log.d(TAG, "API RESPONSE SUCCESSFUL : " + responseData.isSuccessful());

                jsonObject.put(CommerceConstants.RESPONSE_API_CALL, responseData.body().string());
                jsonObject.put(CommerceConstants.STATUS_API_CALL, responseData.isSuccessful());
                this.responseJSON = jsonObject;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
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

    @Override
    public String call() throws Exception {
        return requestSyncCall();
    }

    /**
     * Build body string confimation string.
     *
     * @param checkoutData          the checkout data
     * @param expressCheckoutEnable the express checkout enable
     * @return the string
     */
    public String buildBodyStringConfimation(Map<String, Object> checkoutData, boolean expressCheckoutEnable) {
        String enviroment = "";
        if (CommerceConstants.ENVIROMENT.equalsIgnoreCase(SANDBOX)) {
            enviroment = ENV_SANDBOX;
        }
        JSONObject body = new JSONObject();
        JSONObject bodyPaymentData = new JSONObject();
        JSONObject bodyPairingData = new JSONObject();
        try {
            bodyPaymentData.put("cartId", MasterpassSdkCoordinator.getGeneratedCartId());
            bodyPaymentData.put("transactionId", checkoutData.get(CommerceConstants.API_CALL_TRANSACTION_ID));
            bodyPaymentData.put("paymentDataUrl", checkoutData.get(CommerceConstants.API_CALL_TRANSACTION_ID));

            body.put(ENVIRONMENT, enviroment);
            body.put(CHECKOUT_IDENTIFIER, BuildConfig.CHECKOUT_ID);
            body.put("PaymentDataInput", bodyPaymentData);

            if (checkoutData.get(CommerceConstants.API_CALL_PAIRING_TRANSACTION_ID) != null) {
                bodyPairingData.put("pairingIdUrl", CommerceConstants.API_PAIRING_URL +
                        checkoutData.get(CommerceConstants.API_CALL_PAIRING_TRANSACTION_ID));
                bodyPairingData.put("pairingTransactionId", checkoutData.get(CommerceConstants.API_CALL_PAIRING_TRANSACTION_ID));

                body.put("PairingIdInput", bodyPairingData);
            }
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
        String enviroment = "";
        if (CommerceConstants.ENVIROMENT.equalsIgnoreCase(SANDBOX)) {
            enviroment = ENV_SANDBOX;
        }
        JSONObject body = new JSONObject();
        JSONObject bodyPostBack = new JSONObject();

        try {
            bodyPostBack.put("transactionId", masterpassConfirmationObject.getCompleteTransactionId());
            bodyPostBack.put("currency", masterpassConfirmationObject.getCompleteCurrency());
            bodyPostBack.put("paymentSuccessful", true);
            bodyPostBack.put("amount", masterpassConfirmationObject.getCompleteAmount());

            body.put(ENVIRONMENT, enviroment);
            body.put(CHECKOUT_IDENTIFIER, BuildConfig.CHECKOUT_ID);
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
        String enviroment = "";
        if (CommerceConstants.ENVIROMENT.equalsIgnoreCase(SANDBOX)) {
            enviroment = ENV_SANDBOX;
        }
        JSONObject body = new JSONObject();
        JSONObject bodyExpressCheckoutInput = new JSONObject();
        JSONObject bodyExpressCheckout = new JSONObject();

        try {
            bodyExpressCheckout.put("pairingId", masterpassConfirmationObject.getPairingId());
            bodyExpressCheckout.put("amount", masterpassConfirmationObject.getDoCheckoutAmount());
            bodyExpressCheckout.put("cardId", masterpassConfirmationObject.getDoCheckoutCardId());
            bodyExpressCheckout.put("checkoutId", BuildConfig.CHECKOUT_ID);
            bodyExpressCheckout.put("currency", masterpassConfirmationObject.getCompleteCurrency());
            if (!masterpassConfirmationObject.isDoCheckoutSupressShipping()) {
                bodyExpressCheckout.put("digitalGoods", false);
            }
            bodyExpressCheckout.put("preCheckoutTransactionId", masterpassConfirmationObject.getPreCheckoutTransactionId());
            bodyExpressCheckout.put("shippingAddressId", masterpassConfirmationObject.getDoCheckoutShippingAddressId());

            bodyExpressCheckoutInput.put("expressCheckoutRequest", bodyExpressCheckout);

            body.put(ENVIRONMENT, enviroment);
            body.put(CHECKOUT_IDENTIFIER, BuildConfig.CHECKOUT_ID);
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
    public String buildBodyStringPairingWihtoutCheckout(Map<String, Object> checkoutData) {
        String enviroment = "";
        if (CommerceConstants.ENVIROMENT.equalsIgnoreCase(SANDBOX)) {
            enviroment = ENV_SANDBOX;
        }
        JSONObject body = new JSONObject();
        JSONObject bodyPairingData = new JSONObject();
        try {
            bodyPairingData.put("pairingTransactionId", checkoutData.get(CommerceConstants.API_CALL_PAIRING_TRANSACTION_ID));
            bodyPairingData.put("pairingIdUrl", checkoutData.get(CommerceConstants.API_CALL_PAIRING_TRANSACTION_ID));

            body.put(ENVIRONMENT, enviroment);
            body.put(CHECKOUT_IDENTIFIER, BuildConfig.CHECKOUT_ID);
            body.put("PairingIdInput", bodyPairingData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body.toString();
    }

}
