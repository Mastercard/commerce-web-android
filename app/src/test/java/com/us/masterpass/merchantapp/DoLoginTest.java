package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.domain.model.LoginObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.login.DoLoginUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.LoginPresenter;
import com.us.masterpass.merchantapp.presentation.view.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Test case related to @{@link LoginPresenter}
 */

public class DoLoginTest extends AbsTest{


    @Mock
    private LoginView mLoginView;


    @Captor
    private ArgumentCaptor<MasterpassDataSource.LoadDataLoginCallback> mLoadDataLoginCallbackCaptor;

    private LoginPresenter mLoginPresenter;

    @Before
    public void initPresenter () {
        super.initUseCases();
        mLoginPresenter = declarationLoginPresenter();
    }

    /**
     * Test case when login is attempted with empty user name and password.
     */
    @Test
    public void doLoginEmptyPasswordUsername() {
        mLoginPresenter.doLogin("", "", false);

        verify(mLoginView).showLoadingSpinner(false);
        verify(mLoginView).alertEmpty();
    }

    /**
     * Test case when Logging in with correct username and password.
     */
    @Test
    public void doLoginOk() {
        initLoginParams();
        mLoadDataLoginCallbackCaptor.getValue().onDataLogin(getLoginObject());
        verify(mLoginView).doLogin();
    }

    /**
     * Test case when login fails
     */
    @Test
    public void doLoginFail() {
        initLoginParams();
        mLoadDataLoginCallbackCaptor.getValue().onDataNotAvailable();
        verify(mLoginView).alertWrong();
    }


    @Test
    public void doEmptyTest() {
        presenter = mLoginPresenter;
        super.doEmptyMethodTest();
        mLoginPresenter.start();
    }

    private void initLoginParams() {
        String username = "jsmith";
        String password = "password";

        mLoginPresenter.doLogin(username, password, false);
        verify(mMasterpassExternalDataSource).doLogin(eq(username), eq(password), mLoadDataLoginCallbackCaptor.capture());
        verifyNoMoreInteractions(mMasterpassExternalDataSource);
    }

    private LoginObject getLoginObject() {
        LoginObject loginObject = new LoginObject();
        return loginObject;
    }

    private LoginPresenter declarationLoginPresenter() {

        UseCaseHandler useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
        DoLoginUseCase doLoginUseCase = new DoLoginUseCase(mMasterpassExternalDataSource, mContext);

        return new LoginPresenter(useCaseHandler, mLoginView, doLoginUseCase);
    }

}
