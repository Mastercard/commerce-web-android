package com.mastercard.testapp.presentation.presenter;

import android.support.annotation.NonNull;
import com.mastercard.testapp.data.device.MerchantPaymentMethod;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkInterface;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.masterpass.InitializeSdkUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.AddPaymentMethodUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.DeletePaymentMethodUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.SaveSelectedPaymentMethodUseCase;
import com.mastercard.testapp.presentation.presenter.base.AddPaymentMethodPresenterInterface;
import com.mastercard.testapp.presentation.view.AddPaymentMethodView;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

public class AddPaymentMethodPresenter implements AddPaymentMethodPresenterInterface {

  private final UseCaseHandler mUseCaseHandler;
  private AddPaymentMethodView mAddPaymentMethodView;
  private AddPaymentMethodUseCase mAddPaymentMethodUseCase;
  private DeletePaymentMethodUseCase mDeletePaymentMethodUseCase;
  private SaveSelectedPaymentMethodUseCase mSaveSelectedPaymentMethodUseCase;

  /**
   * @param useCaseHandler the use case handler
   * @param addPaymentMethodView payment method view
   * @param addPaymentMethodUseCase add payment method from available wallets
   * @param deletePaymentMethodUseCase delete payment method
   * @param saveSelectedPaymentMethodUseCase save payment method for checkout
   */
  public AddPaymentMethodPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull AddPaymentMethodView addPaymentMethodView,
      @NonNull AddPaymentMethodUseCase addPaymentMethodUseCase,
      @NonNull DeletePaymentMethodUseCase deletePaymentMethodUseCase,
      @NonNull SaveSelectedPaymentMethodUseCase saveSelectedPaymentMethodUseCase) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "NEVER MUST BE NULL HANDLER");
    mAddPaymentMethodView = checkNotNull(addPaymentMethodView, "NEVER MUST BE NULL VIEW");
    mAddPaymentMethodUseCase = checkNotNull(addPaymentMethodUseCase, "NEVER MUST BE NULL USE CASE");
    mDeletePaymentMethodUseCase =
        checkNotNull(deletePaymentMethodUseCase, "NEVER MUST BE NULL USE CASE");
    mSaveSelectedPaymentMethodUseCase =
        checkNotNull(saveSelectedPaymentMethodUseCase, "Cannot be null");
    mAddPaymentMethodView.setPresenter(this);
  }

  @Override public void start() {
    // Empty implementation
  }

  @Override public void resume() {
    // Empty implementation
  }

  @Override public void pause() {
    // Empty implementation
  }

  @Override public void destroy() {
    // Empty implementation
  }

  @Override public void initializeMasterpassMerchant() {
    mAddPaymentMethodView.showProgress();
    InitializeSdkUseCase.initializeSdk(mAddPaymentMethodView.getContext(),
        new MasterpassSdkInterface.GetFromMasterpassSdk() {
          @Override public void sdkResponseSuccess() {
            mAddPaymentMethodView.hideProgress();
            mAddPaymentMethodView.masterpassSdkInitialized();
          }

          @Override public void sdkResponseError(String error) {
            mAddPaymentMethodView.showError(error);
          }
        });
  }

  @Override public void addPaymentMethod() {
    mUseCaseHandler.execute(mAddPaymentMethodUseCase, new AddPaymentMethodUseCase.RequestValues(),
        new UseCase.UseCaseCallback<AddPaymentMethodUseCase.ResponseValue>() {
          @Override public void onSuccess(AddPaymentMethodUseCase.ResponseValue response) {
            if (response.getPaymentMethodList().get(0).getPairingTransactionId() != null) {
              MasterpassSdkCoordinator.savePairingId(null);
              MasterpassSdkCoordinator.savePairingTransactionId(
                  response.getPaymentMethodList().get(0).getPairingTransactionId());
            }
            mAddPaymentMethodView.showPaymentMethod(response.getPaymentMethodList());
          }

          @Override public void onError() {
            mAddPaymentMethodView.showAlertDialog();
          }
        });
  }

  public void deletePaymentMethod(MerchantPaymentMethod paymentMethod) {
    mAddPaymentMethodView.showProgress();
    mUseCaseHandler.execute(mDeletePaymentMethodUseCase,
        new DeletePaymentMethodUseCase.RequestValues(paymentMethod),
        new UseCase.UseCaseCallback<DeletePaymentMethodUseCase.ResponseValue>() {
          @Override public void onSuccess(DeletePaymentMethodUseCase.ResponseValue response) {
            mAddPaymentMethodView.hideProgress();
          }

          @Override public void onError() {
            mAddPaymentMethodView.hideProgress();
            mAddPaymentMethodView.showError();
          }
        });
  }

  public void selectPaymentMethod(final MerchantPaymentMethod paymentMethod) {
    mAddPaymentMethodView.showProgress();
    mUseCaseHandler.execute(mSaveSelectedPaymentMethodUseCase,
        new SaveSelectedPaymentMethodUseCase.RequestValues(paymentMethod),
        new UseCase.UseCaseCallback<SaveSelectedPaymentMethodUseCase.ResponseValue>() {
          @Override public void onSuccess(SaveSelectedPaymentMethodUseCase.ResponseValue response) {
            mAddPaymentMethodView.hideProgress();
            mAddPaymentMethodView.paymentMethodStatus(response.getPaymentMethodStatus());
          }

          @Override public void onError() {
            mAddPaymentMethodView.hideProgress();
            mAddPaymentMethodView.showError();
          }
        });
  }

  @Override public void retrievePaymentMethods() {
    List<MerchantPaymentMethod> paymentMethodList =
        MasterpassSdkCoordinator.getInstance().retrievePaymentMethods();
    mAddPaymentMethodView.showPaymentMethod(paymentMethodList);
  }

  public void editPaymentMethod(MerchantPaymentMethod paymentMethod) {
    mAddPaymentMethodView.showProgress();
    mUseCaseHandler.execute(mDeletePaymentMethodUseCase,
        new DeletePaymentMethodUseCase.RequestValues(paymentMethod),
        new UseCase.UseCaseCallback<DeletePaymentMethodUseCase.ResponseValue>() {
          @Override public void onSuccess(DeletePaymentMethodUseCase.ResponseValue response) {
            mAddPaymentMethodView.hideProgress();
            addPaymentMethod();
          }

          @Override public void onError() {
            mAddPaymentMethodView.hideProgress();
            mAddPaymentMethodView.showError();
          }
        });
  }
}
