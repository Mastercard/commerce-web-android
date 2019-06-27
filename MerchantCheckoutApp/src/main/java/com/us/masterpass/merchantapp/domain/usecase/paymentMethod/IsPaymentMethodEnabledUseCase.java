package com.us.masterpass.merchantapp.domain.usecase.paymentMethod;

import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConstants;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;

/**
 * UseCase to check if Payment method is enabled in settings screen or not
 */

public class IsPaymentMethodEnabledUseCase extends
    UseCase<IsPaymentMethodEnabledUseCase.RequestValues, IsPaymentMethodEnabledUseCase.ResponseValue> {

  private final SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

  /**
   * Instantiates a new EnablePaymentMethod Use case.
   */
  public IsPaymentMethodEnabledUseCase(SettingsSaveConfigurationSdk settingsSaveConfigurationSdk) {
    this.mSettingsSaveConfigurationSdk = settingsSaveConfigurationSdk;
  }

  @Override protected void executeUseCase(RequestValues requestValues) {
    boolean isPaymentMethodEnabled =
        mSettingsSaveConfigurationSdk.configSwitch(SettingsSaveConstants.SDK_PAYMENT_METHOD);
    ResponseValue responseValue = new ResponseValue(isPaymentMethodEnabled);
    getUseCaseCallback().onSuccess(responseValue);
  }

  /**
   * The type Request Values.
   */
  public static final class RequestValues implements UseCase.RequestValues {

  }

  /**
   * The type Response Value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {
    private final boolean isPaymentMethodEnabled;

    public ResponseValue(boolean isPaymentMethodEnabled) {
      this.isPaymentMethodEnabled = isPaymentMethodEnabled;
    }

    public boolean isPaymentMethodEnabled() {
      return isPaymentMethodEnabled;
    }
  }
}
