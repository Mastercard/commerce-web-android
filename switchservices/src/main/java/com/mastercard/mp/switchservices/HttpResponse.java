package com.mastercard.mp.switchservices;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code Response} returned from an executed network call. This {@code Response} encapsulates the
 * http response code, headers, content, and reason.
 */
class HttpResponse {
  private final int statusCode;
  private final InputStream content;
  private final long totalSize;
  private final String reason;
  private final Map<String, String> headers;
  private final String contentType;

  private HttpResponse(Builder builder) {
    this.statusCode = builder.statusCode;
    this.content = builder.content;
    this.totalSize = builder.size;
    this.reason = builder.reason;
    this.headers = Collections.unmodifiableMap(new HashMap<>(builder.headers));
    this.contentType = builder.contentType;
  }

  int getStatusCode() {
    return statusCode;
  }

  InputStream getContent() {
    return content;
  }

  long getTotalSize() {
    return totalSize;
  }

  String getReason() {
    return reason;
  }

  String getContentType() {
    return contentType;
  }

  String getHeader(String name) {
    return headers.get(name);
  }

  Map<String, String> getAllHeaders() {
    return headers;
  }

  static final class Builder {
    private Map<String, String> headers;
    private InputStream content;
    private String contentType;
    private String reason;
    private long size;
    private int statusCode;

    Builder() {
      this.size = -1;
      this.headers = new HashMap<>();
    }

    /**
     * @param statusCode status code of the response from the network call (i.e. {@code 200} for a
     * successful call)
     */
    Builder statusCode(int statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    /**
     * @param content the response data returned by the network call
     */
    Builder content(InputStream content) {
      this.content = content;
      return this;
    }

    /**
     * @param size content-length of the response
     */
    Builder size(long size) {
      this.size = size;
      return this;
    }

    /**
     * @param reason {@code HTTP Response} message associated with the {@code status code}
     */
    Builder reason(String reason) {
      this.reason = reason;
      return this;
    }

    /**
     * Replace the existing headers with the given map
     *
     * @param headers map of headers to include in this request
     */
    Builder headers(Map<String, String> headers) {
      this.headers = new HashMap<>(headers);
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
     * @param contentType content type of the response
     */
    Builder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    /**
     * @return the formed {@code HttpResponse} object
     */
    HttpResponse build() {
      return new HttpResponse(this);
    }
  }
}