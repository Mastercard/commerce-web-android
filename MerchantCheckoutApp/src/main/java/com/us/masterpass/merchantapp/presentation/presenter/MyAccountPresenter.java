package com.us.masterpass.merchantapp.presentation.presenter;

import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.model.LoginObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.login.RemoveLoginUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.MyAccountPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.MyAccountView;

public class MyAccountPresenter implements MyAccountPresenterInterface {

  private MyAccountView myAccountView;
  private final RemoveLoginUseCase mRemoveLogin;
  private final IsPaymentMethodEnabledUseCase mIsPaymentMethodEnabledUseCase;
  private final UseCaseHandler mUseCaseHandler;
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
