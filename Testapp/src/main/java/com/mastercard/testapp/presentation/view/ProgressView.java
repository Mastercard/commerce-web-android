package com.mastercard.testapp.presentation.view;

/**
 * Interface to interact with progress showing view.
 */
interface ProgressView {
  /**
   * Shows progress dialog
   */
  void showProgress();

  /**
   * Hides the ongoing progress dialog.
   */
  void hideProgress();
}
