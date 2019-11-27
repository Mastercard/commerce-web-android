package com.mastercard.testapp.data.external;

import android.util.Log;
import com.mastercard.mp.switchservices.HttpCallback;
import com.mastercard.mp.switchservices.MasterpassSwitchServices;
import com.mastercard.mp.switchservices.ServiceError;
import com.mastercard.mp.switchservices.checkout.ExpressCheckoutRequest;
import com.mastercard.mp.switchservices.paymentData.PaymentData;
import com.mastercard.testapp.data.JsonParser;
import com.mastercard.testapp.data.pojo.EnvironmentConfiguration;
import com.mastercard.testapp.domain.masterpass.MasterpassConstants;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.model.LoginObject;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.domain.model.MasterpassPreCheckoutCardObject;
import com.mastercard.testapp.domain.model.MasterpassPreCheckoutShippingObject;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Manage request to server
 * <p>
 * Created by Sebastian Farias on 08-10-17.
 */
public class MasterpassExternalDataSource implements MasterpassDataSource {
  private static final String PAIRING_ID_OUTPUT = "PairingIdOutput";
  private static final String PAIRING_ID = "pairingId";
  private static final String BRAND_NAME = "brandName";
  private static final String ACCOUNT_NUMBER = "accountNumber";
  private static final String CARD_HOLDER_NAME = "cardHolderName";
  private static final String SHIPPING_ADDRESS = "shippingAddress";
  private static final String LINE1 = "line1";
  private static final String SUBDIVISION = "subdivision";
  private static final String POSTAL_CODE = "postalCode";
  private static MasterpassExternalDataSource instance;
  private String mTag = getClass().getCanonicalName();

  /**
   * Only created to not allow direct instantiation
   */
  private MasterpassExternalDataSource() {
  }

  /**
   * Get instance of class.
   *
   * @return class instance
   */
  public static MasterpassExternalDataSource getInstance() {
    if (instance == null) {
      instance = new MasterpassExternalDataSource();
    }
    return instance;
  }

  /**
   * Get data confirmation on checkout
   *
   * @param checkoutData checkout data HashMap
   * @param expressCheckoutEnable is express checkout enable
   * @param callback callback {@link LoadDataConfirmationCallback}
   */
  @Override public void getDataConfirmation(Map<String, Object> checkoutData,
      boolean expressCheckoutEnable, LoadDataConfirmationCallback callback) {
    final RestApi restApi = new RestApi();
    String response =
        restApi.getDataConfirmation((HashMap<String, Object>) checkoutData, expressCheckoutEnable);
    if (response == null) {
      callback.onDataNotAvailable();
    } else {
      confirmDataCheckout(response, checkoutData, callback, expressCheckoutEnable);
    }
  }

  /**
   * Confirms data on checkout
   */
  private void confirmDataCheckout(String responseObject, Map<String, Object> checkoutData,
      LoadDataConfirmationCallback callback, boolean expressCheckoutEnable) {
    try {
      JSONObject jsonObject = new JSONObject(responseObject);
      if (jsonObject.getBoolean(MasterpassConstants.STATUS_API_CALL)) {
        JSONObject paymentData =
            new JSONObject(jsonObject.getString(MasterpassConstants.RESPONSE_API_CALL));

        if (checkoutData != null) {
          callback.onDataConfirmation(transformResponse(paymentData,
              checkoutData.get(MasterpassConstants.API_CALL_TRANSACTION_ID).toString()),
              expressCheckoutEnable);
        } else {
          callback.onDataConfirmation(transformResponse(paymentData, ""), expressCheckoutEnable);
        }
      } else {
        callback.onDataNotAvailable();
      }
    } catch (JSONException e) {
      Log.e(mTag, e.getLocalizedMessage());
      e.printStackTrace();
      callback.onDataNotAvailable();
    }
  }

  /**
   * Send confirmation on checkout
   *
   * @param masterpassConfirmationObject masterpass object {@link MasterpassConfirmationObject}
   * @param callback callback {@link LoadDataConfirmationCallback}
   */
  @Override public void sendConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
      LoadDataConfirmationCallback callback) {
    final RestApi restApi = new RestApi();
    String response = restApi.setCompleteTransaction(masterpassConfirmationObject);
    if (response == null) {
      callback.onDataNotAvailable();
    } else {
      try {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.getBoolean(MasterpassConstants.STATUS_API_CALL)) {
          callback.onDataConfirmation(masterpassConfirmationObject, false);
        } else {
          callback.onDataNotAvailable();
        }
      } catch (JSONException e) {
        Log.e(mTag, e.getMessage());
        e.printStackTrace();
        callback.onDataNotAvailable();
      }
    }
  }

  /**
   * Confirm express checkout method
   *
   * @param expressCheckoutRequest masterpass object {@link MasterpassConfirmationObject}
   * @param callback callback {@link LoadDataConfirmationCallback}
   */
  @Override public void expressCheckout(ExpressCheckoutRequest expressCheckoutRequest,
      final LoadDataConfirmationCallback callback, PrivateKey privateKey) {
    EnvironmentConfiguration envConfig = EnvironmentSettings.getCurrentEnvironmentConfiguration();
    MasterpassSwitchServices switchServices = new MasterpassSwitchServices(envConfig.getClientId());
    switchServices.expressCheckout(expressCheckoutRequest, envConfig.getName().toUpperCase(),
        privateKey, new HttpCallback<PaymentData>() {
          @Override public void onResponse(PaymentData response) {
            if (response == null) {
              callback.onDataNotAvailable();
            } else {
              callback.onDataConfirmation(getPaymentCardData(response), false);
            }
          }

          @Override public void onError(ServiceError error) {
            Log.d("TAG", error.toString());
            callback.onDataNotAvailable();
          }
        });
  }

  /**
   * Make login if the user is not logged
   *
   * @param username username for login
   * @param password password for login
   * @param callback callback {@link LoadDataLoginCallback}
   */
  @Override public void doLogin(String username, String password, LoadDataLoginCallback callback) {
    final RestApi restApi = new RestApi();
    String response =
        " {\"response\":\"{\\\"userId\\\":121,\\\"username\\\":\\\"jsmith\\\",\\\"password\\\":\\\"password\\\",\\\"firstName\\\":\\\"Jack\\\",\\\"lastName\\\":\\\"Smith\\\"}\",\"status\":true}";
    if (response == null) {
      callback.onDataNotAvailable();
    } else {
      try {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.getBoolean(MasterpassConstants.STATUS_API_CALL)) {
          JSONObject loginData =
              new JSONObject(jsonObject.getString(MasterpassConstants.RESPONSE_API_CALL));
          callback.onDataLogin(transformResponse(loginData));
        } else {
          callback.onDataNotAvailable();
        }
      } catch (JSONException e) {
        Log.e(mTag, e.getLocalizedMessage());
        e.printStackTrace();
        callback.onDataNotAvailable();
      }
    }
  }

  /**
   * Retrieve pairing id to checkout flow
   *
   * @param checkoutData checkout data HashMap
   * @param callback callback {@link PairingIdCallback}
   */
  @Override public void getPairingId(Map<String, Object> checkoutData, PairingIdCallback callback) {
    final RestApi restApi = new RestApi();
    checkoutData.put(MasterpassConstants.PAIRING_WITHOUT_CHECKOUT_FLOW, true);
    String response = restApi.getPairingId((HashMap<String, Object>) checkoutData);
    if (response == null) {
      callback.onPairingError();
    } else {
      savePairingId(response, callback);
    }
  }

  private void savePairingId(String response, PairingIdCallback callback) {
    try {
      JSONObject jsonObject = new JSONObject(response);
      if (jsonObject.getBoolean(MasterpassConstants.STATUS_API_CALL)) {
        JSONObject paymentData =
            new JSONObject(jsonObject.getString(MasterpassConstants.RESPONSE_API_CALL));
        if (paymentData.has(PAIRING_ID_OUTPUT) && paymentData.getJSONObject(PAIRING_ID_OUTPUT)
            .has(PAIRING_ID)) {
          MasterpassSdkCoordinator.savePairingId(
              JsonParser.getString(paymentData.getJSONObject(PAIRING_ID_OUTPUT), PAIRING_ID, ""));
        }
        callback.onPairing();
      } else {
        callback.onPairingError();
      }
    } catch (JSONException e) {
      Log.e(mTag, e.getLocalizedMessage());
      e.printStackTrace();
      callback.onPairingError();
    }
  }

  /**
   * Get response from post and return as {@link MasterpassConfirmationObject}
   *
   * @param paymentData response from API
   * @param transactionId identifier of masterpass transaction
   * @return {@link MasterpassConfirmationObject}
   */
  private MasterpassConfirmationObject transformResponse(JSONObject paymentData,
      String transactionId) {
    MasterpassConfirmationObject masterpassConfirmation = new MasterpassConfirmationObject();
    try {
      String mPreCheckoutDataOutput = "PreCheckoutDataOutput";
      String mPaymentDataOutput = "PaymentDataOutput";
      if (paymentData.has(mPaymentDataOutput)) {
        masterpassConfirmation.setCardBrandId(JsonParser.getString(
            paymentData.getJSONObject(mPaymentDataOutput).getJSONObject("card"), "brandId", ""));
        masterpassConfirmation.setCardBrandName(JsonParser.getString(
            paymentData.getJSONObject(mPaymentDataOutput).getJSONObject("card"), BRAND_NAME, ""));
        masterpassConfirmation.setCardAccountNumber(JsonParser.getString(
            paymentData.getJSONObject(mPaymentDataOutput).getJSONObject("card"), ACCOUNT_NUMBER,
            ""));
        masterpassConfirmation.setCardAccountNumberHidden(JsonParser.getString(
            paymentData.getJSONObject(mPaymentDataOutput).getJSONObject("card"), ACCOUNT_NUMBER,
            ""));
        masterpassConfirmation.setCardHolderName(JsonParser.getString(
            paymentData.getJSONObject(mPaymentDataOutput).getJSONObject("card"), CARD_HOLDER_NAME,
            ""));
        if (paymentData.getJSONObject(mPaymentDataOutput).has(SHIPPING_ADDRESS)
            && JsonParser.getJSONObject(paymentData.getJSONObject("PaymentDataOutput").toString(),
            SHIPPING_ADDRESS).has(LINE1)) {
          String line1 = JsonParser.getString(
              paymentData.getJSONObject(mPaymentDataOutput).getJSONObject(SHIPPING_ADDRESS), LINE1,
              "");

          String line2 = JsonParser.getString(
              paymentData.getJSONObject(mPaymentDataOutput).getJSONObject(SHIPPING_ADDRESS), "city",
              "");

          String line3a = JsonParser.getString(
              paymentData.getJSONObject(mPaymentDataOutput).getJSONObject(SHIPPING_ADDRESS),
              SUBDIVISION, "");

          String line3b = JsonParser.getString(
              paymentData.getJSONObject(mPaymentDataOutput).getJSONObject(SHIPPING_ADDRESS),
              POSTAL_CODE, "");

          masterpassConfirmation.setShippingLine1(
              line1 + "\n" + line2 + "\n" + line3a + " " + line3b);
          masterpassConfirmation.setShippingCity(JsonParser.getString(
              paymentData.getJSONObject(mPaymentDataOutput).getJSONObject(SHIPPING_ADDRESS), "city",
              ""));
        }

        masterpassConfirmation.setExpressCheckoutEnable(false);

        if (paymentData.has(PAIRING_ID_OUTPUT) && paymentData.getJSONObject(PAIRING_ID_OUTPUT)
            .has(PAIRING_ID)) {
          //MasterpassSdkCoordinator.savePairingId(
          //    JsonParser.getString(paymentData.getJSONObject(PAIRING_ID_OUTPUT), PAIRING_ID, ""));
        }
      } else if (paymentData.has(mPreCheckoutDataOutput)) {
        masterpassConfirmation.setExpressCheckoutEnable(true);
        masterpassConfirmation.setPairingId(
            JsonParser.getString(paymentData.getJSONObject(mPreCheckoutDataOutput), PAIRING_ID,
                ""));
        masterpassConfirmation.setPreCheckoutTransactionId(
            JsonParser.getString(paymentData.getJSONObject(mPreCheckoutDataOutput),
                "precheckoutTransactionId", ""));
        MasterpassSdkCoordinator.savePairingId(
            JsonParser.getString(paymentData.getJSONObject(mPreCheckoutDataOutput), PAIRING_ID,
                ""));
        List<MasterpassPreCheckoutCardObject> listCard = new ArrayList<>();
        JSONArray cards = paymentData.getJSONObject(mPreCheckoutDataOutput)
            .getJSONObject("preCheckoutData")
            .getJSONArray("cards");
        for (int i = 0; i < cards.length(); i++) {
          MasterpassPreCheckoutCardObject mpcco = new MasterpassPreCheckoutCardObject();
          JSONObject jsonObject = cards.getJSONObject(i);

          mpcco.setPreBrandName(JsonParser.getString(jsonObject, BRAND_NAME, ""));
          mpcco.setPreCardHolderName(JsonParser.getString(jsonObject, CARD_HOLDER_NAME, ""));
          mpcco.setPreCardId(JsonParser.getString(jsonObject, "cardId", ""));
          mpcco.setPreDefault(JsonParser.getString(jsonObject, "default", ""));
          mpcco.setPreExpiryMonth(JsonParser.getString(jsonObject, "expiryMonth", ""));
          mpcco.setPreExpiryYear(JsonParser.getString(jsonObject, "expiryYear", ""));
          mpcco.setPreLastFour(JsonParser.getString(jsonObject, "lastFour", ""));

          listCard.add(mpcco);
        }
        masterpassConfirmation.setListMasterpassPreCheckoutCardObject(listCard);

        List<MasterpassPreCheckoutShippingObject> listShipping = new ArrayList<>();
        JSONArray shipping = paymentData.getJSONObject(mPreCheckoutDataOutput)
            .getJSONObject("preCheckoutData")
            .getJSONArray("shippingAddresses");
        for (int i = 0; i < cards.length(); i++) {
          MasterpassPreCheckoutShippingObject mpcso = new MasterpassPreCheckoutShippingObject();
          JSONObject jsonObject = shipping.getJSONObject(i);

          mpcso.setPreRecipientName(
              JsonParser.getString(JsonParser.getJSONObject(jsonObject.toString(), "recipientInfo"),
                  "recipientName", ""));
          mpcso.setPreRecipientPhone(
              JsonParser.getString(JsonParser.getJSONObject(jsonObject.toString(), "recipientInfo"),
                  "recipientPhone", ""));
          mpcso.setPreAddressId(JsonParser.getString(jsonObject, "addressId", ""));
          mpcso.setPreCity(JsonParser.getString(jsonObject, "city", ""));
          mpcso.setPreCountry(JsonParser.getString(jsonObject, "country", ""));
          mpcso.setPreSubdivision(JsonParser.getString(jsonObject, SUBDIVISION, ""));
          mpcso.setPreDefault(JsonParser.getString(jsonObject, "default", ""));
          mpcso.setPreLine1(JsonParser.getString(jsonObject, LINE1, ""));
          mpcso.setPreLine2(JsonParser.getString(jsonObject, "line2", ""));
          mpcso.setPreLine3(JsonParser.getString(jsonObject, "line3", ""));
          mpcso.setPreLine4(JsonParser.getString(jsonObject, "line4", ""));
          mpcso.setPreLine5(JsonParser.getString(jsonObject, "line5", ""));
          mpcso.setPrePostalCode(JsonParser.getString(jsonObject, POSTAL_CODE, ""));

          listShipping.add(mpcso);
        }
        masterpassConfirmation.setListPreCheckoutShipping(listShipping);
      }
    } catch (JSONException e) {
      Log.e(mTag, e.getLocalizedMessage());
      e.printStackTrace();
    }
    masterpassConfirmation.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());
    masterpassConfirmation.setCompleteTransactionId(transactionId);

    return masterpassConfirmation;
  }

  /**
   * Get response from post and return as {@link MasterpassConfirmationObject}
   *
   * @param paymentDataResponse response from API
   * @param transactionId string with transaction id
   * @return {@link MasterpassConfirmationObject}
   */
  private MasterpassConfirmationObject transformResponseCheckout(JSONObject paymentDataResponse,
      String transactionId) {
    MasterpassConfirmationObject masterpassConfirmation = new MasterpassConfirmationObject();
    try {
      JSONObject paymentData =
          paymentDataResponse.getJSONObject("ExpressCheckoutOutput").getJSONObject("paymentData");

      masterpassConfirmation.setCardBrandId(
          JsonParser.getString(paymentData.getJSONObject("card"), "brandId", ""));
      masterpassConfirmation.setCardBrandName(
          JsonParser.getString(paymentData.getJSONObject("card"), BRAND_NAME, ""));
      masterpassConfirmation.setCardAccountNumber(
          JsonParser.getString(paymentData.getJSONObject("card"), ACCOUNT_NUMBER, ""));
      masterpassConfirmation.setCardAccountNumberHidden(
          JsonParser.getString(paymentData.getJSONObject("card"), ACCOUNT_NUMBER, ""));
      masterpassConfirmation.setCardHolderName(
          JsonParser.getString(paymentData.getJSONObject("card"), CARD_HOLDER_NAME, ""));

      String line1 = JsonParser.getString(paymentData.getJSONObject(SHIPPING_ADDRESS), LINE1, "");

      String line2 = JsonParser.getString(paymentData.getJSONObject(SHIPPING_ADDRESS), "city", "");

      String line3a =
          JsonParser.getString(paymentData.getJSONObject(SHIPPING_ADDRESS), SUBDIVISION, "");

      String line3b =
          JsonParser.getString(paymentData.getJSONObject(SHIPPING_ADDRESS), POSTAL_CODE, "");

      masterpassConfirmation.setShippingLine1(line1 + "\n" + line2 + "\n" + line3a + " " + line3b);

      masterpassConfirmation.setShippingCity(
          JsonParser.getString(paymentData.getJSONObject(SHIPPING_ADDRESS), "city", ""));
      masterpassConfirmation.setExpressCheckoutEnable(false);
      masterpassConfirmation.setCompleteTransactionId(transactionId);
      masterpassConfirmation.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());
      if (paymentData.has(PAIRING_ID)) {
        MasterpassSdkCoordinator.savePairingId(JsonParser.getString(paymentData, PAIRING_ID, ""));
      }
    } catch (JSONException e) {
      Log.e(mTag, e.getLocalizedMessage());
      e.printStackTrace();
    }
    masterpassConfirmation.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());

    return masterpassConfirmation;
  }

  /**
   * Save user id from response on login action
   *
   * @param loginData response from API
   * @return {@link LoginObject}
   */
  private LoginObject transformResponse(JSONObject loginData) {
    LoginObject login = new LoginObject();
    try {
      login.setUserId(loginData.getString("userId"));
      login.setUsername(loginData.getString("username"));
      login.setFirstName(loginData.getString("firstName"));
      login.setLastName(loginData.getString("lastName"));
    } catch (JSONException e) {
      Log.e(mTag, e.getLocalizedMessage());
      e.printStackTrace();
    }

    return login;
  }

  public MasterpassConfirmationObject getPaymentCardData(PaymentData paymentData) {
    final MasterpassConfirmationObject masterpassConfirmationObject =
        new MasterpassConfirmationObject();
    masterpassConfirmationObject.setCardAccountNumber(paymentData.getCard().getAccountNumber());
    masterpassConfirmationObject.setCardAccountNumberHidden(
        getCardAccountNumberHidden(paymentData.getCard().getAccountNumber()));
    masterpassConfirmationObject.setCardBrandId(paymentData.getCard().getBrandId());
    masterpassConfirmationObject.setCardBrandName(paymentData.getCard().getBrandName());
    masterpassConfirmationObject.setCardHolderName(paymentData.getCard().getCardHolderName());
    masterpassConfirmationObject.setShippingLine1(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getLine1()
            : null));
    masterpassConfirmationObject.setShippingLine2(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getLine2()
            : null));
    masterpassConfirmationObject.setShippingCity(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getCity()
            : null));
    masterpassConfirmationObject.setPostalCode(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getPostalCode()
            : null));
    masterpassConfirmationObject.setShippingSubDivision(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getSubdivision()
            : null));
    masterpassConfirmationObject.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());
    if (paymentData.getPairingId() != null) {
      MasterpassSdkCoordinator.savePairingId(paymentData.getPairingId());
    }
    return masterpassConfirmationObject;
  }

  private String getCardAccountNumberHidden(String cardAccountNumber) {
    StringBuffer buf = new StringBuffer(cardAccountNumber);
    if (cardAccountNumber.length() > 4) {
      int start = 0;
      int end = cardAccountNumber.length() - 4;
      buf.replace(start, end, "**** **** **** ");
    }
    return buf.toString();
  }

  private String validateEmptyString(String value) {
    String mValue;
    mValue = value != null ? value : "";
    return mValue;
  }
}