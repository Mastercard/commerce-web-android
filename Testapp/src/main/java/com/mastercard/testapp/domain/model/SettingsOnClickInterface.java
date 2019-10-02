package com.mastercard.testapp.domain.model;

import com.mastercard.testapp.presentation.view.MerchantCheckoutView;

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
