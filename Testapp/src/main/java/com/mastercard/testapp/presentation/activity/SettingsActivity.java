package com.mastercard.testapp.presentation.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.mastercard.testapp.R;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.settings.GetSettingsUseCase;
import com.mastercard.testapp.domain.usecase.settings.SaveSettingsUseCase;
import com.mastercard.testapp.presentation.AddFragmentToActivity;
import com.mastercard.testapp.presentation.fragment.SettingsFragment;
import com.mastercard.testapp.presentation.presenter.SettingsPresenter;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Settings activity manage all settings options
 */
public class SettingsActivity extends AppCompatActivity {

  private SettingsPresenter mSettingsPresenter;

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    SettingsFragment settingsFragment =
        (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
    if (settingsFragment == null) {
      settingsFragment = SettingsFragment.newInstance();
      AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(), settingsFragment,
          R.id.main_container);
    }

    mSettingsPresenter = new SettingsPresenter(UseCaseHandler.getInstance(), settingsFragment,
        new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(getApplicationContext())),
        new GetSettingsUseCase(getApplicationContext()));
  }
}
