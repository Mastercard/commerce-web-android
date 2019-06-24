package com.us.masterpass.merchantapp;

import android.content.Context;

import com.mastercard.mp.switchservices.MasterpassSwitchServices;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Currency;
import java.util.Locale;

@RunWith(PowerMockRunner.class) @PrepareForTest({
        SettingsSaveConfigurationSdk.class
})
public class SettingsListOptionsTest {
    public static final String US = "US";
    public static final String EN_US = "en_US";
    public static final String EN = "en";
    public static final String INR = "INR";
    public static final String EXPECTED = "356";
    public static final String GBP = "GBP";
    @Mock
    private Context context;

    @Mock
    private SettingsSaveConfigurationSdk settingsSaveConfigurationSdk;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(SettingsSaveConfigurationSdk.class);
        Mockito.when(SettingsSaveConfigurationSdk.getInstance(context)).thenReturn(settingsSaveConfigurationSdk);

    }
    @Test
    public void getCountryCodeTest() {
        Mockito.when(settingsSaveConfigurationSdk.configSelectedString(Matchers.anyString())).thenReturn(EN_US);
        Assert.assertEquals(US, SettingsListOptions.getCountryCode(context));
    }

    @Test
    public void getCountryCodeNullTest() {
        Mockito.when(settingsSaveConfigurationSdk.configSelectedString(Matchers.anyString())).thenReturn(EN);
        Assert.assertEquals(null, SettingsListOptions.getCountryCode(context));
    }

    @Test
    public void getCurrenyNumberTest() {
        Mockito.when(settingsSaveConfigurationSdk.configSelectedString(Matchers.anyString())).thenReturn(INR);
        Assert.assertEquals(EXPECTED, SettingsListOptions.getCurrenyNumber(context));
    }

    /**
     * Here we can notice that we have not passed the £ but GBP as the context is mocked and the symbol
     * is an outcome of android context pool
     */
    @Test
    public void getCurrenySymbolTest() {
        Currency.getInstance(Locale.getDefault()).getSymbol();
        Mockito.when(settingsSaveConfigurationSdk.configSelectedString(Matchers.anyString())).thenReturn("INR");
        Assert.assertEquals("Rs.", SettingsListOptions.getCurrencySymbol(context));
    }
}
