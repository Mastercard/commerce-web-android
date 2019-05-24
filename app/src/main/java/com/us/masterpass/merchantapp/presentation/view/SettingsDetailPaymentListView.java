package com.us.masterpass.merchantapp.presentation.view;

import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPaymentPresenter;

import java.util.List;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public interface SettingsDetailPaymentListView extends View<SettingsDetailPaymentPresenter> {
    /**
     * Show setting detail.
     *
     * @param settings the settings
     */
    void showSettingDetail(List<SettingsVO> settings);

    /**
     * Show setting detail.
     *
     * @param isLogged     the is logged
     * @param hasPairingId the has pairing id
     */
    void showSettingDetail(boolean isLogged, boolean hasPairingId);

    /**
     * Go back.
     */
    void goBack();

    /**
     * Show alert.
     */
    void showAlert();

    /**
     * Load login activity.
     */
    void loadLoginActivity();

    /**
     * Enable button masterpass payment.
     */
    void enableButtonMasterpassPayment();

    /**
     * Update check box.
     *
     * @param enable the enable
     */
    void updateCheckBox(boolean enable);
}
