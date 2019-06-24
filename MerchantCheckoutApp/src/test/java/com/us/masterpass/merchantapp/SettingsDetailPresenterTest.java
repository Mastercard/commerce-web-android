package com.us.masterpass.merchantapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConstants;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.SettingsSaveInterface;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPresenter;
import com.us.masterpass.merchantapp.presentation.view.SettingsDetailListView;
import com.us.masterpass.merchantapp.utils.BackgroundForTestUseCaseScheduler;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by akhildixit on 12/5/17.
 *
 * Test created for {@link SettingsDetailPresenter}
 */

public class SettingsDetailPresenterTest {
    @Mock
    private SettingsDetailListView mSettingsDetailListView;
    @Mock
    private SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;
    @Captor
    private ArgumentCaptor<List<SettingsVO>> mSettingsDetail;
    @Captor
    private ArgumentCaptor<SettingsSaveInterface.SaveItemsCallback> mSaveItemsCallBack;
    @Mock
    private List<SettingsVO> mSettings;
    @Mock
    private SettingsListOptions mSettingsListOptions;
    private SettingsDetailPresenter mSettingsDetailPresenter;
    @Mock
    private SettingsVO settingsVO;
    @Mock
    private Context mContext;

    SharedPreferences sp = Mockito.mock(SharedPreferences.class);
    Context context = Mockito.mock(Context.class);
    SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);

    @Before
    public void settingsDetailUseCaseMock() {
        MockitoAnnotations.initMocks(this);
        sp = Mockito.mock(SharedPreferences.class);
        context = Mockito.mock(Context.class);
        editor = Mockito.mock(SharedPreferences.Editor.class);

        when(mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE)).thenReturn(sp);
        when(sp.edit()).thenReturn(editor);

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
    public void loadSettingsDetail() {
        mSettingsDetailPresenter = declarationLoadSettingsDetailPresenter();
        mSettingsDetailPresenter.loadSettingsDetail();
        verify(mSettingsDetailListView).setPresenter(mSettingsDetailPresenter);
        verify(mSettingsDetailListView).showSettingDetail(mSettingsDetail.capture());
        verifyNoMoreInteractions(mSettingsDetailListView);
    }

    @Test
    public void saveSettingsDetail() {
        SettingsVO dummySetting = new SettingsVO();
        dummySetting.setName("English");
        dummySetting.setType("LANGUAGE");
        dummySetting.setSelected(true);
        dummySetting.setSaveOption("en-US");
        mSettings.add(dummySetting);
        when(mSettings.get(0)).thenReturn(dummySetting);
        mSettingsDetailPresenter = declarationLoadSettingsDetailPresenter();
        mSettingsDetailPresenter.saveSettings(mSettings);
        verify(mSettingsDetailListView).setPresenter(mSettingsDetailPresenter);
        verify(mSettingsDetailListView).goBack();

    }
    @Test
    public void saveSettingsDetailonError(){
        SettingsVO dummySetting = new SettingsVO();
        dummySetting.setName("ENABLE EXPRESS CHECKOUT");
        dummySetting.setType("SWITCH");
        dummySetting.setSelected(true);
        dummySetting.setSaveOption("en-US");
        mSettings.add(dummySetting);
        when(mSettings.get(0)).thenReturn(dummySetting);
        mSettingsDetailPresenter = declarationLoadSettingsDetailPresenter();
        mSettingsDetailPresenter.saveSettings(mSettings);
        verify(mSettingsDetailListView).setPresenter(mSettingsDetailPresenter);
        verify(mSettingsDetailListView).showAlert();

    }

    private SettingsDetailPresenter declarationLoadSettingsDetailPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
        GetSettingsDetailUseCase getSettingsDetailUseCase = new GetSettingsDetailUseCase(mSettingsSaveConfigurationSdk);
        SaveSettingsUseCase saveSettingsUseCase = new SaveSettingsUseCase(mSettingsSaveConfigurationSdk);
        return new SettingsDetailPresenter(useCaseHandler, mSettingsDetailListView, getSettingsDetailUseCase, saveSettingsUseCase, "SDK_LANG");
    }
}