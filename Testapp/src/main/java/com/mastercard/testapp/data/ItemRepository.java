package com.mastercard.testapp.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mastercard.testapp.data.device.CartLocalObject;
import com.mastercard.testapp.domain.model.Item;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Class to handle external and internal data sources
 * <p>
 * Created by Sebastian Farias on 08-10-17.
 */
public class ItemRepository implements ItemDataSource {

  private static ItemRepository INSTANCE = null;
  private final ItemDataSource mItemDataSourceDataSource;
  private final ItemDataSource mItemsLocalDataSource;

  /**
   * This variable has package local visibility so it can be accessed from tests.
   */
  Map<String, Item> mCachedItems;

  /**
   * Marks the cache as invalid, to force an update the next time data is requested. This variable
   * has package local visibility so it can be accessed from tests.
   */
  boolean mCacheIsDirty = false;

  // Prevent direct instantiation.
  private ItemRepository(@NonNull ItemDataSource itemDataSourceDataSource,
      @NonNull ItemDataSource itemsLocalDataSource) {
    mItemDataSourceDataSource = checkNotNull(itemDataSourceDataSource);
    mItemsLocalDataSource = checkNotNull(itemsLocalDataSource);
  }

  /**
   * Returns the single instance of this class, creating it if necessary.
   *
   * @param itemsRemoteDataSource the backend data source
   * @param itemsLocalDataSource the device storage data source
   * @return the {@link ItemRepository} instance
   */
  public static ItemRepository getInstance(ItemDataSource itemsRemoteDataSource,
      ItemDataSource itemsLocalDataSource) {
    if (INSTANCE == null) {
      INSTANCE = new ItemRepository(itemsRemoteDataSource, itemsLocalDataSource);
    }
    return INSTANCE;
  }

  /**
   * Used to force {@link #getInstance(ItemDataSource, ItemDataSource)} to create a new instance
   * next time it's called.
   */
  public static void destroyInstance() {
    INSTANCE = null;
  }

  /**
   * Gets items from cache, local data source (SQLite) or remote data source, whichever is
   * available first.
   * <p>
   * Note: {@link LoadItemsCallback#onDataNotAvailable()} is fired if all data sources fail to
   * get the data.
   */
  @Override public void getItems(@NonNull final LoadItemsCallback callback) {
    checkNotNull(callback);

    // Respond immediately with cache if available and not dirty
    if (mCachedItems != null && !mCacheIsDirty) {
      callback.onItemsLoaded(new ArrayList<>(mCachedItems.values()));
      return;
    }

    if (mCacheIsDirty) {
      // If the cache is dirty we need to fetch new data from the network.
      getItemsFromRemoteDataSource(callback);
    } else {
      // Query the local storage if available. If not, query the network.
      mItemsLocalDataSource.getItems(new LoadItemsCallback() {
        @Override public void onItemsLoaded(List<Item> items) {
          refreshCache(items);
          callback.onItemsLoaded(new ArrayList<>(mCachedItems.values()));
        }

        @Override public void onDataNotAvailable() {
          getItemsFromRemoteDataSource(callback);
        }
      });
    }
  }

  /**
   * Save item
   *
   * @param item object to save {@link Item}
   */
  @Override public void saveItem(@NonNull Item item) {
    checkNotNull(item);
    mItemDataSourceDataSource.saveItem(item);
    mItemsLocalDataSource.saveItem(item);

    // Do in memory cache update to keep the app UI up to date
    if (mCachedItems == null) {
      mCachedItems = new LinkedHashMap<>();
    }
    mCachedItems.put(item.getProductId(), item);
  }

  /**
   * Gets items from local data source (sqlite) unless the table is new or empty. In that case it
   * uses the network data source. This is done to simplify the sample.
   * <p>
   * Note: {@link LoadItemsCallback#onDataNotAvailable()} is fired if both data sources fail to
   * get the data.
   */
  @Override public void getItem(@NonNull final String itemId,
      @NonNull final GetItemCallback callback) {
    checkNotNull(itemId);
    checkNotNull(callback);

    Item cachedItem = getItemWithId(itemId);

    // Respond immediately with cache if available
    if (cachedItem != null) {
      callback.onItemLoaded(cachedItem);
      return;
    }
    // Load from server/persisted if needed.
    // Is the item in the local data source? If not, query the network.
    mItemsLocalDataSource.getItem(itemId, new GetItemCallback() {
      @Override public void onItemLoaded(Item item) {
        callback.onItemLoaded(item);
      }

      @Override public void onDataNotAvailable() {
        mItemDataSourceDataSource.getItem(itemId, new GetItemCallback() {
          @Override public void onItemLoaded(Item item) {
            callback.onItemLoaded(item);
          }

          @Override public void onDataNotAvailable() {
            callback.onDataNotAvailable();
          }
        });
      }
    });
  }

  /**
   * Add item
   *
   * @param item object to save {@link Item}
   * @param callback {@link GetItemOnCartCallback}
   */
  @Override public void addItem(@NonNull final Item item, final GetItemOnCartCallback callback) {
    mItemsLocalDataSource.addItem(item, new GetItemOnCartCallback() {
      @Override public void onItemOnCart(String totalItem, List<Item> newItemOnCart,
          List<CartLocalObject> itemsOnCart) {
        callback.onItemOnCart(totalItem, null, itemsOnCart);
      }

      @Override public void onDataNotAvailable() {
      }
    });
    return;
  }

  /**
   * Remove item
   *
   * @param item object to remove element {@link Item}
   * @param callback {@link GetItemOnCartCallback}
   */
  @Override public void removeItem(@NonNull Item item, final GetItemOnCartCallback callback) {
    mItemsLocalDataSource.removeItem(item, new GetItemOnCartCallback() {
      @Override public void onItemOnCart(String totalItem, List<Item> newItemOnCart,
          List<CartLocalObject> itemsOnCart) {
        callback.onItemOnCart(totalItem, null, itemsOnCart);
      }

      @Override public void onDataNotAvailable() {
        callback.onDataNotAvailable();
      }
    });
    return;
  }

  /**
   * Remove all item
   *
   * @param item object to remove {@link Item}
   * @param callback {@link GetItemOnCartCallback}
   */
  @Override public void removeAllItem(@NonNull Item item, final GetItemOnCartCallback callback) {
    mItemsLocalDataSource.removeAllItem(item, new GetItemOnCartCallback() {
      @Override public void onItemOnCart(String totalItem, List<Item> newItemOnCart,
          List<CartLocalObject> itemsOnCart) {
        callback.onItemOnCart(totalItem, null, itemsOnCart);
      }

      @Override public void onDataNotAvailable() {
        callback.onDataNotAvailable();
      }
    });
    return;
  }

  /**
   * Get items on cart
   *
   * @param callback {@link GetItemOnCartShippingOptionCallback}
   */
  @Override public void getItemsOnCart(final GetItemOnCartShippingOptionCallback callback) {
    mItemsLocalDataSource.getItemsOnCart(new GetItemOnCartShippingOptionCallback() {
      @Override public void onItemOnCart(String totalItem, List<Item> newItemOnCart,
          List<CartLocalObject> itemsOnCart, boolean suppressShipping) {
        callback.onItemOnCart(totalItem, null, itemsOnCart, suppressShipping);
      }

      @Override public void onDataNotAvailable() {
        callback.onDataNotAvailable();
      }
    });
    return;
  }

  @Override public void refreshItems() {
    mCacheIsDirty = true;
  }

  /**
   * Delete all items
   */
  @Override public void deleteAllItems() {
    mItemDataSourceDataSource.deleteAllItems();
    mItemsLocalDataSource.deleteAllItems();

    if (mCachedItems == null) {
      mCachedItems = new LinkedHashMap<>();
    }
    mCachedItems.clear();
  }

  /**
   * Delete item
   *
   * @param itemId id of selected item
   */
  @Override public void deleteItem(@NonNull String itemId) {
    mItemDataSourceDataSource.deleteItem(checkNotNull(itemId));
    mItemsLocalDataSource.deleteItem(checkNotNull(itemId));

    mCachedItems.remove(itemId);
  }

  /**
   * Get items from remote data source
   *
   * @param callback {@link LoadItemsCallback}
   */
  private void getItemsFromRemoteDataSource(@NonNull final LoadItemsCallback callback) {
    mItemDataSourceDataSource.getItems(new LoadItemsCallback() {
      @Override public void onItemsLoaded(List<Item> items) {
        refreshCache(items);
        refreshLocalDataSource(items);
        callback.onItemsLoaded(new ArrayList<>(mCachedItems.values()));
      }

      @Override public void onDataNotAvailable() {
        callback.onDataNotAvailable();
      }
    });
  }

  private void refreshCache(List<Item> items) {
    if (mCachedItems == null) {
      mCachedItems = new LinkedHashMap<>();
    }
    mCachedItems.clear();
    for (Item item : items) {
      mCachedItems.put(item.getProductId(), item);
    }
    mCacheIsDirty = false;
  }

  private void refreshLocalDataSource(List<Item> items) {
    mItemsLocalDataSource.deleteAllItems();
    for (Item item : items) {
      mItemsLocalDataSource.saveItem(item);
    }
  }

  /**
   * Get item with id
   *
   * @param id item id
   * @return {@link Item}
   */
  @Nullable private Item getItemWithId(@NonNull String id) {
    checkNotNull(id);
    if (mCachedItems == null || mCachedItems.isEmpty()) {
      return null;
    } else {
      return mCachedItems.get(id);
    }
  }
}