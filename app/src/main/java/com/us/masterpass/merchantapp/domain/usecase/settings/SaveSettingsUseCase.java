package com.us.masterpass.merchantapp.domain.usecase.settings;

import androidx.annotation.NonNull;

import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.SettingsSaveInterface;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;

import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class SaveSettingsUseCase extends UseCase<SaveSettingsUseCase.RequestValues, SaveSettingsUseCase.ResponseValue> {

    private final SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

    /**
     * Instantiates a new Save settings use case.
     *
     * @param settingsSaveConfigurationSdk the settings save configuration sdk
     */
    public SaveSettingsUseCase(@NonNull SettingsSaveConfigurationSdk settingsSaveConfigurationSdk) {
        mSettingsSaveConfigurationSdk = checkNotNull(settingsSaveConfigurationSdk, "CLASS TO SAVE ON SP");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        List<SettingsVO> optionsSelected = getRequestValues().getSettings();
        SettingsListOptions.saveSettings(optionsSelected,
                mSettingsSaveConfigurationSdk,
                new SettingsSaveInterface.SaveItemsCallback() {
                    @Override
                    public void onSettingsSaved() {
                        getUseCaseCallback().onSuccess(null);
                    }

                    @Override
                    public void onSettingsNotSaved() {
                        getUseCaseCallback().onError();
                    }
                });
    }

    /**
     * The type Request values.
     */
    public static final class RequestValues implements UseCase.RequestValues {
        private final List<SettingsVO> mSettings;

        /**
         * Instantiates a new Request values.
         *
         * @param settings the settings
         */
        public RequestValues(@NonNull List<SettingsVO> settings) {
            mSettings = checkNotNull(settings, "SETTINGS TO CHECK");
        }

        /**
         * Gets settings.
         *
         * @return the settings
         */
        public List<SettingsVO> getSettings() {
            return mSettings;
        }
    }

    /**
     * The type Response value.
     */
    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<SettingsVO> mSettings;

        /**
         * Instantiates a new Response value.
         *
         * @param settings the settings
         */
        public ResponseValue(@NonNull List<SettingsVO> settings) {
            mSettings = checkNotNull(settings, "SETTINGS CLASS MUST EXIST");
        }

        /**
         * Gets settings.
         *
         * @return the settings
         */
        public List<SettingsVO> getSettings() {
            return mSettings;
        }
    }
}