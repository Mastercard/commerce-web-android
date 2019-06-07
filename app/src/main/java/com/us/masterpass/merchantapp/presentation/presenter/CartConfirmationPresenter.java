package com.us.masterpass.merchantapp.presentation.presenter;

import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.CompleteTransactionUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.CartConfirmationPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.CartConfirmationListView;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartConfirmationPresenter implements CartConfirmationPresenterInterface {

  private CartConfirmationListView mCartConfirmationListView;
  private final GetItemsOnCartUseCase mGetItemsOnCart;
  private final CompleteTransactionUseCase mCompleteTransaction;
  private final UseCaseHandler mUseCaseHandler;

  /**
   * Instantiates a new Cart confirmation presenter.
   *
   * @param useCaseHandler the use case handler
   * @param cartConfirmationListView the cart confirmation list view
   * @param getItemsOnCart the get items on cart
   * @param completeTransaction the complete transaction
   */
  public CartConfirmationPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull CartConfirmationListView cartConfirmationListView,
      @NonNull GetItemsOnCartUseCase getItemsOnCart,
      @NonNull CompleteTransactionUseCase completeTransaction) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
    mCartConfirmationListView = checkNotNull(cartConfirmationListView, "itemsView cannot be null!");
    mGetItemsOnCart = checkNotNull(getItemsOnCart, "Get item use case must exist");
    mCompleteTransaction = checkNotNull(completeTransaction, "Must exist ");
    mCartConfirmationListView.setPresenter(this);
  }

  @Override public void start() {
    loadItemsOnCart(false);
  }

  @Override public void resume() {
    //resume
  }

  @Override public void pause() {
    //pause
  }

  @Override public void destroy() {
    //destroy
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
            //onError
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
            mCartConfirmationListView.showLoadingSpinner(false);
            mCartConfirmationListView.showCompleteScreen(
                response.getmMasterpassConfirmationObject());
          }

          @Override public void onError() {
            mCartConfirmationListView.showLoadingSpinner(false);
          }
        });
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
    mCartConfirmationListView.isSuppressShipping(suppressShipping);
  }
}
