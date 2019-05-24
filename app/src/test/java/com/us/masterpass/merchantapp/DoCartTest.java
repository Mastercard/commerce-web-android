package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveAllItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.ConfirmTransactionUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.CartPresenter;
import com.us.masterpass.merchantapp.presentation.view.CartListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;

public class DoCartTest extends AbsCartTest
{

    @Mock
    private MasterpassExternalDataSource mMasterpassExternalDataSource;

    @Mock
    private CartListView mCartListView;

    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartShippingOptionCallback> mGetItemOnCartShippingCallbackCaptor;

    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartCallback> mGetItemOnCartCallbackCaptor;

    @Captor
    private ArgumentCaptor<MasterpassDataSource.LoadDataConfirmationCallback> mGetLoadDataConfirmationCallbackCaptor;



    private CartPresenter mCartPresenter;

    @Mock
    private com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator mMasterpassSDKCoordinator;


    @Before
    public void init(){
        mCartPresenter = getCartPresenter();
    }

    /**
     * Testcase when an item is added into the cart.
     */
    @Test
    public void doAddItem() {
        mCartPresenter.addItem(getItem());
        verify(mItemsRepository).addItem(any(Item.class), mGetItemOnCartCallbackCaptor.capture());
        mGetItemOnCartCallbackCaptor.getValue().onItemOnCart(TOTAL_ITEM, getNewItemOnCart(), getItemsOnCartList());
        verify(mCartListView).updateBadge(any(String.class));
    }

    /**
     * Testcase when trying to add an item in cart fails.
     */
    @Test
    public void doAddItemWithError() {
        mCartPresenter.addItem(getItem());
        verify(mItemsRepository).addItem(any(Item.class), mGetItemOnCartCallbackCaptor.capture());
        mGetItemOnCartCallbackCaptor.getValue().onDataNotAvailable();
    }

    /**
     * Testcase when Removing all items from cart.
     */
    @Test
    public void doRemoveAllItem() {
        mCartPresenter.removeAllItem(getItem());
        verify(mItemsRepository).removeAllItem(any(Item.class), mGetItemOnCartCallbackCaptor.capture());
        mGetItemOnCartCallbackCaptor.getValue().onItemOnCart(TOTAL_ITEM, getNewItemOnCart(), getItemsOnCartList());
    }

    /**
     * Testcase when Error occurs while removing all items from cart.
     */
    @Test
    public void doRemoveAllItemError() {
        mCartPresenter.removeAllItem(getItem());
        verify(mItemsRepository).removeAllItem(any(Item.class), mGetItemOnCartCallbackCaptor.capture());
        mGetItemOnCartCallbackCaptor.getValue().onDataNotAvailable();
    }

    /**
     * Testcase for empty methods which are fore casted to have future uses.
     */
    @Test
    public void doEmptyMethodTest() {
        presenter = mCartPresenter;
        super.doEmptyMethodTest();
        mCartPresenter.result(REQUEST_CODE, RESULT_CODE);
        mCartPresenter.showBadge();
        mCartPresenter.showMasterpassButton();

    }

    /**
     * Testcase when Loading cart on Cart Screen.
     */
    @Test
    public void doLoadCart() {
        getItemsOnCart();
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingCallbackCaptor.capture());
        mGetItemOnCartShippingCallbackCaptor.getValue().onItemOnCart(TOTAL_ITEM, getNewItemOnCart(), getItemsOnCartList(), true);
        verify(mCartListView).showItems(anyList());
    }

    /**
     * Testcase when Loading the cart for the first time.
     */
    @Test
    public void doStart() {
        mCartPresenter.start();
    }

    /**
     * Testcase when merchant sdk is initialised.
     */
    /*@Test
    public void doInitializeSDK() {
        mCartPresenter.initializeMasterpassMerchant(mContext);
    }*/

    /**
     * Testcase when Loading Items on cart fails.
     */
    @Test
    public void doLoadCartError() {
        getItemsOnCart();
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingCallbackCaptor.capture());
        mGetItemOnCartShippingCallbackCaptor.getValue().onDataNotAvailable();
    }

    /**
     * Testcase when Removing an item from the cart.
     */
    @Test
    public void doRemoveItem() {
        mCartPresenter.removeItem(getItem());
        verify(mItemsRepository).removeItem(any(Item.class),mGetItemOnCartCallbackCaptor.capture());
        mGetItemOnCartCallbackCaptor.getValue().onItemOnCart(TOTAL_ITEM, getNewItemOnCart(), getItemsOnCartList());
    }

    /**
     * Testcase when Item Removal from cart fails.
     */
    @Test
    public void doRemoveItemError() {
        mCartPresenter.removeItem(getItem());
        verify(mItemsRepository).removeItem(any(Item.class),mGetItemOnCartCallbackCaptor.capture());
        mGetItemOnCartCallbackCaptor.getValue().onDataNotAvailable();
    }


    /**
     * Test while Loading confirmation with express enabled true.
     */
    /*@Test
    public void doLoadConfirmation() {
        mCartPresenter.loadConfirmation(getCheckoutData(), true);
        verify(mMasterpassExternalDataSource).getDataConfirmation(any(HashMap.class), any(Boolean.class), mGetLoadDataConfirmationCallbackCaptor.capture());
        mGetLoadDataConfirmationCallbackCaptor.getValue().onDataConfirmation(getMasterpassConfirmationObject(), any(Boolean.class));
    }*/

    /**
     * Test while Loading confirmation false
     */
    /*@Test
    public void doLoadConfirmationExpressEnabledFalse() {
        mCartPresenter.loadConfirmation(getCheckoutData(), false);
        verify(mMasterpassExternalDataSource).getDataConfirmation(any(HashMap.class), any(Boolean.class), mGetLoadDataConfirmationCallbackCaptor.capture());
        mGetLoadDataConfirmationCallbackCaptor.getValue().onDataConfirmation(getMasterpassConfirmationObject(), any(Boolean.class));
    }*/

    /**
     * Testcase when loading confirmation fails
     */
    /*@Test
    public void doLoadConfirmationError() {
        mCartPresenter.loadConfirmation(getCheckoutData(), false);
        verify(mMasterpassExternalDataSource).getDataConfirmation(any(HashMap.class), any(Boolean.class), mGetLoadDataConfirmationCallbackCaptor.capture());
        mGetLoadDataConfirmationCallbackCaptor.getValue().onDataNotAvailable();
    }*/

    private MasterpassConfirmationObject getMasterpassConfirmationObject() {
        MasterpassConfirmationObject masterpassConfirmationObject = new MasterpassConfirmationObject();
        masterpassConfirmationObject.setCardAccountNumberHidden("5455031750000246");
        return masterpassConfirmationObject;
    }

    private HashMap<String, Object> getCheckoutData() {
        return new HashMap<>();
    }

    private void getItemsOnCart() {
        mCartPresenter.loadItemsOnCart(false);
    }

    private CartPresenter getCartPresenter() {

        RemoveItemUseCase removeItemUseCase = new RemoveItemUseCase(mItemsRepository);
        RemoveAllItemUseCase removeAllItemUseCase = new RemoveAllItemUseCase(mItemsRepository);
        ConfirmTransactionUseCase confirmTransactionUseCase = new ConfirmTransactionUseCase(mMasterpassExternalDataSource);
        return new CartPresenter(useCaseHandler, mCartListView, getItemsOnCartUseCase, addItemUseCase, removeItemUseCase,
                removeAllItemUseCase, confirmTransactionUseCase);
    }

}
