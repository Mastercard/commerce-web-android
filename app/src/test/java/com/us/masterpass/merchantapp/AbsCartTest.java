package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains the common field for Cart Related Use Case.
 */
abstract class AbsCartTest extends AbsTest{

    @Captor
    ArgumentCaptor<ItemDataSource.GetItemOnCartCallback> mGetItemOnCartCallbackCaptor;

    private static final String TEST_ITEM_NAME = "TEST_ITEM_NAME";
    private static final double TEST_ITEM_PRICE = 12;
    private static final String TEST_CART_LOCAL_OBJECT = "TEST_CART_LOCAL_OBJECT";

    static final int REQUEST_CODE = 0;
    static final int RESULT_CODE = 0;
    static final String TOTAL_ITEM = "1";


    GetItemsOnCartUseCase getItemsOnCartUseCase;
    AddItemUseCase addItemUseCase;

    /**
     * @return A @{@link Item}
     */
    Item getItem() {
        Item item = new Item();
        item.setName(TEST_ITEM_NAME);
        item.setPrice(TEST_ITEM_PRICE);
        return item;
    }

    /**
     * method initialised elements to be used in its child classes.
     */
    @Before
    public void initUseCases() {
        super.initUseCases();
        getItemsOnCartUseCase = new GetItemsOnCartUseCase(mItemsRepository);
        addItemUseCase = new AddItemUseCase(mItemsRepository);

    }

    /**
     *
     * @return A @{@link List} of New Item added in the cart.
     */
    List<Item> getNewItemOnCart() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(getItem());
        return itemList;
    }

    /**
     *
     * @return A @{@link List} of Items currently present in the cart.
     */
    List<CartLocalObject> getItemsOnCartList() {
        List<CartLocalObject> itemsOnCart = new ArrayList<>();
        itemsOnCart.add(getCartLocalObject());
        return itemsOnCart;
    }

    private CartLocalObject getCartLocalObject() {
        CartLocalObject cartLocalObject = new CartLocalObject();
        cartLocalObject.setName(TEST_CART_LOCAL_OBJECT);
        return cartLocalObject;
    }

}
