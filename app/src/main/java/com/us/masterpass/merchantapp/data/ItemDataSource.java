package com.us.masterpass.merchantapp.data;


import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.domain.model.Item;

import java.util.List;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public interface ItemDataSource {
    /**
     * The interface Load items callback.
     */
    interface LoadItemsCallback {
        /**
         * On items loaded.
         *
         * @param items the items
         */
        void onItemsLoaded(List<Item> items);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface Get item callback.
     */
    interface GetItemCallback {
        /**
         * On item loaded.
         *
         * @param item the item
         */
        void onItemLoaded(Item item);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface Get item on cart callback.
     */
    interface GetItemOnCartCallback {
        /**
         * On item on cart.
         *
         * @param totalItem     the total item
         * @param newItemOnCart the new item on cart
         * @param itemsOnCart   the items on cart
         */
        void onItemOnCart(String totalItem, List<Item> newItemOnCart,
            List<CartLocalObject> itemsOnCart);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface Get item on cart shipping option callback.
     */
    interface GetItemOnCartShippingOptionCallback {
        /**
         * On item on cart.
         *
         * @param totalItem        the total item
         * @param newItemOnCart    the new item on cart
         * @param itemsOnCart      the items on cart
         * @param suppressShipping the suppress shipping
         */
        void onItemOnCart(String totalItem, List<Item> newItemOnCart,
            List<CartLocalObject> itemsOnCart, boolean suppressShipping);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * Gets items.
     *
     * @param callback the callback
     */
    void getItems(@NonNull LoadItemsCallback callback);

    /**
     * Gets item.
     *
     * @param itemId   the item id
     * @param callback the callback
     */
    void getItem(@NonNull String itemId, @NonNull GetItemCallback callback);

    /**
     * Add item.
     *
     * @param item     the item
     * @param callback the callback
     */
    void addItem(@NonNull Item item, GetItemOnCartCallback callback);

    /**
     * Remove item.
     *
     * @param item     the item
     * @param callback the callback
     */
    void removeItem(@NonNull Item item, GetItemOnCartCallback callback);

    /**
     * Remove all item.
     *
     * @param item     the item
     * @param callback the callback
     */
    void removeAllItem(@NonNull Item item, GetItemOnCartCallback callback);

    /**
     * Gets items on cart.
     *
     * @param callback the callback
     */
    void getItemsOnCart(GetItemOnCartShippingOptionCallback callback);

    /**
     * Save item.
     *
     * @param item the item
     */
    void saveItem(@NonNull Item item);

    /**
     * Complete item.
     *
     * @param proud the proud
     */
    void completeItem(@NonNull Item proud);

    /**
     * Complete item.
     *
     * @param itemId the item id
     */
    void completeItem(@NonNull String itemId);

    /**
     * Activate item.
     *
     * @param item the item
     */
    void activateItem(@NonNull Item item);

    /**
     * Activate item.
     *
     * @param itemId the item id
     */
    void activateItem(@NonNull String itemId);

    /**
     * Clear completed items.
     */
    void clearCompletedItems();

    /**
     * Refresh items.
     */
    void refreshItems();

    /**
     * Delete all items.
     */
    void deleteAllItems();

    /**
     * Delete item.
     *
     * @param itemId the item id
     */
    void deleteItem(@NonNull String itemId);
}
