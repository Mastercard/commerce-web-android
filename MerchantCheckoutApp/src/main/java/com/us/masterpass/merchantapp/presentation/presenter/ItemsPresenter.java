package com.us.masterpass.merchantapp.presentation.presenter;

import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.MockGetItemsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.login.IsLoggedUseCase;
import com.us.masterpass.merchantapp.domain.usecase.login.RemoveLoginUseCase;
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
  private final MockGetItemsUseCase mGetItems;
    private final AddItemUseCase mAddItem;
    private final GetItemsOnCartUseCase mGetItemsOnCart;
    private final IsLoggedUseCase mIsLogged;
    private final RemoveLoginUseCase mRemoveLogin;
    private boolean mFirstLoad = true;

    private final UseCaseHandler mUseCaseHandler;

  /**
   * Instantiates a new Items presenter.
   *
   * @param useCaseHandler     the use case handler
   * @param itemsListView      the items list view
   * @param getItems           the get items
   * @param addItem            the add item
   * @param getItemsOnCart     the get items on cart
   * @param isLoggedUseCase    the is logged use case
   * @param removeLoginUseCase the remove login use case
   */
  public ItemsPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull ItemsListView itemsListView, @NonNull MockGetItemsUseCase getItems,
      @NonNull AddItemUseCase addItem,
      @NonNull GetItemsOnCartUseCase getItemsOnCart,
      @NonNull IsLoggedUseCase isLoggedUseCase,
      @NonNull RemoveLoginUseCase removeLoginUseCase) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
    mItemsListView = checkNotNull(itemsListView, "itemsView cannot be null!");
    mGetItems = checkNotNull(getItems, "Get item use case must exist");
    mAddItem = checkNotNull(addItem, "Add item use case must exist");
    mGetItemsOnCart = checkNotNull(getItemsOnCart, "Must not be null");
    mIsLogged = checkNotNull(isLoggedUseCase, "NOT NULL");
    mRemoveLogin = checkNotNull(removeLoginUseCase, "NOT NULL");
    mItemsListView.setPresenter(this);
  }

    @Override
    public void start() {
        loadItems(false);
    }

    @Override
    public void resume() {
      // No use for this yet.
    }

    @Override
    public void pause() {
      // No use for this yet.
    }

    @Override
    public void destroy() {
      // No use for this yet.
    }


    @Override
    public void result(int requestCode, int resultCode) {
      // No use for this yet.
    }

    @Override
    public void loadItems(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadListItems(forceUpdate || mFirstLoad);
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
                      // No use for this yet.
                    }
                });
    }

    @Override
    public void showBadge() {
      // This should be re-checked.
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

    @Override
    public void loadLoginActivity() {
        mUseCaseHandler.execute(mIsLogged,
                new IsLoggedUseCase.LoggedInRequestValues(),
                new UseCase.UseCaseCallback<IsLoggedUseCase.LoggedInResponseValue>() {
                    @Override
                    public void onSuccess(IsLoggedUseCase.LoggedInResponseValue response) {
                        mItemsListView.loadMyAccountActivity();
                    }

                    @Override
                    public void onError() {
                      mItemsListView.loadLoginActivity();
                    }
                });
    }

    @Override
    public void logout() {
        mUseCaseHandler.execute(mRemoveLogin,
                new RemoveLoginUseCase.RequestValues(),
                new UseCase.UseCaseCallback<RemoveLoginUseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(RemoveLoginUseCase.ResponseValue response) {
                        mItemsListView.loadLoginActivity();
                    }

                    @Override
                    public void onError() {
                      // No use for this yet.
                    }
                });
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link ItemDataSource}
     */
    private void loadListItems(boolean forceUpdate) {

      MockGetItemsUseCase.RequestValues requestValue =
          new MockGetItemsUseCase.RequestValues(forceUpdate);

        mUseCaseHandler.execute(mGetItems, requestValue,
            new GetItemsUseCase.UseCaseCallback<MockGetItemsUseCase.ResponseValue>() {
              @Override public void onSuccess(MockGetItemsUseCase.ResponseValue response) {
                        List<Item> items = response.getItems();
                        processItems(items);
                    }

                    @Override
                    public void onError() {
                        mItemsListView.showLoadingItemsError();
                    }
                });
    }

    private void processItems(List<Item> items) {
        mItemsListView.showItems(items);

    }
}
