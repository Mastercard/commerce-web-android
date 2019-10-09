package com.mastercard.testapp.presentation.view;

import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.presentation.presenter.CartConfirmationPresenter;
import java.util.List;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public interface CartConfirmationListView
    extends View<CartConfirmationPresenter>, MerchantCheckoutView {

  /**
   * Show items.
   *
   * @param itemsOnCart the items on cart
   */
  void showItems(List<Item> itemsOnCart);

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
   * Show complete screen.
   *
   * @param masterpassConfirmationObject the masterpass confirmation object
   */
  void showCompleteScreen(MasterpassConfirmationObject masterpassConfirmationObject);

  /**
   * Is suppress shipping.
   *
   * @param suppressShipping the suppress shipping
   */
  void isSuppressShipping(boolean suppressShipping);

  /**
   * Express checkout.
   *
   * @param masterpassConfirmationObject the masterpass confirmation object
   */
  void expressCheckout(MasterpassConfirmationObject masterpassConfirmationObject);
}
