package com.us.masterpass.merchantapp.data;

import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.domain.model.Item;
import java.util.List;

/**
 * Interface use to manage items {@link Item}
 * <p>
 * Created by Sebastian Farias on 08-10-17.
 */
public interface ItemDataSource {
    /**
     * Callback used on  Load items.
     */
    interface LoadItemsCallback {
        /**
         * On items loaded.
         *
         * @param items list of items
         */
        void onItemsLoaded(List<Item> items);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * Callback when Get item.
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
     * Callback when get item on cart.
     */
    interface GetItemOnCartCallback {
        /**
         * On item on cart.
         *
         * @param totalItem     total of items on cart
         * @param newItemOnCart new list of items {@link Item}
         * @param itemsOnCart   list of items {@link CartLocalObject}
         */
        void onItemOnCart(String totalItem, List<Item> newItemOnCart,
            List<CartLocalObject> itemsOnCart);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * Callback when get item on cart and return suppress shipping.
     */
    interface GetItemOnCartShippingOptionCallback {
        /**
         * On item on cart.
         *
         * @param totalItem        the total items on cart
         * @param newItemOnCart    new list of items {@link Item}
         * @param itemsOnCart      list of items {@link CartLocalObject}
         * @param suppressShipping is suppress shipping
         */
        void onItemOnCart(String totalItem, List<Item> newItemOnCart,
            List<CartLocalObject> itemsOnCart, boolean suppressShipping);

        /**
         * On data not available.
         */
        void onDataNotAvailable();
    }

    /**
     * Get items.
     *
     * @param callback {@link LoadItemsCallback}
     */
    void getItems(@NonNull LoadItemsCallback callback);

    /**
     * Get specific item with itemId.
     *
     * @param itemId   item id
     * @param callback {@link GetItemCallback}
     */
    void getItem(@NonNull String itemId, @NonNull GetItemCallback callback);

    /**
     * Add item.
     *
     * @param item     object to save {@link Item}
     * @param callback {@link GetItemOnCartCallback}
     */
    void addItem(@NonNull Item item, GetItemOnCartCallback callback);

    /**
     * Remove item.
     *
     * @param item     object to remove element {@link Item}
     * @param callback {@link GetItemOnCartCallback}
     */
    void removeItem(@NonNull Item item, GetItemOnCartCallback callback);

    /**
     * Remove all item.
     *
     * @param item     object to remove {@link Item}
     * @param callback {@link GetItemOnCartCallback}
     */
    void removeAllItem(@NonNull Item item, GetItemOnCartCallback callback);

    /**
     * Get items on cart.
     *
     * @param callback {@link GetItemOnCartShippingOptionCallback}
     */
    void getItemsOnCart(GetItemOnCartShippingOptionCallback callback);

    /**
     * Save item.
     *
     * @param item object to save {@link Item}
     */
    void saveItem(@NonNull Item item);

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
     * @param itemId id of selected item
     */
    void deleteItem(@NonNull String itemId);
}
