package com.mastercard.mp.switchservices;

import android.util.Log;

/**
 * Network manager class that makes XML requests and returns XML responses from Switch
 */

public class XmlNetworkManager {
  private final NetworkManager networkManager;
  private final XmlParser xmlParser;

  XmlNetworkManager(NetworkManager networkManager, XmlParser xmlParser) {
    this.networkManager = networkManager;
    this.xmlParser = xmlParser;
  }

  <T> void executeRequest(final Class<T> responseType, final HttpRequest request,
      final HttpCallback<T> callback) {
    networkManager.executeRequest(request, new HttpCallback<String>() {
      @Override public void onResponse(String response) {
        try {
          callback.onResponse(xmlParser.deserializeXml(responseType, response));
        } catch (Exception e) {
          Log.e("XmlNetworkManager", e.getLocalizedMessage(), e);
          callback.onError(new ServiceError(ServiceError.ERROR_CODE_BAD_RESPONSE,
              "Bad response from server: " + response));
        }
      }

      @Override public void onError(ServiceError error) {
        callback.onError(error);
      }
    });
  }
}
