package com.us.masterpass.merchantapp.data.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.annotation.NonNull;
import com.us.masterpass.merchantapp.domain.model.LoginObject;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 22-10-17.
 */
public class SettingsSaveConfigurationSdk {
    private static SettingsSaveConfigurationSdk sSettingsSaveConfigurationSdk;
    private Context mContext;
    private String mConfigType;

    private static final String defaultLangSDK = "en-US";
    private static final String defaultCurrencySDK = "USD";

    private SettingsSaveConfigurationSdk(@NonNull Context contextApp) {
        checkNotNull(contextApp);
        this.mContext = contextApp;
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static SettingsSaveConfigurationSdk getInstance(Context context) {
        if (sSettingsSaveConfigurationSdk == null) {
            synchronized (SettingsSaveConfigurationSdk.class) {
                sSettingsSaveConfigurationSdk = new SettingsSaveConfigurationSdk(context);
            }
        }
        return sSettingsSaveConfigurationSdk;
    }

    /**
     * Sets save.
     *
     * @param configType   the config type
     * @param configToSave the config to save
     * @return the save
     */
    public boolean settingsSave(String configType, String configToSave) {
        mConfigType = configType;
        SharedPreferences sp = mContext.getSharedPreferences(
                mContext.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        switch (mConfigType) {
            case SettingsSaveConstants.SDK_CONFIG_LANG:
                edit.putString(SettingsSaveConstants.SDK_CONFIG_LANG, configToSave);
                break;
            case SettingsSaveConstants.SDK_CONFIG_CURRENCY:
                edit.putString(SettingsSaveConstants.SDK_CONFIG_CURRENCY, configToSave);
                break;
            default:
                break;
        }
        edit.commit();
        return true;
    }

    /**
     * Sets save.
     *
     * @param configType   the config type
     * @param configToSave the config to save
     * @return the save
     */
    public boolean settingsSave(String configType, Set<String> configToSave) {
        mConfigType = configType;
        SharedPreferences sp = mContext.getSharedPreferences(
                mContext.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        switch (mConfigType) {
            case SettingsSaveConstants.SDK_CONFIG_CARDS:
                edit.putStringSet(SettingsSaveConstants.SDK_CONFIG_CARDS, configToSave);
                break;
            case SettingsSaveConstants.SDK_CONFIG_DSRP:
                edit.putStringSet(SettingsSaveConstants.SDK_CONFIG_DSRP, configToSave);
                break;
            default:
                break;
        }
        edit.commit();
        return true;
    }

    /**
     * Sets save.
     *
     * @param configType   the config type
     * @param configToSave the config to save
     * @return the save
     */
    public boolean settingsSave(String configType, boolean configToSave) {
        mConfigType = configType;
        SharedPreferences sp = mContext.getSharedPreferences(
                mContext.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        switch (mConfigType) {
            case SettingsSaveConstants.SDK_CONFIG_EXPRESS:
                edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, configToSave);
                break;
            case SettingsSaveConstants.SDK_CONFIG_SUPRESS:
                edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_SUPRESS, configToSave);
                break;
            default:
                break;
        }
        edit.commit();
        return true;
    }

    /**
     * Sets saved conf.
     *
     * @param optionSelected the option selected
     * @param settings       the settings
     * @return the saved conf
     */
    public List<SettingsVO> settingsSavedConf(String optionSelected, List<SettingsVO> settings) {
        SharedPreferences sp = mContext.getSharedPreferences(
                mContext.getPackageName(), Context.MODE_PRIVATE);

        switch (optionSelected) {
            case SettingsSaveConstants.SDK_CONFIG_CARDS:
                Set<String> savedConfigCards = sp.getStringSet(optionSelected, defaultCardsSDK());
                for (int i = 0; i < settings.size(); i++) {
                    for (String config : savedConfigCards) {
                        if (settings.get(i).getSaveOption().equalsIgnoreCase(config)) {
                            settings.get(i).setSelected(true);
                            break;
                        }
                    }
                }
                break;
            case SettingsSaveConstants.SDK_CONFIG_DSRP:
                Set<String> savedConfigDSRP = sp.getStringSet(optionSelected, defaultDsrpSDK());
                for (int i = 0; i < settings.size(); i++) {
                    for (String config : savedConfigDSRP) {
                        if (settings.get(i).getSaveOption().equalsIgnoreCase(config)) {
                            settings.get(i).setSelected(true);
                            break;
                        }
                    }
                }
                break;
            case SettingsSaveConstants.SDK_CONFIG_LANG:
                String savedConfigLang = sp.getString(optionSelected, defaultLangSDK);
                for (int i = 0; i < settings.size(); i++) {
                    if (settings.get(i).getSaveOption().equalsIgnoreCase(savedConfigLang)) {
                        settings.get(i).setSelected(true);
                        break;
                    }
                }
                break;
            case SettingsSaveConstants.SDK_CONFIG_CURRENCY:
                String savedConfigCurrency = sp.getString(optionSelected, defaultCurrencySDK);
                for (int i = 0; i < settings.size(); i++) {
                    if (settings.get(i).getSaveOption().equalsIgnoreCase(savedConfigCurrency)) {
                        settings.get(i).setSelected(true);
                        break;
                    }
                }
                break;
            default:
                break;
        }

        return settings;
    }

    /**
     * Config switch boolean.
     *
     * @param keySearch the key search
     * @return the boolean
     */
    public boolean configSwitch(String keySearch) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        return sp.getBoolean(keySearch, false);
    }

    /**
     * Config selected string string.
     *
     * @param keySearch the key search
     * @return the string
     */
    public String configSelectedString(String keySearch) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        String selected = "";
        if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_LANG)) {
            selected = sp.getString(keySearch, defaultLangSDK);
        } else if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_CURRENCY)) {
            selected = sp.getString(keySearch, defaultCurrencySDK);
        }

        return selected;
    }

    /**
     * Config selected string set set.
     *
     * @param keySearch the key search
     * @return the set
     */
    public Set<String> configSelectedStringSet(String keySearch) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        Set<String> selected = new HashSet<>();
        if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_DSRP)) {
            selected = sp.getStringSet(keySearch, defaultDsrpSDK());
        } else if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_CARDS)) {
            selected = sp.getStringSet(keySearch, defaultCardsSDK());
        }

        return selected;
    }

    private Set<String> defaultDsrpSDK() {
        Set<String> defautltDsrp = new HashSet<>();
        defautltDsrp.add("ICC");
        defautltDsrp.add("UCAF");
        return defautltDsrp;
    }

    private Set<String> defaultCardsSDK() {
        Set<String> defautltCards = new HashSet<>();
        defautltCards.add("American");
        defautltCards.add("Discover");
        defautltCards.add("JCB");
        defautltCards.add("MasterCard");
        defautltCards.add("Unionpay");
        defautltCards.add("VISA");
        return defautltCards;
    }

    /**
     * Gets currency selected.
     *
     * @return the currency selected
     */
    public String getCurrencySelected() {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        return sp.getString(SettingsSaveConstants.SDK_CONFIG_CURRENCY, defaultCurrencySDK);
    }

    /**
     * Login save.
     *
     * @param loginObject     the login object
     * @param forceSaveConfig the force save config
     */
    public void loginSave(LoginObject loginObject, boolean forceSaveConfig) {
        SharedPreferences sp = mContext.getSharedPreferences(
                mContext.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, true);
        edit.putString(SettingsSaveConstants.LOGIN_USER_ID, loginObject.getUserId());
        edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, forceSaveConfig);

        edit.commit();
    }

    /**
     * Gets is logged.
     *
     * @return the is logged
     */
    public boolean getIsLogged() {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        return sp.getBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, false);
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        return sp.getString(SettingsSaveConstants.LOGIN_USER_ID, "");
    }

    /**
     * Gets supress shipping.
     *
     * @return the supress shipping
     */
    public boolean getSupressShipping() {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        return sp.getBoolean(SettingsSaveConstants.SDK_CONFIG_SUPRESS, false);
    }

    /**
     * Gets pairing id.
     *
     * @return the pairing id
     */
    public String getPairingId() {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        return sp.getString(SettingsSaveConstants.EXPRESS_PAIRING_ID, null);
    }

    /**
     * Gets express checkout.
     *
     * @return the express checkout
     */
    public boolean getExpressCheckout() {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        return sp.getBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, false);
    }

    /**
     * Save pairing id.
     *
     * @param pairingId the pairing id
     */
    public void savePairingId(String pairingId){
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString(SettingsSaveConstants.EXPRESS_PAIRING_ID, pairingId);
        edit.commit();
    }

    /**
     * Remove pairing id.
     */
    public void removePairingId(){
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString(SettingsSaveConstants.EXPRESS_PAIRING_ID, null);
        edit.commit();
    }

    /**
     * Remove logged boolean.
     *
     * @return the boolean
     */
    public boolean removeLogged() {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, false);
        edit.putString(SettingsSaveConstants.LOGIN_USER_ID, "");
        edit.putString(SettingsSaveConstants.EXPRESS_PAIRING_ID, null);
        edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, false);
        edit.commit();
        return true;
    }
}
