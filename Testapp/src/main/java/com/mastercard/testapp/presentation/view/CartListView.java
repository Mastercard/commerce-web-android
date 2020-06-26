package com.mastercard.testapp.presentation.view;

import android.content.Context;
import com.mastercard.commerce.CheckoutButton;
import com.mastercard.mp.checkout.MasterpassButton;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.presentation.presenter.CartPresenter;
import java.util.List;

public interface CartListView extends View<CartPresenter>, MerchantCheckoutView {

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
   * Show masterpass button.
   *
   * @param masterpassButton the masterpass button
   */
  void showMasterpassButton(MasterpassButton masterpassButton);

  /**
   * Show SRC button.
   *
   * @param checkoutButton the SRC button
   */
  void showSRCButton(CheckoutButton checkoutButton);

  /**
   * Back to item list.
   */
  void backToItemList();

  /**
   * Hide masterpass button.
   */
  void hideMasterpassButton();

  /**
   * Masterpass sdk initialized.
   */
  void masterpassSdkInitialized();

  /**
   * Show confirmation screen.
   *
   * @param masterpassConfirmationObject the masterpass confirmation object
   */
  void showConfirmationScreen(MasterpassConfirmationObject masterpassConfirmationObject);

  /**
   * Show confirmation pairing screen.
   *
   * @param masterpassPreCheckoutConfirmationObject the masterpass pre checkout confirmation object
   */
  void showConfirmationPairingScreen(
      MasterpassConfirmationObject masterpassPreCheckoutConfirmationObject);

  /**
   * Is suppress shipping.
   *
   * @param suppressShipping the suppress shipping
   */
  void isSuppressShipping(boolean suppressShipping);

  /**
   * Is payment method enable in settings
   *
   * @param isPaymentMethodEnable payment method enable boolean
   */
  void setPaymentMethodVisibility(boolean isPaymentMethodEnable);

  /**
   * go to add payment method screen
   */
  void showPaymentMethodScreen();

  /**
   * @param paymentMethodLogo logo of the payment method
   * @param paymentMethod selected
   * @param paymentMethodLastFourDigits last 4 digits of payment card supported by the payment
   * method
   */
  void setPaymentMethod(byte[] paymentMethodLogo, String paymentMethod,
      String paymentMethodLastFourDigits);

  /**
   * @param error error
   */
  void showError(String error);

  @Override void showError();

  @Override void showProgress();

  @Override void hideProgress();

  @Override void setPresenter(CartPresenter presenter);

  Context getContext();
}
