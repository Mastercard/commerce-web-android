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

/**
 * Listener interface to be notified of initialization updates (i.e. {@code onInitSuccess} and
 * {@code onFailure}
 */

public interface MasterpassInitCallback {
  /**
   * Notifies the listener that initialization has completed successfully. At this point the SDK is
   * in a stable state and all APIs should work as expected. Trying to perform any operations with
   * the SDK before receiving this notification will result in unexpected behavior.
   */
  void onInitSuccess();

  /**
   * Notifies the listener that an error has been encountered during initialization. After receiving
   * this, trying to use the SDK will result in unexpected behavior.
   *
   * @param error details of the cause of the error and potentially steps that can be taken to fix
   * the issue and reinitialize.
   */
  void onInitError(MasterpassError error);
}
