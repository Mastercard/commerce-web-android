package com.us.masterpass.merchantapp.domain.usecase.paymentMethod;

import com.us.masterpass.merchantapp.data.device.MerchantPaymentMethod;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkInterface;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;

/**
 * Use case to save selected payment method for checkout
 */
public class SaveSelectedPaymentMethodUseCase extends
    UseCase<SaveSelectedPaymentMethodUseCase.RequestValues, SaveSelectedPaymentMethodUseCase.ResponseValue> {

  private final MasterpassSdkCoordinator masterpassSdkCoordinator;

  public SaveSelectedPaymentMethodUseCase(MasterpassSdkCoordinator masterpassSdkCoordinator) {
    this.masterpassSdkCoordinator = masterpassSdkCoordinator;
  }

  @Override protected void executeUseCase(RequestValues requestValues) {

    masterpassSdkCoordinator.saveSelectedPaymentMethod(requestValues.getPaymentMethod(),
        new MasterpassSdkInterface.SaveSelectedPaymentMethod() {
          @Override public void sdkResponseSuccess(String msg) {
            getUseCaseCallback().onSuccess(new ResponseValue(msg));
          }

          @Override public void sdkResponseError(String error) {
            getUseCaseCallback().onError();
          }
        });
  }

  /**
   * The type Request Values.
   */
  public static final class RequestValues implements UseCase.RequestValues {

    private final MerchantPaymentMethod paymentMethod;

    public RequestValues(MerchantPaymentMethod paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    public MerchantPaymentMethod getPaymentMethod() {
      return paymentMethod;
    }
  }

  /**
   * The type Response Value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {

    private final String response;

    public ResponseValue(String msg) {
      this.response = msg;
    }

    public String getPaymentMethodStatus() {
      return response;
    }
  }
}
