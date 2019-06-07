package com.us.masterpass.merchantapp.presentation.presenter.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import com.us.masterpass.merchantapp.domain.model.Item;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public interface CartPresenterInterface extends Presenter {

    /**
     * Result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     */
    void result(int requestCode, int resultCode);

    /**
     * Load items on cart.
     *
     * @param forceUpdate the force update
     */
    void loadItemsOnCart(boolean forceUpdate);

    /**
     * Add item.
     *
     * @param itemToAdd the item to add
     */
    void addItem(@NonNull Item itemToAdd);

    /**
     * Show items on cart.
     *
     * @param itemsOnCart the items on cart
     */
    void showItemsOnCart(List<Item> itemsOnCart);

    /**
     * Remove item.
     *
     * @param itemToRemove the item to remove
     */
    void removeItem(@NonNull Item itemToRemove);

    /**
     * Remove all item.
     *
     * @param itemRemoveAll the item remove all
     */
    void removeAllItem(@NonNull Item itemRemoveAll);

    /**
     * Show badge.
     */
    void showBadge();

    /**
     * Update badge.
     *
     * @param totalCartCount the total cart count
     */
    void updateBadge(String totalCartCount);

    /**
     * Total price.
     *
     * @param totalPrice the total price
     */
    void totalPrice(String totalPrice);

    /**
     * Subtotal price.
     *
     * @param subtotalPrice the subtotal price
     */
    void subtotalPrice(String subtotalPrice);

    /**
     * Tax price.
     *
     * @param taxPrice the tax price
     */
    void taxPrice(String taxPrice);

    /**
     * Back to item list.
     */
    void backToItemList();

    /**
     * Initialize masterpass merchant.
     *
     * @param configuration the configuration
     */
    void initializeMasterpassMerchant(Configuration configuration);

    /**
     * Initialize masterpass merchant.
     *
     * @param context the context
     */
    void initializeMasterpassMerchant(Context context);

    /**
     * Show masterpass button.
     */
    void showMasterpassButton();

    /**
     * Load confirmation.
     *
     * @param checkoutData          the checkout data
     */
    void loadConfirmation(HashMap<String, Object> checkoutData);

    /**
     * Is suppress shipping.
     *
     * @param suppressShipping the suppress shipping
     */
    void isSuppressShipping(boolean suppressShipping);
}
