package com.mastercard.testapp.presentation.presenter;

import android.support.annotation.NonNull;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.login.DoLoginUseCase;
import com.mastercard.testapp.presentation.presenter.base.LoginPresenterInterface;
import com.mastercard.testapp.presentation.view.LoginView;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public class LoginPresenter implements LoginPresenterInterface {

  private final DoLoginUseCase mDoLogin;
  private final UseCaseHandler mUseCaseHandler;
  private LoginView mLoginView;

  /**
   * Instantiates a new Login presenter.
   *
   * @param useCaseHandler the use case handler
   * @param loginView the login view
   * @param doLoginUseCase the do login use case
   */
  public LoginPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull LoginView loginView,
      @NonNull DoLoginUseCase doLoginUseCase) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "NEVER MUST BE NULL HANDLER");
    mLoginView = checkNotNull(loginView, "NEVER MUST BE NULL VIEW");
    mDoLogin = checkNotNull(doLoginUseCase, "NEVER MUST BE NULL USE CASE");
    mLoginView.setPresenter(this);
  }

  @Override public void start() {
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void doLogin(String username, String password, boolean forceSaveConfig) {
    mLoginView.showLoadingSpinner(true);
    mUseCaseHandler.execute(mDoLogin,
        new DoLoginUseCase.RequestValues(username, password, forceSaveConfig),
        new UseCase.UseCaseCallback<DoLoginUseCase.ResponseValue>() {
          @Override public void onSuccess(DoLoginUseCase.ResponseValue response) {
            mLoginView.showLoadingSpinner(false);
            if (response.isLoginSuccess()) {
              mLoginView.doLogin();
            } else {
              mLoginView.alertWrong();
            }
          }

          @Override public void onError() {
            mLoginView.showLoadingSpinner(false);
            mLoginView.alertEmpty();
          }
        });
  }
}
