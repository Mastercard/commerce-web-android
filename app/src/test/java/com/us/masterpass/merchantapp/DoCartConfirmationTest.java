package com.us.masterpass.merchantapp;

import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.device.CartLocalStorage;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.CompleteTransactionUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.CartConfirmationPresenter;
import com.us.masterpass.merchantapp.presentation.view.CartConfirmationListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;

/**
 * Testcase related to @{@link CartConfirmationPresenter} use cases.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ CartLocalStorage.class})
public class DoCartConfirmationTest extends AbsCartTest
{

    @Mock
    private CartConfirmationListView mCartCompleteListView;

    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartShippingOptionCallback> mGetItemOnCartShippingCallbackCaptor;

    @Captor
    private ArgumentCaptor<MasterpassDataSource.LoadDataConfirmationCallback> mLoadDataConfirmationCallbackCaptor;

    private CartConfirmationPresenter mCartConfirmationPresenter;

    @Mock
    private CartLocalStorage mCartLocalStorage;


    @Before
    public void init(){
        mCartConfirmationPresenter = getCartPresenter();
        PowerMockito.mockStatic(CartLocalStorage.class);
        PowerMockito.when(CartLocalStorage.getInstance(mContext)).thenReturn(mCartLocalStorage);
    }

    @Mock
    private MasterpassExternalDataSource mMasterpassExternalDataSource;


    /**
     * Testcase for empty methods which are fore casted to have future uses.
     */
    @Test
    public void doEmptyMethodTest() {
        presenter = mCartConfirmationPresenter;
        super.doEmptyMethodTest();
    }

    /**
     * Testcase when Loading the cart for the first time.
     */
    @Test
    public void doStart() {
        mCartConfirmationPresenter.start();
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
     * Testcase when Loading cart with no items.
     */
    @Test
    public void doLoadCartWithNoItems() {
        getItemsOnCart();
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingCallbackCaptor.capture());
        mGetItemOnCartShippingCallbackCaptor.getValue().onDataNotAvailable();
    }

    /**
     * Test case for when confirming checkout.
     */
    @Test
    public void doConfirmCheckout() throws IOException {
        mCartConfirmationPresenter.confirmCheckout(getMasterpassConfimationObject());
        verify(mMasterpassExternalDataSource).sendConfirmation(any(MasterpassConfirmationObject.class),mLoadDataConfirmationCallbackCaptor.capture());
        mLoadDataConfirmationCallbackCaptor.getValue().onDataConfirmation(getMasterpassConfimationObject(), true);
    }

    /**
     * Test case when confirming checkout and Error occurs.
     *
     */
    @Test
    public void doConfirmCheckoutWithError() throws IOException {
        mCartConfirmationPresenter.confirmCheckout(getMasterpassConfimationObject());
        verify(mMasterpassExternalDataSource).sendConfirmation(any(MasterpassConfirmationObject.class),mLoadDataConfirmationCallbackCaptor.capture());
        mLoadDataConfirmationCallbackCaptor.getValue().onDataNotAvailable();
        verify(mCartCompleteListView).showLoadingSpinner(any(Boolean.class));
    }

    /**
     * Test case while doing express checkout.
     *
     */
    @Test
    public void doExpressCheckout() throws IOException {
        mCartConfirmationPresenter.expressCheckout(getMasterpassConfimationObject());
        verify(mMasterpassExternalDataSource).expressCheckout(any(MasterpassConfirmationObject.class),mLoadDataConfirmationCallbackCaptor.capture());
        mLoadDataConfirmationCallbackCaptor.getValue().onDataConfirmation(getMasterpassConfimationObject(), true);
    }

    /**
     * Test case when doing express checkout and Error occurs.
     * @throws IOException
     */
    @Test
    public void doExpressCheckoutWithError() throws IOException {
        mCartConfirmationPresenter.expressCheckout(getMasterpassConfimationObject());
        verify(mMasterpassExternalDataSource).expressCheckout(any(MasterpassConfirmationObject.class),mLoadDataConfirmationCallbackCaptor.capture());
        mLoadDataConfirmationCallbackCaptor.getValue().onDataNotAvailable();
        verify(mCartCompleteListView).showLoadingSpinner(any(Boolean.class));
    }

    private MasterpassConfirmationObject getMasterpassConfimationObject() {
        MasterpassConfirmationObject masterpassConfirmationObject = new MasterpassConfirmationObject();
        masterpassConfirmationObject.setCardAccountNumberHidden("*****21865");
        return masterpassConfirmationObject;
    }

    private void getItemsOnCart() {
        mCartConfirmationPresenter.loadItemsOnCart(false);
    }

    private CartConfirmationPresenter getCartPresenter() {
        CompleteTransactionUseCase completeTransactionUseCase = new CompleteTransactionUseCase(mMasterpassExternalDataSource, mContext);
        ConfirmExpressTransactionUseCase confirmTransactionUseCase = new ConfirmExpressTransactionUseCase(mMasterpassExternalDataSource, mContext);
        return new CartConfirmationPresenter(useCaseHandler, mCartCompleteListView, getItemsOnCartUseCase,
                completeTransactionUseCase, confirmTransactionUseCase);
    }

}
