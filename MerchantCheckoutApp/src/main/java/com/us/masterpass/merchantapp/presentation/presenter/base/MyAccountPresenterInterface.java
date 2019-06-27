package com.us.masterpass.merchantapp.presentation.presenter.base;

public interface MyAccountPresenterInterface extends Presenter {

  /**
   * Logout button pressed.
   */
  void logoutButton();

  /**
   * Logout.
   */
  void logout();

  /**
   * Items activity.
   */
  void loadItemsActivity();

  /**
   * Loads Added Payment Method screen
   */
  void loadPaymentMethod();

  /**
   * Checks if Payment method toggle is enabled in settings screen or not
   */
  void isPaymentMethodEnabled();
}
