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

package com.mastercard.commerce;

import java.util.Set;

/**
 * Utility class to validate specific values (i.e. if given objects are null)
 */

final class Validate {
  private Validate() {
  }

  /**
   * <p>A method to check if provided object is null or not, throw {@link IllegalArgumentException}
   * if object is null</p>
   *
   * e @param obj Object to check
   */
  static void notNull(String key, Object obj) {
    if (obj == null) {
      throw new IllegalArgumentException(
          String.format("Invalid value provided for parameter %s", key));
    }
  }

  static void validMinimum(String key, double arg, double minimum) {
    if (arg <= minimum) {
      throw new IllegalArgumentException(
          String.format("Invalid value provided for parameter %s", key));
    }
  }

  static void notEmpty(Set... args) {
    for (Set set : args) {
      if (null == set || set.isEmpty()) {
        throw new IllegalArgumentException("Sets cannot be empty!");
      }
    }
  }
}
