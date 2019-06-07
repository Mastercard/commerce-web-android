package com.us.masterpass.merchantapp.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.fragment.SettingsFragment;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsPresenter;

/**
 * Created by Sebastian Farias on 09-10-17.
 */
public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        SettingsFragment settingsFragment = (SettingsFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (settingsFragment == null) {
            settingsFragment = SettingsFragment.newInstance();
            AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(),
                    settingsFragment, R.id.main_container);
        }

        new SettingsPresenter(
                UseCaseHandler.getInstance(),
                settingsFragment,
                new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(getApplicationContext())),
                new GetSettingsUseCase(getApplicationContext())
        );
    }
}
