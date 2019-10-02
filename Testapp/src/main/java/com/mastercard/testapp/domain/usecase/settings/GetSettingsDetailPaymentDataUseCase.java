package com.mastercard.testapp.domain.usecase.settings;

import android.support.annotation.NonNull;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.domain.usecase.base.UseCase;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 * <p>
 * Get detail for payment methods
 */
public class GetSettingsDetailPaymentDataUseCase extends
    UseCase<GetSettingsDetailPaymentDataUseCase.RequestValues, GetSettingsDetailPaymentDataUseCase.ResponseValue> {

  private final SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

  /**
   * Instantiates a new Get settings detail payment data use case.
   *
   * @param settingsSaveConfigurationSdk the settings save configuration sdk
   */
  public GetSettingsDetailPaymentDataUseCase(
      @NonNull SettingsSaveConfigurationSdk settingsSaveConfigurationSdk) {
    mSettingsSaveConfigurationSdk =
        checkNotNull(settingsSaveConfigurationSdk, "CLASS TO SAVE ON SP");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    boolean hasPairing = false;
    if (mSettingsSaveConfigurationSdk.getPairingId() != null) {
      hasPairing = mSettingsSaveConfigurationSdk.getPairingId().length() > 0;
    }
    ResponseValue responseValue =
        new ResponseValue(mSettingsSaveConfigurationSdk.getIsLogged(), hasPairing);
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
    private boolean isLogged;
    private boolean hasPairing;

    /**
     * Instantiates a new Response value.
     *
     * @param isLogged the is logged
     * @param hasPairing the has pairing
     */
    public ResponseValue(boolean isLogged, boolean hasPairing) {
      this.isLogged = checkNotNull(isLogged);
      this.hasPairing = checkNotNull(hasPairing);
    }

    /**
     * Is logged boolean.
     *
     * @return the boolean
     */
    public boolean isLogged() {
      return isLogged;
    }

    /**
     * Has pairing boolean.
     *
     * @return the boolean
     */
    public boolean hasPairing() {
      return hasPairing;
    }
  }
}