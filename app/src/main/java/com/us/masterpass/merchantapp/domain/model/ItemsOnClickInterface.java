package com.us.masterpass.merchantapp.domain.model;

/**
 * Created by Sebastian Farias on 08-10-17.
 * Options items on click
 */
public interface ItemsOnClickInterface {

    /**
     * On add items cart.
     *
     * @param addedItems the added items
     */
    void onAddItemsCart(Item addedItems);

    /**
     * On remove items cart.
     *
     * @param removedItems the removed items
     */
    void onRemoveItemsCart(Item removedItems);

    /**
     * On remove all items.
     *
     * @param removeAllItems the remove all items
     */
    void onRemoveAllItems(Item removeAllItems);
}
