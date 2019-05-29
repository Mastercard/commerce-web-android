package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.items.GetLocalItemsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.login.IsLoggedUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.ItemsPresenter;
import com.us.masterpass.merchantapp.presentation.view.ItemsListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test case for @{@link ItemsPresenter}
 */
public class DoLoadItemsTest extends AbsCartTest{

    private static final int REQUEST_CODE = 0;
    private static final int RESULT_CODE = 1;


    @Mock
    private ItemsListView mItemsListView;

    @Captor
    private ArgumentCaptor<ItemDataSource.LoadItemsCallback> mLoadItemsCallbackCaptor;

    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartShippingOptionCallback> mGetItemOnCartShippingCallbackCaptor;


    private ItemsPresenter mItemsPresenter;

    @Before
    public void init(){
        mItemsPresenter = instantiateItemsPresenter();
    }

    /**
     * Test case for loading items in list view when there are items to be displayed.
     */
  /*  @Test
    public void doLoadItemsDataAvailable() {
        mItemsPresenter.start();
        verify(mItemsRepository).getItems(mLoadItemsCallbackCaptor.capture());
        mLoadItemsCallbackCaptor.getValue().onItemsLoaded(getNewItemOnCart());
        verify(mItemsListView).showItems(anyList());
    }*/

    /**
     * Testcase when there are no items to displayed when the screen is loaded.
     */
 /*   @Test
    public void doLoadItemsDataNoAvailable() {
        mItemsPresenter.start();
        verify(mItemsRepository).getItems(mLoadItemsCallbackCaptor.capture());
        mLoadItemsCallbackCaptor.getValue().onDataNotAvailable();
        verify(mItemsListView).showLoadingItemsError();
    }*/

    /**
     * Testcase when an item is added into the cart.
     */
    @Test
    public void doAddItem() {
        mItemsPresenter.addItem(getItem());
        verify(mItemsRepository).addItem(any(Item.class), mGetItemOnCartCallbackCaptor.capture());
        mGetItemOnCartCallbackCaptor.getValue().onItemOnCart(TOTAL_ITEM, getNewItemOnCart(), getItemsOnCartList());
        verify(mItemsListView).updateBadge(any(String.class));
    }

    /**
     * Testcase when trying to add an item in cart fails.
     */
    @Test
    public void doAddItemWithError() {
        mItemsPresenter.addItem(getItem());
        verify(mItemsRepository).addItem(any(Item.class), mGetItemOnCartCallbackCaptor.capture());
        mGetItemOnCartCallbackCaptor.getValue().onDataNotAvailable();
    }

    /**
     * Testcase when cart is supposed to be displayed.
     */
    @Test
    public void doLoadCart() {
        getItemsOnCart();
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingCallbackCaptor.capture());
        mGetItemOnCartShippingCallbackCaptor.getValue().onItemOnCart(TOTAL_ITEM, getNewItemOnCart(), getItemsOnCartList(), true);
        verify(mItemsListView).loadCartActivity();
    }

    /**
     * Testcase when cart is supposed be displayed and the cart is empty.
     */
    @Test
    public void doLoadCartWithNoItems() {
        getItemsOnCart();
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingCallbackCaptor.capture());
        mGetItemOnCartShippingCallbackCaptor.getValue().onDataNotAvailable();
        verify(mItemsListView).loadCartActivityShowError();
    }

    /**
     * Testcase for empty methods which are fore casted to have future uses.
     */
    @Test
    public void doEmptyMethodTest() {
        presenter = mItemsPresenter;
        super.doEmptyMethodTest();
        mItemsPresenter.result(REQUEST_CODE, RESULT_CODE);
        mItemsPresenter.showBadge();
    }

    /**
     * Testcase when user navigates to Login UI.
     */
    @Test
    public void doLoadLoginActivity() {
        when(sharedPreferences.getBoolean(any(String.class), any(Boolean.class))).thenReturn(false);
        mItemsPresenter.loadLoginActivity();
        verify(mItemsListView).loadLoginActivity();
    }

    /**
     * Testcase when user navigates to login UI when already logged in.
     */
    @Test
    public void doLoadLoginActivityWithError() {
        when(SettingsSaveConfigurationSdk.getInstance(mContext).getIsLogged()).thenReturn(true);
        mItemsPresenter.loadLoginActivity();
        verify(mItemsListView).showAlertIsLogged();
    }

    /**
     * Testcase when user Logout.
     */
    @Test
    public void doLogout() {
        when(sharedPreferences.edit()).thenReturn(editor);
        mItemsPresenter.logout();
        verify(mItemsListView).loadLoginActivity();
    }

    /**
     * Testcase when user go to settings UI.
     */
    @Test
    public void doLoadSettings() {
        mItemsPresenter.loadSettings();
        verify(mItemsListView).loadSettingsActivity();
    }




    private ItemsPresenter instantiateItemsPresenter() {

        GetLocalItemsUseCase getItemsUseCase = new GetLocalItemsUseCase(mItemsRepository, mContext);
        IsLoggedUseCase isLoggedUseCase = new IsLoggedUseCase(mContext);
        RemoveLoginUseCase removeLoginUseCase = new RemoveLoginUseCase(mContext);
        return new ItemsPresenter(useCaseHandler, mItemsListView, getItemsUseCase, addItemUseCase,
                getItemsOnCartUseCase, isLoggedUseCase, removeLoginUseCase);
    }

    private void getItemsOnCart() {
        mItemsPresenter.getItemsOnCart();
    }

}
