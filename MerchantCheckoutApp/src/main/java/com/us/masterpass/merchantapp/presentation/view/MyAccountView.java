package com.us.masterpass.merchantapp.presentation.view;

import com.us.masterpass.merchantapp.presentation.presenter.MyAccountPresenter;

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
