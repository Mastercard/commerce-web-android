package com.us.masterpass.merchantapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.data.device.ItemLocalDataSource;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConstants;
import com.us.masterpass.merchantapp.data.external.ItemExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.MockGetItemsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.login.IsLoggedUseCase;
import com.us.masterpass.merchantapp.domain.usecase.login.RemoveLoginUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.ItemsPresenter;
import com.us.masterpass.merchantapp.presentation.view.ItemsListView;
import com.us.masterpass.merchantapp.utils.BackgroundForTestUseCaseScheduler;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by akhildixit on 12/1/17.
 * <p>
 * Test created for {@link ItemsPresenter}
 */
public class ItemsPresenterTest {
    @Mock
    GetItemsOnCartUseCase getItemsOnCartUseCase;
    @Mock
    List<CartLocalObject> mCartLocalObjectList;
    @Mock
    ItemLocalDataSource mItemLocalDataSource;
    @Mock
    List<Item> mListItem;
    @Mock
    Handler handler;
    @Mock
    Item mItem;
    @Mock
    ItemsPresenter mItemsPresenterMock;
  SharedPreferences sp = Mockito.mock(SharedPreferences.class);
  Context context = Mockito.mock(Context.class);
  SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
  SettingsSaveConfigurationSdk settingsSaveConfigurationSdkMock =
      Mockito.mock(SettingsSaveConfigurationSdk.class);
  @Mock private ItemExternalDataSource mItemExternalDataSource;
  @Mock private ItemRepository mItemsRepository;
  @Mock private ItemsListView list;
  @Mock private Context mContext;
    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartCallback> mGetItemOnCartCallBack;
    @Captor
    private ArgumentCaptor<ItemDataSource.LoadItemsCallback> mLoadDataItemCallbackCaptor;
    @Captor
    private ArgumentCaptor<ItemDataSource.GetItemOnCartShippingOptionCallback> mGetItemOnCartShippingOptionCallbackArgumentCaptor;
  private ItemsPresenter mItemsPresenter;

    @Before
    public void setItemsUseCase() {
        MockitoAnnotations.initMocks(this);
        sp = Mockito.mock(SharedPreferences.class);
        context = Mockito.mock(Context.class);
        editor = Mockito.mock(SharedPreferences.Editor.class);
        //Mock data on shared preferences
        when(mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE))
                .thenReturn(sp);
        when(sp.edit()).thenReturn(editor);
        when(sp.getString("SDK_LANG", "en-US")).thenReturn("en-US");
        when(sp.getString("SDK_CURRENCY", "USD")).thenReturn("USD");
        when(sp.getBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, false)).thenReturn(true);
        when(handler.post(Matchers.any(Runnable.class))).thenReturn(true);
    }

    /*@Test
    public void loadItems() {
        mItemsPresenter = declarationItemsPresenter();
        mItemsPresenter.loadItems(true);
        verify(mItemsRepository).getItems(mLoadDataItemCallbackCaptor.capture());
        when(mListItem.size()).thenReturn(0);
        mLoadDataItemCallbackCaptor.getValue().onItemsLoaded(anyListOf(Item.class));
        verify(list).showItems(anyListOf(Item.class));
    }*/
    @Test
    public void updateBadge(){
        mItemsPresenter = declarationItemsPresenter();
        mItemsPresenter.updateBadge(anyString());
        verify(list).updateBadge(anyString());
    }
    @Test
    public void loadCartActivity(){
        mItemsPresenter=declarationItemsPresenter();
        mItemsPresenter.loadCartActivity();
        verify(list).loadCartActivity();
    }
    @Test
    public void loadSettingsActivity(){
        mItemsPresenter=declarationItemsPresenter();
        mItemsPresenter.loadSettings();
        verify(list).loadSettingsActivity();
    }

    /*@Test
    public void loadItemsOnError() {
        mItemsPresenter = declarationItemsPresenter();
        mItemsPresenter.loadItems(true);
        verify(mItemsRepository).getItems(mLoadDataItemCallbackCaptor.capture());
        mLoadDataItemCallbackCaptor.getValue().onDataNotAvailable();
        verify(list).showLoadingItemsError();
    }*/

    @Test
    public void getItems() {
        mItemsPresenter = declarationItemsPresenter();
        mItemsPresenter.getItemsOnCart();
        when(mCartLocalObjectList.size()).thenReturn(0);
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingOptionCallbackArgumentCaptor.capture());
        final ItemDataSource.GetItemOnCartShippingOptionCallback callback = mGetItemOnCartShippingOptionCallbackArgumentCaptor.getAllValues().get(0);
        try {
            callback.onItemOnCart(anyString(), null, anyListOf(CartLocalObject.class), anyBoolean());
        } catch (InvalidUseOfMatchersException ex) {
            //In order to test async call on UseCase, this catch exception when ArgumentCaptor is being called afters the callback
        }
        verifyNoMoreInteractions(mItemsRepository);
    }

    @Test
    public void getItemsOnError() {
        mItemsPresenter = declarationItemsPresenter();
        mItemsPresenter.getItemsOnCart();
        verify(mItemsRepository).getItemsOnCart(mGetItemOnCartShippingOptionCallbackArgumentCaptor.capture());
        mGetItemOnCartShippingOptionCallbackArgumentCaptor.getValue().onDataNotAvailable();
        verify(list).loadCartActivityShowError();
    }

    @Test
    public void addItems() {
        mItemsPresenter = declarationItemsPresenter();
        mItemsPresenter.addItem(mItem);
        when(mListItem.size()).thenReturn(0);
        verify(mItemsRepository).addItem(any(Item.class), mGetItemOnCartCallBack.capture());
        final ItemDataSource.GetItemOnCartCallback callback = mGetItemOnCartCallBack.getAllValues().get(0);
        try {
            callback.onItemOnCart(anyString(), anyListOf(Item.class), anyListOf(CartLocalObject.class));
        } catch (InvalidUseOfMatchersException ex) {

        }
        verifyNoMoreInteractions(mItemsRepository);
    }

    /*@Test
    public void showAlertOnLogged() {
        mItemsPresenter = declarationItemsPresenter();
        verify(list).setPresenter(mItemsPresenter);
        mItemsPresenter.loadLoginActivity();
        when(sp.getBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, false)).thenReturn(false);
        verify(list).showAlertIsLogged();
    }

    @Test
    public void loadLogin() {
        mItemsPresenter = declarationItemsPresenter();
        when(sp.getBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, false)).thenReturn(false);
        mItemsPresenter.loadLoginActivity();
        verify(list).loadLoginActivity();
    }


    @Test
    public void logout() {
        mItemsPresenter = declarationItemsPresenter();
        verify(list).setPresenter(mItemsPresenter);
        when(sp.getBoolean(SettingsSaveConstants.LOGIN_IS_LOGGED, false)).thenReturn(false);
        when(sp.getString(SettingsSaveConstants.LOGIN_USER_ID, "")).thenReturn("");
        when(sp.getString(SettingsSaveConstants.EXPRESS_PAIRING_ID, null)).thenReturn(null);
        when(sp.getBoolean(SettingsSaveConstants.SDK_CONFIG_EXPRESS, false)).thenReturn(false);
        mItemsPresenter.logout();
        verify(list).loadLoginActivity();
    }*/


    private ItemsPresenter declarationItemsPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
      MockGetItemsUseCase mockGetItemsUseCase = new MockGetItemsUseCase(mItemsRepository, null);
        AddItemUseCase addItemsUseCase = new AddItemUseCase(mItemsRepository);
        GetItemsOnCartUseCase getItemsOnCartUseCase = new GetItemsOnCartUseCase(mItemsRepository, mContext);
        IsLoggedUseCase isLoggedUseCase = new IsLoggedUseCase(mContext);
        RemoveLoginUseCase removeLoginUseCase = new RemoveLoginUseCase(mContext);
        return new ItemsPresenter(useCaseHandler, list, mockGetItemsUseCase,
                addItemsUseCase,
                getItemsOnCartUseCase,
                isLoggedUseCase, removeLoginUseCase);
    }

}
