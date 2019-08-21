package com.mastercard.mp.switchservices;

import android.util.Log;
import org.json.JSONException;

/**
 * Class to perform network requests with a given {@link HttpRequest} and return a response of an
 * expected type
 */

class NetworkManager {
  private final JsonSerializer jsonSerializer;
  private final HttpClient httpClient;
  private final HttpHandler httpHandler;

  NetworkManager(HttpClient httpClient, HttpHandler httpHandler,
      JsonSerializer jsonSerializer) {
    this.httpClient = httpClient;
    this.httpHandler = httpHandler;
    this.jsonSerializer = jsonSerializer;
  }

  <T> void executeRequest(final Class<T> responseType, final HttpRequest request,
      final HttpCallback<T> callback) {

    httpHandler.execute(httpClient, request, new HttpCallback<String>() {
      @Override public void onResponse(String response) {
        if (responseType != String.class) {
          T objectResponse = null;
          try {
            objectResponse = jsonSerializer.deserialize(responseType, response);
          } catch (JSONException e) {
            e.printStackTrace();
          }
          callback.onResponse(objectResponse);
        }
        else {
          callback.onResponse((T) response);
        }
      }

      @Override public void onError(ServiceError error) {
        callback.onError(error);
      }
    });
  }

  void executeRequest(final HttpRequest request, final HttpCallback<String> callback) {
    Log.d("NetworkManager", "Request: " + request.getUrl());
    httpHandler.execute(httpClient, request, new HttpCallback<String>() {
      @Override public void onResponse(String response) {
        callback.onResponse(response);
      }

      @Override public void onError(ServiceError error) {
        callback.onError(error);
      }
    });
  }

}
