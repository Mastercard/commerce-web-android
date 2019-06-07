package com.us.masterpass.merchantapp.presentation.presenter;

import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetLocalItemsUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.ItemsPresenterInterface;
import com.us.masterpass.merchantapp.presentation.presenter.base.Presenter;
import com.us.masterpass.merchantapp.presentation.view.ItemsListView;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 * Communication to view an model life cycle implements {@link Presenter}
 */
public class ItemsPresenter implements ItemsPresenterInterface {

  private ItemsListView mItemsListView;
  private final GetLocalItemsUseCase mGetItems;
  private final AddItemUseCase mAddItem;
  private final GetItemsOnCartUseCase mGetItemsOnCart;
  private boolean mFirstLoad = true;

    private final UseCaseHandler mUseCaseHandler;

    @Override
    public void start() {
        loadItems(false);
    }

    @Override
    public void resume() {
//resume
    }

    @Override
    public void pause() {
//pause
    }

    @Override
    public void destroy() {
//destroy
    }

  /**
   * Instantiates a new Items presenter.
   *
   * @param useCaseHandler the use case handler
   * @param itemsListView the items list view
   * @param getItems the get items
   * @param addItem the add item
   * @param getItemsOnCart the get items on cart
   */
  public ItemsPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull ItemsListView itemsListView, @NonNull GetLocalItemsUseCase getItems,
      @NonNull AddItemUseCase addItem, @NonNull GetItemsOnCartUseCase getItemsOnCart) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
    mItemsListView = checkNotNull(itemsListView, "itemsView cannot be null!");
    mGetItems = checkNotNull(getItems, "Get item use case must exist");
    mAddItem = checkNotNull(addItem, "Add item use case must exist");
    mGetItemsOnCart = checkNotNull(getItemsOnCart, "Must not be null");
    mItemsListView.setPresenter(this);
  }


    @Override
    public void result(int requestCode, int resultCode) {
//result
    }

  @Override public void loadItems(boolean forceUpdate) {
    GetLocalItemsUseCase.RequestValues requestValue =
        new GetLocalItemsUseCase.RequestValues(forceUpdate || mFirstLoad);

    mUseCaseHandler.execute(mGetItems, requestValue,
        new GetLocalItemsUseCase.UseCaseCallback<GetLocalItemsUseCase.ResponseValue>() {
          @Override public void onSuccess(GetLocalItemsUseCase.ResponseValue response) {
            List<Item> items = response.getItems();
            processItems(items);
          }

          @Override public void onError() {
            mItemsListView.showLoadingItemsError();
          }
        });
    mFirstLoad = false;
  }

    @Override
    public void addItem(@NonNull Item itemAdd) {
        checkNotNull(itemAdd, "completedTask cannot be null!");
        mUseCaseHandler.execute(mAddItem,
                new AddItemUseCase.RequestValues(itemAdd),
                new UseCase.UseCaseCallback<AddItemUseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(AddItemUseCase.ResponseValue response) {
                        updateBadge(response.getAddItemCount());
                    }

                    @Override
                    public void onError() {
//onError
                    }
                });
    }

    @Override
    public void showBadge() {
        //EVAL IF SHOW OR NOT
    }

    @Override
    public void updateBadge(String totalCartCount) {
        mItemsListView.updateBadge(totalCartCount);
    }

    @Override
    public void loadCartActivity() {
        mItemsListView.loadCartActivity();
    }

    @Override
    public void loadCartActivityShowError() {
        mItemsListView.loadCartActivityShowError();
    }

    @Override
    public void loadSettings() {
        mItemsListView.loadSettingsActivity();
    }

    @Override
    public void getItemsOnCart() {
        mUseCaseHandler.execute(mGetItemsOnCart,
                new GetItemsOnCartUseCase.RequestValues(),
                new GetItemsOnCartUseCase.UseCaseCallback<GetItemsOnCartUseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(GetItemsOnCartUseCase.ResponseValue response) {
                        loadCartActivity();
                    }

                    @Override
                    public void onError() {
                        loadCartActivityShowError();
                    }
                });
    }

    private void processItems(List<Item> items) {
        mItemsListView.showItems(items);

    }
}
