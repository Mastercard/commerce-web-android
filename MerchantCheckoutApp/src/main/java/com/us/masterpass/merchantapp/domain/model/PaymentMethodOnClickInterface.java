package com.us.masterpass.merchantapp.domain.model;

import com.us.masterpass.merchantapp.data.device.MerchantPaymentMethod;

/**
 * Payment method on click interface
 */
public interface PaymentMethodOnClickInterface {

  /**
   * On click of delete payment method
   */
  void deletePaymentMethod(MerchantPaymentMethod merchantPaymentMethod);

  /**
   * On click of edit payment method
   */
  void editPaymentMethod(MerchantPaymentMethod merchantPaymentMethod);

  /**
   * On click of a payment method
   */
  void selectPaymentMethod(MerchantPaymentMethod merchantPaymentMethod);

}
