package com.mastercard.testapp.presentation.presenter.base;

import android.content.Context;
import com.mastercard.testapp.domain.model.SettingsVO;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public interface SettingsDetailPresenterInterface extends Presenter {
  /**
   * Load settings detail.
   */
  void loadSettingsDetail();

  /**
   * Save settings.
   *
   * @param settings the settings
   */
  void saveSettings(List<SettingsVO> settings);

  /**
   * Select masterpass payment.
   */
  void selectMasterpassPayment();

  /**
   * Initialize masterpass merchant.
   *
   * @param context the context
   */
  void initializeMasterpassMerchant(Context context);

  /**
   * Gets pairing id.
   *
   * @param checkoutData the checkout data
   */
  void getPairingId(HashMap<String, Object> checkoutData, Context context);

  /**
   * Remove pairing id.
   */
  void removePairingId();
}
