package com.us.masterpass.merchantapp.presentation.view;

import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsPresenter;

import java.util.List;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public interface SettingsListView extends View<SettingsPresenter>{
    /**
     * Show settings.
     *
     * @param settings the settings
     */
    void showSettings(List<SettingsVO> settings);

    /**
     * Show setting detail.
     *
     * @param title          the title
     * @param optionSelected the option selected
     */
    void showSettingDetail(String title, String optionSelected);

    /**
     * Save setting switch.
     *
     * @param settingsVO the settings vo
     */
    void saveSettingSwitch(SettingsVO settingsVO);

}
