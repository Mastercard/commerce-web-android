package com.us.masterpass.merchantapp.presentation.presenter.base;

import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import java.util.HashMap;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public interface SettingsPresenterInterface extends Presenter {
    /**
     * Load settings.
     */
    void loadSettings();

    /**
     * Save settings switch.
     *
     * @param settingsVO the settings vo
     */
    void saveSettingsSwitch(SettingsVO settingsVO);

    /**
     * Gets pairing id.
     *
     * @param checkoutData the checkout data
     */
    void getPairingId(HashMap<String, Object> checkoutData);
}
