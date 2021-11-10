package com.mastercard.testapp.presentation.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.mastercard.testapp.R;
import com.mastercard.testapp.data.external.MasterpassExternalDataSource;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.login.DoLoginUseCase;
import com.mastercard.testapp.presentation.AddFragmentToActivity;
import com.mastercard.testapp.presentation.fragment.LoginFragment;
import com.mastercard.testapp.presentation.presenter.LoginPresenter;

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

    LoginFragment loginFragment =
        (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
    if (loginFragment == null) {
      loginFragment = LoginFragment.newInstance();
      loginFragment.setArguments(getIntent().getExtras());
      AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(), loginFragment,
          R.id.main_container);
    }

    mLoginPresenter = new LoginPresenter(UseCaseHandler.getInstance(), loginFragment,
        new DoLoginUseCase(MasterpassExternalDataSource.getInstance(), getApplicationContext()));
  }
}
