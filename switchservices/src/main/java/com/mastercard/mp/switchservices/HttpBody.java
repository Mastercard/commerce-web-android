package com.mastercard.mp.switchservices;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Body of {@link HttpRequest}, encapsulating the payload
 */
class HttpBody {
  private final String contentType;
  private final long contentLength;
  private final byte[] content;
  private final ByteArrayInputStream contentInputStream;

  HttpBody(String content, String contentType) throws UnsupportedEncodingException {
    this(content.getBytes("UTF-8"), contentType);
  }

  HttpBody(byte[] content, String contentType) {
    this.contentType = contentType;
    this.contentLength = content.length;
    this.content = content;
    this.contentInputStream = new ByteArrayInputStream(content);
  }

  public InputStream getContent() {
    return contentInputStream;
  }

  long getContentLength() {
    return contentLength;
  }

  String getContentType() {
    return contentType;
  }

  void writeTo(OutputStream out) throws IOException {
    if (out == null) {
      throw new IllegalArgumentException("Output stream cannot be null");
    }

    out.write(content);
  }
}