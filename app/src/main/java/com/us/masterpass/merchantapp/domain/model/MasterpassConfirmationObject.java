package com.us.masterpass.merchantapp.domain.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sebastian Farias on 10/29/17.
 */
public class MasterpassConfirmationObject implements Serializable{

    private static final long serialVersionUID = -9217503993122709888L;
    private String cardBrandId;
    private String cardBrandName;
    private String cardAccountNumber;
    private String cardAccountNumberHidden;
    private String cardHolderName;
    private String shippingLine1;
    private String shippingLine2;
    private String shippingLine3;
    private String shippingLine4;
    private String shippingLine5;
    private String shippingCity;
    private String cartId;
    private String completeTransactionId;
    private String completeAmount;
    private String completeCurrency;

    private String pairingId;
    private boolean expressCheckoutEnable;
    private List<MasterpassPreCheckoutCardObject> listPreCheckoutCard;
    private List<MasterpassPreCheckoutShippingObject> listPreCheckoutShipping;
    private String preCheckoutTransactionId;
    private double doCheckoutAmount;
    private String doCheckoutCardId;
    private String doCheckoutShippingAddressId;
    private boolean doCheckoutSupressShipping;

    /**
     * Gets card brand id.
     *
     * @return the card brand id
     */
    public String getCardBrandId() {
        return cardBrandId;
    }

    /**
     * Sets card brand id.
     *
     * @param cardBrandId the card brand id
     */
    public void setCardBrandId(String cardBrandId) {
        this.cardBrandId = cardBrandId;
    }

    /**
     * Gets card brand name.
     *
     * @return the card brand name
     */
    public String getCardBrandName() {
        return cardBrandName;
    }

    /**
     * Sets card brand name.
     *
     * @param cardBrandIName the card brand i name
     */
    public void setCardBrandName(String cardBrandIName) {
        this.cardBrandName = cardBrandIName;
    }

    /**
     * Gets card account number.
     *
     * @return the card account number
     */
    public String getCardAccountNumber() {
        return cardAccountNumber;
    }

    /**
     * Sets card account number.
     *
     * @param cardAccountNumber the card account number
     */
    public void setCardAccountNumber(String cardAccountNumber) {
        this.cardAccountNumber = cardAccountNumber;
    }

    /**
     * Gets card account number hidden.
     *
     * @return the card account number hidden
     */
    public String getCardAccountNumberHidden() {
        return cardAccountNumberHidden;
    }

    /**
     * Sets card account number hidden.
     *
     * @param cardAccountNumberHidden the card account number hidden
     */
    public void setCardAccountNumberHidden(String cardAccountNumberHidden) {
        this.cardAccountNumberHidden = cardAccountNumberHidden;
    }

    /**
     * Gets card holder name.
     *
     * @return the card holder name
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * Sets card holder name.
     *
     * @param cardHolderName the card holder name
     */
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    /**
     * Gets shipping line 1.
     *
     * @return the shipping line 1
     */
    public String getShippingLine1() {
        return shippingLine1;
    }

    /**
     * Sets shipping line 1.
     *
     * @param shippingLine1 the shipping line 1
     */
    public void setShippingLine1(String shippingLine1) {
        this.shippingLine1 = shippingLine1;
    }

    /**
     * Gets shipping line 2.
     *
     * @return the shipping line 2
     */
    public String getShippingLine2() {
        return shippingLine2;
    }

    /**
     * Sets shipping line 2.
     *
     * @param shippingLine2 the shipping line 2
     */
    public void setShippingLine2(String shippingLine2) {
        this.shippingLine2 = shippingLine2;
    }

    /**
     * Gets shipping line 3.
     *
     * @return the shipping line 3
     */
    public String getShippingLine3() {
        return shippingLine3;
    }

    /**
     * Sets shipping line 3.
     *
     * @param shippingLine3 the shipping line 3
     */
    public void setShippingLine3(String shippingLine3) {
        this.shippingLine3 = shippingLine3;
    }

    /**
     * Gets shipping line 4.
     *
     * @return the shipping line 4
     */
    public String getShippingLine4() {
        return shippingLine4;
    }

    /**
     * Sets shipping line 4.
     *
     * @param shippingLine4 the shipping line 4
     */
    public void setShippingLine4(String shippingLine4) {
        this.shippingLine4 = shippingLine4;
    }

    /**
     * Gets shipping line 5.
     *
     * @return the shipping line 5
     */
    public String getShippingLine5() {
        return shippingLine5;
    }

    /**
     * Sets shipping line 5.
     *
     * @param shippingLine5 the shipping line 5
     */
    public void setShippingLine5(String shippingLine5) {
        this.shippingLine5 = shippingLine5;
    }

    /**
     * Gets shipping city.
     *
     * @return the shipping city
     */
    public String getShippingCity() {
        return shippingCity;
    }

    /**
     * Sets shipping city.
     *
     * @param shippingCity the shipping city
     */
    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    /**
     * Gets cart id.
     *
     * @return the cart id
     */
    public String getCartId() {
        return cartId;
    }

    /**
     * Sets cart id.
     *
     * @param cartId the cart id
     */
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    /**
     * Gets complete transaction id.
     *
     * @return the complete transaction id
     */
    public String getCompleteTransactionId() {
        return completeTransactionId;
    }

    /**
     * Sets complete transaction id.
     *
     * @param completeTransactionId the complete transaction id
     */
    public void setCompleteTransactionId(String completeTransactionId) {
        this.completeTransactionId = completeTransactionId;
    }

    /**
     * Gets complete amount.
     *
     * @return the complete amount
     */
    public String getCompleteAmount() {
        return completeAmount;
    }

    /**
     * Sets complete amount.
     *
     * @param completeAmount the complete amount
     */
    public void setCompleteAmount(String completeAmount) {
        this.completeAmount = completeAmount;
    }

    /**
     * Gets complete currency.
     *
     * @return the complete currency
     */
    public String getCompleteCurrency() {
        return completeCurrency;
    }

    /**
     * Sets complete currency.
     *
     * @param completeCurrency the complete currency
     */
    public void setCompleteCurrency(String completeCurrency) {
        this.completeCurrency = completeCurrency;
    }

    /**
     * Gets pairing id.
     *
     * @return the pairing id
     */
    public String getPairingId() {
        return pairingId;
    }

    /**
     * Sets pairing id.
     *
     * @param pairingId the pairing id
     */
    public void setPairingId(String pairingId) {
        this.pairingId = pairingId;
    }

    /**
     * Gets list pre checkout card.
     *
     * @return the list pre checkout card
     */
    public List<MasterpassPreCheckoutCardObject> getListPreCheckoutCard() {
        return listPreCheckoutCard;
    }

    /**
     * Sets list pre checkout card.
     *
     * @param listPreCheckoutCard the list pre checkout card
     */
    public void setListPreCheckoutCard(List<MasterpassPreCheckoutCardObject> listPreCheckoutCard) {
        this.listPreCheckoutCard = listPreCheckoutCard;
    }

    /**
     * Gets list pre checkout shipping.
     *
     * @return the list pre checkout shipping
     */
    public List<MasterpassPreCheckoutShippingObject> getListPreCheckoutShipping() {
        return listPreCheckoutShipping;
    }

    /**
     * Sets list pre checkout shipping.
     *
     * @param listPreCheckoutShipping the list pre checkout shipping
     */
    public void setListPreCheckoutShipping(List<MasterpassPreCheckoutShippingObject> listPreCheckoutShipping) {
        this.listPreCheckoutShipping = listPreCheckoutShipping;
    }

    /**
     * Gets pre checkout transaction id.
     *
     * @return the pre checkout transaction id
     */
    public String getPreCheckoutTransactionId() {
        return preCheckoutTransactionId;
    }

    /**
     * Sets pre checkout transaction id.
     *
     * @param preCheckoutTransactionId the pre checkout transaction id
     */
    public void setPreCheckoutTransactionId(String preCheckoutTransactionId) {
        this.preCheckoutTransactionId = preCheckoutTransactionId;
    }

    /**
     * Gets do checkout amount.
     *
     * @return the do checkout amount
     */
    public double getDoCheckoutAmount() {
        return doCheckoutAmount;
    }

    /**
     * Sets do checkout amount.
     *
     * @param doCheckoutAmount the do checkout amount
     */
    public void setDoCheckoutAmount(double doCheckoutAmount) {
        this.doCheckoutAmount = doCheckoutAmount;
    }

    /**
     * Gets do checkout card id.
     *
     * @return the do checkout card id
     */
    public String getDoCheckoutCardId() {
        return doCheckoutCardId;
    }

    /**
     * Sets do checkout card id.
     *
     * @param doCheckoutCardId the do checkout card id
     */
    public void setDoCheckoutCardId(String doCheckoutCardId) {
        this.doCheckoutCardId = doCheckoutCardId;
    }

    /**
     * Gets do checkout shipping address id.
     *
     * @return the do checkout shipping address id
     */
    public String getDoCheckoutShippingAddressId() {
        return doCheckoutShippingAddressId;
    }

    /**
     * Sets do checkout shipping address id.
     *
     * @param doCheckoutShippingAddressId the do checkout shipping address id
     */
    public void setDoCheckoutShippingAddressId(String doCheckoutShippingAddressId) {
        this.doCheckoutShippingAddressId = doCheckoutShippingAddressId;
    }

    /**
     * Is do checkout supress shipping boolean.
     *
     * @return the boolean
     */
    public boolean isDoCheckoutSupressShipping() {
        return doCheckoutSupressShipping;
    }

    /**
     * Sets do checkout supress shipping.
     *
     * @param doCheckoutSupressShipping the do checkout supress shipping
     */
    public void setDoCheckoutSupressShipping(boolean doCheckoutSupressShipping) {
        this.doCheckoutSupressShipping = doCheckoutSupressShipping;
    }
}
