package com.us.masterpass.merchantapp.data.device;

import android.content.Context;
import androidx.annotation.NonNull;
import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.domain.Constants;
import com.us.masterpass.merchantapp.domain.model.Item;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class ItemLocalDataSource implements ItemDataSource {
    private static final String keyLocalModel = "localCart";
    private static ItemLocalDataSource INSTANCE;
    private Context context;

    private ItemLocalDataSource(@NonNull Context contextApp) {
        checkNotNull(contextApp);
        this.context = contextApp;
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static ItemLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ItemLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getItems(@NonNull LoadItemsCallback callback) {
        //Gets items.
    }

    @Override
    public void getItem(@NonNull String itemId, @NonNull GetItemCallback callback) {
    //Gets item.
    }

    @Override
    public void addItem(@NonNull Item item, GetItemOnCartCallback callback) {
        List<CartLocalObject> mItemsOnCart =
                CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel);
        CartLocalObject tempCart = new CartLocalObject();
        tempCart.setItemId(item.getProductId());
        tempCart.setTotalCount(1);
        int totalInCart = 0;
        double totalPrice = 0;
        boolean exist = false;
        if (!mItemsOnCart.isEmpty()) {
            for (CartLocalObject cartLocalObject : mItemsOnCart) {
                if (cartLocalObject.getItemId().equalsIgnoreCase(item.getProductId())) {
                    cartLocalObject.setTotalCount(cartLocalObject.getTotalCount() + 1);
                    exist = true;
                }
                cartLocalObject.setTotalPrice(
                        cartLocalObject.getSalePrice() *
                                cartLocalObject.getTotalCount()
                );
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

        totalPrice = +totalPrice + Constants.TAX_VALUE;

        CartLocalStorage.getInstance(context).setCartLocal(keyLocalModel, mItemsOnCart, totalPrice);
        CartLocalStorage.getInstance(context).persistData();
        if (item.getProductId() != null) {
            callback.onItemOnCart(Integer.toString(totalInCart), null, mItemsOnCart);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void removeItem(@NonNull Item item, GetItemOnCartCallback callback) {
        List<CartLocalObject> mItemsOnCart =
                CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel);
        CartLocalObject tempCart = new CartLocalObject();
        tempCart.setItemId(item.getProductId());
        tempCart.setTotalCount(1);
        int totalInCart = 0;
        int pos = 0;
        double totalPrice = 0;
        boolean remove = false;
        if (!mItemsOnCart.isEmpty()) {
            for (int i = 0; i < mItemsOnCart.size(); i++) {
                if (mItemsOnCart.get(i).getItemId().equalsIgnoreCase(item.getProductId())) {
                    mItemsOnCart.get(i).setTotalCount(mItemsOnCart.get(i).getTotalCount() - 1);
                    if (mItemsOnCart.get(i).getTotalCount() == 0) {
                        pos = i;
                        remove = true;
                    }
                }
                mItemsOnCart.get(i).setTotalPrice(
                        mItemsOnCart.get(i).getSalePrice() *
                                mItemsOnCart.get(i).getTotalCount()
                );
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

        if (!mItemsOnCart.isEmpty()) {
            callback.onItemOnCart(Integer.toString(totalInCart), null, mItemsOnCart);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void removeAllItem(@NonNull Item item, GetItemOnCartCallback callback) {
        List<CartLocalObject> mItemsOnCart =
                CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel);
        int totalInCart = 0;
        int pos = 0;
        double totalPrice = 0;
        boolean remove = false;
        if (!mItemsOnCart.isEmpty()) {
            for (int i = 0; i < mItemsOnCart.size(); i++) {
                if (mItemsOnCart.get(i).getItemId().equalsIgnoreCase(item.getProductId())) {
                    pos = i;
                    remove = true;
                } else {
                    mItemsOnCart.get(i).setTotalPrice(
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

        if (!mItemsOnCart.isEmpty()) {
            callback.onItemOnCart(Integer.toString(totalInCart), null, mItemsOnCart);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void getItemsOnCart(GetItemOnCartShippingOptionCallback callback) {
        List<CartLocalObject> mItemsOnCart =
                CartLocalStorage.getInstance(context).getCartLocal(keyLocalModel);
        int totalInCart = 0;
        if (!mItemsOnCart.isEmpty()) {
            for (CartLocalObject cartLocalObject : mItemsOnCart) {
                totalInCart = +totalInCart + cartLocalObject.getTotalCount();
            }
            callback.onItemOnCart(Integer.toString(totalInCart), null, mItemsOnCart,
                    suppressShipping());
        } else {
            callback.onDataNotAvailable();
        }

    }

    @Override
    public void saveItem(@NonNull Item item) {
        //TODO ADD DATA ON LOCAL DATABASE
    }

    @Override
    public void completeItem(@NonNull Item proud) {
        //Complete item.
    }

    @Override
    public void completeItem(@NonNull String itemId) {
        //Complete item.
    }

    @Override
    public void activateItem(@NonNull Item item) {
        //Activate item
    }

    @Override
    public void activateItem(@NonNull String itemId) {
        //Activate item
    }

    @Override
    public void clearCompletedItems() {
        //Clear completed items
    }

    @Override
    public void refreshItems() {
        //Refresh items
    }

    @Override
    public void deleteAllItems() {
        //TODO REMOVE OLD DATA
        //Delete all items
    }

    @Override
    public void deleteItem(@NonNull String itemId) {
        //Delete item
    }

    private boolean suppressShipping(){
        return SettingsSaveConfigurationSdk.getInstance(context)
                .configSwitch(SettingsSaveConstants.SDK_CONFIG_SUPRESS);
    }
}
