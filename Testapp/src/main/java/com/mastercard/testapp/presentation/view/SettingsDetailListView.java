package com.mastercard.testapp.presentation.view;

import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.presentation.presenter.SettingsDetailPresenter;
import java.util.List;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public interface SettingsDetailListView extends View<SettingsDetailPresenter> {
  /**
   * Show setting detail.
   *
   * @param settings the settings
   */
  void showSettingDetail(List<SettingsVO> settings);

  /**
   * Go back.
   */
  void goBack();

  /**
   * Show alert.
   */
  void showAlert();
}
