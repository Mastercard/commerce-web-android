package com.us.masterpass.merchantapp.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.login.RemoveLoginUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.fragment.MyAccountFragment;
import com.us.masterpass.merchantapp.presentation.presenter.MyAccountPresenter;

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
