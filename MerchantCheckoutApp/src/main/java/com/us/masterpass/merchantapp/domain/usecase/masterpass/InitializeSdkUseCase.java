package com.us.masterpass.merchantapp.domain.usecase.masterpass;

import android.content.Context;
import com.mastercard.mp.checkout.MasterpassButton;
import com.mastercard.mp.checkout.MasterpassMerchantConfiguration;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkInterface.GetFromMasterpassSdk;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkInterface.GetFromMasterpassSdkButton;


/**
 * Created by Sebastian Farias on 08-10-17.
 * <p>
 * Initialize masterpass SDK class called to get unique instance of {@link MasterpassSdkCoordinator}
 * that handles all request an interaction with the SDK
 */
public class InitializeSdkUseCase {

    /**
     * Initialize sdk, only one instance is allowed.
     *
     * @param configuration configuration {@link MasterpassMerchantConfiguration}
     * @param callback      {@link GetFromMasterpassSdk}
     */
    public static void initializeSdk(MasterpassMerchantConfiguration configuration,
                                     final GetFromMasterpassSdk callback) {
        MasterpassSdkCoordinator.getInstance().initializeMasterpassMerchantCallback(
                configuration,
                new GetFromMasterpassSdk() {
                    @Override
                    public void sdkResponseSuccess() {
                        callback.sdkResponseSuccess();
                    }

                    @Override
                    public void sdkResponseError(String error) {
                        callback.sdkResponseError("");
                    }
                });
    }

    /**
     * Initialize sdk, only one instance is allowed.
     *
     * @param context  Android application context
     * @param callback {@link GetFromMasterpassSdk}
     */
    public static void initializeSdk(Context context,
                                     final GetFromMasterpassSdk callback) {
        MasterpassSdkCoordinator.getInstance().initializeMasterpassMerchantCallback(
                context,
                new GetFromMasterpassSdk() {
                    @Override
                    public void sdkResponseSuccess() {
                        callback.sdkResponseSuccess();
                    }

                    @Override
                    public void sdkResponseError(String error) {
                        callback.sdkResponseError("");
                    }
                });
    }

    /**
     * Get SDK button.
     *
     * @param callback {@link GetFromMasterpassSdkButton}
     */
    public static void getSdkButton(final GetFromMasterpassSdkButton callback) {
        MasterpassSdkCoordinator.getInstance().getMasterpassButton(
                new GetFromMasterpassSdkButton() {
                    @Override
                    public void sdkResponseSuccess(MasterpassButton masterpassButton) {
                        callback.sdkResponseSuccess(masterpassButton);
                    }

                    @Override
                    public void sdkResponseError() {
                        callback.sdkResponseError();
                    }
                });
    }

    /**
     * Get merchant pairing.
     *
     * @param callback {@link GetFromMasterpassSdk}
     */
    public static void getMerchantPairing(final GetFromMasterpassSdk callback) {
        MasterpassSdkCoordinator.getInstance().pairingSDKWithoutButton(
                new GetFromMasterpassSdk() {
                    @Override
                    public void sdkResponseSuccess() {
                        callback.sdkResponseSuccess();
                    }

                    @Override
                    public void sdkResponseError(String error) {
                        callback.sdkResponseError("");
                    }
                });
    }
}