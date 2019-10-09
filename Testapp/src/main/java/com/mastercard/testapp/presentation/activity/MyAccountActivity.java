package com.mastercard.testapp.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.mastercard.testapp.R;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.login.RemoveLoginUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.mastercard.testapp.presentation.AddFragmentToActivity;
import com.mastercard.testapp.presentation.fragment.MyAccountFragment;
import com.mastercard.testapp.presentation.presenter.MyAccountPresenter;

public class MyAccountActivity extends AppCompatActivity {

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    MyAccountFragment myAccountFragment =
        (MyAccountFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
    if (myAccountFragment == null) {
      myAccountFragment = MyAccountFragment.newInstance();
      SettingsSaveConfigurationSdk settingsSaveConfigurationSdk =
          SettingsSaveConfigurationSdk.getInstance(getApplicationContext());
      MyAccountPresenter myAccountPresenter = new MyAccountPresenter(UseCaseHandler.getInstance(),
          new RemoveLoginUseCase(getApplicationContext()), myAccountFragment,
          new IsPaymentMethodEnabledUseCase(settingsSaveConfigurationSdk),
          settingsSaveConfigurationSdk);
      myAccountFragment.setPresenter(myAccountPresenter);
      AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(), myAccountFragment,
          R.id.main_container);
    }
  }
}
