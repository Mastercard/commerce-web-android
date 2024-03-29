package com.mastercard.testapp.domain.usecase.settings;

import androidx.annotation.NonNull;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Save new configuration for payment method
 */
public class SaveMasterpassPaymentMethodUseCase extends
    UseCase<SaveMasterpassPaymentMethodUseCase.RequestValues, SaveMasterpassPaymentMethodUseCase.ResponseValue> {

  private final SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

  /**
   * Instantiates a new Save masterpass payment method use case.
   *
   * @param settingsSaveConfigurationSdk the settings save configuration sdk
   */
  public SaveMasterpassPaymentMethodUseCase(
      @NonNull SettingsSaveConfigurationSdk settingsSaveConfigurationSdk) {
    mSettingsSaveConfigurationSdk =
        checkNotNull(settingsSaveConfigurationSdk, "CLASS TO SAVE ON SP");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    if (mSettingsSaveConfigurationSdk.getIsLogged()) {

    } else {
      getUseCaseCallback().onError();
    }
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