package com.us.masterpass.merchantapp.domain;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public interface SettingsSaveInterface {
    /**
     * The interface Save items callback.
     */
    interface SaveItemsCallback {
        /**
         * On settings saved.
         */
        void onSettingsSaved();

        /**
         * On settings not saved.
         */
        void onSettingsNotSaved();
    }
}
