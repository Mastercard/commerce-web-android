package com.us.masterpass.merchantapp.domain.usecase.masterpass;

import android.content.Context;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkInterface.GetFromMasterpassSdk;


/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class InitializeSdkUseCase {

    /**
     * Initialize sdk.
     *
     * @param context  the context
     * @param callback the callback
     */
    public static void initializeSdk(Context context,

                                     final GetFromMasterpassSdk callback) {
        //Older implementation
       /* MasterpassSdkCoordinator.getInstance().initializeMasterpassMerchantCallback(
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
                });*/
    }

}