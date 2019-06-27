package com.us.masterpass.merchantapp.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.login.DoLoginUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.fragment.LoginFragment;
import com.us.masterpass.merchantapp.presentation.presenter.LoginPresenter;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Manage login actions
 */
public class LoginActivity extends AppCompatActivity {

    private LoginPresenter mLoginPresenter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        onNewIntent(getIntent());

        LoginFragment loginFragment = (LoginFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            loginFragment.setArguments(getIntent().getExtras());
            AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(),
                    loginFragment, R.id.main_container);
        }

        mLoginPresenter = new LoginPresenter(
                UseCaseHandler.getInstance(),
                loginFragment,
                new DoLoginUseCase(MasterpassExternalDataSource.getInstance(),
                        getApplicationContext())
        );
    }

}
