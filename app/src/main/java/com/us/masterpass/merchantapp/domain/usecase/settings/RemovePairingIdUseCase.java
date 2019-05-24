package com.us.masterpass.merchantapp.domain.usecase.settings;

import androidx.annotation.NonNull;

import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class RemovePairingIdUseCase extends UseCase<RemovePairingIdUseCase.RequestValues, RemovePairingIdUseCase.ResponseValue> {

    private final SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

    /**
     * Instantiates a new Remove pairing id use case.
     *
     * @param settingsSaveConfigurationSdk the settings save configuration sdk
     */
    public RemovePairingIdUseCase(@NonNull SettingsSaveConfigurationSdk settingsSaveConfigurationSdk) {
        mSettingsSaveConfigurationSdk = checkNotNull(settingsSaveConfigurationSdk, "CLASS TO SAVE ON SP");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        mSettingsSaveConfigurationSdk.removePairingId();
        getUseCaseCallback().onSuccess(null);
    }

    /**
     * The type Request values.
     */
    public static final class RequestValues implements UseCase.RequestValues {

    }

    /**
     * The type Response value.
     */
    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}