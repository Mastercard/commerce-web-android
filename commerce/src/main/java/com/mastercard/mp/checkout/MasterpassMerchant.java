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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import android.util.Log;
import com.mastercard.commerce.CardType;
import com.mastercard.commerce.CheckoutButton;
import com.mastercard.commerce.CheckoutButtonManager;
import com.mastercard.commerce.CheckoutRequest;
import com.mastercard.commerce.CommerceConfig;
import com.mastercard.commerce.CommerceWebSdk;
import com.mastercard.commerce.Mastercard;
import com.mastercard.commerce.R;
import com.mastercard.commerce.Visa;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code MasterpassMerchant} is the entry point for the Masterpass Merchant Checkout SDK. Here you
 * will find static methods to perform SDK initialization and configuration.
 *
 * @deprecated You should migrate your code to use {@link CommerceWebSdk} instead. All APIs available
 * in this package will be deprecated in a future release.
 */
@Deprecated public final class MasterpassMerchant {
  private static final String TAG = MasterpassMerchant.class.getSimpleName();
  private static WeakReference<Context> contextWeakReference;
  private static volatile CommerceWebSdk commerceWebSdk;
  private static MasterpassCheckoutCallback checkoutCallback;
  private static boolean merchantInitiated;

  private MasterpassMerchant() {
    throw new IllegalArgumentException("Cannot instantiate this class!");
  }

  /**
   * Initializes the SDK so that it can safely be accessed for mobile checkout functionality
   *
   * @param masterpassMerchantConfiguration merchant-specific configuration used to properly setup
   * the SDK
   * @param listener object to listen for initialization updates
   */

  public static void initialize(
      @NonNull MasterpassMerchantConfiguration masterpassMerchantConfiguration,
      final MasterpassInitCallback listener) {
    Set<CardType> allowedCardTypes =
        convertAllowedNetworkTypes(masterpassMerchantConfiguration.getAllowedNetworkTypes());
    CommerceConfig commerceConfig = new CommerceConfig(masterpassMerchantConfiguration.getLocale(),
        masterpassMerchantConfiguration.getCheckoutId(),
        masterpassMerchantConfiguration.getEnvironment(), allowedCardTypes);
    commerceWebSdk = CommerceWebSdk.getInstance();
    commerceWebSdk.initialize(masterpassMerchantConfiguration.getContext(), commerceConfig);
    contextWeakReference = new WeakReference<>(masterpassMerchantConfiguration.getContext());

    listener.onInitSuccess();
  }

  /**
   * Adds SRC as payment method.
   * {@param paymentMethodCallback} callback that provides different method to return success or
   * failure response. {@code PaymentMethodCallback#getPaymentMethodRequest()} should be used to
   * notify {@code AddPaymentMethodRequest} to SDK.
   */
  public static void addMasterpassPaymentMethod(PaymentMethodCallback paymentMethodCallback) {
    Bitmap bitmap =
        BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_masterpass);
    String walletId = "Masterpass";
    String paymentMethodName = "Masterpass";
    paymentMethodCallback.onPaymentMethodAdded(
        new MasterpassPaymentMethod(bitmap, walletId, paymentMethodName, "", ""));
  }

  /**
   * {@code MasterpassButton} is used to initiate transactions using payment methods that are
   * collected by the SDK. {@code MasterpassButton} is an {@code ImageButton} that should be added
   * to a layout when the user is expected to complete checkout.
   *
   * @param masterpassCheckoutCallback {@link MasterpassCheckoutCallback} to retrieve {@link
   * MasterpassCheckoutRequest} and to notify with updates related to this transaction.
   * @return {@link MasterpassButton} to display to user to initiate transaction
   */
  public static MasterpassButton getMasterpassButton(
      MasterpassCheckoutCallback masterpassCheckoutCallback) {
    return getMasterpassButton(MasterpassButton.NO_FLOWS_SET, masterpassCheckoutCallback);
  }

  /**
   * This method no longer support checkout. Calling this method will result error
   * MasterpassError.ERROR_CODE_NOT_SUPPORTED in callback.
   *
   * @param behaviour {@link MasterpassButton.Behavior} to set web flow
   * @param masterpassCheckoutCallback {@link MasterpassCheckoutCallback} to retrieve {@link
   * MasterpassCheckoutRequest} and to notify with updates related to this transaction.
   * @return {@link MasterpassButton} to display to user to initiate transaction
   */
  public static MasterpassButton getMasterpassButton(@MasterpassButton.Behavior int behaviour,
      final MasterpassCheckoutCallback masterpassCheckoutCallback) {
    CheckoutButtonManager checkoutButtonManager = CheckoutButtonManager.getInstance();
    MasterpassButton button =
        checkoutButtonManager.getCheckoutButton(new CheckoutButton.CheckoutButtonClickListener() {
          @Override public void onClick() {
            merchantInitiated = false;
            checkout(masterpassCheckoutCallback.getCheckoutRequest(), masterpassCheckoutCallback);
          }
        });
    return button;
  }

  /**
   * Provides the ability to directly call a checkout which gives SRC experience
   *
   * @param masterpassCheckoutCallback callback to return results to merchant
   */
  public static void masterpassCheckout(
      final MasterpassCheckoutCallback masterpassCheckoutCallback) {
    merchantInitiated = true;

    checkout(masterpassCheckoutCallback.getCheckoutRequest(), masterpassCheckoutCallback);
  }

  /**
   * This method no longer support checkout. Calling this method will result error
   * MasterpassError.ERROR_CODE_NOT_SUPPORTED in callback.
   *
   * @param isCheckoutWithPairingEnabled true if checkout with pairing is required, else false
   * @param masterpassCheckoutCallback callback to return results to merchant
   */
  public static void pairing(boolean isCheckoutWithPairingEnabled,
      MasterpassCheckoutCallback masterpassCheckoutCallback) {
    // Since pairing flow supports are removed error will be returned in callback
    if (isCheckoutWithPairingEnabled) {
      merchantInitiated = true;

      checkout(masterpassCheckoutCallback.getCheckoutRequest(), masterpassCheckoutCallback);
    } else {
      pairingError(masterpassCheckoutCallback);
    }
  }

  /**
   * This is legacy method. It is no longer supported. Invoking it will do nothing.
   */
  public static void resetDefaultWallet() {

  }

  /**
   * This is legacy method. It is no longer supported. Invoking it will do nothing.
   */
  public static void setUserDetails(UserSetup userSetup) {

  }

  /**
   * Initiates PaymentMethod checkout with SRC experience.
   *
   * @param paymentMethodId id of added payment method
   * @param masterpassCheckoutCallback {@link MasterpassCheckoutCallback} implementation provided
   * by
   * MerchantListener.
   */
  public static void paymentMethodCheckout(String paymentMethodId,
      MasterpassCheckoutCallback masterpassCheckoutCallback) {
    merchantInitiated = true;
    checkout(masterpassCheckoutCallback.getCheckoutRequest(), masterpassCheckoutCallback);
  }

  public static MasterpassCheckoutCallback getCheckoutCallback() {
    return checkoutCallback;
  }

  public static boolean isMerchantInitiated() {
    return merchantInitiated;
  }

  private static CheckoutRequest buildCheckoutRequest(
      MasterpassCheckoutRequest masterpassCheckoutRequest) {

    // long amount to double amount conversion
    Amount amount = masterpassCheckoutRequest.getAmount();

    return new CheckoutRequest.Builder().amount(getAmount(amount))
        .currency(amount.getCurrencyCode())
        .cartId(masterpassCheckoutRequest.getCartId())
        .cvc2Support(masterpassCheckoutRequest.isCvc2support())
        .shippingLocationProfile(masterpassCheckoutRequest.getShippingProfileId())
        .suppress3ds(masterpassCheckoutRequest.isSuppress3Ds())
        .suppressShippingAddress(!masterpassCheckoutRequest.isShippingRequired())
        .validityPeriodMinutes(masterpassCheckoutRequest.getValidityPeriodMinutes())
        .unpredictableNumber(masterpassCheckoutRequest.getTokenization() != null
            ? masterpassCheckoutRequest.getTokenization().getUnpredictableNumber() : null)
        .cryptoOptions(buildCryptoOptions(masterpassCheckoutRequest.getTokenization()))
        .build();
  }

  private static double getAmount(Amount amount) {
    return (double) amount.getTotal() / 100;
  }

  private static Set<CardType> buildCardTypes(List<NetworkType> networkTypeList) {
    Set<CardType> cardTypesSet = new HashSet<>();
    if (null != networkTypeList) {
      List<CardType> cardTypeList = new ArrayList<>(EnumSet.allOf(CardType.class));
      for (NetworkType networkType : networkTypeList) {
        for (CardType cardType : cardTypeList) {
          if (networkType.getNetworkType().equalsIgnoreCase(cardType.toString())) {
            cardTypesSet.add(cardType);
            break;
          }
        }
      }
    }
    return cardTypesSet;
  }

  private static Set<com.mastercard.commerce.CryptoOptions> buildCryptoOptions(
      Tokenization tokenization) {

    Set<com.mastercard.commerce.CryptoOptions> cryptoOptionSet = null;
    if (null != tokenization && null != tokenization.getCryptoOptions()) {
      cryptoOptionSet = new HashSet<>();
      buildMastercardCryptoOptions(tokenization.getCryptoOptions(), cryptoOptionSet);
      buildVisaCryptoOptions(tokenization.getCryptoOptions(), cryptoOptionSet);
    }
    return cryptoOptionSet;
  }

  private static void buildMastercardCryptoOptions(CryptoOptions cryptoOptions,
      Set<com.mastercard.commerce.CryptoOptions> cryptoOptionSet) {
    if (cryptoOptions.getMastercard() == null) return;

    List<Mastercard.MastercardFormat> baseMastercardFormatList =
        new ArrayList<>(EnumSet.allOf(Mastercard.MastercardFormat.class));
    Set<Mastercard.MastercardFormat> mastercardFormatSet = new HashSet<>();

    for (String mastercardFormat : cryptoOptions.getMastercard().getFormat()) {
      for (Mastercard.MastercardFormat baseMastercardFormat : baseMastercardFormatList) {
        if (mastercardFormat.equalsIgnoreCase(baseMastercardFormat.toString())) {
          mastercardFormatSet.add(baseMastercardFormat);
        }
      }
    }

    if (!mastercardFormatSet.isEmpty()) {
      com.mastercard.commerce.CryptoOptions mastercard = new Mastercard(mastercardFormatSet);
      cryptoOptionSet.add(mastercard);
    }
  }

  private static void buildVisaCryptoOptions(CryptoOptions cryptoOptions,
      Set<com.mastercard.commerce.CryptoOptions> cryptoOptionSet) {
    if (cryptoOptions.getVisa() == null) return;

    List<Visa.VisaFormat> baseVisaFormatList =
        new ArrayList<>(EnumSet.allOf(Visa.VisaFormat.class));
    Set<Visa.VisaFormat> visaFormatSet = new HashSet<>();

    for (String visaFormat : cryptoOptions.getVisa().getFormat()) {
      for (Visa.VisaFormat baseVisaFormat : baseVisaFormatList) {
        if (visaFormat.equalsIgnoreCase(baseVisaFormat.toString())) {
          visaFormatSet.add(baseVisaFormat);
        }
      }
    }

    if (!visaFormatSet.isEmpty()) {
      com.mastercard.commerce.CryptoOptions visa = new Visa(visaFormatSet);
      cryptoOptionSet.add(visa);
    }
  }

  private static Context getContext() {
    return contextWeakReference.get();
  }

  // Since pairing flow supports are removed error will be returned in callback

  private static void pairingError(MasterpassCheckoutCallback masterpassCheckoutCallback) {
    Log.e(TAG, "Pairing without checkout is no longer supported");

    MasterpassError error = new MasterpassError(MasterpassError.ERROR_CODE_NOT_SUPPORTED,
        "Pairing without checkout is no longer supported");

    masterpassCheckoutCallback.onCheckoutError(error);
  }

  private static void checkout(MasterpassCheckoutRequest masterpassCheckoutRequest,
      MasterpassCheckoutCallback masterpassCheckoutCallback) {
    checkoutCallback = masterpassCheckoutCallback;

    commerceWebSdk.checkout(buildCheckoutRequest(masterpassCheckoutRequest));
  }

  private static Set<CardType> convertAllowedNetworkTypes(List<NetworkType> allowedNetworkTypes) {
    Set<CardType> allowedCardTypes = new HashSet<>();

    for (NetworkType allowedNetworkType : allowedNetworkTypes) {
      CardType allowedCardType;
      switch (allowedNetworkType.getNetworkType()) {
        case NetworkType.JCB:
          allowedCardType = CardType.JCB;
          break;
        case NetworkType.VISA:
          allowedCardType = CardType.VISA;
          break;
        case NetworkType.AMEX:
          allowedCardType = CardType.AMEX;
          break;
        case NetworkType.DISCOVER:
          allowedCardType = CardType.DISCOVER;
          break;
        case NetworkType.MAESTRO:
          allowedCardType = CardType.MAESTRO;
          break;
        case NetworkType.DINERS:
          allowedCardType = CardType.DINERS;
          break;
        default:
          allowedCardType = CardType.MASTER;
          break;
      }

      allowedCardTypes.add(allowedCardType);
    }

    return allowedCardTypes;
  }
}
