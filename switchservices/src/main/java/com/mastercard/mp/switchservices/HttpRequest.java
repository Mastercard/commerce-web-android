package com.mastercard.mp.switchservices;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code Request} used with {@link HttpClient} to perform a request to a given {@code URL}. This
 * class uses {@link Builder} to produce a usable object. When this object is given to {@link
 * HttpClient}, the {@code client} can execute the network call.
 */
class HttpRequest {
  static final String POST = "POST";
  static final String GET = "GET";
  static final String PUT = "PUT";
  static final String DELETE = "DELETE";
  private final String url;
  private final String method;
  private final Map<String, String> headers;
  private final HttpBody body;

  private HttpRequest(Builder builder) {
    this.url = builder.url;
    this.method = builder.method;
    this.headers = Collections.unmodifiableMap(new HashMap<>(builder.headers));
    this.body = builder.body;
  }

  String getUrl() {
    return url;
  }

  String getMethod() {
    return method;
  }

  Map<String, String> getAllHeaders() {
    return headers;
  }

  String getHeader(String name) {
    return headers.get(name);
  }

  HttpBody getBody() {
    return body;
  }

  static final class Builder {
    private String method;
    private String url;
    private Map<String, String> headers;
    private HttpBody body;

    Builder() {
      this.headers = new HashMap<>();
    }

    /**
     * @param url URL of the server to which the request should be made
     */
    Builder setUrl(String url) {
      this.url = url;
      return this;
    }

    /**
     * @param method Http method must match one of the pre-defined {@code static method strings}
     */
    Builder setMethod(String method) {
      this.method = method;
      return this;
    }

    /**
     * @param body the request body with content
     */
    Builder setBody(HttpBody body) {
      this.body = body;
      return this;
    }

    /**
     * Add header to the existing headers map
     *
     * @param name header key
     * @param value header value
     */
    Builder addHeader(String name, String value) {
      headers.put(name, value);
      return this;
    }

    /**
     * Add to the existing headers the given map
     *
     * @param headers map of headers to include in this request
     */
    Builder addHeaders(Map<String, String> headers) {
      this.headers.putAll(headers);
      return this;
    }

    /**
     * Replace the existing headers with the given map
     *
     * @param headers map of headers to include in this request
     */
    Builder setHeaders(Map<String, String> headers) {
      this.headers = new HashMap<>(headers);
      return this;
    }

    /**
     * @return the formed {@code HttpRequest}
     */
    HttpRequest build() {
      return new HttpRequest(this);
    }
  }
}