package com.mastercard.testapp.domain.usecase.paymentMethod;

import com.mastercard.testapp.data.device.MerchantPaymentMethod;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.usecase.base.UseCase;

/**
 * Use case to get the last selected payment method during checkout
 */
public class GetSelectedPaymentMethodUseCase extends
    UseCase<GetSelectedPaymentMethodUseCase.RequestValues, GetSelectedPaymentMethodUseCase.ResponseValue> {

  private final MasterpassSdkCoordinator masterpassSdkCoordinator;

  public GetSelectedPaymentMethodUseCase(MasterpassSdkCoordinator masterpassSdkCoordinator) {
    this.masterpassSdkCoordinator = masterpassSdkCoordinator;
  }

  @Override protected void executeUseCase(RequestValues requestValues) {
    MerchantPaymentMethod paymentMethod = masterpassSdkCoordinator.getSelectedPaymentMethod();
    ResponseValue responseValue = new ResponseValue(paymentMethod);
    getUseCaseCallback().onSuccess(responseValue);
  }

  /**
   * The type Request Values.
   */
  public static final class RequestValues implements UseCase.RequestValues {

  }

  /**
   * The type Response Value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {

    private final MerchantPaymentMethod paymentMethod;

    public ResponseValue(MerchantPaymentMethod paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    public MerchantPaymentMethod getSelectedPaymentMethod() {
      return paymentMethod;
    }
  }
}
