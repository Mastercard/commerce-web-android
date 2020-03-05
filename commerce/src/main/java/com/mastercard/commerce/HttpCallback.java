package com.mastercard.commerce;

/**
 * Callback interface used to receive Http response data. Data comes back as a string as either an
 * error or a response.
 */

interface HttpCallback {
  /**
   * Notifies the listener that a successful response has been returned by the server
   *
   * @param response the server response returned from the server as an object {@code <R>}
   */
  void onResponse(String response);

  /**
   * Notifies the listener that an error was returned from the network call
   *
   * @param error error given by the network call
   */
  void onErrorResponse(String error);
}
