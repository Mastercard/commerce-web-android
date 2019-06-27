package com.us.masterpass.merchantapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.CartCompletePresenter;
import com.us.masterpass.merchantapp.presentation.view.CartCompleteListView;
import com.us.masterpass.merchantapp.utils.BackgroundForTestUseCaseScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sebastian Farias on 17-10-17.
 * <p>
 * Test created for {@link CartCompletePresenter}
 */

public class CartCompletePresenterTest {

    @Mock
    private CartCompleteListView mCartCompleteListView;

    @Mock
    private ItemRepository mItemRepository;

    @Mock
    private Context mContext;

    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartShippingOptionCallback> mGetItemOnCartShippingOptionCallbackAC;

    private CartCompletePresenter mCartCompletePresenter;
    UseCaseHandler useCaseHandler;

    SharedPreferences sp = Mockito.mock(SharedPreferences.class);
    Context context = Mockito.mock(Context.class);
    GetItemsOnCartUseCase getItemsOnCartUseCase;

    @Before
    public void setCartConfirmationUseCaseMock() {
        useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());

        MockitoAnnotations.initMocks(this);
        sp = Mockito.mock(SharedPreferences.class);
        context = Mockito.mock(Context.class);
        getItemsOnCartUseCase = Mockito.mock(GetItemsOnCartUseCase.class);

        //Mock data on shared preferences
        when(mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE))
                .thenReturn(sp);
        when(sp.getString("SDK_LANG", "en-US")).thenReturn("en-US");
        when(sp.getString("SDK_CURRENCY", "USD")).thenReturn("USD");
    }

    @Test
    public void loadItemsOnCart() {
        mCartCompletePresenter = declarationCartCompletePresenter();
        mCartCompletePresenter.loadItemsOnCart(false);
        verify(mItemRepository).getItemsOnCart(mGetItemOnCartShippingOptionCallbackAC.capture());
        ItemDataSource.GetItemOnCartShippingOptionCallback callback = mGetItemOnCartShippingOptionCallbackAC.getAllValues().get(0);
        try {
            callback.onItemOnCart(
                    anyString(),
                    null,
                    anyListOf(CartLocalObject.class),
                    anyBoolean());
        } catch (InvalidUseOfMatchersException ex){
            //In order to test async call on UseCase, this catch exception when ArgumentCaptor is being called afters the callback
        }
        mCartCompletePresenter.showItemsOnCart(anyListOf(Item.class));
        verify(mCartCompleteListView).showItems(anyListOf(Item.class));
    }

    @Test
    public void showItemsOnCart(){
        mCartCompletePresenter = declarationCartCompletePresenter();
        mCartCompletePresenter.showItemsOnCart(anyListOf(Item.class));
        verify(mCartCompleteListView).showItems(anyListOf(Item.class));
    }

    @Test
    public void totalPrice(){
        mCartCompletePresenter = declarationCartCompletePresenter();
        mCartCompletePresenter.totalPrice(anyString());
        verify(mCartCompleteListView).totalPrice(anyString());
    }

    @Test
    public void subtotalPrice(){
        mCartCompletePresenter = declarationCartCompletePresenter();
        mCartCompletePresenter.subtotalPrice(anyString());
        verify(mCartCompleteListView).subtotalPrice(anyString());
    }

    @Test
    public void taxPrice(){
        mCartCompletePresenter = declarationCartCompletePresenter();
        mCartCompletePresenter.taxPrice(anyString());
        verify(mCartCompleteListView).taxPrice(anyString());
    }
    @Test
    public void isSupressShipping(){
        mCartCompletePresenter=declarationCartCompletePresenter();
        mCartCompletePresenter.isSuppressShipping(anyBoolean());
        verify(mCartCompleteListView).isSuppressShipping(anyBoolean());
    }

    private CartCompletePresenter declarationCartCompletePresenter() {
        GetItemsOnCartUseCase getItemsOnCartUseCase = new GetItemsOnCartUseCase(mItemRepository, context);
        return new CartCompletePresenter(useCaseHandler, mCartCompleteListView, getItemsOnCartUseCase);
    }

}
