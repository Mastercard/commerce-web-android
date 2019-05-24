package com.us.masterpass.merchantapp.domain.masterpass;

import android.widget.Button;

/**
 * Created by sfarias on 13-10-17.
 */
public interface MasterpassSdkInterface {

    /**
     * The interface Get from masterpass sdk.
     */
    interface GetFromMasterpassSdk {
        /**
         * Sdk response success.
         */
        void sdkResponseSuccess();

        /**
         * Sdk response error.
         *
         * @param error the error
         */
        void sdkResponseError(String error);
    }

    /**
     * The interface Get from masterpass sdk button.
     */
    interface GetFromMasterpassSdkButton {
        /**
         * Sdk response success.
         *
         * @param masterpassButton the masterpass button
         */
        void sdkResponseSuccess(Button masterpassButton);

        /**
         * Sdk response error.
         */
        void sdkResponseError();
    }
}
