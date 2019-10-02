package com.mastercard.testapp.domain;

/**
 * Created by Sebastian Farias on 17-10-17.
 *
 * Constants used by settings, set data for saved values, set options for list of items
 * - Language
 * - Cards
 * - DSRP
 * - Currency
 */
public class SettingsConstants {
  /**
   * The constant TYPE_CARD.
   */
  public static final String TYPE_CARD = "CARDS";
  /**
   * The constant TYPE_ARROW.
   */
  public static final String TYPE_ARROW = "ARROW";
  /**
   * The constant TYPE_SWITCH.
   */
  public static final String TYPE_SWITCH = "SWITCH";

  /**
   * The constant ITEM_CARDS.
   */
  public static final String ITEM_CARDS = "ALLOWED CARD TYPE";
  /**
   * The constant ITEM_LANGUAGE.
   */
  public static final String ITEM_LANGUAGE = "LANGUAGE";
  /**
   * The constant ITEM_CURRENCY.
   */
  public static final String ITEM_CURRENCY = "CURRENCY";
  /**
   * The constant ITEM_SHIPPING.
   */
  public static final String ITEM_SHIPPING = "SHIPPING";
  /**
   * The constant ITEM_MASTERPASS.
   */
  public static final String ITEM_MASTERPASS = "MASTERPASS CHECKOUT";
  /**
   * The constant ITEM_SUPRESS.
   */
  public static final String ITEM_SUPRESS = "SUPPRESS SHIPPING";
  /**
   * The constant ITEM_EXPRESS.
   */
  public static final String ITEM_EXPRESS = "ENABLE EXPRESS CHECKOUT";
  /**
   * The constant PAYMENT_METHOD.
   */
  public static final String ITEM_PAYMENT_METHOD = "ENABLE PAYMENT METHOD";
  /**
   * The constant SDK_WEB_CHECKOUT.
   */
  public static final String ENABLE_WEB_CHECKOUT = "ENABLE WEB PAIRING WITH CHECKOUT";
  /**
   * The constant PAIRING_ONLY
   */
  public static final String PAIRING_ONLY = "PAIRING ONLY";
  /**
   * The constant ITEM_DSRP.
   */
  public static final String ITEM_DSRP = "TOKENIZATION";
  /**
   * The constant ITEM_PAYMENT.
   */
  public static final String ITEM_PAYMENT = "PAYMENT METHODS";
  /**
   * The constant ITEM_PAYMENT.
   */
  public static final String SUPRESS_3DS = "SUPPRESS 3DS";

  /**
   * The enum Sdk lang.
   */
  public enum SDK_LANG {

    ENGLISH("English(UK)", "en_GB"), ENGLISH_IN("English(IN)", "en_IN"), ENGLISH_US("English(US)",
        "en_US"), PORTUGUESE("Portuguese(BR)", "pt_BR"), SPANISH("Spanish(MX)", "es_MX"), FRENCH(
        "French(CA)", "fr_CA"), ENGLISH_CA("English(CA)", "en_CA"), ENGLISH_TH("English(TH)",
        "en_TH"), SPANISH_AR("Spanish(AR)", "es_AR"), SPANISH_CO("Spanish(CO)",
        "es_CO"), ENGLISH_HK("English(HK)", "en_HK"), ENGLISH_SG("English(SG)",
        "en_SG"), ENGLISH_PH("English(PH)", "en_PH"), ENGLISH_MY("English(MY)",
        "en_MY"), ENGLISH_AU("English(AU)", "en_AU"), ENGLISH_NZ("English(NZ)", "en_NZ");

    private String textDisplay;
    private String configToSave;

    SDK_LANG(String textDisplay, String configToSave) {
      this.textDisplay = textDisplay;
      this.configToSave = configToSave;
    }

    /**
     * Gets text display.
     *
     * @return the text display
     */
    public String getTextDisplay() {
      return textDisplay;
    }

    /**
     * Gets config to save.
     *
     * @return the config to save
     */
    public String getConfigToSave() {
      return configToSave;
    }
  }

  /**
   * The enum Sdk currency.
   */
  public enum SDK_CURRENCY {
    /**
     * Usd sdk currency.
     */
    USD("USD", "USD", "840"),
    /**
     * Inr sdk currency.
     */
    INR("INR", "INR", "356"),
    /**
     * The Gbp.
     */
    GBP("UK Pound", "GBP", "826"),
    /**
     * The Cad.
     */
    CAD("Canadian Dollar", "CAD", "124");

    private String textDisplay;
    private String configToSave;
    private String currencyNumber;

    SDK_CURRENCY(String textDisplay, String configToSave, String currencyNumber) {
      this.textDisplay = textDisplay;
      this.configToSave = configToSave;
      this.currencyNumber = currencyNumber;
    }

    /**
     * Gets text display.
     *
     * @return the text display
     */
    public String getTextDisplay() {
      return textDisplay;
    }

    /**
     * Gets config to save.
     *
     * @return the config to save
     */
    public String getConfigToSave() {
      return configToSave;
    }

    /**
     * Gets Curreny Number.
     *
     * @return the Curreny Number
     */
    public String getCurrencyNumber() {
      return currencyNumber;
    }
  }

  /**
   * The enum Sdk dsrp.
   */
  public enum SDK_DSRP {
    /**
     * Icc sdk dsrp.
     */
    ICC("ICC", "ICC"),
    /**
     * Ucaf sdk dsrp.
     */
    UCAF("UCAF", "UCAF");

    private String textDisplay;
    private String configToSave;

    SDK_DSRP(String textDisplay, String configToSave) {
      this.textDisplay = textDisplay;
      this.configToSave = configToSave;
    }

    /**
     * Gets text display.
     *
     * @return the text display
     */
    public String getTextDisplay() {
      return textDisplay;
    }

    /**
     * Gets config to save.
     *
     * @return the config to save
     */
    public String getConfigToSave() {
      return configToSave;
    }
  }

  /**
   * The enum Sdk cards.
   */
  public enum SDK_CARDS {
    /**
     * American sdk cards.
     */
    AMERICAN("American", "American", "american_express_settings_icon"),
    /**
     * Discover sdk cards.
     */
    DISCOVER("Discover", "Discover", "discover_settings_icon"),
    /**
     * Jcb sdk cards.
     */
    //JCB("JCB", "JCB","jcb_settings_icon"),
    /**
     * Mastercard sdk cards.
     */
    MASTERCARD("MasterCard", "MasterCard", "mastercard_settings_icon"),

    /**
     * Mestro sdk cards
     */
    MESTRO("Mestro", "Mestro", "maestro_pill"),

    /**
     * Diners sdk cards
     */
    DINERS("Diners", "Diners", "diners_club_pill"),

    /**
     * Visa sdk cards.
     */
    VISA("VISA", "VISA", "visa_settings_icon");

    private String textDisplay;
    private String configToSave;
    private String imageToDisplay;

    SDK_CARDS(String textDisplay, String configToSave, String imageToDisplay) {
      this.textDisplay = textDisplay;
      this.configToSave = configToSave;
      this.imageToDisplay = imageToDisplay;
    }

    /**
     * Gets text display.
     *
     * @return the text display
     */
    public String getTextDisplay() {
      return textDisplay;
    }

    /**
     * Gets config to save.
     *
     * @return the config to save
     */
    public String getConfigToSave() {
      return configToSave;
    }

    /**
     * Gets image to display.
     *
     * @return the image to display
     */
    public String getImageToDisplay() {
      return imageToDisplay;
    }
  }
}
