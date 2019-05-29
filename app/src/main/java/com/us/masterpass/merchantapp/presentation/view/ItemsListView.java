package com.us.masterpass.merchantapp.presentation.view;

import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.presentation.presenter.ItemsPresenter;

import java.util.List;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public interface ItemsListView extends View<ItemsPresenter> {
    /**
     * Sets loading indicator.
     *
     * @param active the active
     */
    void setLoadingIndicator(boolean active);

    /**
     * Show items.
     *
     * @param items the items
     */
    void showItems(List<Item> items);

    /**
     * Show loading items error.
     */
    void showLoadingItemsError();

    /**
     * Update badge.
     *
     * @param totalCartCount the total cart count
     */
    void updateBadge(String totalCartCount);

    /**
     * Load cart activity.
     */
    void loadCartActivity();

    /**
     * Load cart activity show error.
     */
    void loadCartActivityShowError();

    /**
     * Load settings activity.
     */
    void loadSettingsActivity();
}
