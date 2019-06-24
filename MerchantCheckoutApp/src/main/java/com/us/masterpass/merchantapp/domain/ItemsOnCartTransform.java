package com.us.masterpass.merchantapp.domain;

import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveAllItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveItemUseCase;
import java.util.ArrayList;
import java.util.List;

/**
 * Format and return expected list for display data and additional info on screens
 * <p>
 * Created by Sebastian Farias on 13-10-17.
 */
public class ItemsOnCartTransform {
    /**
     * The Cart local objects.
     */
    public List<CartLocalObject> cartLocalObjects;
    /**
     * The New item on cart final.
     */
    public List<Item> newItemOnCartFinal;
    /**
     * The New total sale price.
     */
    public double newTotalSalePrice = 0;

    /**
     * Instantiates a new Items on cart transform.
     *
     * @param itemsOnLocalData the items on local data
     */
    public ItemsOnCartTransform(List<CartLocalObject> itemsOnLocalData) {
        cartLocalObjects = itemsOnLocalData;
        itemsOnCart(cartLocalObjects);
    }

    /**
     * Handle the data stored on the device, with all items on cart.
     * Transformed to show on view. Used on:
     * {@link GetItemsOnCartUseCase}
     * {@link AddItemUseCase}
     * {@link RemoveAllItemUseCase}
     * {@link RemoveItemUseCase}
     *
     * @param itemsOnLocalData list of items saved on local shopping cart
     */
    public void itemsOnCart(List<CartLocalObject> itemsOnLocalData) {
        List<Item> itemsOnCartToShow = new ArrayList<>();
        if (itemsOnLocalData.size() > 0) {
            for (CartLocalObject cartLocalObject : itemsOnLocalData) {
                Item finalItem = new Item();

                finalItem.setProductId(cartLocalObject.getProductId());
                finalItem.setName(cartLocalObject.getName());
                finalItem.setPrice(cartLocalObject.getPrice());
                finalItem.setSalePrice(cartLocalObject.getSalePrice());
                finalItem.setTotalCount(cartLocalObject.getTotalCount());
                finalItem.setImage(cartLocalObject.getImage());
                finalItem.setDescription(cartLocalObject.getDescription());
                finalItem.setDateAdded(cartLocalObject.getDateAdded());
                finalItem.setTotalPrice(cartLocalObject.getTotalPrice());

                newTotalSalePrice += cartLocalObject.getTotalPrice();
                itemsOnCartToShow.add(finalItem);
            }
        }
        newItemOnCartFinal = itemsOnCartToShow;
    }
}