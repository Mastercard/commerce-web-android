package com.mastercard.testapp.domain.usecase.items;

import androidx.annotation.NonNull;
import com.mastercard.testapp.data.ItemDataSource;
import com.mastercard.testapp.data.ItemRepository;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Get items from external repository in this case, HTTP request
 */
public class GetItemsUseCase
    extends UseCase<GetItemsUseCase.RequestValues, GetItemsUseCase.ResponseValue> {

  private final ItemRepository mItemsRepository;

  /**
   * Instantiates a new Get items use case.
   *
   * @param itemsRepository the items repository
   */
  public GetItemsUseCase(@NonNull ItemRepository itemsRepository) {
    mItemsRepository = checkNotNull(itemsRepository, "Item must exist");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    if (values.isForceUpdate()) {
      mItemsRepository.refreshItems();
    }

    mItemsRepository.getItems(new ItemDataSource.LoadItemsCallback() {
      @Override public void onItemsLoaded(List<Item> items) {
        ResponseValue responseValue = new ResponseValue(items);
        getUseCaseCallback().onSuccess(responseValue);
      }

      @Override public void onDataNotAvailable() {
        getUseCaseCallback().onError();
      }
    });
  }

  /**
   * The type Request values.
   */
  public static final class RequestValues implements UseCase.RequestValues {
    private final boolean mForceUpdate;

    /**
     * Instantiates a new Request values.
     *
     * @param forceUpdate the force update
     */
    public RequestValues(boolean forceUpdate) {
      mForceUpdate = forceUpdate;
    }

    /**
     * Is force update boolean.
     *
     * @return the boolean
     */
    public boolean isForceUpdate() {
      return mForceUpdate;
    }
  }

  /**
   * The type Response value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {
    private final List<Item> mItems;

    /**
     * Instantiates a new Response value.
     *
     * @param items the items
     */
    public ResponseValue(@NonNull List<Item> items) {
      mItems = checkNotNull(items, "items cannot be null!");
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<Item> getItems() {
      return mItems;
    }
  }
}