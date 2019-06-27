package com.us.masterpass.merchantapp;

import android.content.Context;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.login.DoLoginUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.LoginPresenter;
import com.us.masterpass.merchantapp.presentation.view.LoginView;
import com.us.masterpass.merchantapp.utils.BackgroundForTestUseCaseScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by sebastianfarias on 11/20/17.
 *
 * Test created for {@link LoginPresenter}
 */

public class LoginPresenterTest {

    @Mock
    private MasterpassExternalDataSource mMasterpassExternalDataSource;

    @Mock
    private LoginView mLoginView;

    @Mock
    private Context mContext;

    @Captor
    private ArgumentCaptor<MasterpassDataSource.LoadDataLoginCallback> mLoadDataLoginCallbackCaptor;

    @Before
    public void setDoLoginUseCaseMock() {
        MockitoAnnotations.initMocks(this);
    }

    private LoginPresenter mLoginPresenter;

    @Test
    public void doLoginEmptyPasswordUsername() {
        mLoginPresenter = declarationLoginPresenter();
        mLoginPresenter.doLogin("", "", false);

        verify(mLoginView).showLoadingSpinner(false);
        verify(mLoginView).alertEmpty();
    }

    @Test
    public void doLoginOk() {
        String username = "jsmith";
        String password = "password";

        mLoginPresenter = declarationLoginPresenter();
        mLoginPresenter.doLogin(username, password, false);
        verify(mMasterpassExternalDataSource).doLogin(eq(username), eq(password), mLoadDataLoginCallbackCaptor.capture());
        verifyNoMoreInteractions(mMasterpassExternalDataSource);
    }

    private LoginPresenter declarationLoginPresenter() {

        UseCaseHandler useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
        DoLoginUseCase doLoginUseCase = new DoLoginUseCase(mMasterpassExternalDataSource, mContext);

        return new LoginPresenter(useCaseHandler, mLoginView, doLoginUseCase);
    }

}
