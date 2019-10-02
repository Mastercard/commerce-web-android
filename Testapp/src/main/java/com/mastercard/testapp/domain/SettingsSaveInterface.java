package com.mastercard.testapp.domain;

/**
 * Created by Sebastian Farias on 08-10-17.
 * <p>
 * Interface used on settings save options
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
