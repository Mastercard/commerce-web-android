package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConstants;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.GetPairingIdUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailPaymentDataUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.RemovePairingIdUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveMasterpassPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPaymentPresenter;
import com.us.masterpass.merchantapp.presentation.view.SettingsDetailPaymentListView;
import com.us.masterpass.merchantapp.utils.BackgroundForTestUseCaseScheduler;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by akhildixit on 12/7/17.
 *
 * Test created for {@link SettingsDetailPaymentPresenter}
 */

public class SettingsDetailPaymentPresenterTest {
    @Mock
    private SettingsDetailPaymentListView mSettingsDetailPaymentView;
    @Mock
    private SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;
    @Mock
    private MasterpassExternalDataSource mMasterpassExternalDataSource;
    @Mock
    private List<SettingsVO> mSettings;
    @Mock
    HashMap<String,Object>mHashMap;
    @Mock
    private SettingsVO settingsVO;
    @Captor
    private ArgumentCaptor<GetSettingsDetailPaymentDataUseCase.UseCaseCallback>mGetSettingsDetailPaymentCallBack;
    @Captor
    private ArgumentCaptor<MasterpassDataSource.PairingIdCallback>mPairingIdCallbackArgumentCaptor;
    private SettingsDetailPaymentPresenter mSettingsDetailPayment;

    @Before
    public void setSettingsDetailPaymentUseCase(){
        MockitoAnnotations.initMocks(this);
        SettingsVO dummySetting = new SettingsVO();
        dummySetting.setName("SUPPRESS SHIPPING");
        dummySetting.setType("LANGUAGE");
        dummySetting.setSelected(true);
        dummySetting.setSaveOption("en-US");
        mSettings.add(dummySetting);
        when(settingsVO.getType()).thenReturn("LANGUAGE");
        when(mSettings.size()).thenReturn(1);
        when(mSettingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_LANG, "en-US")).thenReturn(true);
        when(mSettingsSaveConfigurationSdk.getIsLogged()).thenReturn(false);
    }
    @Test
    public void loadSettings(){
        mSettingsDetailPayment=declarationLoadSettingsPaymentPresenter();
        mSettingsDetailPayment.loadSettingsDetail();
        verify(mSettingsDetailPaymentView).setPresenter(mSettingsDetailPayment);
        verify(mSettingsDetailPaymentView).showSettingDetail(anyBoolean(),anyBoolean());

    }
    @Test
    public void saveSettings(){
        SettingsVO dummySetting = new SettingsVO();
        dummySetting.setName("English");
        dummySetting.setType("LANGUAGE");
        dummySetting.setSelected(true);
        dummySetting.setSaveOption("en-US");
        mSettings.add(dummySetting);
        when(mSettings.get(0)).thenReturn(dummySetting);
        mSettingsDetailPayment = declarationLoadSettingsPaymentPresenter();
        mSettingsDetailPayment.saveSettings(mSettings);
        verify(mSettingsDetailPaymentView).setPresenter(mSettingsDetailPayment);
        verify(mSettingsDetailPaymentView).goBack();
    }
    @Test
    public void saveSettingsonError(){
        SettingsVO dummySetting = new SettingsVO();
        dummySetting.setName("ENABLE EXPRESS CHECKOUT");
        dummySetting.setType("SWITCH");
        dummySetting.setSelected(true);
        dummySetting.setSaveOption("en-US");
        mSettings.add(dummySetting);
        when(mSettings.get(0)).thenReturn(dummySetting);
        mSettingsDetailPayment = declarationLoadSettingsPaymentPresenter();
        mSettingsDetailPayment .saveSettings(mSettings);
        verify(mSettingsDetailPaymentView).setPresenter(mSettingsDetailPayment);
        verify(mSettingsDetailPaymentView).showAlert();

    }

    /*@Test
    public void getPairingID(){
        mSettingsDetailPayment=declarationLoadSettingsPaymentPresenter();
        mSettingsDetailPayment.getPairingId(mHashMap);
        verify(mMasterpassExternalDataSource).getPairingId(eq(mHashMap),mPairingIdCallbackArgumentCaptor.capture());
        MasterpassDataSource.PairingIdCallback callback=mPairingIdCallbackArgumentCaptor.getAllValues().get(0);
        try{
            callback.onPairing();
        }catch (InvalidUseOfMatchersException ex){

        }
        verify(mSettingsDetailPaymentView).updateCheckBox(anyBoolean());
    }
    @Test
    public void getPairingIdOnError(){
        mSettingsDetailPayment=declarationLoadSettingsPaymentPresenter();
        mSettingsDetailPayment.getPairingId(mHashMap);
        verify(mMasterpassExternalDataSource).getPairingId(eq(mHashMap),mPairingIdCallbackArgumentCaptor.capture());
        MasterpassDataSource.PairingIdCallback callback=mPairingIdCallbackArgumentCaptor.getAllValues().get(0);
        try{
            callback.onPairingError();
        }catch (InvalidUseOfMatchersException ex){
            verify(mSettingsDetailPaymentView).updateCheckBox(anyBoolean());
        }
    }*/
    @Test
    public void removePairingId(){
        mSettingsDetailPayment=declarationLoadSettingsPaymentPresenter();
        mSettingsDetailPayment.removePairingId();
        verify(mSettingsSaveConfigurationSdk).removePairingId();
        verify(mSettingsDetailPaymentView).updateCheckBox(anyBoolean());
    }

    private SettingsDetailPaymentPresenter declarationLoadSettingsPaymentPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
        GetSettingsDetailPaymentDataUseCase getSettingsDetailPaymentDataUseCase=new GetSettingsDetailPaymentDataUseCase(mSettingsSaveConfigurationSdk);
        SaveSettingsUseCase saveSettingsUseCase=new SaveSettingsUseCase(mSettingsSaveConfigurationSdk);
        GetPairingIdUseCase getPairingIdUseCase=new GetPairingIdUseCase(mMasterpassExternalDataSource);
        RemovePairingIdUseCase removePairingIdUseCase=new RemovePairingIdUseCase(mSettingsSaveConfigurationSdk);
        SaveMasterpassPaymentMethodUseCase saveMasterpassMethodUseCase=new SaveMasterpassPaymentMethodUseCase(mSettingsSaveConfigurationSdk);
      return new SettingsDetailPaymentPresenter(useCaseHandler, mSettingsDetailPaymentView,
          getSettingsDetailPaymentDataUseCase, saveSettingsUseCase, saveMasterpassMethodUseCase,
          getPairingIdUseCase, removePairingIdUseCase, "SDK_LANG");
    }
}
