package com.mastercard.mp.switchservices;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handler to execute {@link HttpRequest} for a given {@link HttpClient} and return responses on
 * the given {@link HttpCallback}
 */

class HttpHandler {
  private static final Logger logger = Logger.getLogger(HttpHandler.class.getSimpleName());
  private final Scheduler scheduler;

  HttpHandler(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  /**
   * Executes a given {@link HttpRequest} with the given {@link HttpClient} and returns the
   * response
   * to the given {@link HttpCallback}.
   *
   * @param client {@code HttpClient} to execute the request with
   * @param request {@code HttpRequest} encapsulating the request parameters
   * @param callback listener to receive response from the network call
   */
  void execute(final HttpClient client, final HttpRequest request,
      final HttpCallback<String> callback) {
    scheduler.execute(new Runnable() {
      @Override public void run() {
        try {
          HttpResponse httpResponse = client.execute(request);
          String stringResult = getResponseString(httpResponse);
          Log.d("NetworkManager", "Response: " + stringResult);

          if (httpResponse.getStatusCode() < 400) {
            scheduler.notifyResponse(stringResult, callback);
          } else {
            scheduler.notifyError(getMasterpassError(httpResponse, stringResult), callback);
          }
        } catch (IOException e) {
          logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
          scheduler.notifyError(new ServiceError(ServiceError.ERROR_CODE_REQUEST_FAILED,
              "The request failed to complete"), callback);
        }
      }
    });
  }

  private ServiceError getMasterpassError(HttpResponse httpResponse, String response) {
    int errorCode;
    String errorMessage;

    switch (httpResponse.getStatusCode()) {
      case 400:
        errorCode = ServiceError.ERROR_CODE_BAD_REQUEST;
        errorMessage = "Server could not process the given request";
        break;
      case 408:
        errorCode = ServiceError.ERROR_CODE_REQUEST_TIMEOUT;
        errorMessage = "Request has timed out";
        break;
      case 500:
        errorCode = ServiceError.ERROR_CODE_SERVER_ERROR;
        errorMessage = response;
        break;
      default:
        errorCode = ServiceError.ERROR_CODE_NETWORK_ERROR;
        errorMessage = response;
    }

    return new ServiceError(errorCode, errorMessage);
  }

  private String getResponseString(HttpResponse httpResponse) throws IOException {
    InputStream responseStream = httpResponse.getContent();
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;

    while ((length = responseStream.read(buffer)) != -1) {
      result.write(buffer, 0, length);
    }

    return result.toString("UTF-8");
  }
}
