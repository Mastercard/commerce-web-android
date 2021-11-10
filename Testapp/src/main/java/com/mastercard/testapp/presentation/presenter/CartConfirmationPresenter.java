package com.mastercard.testapp.presentation.presenter;

import androidx.annotation.NonNull;
import com.mastercard.mp.switchservices.checkout.ExpressCheckoutRequest;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.mastercard.testapp.domain.usecase.masterpass.CompleteTransactionUseCase;
import com.mastercard.testapp.domain.usecase.masterpass.ConfirmExpressTransactionUseCase;
import com.mastercard.testapp.presentation.presenter.base.CartConfirmationPresenterInterface;
import com.mastercard.testapp.presentation.view.CartConfirmationListView;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartConfirmationPresenter implements CartConfirmationPresenterInterface {

  private final GetItemsOnCartUseCase mGetItemsOnCart;
  private final CompleteTransactionUseCase mCompleteTransaction;
  private final ConfirmExpressTransactionUseCase mConfirmExpressTransaction;
  private final UseCaseHandler mUseCaseHandler;
  private CartConfirmationListView mCartConfirmationListView;

  /**
   * Instantiates a new Cart confirmation presenter.
   *
   * @param useCaseHandler the use case handler
   * @param cartConfirmationListView the cart confirmation list view
   * @param getItemsOnCart the get items on cart
   * @param completeTransaction the complete transaction
   * @param confirmTransaction the confirm transaction
   */
  public CartConfirmationPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull CartConfirmationListView cartConfirmationListView,
      @NonNull GetItemsOnCartUseCase getItemsOnCart,
      @NonNull CompleteTransactionUseCase completeTransaction,
      @NonNull ConfirmExpressTransactionUseCase confirmTransaction) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
    mCartConfirmationListView = checkNotNull(cartConfirmationListView, "itemsView cannot be null!");
    mGetItemsOnCart = checkNotNull(getItemsOnCart, "Get item use case must exist");
    mCompleteTransaction = checkNotNull(completeTransaction, "Must exist ");
    mConfirmExpressTransaction = checkNotNull(confirmTransaction, "Must exist ");
    mCartConfirmationListView.setPresenter(this);
  }

  @Override public void start() {
    loadItemsOnCart(false);
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void loadItemsOnCart(boolean forceUpdate) {
    mUseCaseHandler.execute(mGetItemsOnCart, new GetItemsOnCartUseCase.RequestValues(),
        new GetItemsOnCartUseCase.UseCaseCallback<GetItemsOnCartUseCase.ResponseValue>() {
          @Override public void onSuccess(GetItemsOnCartUseCase.ResponseValue response) {
            List<Item> itemsOnCart = response.getNewItemOnCart();
            showItemsOnCart(itemsOnCart);
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
            isSuppressShipping(response.isSuppressShipping());
          }

          @Override public void onError() {

          }
        });
  }

  @Override public void showItemsOnCart(List<Item> itemsOnCart) {
    mCartConfirmationListView.showItems(itemsOnCart);
  }

  @Override public void totalPrice(String totalPrice) {
    mCartConfirmationListView.totalPrice(totalPrice);
  }

  @Override public void subtotalPrice(String subtotalPrice) {
    mCartConfirmationListView.subtotalPrice(subtotalPrice);
  }

  @Override public void taxPrice(String taxPrice) {
    mCartConfirmationListView.taxPrice(taxPrice);
  }

  @Override public void confirmCheckout(MasterpassConfirmationObject masterpassConfirmationObject) {
    mUseCaseHandler.execute(mCompleteTransaction,
        new CompleteTransactionUseCase.RequestValues(masterpassConfirmationObject),
        new UseCase.UseCaseCallback<CompleteTransactionUseCase.ResponseValue>() {
          @Override public void onSuccess(CompleteTransactionUseCase.ResponseValue response) {
            mCartConfirmationListView.hideProgress();
            mCartConfirmationListView.showCompleteScreen(
                response.getmMasterpassConfirmationObject());
          }

          @Override public void onError() {
            mCartConfirmationListView.hideProgress();
            mCartConfirmationListView.showError();
          }
        });
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
    mCartConfirmationListView.isSuppressShipping(suppressShipping);
  }

  @Override public void expressCheckout(ExpressCheckoutRequest masterpassExpressCheckoutObject) {
    mUseCaseHandler.execute(mConfirmExpressTransaction,
        new ConfirmExpressTransactionUseCase.RequestValues(masterpassExpressCheckoutObject),
        new UseCase.UseCaseCallback<ConfirmExpressTransactionUseCase.ResponseValue>() {
          @Override public void onSuccess(ConfirmExpressTransactionUseCase.ResponseValue response) {
            mCartConfirmationListView.hideProgress();
            mCartConfirmationListView.showCompleteScreen(
                response.getmMasterpassConfirmationObject());
          }

          @Override public void onError() {
            mCartConfirmationListView.hideProgress();
            mCartConfirmationListView.showError();
          }
        });
  }
}
