package com.mastercard.testapp.presentation.presenter.base;

import androidx.annotation.NonNull;
import com.mastercard.testapp.domain.model.Item;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public interface ItemsPresenterInterface extends Presenter {

  /**
   * Result.
   *
   * @param requestCode the request code
   * @param resultCode the result code
   */
  void result(int requestCode, int resultCode);

  /**
   * Load items.
   *
   * @param forceUpdate the force update
   */
  void loadItems(boolean forceUpdate);

  /**
   * Add item.
   *
   * @param itemToAdd the item to add
   */
  void addItem(@NonNull Item itemToAdd);

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
   * Load cart activity.
   */
  void loadCartActivity();

  /**
   * Load cart activity show error.
   */
  void loadCartActivityShowError();

  /**
   * Load settings.
   */
  void loadSettings();

  /**
   * Gets items on cart.
   */
  void getItemsOnCart();

  /**
   * Load login activity.
   */
  void loadLoginActivity();

  /**
   * Logout.
   */
  void logout();
}