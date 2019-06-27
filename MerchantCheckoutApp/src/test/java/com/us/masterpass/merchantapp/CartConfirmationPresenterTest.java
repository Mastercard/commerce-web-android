package com.us.masterpass.merchantapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.data.device.CartLocalStorage;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.Constants;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.CompleteTransactionUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.ConfirmExpressTransactionUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.CartConfirmationPresenter;
import com.us.masterpass.merchantapp.presentation.view.CartConfirmationListView;
import com.us.masterpass.merchantapp.utils.BackgroundForTestUseCaseScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by akhildixit on 12/8/17.
 * <p>
 * Test created for {@link CartConfirmationPresenter}
 */

public class CartConfirmationPresenterTest {
  SharedPreferences sp = Mockito.mock(SharedPreferences.class);
  Context context = Mockito.mock(Context.class);
  GetItemsOnCartUseCase getItemsOnCartUseCase;
    @Mock
    private CartConfirmationListView mCartConfirmationListView;
    @Mock
    private ItemRepository mItemRepository;
    @Mock
    private MasterpassConfirmationObject mMasterpassConfirmationObject;
    @Mock
    private MasterpassExternalDataSource mMasterpassExternal;
    @Mock
    private Context mContext;
    @Mock
    private CartLocalStorage mCartLocalStorage;
    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartShippingOptionCallback> mGetItemOnCartShippingOptionCallback;
    @Captor
    private ArgumentCaptor<MasterpassDataSource.LoadDataConfirmationCallback> mLoadDataConfirmationCallback;
    private CartConfirmationPresenter mCartConfirmationPresenter;

    @Before
    public void setCartConfirmationUseCaseMock() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(this);
        sp = Mockito.mock(SharedPreferences.class);
        context = Mockito.mock(Context.class);
        getItemsOnCartUseCase = Mockito.mock(GetItemsOnCartUseCase.class);
        //Mock data on shared preferences
        when(mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE))
                .thenReturn(sp);
        when(sp.getString("SDK_LANG", "en-US")).thenReturn("en-US");
        when(sp.getString("SDK_CURRENCY", "USD")).thenReturn("USD");
        when(mCartLocalStorage.getCartTotal(Constants.LOCAL_CART_DATASOURCE)).thenReturn(0.00);
        when(mMasterpassConfirmationObject.getCardAccountNumberHidden()).thenReturn("000");
    }

    @Test
    public void loadItems() {
        mCartConfirmationPresenter = declarationCartConfirmationPresenter();
        mCartConfirmationPresenter.loadItemsOnCart(false);
        verify(mItemRepository).getItemsOnCart(mGetItemOnCartShippingOptionCallback.capture());
        ItemDataSource.GetItemOnCartShippingOptionCallback callback = mGetItemOnCartShippingOptionCallback.getAllValues().get(0);
        try {
            callback.onItemOnCart(anyString(), null, anyListOf(CartLocalObject.class), anyBoolean());
        } catch (InvalidUseOfMatchersException ex) {

        }
        mCartConfirmationPresenter.showItemsOnCart(anyListOf(Item.class));
        verify(mCartConfirmationListView).showItems(anyListOf(Item.class));
    }

    @Test
    public void showItemsOnCart() {
        mCartConfirmationPresenter = declarationCartConfirmationPresenter();
        mCartConfirmationPresenter.showItemsOnCart(anyListOf(Item.class));
        verify(mCartConfirmationListView).showItems(anyListOf(Item.class));
    }

    @Test
    public void totalPrice() {
        mCartConfirmationPresenter = declarationCartConfirmationPresenter();
        mCartConfirmationPresenter.totalPrice(anyString());
        verify(mCartConfirmationListView).totalPrice(anyString());
    }

    @Test
    public void subtotalPrice() {
        mCartConfirmationPresenter = declarationCartConfirmationPresenter();
        mCartConfirmationPresenter.subtotalPrice(anyString());
        verify(mCartConfirmationListView).subtotalPrice(anyString());
    }

    @Test
    public void taxPrice() {
        mCartConfirmationPresenter = declarationCartConfirmationPresenter();
        mCartConfirmationPresenter.taxPrice(anyString());
        verify(mCartConfirmationListView).taxPrice(anyString());
    }
    @Test
    public void isSupressShipping(){
        mCartConfirmationPresenter = declarationCartConfirmationPresenter();
        mCartConfirmationPresenter.isSuppressShipping(anyBoolean());
        verify(mCartConfirmationListView).isSuppressShipping(anyBoolean());
    }

    /*@Test
    public void confirmCheckout() {
        //Null pointer when try to get not mocked file on device, controlled
        mCartConfirmationPresenter = declarationCartConfirmationPresenter();
        mCartConfirmationPresenter.confirmCheckout(mMasterpassConfirmationObject);
        verify(mMasterpassExternal).sendConfirmation(eq(mMasterpassConfirmationObject), mLoadDataConfirmationCallback.capture());
        MasterpassDataSource.LoadDataConfirmationCallback callback = mLoadDataConfirmationCallback.getAllValues().get(0);
        try {
            callback.onDataConfirmation(mMasterpassConfirmationObject, true);
        } catch (InvalidUseOfMatchersException ex) {

        }
//        verify(mCartConfirmationListView).showLoadingSpinner(anyBoolean());
        verify(mCartConfirmationListView).showCompleteScreen(any(MasterpassConfirmationObject.class));
    }
    @Test
    public void confirmCheckoutOnError(){
        mCartConfirmationPresenter = declarationCartConfirmationPresenter();
        mCartConfirmationPresenter.confirmCheckout(mMasterpassConfirmationObject);
        verify(mMasterpassExternal).sendConfirmation(eq(mMasterpassConfirmationObject),mLoadDataConfirmationCallback.capture());
        MasterpassDataSource.LoadDataConfirmationCallback callback=mLoadDataConfirmationCallback.getAllValues().get(0);
        try{
            callback.onDataNotAvailable();
        }catch (InvalidUseOfMatchersException ex){

        }
//        verify(mCartConfirmationListView).showLoadingSpinner(anyBoolean());
    }*/
   /*@Test
   public void expressCheckout(){
       mCartConfirmationPresenter=declarationCartConfirmationPresenter();
     //  mCartConfirmationPresenter.expressCheckout(mMasterpassConfirmationObject);
     //   verify(mMasterpassExternal).expressCheckout(eq(mMasterpassConfirmationObject),
     //     mLoadDataConfirmationCallback.capture(), );
       MasterpassDataSource.LoadDataConfirmationCallback callback=mLoadDataConfirmationCallback.getAllValues().get(0);
       try{
           callback.onDataConfirmation(mMasterpassConfirmationObject,true);
       }catch (InvalidUseOfMatchersException ex){

       }
//       verify(mCartConfirmationListView).showLoadingSpinner(anyBoolean());
       verify(mCartConfirmationListView).showCompleteScreen(any(MasterpassConfirmationObject.class));
   }
   @Test
   public void expressCheckoutOnError(){
       mCartConfirmationPresenter=declarationCartConfirmationPresenter();
     //  mCartConfirmationPresenter.expressCheckout(mMasterpassConfirmationObject);
     //  verify(mMasterpassExternal).expressCheckout(eq(mMasterpassConfirmationObject),
     //     mLoadDataConfirmationCallback.capture(), );
       MasterpassDataSource.LoadDataConfirmationCallback callback=mLoadDataConfirmationCallback.getAllValues().get(0);
       try{
           callback.onDataNotAvailable();
       }catch (InvalidUseOfMatchersException ex){

       }
//       verify(mCartConfirmationListView).showLoadingSpinner(anyBoolean());
   }*/

    private CartConfirmationPresenter declarationCartConfirmationPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
        GetItemsOnCartUseCase getItemsOnCartUseCase = new GetItemsOnCartUseCase(mItemRepository, context);
        CompleteTransactionUseCase completeTransactionUseCase = new CompleteTransactionUseCase(mMasterpassExternal, mContext);
        ConfirmExpressTransactionUseCase confirmExpressTransactionUseCase = new ConfirmExpressTransactionUseCase(mMasterpassExternal, mContext);
        return new CartConfirmationPresenter(useCaseHandler, mCartConfirmationListView, getItemsOnCartUseCase, completeTransactionUseCase, confirmExpressTransactionUseCase);
    }

}
