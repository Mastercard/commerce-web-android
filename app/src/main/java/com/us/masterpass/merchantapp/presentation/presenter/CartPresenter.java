package com.us.masterpass.merchantapp.presentation.presenter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import com.mastercard.commerce.CardType;
import com.mastercard.commerce.CheckoutRequest;
import com.mastercard.commerce.CommerceConfig;
import com.mastercard.commerce.CommerceWebSdk;
import com.mastercard.commerce.CryptoOptions;
import com.mastercard.commerce.Mastercard;
import com.mastercard.commerce.Visa;
import com.us.masterpass.merchantapp.domain.masterpass.CommerceConstants;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveAllItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.ConfirmTransactionUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.CartPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.CartListView;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartPresenter implements CartPresenterInterface {

  private CartListView mCartListView;
  private final GetItemsOnCartUseCase mGetItemsOnCart;
  private final AddItemUseCase mAddItem;
  private final RemoveItemUseCase mRemoveItem;
  private final RemoveAllItemUseCase mRemoveAllItem;
  private final ConfirmTransactionUseCase mConfirmTransaction;
  private final UseCaseHandler mUseCaseHandler;
  private CommerceWebSdk commerceWebSdk;
  private double totalAmount;

  /**
   * Instantiates a new Cart presenter.
   *
   * @param useCaseHandler the use case handler
   * @param cartListView the cart list view
   * @param getItemsOnCart the get items on cart
   * @param addItem the add item
   * @param removeItem the remove item
   * @param removeAllItem the remove all item
   * @param confirmTransaction the confirm transaction
   */
  public CartPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull CartListView cartListView,
      @NonNull GetItemsOnCartUseCase getItemsOnCart,
      @NonNull AddItemUseCase addItem,
      @NonNull RemoveItemUseCase removeItem,
      @NonNull RemoveAllItemUseCase removeAllItem,
      @NonNull ConfirmTransactionUseCase confirmTransaction) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
    mCartListView = checkNotNull(cartListView, "itemsView cannot be null!");
    mGetItemsOnCart = checkNotNull(getItemsOnCart, "Get item use case must exist");
    mAddItem = checkNotNull(addItem, "Add item use case must exist");
    mRemoveItem = checkNotNull(removeItem, "Must not be null");
    mRemoveAllItem = checkNotNull(removeAllItem, "Must not be null");
    mConfirmTransaction = checkNotNull(confirmTransaction, "Mastercard use case");
    mCartListView.setPresenter(this);
  }

  @Override
  public void start() {
    loadItemsOnCart(false);
  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void result(int requestCode, int resultCode) {

  }

  @Override
  public void loadItemsOnCart(boolean forceUpdate) {
    mUseCaseHandler.execute(mGetItemsOnCart,
        new GetItemsOnCartUseCase.RequestValues(),
        new GetItemsOnCartUseCase.UseCaseCallback<GetItemsOnCartUseCase.ResponseValue>() {
          @Override
          public void onSuccess(GetItemsOnCartUseCase.ResponseValue response) {
            List<Item> itemsOnCart = response.getNewItemOnCart();
            updateBadge(response.getAddItemCount());
            showItemsOnCart(itemsOnCart);
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
            isSuppressShipping(response.isSuppressShipping());
            setTotalAmount(response.getTotalAmount());
          }

          @Override
          public void onError() {

          }
        });
  }

  @Override
  public void addItem(@NonNull Item itemToAdd) {
    checkNotNull(itemToAdd, "completedTask cannot be null!");
    mUseCaseHandler.execute(mAddItem,
        new AddItemUseCase.RequestValues(itemToAdd),
        new UseCase.UseCaseCallback<AddItemUseCase.ResponseValue>() {
          @Override
          public void onSuccess(AddItemUseCase.ResponseValue response) {
            updateBadge(response.getAddItemCount());
            showItemsOnCart(response.getNewItemOnCart());
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
            setTotalAmount(response.getTotalAmount());
          }

          @Override
          public void onError() {

          }
        });
  }

  @Override
  public void showItemsOnCart(List<Item> itemsOnCart) {
    mCartListView.showItems(itemsOnCart);
  }

  @Override
  public void removeItem(@NonNull Item itemToRemove) {
    checkNotNull(itemToRemove, "completedTask cannot be null!");
    mUseCaseHandler.execute(mRemoveItem,
        new RemoveItemUseCase.RequestValues(itemToRemove),
        new UseCase.UseCaseCallback<RemoveItemUseCase.ResponseValue>() {
          @Override
          public void onSuccess(RemoveItemUseCase.ResponseValue response) {
            updateBadge(response.getAddItemCount());
            showItemsOnCart(response.getNewItemOnCart());
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
            setTotalAmount(response.getTotalAmount());
          }

          @Override
          public void onError() {
            backToItemList();
          }
        });
  }

  @Override
  public void removeAllItem(@NonNull Item itemRemoveAll) {
    checkNotNull(itemRemoveAll, "completedTask cannot be null!");
    mUseCaseHandler.execute(mRemoveAllItem,
        new RemoveAllItemUseCase.RequestValues(itemRemoveAll),
        new UseCase.UseCaseCallback<RemoveAllItemUseCase.ResponseValue>() {
          @Override
          public void onSuccess(RemoveAllItemUseCase.ResponseValue response) {
            updateBadge(response.getAddItemCount());
            showItemsOnCart(response.getNewItemOnCart());
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
            setTotalAmount(response.getTotalAmount());
          }

          @Override
          public void onError() {
            backToItemList();
          }
        });
  }

  @Override
  public void showBadge() {

  }

  @Override
  public void updateBadge(String totalCartCount) {
    mCartListView.updateBadge(totalCartCount);
  }

  @Override
  public void totalPrice(String totalPrice) {
    mCartListView.totalPrice(totalPrice);
  }

  @Override
  public void subtotalPrice(String subtotalPrice) {
    mCartListView.subtotalPrice(subtotalPrice);
  }

  @Override
  public void taxPrice(String taxPrice) {
    mCartListView.taxPrice(taxPrice);
  }

  @Override
  public void backToItemList() {
    mCartListView.backToItemList();
  }

  @Override
  public void initializeMasterpassMerchant(Configuration configuration) {

  }

  @Override
  public void initializeMasterpassMerchant(Context context) {
    CommerceConfig config = new CommerceConfig(
        Locale.US,
        CommerceConstants.MERCHANT_ID,
        CommerceConstants.SRC_URL,
        CommerceConstants.CALLBACK_SCHEME);

    commerceWebSdk = new CommerceWebSdk(config);

    mCartListView.showLoadingSpinner(false);
  }

  @Override
  public void showMasterpassButton() {

  }

  @Override
  public void loadConfirmation(HashMap<String, Object> checkoutData) {
    mUseCaseHandler.execute(mGetItemsOnCart, new GetItemsOnCartUseCase.RequestValues(),
        new GetItemsOnCartUseCase.UseCaseCallback<GetItemsOnCartUseCase.ResponseValue>() {
          @Override public void onSuccess(GetItemsOnCartUseCase.ResponseValue response) {
            checkout(response);
          }

          @Override public void onError() {
            //onError
          }
        });
  }

  private void checkout(GetItemsOnCartUseCase.ResponseValue response) {
    Set<Mastercard.MastercardFormat> mastercardFormatSet = new HashSet<>();
    mastercardFormatSet.add(Mastercard.MastercardFormat.ICC);
    mastercardFormatSet.add(Mastercard.MastercardFormat.UCAF);

    Set<Visa.VisaFormat> visaFormatSet = new HashSet<>();
    visaFormatSet.add(Visa.VisaFormat.TVV);

    CryptoOptions mastercard = new Mastercard(mastercardFormatSet);
    CryptoOptions visa = new Visa(visaFormatSet);

    Set<CryptoOptions> cryptoOptionsSet = new HashSet<>();
    cryptoOptionsSet.add(mastercard);
    cryptoOptionsSet.add(visa);

    Set<CardType> cardTypes = new HashSet<>();
    cardTypes.add(CardType.MASTER);
    cardTypes.add(CardType.VISA);

    CheckoutRequest request = new CheckoutRequest.Builder()
        .amount(totalAmount)
        .cartId(UUID.randomUUID().toString())
        .currency("USD")
        .allowedCardTypes(cardTypes)
        .cryptoOptions(cryptoOptionsSet)
        .suppressShippingAddress(response.isSuppressShipping())
        .build();

    commerceWebSdk.checkout(request, mCartListView.getActivity());
  }

  private void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  @Override
  public void isSuppressShipping(boolean suppressShipping) {
    mCartListView.isSuppressShipping(suppressShipping);
  }
}