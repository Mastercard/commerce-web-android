package com.mastercard.mp.switchservices;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * {@code Client} used execute requests and return the response
 */
class HttpClient {

  private static final String CONTENT_LENGTH_HEADER = "Content-Length";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  private int timeoutInMillis;

  private HttpClient(int timeoutInMillis) {
    this.timeoutInMillis = timeoutInMillis;
  }

  static HttpClient createClient(int timeoutInMillis) {
    return new HttpClient(timeoutInMillis);
  }

  /**
   * Executes the given {@code} request on an {@link HttpURLConnection} and returns the response
   *
   * @param httpRequest request to be executed
   * @return {@code HttpResponse} returned from the connection
   * @throws IOException exception thrown if the output stream cannot be given
   */
  HttpResponse execute(HttpRequest httpRequest) throws IOException {
    HttpsURLConnection connection = getRequest(httpRequest);

    HttpBody body = httpRequest.getBody();
    if (body != null) {
      OutputStream outputStream = connection.getOutputStream();
      body.writeTo(outputStream);
      outputStream.flush();
      outputStream.close();
    }
    return getResponse(connection);
  }

  private HttpsURLConnection getRequest(HttpRequest httpRequest) throws IOException {
    HttpsURLConnection connection;

    URL url = new URL(httpRequest.getUrl());

    connection = (HttpsURLConnection) url.openConnection();
    connection.setRequestMethod(httpRequest.getMethod());
    connection.setConnectTimeout(timeoutInMillis);
    connection.setReadTimeout(timeoutInMillis);
    connection.setDoInput(true);
    //In case of 302 if we decide to go with caching approach ??  YAGNI??
    connection.setInstanceFollowRedirects(false);
    connection.setSSLSocketFactory(getSocketFactory());
    for (Map.Entry<String, String> entry : httpRequest.getAllHeaders().entrySet()) {
      connection.setRequestProperty(entry.getKey(), entry.getValue());
    }

    HttpBody body = httpRequest.getBody();
    if (body != null) {
      connection.setRequestProperty(CONTENT_LENGTH_HEADER, String.valueOf(body.getContentLength()));
      connection.setRequestProperty(CONTENT_TYPE_HEADER, body.getContentType());
      connection.setDoOutput(true);
    }

    return connection;
  }

  private SSLSocketFactory getSocketFactory() {
    SSLContext sslContext = null;
    TLSOnlySSLSocketFactory factory = null;
    try {
      sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, null, null);
      factory = new TLSOnlySSLSocketFactory(sslContext.getSocketFactory());
    } catch (KeyManagementException | NoSuchAlgorithmException e) {
      Log.e("HttpClient", e.getLocalizedMessage(), e);
    }

    return factory;
  }

  private HttpResponse getResponse(HttpsURLConnection connection) throws IOException {

    int statusCode = connection.getResponseCode();

    InputStream content;
    if (statusCode < 400) {
      content = connection.getInputStream();
    } else {
      content = connection.getErrorStream();
    }

    int totalSize = connection.getContentLength();
    String reason = connection.getResponseMessage();

    Map<String, String> headers = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
      if (entry.getKey() != null && !entry.getValue().isEmpty()) {
        headers.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().get(0));
      }
    }

    String contentType = connection.getContentType();

    return new HttpResponse.Builder().statusCode(statusCode)
        .content(content)
        .size(totalSize)
        .reason(reason)
        .headers(headers)
        .contentType(contentType)
        .build();
  }
}