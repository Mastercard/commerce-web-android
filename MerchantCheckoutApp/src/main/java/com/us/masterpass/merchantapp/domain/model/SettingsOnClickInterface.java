package com.us.masterpass.merchantapp.domain.model;

import com.us.masterpass.merchantapp.presentation.view.MerchantCheckoutView;

/**
 * Created by Sebastian Farias on 21-10-17.
 * <p>
 * Settings interface on items click
 */
public interface SettingsOnClickInterface extends MerchantCheckoutView {
    /**
     * On click setting item.
     *
     * @param settingsVO the settings vo
     */
    void onClickSettingItem(SettingsVO settingsVO);

    /**
     * Load login.
     */
    void loadLogin();
}
