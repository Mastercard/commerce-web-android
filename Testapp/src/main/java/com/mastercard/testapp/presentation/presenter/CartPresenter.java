package com.mastercard.testapp.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.mastercard.commerce.CheckoutCallback;
import com.mastercard.commerce.CheckoutRequest;
import com.mastercard.commerce.CryptoOptions;
import com.mastercard.commerce.Mastercard;
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
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.data.device.SettingsSaveConstants;
import com.mastercard.testapp.data.external.EnvironmentSettings;
import com.mastercard.testapp.data.pojo.EnvironmentConfiguration;
import com.mastercard.testapp.domain.SettingsListOptions;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkInterface;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.domain.model.MasterpassPreCheckoutCardObject;
import com.mastercard.testapp.domain.model.MasterpassPreCheckoutShippingObject;
import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.items.AddItemUseCase;
import com.mastercard.testapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.mastercard.testapp.domain.usecase.items.RemoveAllItemUseCase;
import com.mastercard.testapp.domain.usecase.items.RemoveItemUseCase;
import com.mastercard.testapp.domain.usecase.masterpass.ConfirmTransactionUseCase;
import com.mastercard.testapp.domain.usecase.masterpass.InitializeSdkUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.GetSelectedPaymentMethodUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.PaymentMethodCheckoutUseCase;
import com.mastercard.testapp.presentation.SettingsConstants;
import com.mastercard.testapp.presentation.presenter.base.CartPresenterInterface;
import com.mastercard.testapp.presentation.view.CartListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mastercard.commerce.CommerceWebSdk.COMMERCE_TRANSACTION_ID;
import static com.mastercard.mp.checkout.CheckoutResponseConstants.PAIRING_TRANSACTION_ID;
import static com.mastercard.testapp.domain.SettingsListOptions.getCurrenyCode;
import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartPresenter implements CartPresenterInterface {

  private static final String TAG = CartPresenter.class.getSimpleName();
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

  private double amount;

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
    switchServices = new MasterpassSwitchServices(
        EnvironmentSettings.getCurrentEnvironmentConfiguration().getClientId());
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
            amount = response.getAmount();

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
            amount = response.getAmount();
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

  @Override public void getPairingId(final Context context) {
    EnvironmentConfiguration envConfig = EnvironmentSettings.getCurrentEnvironmentConfiguration();
    if ((MasterpassSdkCoordinator.getPairingId() == null || MasterpassSdkCoordinator.getPairingId()
        .isEmpty()) && (MasterpassSdkCoordinator.getPairingTransactionId() != null
        && !MasterpassSdkCoordinator.getPairingTransactionId().isEmpty())) {
      MasterpassSwitchServices switchServices =
          new MasterpassSwitchServices(envConfig.getClientId());
      mCartListView.showProgress();
      switchServices.pairingId(MasterpassSdkCoordinator.getPairingTransactionId(),
          MasterpassSdkCoordinator.getUserId(), envConfig.getName().toUpperCase(),
          MasterpassSdkCoordinator.getPublicKey(context), new HttpCallback<PairingIdResponse>() {
            @Override public void onResponse(PairingIdResponse response) {
              MasterpassSdkCoordinator.savePairingId(response.getPairingId());
              mCartListView.hideProgress();
              getPreCheckoutData(context);
            }

            @Override public void onError(ServiceError error) {
              mCartListView.hideProgress();
              mCartListView.showError();
            }
          });
    } else if (MasterpassSdkCoordinator.getPairingId() != null
        && !MasterpassSdkCoordinator.getPairingId().isEmpty()) {
      getPreCheckoutData(context);
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

  @Override public void showMasterpassButton(Context context) {

    boolean usingV7 = SettingsSaveConfigurationSdk.getInstance(context).getUsingOldApi();

    if (usingV7) {
      InitializeSdkUseCase.getSdkButton(new MasterpassSdkInterface.GetFromMasterpassSdkButton() {
        @Override public void sdkResponseSuccess(MasterpassButton masterpassButton) {
          mCartListView.showMasterpassButton(masterpassButton);
        }

        @Override public void sdkResponseError() {
          mCartListView.hideMasterpassButton();
        }
      });
    } else {
      mCartListView.showSRCButton(InitializeSdkUseCase.getSrcButton(new CheckoutCallback() {
        @Override public void getCheckoutRequest(CheckoutRequestListener listener) {
          listener.setRequest(getSrcCheckoutRequest());
        }
      }));
    }
  }

  private CheckoutRequest getSrcCheckoutRequest() {
    SettingsSaveConfigurationSdk settingsSaveConfigurationSdk =
        SettingsSaveConfigurationSdk.getInstance(mCartListView.getContext());

    boolean isShippingSuppress =
        settingsSaveConfigurationSdk
            .configSwitch(SettingsSaveConstants.SDK_CONFIG_SUPRESS);

    List<SettingsVO> cryptoOptions = SettingsListOptions.settingsDetail(
        SettingsConstants.ITEM_DSRP, settingsSaveConfigurationSdk);

    return new CheckoutRequest.Builder().amount(amount)
        .cartId(MasterpassSdkCoordinator.getGeneratedCartId())
        .currency(getCurrenyCode(mCartListView.getContext()))
        .cryptoOptions(getCryptoOptionsFromSettingsList(cryptoOptions))
        .suppressShippingAddress(isShippingSuppress)
        .build();
  }

  private Set<CryptoOptions> getCryptoOptionsFromSettingsList(List<SettingsVO> cryptoOptions) {
    Set<CryptoOptions> cryptoOptionsSet = new HashSet<>();
    Set<Mastercard.MastercardFormat> mastercardFormatSet = new HashSet<>();

    for (SettingsVO settings : cryptoOptions) {
      if (settings.getName().equals(Mastercard.MastercardFormat.ICC.toString())
          && settings.isSelected()) {
        mastercardFormatSet.add(Mastercard.MastercardFormat.ICC);
      }
      if (settings.getName().equals(Mastercard.MastercardFormat.UCAF.toString())
          && settings.isSelected()) {
        mastercardFormatSet.add(Mastercard.MastercardFormat.UCAF);
      }
    }

    CryptoOptions mastercard = new Mastercard(mastercardFormatSet);
    cryptoOptionsSet.add(mastercard);
    return cryptoOptionsSet;
  }

  @Override public void loadConfirmation(HashMap<String, Object> checkoutData,
      final boolean expressCheckoutEnable, Context context) {
    EnvironmentConfiguration envConfig = EnvironmentSettings.getCurrentEnvironmentConfiguration();
    //Store pairing transaction id in shared shared preference
    Log.d(TAG, "before calling getpaymentdata");
    if ((checkoutData.get(PAIRING_TRANSACTION_ID) != null)) {
      MasterpassSdkCoordinator.savePairingTransactionId(
          checkoutData.get(PAIRING_TRANSACTION_ID).toString());
    }
    mCartListView.hideProgress();
    switchServices.paymentData(checkoutData.get(COMMERCE_TRANSACTION_ID).toString(),
        envConfig.getCheckoutId(), MasterpassSdkCoordinator.getGeneratedCartId(),
        envConfig.getName().toUpperCase(), MasterpassSdkCoordinator.getPublicKey(context),
        new HttpCallback<PaymentData>() {
          @Override public void onResponse(PaymentData response) {
            Log.d(TAG, "payment data success response");
            if (expressCheckoutEnable) {
              mCartListView.showConfirmationPairingScreen(getPaymentCardData(response));
            } else {
              mCartListView.showConfirmationScreen(getPaymentCardData(response));
            }
          }

          @Override public void onError(ServiceError error) {
            Log.d("PAYMENTDATA", "payment data error response =" + error.message());
            mCartListView.hideProgress();
            mCartListView.showError();
          }
        });
  }

  @Override public void getPreCheckoutData(Context context) {
    EnvironmentConfiguration envConfig = EnvironmentSettings.getCurrentEnvironmentConfiguration();
    MasterpassSwitchServices switchServices = new MasterpassSwitchServices(envConfig.getClientId());
    mCartListView.showProgress();
    switchServices.precheckoutData(MasterpassSdkCoordinator.getPairingId(),
        envConfig.getName().toUpperCase(), MasterpassSdkCoordinator.getPublicKey(context),
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
    masterpassConfirmationObject.setShippingLine1(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getLine1()
            : ""));
    masterpassConfirmationObject.setShippingLine2(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getLine2()
            : ""));
    masterpassConfirmationObject.setShippingCity(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getCity()
            : ""));
    masterpassConfirmationObject.setShippingSubDivision(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getSubdivision()
            : ""));
    masterpassConfirmationObject.setPostalCode(validateEmptyString(
        paymentData.getShippingAddress() != null ? paymentData.getShippingAddress().getPostalCode()
            : ""));
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
    List<MasterpassPreCheckoutShippingObject> masterpassPreCheckoutShippingObjectList =
        new ArrayList<>();
    if (shippingAddresses != null) {
      for (int i = 0; i < shippingAddresses.size(); i++) {
        MasterpassPreCheckoutShippingObject masterpassPreCheckoutShippingObject =
            new MasterpassPreCheckoutShippingObject();
        masterpassPreCheckoutShippingObject.setPreCountry(shippingAddresses.get(i).getCountry());
        masterpassPreCheckoutShippingObject.setPreSubdivision(
            shippingAddresses.get(i).getSubdivision());
        masterpassPreCheckoutShippingObject.setPreAddressId(
            shippingAddresses.get(i).getAddressId());
        masterpassPreCheckoutShippingObject.setPreLine1(
            validateEmptyString(shippingAddresses.get(i).getLine1()));
        masterpassPreCheckoutShippingObject.setPreLine2(
            validateEmptyString(shippingAddresses.get(i).getLine2()));
        masterpassPreCheckoutShippingObject.setPreLine3(
            validateEmptyString(shippingAddresses.get(i).getLine3()));
        masterpassPreCheckoutShippingObject.setPreCity(
            validateEmptyString(shippingAddresses.get(i).getCity()));
        masterpassPreCheckoutShippingObject.setPrePostalCode(
            validateEmptyString(shippingAddresses.get(i).getPostalCode()));
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

  private String validateEmptyString(String value) {
    String mValue;
    mValue = value != null ? value : "";
    return mValue;
  }
}