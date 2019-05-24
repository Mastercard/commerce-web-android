package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.presentation.presenter.CartCompletePresenter;
import com.us.masterpass.merchantapp.presentation.view.CartCompleteListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;


/**
 * Testcase for use cases related to @CartCompletePresenter.
 */
public class DoCartCompleteTest extends AbsCartTest
{

    @Mock
    private CartCompleteListView mCartCompleteListView;

    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartShippingOptionCallback> mGetItemOnCartShippingCallbackCaptor;


    private CartCompletePresenter mCartCompletedPresenter;


    @Before
    public void init(){
        mCartCompletedPresenter = getCartPresenter();
    }


    /**
     * Testcase for empty methods which are fore casted to have future uses.
     */
    @Test
    public void doEmptyMethodTest() {
        presenter = mCartCompletedPresenter;
        super.doEmptyMethodTest();
    }

    /**
     * Testcase when Loading cart on Cart Screen.
     */
    @Test
    public void doLoadCart() {
        getItemsOnCart();
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingCallbackCaptor.capture());
        mGetItemOnCartShippingCallbackCaptor.getValue().onItemOnCart(TOTAL_ITEM, getNewItemOnCart(), getItemsOnCartList(), true);
        verify(mCartCompleteListView).showItems(anyList());
    }

    /**
     * Testcase when checkout is invoked and the cart is empty.
     */
    @Test
    public void doLoadCartWithNoItems() {
        getItemsOnCart();
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingCallbackCaptor.capture());
        mGetItemOnCartShippingCallbackCaptor.getValue().onDataNotAvailable();
    }

    /**
     * Testcase when Loading the cart for the first time.
     */
    @Test
    public void doStart() {
        mCartCompletedPresenter.start();
    }

    private void getItemsOnCart() {
        mCartCompletedPresenter.loadItemsOnCart(false);
    }

    private CartCompletePresenter getCartPresenter() {

        return new CartCompletePresenter(useCaseHandler, mCartCompleteListView, getItemsOnCartUseCase);
    }

}
