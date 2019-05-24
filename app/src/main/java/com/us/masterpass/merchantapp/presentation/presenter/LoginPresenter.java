package com.us.masterpass.merchantapp.presentation.presenter;

import androidx.annotation.NonNull;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.login.DoLoginUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.LoginPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.LoginView;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public class LoginPresenter implements LoginPresenterInterface {

    private LoginView mLoginView;
    private final DoLoginUseCase mDoLogin;
    private final UseCaseHandler mUseCaseHandler;

    /**
     * Instantiates a new Login presenter.
     *
     * @param useCaseHandler the use case handler
     * @param loginView      the login view
     * @param doLoginUseCase the do login use case
     */
    public LoginPresenter(@NonNull UseCaseHandler useCaseHandler,
                          @NonNull LoginView loginView,
                          @NonNull DoLoginUseCase doLoginUseCase) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "NEVER MUST BE NULL HANDLER");
        mLoginView = checkNotNull(loginView, "NEVER MUST BE NULL VIEW");
        mDoLogin = checkNotNull(doLoginUseCase, "NEVER MUST BE NULL USE CASE");
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {
        //start
    }

    @Override
    public void resume() {
        //resume
    }

    @Override
    public void pause() {
        //pause
    }

    @Override
    public void destroy() {
        //destroy
    }

    @Override
    public void doLogin(String username, String password, boolean forceSaveConfig) {
        mLoginView.showLoadingSpinner(true);
        mUseCaseHandler.execute(mDoLogin,
                new DoLoginUseCase.RequestValues(username, password, forceSaveConfig),
                new UseCase.UseCaseCallback<DoLoginUseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(DoLoginUseCase.ResponseValue response) {
                        mLoginView.showLoadingSpinner(false);
                        if (response.isLoginSuccess()){
                            mLoginView.doLogin();
                        } else {
                            mLoginView.alertWrong();
                        }
                    }

                    @Override
                    public void onError() {
                        mLoginView.showLoadingSpinner(false);
                        mLoginView.alertEmpty();
                    }
                });
    }
}
