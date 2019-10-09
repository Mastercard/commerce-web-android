package com.mastercard.testapp.domain.usecase.settings;

import android.support.annotation.NonNull;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.domain.usecase.base.UseCase;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Remove pairing id from saved settings
 */
public class RemovePairingIdUseCase
    extends UseCase<RemovePairingIdUseCase.RequestValues, RemovePairingIdUseCase.ResponseValue> {

  private final SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

  /**
   * Instantiates a new Remove pairing id use case.
   *
   * @param settingsSaveConfigurationSdk the settings save configuration sdk
   */
  public RemovePairingIdUseCase(
      @NonNull SettingsSaveConfigurationSdk settingsSaveConfigurationSdk) {
    mSettingsSaveConfigurationSdk =
        checkNotNull(settingsSaveConfigurationSdk, "CLASS TO SAVE ON SP");
  }

  @Override protected void executeUseCase(final RequestValues values) {
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