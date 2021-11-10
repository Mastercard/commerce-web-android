package com.mastercard.testapp.data.device;

import android.content.Context;
import androidx.annotation.NonNull;
import com.mastercard.testapp.data.ItemDataSource;
import com.mastercard.testapp.domain.Constants;
import com.mastercard.testapp.domain.model.Item;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Manage local items on shopping cart
 * Created by Sebastian Farias on 08-10-17.
 */
public class ItemLocalDataSource implements ItemDataSource {
  private static ItemLocalDataSource INSTANCE;
  private static Context context;
  private final String keyLocalModel = "localCart";
  private ItemSQLiteUtil mDBUtil;

  private ItemLocalDataSource(@NonNull Context contextApp) {
    checkNotNull(contextApp);
    this.context = contextApp;
    mDBUtil = new ItemSQLiteUtil(context);
  }

  /**
   * Get instance.
   *
   * @param context of android application
   * @return the instance of class
   */
  public static ItemLocalDataSource getInstance(@NonNull Context context) {
    if (INSTANCE == null) {
      INSTANCE = new ItemLocalDataSource(context);
    }
    return INSTANCE;
  }

  @Override public void getItems(@NonNull LoadItemsCallback callback) {

  }

  @Override public void getItem(@NonNull String itemId, @NonNull GetItemCallback callback) {
  }

  /**
   * Add items to the shopping cart stored on device
   *
   * @param item item object {@link Item}
   * @param callback callback to manage add item action
   */
  @Override public void addItem(@NonNull Item item, GetItemOnCartCallback callback) {
    List<CartLocalObject> mItemsOnCart =
        CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel);
    CartLocalObject tempCart = new CartLocalObject();
    tempCart.setItemId(item.getProductId());
    tempCart.setTotalCount(1);
    int totalInCart = 0;
    double totalPrice = 0;
    boolean exist = false;
    if (mItemsOnCart.size() > 0) {
      for (CartLocalObject cartLocalObject : mItemsOnCart) {
        if (cartLocalObject.getItemId().equalsIgnoreCase(item.getProductId())) {
          cartLocalObject.setTotalCount(cartLocalObject.getTotalCount() + 1);
          exist = true;
        }
        cartLocalObject.setTotalPrice(
            cartLocalObject.getSalePrice() * cartLocalObject.getTotalCount());
        totalPrice = +totalPrice + cartLocalObject.getTotalPrice();
        totalInCart = +totalInCart + cartLocalObject.getTotalCount();
      }
    }

    if (!exist) {
      tempCart.setProductId(item.getProductId());
      tempCart.setImage(item.getImage());
      tempCart.setName(item.getName());
      tempCart.setDateAdded(item.getDateAdded());
      tempCart.setDescription(item.getDescription());
      tempCart.setPrice(item.getPrice());
      tempCart.setSalePrice(item.getSalePrice());
      tempCart.setTotalCount(1);
      tempCart.setTotalPrice(item.getSalePrice());
      mItemsOnCart.add(tempCart);
      totalPrice = tempCart.getTotalPrice();
      totalInCart = +totalInCart + tempCart.getTotalCount();
    }

    CartLocalStorage.getInstance(context).setCartLocal(keyLocalModel, mItemsOnCart, totalPrice);
    CartLocalStorage.getInstance(context).persistData();
    if (item.getProductId() != null) {
      callback.onItemOnCart(Integer.toString(totalInCart), null, mItemsOnCart);
    } else {
      callback.onDataNotAvailable();
    }
  }

  /**
   * Remove items on the shopping cart stored on device
   *
   * @param item item object {@link Item}
   * @param callback callback to manage remove item action
   */
  @Override public void removeItem(@NonNull Item item, GetItemOnCartCallback callback) {
    List<CartLocalObject> mItemsOnCart =
        CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel);
    CartLocalObject tempCart = new CartLocalObject();
    tempCart.setItemId(item.getProductId());
    tempCart.setTotalCount(1);
    int totalInCart = 0;
    int pos = 0;
    double totalPrice = 0;
    boolean remove = false;
    if (mItemsOnCart.size() > 0) {
      for (int i = 0; i < mItemsOnCart.size(); i++) {
        if (mItemsOnCart.get(i).getItemId().equalsIgnoreCase(item.getProductId())) {
          mItemsOnCart.get(i).setTotalCount(mItemsOnCart.get(i).getTotalCount() - 1);
          if (mItemsOnCart.get(i).getTotalCount() == 0) {
            pos = i;
            remove = true;
          }
        }
        mItemsOnCart.get(i)
            .setTotalPrice(
                mItemsOnCart.get(i).getSalePrice() * mItemsOnCart.get(i).getTotalCount());
        totalInCart = +totalInCart + mItemsOnCart.get(i).getTotalCount();
        totalPrice = +totalPrice + mItemsOnCart.get(i).getTotalPrice();
      }
    }

    totalPrice = +totalPrice + Constants.TAX_VALUE;

    if (remove) {
      CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel).remove(pos);
    }

    CartLocalStorage.getInstance(context).setCartLocal(keyLocalModel, mItemsOnCart, totalPrice);
    CartLocalStorage.getInstance(context).persistData();

    if (mItemsOnCart.size() > 0) {
      callback.onItemOnCart(Integer.toString(totalInCart), null, mItemsOnCart);
    } else {
      callback.onDataNotAvailable();
    }
  }

  /**
   * Remove all items on the shopping cart stored on device
   *
   * @param item item object {@link Item}
   * @param callback callback to manage remove all items action
   */
  @Override public void removeAllItem(@NonNull Item item, GetItemOnCartCallback callback) {
    List<CartLocalObject> mItemsOnCart =
        CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel);
    int totalInCart = 0;
    int pos = 0;
    double totalPrice = 0;
    boolean remove = false;
    if (mItemsOnCart.size() > 0) {
      for (int i = 0; i < mItemsOnCart.size(); i++) {
        if (mItemsOnCart.get(i).getItemId().equalsIgnoreCase(item.getProductId())) {
          pos = i;
          remove = true;
        } else {
          mItemsOnCart.get(i)
              .setTotalPrice(
                  mItemsOnCart.get(i).getSalePrice() * mItemsOnCart.get(i).getTotalCount());
          totalInCart = +totalInCart + mItemsOnCart.get(i).getTotalCount();
          totalPrice = +totalPrice + mItemsOnCart.get(i).getTotalPrice();
        }
      }
    }

    totalPrice = +totalPrice + Constants.TAX_VALUE;

    if (remove) {
      CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel).remove(pos);
    }

    CartLocalStorage.getInstance(context).setCartLocal(keyLocalModel, mItemsOnCart, totalPrice);
    CartLocalStorage.getInstance(context).persistData();

    if (mItemsOnCart.size() > 0) {
      callback.onItemOnCart(Integer.toString(totalInCart), null, mItemsOnCart);
    } else {
      callback.onDataNotAvailable();
    }
  }

  /**
   * Get list of items on shopping cart
   *
   * @param callback the callback
   */
  @Override public void getItemsOnCart(GetItemOnCartShippingOptionCallback callback) {
    List<CartLocalObject> mItemsOnCart =
        CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel);
    int totalInCart = 0;
    if (mItemsOnCart.size() > 0) {
      for (CartLocalObject cartLocalObject : mItemsOnCart) {
        totalInCart = +totalInCart + cartLocalObject.getTotalCount();
      }
      callback.onItemOnCart(Integer.toString(totalInCart), null, mItemsOnCart, suppressShipping());
    } else {
      callback.onDataNotAvailable();
    }
  }

  @Override public void saveItem(@NonNull Item item) {
    //TODO ADD DATA ON LOCAL DATABASE
  }

  @Override public void refreshItems() {

  }

  @Override public void deleteAllItems() {
    //TODO REMOVE OLD DATA
  }

  @Override public void deleteItem(@NonNull String itemId) {

  }

  /**
   * Check if suppress shipping is enable or disable, when return the list of items
   *
   * @return enable / disable
   */
  private boolean suppressShipping() {
    return SettingsSaveConfigurationSdk.getInstance(context)
        .configSwitch(SettingsSaveConstants.SDK_CONFIG_SUPRESS);
  }
}
