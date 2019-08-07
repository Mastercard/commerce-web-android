package com.mastercard.mp.switchservices;

/**
 * Error class used to indicate when issues arise during execution of the SDK. {@code
 * MasterpassError} uses a {@code code} to distinguish between different types of errors. An
 * accompanying {@code message} gives a detailed explanation as to the cause of the error.
 */

public class ServiceError {
  public static final int ERROR_CODE_NETWORK_ERROR = 100;
  public static final int ERROR_CODE_SERVER_ERROR = 101;
  public static final int ERROR_CODE_BAD_REQUEST = 102;
  public static final int ERROR_CODE_REQUEST_TIMEOUT = 103;
  public static final int ERROR_CODE_REQUEST_FAILED = 104;
  public static final int ERROR_CODE_BAD_RESPONSE = 115;
  public static final int ERROR_CODE_OAUTH_FAILED = 116;
  private final int code;
  private final String message;

  public ServiceError(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int code() {
    return code;
  }

  public String message() {
    return message;
  }
}
