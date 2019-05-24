package com.us.masterpass.merchantapp;


import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.SettingsSaveInterface;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.SettingsConstants;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPresenter;
import com.us.masterpass.merchantapp.presentation.view.SettingsDetailListView;

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

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SettingsListOptions.class})
public class DoSettingsDetailsTest extends AbsSettingsTest{

    private UseCaseHandler spyUseCaseHandler;

    @Mock
    private SettingsDetailListView mSettingsDetailedListView;

    @Captor
    private ArgumentCaptor<SettingsSaveInterface.SaveItemsCallback> mSaveItemCallbackCaptor;

    @Captor
    private ArgumentCaptor<UseCase.UseCaseCallback> mUseCaseCallbackCallbackCaptor;


    private SettingsDetailPresenter mSettingsDetailsPresenter;

    @Before
    public void init(){
        mSettingsDetailsPresenter = instantiateSettingsDetailsPresenter();
        presenter = mSettingsDetailsPresenter;
        PowerMockito.mockStatic(SettingsListOptions.class);
        PowerMockito.when(SettingsListOptions.settingsList(mContext)).thenReturn(getSettingsVoList());
    }

    /**
     * Test case Loading Settings
     */
    @Test
    public void doLoadSettingsDetails() {

        PowerMockito.doNothing().when(spyUseCaseHandler).execute(any(UseCase.class),
                any(GetSettingsDetailUseCase.RequestValues.class), mUseCaseCallbackCallbackCaptor.capture());
        mSettingsDetailsPresenter.start();
        mUseCaseCallbackCallbackCaptor.getValue().onSuccess(getResponseValues());
        verify(mSettingsDetailedListView).showSettingDetail(anyList());
    }

    /**
     * Test case Loading Settings with Error
     */
    @Test
    public void doLoadSettingsDetailsError() {

        PowerMockito.doNothing().when(spyUseCaseHandler).execute(any(UseCase.class),
                any(GetSettingsDetailUseCase.RequestValues.class), mUseCaseCallbackCallbackCaptor.capture());
        mSettingsDetailsPresenter.start();
        mUseCaseCallbackCallbackCaptor.getValue().onError();
//        verifyZeroInteractions(mSettingsDetailedListView);
    }

    /**
     * Test case Saving Settings
     */
    @Test
    public void doSaveSettingsSwitch() throws Exception {

        PowerMockito.doNothing().when(SettingsListOptions.class,"saveSettings",anyList(),
                any(SettingsSaveConfigurationSdk.class),
                mSaveItemCallbackCaptor.capture());
        mSettingsDetailsPresenter.saveSettings(getSettingsVoList());
        mSaveItemCallbackCaptor.getValue().onSettingsSaved();
    }

    /**
     * Test case Saving Settings with Error
     */
    @Test
    public void doSaveSettingsSwitchWithError() throws Exception {

        PowerMockito.doNothing().when(SettingsListOptions.class,"saveSettings",anyList(),
                any(SettingsSaveConfigurationSdk.class),
                mSaveItemCallbackCaptor.capture());
        mSettingsDetailsPresenter.saveSettings(getSettingsVoList());
        mSaveItemCallbackCaptor.getValue().onSettingsNotSaved();
        verify(mSettingsDetailedListView).showAlert();
    }

    private SettingsDetailPresenter instantiateSettingsDetailsPresenter() {
        spyUseCaseHandler = Mockito.spy(new UseCaseHandler(new BackgroundForTestUseCaseScheduler()));
        SaveSettingsUseCase saveSettingsUseCase = new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(mContext));
        GetSettingsDetailUseCase getSettingsUseCase = new GetSettingsDetailUseCase(SettingsSaveConfigurationSdk.getInstance(mContext));
        return new SettingsDetailPresenter(spyUseCaseHandler, mSettingsDetailedListView, getSettingsUseCase, saveSettingsUseCase,
                SettingsConstants.ITEM_CARDS);
    }
    private GetSettingsDetailUseCase.ResponseValue getResponseValues() {
        GetSettingsDetailUseCase.ResponseValue responseValue =
                new GetSettingsDetailUseCase.ResponseValue(getSettingsVoList());
        return responseValue;
    }

    @Test
    public void doEmptyMethodTest() {
        super.doEmptyMethodTest();
        mSettingsDetailsPresenter.selectMasterpassPayment();
        mSettingsDetailsPresenter.initializeMasterpassMerchant(mContext);
        mSettingsDetailsPresenter.getPairingId(getCheckoutData());
        mSettingsDetailsPresenter.removePairingId();

    }

    private HashMap<String,Object> getCheckoutData() {
        return new HashMap<>();
    }
}
