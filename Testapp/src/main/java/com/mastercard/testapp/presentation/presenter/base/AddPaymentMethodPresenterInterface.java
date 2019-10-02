package com.mastercard.testapp.presentation.presenter.base;

/**
 * Interface to communicate between AddPaymentMethodFragment and PaymentMethodPresenter
 */
public interface AddPaymentMethodPresenterInterface extends Presenter {

  /**
   * Adds payment method requested by Merchant
   */
  void addPaymentMethod();

  /**
   * Initialize Masterpass Merchant Sdk
   */
  void initializeMasterpassMerchant();

  /**
   * Retrieves a list of added payment methods
   */
  void retrievePaymentMethods();
}
