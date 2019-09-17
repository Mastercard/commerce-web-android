package com.mastercard.mp.switchservices;

import android.util.Log;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.mastercard.mp.switchservices.checkout.ExpressCheckoutRequest;
import com.mastercard.mp.switchservices.checkout.PairingIdResponse;
import com.mastercard.mp.switchservices.checkout.PreCheckoutData;
import com.mastercard.mp.switchservices.paymentData.PaymentData;
import com.mastercard.mp.switchservices.sessionKeySigning.SessionKeySigningRequest;
import com.mastercard.mp.switchservices.sessionKeySigning.SessionKeySigningResponse;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

import static com.mastercard.mp.switchservices.Environments.INT;
import static com.mastercard.mp.switchservices.Environments.ITF;
import static com.mastercard.mp.switchservices.Environments.PRODUCTION;
import static com.mastercard.mp.switchservices.Environments.SANDBOX;
import static com.mastercard.mp.switchservices.Environments.STAGE;
import static com.mastercard.mp.switchservices.Environments.STAGE1;
import static com.mastercard.mp.switchservices.Environments.STAGE2;
import static com.mastercard.mp.switchservices.Environments.STAGE3;

/**
 * Helper class to facilitate API requests to Switch and OpenAPI
 */
public final class MasterpassSwitchServices {
  private static final String TAG = MasterpassSwitchServices.class.getSimpleName();
  private static final Logger logger = Logger.getLogger(TAG);
  private static final String CONTENT_TYPE_XML = "application/xml";
  private static final String CONTENT_TYPE_JSON = "application/json";
  private static final int TIMEOUT_MS = 60000;
  private static final int POOL_SIZE = 2;
  private static final int MAX_POOL_SIZE = 4;
  private static final int TIMEOUT = 30;
  private static final String HTTP_METHOD_GET = "GET";
  private static final String HTTP_METHOD_POST = "POST";
  private final XmlNetworkManager xmlNetworkManager;
  private final NetworkManager networkManager;
  private final String clientId;
  private final XmlParser xmlParser;
  private final JsonSerializer jsonSerializer;

  public MasterpassSwitchServices(String clientId) {
    ThreadPoolExecutor threadPoolExecutor =
        new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(POOL_SIZE));
    Scheduler scheduler = new Scheduler(threadPoolExecutor);
    HttpHandler httpHandler = new HttpHandler(scheduler);
    HttpClient httpClient = HttpClient.createClient(TIMEOUT_MS);

    this.xmlParser = new XmlParser();
    this.jsonSerializer = new JsonSerializer();
    this.networkManager = new NetworkManager(httpClient, httpHandler, jsonSerializer);
    this.xmlNetworkManager = new XmlNetworkManager(networkManager, xmlParser);
    this.clientId = clientId;
  }

  /**
   * To Test this method and obtain a SessionKeySignature for a merchant, you can use the following sample code:
   *
   * val sessionKeyRequest = SessionKeySigningRequest()
   sessionKeyRequest.appId = "com.us.masterpass.merchanta" //replace this with the application package id
   sessionKeyRequest.appSigningPublicKey = "p" //use the penultimate character of the package id
   sessionKeyRequest.appVersion = "p" //use the final character of the package id
   val switchServices = MasterpassSwitchServices(EnvironmentFactory.getClientId(env))
   switchServices.sessionKeySigning(sessionKeyRequest,
   env, LocalOAuthUtil.getKey(mContext!!, EnvironmentFactory.getMerchantP12Certificate(env),
   EnvironmentFactory.getPassword(env), EnvironmentFactory.getKeyAlias(env)), object : HttpCallback<SessionKeySigningResponse> {
   override fun onResponse(
   response: SessionKeySigningResponse?) {
   Log.d(TAG, response?.sessionSignature)
   }

   override fun onError(error: ServiceError?) {
   //don't need to implement this method
   }
   })
   */
  public void sessionKeySigning(SessionKeySigningRequest signingRequest, String environment,
      final PrivateKey privateKey, final HttpCallback<SessionKeySigningResponse> httpCallback) {
    String url = getBaseApiUrl(environment) + "/masterpass/v6/sessionkeysigning";
    try {
      String requestString = xmlParser.generateXml(signingRequest);
      Log.d("SessionKey", url);
      Log.d("SessionKey", "Request: " + requestString);
      Map<String, String> headers =
          generateHeaders(privateKey, requestString, url, clientId, null, HTTP_METHOD_POST,
              CONTENT_TYPE_XML);
      HttpBody body = new HttpBody(requestString, CONTENT_TYPE_XML);
      HttpRequest request = new HttpRequest.Builder().setMethod(HttpRequest.POST)
          .setUrl(url)
          .setHeaders(headers)
          .setBody(body)
          .build();

      xmlNetworkManager.executeRequest(SessionKeySigningResponse.class, request, httpCallback);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (OAuthException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void pairingId(String pairingTransactionId, String userId, String environment,
      PrivateKey privateKey, final HttpCallback<PairingIdResponse> httpCallback) {
    try {
      String url = getBaseApiUrl(environment)
          + "/masterpass/pairingid"
          + "?pairingTransactionId="
          + pairingTransactionId
          + "&userId="
          + userId;
      Log.d("PairingId", url);
      Map<String, String> headers =
          generateHeaders(privateKey, null, url, clientId, null, HTTP_METHOD_GET,
              CONTENT_TYPE_JSON);
      HttpRequest request = new HttpRequest.Builder().setMethod(HttpRequest.GET)
          .setUrl(url)
          .setHeaders(headers)
          .build();

      networkManager.executeRequest(PairingIdResponse.class, request, httpCallback);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
      httpCallback.onError(
          new ServiceError(ServiceError.ERROR_CODE_OAUTH_FAILED, "Private Key is invalid"));
    }
  }

  public void precheckoutData(String pairingId, String environment, PrivateKey privateKey,
      final HttpCallback<PreCheckoutData> httpCallback) {
    try {
      String url = getBaseApiUrl(environment) + "/masterpass/precheckoutdata/" + pairingId;
      Log.d("PreCheckoutData", url);
      Map<String, String> headers =
          generateHeaders(privateKey, null, url, clientId, null, HTTP_METHOD_GET,
              CONTENT_TYPE_JSON);
      HttpRequest request = new HttpRequest.Builder().setMethod(HttpRequest.GET)
          .setUrl(url)
          .setHeaders(headers)
          .build();
      networkManager.executeRequest(PreCheckoutData.class, request, httpCallback);
    } catch (OAuthException e) {
      httpCallback.onError(
          new ServiceError(ServiceError.ERROR_CODE_OAUTH_FAILED, "Private Key is invalid"));
    }
  }

  public void expressCheckout(ExpressCheckoutRequest expressCheckoutRequest, String environment,
      PrivateKey privateKey, final HttpCallback<PaymentData> httpCallback) {
    try {
      String url = getBaseApiUrl(environment) + "/masterpass/expresscheckout";
      Log.d("ExpressCheckout", url);
      String requestString = jsonSerializer.serialize(expressCheckoutRequest);
      Map<String, String> headers =
          generateHeaders(privateKey, requestString, url, clientId, null, HTTP_METHOD_POST,
              CONTENT_TYPE_JSON);
      HttpBody body = new HttpBody(requestString, CONTENT_TYPE_JSON);
      HttpRequest request = new HttpRequest.Builder().setMethod(HttpRequest.POST)
          .setUrl(url)
          .setHeaders(headers)
          .setBody(body)
          .build();
      networkManager.executeRequest(PaymentData.class, request, httpCallback);
    } catch (OAuthException e) {
      httpCallback.onError(
          new ServiceError(ServiceError.ERROR_CODE_OAUTH_FAILED, "Private Key is invalid"));
    } catch (JSONException e) {
      httpCallback.onError(new ServiceError(ServiceError.ERROR_CODE_BAD_REQUEST, "Bad request"));
    } catch (UnsupportedEncodingException e) {
      Log.e("ExpressCheckout", e.getLocalizedMessage(), e);
    }
  }

  public void paymentData(String paymentId, String checkoutId, String cartId, String environment, PrivateKey privateKey, final HttpCallback<PaymentData> httpCallback) {
    try {
      String url = this.getBaseApiUrl(environment) + "/masterpass/paymentdata/" + paymentId + "?checkoutId=" + checkoutId + "&cartId=" + cartId;
      Log.d("URLTAG", "Url ------------" + url);
      Map<String, String> headers = this.generateHeaders(privateKey, (String)null, url, this.clientId, (String)null, "GET", "application/json");
      HttpRequest request = (new HttpRequest.Builder()).setMethod("GET").setUrl(url).setHeaders(headers).build();
      this.networkManager.executeRequest(PaymentData.class, request, new HttpCallback<PaymentData>() {
        public void onResponse(PaymentData response) {
          httpCallback.onResponse(response);
        }

        public void onError(ServiceError error) {
          httpCallback.onError(error);
        }
      });
    } catch (Exception var10) {
      logger.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      httpCallback.onError(new ServiceError(116, "Private Key is invalid"));
    }

  }

  private String extractField(String fieldName, String encodedString) {
    String[] fields = encodedString.split("&");
    String oAuthToken = null;

    for (String field : fields) {
      if (field.contains(fieldName + "=")) {
        oAuthToken = field.split("=")[1];
        break;
      }
    }

    return oAuthToken;
  }

  private Map<String, String> generateHeaders(PrivateKey privateKey, String request, String url,
      String clientId, String serviceName, String httpMethod, String contentType)
      throws OAuthException {
    Map<String, String> headers = new HashMap<>();
    String oAuthHeader =
        MasterpassOAuthUtil.generateOAuthHeader(privateKey, request, url, clientId, serviceName,
            httpMethod);
    headers.put("Content-Type", contentType);
    headers.put("Authorization", oAuthHeader);

    return headers;
  }

  private String getBaseApiUrl(@Environments.Environment String environment) {
    String baseUrl;

    switch (environment) {
      case STAGE:
        baseUrl = "https://stage.api.mastercard.com";
        break;
      case STAGE1:
        baseUrl = "https://stage.api.mastercard.com/stage1";
        break;
      case STAGE2:
        baseUrl = "https://stage.api.mastercard.com/stage2";
        break;
      case STAGE3:
        baseUrl = "https://stage.api.mastercard.com/stage3";
        break;
      case SANDBOX:
        baseUrl = "https://sandbox.api.mastercard.com";
        break;
      case ITF:
        baseUrl = "https://stage.api.mastercard.com/itf";
        break;
      case INT:
        baseUrl = "https://stage.api.mastercard.com/int";
        break;
      case PRODUCTION:
      default:
        baseUrl = "https://api.mastercard.com";
        break;
    }

    return baseUrl;
  }
}
