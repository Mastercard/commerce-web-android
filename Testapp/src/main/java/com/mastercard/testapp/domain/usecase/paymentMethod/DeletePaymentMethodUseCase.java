package com.mastercard.testapp.domain.usecase.paymentMethod;

import com.mastercard.testapp.data.device.MerchantPaymentMethod;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkInterface;
import com.mastercard.testapp.domain.usecase.base.UseCase;

/**
 * UseCase for deletePaymentMethod
 */

public class DeletePaymentMethodUseCase extends
    UseCase<DeletePaymentMethodUseCase.RequestValues, DeletePaymentMethodUseCase.ResponseValue> {

  public DeletePaymentMethodUseCase() {
  }

  @Override protected void executeUseCase(RequestValues requestValues) {
    MasterpassSdkCoordinator.getInstance()
        .deletePaymentMethod(requestValues.getPaymentMethod(),
            new MasterpassSdkInterface.DeleteMasterpassPaymentMethod() {
              @Override public void sdkResponseSuccess() {
                getUseCaseCallback().onSuccess(null);
              }

              @Override public void sdkResponseError() {
                getUseCaseCallback().onError();
              }
            });
  }

  public static final class RequestValues implements UseCase.RequestValues {
    private final MerchantPaymentMethod paymentMethod;

    public RequestValues(MerchantPaymentMethod paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    public MerchantPaymentMethod getPaymentMethod() {
      return paymentMethod;
    }
  }

  public static final class ResponseValue implements UseCase.ResponseValue {

  }
}
