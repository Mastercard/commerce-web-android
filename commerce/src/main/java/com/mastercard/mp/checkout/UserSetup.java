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
 * This class represents user details provided by merchant application
 */

public class UserSetup {
  private String phoneNumber;
  private String email;

  String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * This API is used by the user to set the phone number for Mex Registration
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  String getEmail() {
    return email;
  }

  /**
   * This API is used by the user to set the email id for Mex Registration
   */
  public void setEmail(String email) {
    this.email = email;
  }
}