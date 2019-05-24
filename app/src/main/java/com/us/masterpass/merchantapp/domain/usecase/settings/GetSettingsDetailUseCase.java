package com.us.masterpass.merchantapp.domain.usecase.settings;

import androidx.annotation.NonNull;

import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;

import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class GetSettingsDetailUseCase extends UseCase<GetSettingsDetailUseCase.RequestValues, GetSettingsDetailUseCase.ResponseValue> {

    private final SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

    /**
     * Instantiates a new Get settings detail use case.
     *
     * @param settingsSaveConfigurationSdk the settings save configuration sdk
     */
    public GetSettingsDetailUseCase(@NonNull SettingsSaveConfigurationSdk settingsSaveConfigurationSdk) {
        mSettingsSaveConfigurationSdk = checkNotNull(settingsSaveConfigurationSdk, "CLASS TO SAVE ON SP");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        String optionSelected = getRequestValues().getOptionSelected();
        ResponseValue responseValue = new
                ResponseValue(SettingsListOptions.settingsDetail(
                        optionSelected, mSettingsSaveConfigurationSdk
        ));
        getUseCaseCallback().onSuccess(responseValue);
    }

    /**
     * The type Request values.
     */
    public static final class RequestValues implements UseCase.RequestValues {
        private final String mOptionSelected;

        /**
         * Instantiates a new Request values.
         *
         * @param optionSelected the option selected
         */
        public RequestValues(@NonNull String optionSelected) {
            mOptionSelected = checkNotNull(optionSelected, "completedTask cannot be null!");
        }

        /**
         * Gets option selected.
         *
         * @return the option selected
         */
        public String getOptionSelected() {
            return mOptionSelected;
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