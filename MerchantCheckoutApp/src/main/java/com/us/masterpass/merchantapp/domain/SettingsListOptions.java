package com.us.masterpass.merchantapp.domain;

import android.content.Context;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConstants;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sebastian Farias on 11-10-17.
 * <p>
 * All settings options show on settings view, settings detail, and settings configuration
 */
public class SettingsListOptions {

    private SettingsListOptions() {

    }

    /**
     * Return settings list to display.
     *
     * @param context Android application context
     * @return the list
     */
    public static List<SettingsVO> settingsList(Context context) {
        List<SettingsVO> settingsItems = new ArrayList<>();

        SettingsVO cardOption = new SettingsVO();
        cardOption.setName(SettingsConstants.ITEM_CARDS);
        cardOption.setDescription(
                settingsValueDisplayCards(context, SettingsSaveConstants.SDK_CONFIG_CARDS)
        );
        cardOption.setType(SettingsConstants.TYPE_CARD);
        settingsItems.add(cardOption);

        SettingsVO languageOption = new SettingsVO();
        languageOption.setName(SettingsConstants.ITEM_LANGUAGE);
        languageOption.setDescription(
                settingsValueDisplay(context, SettingsSaveConstants.SDK_CONFIG_LANG)
        );
        languageOption.setType(SettingsConstants.TYPE_ARROW);
        settingsItems.add(languageOption);

        SettingsVO currencyOption = new SettingsVO();
        currencyOption.setName(SettingsConstants.ITEM_CURRENCY);
        currencyOption.setDescription(
                settingsValueDisplay(context, SettingsSaveConstants.SDK_CONFIG_CURRENCY)
        );
        currencyOption.setType(SettingsConstants.TYPE_ARROW);
        settingsItems.add(currencyOption);

        SettingsVO shippingOption = new SettingsVO();
        shippingOption.setName(SettingsConstants.ITEM_SHIPPING);
        shippingOption.setType(SettingsConstants.TYPE_ARROW);
        shippingOption.setDescription("World Postal Service (WOPS)");
        settingsItems.add(shippingOption);

        SettingsVO supressOption = new SettingsVO();
        supressOption.setName(SettingsConstants.ITEM_SUPRESS);
        supressOption.setType(SettingsConstants.TYPE_SWITCH);
        supressOption.setSelected(
                SettingsSaveConfigurationSdk.getInstance(context)
                        .configSwitch(SettingsSaveConstants.SDK_CONFIG_SUPRESS)
        );
        settingsItems.add(supressOption);

        SettingsVO expressOption = new SettingsVO();
        expressOption.setName(SettingsConstants.ITEM_EXPRESS);
        expressOption.setType(SettingsConstants.TYPE_SWITCH);
        expressOption.setSelected(
                SettingsSaveConfigurationSdk.getInstance(context)
                        .configSwitch(SettingsSaveConstants.SDK_CONFIG_EXPRESS)
        );
        settingsItems.add(expressOption);

      SettingsVO enableWebCheckout = new SettingsVO();
      enableWebCheckout.setName(SettingsConstants.ENABLE_WEB_CHECKOUT);
      enableWebCheckout.setType(SettingsConstants.TYPE_SWITCH);
      enableWebCheckout.setSelected(SettingsSaveConfigurationSdk.getInstance(context)
          .configSwitch(SettingsSaveConstants.SDK_WEB_CHECKOUT));
      settingsItems.add(enableWebCheckout);

      SettingsVO pairingOnly = new SettingsVO();
      pairingOnly.setName(SettingsConstants.PAIRING_ONLY);
      pairingOnly.setType(SettingsConstants.TYPE_ARROW);
      settingsItems.add(pairingOnly);

        SettingsVO supress3ds = new SettingsVO();
        supress3ds.setName(SettingsConstants.SUPRESS_3DS);
        supress3ds.setType(SettingsConstants.TYPE_SWITCH);
        supress3ds.setSelected(SettingsSaveConfigurationSdk.getInstance(context)
                .configSwitch(SettingsSaveConstants.SDK_SUPRESS_3DS));
        settingsItems.add(supress3ds);

        SettingsVO enablePaymentMethodOption = new SettingsVO();
        enablePaymentMethodOption.setName(SettingsConstants.ITEM_PAYMENT_METHOD);
        enablePaymentMethodOption.setType(SettingsConstants.TYPE_SWITCH);
        enablePaymentMethodOption.setSelected(
            SettingsSaveConfigurationSdk.getInstance(context)
                .configSwitch(SettingsSaveConstants.SDK_PAYMENT_METHOD)
        );
        settingsItems.add(enablePaymentMethodOption);

        SettingsVO paymentMethodOption = new SettingsVO();
        paymentMethodOption.setName(SettingsConstants.ITEM_PAYMENT);
        paymentMethodOption.setType(SettingsConstants.TYPE_ARROW);
        settingsItems.add(paymentMethodOption);

      SettingsVO dsrpOption = new SettingsVO();
      dsrpOption.setName(SettingsConstants.ITEM_DSRP);
      dsrpOption.setType(SettingsConstants.TYPE_ARROW);
      settingsItems.add(dsrpOption);
        return settingsItems;
    }

    /**
     * Return detail options for settings selected .
     *
     * @param optionSelected         the option selected
     * @param settingsSavedConfigSdk the settings saved config sdk {@link SettingsSaveConfigurationSdk}
     * @return list for settings
     */
    public static List<SettingsVO> settingsDetail(String optionSelected,
                                                  SettingsSaveConfigurationSdk settingsSavedConfigSdk) {
        List<SettingsVO> settingsDetail = new ArrayList<>();
        switch (optionSelected) {
            case SettingsConstants.ITEM_LANGUAGE:
                for (SettingsConstants.SDK_LANG lang : SettingsConstants.SDK_LANG.values()) {
                    settingsDetail.add(itemDetail(lang, optionSelected));
                }
                settingsSavedConfigSdk.settingsSavedConf(
                        SettingsSaveConstants.SDK_CONFIG_LANG, settingsDetail);
                break;
            case SettingsConstants.ITEM_CURRENCY:
                for (SettingsConstants.SDK_CURRENCY currency : SettingsConstants.SDK_CURRENCY.values()) {
                    settingsDetail.add(itemDetail(currency, optionSelected));
                }
                settingsSavedConfigSdk.settingsSavedConf(
                        SettingsSaveConstants.SDK_CONFIG_CURRENCY, settingsDetail);
                break;
            case SettingsConstants.ITEM_DSRP:
                for (SettingsConstants.SDK_DSRP dsrp : SettingsConstants.SDK_DSRP.values()) {
                    settingsDetail.add(itemDetail(dsrp, optionSelected));
                }
                settingsSavedConfigSdk.settingsSavedConf(
                        SettingsSaveConstants.SDK_CONFIG_DSRP, settingsDetail);
                break;
            case SettingsConstants.ITEM_CARDS:
                for (SettingsConstants.SDK_CARDS cards : SettingsConstants.SDK_CARDS.values()) {
                    settingsDetail.add(itemDetail(cards, optionSelected));
                }
                settingsSavedConfigSdk.settingsSavedConf(
                        SettingsSaveConstants.SDK_CONFIG_CARDS, settingsDetail);
                break;
            default:
                break;
        }


        return settingsDetail;
    }


    /**
     * Check if user is logged.
     *
     * @param context Android application context
     * @return is logged boolean
     */
    public static boolean isLogged(Context context) {
        return SettingsSaveConfigurationSdk.getInstance(context)
                .configSwitch(SettingsSaveConstants.LOGIN_IS_LOGGED);
    }

    /**
     * Specific detail for selected item on list
     *
     * @param itemSDK        item to add
     * @param optionSelected option selected
     * @return {@link SettingsVO} return specific object
     */
    private static SettingsVO itemDetail(SettingsConstants.SDK_LANG itemSDK, String optionSelected) {
        SettingsVO item = new SettingsVO();
        item.setName(itemSDK.getTextDisplay());
        item.setSaveOption(itemSDK.getConfigToSave());
        item.setType(optionSelected);
        return item;
    }

    /**
     * Specific detail for selected item on list
     *
     * @param itemSDK        item to add
     * @param optionSelected option selected
     * @return {@link SettingsVO} return specific object
     */
    private static SettingsVO itemDetail(SettingsConstants.SDK_CURRENCY itemSDK, String optionSelected) {
        SettingsVO item = new SettingsVO();
        item.setName(itemSDK.getTextDisplay());
        item.setSaveOption(itemSDK.getConfigToSave());
        item.setType(optionSelected);
        return item;
    }

    /**
     * Specific detail for selected item on list
     *
     * @param itemSDK        item to add
     * @param optionSelected option selected
     * @return {@link SettingsVO} return specific object
     */
    private static SettingsVO itemDetail(SettingsConstants.SDK_DSRP itemSDK, String optionSelected) {
        SettingsVO item = new SettingsVO();
        item.setName(itemSDK.getTextDisplay());
        item.setSaveOption(itemSDK.getConfigToSave());
        item.setType(optionSelected);
        return item;
    }

    /**
     * Specific detail for selected item on list
     *
     * @param itemSDK        item to add
     * @param optionSelected option selected
     * @return {@link SettingsVO} return specific object
     */
    private static SettingsVO itemDetail(SettingsConstants.SDK_CARDS itemSDK, String optionSelected) {
        SettingsVO item = new SettingsVO();
        item.setName(itemSDK.getTextDisplay());
        item.setSaveOption(itemSDK.getConfigToSave());
        item.setImageName(itemSDK.getImageToDisplay());
        item.setType(optionSelected);
        return item;
    }

    /**
     * Save settings.
     *
     * @param settings                     settings list
     * @param settingsSaveConfigurationSdk instance of {@link SettingsSaveConfigurationSdk}
     * @param callback                     {@link SettingsSaveInterface.SaveItemsCallback}
     */
    public static void saveSettings(List<SettingsVO> settings,
                                    SettingsSaveConfigurationSdk settingsSaveConfigurationSdk,
                                    SettingsSaveInterface.SaveItemsCallback callback) {
        String configToSaveString = "";
        Set<String> configToSaveStringSet = new HashSet<>();
        SettingsVO settingsSwitch = new SettingsVO();
        String typeSelected = "";

        for (int i = 0; i < settings.size(); i++) {
            settingsSwitch = settings.get(i);
            if (typeSelected.isEmpty()) {
                typeSelected = settings.get(i).getType();
            }
            if (settings.get(i).isSelected()) {
                configToSaveString = settings.get(i).getSaveOption();
                configToSaveStringSet.add(configToSaveString);
            }
        }

        switch (typeSelected) {
            case SettingsConstants.ITEM_LANGUAGE:
                //ONLY ONE VALUE
                if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_LANG, configToSaveString)) {
                    callback.onSettingsSaved();
                }
                break;
            case SettingsConstants.ITEM_CURRENCY:
                //ONLY ONE VALUE
                if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_CURRENCY, configToSaveString)) {
                    callback.onSettingsSaved();
                }
                break;
            case SettingsConstants.ITEM_CARDS:
                //MULTIPLE VALUES
                saveCards(settingsSaveConfigurationSdk, callback, configToSaveStringSet);
                break;
            case SettingsConstants.ITEM_DSRP:
                //MULTIPLE VALUES
                if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_DSRP, configToSaveStringSet)) {
                    callback.onSettingsSaved();
                }
                break;
            case SettingsConstants.TYPE_SWITCH:
                saveSwitchSettings(settingsSaveConfigurationSdk, callback, settingsSwitch);
                break;
            default:
                break;
        }


    }

    private static void saveCards(SettingsSaveConfigurationSdk settingsSaveConfigurationSdk, SettingsSaveInterface.SaveItemsCallback callback, Set<String> configToSaveStringSet) {
        if (!configToSaveStringSet.isEmpty()) {
            if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_CARDS, configToSaveStringSet)) {
                callback.onSettingsSaved();
            }
        } else {
            callback.onSettingsNotSaved();
        }
    }

    private static void saveSwitchSettings(SettingsSaveConfigurationSdk settingsSaveConfigurationSdk, SettingsSaveInterface.SaveItemsCallback callback, SettingsVO settingsSwitch) {
        if (settingsSwitch.getName().equalsIgnoreCase(SettingsConstants.ITEM_SUPRESS)
                && settingsSaveConfigurationSdk.settingsSave(
                SettingsSaveConstants.SDK_CONFIG_SUPRESS, settingsSwitch.isSelected())) {
            callback.onSettingsSaved();
        } else if (settingsSwitch.getName().equalsIgnoreCase(SettingsConstants.ITEM_EXPRESS)) {
            if (settingsSaveConfigurationSdk.getIsLogged()
                    && settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_EXPRESS,
                    settingsSwitch.isSelected())) {
                callback.onSettingsSaved();
            } else {
                callback.onSettingsNotSaved();
            }
        } else if (settingsSwitch.getName()
            .equalsIgnoreCase(SettingsConstants.ENABLE_WEB_CHECKOUT)
                && settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_WEB_CHECKOUT, settingsSwitch.isSelected())) {
            settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_PAYMENT_METHOD, false);
            callback.onSettingsSaved();
        } else if (settingsSwitch.getName().equalsIgnoreCase(SettingsConstants.ITEM_PAYMENT_METHOD)
                && settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_PAYMENT_METHOD, settingsSwitch.isSelected())) {
            settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_WEB_CHECKOUT, false);
            callback.onSettingsSaved();
        } else if (settingsSwitch.getName().equalsIgnoreCase(SettingsConstants.SUPRESS_3DS)
                && settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_SUPRESS_3DS, settingsSwitch.isSelected())) {
            callback.onSettingsSaved();
        }
    }

    /**
     * Value to display on fragment
     *
     * @param context   Android application context
     * @param searchKey element to search
     * @return string with value to display
     */
    private static String settingsValueDisplay(Context context, String searchKey) {
        String valueToDisplay = getConfigString(context, searchKey);
        switch (searchKey) {
            case SettingsSaveConstants.SDK_CONFIG_LANG:
                SettingsConstants.SDK_LANG sdkLang = getSDKLang(valueToDisplay);
                if(sdkLang != null) {
                    valueToDisplay = sdkLang.getTextDisplay();
                }
                break;
            case SettingsSaveConstants.SDK_CONFIG_CURRENCY:
                SettingsConstants.SDK_CURRENCY sdkCurrency =  getSDKCurrency(valueToDisplay);
                if(sdkCurrency != null) {
                    valueToDisplay = sdkCurrency.getTextDisplay();
                }
                break;
            default:
                break;
        }
        return valueToDisplay;
    }

    private static String getConfigString(Context context, String searchKey) {
        return SettingsSaveConfigurationSdk.getInstance(context).configSelectedString(searchKey);
    }

    /**
     * Method to get the country code of currently selected locale
     * @param context
     * @return the countryCode
     */
    public static String getCountryCode(Context context) {
        String countryConfig = getConfigString(context,SettingsSaveConstants.SDK_CONFIG_LANG);
        if(countryConfig != null) {
            int arr = countryConfig.split("_").length;
            if(arr >1)
                return countryConfig.split("_")[1];
        }
        return null;
    }

    public static String getCurrenyCode(Context context) {
        return getConfigString(context, SettingsSaveConstants.SDK_CONFIG_CURRENCY);
    }

    /**
     * Returns the currency number of currently selected Currency.
     * @param context
     * @return the currency Number
     */
    public static String getCurrenyNumber(Context context) {
        SettingsConstants.SDK_CURRENCY sdkCurrency = getSDKCurrency(getConfigString(context, SettingsSaveConstants.SDK_CONFIG_CURRENCY));
        if(sdkCurrency!= null) {
            return sdkCurrency.getCurrencyNumber();
        }
        return null;
    }
    private static SettingsConstants.SDK_CURRENCY getSDKCurrency(String valueToDisplay) {
        SettingsConstants.SDK_CURRENCY sdkCurrency = null;
        for (SettingsConstants.SDK_CURRENCY currency : SettingsConstants.SDK_CURRENCY.values()) {
            if (valueToDisplay.equalsIgnoreCase(currency.getConfigToSave())) {
                sdkCurrency = currency;
            }
        }
        return sdkCurrency;
    }

    private static SettingsConstants.SDK_LANG getSDKLang(String valueToDisplay) {
        SettingsConstants.SDK_LANG sdkLang = null;
        for (SettingsConstants.SDK_LANG lang : SettingsConstants.SDK_LANG.values()) {
            if (valueToDisplay.equalsIgnoreCase(lang.getConfigToSave())) {
                sdkLang = lang;
            }
        }
        return sdkLang;
    }

    /**
     * Value to display for selected cards
     *
     * @param context   Android application context
     * @param searchKey element to search
     * @return selected cards
     */
    private static String settingsValueDisplayCards(Context context, String searchKey) {
        Set<String> valueToTrasnform =
                SettingsSaveConfigurationSdk.getInstance(context).configSelectedStringSet(searchKey);
        StringBuilder valueToDisplay = new StringBuilder();
        for (String text : valueToTrasnform) {
            valueToDisplay = valueToDisplay.append(text).append(",");
        }

        return valueToDisplay.toString();
    }

    public static String getCurrencySymbol(Context context) {
        Currency currency = Currency.getInstance(getCurrenyCode(context));
        return currency.getSymbol();
    }
}