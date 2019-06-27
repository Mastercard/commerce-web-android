package com.us.masterpass.merchantapp.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.mastercard.mp.checkout.MasterpassButton;
import com.mastercard.mp.checkout.MasterpassMerchant;
import com.mastercard.mp.checkout.MasterpassMerchantConfiguration;
import com.mastercard.mp.switchservices.HttpCallback;
import com.mastercard.mp.switchservices.MasterpassSwitchServices;
import com.mastercard.mp.switchservices.ServiceError;
import com.mastercard.mp.switchservices.checkout.PairingIdResponse;
import com.mastercard.mp.switchservices.checkout.PreCheckoutCard;
import com.mastercard.mp.switchservices.checkout.PreCheckoutData;
import com.mastercard.mp.switchservices.checkout.PreCheckoutShippingAddress;
import com.mastercard.mp.switchservices.paymentData.PaymentData;
import com.us.masterpass.merchantapp.BuildConfig;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkInterface;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.model.MasterpassPreCheckoutCardObject;
import com.us.masterpass.merchantapp.domain.model.MasterpassPreCheckoutShippingObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveAllItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.ConfirmTransactionUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.InitializeSdkUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.GetSelectedPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.PaymentMethodCheckoutUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.CartPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.CartListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mastercard.mp.checkout.CheckoutResponseConstants.PAIRING_TRANSACTION_ID;
import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartPresenter implements CartPresenterInterface{

  private static final String TRANSACTION_ID = "TransactionId";
  private final GetItemsOnCartUseCase mGetItemsOnCart;
  private final AddItemUseCase mAddItem;
  private final RemoveItemUseCase mRemoveItem;
  private final RemoveAllItemUseCase mRemoveAllItem;
  private final ConfirmTransactionUseCase mConfirmTransaction;
  private final UseCaseHandler mUseCaseHandler;
  private final IsPaymentMethodEnabledUseCase mIsPaymentMethodEnabledUseCase;
  private final GetSelectedPaymentMethodUseCase mGetSelectedPaymentMethodUseCase;
  private CartListView mCartListView;
  private MasterpassSwitchServices switchServices;

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
   * @param isPaymentMethodEnabled the payment method toggle is enable or not
   * @param paymentMethodUseCase get the selected payment method
   */
  public CartPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull CartListView cartListView,
      @NonNull GetItemsOnCartUseCase getItemsOnCart, @NonNull AddItemUseCase addItem,
      @NonNull RemoveItemUseCase removeItem, @NonNull RemoveAllItemUseCase removeAllItem,
      @NonNull ConfirmTransactionUseCase confirmTransaction,
      @NonNull IsPaymentMethodEnabledUseCase isPaymentMethodEnabled,
      @NonNull GetSelectedPaymentMethodUseCase paymentMethodUseCase) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
    mCartListView = checkNotNull(cartListView, "itemsView cannot be null!");
    mGetItemsOnCart = checkNotNull(getItemsOnCart, "Get item use case must exist");
    mAddItem = checkNotNull(addItem, "Add item use case must exist");
    mRemoveItem = checkNotNull(removeItem, "Must not be null");
    mRemoveAllItem = checkNotNull(removeAllItem, "Must not be null");
    mConfirmTransaction = checkNotNull(confirmTransaction, "Mastercard use case");
    mIsPaymentMethodEnabledUseCase = checkNotNull(isPaymentMethodEnabled, "Must not be null");
    mGetSelectedPaymentMethodUseCase = checkNotNull(paymentMethodUseCase, "Must not be null");
    mCartListView.setPresenter(this);
    switchServices = new MasterpassSwitchServices(BuildConfig.CLIENT_ID);
  }

  @Override public void start() {
    loadItemsOnCart(false);
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void result(int requestCode, int resultCode) {

  }

  @Override public void loadItemsOnCart(boolean forceUpdate) {
    mUseCaseHandler.execute(mGetItemsOnCart, new GetItemsOnCartUseCase.RequestValues(),
        new GetItemsOnCartUseCase.UseCaseCallback<GetItemsOnCartUseCase.ResponseValue>() {
          @Override public void onSuccess(GetItemsOnCartUseCase.ResponseValue response) {
            List<Item> itemsOnCart = response.getNewItemOnCart();
            updateBadge(response.getAddItemCount());
            showItemsOnCart(itemsOnCart);
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
            isSuppressShipping(response.isSuppressShipping());
          }

          @Override public void onError() {
            mCartListView.showError();
          }
        });
  }

  @Override public void addItem(@NonNull Item itemToAdd) {
    checkNotNull(itemToAdd, "completedTask cannot be null!");
    mUseCaseHandler.execute(mAddItem, new AddItemUseCase.RequestValues(itemToAdd),
        new UseCase.UseCaseCallback<AddItemUseCase.ResponseValue>() {
          @Override public void onSuccess(AddItemUseCase.ResponseValue response) {
            updateBadge(response.getAddItemCount());
            showItemsOnCart(response.getNewItemOnCart());
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
          }

          @Override public void onError() {
            mCartListView.showError();
          }
        });
  }

  @Override public void showItemsOnCart(List<Item> itemsOnCart) {
    mCartListView.showItems(itemsOnCart);
  }

  @Override public void enableWebCheckout(boolean isPairingEnabled) {
    MasterpassMerchant.pairing(isPairingEnabled, MasterpassSdkCoordinator.getInstance());
  }

  @Override public void removeItem(@NonNull Item itemToRemove) {
    checkNotNull(itemToRemove, "completedTask cannot be null!");
    mUseCaseHandler.execute(mRemoveItem, new RemoveItemUseCase.RequestValues(itemToRemove),
        new UseCase.UseCaseCallback<RemoveItemUseCase.ResponseValue>() {
          @Override public void onSuccess(RemoveItemUseCase.ResponseValue response) {
            updateBadge(response.getAddItemCount());
            showItemsOnCart(response.getNewItemOnCart());
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
          }

          @Override public void onError() {
            backToItemList();
          }
        });
  }

  @Override public void removeAllItem(@NonNull Item itemRemoveAll) {
    checkNotNull(itemRemoveAll, "completedTask cannot be null!");
    mUseCaseHandler.execute(mRemoveAllItem, new RemoveAllItemUseCase.RequestValues(itemRemoveAll),
        new UseCase.UseCaseCallback<RemoveAllItemUseCase.ResponseValue>() {
          @Override public void onSuccess(RemoveAllItemUseCase.ResponseValue response) {
            updateBadge(response.getAddItemCount());
            showItemsOnCart(response.getNewItemOnCart());
            totalPrice(response.getTotalPrice());
            subtotalPrice(response.getSubTotalPrice());
            taxPrice(response.getTax());
          }

          @Override public void onError() {
            backToItemList();
          }
        });
  }

  @Override public void showBadge() {

  }

  @Override public void updateBadge(String totalCartCount) {
    mCartListView.updateBadge(totalCartCount);
  }

  @Override public void totalPrice(String totalPrice) {
    mCartListView.totalPrice(totalPrice);
  }

  @Override public void subtotalPrice(String subtotalPrice) {
    mCartListView.subtotalPrice(subtotalPrice);
  }

  @Override public void taxPrice(String taxPrice) {
    mCartListView.taxPrice(taxPrice);
  }

  @Override public void backToItemList() {
    mCartListView.backToItemList();
  }

  @Override
  public void initializeMasterpassMerchant(MasterpassMerchantConfiguration configuration) {
    InitializeSdkUseCase.initializeSdk(configuration,
        new MasterpassSdkInterface.GetFromMasterpassSdk() {
          @Override public void sdkResponseSuccess() {
            mCartListView.masterpassSdkInitialized();
          }

          @Override public void sdkResponseError(String error) {
          }
        });
  }

  @Override public void getPairingId() {
    if (MasterpassSdkCoordinator.getPairingId() == null) {
      MasterpassSwitchServices switchServices = new MasterpassSwitchServices(BuildConfig.CLIENT_ID);
      switchServices.pairingId(MasterpassSdkCoordinator.getPairingTransactionId(),
          MasterpassSdkCoordinator.getUserId(), BuildConfig.ENVIRONMENT.toUpperCase(),
          MasterpassSdkCoordinator.getPublicKey(), new HttpCallback<PairingIdResponse>() {
            @Override public void onResponse(PairingIdResponse response) {
              MasterpassSdkCoordinator.savePairingId(response.getPairingId());
              getPreCheckoutData();
            }

            @Override public void onError(ServiceError error) {
              mCartListView.hideProgress();
              mCartListView.showError();
            }
          });
    } else {
      getPreCheckoutData();
    }
  }

  @Override public void initializeMasterpassMerchant(Context context) {
    InitializeSdkUseCase.initializeSdk(context, new MasterpassSdkInterface.GetFromMasterpassSdk() {
      @Override public void sdkResponseSuccess() {
        mCartListView.masterpassSdkInitialized();
      }

      @Override public void sdkResponseError(String error) {
      }
    });
  }

  @Override public void showMasterpassButton() {
    InitializeSdkUseCase.getSdkButton(new MasterpassSdkInterface.GetFromMasterpassSdkButton() {
      @Override public void sdkResponseSuccess(MasterpassButton masterpassButton) {
        mCartListView.showMasterpassButton(masterpassButton);
      }

      @Override public void sdkResponseError() {
        mCartListView.hideMasterpassButton();
      }
    });
  }

  @Override public void loadConfirmation(HashMap<String, Object> checkoutData,
      final boolean expressCheckoutEnable) {
    //Store pairing transaction id in shared shared preference
    if ((checkoutData.get(PAIRING_TRANSACTION_ID) != null)) {
      MasterpassSdkCoordinator.savePairingTransactionId(
          checkoutData.get(PAIRING_TRANSACTION_ID).toString());
    }

    switchServices.paymentData(checkoutData.get(TRANSACTION_ID).toString(), BuildConfig.CHECKOUT_ID,
        MasterpassSdkCoordinator.getGeneratedCartId(), BuildConfig.ENVIRONMENT.toUpperCase(),
        MasterpassSdkCoordinator.getPublicKey(), new HttpCallback<PaymentData>() {
          @Override public void onResponse(PaymentData response) {
            if (expressCheckoutEnable) {
              mCartListView.showConfirmationPairingScreen(getPaymentCardData(response));
            } else {
              mCartListView.showConfirmationScreen(getPaymentCardData(response));
            }
          }

          @Override public void onError(ServiceError error) {
            mCartListView.hideProgress();
            mCartListView.showError();
          }
        });
  }

  @Override public void getPreCheckoutData() {
    MasterpassSwitchServices switchServices = new MasterpassSwitchServices(BuildConfig.CLIENT_ID);
    switchServices.precheckoutData(MasterpassSdkCoordinator.getPairingId(),
        BuildConfig.ENVIRONMENT.toUpperCase(), MasterpassSdkCoordinator.getPublicKey(),
        new HttpCallback<PreCheckoutData>() {
          @Override public void onResponse(PreCheckoutData response) {
            mCartListView.hideProgress();
            mCartListView.showConfirmationPairingScreen(getPreCheckoutData(response));
          }

          @Override public void onError(ServiceError error) {
            mCartListView.hideProgress();
            mCartListView.showError();
          }
        });
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
    mCartListView.isSuppressShipping(suppressShipping);
  }

  @Override public void isPaymentMethodEnabled() {
    mUseCaseHandler.execute(mIsPaymentMethodEnabledUseCase,
        new IsPaymentMethodEnabledUseCase.RequestValues(),
        new UseCase.UseCaseCallback<IsPaymentMethodEnabledUseCase.ResponseValue>() {
          @Override public void onSuccess(IsPaymentMethodEnabledUseCase.ResponseValue response) {
            mCartListView.setPaymentMethodVisibility(response.isPaymentMethodEnabled());
          }

          @Override public void onError() {
            mCartListView.showError();
          }
        });
  }

  @Override public void getSelectedPaymentMethod() {
    mUseCaseHandler.execute(mGetSelectedPaymentMethodUseCase,
        new GetSelectedPaymentMethodUseCase.RequestValues(),
        new UseCase.UseCaseCallback<GetSelectedPaymentMethodUseCase.ResponseValue>() {
          @Override public void onSuccess(GetSelectedPaymentMethodUseCase.ResponseValue response) {
            if (response.getSelectedPaymentMethod() == null) {
              mCartListView.setPaymentMethod(null, null, null);
            } else {
              mCartListView.setPaymentMethod(
                  response.getSelectedPaymentMethod().getPaymentMethodLogo(),
                  response.getSelectedPaymentMethod().getPaymentMethodName(),
                  response.getSelectedPaymentMethod().getPaymentMethodLastFourDigits());
            }
          }

          @Override public void onError() {
            mCartListView.showError();
          }
        });
  }

  @Override public void selectedPaymentMethodCheckout(String paymentMethodName) {

    PaymentMethodCheckoutUseCase.completeCheckout(paymentMethodName,
        new MasterpassSdkInterface.GetFromMasterpassSdk() {
          @Override public void sdkResponseSuccess() {
            //no need for this yet
          }

          @Override public void sdkResponseError(String error) {
            mCartListView.showError(error);
          }
        });
  }

  private MasterpassConfirmationObject getPaymentCardData(PaymentData paymentData) {
    MasterpassConfirmationObject masterpassConfirmationObject = new MasterpassConfirmationObject();
    masterpassConfirmationObject.setCardAccountNumber(paymentData.getCard().getAccountNumber());
    masterpassConfirmationObject.setCardAccountNumberHidden(
        getMaskedCardNumber(paymentData.getCard().getAccountNumber()));
    masterpassConfirmationObject.setCardBrandId(paymentData.getCard().getBrandId());
    masterpassConfirmationObject.setCardBrandName(paymentData.getCard().getBrandName());
    masterpassConfirmationObject.setCardHolderName(paymentData.getCard().getCardHolderName());
    masterpassConfirmationObject.setShippingLine1(validateEmptyString
        (paymentData.getShippingAddress()!= null ? paymentData.getShippingAddress().getLine1():""));
    masterpassConfirmationObject.setShippingLine2(validateEmptyString
        (paymentData.getShippingAddress()!= null ? paymentData.getShippingAddress().getLine2():""));
    masterpassConfirmationObject.setShippingCity(validateEmptyString
        (paymentData.getShippingAddress()!= null ? paymentData.getShippingAddress().getCity():""));
    masterpassConfirmationObject.setShippingSubDivision(validateEmptyString(
        paymentData.getShippingAddress()!= null ? paymentData.getShippingAddress().getSubdivision():""));
    masterpassConfirmationObject.setPostalCode(validateEmptyString
        (paymentData.getShippingAddress()!= null ? paymentData.getShippingAddress().getPostalCode():""));
    masterpassConfirmationObject.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());

    return masterpassConfirmationObject;
  }

  private MasterpassConfirmationObject getPreCheckoutData(PreCheckoutData preCheckoutData) {
    MasterpassConfirmationObject masterpassConfirmationObject = new MasterpassConfirmationObject();
    MasterpassSdkCoordinator.savePairingId(preCheckoutData.getPairingId());
    masterpassConfirmationObject.setListMasterpassPreCheckoutCardObject(
        getCardObjects(preCheckoutData.getCards()));
    masterpassConfirmationObject.setListPreCheckoutShipping(
        getShippingAddressObjects(preCheckoutData.getShippingAddresses()));
    masterpassConfirmationObject.setCartId(MasterpassSdkCoordinator.getGeneratedCartId());
    masterpassConfirmationObject.setPreCheckoutTransactionId(
        preCheckoutData.getPreCheckoutTransactionId());
    return masterpassConfirmationObject;
  }

  private List<MasterpassPreCheckoutCardObject> getCardObjects(List<PreCheckoutCard> listCard) {

    List<MasterpassPreCheckoutCardObject> masterpassPreCheckoutCardObjectList = new ArrayList<>();
    for (int i = 0; i < listCard.size(); i++) {
      MasterpassPreCheckoutCardObject masterpassPreCheckoutCardObject =
          new MasterpassPreCheckoutCardObject();
      masterpassPreCheckoutCardObject.setPreBrandName(listCard.get(i).getBrandName());
      masterpassPreCheckoutCardObject.setPreCardHolderName(listCard.get(i).getCardHolderName());
      masterpassPreCheckoutCardObject.setPreCardId(listCard.get(i).getCardId());
      masterpassPreCheckoutCardObject.setPreExpiryMonth(listCard.get(i).getExpiryMonth());
      masterpassPreCheckoutCardObject.setPreExpiryYear(listCard.get(i).getExpiryYear());
      masterpassPreCheckoutCardObject.setPreLastFour(listCard.get(i).getLastFour());
      masterpassPreCheckoutCardObjectList.add(masterpassPreCheckoutCardObject);
    }
    return masterpassPreCheckoutCardObjectList;
  }

  private List<MasterpassPreCheckoutShippingObject> getShippingAddressObjects(
      List<PreCheckoutShippingAddress> shippingAddresses) {
    List<MasterpassPreCheckoutShippingObject> masterpassPreCheckoutShippingObjectList = new ArrayList<>();
    if(shippingAddresses != null) {
      for (int i = 0; i < shippingAddresses.size(); i++) {
        MasterpassPreCheckoutShippingObject masterpassPreCheckoutShippingObject = new MasterpassPreCheckoutShippingObject();
        masterpassPreCheckoutShippingObject.setPreCountry(shippingAddresses.get(i).getCountry());
        masterpassPreCheckoutShippingObject.setPreSubdivision(shippingAddresses.get(i).getSubdivision());
        masterpassPreCheckoutShippingObject.setPreAddressId(shippingAddresses.get(i).getAddressId());
        masterpassPreCheckoutShippingObject.setPreLine1(validateEmptyString(shippingAddresses.get
            (i).getLine1()));
        masterpassPreCheckoutShippingObject.setPreLine2(validateEmptyString(shippingAddresses.get
            (i).getLine2()));
        masterpassPreCheckoutShippingObject.setPreLine3(validateEmptyString(shippingAddresses.get
            (i).getLine3()));
        masterpassPreCheckoutShippingObject.setPreCity(validateEmptyString(shippingAddresses.get
            (i).getCity()));
        masterpassPreCheckoutShippingObject.setPrePostalCode(validateEmptyString
            (shippingAddresses.get(i).getPostalCode()));
        masterpassPreCheckoutShippingObjectList.add(masterpassPreCheckoutShippingObject);
      }
    }
    return masterpassPreCheckoutShippingObjectList;
  }

  private String getMaskedCardNumber(String cardAccountNumber) {
    StringBuilder bul = new StringBuilder(cardAccountNumber);
    if (cardAccountNumber.length() > 4) {
      int start = 0;
      int end = cardAccountNumber.length() - 4;
      bul.replace(start, end, "**** **** **** ");
    }
    return bul.toString();
  }

  private String validateEmptyString(String value){
    String mValue;
    mValue = value!=null ? value : "";
    return mValue;
  }
}