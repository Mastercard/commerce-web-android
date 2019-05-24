package com.us.masterpass.referenceApp.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.us.masterpass.merchantapp.BuildConfig;
import com.us.masterpass.referenceApp.Pojo.Address;
import com.us.masterpass.referenceApp.Pojo.PaymentCard;
import com.us.masterpass.referenceApp.Pojo.UserData;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class TestData {
  private String loadJSONFromAsset(Context context, String fileName) {
    String json;

    try {
      InputStream ex = context.getAssets().open(fileName);
      int size = ex.available();
      byte[] buffer = new byte[size];
      ex.read(buffer);
      ex.close();
      json = new String(buffer, "UTF-8");
      return json;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public UserData getDuplicateUser(Context context) {
    return getUserData(context, "Sandbox");
  }

  public PaymentCard getFirstCard(Context context) {
    return getCard(context, "firstCard");
  }

  public PaymentCard getSecondCard(Context context) {
    return getCard(context, "secondCard");
  }

  public PaymentCard getMasterCardFPAN(Context context) {
    return getCard(context, "masterCardFPAN");
  }


  public Address getFistAddress(Context context) {
    return getAddress(context, "firstAddress");
  }

  public Address getFistShippingAddress(Context context) {
    return getAddress(context, "firstShippingAddress");
  }

  public Address getAddressWithoutOptionalField(Context context) {
    return getAddress(context, "mandatoryFieldAddress");
  }

  public String getStringsFromJson(Context context, String text) {
    String textStrings = loadJSONFromAsset(context, "en-US.json");
    String retrievedText = null;
    try {
      JSONObject jObject = new JSONObject(textStrings);
      retrievedText = jObject.getString(text);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return retrievedText;
  }

  private PaymentCard getCard(Context context, String tag) {
    PaymentCard paymentCard = new PaymentCard();
    Gson gson = new Gson();
    String testCards = loadJSONFromAsset(context, "testCards.json");
    try {
      JSONObject testCardsObject = new JSONObject(testCards);
      JSONObject cardsObject = testCardsObject.getJSONObject(BuildConfig.FLAVOR);
      String cardInfo = cardsObject.getString(tag);
      paymentCard = gson.fromJson(cardInfo, PaymentCard.class);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return paymentCard;
  }

  private Address getAddress(Context context, String tag) {
    Address address = new Address();
    Gson gson = new Gson();
    String testAddress = loadJSONFromAsset(context, "testAddresses.json");
    try {
      JSONObject testAddressObject = new JSONObject(testAddress);
      String addressInfo = testAddressObject.getString(tag);
      address = gson.fromJson(addressInfo, Address.class);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return address;
  }

  private UserData getUserData(Context context, String tag) {
    UserData userData = new UserData();
    Gson gson = new Gson();
    String testData = loadJSONFromAsset(context, "userData.json");
    try {
      JSONObject testUserObject = new JSONObject(testData);
        //  JSONObject userObject = testUserObject.getJSONObject(BuildConfig.BUILD_TYPE);
      String userInfo = testUserObject.getString(tag);
      userData = gson.fromJson(userInfo, UserData.class);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return userData;
  }
}
