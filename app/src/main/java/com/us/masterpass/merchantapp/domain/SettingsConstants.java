package com.us.masterpass.merchantapp.domain;

/**
 * Created by Sebastian Farias on 17-10-17.
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
     * The constant ITEM_SUPRESS.
     */
    public static final String ITEM_SUPRESS = "SUPRESS SHIPPING";
    /**
     * The constant ITEM_DSRP.
     */
    public static final String ITEM_DSRP = "TOKENIZATION";
    /**
     * The enum Sdk lang.
     */
    public enum SDK_LANG {
        /**
         * English sdk lang.
         */
        ENGLISH("English", "en-US"), /**
         * French sdk lang.
         */
        FRENCH("French", "fr-FR");

        private String textDisplay;
        private String configToSave;

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

        SDK_LANG(String textDisplay, String configToSave) {
            this.textDisplay = textDisplay;
            this.configToSave = configToSave;
        }
    }

    /**
     * The enum Sdk currency.
     */
    public enum SDK_CURRENCY {
        /**
         * Usd sdk currency.
         */
        USD("USD", "USD"), /**
         * Inr sdk currency.
         */
        INR("INR", "INR"), /**
         * The Gbp.
         */
        GBP("UK Pound", "GBP"), /**
         * The Cad.
         */
        CAD("Canadian Dollar", "CAD");

        private String textDisplay;
        private String configToSave;

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

        SDK_CURRENCY(String textDisplay, String configToSave) {
            this.textDisplay = textDisplay;
            this.configToSave = configToSave;
        }
    }

    /**
     * The enum Sdk dsrp.
     */
    public enum SDK_DSRP {
        /**
         * Icc sdk dsrp.
         */
        ICC("ICC", "ICC"), /**
         * Ucaf sdk dsrp.
         */
        UCAF("UCAF", "UCAF");

        private String textDisplay;
        private String configToSave;

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

        SDK_DSRP(String textDisplay, String configToSave) {
            this.textDisplay = textDisplay;
            this.configToSave = configToSave;
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
        DISCOVER("Discover", "Discover","discover_settings_icon"),
        /**
         * Jcb sdk cards.
         */
        JCB("JCB", "JCB","jcb_settings_icon"),
        /**
         * Mastercard sdk cards.
         */
        MASTERCARD("MasterCard", "MasterCard","mastercard_settings_icon"),
        /**
         * Unionpay sdk cards.
         */
        UNIONPAY("Unionpay", "Unionpay","unionpay_settings_icon"),
        /**
         * Visa sdk cards.
         */
        VISA("VISA","VISA","visa_settings_icon");

        private String textDisplay;
        private String configToSave;
        private String imageToDisplay;

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


        SDK_CARDS(String textDisplay, String configToSave, String imageToDisplay) {
            this.textDisplay = textDisplay;
            this.configToSave = configToSave;
            this.imageToDisplay = imageToDisplay;
        }
    }

}
