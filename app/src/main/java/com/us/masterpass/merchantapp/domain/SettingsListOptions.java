package com.us.masterpass.merchantapp.domain;

import android.content.Context;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConstants;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sebastian Farias on 11-10-17.
 */
public class SettingsListOptions {

  private SettingsListOptions() {
  }

  /**
   * Sets list.
   *
   * @param context the context
   * @return the list
   */
  public static List<SettingsVO> settingsList(Context context) {
    List<SettingsVO> settingsItems = new ArrayList<>();

    SettingsVO cardOption = new SettingsVO();
    cardOption.setName(SettingsConstants.ITEM_CARDS);
    cardOption.setDescription(
        settingsValueDisplayCards(context, SettingsSaveConstants.SDK_CONFIG_CARDS));
    cardOption.setType(SettingsConstants.TYPE_CARD);
    settingsItems.add(cardOption);

    SettingsVO languageOption = new SettingsVO();
    languageOption.setName(SettingsConstants.ITEM_LANGUAGE);
    languageOption.setDescription(
        settingsValueDisplay(context, SettingsSaveConstants.SDK_CONFIG_LANG));
    languageOption.setType(SettingsConstants.TYPE_ARROW);
    settingsItems.add(languageOption);

    SettingsVO currencyOption = new SettingsVO();
    currencyOption.setName(SettingsConstants.ITEM_CURRENCY);
    currencyOption.setDescription(
        settingsValueDisplay(context, SettingsSaveConstants.SDK_CONFIG_CURRENCY));
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
    supressOption.setSelected(SettingsSaveConfigurationSdk.getInstance(context)
        .configSwitch(SettingsSaveConstants.SDK_CONFIG_SUPRESS));
    settingsItems.add(supressOption);

    SettingsVO dsrpOption = new SettingsVO();
    dsrpOption.setName(SettingsConstants.ITEM_DSRP);
    dsrpOption.setType(SettingsConstants.TYPE_ARROW);
    settingsItems.add(dsrpOption);

    return settingsItems;
  }

  /**
   * Sets detail.
   *
   * @param optionSelected the option selected
   * @param settingsSavedConfigSdk the settings saved config sdk
   * @return the detail
   */
  public static List<SettingsVO> settingsDetail(String optionSelected,
      SettingsSaveConfigurationSdk settingsSavedConfigSdk) {
    List<SettingsVO> settingsDetail = new ArrayList<>();
    switch (optionSelected) {
      case SettingsConstants.ITEM_LANGUAGE:
        for (SettingsConstants.SDK_LANG lang : SettingsConstants.SDK_LANG.values()) {
          settingsDetail.add(itemDetail(lang, optionSelected));
        }
        settingsSavedConfigSdk.settingsSavedConf(SettingsSaveConstants.SDK_CONFIG_LANG,
            settingsDetail);
        break;
      case SettingsConstants.ITEM_CURRENCY:
        for (SettingsConstants.SDK_CURRENCY currency : SettingsConstants.SDK_CURRENCY.values()) {
          settingsDetail.add(itemDetail(currency, optionSelected));
        }
        settingsSavedConfigSdk.settingsSavedConf(SettingsSaveConstants.SDK_CONFIG_CURRENCY,
            settingsDetail);
        break;
      case SettingsConstants.ITEM_DSRP:
        for (SettingsConstants.SDK_DSRP dsrp : SettingsConstants.SDK_DSRP.values()) {
          settingsDetail.add(itemDetail(dsrp, optionSelected));
        }
        settingsSavedConfigSdk.settingsSavedConf(SettingsSaveConstants.SDK_CONFIG_DSRP,
            settingsDetail);
        break;
      case SettingsConstants.ITEM_CARDS:
        for (SettingsConstants.SDK_CARDS cards : SettingsConstants.SDK_CARDS.values()) {
          settingsDetail.add(itemDetail(cards, optionSelected));
        }
        settingsSavedConfigSdk.settingsSavedConf(SettingsSaveConstants.SDK_CONFIG_CARDS,
            settingsDetail);
        break;
      default:
        break;
    }

    return settingsDetail;
  }

  private static SettingsVO itemDetail(SettingsConstants.SDK_LANG itemSDK, String optionSelected) {
    SettingsVO item = new SettingsVO();
    item.setName(itemSDK.getTextDisplay());
    item.setSaveOption(itemSDK.getConfigToSave());
    item.setType(optionSelected);
    return item;
  }

  private static SettingsVO itemDetail(SettingsConstants.SDK_CURRENCY itemSDK,
      String optionSelected) {
    SettingsVO item = new SettingsVO();
    item.setName(itemSDK.getTextDisplay());
    item.setSaveOption(itemSDK.getConfigToSave());
    item.setType(optionSelected);
    return item;
  }

  private static SettingsVO itemDetail(SettingsConstants.SDK_DSRP itemSDK, String optionSelected) {
    SettingsVO item = new SettingsVO();
    item.setName(itemSDK.getTextDisplay());
    item.setSaveOption(itemSDK.getConfigToSave());
    item.setType(optionSelected);
    return item;
  }

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
   * @param settings the settings
   * @param settingsSaveConfigurationSdk the settings save configuration sdk
   * @param callback the callback
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
        if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_LANG,
            configToSaveString)) {
          callback.onSettingsSaved();
        }
        break;
      case SettingsConstants.ITEM_CURRENCY:
        //ONLY ONE VALUE
        if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_CURRENCY,
            configToSaveString)) {
          callback.onSettingsSaved();
        }
        break;
      case SettingsConstants.ITEM_CARDS:
        //MULTIPLE VALUES
        if (!configToSaveStringSet.isEmpty()) {
          if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_CARDS,
              configToSaveStringSet)) {
            callback.onSettingsSaved();
          }
        } else {
          callback.onSettingsNotSaved();
        }
        break;
      case SettingsConstants.ITEM_DSRP:
        //MULTIPLE VALUES
        if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_DSRP,
            configToSaveStringSet)) {
          callback.onSettingsSaved();
        }
        break;
      case SettingsConstants.TYPE_SWITCH:
        if (settingsSwitch.getName().equalsIgnoreCase(SettingsConstants.ITEM_SUPRESS)) {
          if (settingsSaveConfigurationSdk.settingsSave(SettingsSaveConstants.SDK_CONFIG_SUPRESS,
              settingsSwitch.isSelected())) {
            callback.onSettingsSaved();
          }
        }
        break;
      default:
        break;
    }
  }

  private static String settingsValueDisplay(Context context, String searchKey) {
    String valueToDisplay =
        SettingsSaveConfigurationSdk.getInstance(context).configSelectedString(searchKey);
    switch (searchKey) {
      case SettingsSaveConstants.SDK_CONFIG_LANG:
        for (SettingsConstants.SDK_LANG lang : SettingsConstants.SDK_LANG.values()) {
          if (valueToDisplay.equalsIgnoreCase(lang.getConfigToSave())) {
            valueToDisplay = lang.getTextDisplay();
            break;
          }
        }
        break;
      case SettingsSaveConstants.SDK_CONFIG_CURRENCY:
        for (SettingsConstants.SDK_CURRENCY currency : SettingsConstants.SDK_CURRENCY.values()) {
          if (valueToDisplay.equalsIgnoreCase(currency.getConfigToSave())) {
            valueToDisplay = currency.getTextDisplay();
            break;
          }
        }
        break;
      default:
        break;
    }
    return valueToDisplay;
  }

  private static String settingsValueDisplayCards(Context context, String searchKey) {
    Set<String> valueToTrasnform =
        SettingsSaveConfigurationSdk.getInstance(context).configSelectedStringSet(searchKey);
    String valueToDisplay = "";
    for (String text : valueToTrasnform) {
      valueToDisplay = valueToDisplay + text + ",";
    }

    return valueToDisplay;
  }
}