package com.us.masterpass.merchantapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.mastercard.mp.switchservices.HttpCallback;
import com.mastercard.mp.switchservices.MasterpassSwitchServices;
import com.mastercard.mp.switchservices.paymentData.PaymentData;
import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveAllItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.ConfirmTransactionUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.GetSelectedPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.CartPresenter;
import com.us.masterpass.merchantapp.presentation.view.CartListView;
import com.us.masterpass.merchantapp.utils.BackgroundForTestUseCaseScheduler;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by akhildixit on 12/5/17.
 * <p>
 * Test created for {@link CartPresenter}
 */
@RunWith(PowerMockRunner.class) @PrepareForTest({
    MasterpassSwitchServices.class, BuildConfig.class, MasterpassSdkCoordinator.class
})
public class CartPresenterTest extends android.test.InstrumentationTestCase {

  @Mock AssetManager assetManager;
  public static final String TRANSACTION_ID = "TransactionId";
  public static final String PAIRING_TRANSACTION_ID = "PairingTransactionId";
  @Mock HashMap<String, Object> mHashMap;
  @Mock List<Item> mListItem;
  @Mock List<CartLocalObject> mCartLocalObjectList;
  GetItemsOnCartUseCase getItemsOnCartUseCase;
  @Mock private SettingsSaveConfigurationSdk settingsSaveConfigurationSdk;
  @Mock private BuildConfig buildConfig;
  @Mock private MasterpassSdkCoordinator masterpassSdkCoordinator;
  @Mock private CartListView mCartListView;
  @Mock private PrivateKey privateKey;
  @Mock PaymentData paymentData;
  @Captor ArgumentCaptor<HttpCallback<PaymentData>> httpCallbackArgumentCaptor;
  @Mock
  private ItemRepository mItemRepository;
  @Mock
  private Item mItem;

  @Mock
  private Context context;
  @Captor
  private ArgumentCaptor<List<Item>> captor;
  @Mock
  private MasterpassSwitchServices masterpassSwitchServices;
  @Mock
  private MasterpassExternalDataSource mMasterpassExternalDataSource;
  @Mock
  private MasterpassConfirmationObject mMasterpassConfObject;
  @Captor
  private ArgumentCaptor<ItemDataSource.GetItemOnCartShippingOptionCallback> mGetItemOnCartShippingOptionCallBack;
  @Captor
  private ArgumentCaptor<ItemDataSource.GetItemOnCartCallback> mGetItemOnCartCallBack;
  @Captor
  private ArgumentCaptor<MasterpassDataSource.LoadDataConfirmationCallback> mLoadDataConfirmationCallbackArgumentCaptor;
  private CartPresenter mCartPresenter;
  private boolean expresscheckoutenable = true;


  @Before
  public void setCartMock() {
    MockitoAnnotations.initMocks(this);
    when(mMasterpassConfObject.getCardAccountNumberHidden()).thenReturn("1234");
    getItemsOnCartUseCase = Mockito.mock(GetItemsOnCartUseCase.class);
  }

  @Test
  public void loadCartList() {
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.loadItemsOnCart(true);
    when(mCartLocalObjectList.size()).thenReturn(0);
    verify(mItemRepository).getItemsOnCart(mGetItemOnCartShippingOptionCallBack.capture());
    final ItemDataSource.GetItemOnCartShippingOptionCallback callback = mGetItemOnCartShippingOptionCallBack.getAllValues().get(0);
    try {
      callback.onItemOnCart(anyString(), null, anyListOf(CartLocalObject.class), anyBoolean());
    } catch (InvalidUseOfMatchersException ex) {
    }
    mCartPresenter.showItemsOnCart(anyListOf(Item.class));
    verify(mCartListView).showItems(anyListOf(Item.class));
    verifyNoMoreInteractions(mItemRepository);
  }
  @Test
  public void showItemsOnCart(){
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.showItemsOnCart(anyListOf(Item.class));
    verify(mCartListView).showItems(anyListOf(Item.class));
  }
  @Test
  public void updateBadge(){
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.updateBadge(anyString());
    verify(mCartListView).updateBadge(anyString());
  }
  @Test
  public void totalPrice(){
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.totalPrice(anyString());
    verify(mCartListView).totalPrice(anyString());
  }

  @Test
  public void subtotalPrice(){
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.subtotalPrice(anyString());
    verify(mCartListView).subtotalPrice(anyString());
  }

  @Test
  public void taxPrice(){
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.taxPrice(anyString());
    verify(mCartListView).taxPrice(anyString());
  }
  @Test
  public void isSupressShipping(){
    mCartPresenter=declarationCartPresenter();
    mCartPresenter.isSuppressShipping(anyBoolean());
    verify(mCartListView).isSuppressShipping(anyBoolean());
  }

  @Test
  public void addItemInCart() {
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.addItem(mItem);
    when(mListItem.size()).thenReturn(0);
    verify(mItemRepository).addItem(eq(mItem), mGetItemOnCartCallBack.capture());
    final ItemDataSource.GetItemOnCartCallback callback = mGetItemOnCartCallBack.getAllValues().get(0);
    try {
      callback.onItemOnCart(anyString(), anyListOf(Item.class), anyListOf(CartLocalObject.class));
    } catch (InvalidUseOfMatchersException ex) {

    }
    verifyNoMoreInteractions(mItemRepository);
  }

  @Test
  public void removeItemInCart() {
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.removeItem(mItem);
    when(mListItem.size()).thenReturn(0);
    verify(mItemRepository).removeItem(eq(mItem), mGetItemOnCartCallBack.capture());
    final ItemDataSource.GetItemOnCartCallback callback = mGetItemOnCartCallBack.getAllValues().get(0);
    try {
      callback.onItemOnCart(anyString(), anyListOf(Item.class), anyListOf(CartLocalObject.class));
    } catch (InvalidUseOfMatchersException ex) {

    }
    mCartPresenter.showItemsOnCart(anyListOf(Item.class));
    verify(mCartListView).showItems(anyListOf(Item.class));
    verifyNoMoreInteractions(mItemRepository);
  }

  @Test
  public void removeItemInCartOnError() {
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.removeItem(mItem);
    when(mListItem.size()).thenReturn(0);
    verify(mItemRepository).removeItem(eq(mItem), mGetItemOnCartCallBack.capture());
    final ItemDataSource.GetItemOnCartCallback callback = mGetItemOnCartCallBack.getAllValues().get(0);
    try {
      callback.onDataNotAvailable();
    } catch (InvalidUseOfMatchersException ex) {

    }
    verify(mCartListView).backToItemList();
  }

  @Test
  public void removeAllItemInCart() {
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.removeAllItem(mItem);
    when(mListItem.size()).thenReturn(0);
    verify(mItemRepository).removeAllItem(eq(mItem), mGetItemOnCartCallBack.capture());
    final ItemDataSource.GetItemOnCartCallback callback = mGetItemOnCartCallBack.getAllValues().get(0);
    try {
      callback.onItemOnCart(anyString(), anyListOf(Item.class), anyListOf(CartLocalObject.class));
    } catch (InvalidUseOfMatchersException ex) {

    }
    verifyNoMoreInteractions(mItemRepository);
  }

  @Test
  public void removeAllItemInCartOnError() {
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.removeAllItem(mItem);
    when(mListItem.size()).thenReturn(0);
    verify(mItemRepository).removeAllItem(eq(mItem), mGetItemOnCartCallBack.capture());
    final ItemDataSource.GetItemOnCartCallback callback = mGetItemOnCartCallBack.getAllValues().get(0);
    try {
      callback.onDataNotAvailable();
    } catch (InvalidUseOfMatchersException ex) {

    }
    verify(mCartListView).backToItemList();

  }

  /*@Test
  public void loadConfirmationOnError(){
    setWhens();
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.loadConfirmation(mHashMap, true);
    verify(masterpassSwitchServices).paymentData(mHashMap.get("TRANSACTION_ID").toString(),
        buildConfig.CHECKOUT_ID, masterpassSdkCoordinator.getGeneratedCartId(), buildConfig
            .ENVIRONMENT.toUpperCase(), getPublicKey(), httpCallbackArgumentCaptor.capture());

    httpCallbackArgumentCaptor.getValue().onResponse(paymentData);

    //verify(mMasterpassExternalDataSource).getDataConfirmation(eq(mHashMap), anyBoolean(), mLoadDataConfirmationCallbackArgumentCaptor.capture());
    //final MasterpassDataSource.LoadDataConfirmationCallback callback = mLoadDataConfirmationCallbackArgumentCaptor.getAllValues().get(0);
    //try{
    //    callback.onDataNotAvailable();
    //}catch (InvalidUseOfMatchersException ex){
    //
    //}
    //verify(mCartListView).showLoadingSpinner(anyBoolean());
  }*/

  /*@Test
  public void loadConfirmation() {
    setWhens();
    mCartPresenter = declarationCartPresenter();
    mCartPresenter.loadConfirmation(mHashMap, true);
    verify(mMasterpassExternalDataSource).getDataConfirmation(eq(mHashMap), anyBoolean(), mLoadDataConfirmationCallbackArgumentCaptor.capture());
    final MasterpassDataSource.LoadDataConfirmationCallback callback = mLoadDataConfirmationCallbackArgumentCaptor.getAllValues().get(0);
    try {
      callback.onDataConfirmation(mMasterpassConfObject, true);
    } catch (InvalidUseOfMatchersException ex) {

    }
    if (expresscheckoutenable) {
      verify(mCartListView).showConfirmationPairingScreen(any(MasterpassConfirmationObject.class));
    } else {
      verify(mCartListView).showConfirmationScreen(any(MasterpassConfirmationObject.class));
    }
  }*/

  private CartPresenter declarationCartPresenter() {
    UseCaseHandler useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
    GetItemsOnCartUseCase getItemsOnCartUseCase = new GetItemsOnCartUseCase(mItemRepository, context);
    AddItemUseCase addItemsUseCase = new AddItemUseCase(mItemRepository);
    RemoveItemUseCase removeItemUseCase = new RemoveItemUseCase(mItemRepository);
    RemoveAllItemUseCase removeAllItemUseCase = new RemoveAllItemUseCase(mItemRepository);
    ConfirmTransactionUseCase confirmTransactionUseCase = new ConfirmTransactionUseCase(mMasterpassExternalDataSource);
    IsPaymentMethodEnabledUseCase isPaymentMethodEnabledUseCase =
        new IsPaymentMethodEnabledUseCase(settingsSaveConfigurationSdk);
    GetSelectedPaymentMethodUseCase getSelectedPaymentMethodUseCase =
        new GetSelectedPaymentMethodUseCase(masterpassSdkCoordinator);
    return new CartPresenter(useCaseHandler, mCartListView, getItemsOnCartUseCase,
        addItemsUseCase, removeItemUseCase, removeAllItemUseCase, confirmTransactionUseCase,
        isPaymentMethodEnabledUseCase, getSelectedPaymentMethodUseCase);
  }

  private void setWhens() {
    mHashMap = new HashMap<>();
    mHashMap.put(TRANSACTION_ID,"8274932");
    getPublicKey();
  }

  public PrivateKey getPublicKey() {
    PrivateKey privateKey = null;
    try {
      KeyStore keyStore = KeyStore.getInstance("PKCS12");
      InputStream keyStoreInputStream =
          getInstrumentation().getContext().openFileInput(BuildConfig
              .MERCHANT_P12_CERTIFICATE);
      keyStore.load(keyStoreInputStream, BuildConfig.PASSWORD.toCharArray());
      return (PrivateKey) keyStore.getKey(BuildConfig.KEY_ALIAS,
          BuildConfig.PASSWORD.toCharArray());
    } catch (Exception e) {
      Log.d("CartFragment", e.toString());
    }
    return null;
  }
}
