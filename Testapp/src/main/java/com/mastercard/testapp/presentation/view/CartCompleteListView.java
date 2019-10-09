package com.mastercard.testapp.presentation.view;

import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.presentation.presenter.CartCompletePresenter;
import java.util.List;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public interface CartCompleteListView extends View<CartCompletePresenter> {

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
   * Is suppress shipping.
   *
   * @param suppressShipping the suppress shipping
   */
  void isSuppressShipping(boolean suppressShipping);
}
