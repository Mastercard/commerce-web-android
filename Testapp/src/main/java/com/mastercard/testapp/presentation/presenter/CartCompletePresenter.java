package com.mastercard.testapp.presentation.presenter;

import androidx.annotation.NonNull;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.mastercard.testapp.presentation.presenter.base.CartCompletePresenterInterface;
import com.mastercard.testapp.presentation.view.CartCompleteListView;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartCompletePresenter implements CartCompletePresenterInterface {

  private final GetItemsOnCartUseCase mGetItemsOnCart;
  private final UseCaseHandler mUseCaseHandler;
  private CartCompleteListView mCartCompleteListView;

  /**
   * Instantiates a new Cart complete presenter.
   *
   * @param useCaseHandler the use case handler
   * @param cartConfirmationListView the cart confirmation list view
   * @param getItemsOnCart the get items on cart
   */
  public CartCompletePresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull CartCompleteListView cartConfirmationListView,
      @NonNull GetItemsOnCartUseCase getItemsOnCart) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
    mCartCompleteListView = checkNotNull(cartConfirmationListView, "itemsView cannot be null!");
    mGetItemsOnCart = checkNotNull(getItemsOnCart, "Get item use case must exist");
    mCartCompleteListView.setPresenter(this);
  }

  public void start() {
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
    mCartCompleteListView.showItems(itemsOnCart);
  }

  @Override public void totalPrice(String totalPrice) {
    mCartCompleteListView.totalPrice(totalPrice);
  }

  @Override public void subtotalPrice(String subtotalPrice) {
    mCartCompleteListView.subtotalPrice(subtotalPrice);
  }

  @Override public void taxPrice(String taxPrice) {
    mCartCompleteListView.taxPrice(taxPrice);
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
    mCartCompleteListView.isSuppressShipping(suppressShipping);
  }
}
