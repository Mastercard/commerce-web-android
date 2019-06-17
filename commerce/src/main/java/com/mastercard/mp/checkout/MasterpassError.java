/* Copyright © 2019 Mastercard. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 =============================================================================*/

package com.mastercard.mp.checkout;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Error class used to indicate when issues arise during execution of the SDK. {@code
 * MasterpassError} uses a {@code code} to distinguish between different types of errors. An
 * accompanying {@code message} gives a detailed explanation as to the cause of the error.
 */

public class MasterpassError {
  public static final int ERROR_CODE_REQUEST_TIMEOUT = 103;
  public static final int ERROR_CODE_REQUEST_FAILED = 104;
  public static final int ERROR_CODE_INITIALIZATION_FAILED = 105;
  public static final int ERROR_CODE_INITIALIZATION_FAILED_CANNOT_RETRIEVE_WALLETS = 107;
  public static final int ERROR_WEB_CHECKOUT_NOT_ENABLED = 108;
  public static final int ERROR_CODE_CANCEL_WALLET = 116;
  public static final int ERROR_WEB_BROWSER_NOT_FOUND = 122;
  public static final int ERROR_CODE_WALLET_CERTIFICATE_RETRIEVE_FAILED = 125;
  public static final int ERROR_CODE_PAYMENT_METHODS_NOT_AVAILABLE = 126;
  public static final int ERROR_MEX_CHECKOUT_NOT_AVAILABLE = 127;
  public static final int ERROR_GET_LEGAL_DOCS_INFO_FAILED = 129;
  public static final int ERROR_CODE_NOT_SUPPORTED = 130;

  private final int code;
  private final String message;

  public MasterpassError(@ErrorCode int code, String message) {
    this.code = code;
    this.message = message;
  }

  public @ErrorCode int code() {
    return code;
  }

  public String message() {
    return message;
  }

  @Retention(RetentionPolicy.SOURCE) @IntDef({
      ERROR_CODE_REQUEST_TIMEOUT, ERROR_CODE_REQUEST_FAILED, ERROR_CODE_INITIALIZATION_FAILED,
      ERROR_CODE_INITIALIZATION_FAILED_CANNOT_RETRIEVE_WALLETS, ERROR_WEB_CHECKOUT_NOT_ENABLED,
      ERROR_CODE_CANCEL_WALLET, ERROR_WEB_BROWSER_NOT_FOUND,
      ERROR_CODE_WALLET_CERTIFICATE_RETRIEVE_FAILED, ERROR_GET_LEGAL_DOCS_INFO_FAILED,
      ERROR_CODE_PAYMENT_METHODS_NOT_AVAILABLE, ERROR_MEX_CHECKOUT_NOT_AVAILABLE,
      ERROR_CODE_NOT_SUPPORTED
  }) @interface ErrorCode {
  }
}
