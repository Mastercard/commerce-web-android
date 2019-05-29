package com.us.masterpass.merchantapp.presentation.view;

import android.app.Activity;
import android.widget.Button;

import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.presentation.presenter.CartPresenter;

import java.util.List;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public interface CartListView extends View<CartPresenter> {

    /**
     * Show items.
     *
     * @param itemsOnCart the items on cart
     */
    void showItems(List<Item> itemsOnCart);

    /**
     * Show badge.
     */
    void showBadge();

    /**
     * Hide badge.
     */
    void hideBadge();

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
     * Show masterpass button.
     *
     */
    void showMasterpassButton(Button masterpassButton);

    /**
     * Show error message .
     *
     * @param errorMessage
     */
    void showErrorMessage(String errorMessage);

    /**
     * Hide masterpass button.
     */
    void hideMasterpassButton();

    /**
     * Masterpass sdk initialized.
     */
    void masterpassSdkInitialized();

    /**
     * Show loading spinner.
     *
     * @param show the show
     */
    void showLoadingSpinner(boolean show);

    /**
     * Show confirmation screen.
     *
     * @param masterpassConfirmationObject the masterpass confirmation object
     */
    void showConfirmationScreen(MasterpassConfirmationObject masterpassConfirmationObject);

    /**
     * Is suppress shipping.
     *
     * @param suppressShipping the suppress shipping
     */
    void isSuppressShipping(boolean suppressShipping);

    /**
     *
     * @return the activity associated with this view
     */
    Activity getActivity();
}
