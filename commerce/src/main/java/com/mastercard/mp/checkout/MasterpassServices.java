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
 * {@code MasterpassServices} is designed to make network calls to {@code Switch} more convenient.
 * This class maps the {@code Switch} APIs consumed by the SDK and handle creation of requests.
 */

@Deprecated final class MasterpassServices {

  static String getBaseUrl(@MasterpassMerchantConfiguration.Environment String environment) {
    String baseUrl = null;

    switch (environment) {
      case MasterpassMerchantConfiguration.DEV:
        baseUrl = "http://ech-10-157-130-186.devcloud.mastercard.com/srci/";
        break;
      case MasterpassMerchantConfiguration.STAGE:
        baseUrl = "https://stage.src.mastercard.com/srci/";
        break;
      case MasterpassMerchantConfiguration.STAGE1:
        baseUrl = "https://stage1.masterpass.com/srci/";
        break;
      case MasterpassMerchantConfiguration.STAGE2:
        baseUrl = "https://stage2.masterpass.com/srci/";
        break;
      case MasterpassMerchantConfiguration.ITF:
        baseUrl = "https://itf.masterpass.com/srci/";
        break;
      case MasterpassMerchantConfiguration.STAGE3:
        baseUrl = "https://stage3.masterpass.com/srci/";
        break;
      case MasterpassMerchantConfiguration.SANDBOX:
        //baseUrl = "https://sandbox.masterpass.com/srci/";
        baseUrl = "https://sandbox.masterpass.com/routing/v2/mobileapi/web-checkout";
        break;
      case MasterpassMerchantConfiguration.PRODUCTION:
        baseUrl = "https://masterpass.com/srci/";
        break;
      case MasterpassMerchantConfiguration.INT:
        baseUrl = "https://int.masterpass.com/srci/";
        break;
      default:
        baseUrl = "https://masterpass.com/srci/";
        break;
    }

    return baseUrl;
  }
}
