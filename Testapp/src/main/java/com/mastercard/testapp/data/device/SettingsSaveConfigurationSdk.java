package com.mastercard.testapp.data.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.mastercard.mp.checkout.MasterpassPaymentMethod;
import com.mastercard.mp.switchservices.CryptoUtil;
import com.mastercard.testapp.data.external.EnvironmentConstants;
import com.mastercard.testapp.domain.model.LoginObject;
import com.mastercard.testapp.domain.model.SettingsVO;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 22-10-17.
 */
public class SettingsSaveConfigurationSdk {
  private static final String DEFAULT_LANG_SDK = "en_US";
  private static final String DEFAULT_CURRENCY_SDK = "USD";
  private static SettingsSaveConfigurationSdk sSettingsSaveConfigurationSdk;
  private Context mContext;
  private String mConfigType;

  private SettingsSaveConfigurationSdk(@NonNull Context contextApp) {
    checkNotNull(contextApp);
    mContext = contextApp;
  }

  /**
   * Get instance of the class.
   *
   * @param context of android application
   * @return instance of class
   */
  public static synchronized SettingsSaveConfigurationSdk getInstance(Context context) {
    if (sSettingsSaveConfigurationSdk == null) {
      synchronized (SettingsSaveConfigurationSdk.class) {
        sSettingsSaveConfigurationSdk = new SettingsSaveConfigurationSdk(context);
      }
    }
    return sSettingsSaveConfigurationSdk;
  }

  /**
   * Save settings of SDK on Shared Preferences.
   *
   * @param configType config to save lang / currency
   * @param configToSave string with value to save
   * @return true, data saved
   */
  public boolean settingsSave(String configType, String configToSave) {
    mConfigType = configType;
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Editor edit = sp.edit();
    switch (mConfigType) {
      case SettingsSaveConstants.SDK_CONFIG_LANG:
        edit.putString(SettingsSaveConstants.SDK_CONFIG_LANG, configToSave);
        break;
      case SettingsSaveConstants.SDK_CONFIG_CURRENCY:
        edit.putString(SettingsSaveConstants.SDK_CONFIG_CURRENCY, configToSave);
        break;
      case SettingsSaveConstants.SDK_CONFIG_ENVIRONMENT:
        edit.putString(SettingsSaveConstants.SDK_CONFIG_ENVIRONMENT, configToSave);
        EnvironmentConstants.setCurrentEnvironment(configToSave);
        break;
      default:
        break;
    }
    edit.apply();
    return true;
  }

  /**
   * Save settings of SDK on Shared Preferences.
   *
   * @param configType config to save cards / dsrp
   * @param configToSave string set with values to save
   * @return true, data saved
   */
  public boolean settingsSave(String configType, Set<String> configToSave) {
    mConfigType = configType;
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
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
    edit.apply();
    return true;
  }

  /**
   * Save settings of SDK on Shared Preferences.
   *
   * @param configType config to save express / suppress
   * @param configToSave boolean to save
   * @return true, data saved
   */
  public boolean settingsSave(String configType, boolean configToSave) {
    mConfigType = configType;
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Editor edit = sp.edit();
    switch (mConfigType) {
      case SettingsSaveConstants.SDK_CONFIG_EXPRESS:
        edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, configToSave);
        break;
      case SettingsSaveConstants.SDK_CONFIG_SUPRESS:
        edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_SUPRESS, configToSave);
        break;
      case SettingsSaveConstants.SDK_CONFIG_OLD_API:
        edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_OLD_API, configToSave);
        break;
      case SettingsSaveConstants.SDK_CONFIG_MASTERPASS:
        edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_MASTERPASS, configToSave);
        break;
      case SettingsSaveConstants.SDK_PAYMENT_METHOD:
        edit.putBoolean(SettingsSaveConstants.SDK_PAYMENT_METHOD, configToSave);
        break;
      case SettingsSaveConstants.SDK_WEB_CHECKOUT:
        edit.putBoolean(SettingsSaveConstants.SDK_WEB_CHECKOUT, configToSave);
        break;
      case SettingsSaveConstants.SDK_SUPRESS_3DS:
        edit.putBoolean(SettingsSaveConstants.SDK_SUPRESS_3DS, configToSave);
        break;
      default:
        break;
    }
    edit.apply();
    return true;
  }

  /**
   * Get saved configurations on Shared Preferences.
   *
   * @param optionSelected configuration to get
   * @param settings settings selected
   * @return settings list
   */
  public List<SettingsVO> settingsSavedConf(String optionSelected, List<SettingsVO> settings) {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);

    switch (optionSelected) {
      case SettingsSaveConstants.SDK_CONFIG_CARDS:
        Set<String> savedConfigCards = sp.getStringSet(optionSelected, defaultCardsSDK());
        setSettingsSelectedList(savedConfigCards, settings);
        break;
      case SettingsSaveConstants.SDK_CONFIG_DSRP:
        Set<String> savedConfigDSRP = sp.getStringSet(optionSelected, defaultDsrpSDK());
        setSettingsSelectedList(savedConfigDSRP, settings);
        break;
      case SettingsSaveConstants.SDK_CONFIG_LANG:
        String savedConfigLang = sp.getString(optionSelected, DEFAULT_LANG_SDK);
        setSettingsSelected(savedConfigLang, settings);
        break;
      case SettingsSaveConstants.SDK_CONFIG_CURRENCY:
        String savedConfigCurrency = sp.getString(optionSelected, DEFAULT_CURRENCY_SDK);
        setSettingsSelected(savedConfigCurrency, settings);
        break;
      case SettingsSaveConstants.SDK_CONFIG_ENVIRONMENT:
        String savedConfigEnvironment = sp.getString(optionSelected, EnvironmentConstants.getCurrentEnvironment());
        setSettingsSelected(savedConfigEnvironment, settings);
        break;
      default:
        break;
    }

    return settings;
  }

  /**
   * Method created from the switch case in settingsSavedConf.
   */
  private void setSettingsSelected(String savedConfig, List<SettingsVO> settings) {
    for (int i = 0; i < settings.size(); i++) {
      if (settings.get(i).getSaveOption().equalsIgnoreCase(savedConfig)) {
        settings.get(i).setSelected(true);
        break;
      }
    }
  }

  /**
   * Method created from the switch case in settingsSavedConf.
   */
  private void setSettingsSelectedList(Set<String> savedConfig, List<SettingsVO> settings) {
    for (int i = 0; i < settings.size(); i++) {
      for (String config : savedConfig) {
        if (settings.get(i).getSaveOption().equalsIgnoreCase(config)) {
          settings.get(i).setSelected(true);
          break;
        }
      }
    }
  }

  /**
   * Boolean saved configuration.
   *
   * @param keySearch configuration to search on Shared Preferences
   * @return configuration selected
   */
  public boolean configSwitch(String keySearch) {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    boolean defaultValue = false;
    if (keySearch.equals(SettingsSaveConstants.SDK_SUPRESS_3DS)) defaultValue = true;
    return sp.getBoolean(keySearch, defaultValue);
  }

  /**
   * String saved configuration.
   *
   * @param keySearch configuration to search on Shared Preferences
   * @return configuration selected
   */
  public String configSelectedString(String keySearch) {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    String selected = "";

    if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_LANG)) {
      selected = sp.getString(keySearch, DEFAULT_LANG_SDK);
    } else if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_CURRENCY)) {
      selected = sp.getString(keySearch, DEFAULT_CURRENCY_SDK);
    } else if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_ENVIRONMENT)) {
      selected = sp.getString(keySearch, EnvironmentConstants.getCurrentEnvironment());
    }

    return selected;
  }

  /**
   * String Set saved configuration.
   *
   * @param keySearch configuration to search on Shared Preferences
   * @return configuration selected
   */
  public Set<String> configSelectedStringSet(String keySearch) {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Set<String> selected = new HashSet<>();
    if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_DSRP)) {
      selected = sp.getStringSet(keySearch, defaultDsrpSDK());
    } else if (keySearch.equalsIgnoreCase(SettingsSaveConstants.SDK_CONFIG_CARDS)) {
      selected = sp.getStringSet(keySearch, defaultCardsSDK());
    }

    return selected;
  }

  /**
   * Default DSRP data
   */
  private Set<String> defaultDsrpSDK() {
    Set<String> defautltDsrp = new HashSet<>();
    defautltDsrp.add("ICC");
    defautltDsrp.add("UCAF");
    return defautltDsrp;
  }

  /**
   * Default selected cards
   */
  private Set<String> defaultCardsSDK() {
    Set<String> defautltCards = new HashSet<>();
    defautltCards.add("American");
    defautltCards.add("Discover");
    //defautltCards.add("JCB");
    defautltCards.add("MasterCard");
    defautltCards.add("Mestro");
    defautltCards.add("Diners");
    defautltCards.add("VISA");
    return defautltCards;
  }

  /**
   * Get currency saved on Shared Preferences.
   *
   * @return the currency saved
   */
  public String getCurrencySelected() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getString(SettingsSaveConstants.SDK_CONFIG_CURRENCY, DEFAULT_CURRENCY_SDK);
  }

  /**
   * Save data on login.
   *
   * @param loginObject login data {@link LoginObject}
   * @param forceSaveConfig check if the login is from checkout express on settings list
   */
  public void loginSave(LoginObject loginObject, boolean forceSaveConfig) {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Editor edit = sp.edit();
    edit.putBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, true);
    edit.putString(SettingsSaveConstants.LOGIN_USER_ID, loginObject.getUserId());
    edit.putString(SettingsSaveConstants.LOGIN_USERNAME, loginObject.getUsername());
    edit.putString(SettingsSaveConstants.LOGIN_FIRST_NAME, loginObject.getFirstName());
    edit.putString(SettingsSaveConstants.LOGIN_LAST_NAME, loginObject.getLastName());
    String hash = CryptoUtil.getSHAHash(
        (loginObject.getUsername() + loginObject.getPassword()).getBytes(Charset.forName("UTF-8")))
        .substring(0, 16);
    edit.putString(SettingsSaveConstants.MERCHANT_USER_ID, hash);
    edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, forceSaveConfig);

    edit.apply();
  }

  /**
   * Returns the Merchant User ID stored at login.
   *
   * @return Merchant User ID
   */
  public String getMerchantUserID() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getString(SettingsSaveConstants.MERCHANT_USER_ID, "");
  }

  // Add get merchant id method. clear that on logout.
  //username+password and hash. 16 chars.

  /**
   * Check if the user is logged.
   *
   * @return is logged
   */
  public boolean getIsLogged() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, false);
  }

  /**
   * Returns the loggedIn data.
   *
   * @return LoginObject with data except password.
   */
  public LoginObject getLoggedInData() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    LoginObject loginObject = new LoginObject();

    loginObject.setFirstName(sp.getString(SettingsSaveConstants.LOGIN_FIRST_NAME, ""));
    loginObject.setLastName(sp.getString(SettingsSaveConstants.LOGIN_LAST_NAME, ""));
    loginObject.setUserId(sp.getString(SettingsSaveConstants.LOGIN_USER_ID, ""));
    loginObject.setUsername(sp.getString(SettingsSaveConstants.LOGIN_USERNAME, ""));

    return loginObject;
  }

  /**
   * Get user id if is logged.
   *
   * @return the user id
   */
  public String getUserId() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getString(SettingsSaveConstants.LOGIN_USER_ID, "");
  }

  /**
   * Get suppress shipping selection.
   *
   * @return the suppress shipping
   */
  public boolean getSupressShipping() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getBoolean(SettingsSaveConstants.SDK_CONFIG_SUPRESS, false);
  }

  /**
   * Get masterpass or SRC selection.
   *
   * @return true if using masterpass
   */
  public boolean getUsingMasterpass() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getBoolean(SettingsSaveConstants.SDK_CONFIG_MASTERPASS, false);
  }

  /**
   * Get v7 or Commerce selection.
   *
   * @return true if using v7 APIs
   */
  public boolean getUsingOldApi() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getBoolean(SettingsSaveConstants.SDK_CONFIG_OLD_API, false);
  }

  /**
   * Get masterpass or SRC selection.
   *
   * @return true if using
   */
  public String getEnvironment() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getString(SettingsSaveConstants.SDK_CONFIG_ENVIRONMENT, "Sandbox");
  }

  /**
   * Get pairing id.
   *
   * @return the pairing id
   */
  public String getPairingId() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getString(SettingsSaveConstants.EXPRESS_PAIRING_ID, null);
  }

  /**
   * Get pairing id.
   *
   * @return the pairing transaction id
   */
  public String getPairingTransactionId() {
    SharedPreferences sharedPreferences =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sharedPreferences.getString(SettingsSaveConstants.EXPRESS_PAIRING_TRANSACTION_ID, null);
  }

  /**
   * Get express checkout.
   *
   * @return the express checkout
   */
  public boolean getExpressCheckout() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, false);
  }

  /**
   * Get pairing with checkout.
   *
   * @return the express checkout
   */
  public boolean getPairingWithCheckout() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    return sp.getBoolean(SettingsSaveConstants.SDK_WEB_CHECKOUT, false);
  }

  /**
   * Save pairing id.
   *
   * @param pairingId the pairing id
   */
  public void savePairingId(String pairingId) {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Editor edit = sp.edit();
    edit.putString(SettingsSaveConstants.EXPRESS_PAIRING_ID, pairingId);
    edit.apply();
  }

  /**
   * Save pairing id.
   *
   * @param pairingTransactionId the pairing id
   */
  public void savePairingTransactionId(String pairingTransactionId) {
    SharedPreferences sharedPreferences =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Editor edit = sharedPreferences.edit();
    edit.putString(SettingsSaveConstants.EXPRESS_PAIRING_TRANSACTION_ID, pairingTransactionId);
    edit.apply();
  }

  /**
   * Remove pairing id.
   */
  public void removePairingId() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Editor edit = sp.edit();
    edit.putString(SettingsSaveConstants.EXPRESS_PAIRING_ID, null);
    edit.apply();
  }

  /**
   * Remove logged and information related.
   *
   * @return true
   */
  public boolean removeLogged() {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Editor edit = sp.edit();
    edit.putBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, false);
    edit.putString(SettingsSaveConstants.LOGIN_USER_ID, "");
    edit.putString(SettingsSaveConstants.LOGIN_USERNAME, "");
    edit.putString(SettingsSaveConstants.LOGIN_FIRST_NAME, "");
    edit.putString(SettingsSaveConstants.LOGIN_LAST_NAME, "");
    edit.putString(SettingsSaveConstants.MERCHANT_USER_ID, "");
    edit.putString(SettingsSaveConstants.EXPRESS_PAIRING_ID, null);
    edit.putBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, false);
    edit.apply();
    return true;
  }

  /**
   * Save payment method list as json string in shared preference
   */
  public void savePaymentMethod(MasterpassPaymentMethod masterpassPaymentMethod) {
    List<MerchantPaymentMethod> savedPaymentMethodList = new ArrayList<>();
    if (masterpassPaymentMethod != null) {
      Gson gson = new Gson();
      savedPaymentMethodList = getPaymentMethod();
      if (savedPaymentMethodList != null) {
        savedPaymentMethodList =
            replacePaymentMethod(savedPaymentMethodList, masterpassPaymentMethod);
      } else {
        savedPaymentMethodList.add(
            new MerchantPaymentMethod(getByteArray(masterpassPaymentMethod.getPaymentMethodLogo()),
                masterpassPaymentMethod.getPaymentWalletId(),
                masterpassPaymentMethod.getPaymentMethodName(),
                masterpassPaymentMethod.getPaymentMethodLastFourDigits(),
                masterpassPaymentMethod.getPairingTransactionId()));
      }
      savePaymentMethodJsonString(gson.toJson(savedPaymentMethodList));
    }
  }

  /**
   * Get payment method list as json string from shared preference
   *
   * @return List of payment method
   */
  public List<MerchantPaymentMethod> getPaymentMethod() {
    List<MerchantPaymentMethod> paymentMethodList = new ArrayList<>();
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    String paymentMethodJsonStr = sp.getString(SettingsSaveConstants.PAYMENT_METHOD_KEY, null);
    if (paymentMethodJsonStr != null) {
      Gson gson = new Gson();
      StringReader reader = new StringReader(paymentMethodJsonStr);
      MerchantPaymentMethod[] paymentMethodArray =
          gson.fromJson(reader, MerchantPaymentMethod[].class);
      paymentMethodList = new ArrayList<>(Arrays.asList(paymentMethodArray));
    }
    return paymentMethodList;
  }

  /**
   * Removes given payment method from shared preference
   */
  public boolean removePaymentMethod(MerchantPaymentMethod paymentMethod) {
    Gson gson = new Gson();
    List<MerchantPaymentMethod> savedPaymentMethodList = getPaymentMethod();
    List<MerchantPaymentMethod> merchantPaymentMethodList = new ArrayList<>(savedPaymentMethodList);
    for (int i = 0; i < savedPaymentMethodList.size(); i++) {
      if (savedPaymentMethodList.get(i)
          .getPaymentMethodName()
          .equals(paymentMethod.getPaymentMethodName())) {
        merchantPaymentMethodList.remove(savedPaymentMethodList.get(i));
        break;
      }
    }
    return savePaymentMethodJsonString(gson.toJson(merchantPaymentMethodList));
  }

  public boolean savePaymentMethodJsonString(String paymentMethodJsonString) {
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    Editor editor = sp.edit();
    editor.putString(SettingsSaveConstants.PAYMENT_METHOD_KEY, paymentMethodJsonString);
    return editor.commit();
  }

  /**
   * save selected payment method to shared preference
   *
   * @param merchantPaymentMethod merchantpaymentmethod object
   */
  public boolean saveSelectedPaymentMethod(MerchantPaymentMethod merchantPaymentMethod) {

    if (merchantPaymentMethod != null) {
      SharedPreferences sp =
          mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
      Editor editor = sp.edit();
      editor.putString(SettingsSaveConstants.SELECTED_PAYMENT_METHOD_NAME,
          merchantPaymentMethod.getPaymentMethodName());
      return editor.commit();
    }
    return false;
  }

  /**
   * get the last selected payment method from shared preference
   */
  public MerchantPaymentMethod getSelectedPaymentMethod() {
    List<MerchantPaymentMethod> paymentMethods = getPaymentMethod();
    SharedPreferences sp =
        mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    if (getPaymentMethod().isEmpty()) {
      Editor editor = sp.edit();
      editor.putString(SettingsSaveConstants.SELECTED_PAYMENT_METHOD_NAME, null);
      editor.apply();
      return null;
    }
    String paymentMethodName =
        sp.getString(SettingsSaveConstants.SELECTED_PAYMENT_METHOD_NAME, null);

    for (MerchantPaymentMethod paymentMethod : paymentMethods) {
      if (paymentMethod.getPaymentMethodName().equals(paymentMethodName)) {
        return paymentMethod;
      }
    }
    return null;
  }

  private List<MerchantPaymentMethod> replacePaymentMethod(
      List<MerchantPaymentMethod> paymentMethodList,
      MasterpassPaymentMethod masterpassPaymentMethod) {
    List<MerchantPaymentMethod> merchantPaymentMethodList = new ArrayList<>(paymentMethodList);

    MerchantPaymentMethod paymentMethod =
        new MerchantPaymentMethod(getByteArray(masterpassPaymentMethod.getPaymentMethodLogo()),
            masterpassPaymentMethod.getPaymentWalletId(),
            masterpassPaymentMethod.getPaymentMethodName(),
            masterpassPaymentMethod.getPaymentMethodLastFourDigits(),
            masterpassPaymentMethod.getPairingTransactionId());
    boolean isPaymentExist = false;
    int position = 0;

    for (MerchantPaymentMethod merchantPayment : paymentMethodList) {
      if (merchantPayment.getPaymentMethodName().equals(paymentMethod.getPaymentMethodName())) {
        isPaymentExist = true;
        merchantPaymentMethodList.set(position, paymentMethod);
      }
      position++;
    }
    if (!isPaymentExist) {
      merchantPaymentMethodList.add(paymentMethod);
    }
    return merchantPaymentMethodList;
  }

  private byte[] getByteArray(Bitmap bitmap) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
    return outputStream.toByteArray();
  }
}
