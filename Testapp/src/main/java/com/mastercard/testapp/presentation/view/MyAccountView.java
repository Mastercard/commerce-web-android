package com.mastercard.testapp.presentation.view;

import com.mastercard.testapp.presentation.presenter.MyAccountPresenter;

public interface MyAccountView extends View<MyAccountPresenter> {

  /**
   * Load login activity.
   */
  void loadLoginActivity();

  /**
   * Load login activity.
   */
  void loadItemsActivity();

  void showConfirmLogoutDialog();

  /**
   * Shows Payment Method screen
   */
  void showPaymentMethodScreen();

  /**
   * Sets Payment Method button visibility
   */
  void setPaymentMethodVisibility(boolean visibility);
}
