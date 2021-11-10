package com.mastercard.testapp.presentation.presenter;

import androidx.annotation.NonNull;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.model.LoginObject;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.login.RemoveLoginUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.mastercard.testapp.presentation.presenter.base.MyAccountPresenterInterface;
import com.mastercard.testapp.presentation.view.MyAccountView;

public class MyAccountPresenter implements MyAccountPresenterInterface {

  private final RemoveLoginUseCase mRemoveLogin;
  private final IsPaymentMethodEnabledUseCase mIsPaymentMethodEnabledUseCase;
  private final UseCaseHandler mUseCaseHandler;
  private MyAccountView myAccountView;
  private SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

  public MyAccountPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull RemoveLoginUseCase removeLoginUseCase, @NonNull MyAccountView accountView,
      @NonNull IsPaymentMethodEnabledUseCase isPaymentMethodEnabledUseCase,
      @NonNull SettingsSaveConfigurationSdk settingsSaveConfigurationSdk) {
    mUseCaseHandler = useCaseHandler;
    mRemoveLogin = removeLoginUseCase;
    myAccountView = accountView;
    mIsPaymentMethodEnabledUseCase = isPaymentMethodEnabledUseCase;
    mSettingsSaveConfigurationSdk = settingsSaveConfigurationSdk;
  }

  @Override public void start() {
    // No use for this yet.
  }

  @Override public void resume() {
    // No use for this yet.
  }

  @Override public void pause() {
    // No use for this yet.
  }

  @Override public void destroy() {
    // No use for this yet.
  }

  public LoginObject getSavedLoggedInData() {
    return mSettingsSaveConfigurationSdk.getLoggedInData();
  }

  @Override public void logoutButton() {
    myAccountView.showConfirmLogoutDialog();
  }

  @Override public void logout() {
    mUseCaseHandler.execute(mRemoveLogin, new RemoveLoginUseCase.RequestValues(),
        new UseCase.UseCaseCallback<RemoveLoginUseCase.ResponseValue>() {
          @Override public void onSuccess(RemoveLoginUseCase.ResponseValue response) {
            MasterpassSdkCoordinator.savePairingTransactionId(null);
            MasterpassSdkCoordinator.savePairingId(null);
            myAccountView.loadLoginActivity();
          }

          @Override public void onError() {
            // No use for this yet.
          }
        });
  }

  @Override public void loadItemsActivity() {
    myAccountView.loadItemsActivity();
  }

  @Override public void loadPaymentMethod() {
    myAccountView.showPaymentMethodScreen();
  }

  @Override public void isPaymentMethodEnabled() {
    mUseCaseHandler.execute(mIsPaymentMethodEnabledUseCase,
        new IsPaymentMethodEnabledUseCase.RequestValues(),
        new UseCase.UseCaseCallback<IsPaymentMethodEnabledUseCase.ResponseValue>() {
          @Override public void onSuccess(IsPaymentMethodEnabledUseCase.ResponseValue response) {
            myAccountView.setPaymentMethodVisibility(response.isPaymentMethodEnabled());
          }

          @Override public void onError() {
            // No use for this yet.
          }
        });
  }
}
