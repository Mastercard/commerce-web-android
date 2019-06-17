package com.us.masterpass.merchantapp.data.external;

import com.us.masterpass.merchantapp.data.JsonParser;
import com.us.masterpass.merchantapp.domain.masterpass.CommerceConstants;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.model.MasterpassPreCheckoutCardObject;
import com.us.masterpass.merchantapp.domain.model.MasterpassPreCheckoutShippingObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class MasterpassExternalDataSource implements MasterpassDataSource {
  private static MasterpassExternalDataSource INSTANCE;
  private static final String PAYMENT_DATA_OUTPUT = "PaymentDataOutput";
  private static final String BRAND_NAME = "brandName";
  private static final String ACCOUNT_NUMBER = "accountNumber";
  private static final String CARD_HOLDER_NAME = "cardHolderName";
  private static final String SHIPPING_ADDRESS = "shippingAddress";
  private static final String LINE1 = "line1";
  private static final String SUB_DIVISION = "subdivision";
  private static final String POSTAL_CODE = "postalCode";
  private static final String PRE_CHECKOUT_DATA_OP = "PreCheckoutDataOutput";

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static MasterpassExternalDataSource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new MasterpassExternalDataSource();
    }
    return INSTANCE;
  }

  // Prevent direct instantiation.
  private MasterpassExternalDataSource() {
  }

  @Override public void getDataConfirmation(Map<String, Object> checkoutData,
      LoadDataConfirmationCallback callback) {
    final RestApi restApi = new RestApi();
    String response = restApi.getDataConfirmation(checkoutData);
    if (response == null) {
      callback.onDataNotAvailable();
    } else {
      try {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.getBoolean(CommerceConstants.STATUS_API_CALL)) {
          JSONObject paymentData =
              new JSONObject(jsonObject.getString(CommerceConstants.RESPONSE_API_CALL));

          if (checkoutData != null) {
            callback.onDataConfirmation(transformResponse(paymentData,
                checkoutData.get(CommerceConstants.API_CALL_TRANSACTION_ID).toString()));
          } else {
            callback.onDataConfirmation(transformResponse(paymentData, ""));
          }
        } else {
          callback.onDataNotAvailable();
        }
      } catch (JSONException e) {
        e.printStackTrace();
        callback.onDataNotAvailable();
      }
    }
  }

  @Override public void sendConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
      LoadDataConfirmationCallback callback) {
    final RestApi restApi = new RestApi();
    String response = restApi.setCompleteTransaction(masterpassConfirmationObject);
    if (response == null) {
      callback.onDataNotAvailable();
    } else {
      try {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.getBoolean(CommerceConstants.STATUS_API_CALL)) {
          callback.onDataConfirmation(masterpassConfirmationObject);
        } else {
          callback.onDataNotAvailable();
        }
      } catch (JSONException e) {
        e.printStackTrace();
        callback.onDataNotAvailable();
      }
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
      if (paymentData.has(PAYMENT_DATA_OUTPUT)) {
        masterpassConfirmation.setCardBrandId(JsonParser.getString(
            paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject("card"), "brandId", ""));
        masterpassConfirmation.setCardBrandName(JsonParser.getString(
            paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject("card"), BRAND_NAME, ""));
        masterpassConfirmation.setCardAccountNumber(JsonParser.getString(
            paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject("card"), ACCOUNT_NUMBER,
            ""));
        masterpassConfirmation.setCardAccountNumberHidden(JsonParser.getString(
            paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject("card"), ACCOUNT_NUMBER,
            ""));
        masterpassConfirmation.setCardHolderName(JsonParser.getString(
            paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject("card"), CARD_HOLDER_NAME,
            ""));

        if (JsonParser.getJSONObject(paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).toString(),
            SHIPPING_ADDRESS).has(LINE1)) {
          String line1 = JsonParser.getString(
              paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject(SHIPPING_ADDRESS), LINE1,
              "");

          String line2 = JsonParser.getString(
              paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject(SHIPPING_ADDRESS),
              "city", "");

          String line3a = JsonParser.getString(
              paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject(SHIPPING_ADDRESS),
              SUB_DIVISION, "");

          String line3b = JsonParser.getString(
              paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject(SHIPPING_ADDRESS),
              POSTAL_CODE, "");

          masterpassConfirmation.setShippingLine1(
              line1 + "\n" + line2 + "\n" + line3a + " " + line3b);

          masterpassConfirmation.setShippingCity(JsonParser.getString(
              paymentData.getJSONObject(PAYMENT_DATA_OUTPUT).getJSONObject(SHIPPING_ADDRESS),
              "city", ""));
        }
      } else if (paymentData.has(PRE_CHECKOUT_DATA_OP)) {
        masterpassConfirmation.setPreCheckoutTransactionId(
            JsonParser.getString(paymentData.getJSONObject(PRE_CHECKOUT_DATA_OP),
                "precheckoutTransactionId", ""));
        List<MasterpassPreCheckoutCardObject> listCard = new ArrayList<>();
        JSONArray cards = paymentData.getJSONObject(PRE_CHECKOUT_DATA_OP)
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
        masterpassConfirmation.setListPreCheckoutCard(listCard);

        List<MasterpassPreCheckoutShippingObject> listShipping = new ArrayList<>();
        JSONArray shipping = paymentData.getJSONObject(PRE_CHECKOUT_DATA_OP)
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
          mpcso.setPreSubdivision(JsonParser.getString(jsonObject, SUB_DIVISION, ""));
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
      e.printStackTrace();
    }

    masterpassConfirmation.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());
    masterpassConfirmation.setCompleteTransactionId(transactionId);

    return masterpassConfirmation;
  }

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
          JsonParser.getString(paymentData.getJSONObject(SHIPPING_ADDRESS), SUB_DIVISION, "");

      String line3b =
          JsonParser.getString(paymentData.getJSONObject(SHIPPING_ADDRESS), POSTAL_CODE, "");

      masterpassConfirmation.setShippingLine1(line1 + "\n" + line2 + "\n" + line3a + " " + line3b);

      masterpassConfirmation.setShippingCity(
          JsonParser.getString(paymentData.getJSONObject(SHIPPING_ADDRESS), "city", ""));
      masterpassConfirmation.setCompleteTransactionId(transactionId);
      masterpassConfirmation.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    masterpassConfirmation.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());

    return masterpassConfirmation;
  }
}