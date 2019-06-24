package com.us.masterpass.merchantapp.presentation.view;

import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPresenter;
import java.util.List;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public interface SettingsDetailListView extends View<SettingsDetailPresenter>{
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
