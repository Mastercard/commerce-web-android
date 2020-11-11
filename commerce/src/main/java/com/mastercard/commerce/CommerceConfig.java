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

import android.graphics.drawable.Drawable;
import java.util.Locale;
import java.util.Set;

/**
 * Configuration class used to initialize {@link CommerceWebSdk} with the specific
 * merchant parameters.
 */
public class CommerceConfig {
  private final Locale locale;
  private final String checkoutId;
  private final String checkoutUrl;
  private final Set<CardType> allowedCardTypes;
  private Drawable checkoutButtonImage;

  public CommerceConfig(Locale locale,
      String checkoutId,
      String checkoutUrl,
      Set<CardType> allowedCardTypes) {
    this.locale = locale;
    this.checkoutId = checkoutId;
    this.checkoutUrl = checkoutUrl;
    this.allowedCardTypes = allowedCardTypes;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getCheckoutId() {
    return checkoutId;
  }

  public String getCheckoutUrl() {
    return checkoutUrl;
  }

  public Set<CardType> getAllowedCardTypes() {
    return allowedCardTypes;
  }

  public Drawable getCheckoutButtonImage() {
    return checkoutButtonImage;
  }

  public void setCheckoutButtonImage(Drawable checkoutButtonImage) {
    this.checkoutButtonImage = checkoutButtonImage;
  }
}
