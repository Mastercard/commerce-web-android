package com.mastercard.testapp.data.external;

import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApi {

  /**
   * Gets items.
   *
   * @return the items
   */
  public String getItems() {
    String response = null;
    try {
      response = getItemsFromApi();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return response;
  }

  private String getItemsFromApi() throws MalformedURLException {
    return ApiConnection.createGET(MasterpassUrlConstants.URL_API_GET_ITEMS).requestSyncCall();
  }

  /**
   * Gets data confirmation.
   *
   * @param checkoutData the checkout data
   * @param expressCheckoutEnable the express checkout enable
   * @return the data confirmation
   */
  public String getDataConfirmation(HashMap<String, Object> checkoutData,
      boolean expressCheckoutEnable) {
    String response = null;
    try {
      response = getDataConfirmationFromApi(checkoutData, expressCheckoutEnable);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return response;
  }

  private String getDataConfirmationFromApi(HashMap<String, Object> checkoutData,
      boolean expressCheckoutEnable) throws MalformedURLException {
    if (expressCheckoutEnable) {
      return ApiConnection.createPOST(MasterpassUrlConstants.URL_API_POST_PRECHECKOUT, checkoutData,
          expressCheckoutEnable).requestSyncCallWithBody();
    } else {
      return ApiConnection.createPOST(MasterpassUrlConstants.URL_API_POST_CONFIRMATION,
          checkoutData, expressCheckoutEnable).requestSyncCallWithBody();
    }
  }

  /**
   * Gets pairing id.
   *
   * @param checkoutData the checkout data
   * @return the pairing id
   */
  public String getPairingId(HashMap<String, Object> checkoutData) {
    String response = null;
    try {
      response = getPairingIdFromApi(checkoutData);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return response;
  }

  private String getPairingIdFromApi(HashMap<String, Object> checkoutData)
      throws MalformedURLException {
    return ApiConnection.createPOST(MasterpassUrlConstants.URL_API_POST_PAIRING, checkoutData)
        .requestSyncCallWithBody();
  }

  /**
   * Sets complete transaction.
   *
   * @param masterpassConfirmationObject the masterpass confirmation object
   * @return the complete transaction
   */
  public String setCompleteTransaction(MasterpassConfirmationObject masterpassConfirmationObject) {
    String response = null;
    try {
      response = getCompleteTransactionFromApi(masterpassConfirmationObject);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return response;
  }

  private String getCompleteTransactionFromApi(
      MasterpassConfirmationObject masterpassConfirmationObject) throws MalformedURLException {
    return ApiConnection.createPOST(MasterpassUrlConstants.URL_API_POST_COMPLETE,
        masterpassConfirmationObject).requestSyncCallWithBody();
  }

  /**
   * Sets express checkout.
   *
   * @param masterpassConfirmationObject the masterpass confirmation object
   * @return the express checkout
   */
  //EXPRESS CHECKOUT
  public String setExpressCheckout(MasterpassConfirmationObject masterpassConfirmationObject) {
    String response = null;
    try {
      response = getExpressCheckoutFromApi(masterpassConfirmationObject);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return response;
  }

  private String getExpressCheckoutFromApi(
      MasterpassConfirmationObject masterpassConfirmationObject) throws MalformedURLException {
    return ApiConnection.createPOST(MasterpassUrlConstants.URL_API_POST_CHECKOUT,
        masterpassConfirmationObject).requestSyncCallWithBody();
  }
  //EXPRESS CHECKOUT

  /**
   * Do login string.
   *
   * @param username the username
   * @param password the password
   * @return the string
   */
  public String doLogin(String username, String password) {
    String response = null;
    try {
      response = dataLoginFromApi(username, password);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return response;
  }

  private String dataLoginFromApi(String username, String password) throws MalformedURLException {
    //username=jsmith&password=password
    String concatUrl = "username=" + username + "&password=" + password;
    return ApiConnection.createGET(MasterpassUrlConstants.URL_API_GET_LOGIN + concatUrl)
        .requestSyncCallWithStatus();
  }
}
