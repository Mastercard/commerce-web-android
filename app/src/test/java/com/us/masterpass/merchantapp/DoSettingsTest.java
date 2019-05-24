package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.SettingsSaveInterface;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsPresenter;
import com.us.masterpass.merchantapp.presentation.view.SettingsListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;

/**
 * Test case related to @{@link SettingsPresenter}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SettingsListOptions.class})
public class DoSettingsTest extends AbsSettingsTest{

    GetSettingsUseCase getSettingsUseCase;

    UseCaseHandler spyUseCaseHandler;

    @Mock
    private SettingsListView mSettingsListView;

    @Captor
    private ArgumentCaptor<SettingsSaveInterface.SaveItemsCallback> mSaveItemCallbackCaptor;

    @Captor
    private ArgumentCaptor<UseCase.UseCaseCallback> mUseCaseCallbackCallbackCaptor;


    private SettingsPresenter mSettingsPresenter;

    @Before
    public void init(){
        mSettingsPresenter = instantiateSettingsPresenter();
        presenter = mSettingsPresenter;
        PowerMockito.mockStatic(SettingsListOptions.class);
        PowerMockito.when(SettingsListOptions.settingsList(mContext)).thenReturn(getSettingsVoList());
    }

    /**
     * Test case Loading Settings
     */
    @Test
    public void doLoadSettings() throws Exception {

        PowerMockito.doNothing().when(spyUseCaseHandler).execute(any(UseCase.class),
                any(GetSettingsUseCase.RequestValues.class), mUseCaseCallbackCallbackCaptor.capture());
        mSettingsPresenter.start();
        mUseCaseCallbackCallbackCaptor.getValue().onSuccess(getResponseValues());
        verify(mSettingsListView).showSettings(anyList(), any(Boolean.class));
    }

    /**
     * Test case Loading Settings with Error
     */
    @Test
    public void doLoadSettingsWithError() throws Exception {

        PowerMockito.doNothing().when(spyUseCaseHandler).execute(any(UseCase.class),
                any(GetSettingsUseCase.RequestValues.class), mUseCaseCallbackCallbackCaptor.capture());
        mSettingsPresenter.start();
        mUseCaseCallbackCallbackCaptor.getValue().onError();
    }


    /**
     * Test case Saving Settings
     */
    @Test
    public void doSaveSettingsSwitch() throws Exception {

        PowerMockito.doNothing().when(SettingsListOptions.class,"saveSettings",anyList(),
                any(SettingsSaveConfigurationSdk.class),
                mSaveItemCallbackCaptor.capture());
        mSettingsPresenter.saveSettingsSwitch(getSettingsVo());
        mSaveItemCallbackCaptor.getValue().onSettingsSaved();
    }

    /**
     * Test case Saving Settings
     */
    @Test
    public void doSaveSettingsSwitchWithError() throws Exception {

        PowerMockito.doNothing().when(SettingsListOptions.class,"saveSettings",anyList(),
                any(SettingsSaveConfigurationSdk.class),
                mSaveItemCallbackCaptor.capture());
        mSettingsPresenter.saveSettingsSwitch(getSettingsVo());
        mSaveItemCallbackCaptor.getValue().onSettingsNotSaved();
        verify(mSettingsListView).loadLoginActivity();
    }


    private SettingsPresenter instantiateSettingsPresenter() {
        spyUseCaseHandler = Mockito.spy(new UseCaseHandler(new BackgroundForTestUseCaseScheduler()));
        SaveSettingsUseCase saveSettingsUseCase = new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(mContext));
        getSettingsUseCase = new GetSettingsUseCase(mContext);
        return new SettingsPresenter(spyUseCaseHandler, mSettingsListView, saveSettingsUseCase, getSettingsUseCase);
    }

    public GetSettingsUseCase.ResponseValue getResponseValues() {
        GetSettingsUseCase.ResponseValue responseValue =
                new GetSettingsUseCase.ResponseValue(getSettingsVoList(), true);
        return responseValue;
    }

}
