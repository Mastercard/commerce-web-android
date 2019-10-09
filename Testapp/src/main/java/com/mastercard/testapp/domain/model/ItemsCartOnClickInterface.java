package com.mastercard.testapp.domain.model;

import com.mastercard.testapp.data.device.CartLocalObject;

/**
 * Created by Sebastian Farias on 08-10-17.
 * <p>
 * Options items on click
 */
public interface ItemsCartOnClickInterface {

  /**
   * On add items cart.
   *
   * @param addedItems the added items
   */
  void onAddItemsCart(CartLocalObject addedItems);

  /**
   * On remove items cart.
   *
   * @param removedItems the removed items
   */
  void onRemoveItemsCart(CartLocalObject removedItems);
}
