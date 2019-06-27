package com.us.masterpass.merchantapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConstants;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsPresenter;
import com.us.masterpass.merchantapp.presentation.view.SettingsListView;
import com.us.masterpass.merchantapp.utils.BackgroundForTestUseCaseScheduler;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by akhildixit on 12/4/17.
 * <p>
 * Test created for {@link SettingsPresenter}
 */
public class SettingsPresenterTest {
    @Mock
    private SettingsListView mSettingsListView;

    @Mock
    private Context mContext;

    @Mock
    private SettingsSaveConfigurationSdk mSettingsSaveConfigurationSdk;

    @Mock
    private SettingsVO settingsVO;

    @Captor
    private ArgumentCaptor<List<SettingsVO>> settingsList;

    @Mock
    private List<SettingsVO> mOptionSelected;

    SharedPreferences sp = Mockito.mock(SharedPreferences.class);
    Context context = Mockito.mock(Context.class);

    @Before
    public void setSettingsUseCaseMock() {
        MockitoAnnotations.initMocks(this);
        sp = Mockito.mock(SharedPreferences.class);
        context = Mockito.mock(Context.class);

        //Mock data on shared preferences
        when(mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE))
                .thenReturn(sp);
        when(sp.getString("SDK_LANG", "en-US")).thenReturn("en-US");
        when(sp.getString("SDK_CURRENCY", "USD")).thenReturn("USD");

        SettingsVO dummySetting = new SettingsVO();
        dummySetting.setName("SUPPRESS SHIPPING");
        dummySetting.setType("LANGUAGE");
        dummySetting.setSelected(true);
        dummySetting.setSaveOption("en-US");
        mOptionSelected.add(dummySetting);

        when(settingsVO.getType()).thenReturn("LANGUAGE");
        when(mOptionSelected.size()).thenReturn(1);
        when(mSettingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_LANG,"en-US")).thenReturn(true);
    }

    private SettingsPresenter mSettingsPresenter;

    /*@Test
    public void loadSettingsScreen() {
        mSettingsPresenter = declarationSettingsPresenter();
        mSettingsPresenter.loadSettings();
        verify(mSettingsListView).setPresenter(mSettingsPresenter);
        verify(mSettingsListView).showSettings(settingsList.capture(), eq(false));
        verifyNoMoreInteractions(mSettingsListView);
    }*/

    @Test
    public void saveSettingsSwitch() {
        mSettingsPresenter = declarationSettingsPresenter();
        mSettingsPresenter.saveSettingsSwitch(settingsVO);
        mSettingsPresenter.saveSettingsSwitch(settingsVO);
        verify(mSettingsListView).setPresenter(mSettingsPresenter);
        verifyNoMoreInteractions(mSettingsListView);
    }
    @Test
    public void saveSettingsSwitchOnError(){
        mSettingsPresenter=declarationSettingsPresenter();
        mSettingsPresenter.saveSettingsSwitch(settingsVO);
        verify(mSettingsListView).setPresenter(mSettingsPresenter);
        verifyNoMoreInteractions(mSettingsListView);
    }

    private SettingsPresenter declarationSettingsPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
        GetSettingsUseCase getSettingsUseCase = new GetSettingsUseCase(mContext);
        SaveSettingsUseCase saveSettingsUseCase = new SaveSettingsUseCase(mSettingsSaveConfigurationSdk);
        return new SettingsPresenter(useCaseHandler, mSettingsListView, saveSettingsUseCase, getSettingsUseCase);
    }

}