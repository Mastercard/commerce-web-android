package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.SettingsSaveInterface;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.SettingsConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;

/**
 * Test case for SettingsDetailPaymentPresenter related use cases.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SettingsListOptions.class})
public class DoSettingsDetailsPaymentTest extends AbsSettingsTest{

    private UseCaseHandler spyUseCaseHandler;

    @Mock
    private SettingsDetailPaymentListView sSettingsDetailPaymentListView;

    @Captor
    private ArgumentCaptor<SettingsSaveInterface.SaveItemsCallback> mSaveItemCallbackCaptor;

    @Captor
    private ArgumentCaptor<UseCase.UseCaseCallback> mUseCaseCallbackCallbackCaptor;

    @Captor
    private ArgumentCaptor<MasterpassDataSource.PairingIdCallback> mPairingIdCallBackCaptor;


    private SettingsDetailPaymentPresenter mSettingsDetailsPaymentPresenter;

    @Before
    public void init(){
        mSettingsDetailsPaymentPresenter = instantiateSettingsDetailsPresenter();
        presenter = mSettingsDetailsPaymentPresenter;
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
        mSettingsDetailsPaymentPresenter.start();
        mUseCaseCallbackCallbackCaptor.getValue().onSuccess(getResponseValues());
        verify(sSettingsDetailPaymentListView).showSettingDetail(any(Boolean.class), any(Boolean.class));
    }

    /**
     * Test case Loading Settings with Error
     */
    @Test
    public void doLoadSettingsDetailsError() {

        PowerMockito.doNothing().when(spyUseCaseHandler).execute(any(UseCase.class),
                any(GetSettingsDetailUseCase.RequestValues.class), mUseCaseCallbackCallbackCaptor.capture());
        mSettingsDetailsPaymentPresenter.start();
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
        mSettingsDetailsPaymentPresenter.saveSettings(getSettingsVoList());
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
        mSettingsDetailsPaymentPresenter.saveSettings(getSettingsVoList());
        mSaveItemCallbackCaptor.getValue().onSettingsNotSaved();
        verify(sSettingsDetailPaymentListView).showAlert();
    }

    @Test
    public void getPairingId() {
        mSettingsDetailsPaymentPresenter.getPairingId(getCheckoutData());
        verify(mMasterpassExternalDataSource).getPairingId(any(HashMap.class), mPairingIdCallBackCaptor.capture());
        mPairingIdCallBackCaptor.getValue().onPairing();
        verify(sSettingsDetailPaymentListView).updateCheckBox(true);
    }


    @Test
    public void getPairingIdWithError() {
        mSettingsDetailsPaymentPresenter.getPairingId(getCheckoutData());
        verify(mMasterpassExternalDataSource).getPairingId(any(HashMap.class), mPairingIdCallBackCaptor.capture());
        mPairingIdCallBackCaptor.getValue().onPairingError();
        verify(sSettingsDetailPaymentListView).updateCheckBox(false);
    }

    @Test
    public void removePairingId() {
        PowerMockito.doNothing().when(spyUseCaseHandler).execute(any(UseCase.class),
                any(GetSettingsDetailUseCase.RequestValues.class), mUseCaseCallbackCallbackCaptor.capture());
        mSettingsDetailsPaymentPresenter.removePairingId();
        mUseCaseCallbackCallbackCaptor.getValue().onSuccess(Matchers.any());
    }

    @Test
    public void removePairingIdWithError() {
        PowerMockito.doNothing().when(spyUseCaseHandler).execute(any(UseCase.class),
                any(GetSettingsDetailUseCase.RequestValues.class), mUseCaseCallbackCallbackCaptor.capture());
        mSettingsDetailsPaymentPresenter.removePairingId();
        mUseCaseCallbackCallbackCaptor.getValue().onError();
    }


    /*@Test
    public void doEmptyMethodTest() {
        super.doEmptyMethodTest();
        mSettingsDetailsPaymentPresenter.selectMasterpassPayment();
        mSettingsDetailsPaymentPresenter.initializeMasterpassMerchant(mContext);
    }*/

    private SettingsDetailPaymentPresenter instantiateSettingsDetailsPresenter() {
        spyUseCaseHandler = Mockito.spy(new UseCaseHandler(new BackgroundForTestUseCaseScheduler()));
        SaveSettingsUseCase saveSettingsUseCase = new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(mContext));
        GetSettingsDetailPaymentDataUseCase getSettingsDetailPaymentDataUseCase = new GetSettingsDetailPaymentDataUseCase(SettingsSaveConfigurationSdk.getInstance(mContext));
        SaveMasterpassPaymentMethodUseCase saveMasterpassPaymentMethodUseCase = new SaveMasterpassPaymentMethodUseCase(SettingsSaveConfigurationSdk.getInstance(mContext));
        RemovePairingIdUseCase removePairingIdUseCase = new RemovePairingIdUseCase(SettingsSaveConfigurationSdk.getInstance(mContext));
        GetPairingIdUseCase getPairingIdUseCase = new GetPairingIdUseCase(mMasterpassExternalDataSource);
        return new SettingsDetailPaymentPresenter(spyUseCaseHandler, sSettingsDetailPaymentListView, getSettingsDetailPaymentDataUseCase,
                saveSettingsUseCase, saveMasterpassPaymentMethodUseCase, getPairingIdUseCase,
                removePairingIdUseCase, SettingsConstants.ITEM_CARDS);
    }

    private GetSettingsDetailPaymentDataUseCase.ResponseValue getResponseValues() {
        GetSettingsDetailPaymentDataUseCase.ResponseValue responseValue =
                new GetSettingsDetailPaymentDataUseCase.ResponseValue(true, true);
        return responseValue;
    }

    private HashMap<String,Object> getCheckoutData() {
        return new HashMap<>();
    }
}
