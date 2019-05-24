package com.us.masterpass.merchantapp.domain.model;

import java.io.Serializable;

/**
 * Created by Sebastian Farias on 10/29/17.
 */
public class MasterpassPreCheckoutCardObject implements Serializable{

    private static final long serialVersionUID = -8566361733057649839L;
    private String preBrandName;
    private String preCardHolderName;
    private String preCardId;
    private String preExpiryYear;
    private String preExpiryMonth;
    private String preLastFour;
    private String preDefault;

    /**
     * Gets pre brand name.
     *
     * @return the pre brand name
     */
    public String getPreBrandName() {
        return preBrandName;
    }

    /**
     * Sets pre brand name.
     *
     * @param preBrandName the pre brand name
     */
    public void setPreBrandName(String preBrandName) {
        this.preBrandName = preBrandName;
    }

    /**
     * Gets pre card holder name.
     *
     * @return the pre card holder name
     */
    public String getPreCardHolderName() {
        return preCardHolderName;
    }

    /**
     * Sets pre card holder name.
     *
     * @param preCardHolderName the pre card holder name
     */
    public void setPreCardHolderName(String preCardHolderName) {
        this.preCardHolderName = preCardHolderName;
    }

    /**
     * Gets pre card id.
     *
     * @return the pre card id
     */
    public String getPreCardId() {
        return preCardId;
    }

    /**
     * Sets pre card id.
     *
     * @param preCardId the pre card id
     */
    public void setPreCardId(String preCardId) {
        this.preCardId = preCardId;
    }

    /**
     * Gets pre expiry year.
     *
     * @return the pre expiry year
     */
    public String getPreExpiryYear() {
        return preExpiryYear;
    }

    /**
     * Sets pre expiry year.
     *
     * @param preExpiryYear the pre expiry year
     */
    public void setPreExpiryYear(String preExpiryYear) {
        this.preExpiryYear = preExpiryYear;
    }

    /**
     * Gets pre expiry month.
     *
     * @return the pre expiry month
     */
    public String getPreExpiryMonth() {
        return preExpiryMonth;
    }

    /**
     * Sets pre expiry month.
     *
     * @param preExpiryMonth the pre expiry month
     */
    public void setPreExpiryMonth(String preExpiryMonth) {
        this.preExpiryMonth = preExpiryMonth;
    }

    /**
     * Gets pre last four.
     *
     * @return the pre last four
     */
    public String getPreLastFour() {
        return preLastFour;
    }

    /**
     * Sets pre last four.
     *
     * @param preLastFour the pre last four
     */
    public void setPreLastFour(String preLastFour) {
        this.preLastFour = preLastFour;
    }

    /**
     * Gets pre default.
     *
     * @return the pre default
     */
    public String getPreDefault() {
        return preDefault;
    }

    /**
     * Sets pre default.
     *
     * @param preDefault the pre default
     */
    public void setPreDefault(String preDefault) {
        this.preDefault = preDefault;
    }
}
