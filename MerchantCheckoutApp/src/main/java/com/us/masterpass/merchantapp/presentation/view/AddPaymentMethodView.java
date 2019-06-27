package com.us.masterpass.merchantapp.presentation.view;

import android.content.Context;

import com.mastercard.mp.checkout.MasterpassPaymentMethod;
import com.us.masterpass.merchantapp.data.device.MerchantPaymentMethod;
import com.us.masterpass.merchantapp.presentation.presenter.AddPaymentMethodPresenter;
import java.util.List;

/**
 * Interface to communicate back from PaymentMethodPresenter to AddPaymentMethodFragment
 */
public interface AddPaymentMethodView
    extends View<AddPaymentMethodPresenter>, MerchantCheckoutView {

  /**
   * Called by presenter to show a list of added payment method on view
   */
  void showPaymentMethod(List<MerchantPaymentMethod> paymentMethodList);

  /**
   * Shows error if add payment method call fails and throws an error
   */
  void showError(String error);

  /**
   * Callback when sdk is initialized successfully
   */
  void masterpassSdkInitialized();

  /**
   * This method provides context to presenter
   *
   * @return Context
   */
  Context getContext();

  /**
   * @param msg payment method is added or not
   */
  void paymentMethodStatus(String msg);

  /**
   * Shows error alert dialog
   */
  void showAlertDialog();

}
