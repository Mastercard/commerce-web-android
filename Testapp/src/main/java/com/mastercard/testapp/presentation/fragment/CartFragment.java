package com.mastercard.testapp.presentation.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mastercard.commerce.CheckoutButton;
import com.mastercard.mp.checkout.MasterpassButton;
import com.mastercard.mp.checkout.MasterpassError;
import com.mastercard.testapp.R;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.masterpass.MasterpassUICallback;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.model.ItemsOnClickInterface;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.paymentMethod.AddPaymentMethodUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.DeletePaymentMethodUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.SaveSelectedPaymentMethodUseCase;
import com.mastercard.testapp.presentation.AddFragmentToActivity;
import com.mastercard.testapp.presentation.AnimateUtils;
import com.mastercard.testapp.presentation.PresentationConstants;
import com.mastercard.testapp.presentation.adapter.CartAdapter;
import com.mastercard.testapp.presentation.presenter.AddPaymentMethodPresenter;
import com.mastercard.testapp.presentation.presenter.CartPresenter;
import com.mastercard.testapp.presentation.presenter.base.CartPresenterInterface;
import com.mastercard.testapp.presentation.view.CartListView;
import com.mastercard.testapp.presentation.view.MerchantCheckoutView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mastercard.mp.checkout.MasterpassError.ERROR_MEX_CHECKOUT_NOT_AVAILABLE;
import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Fragment that handles the shopping cart screen
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartFragment extends Fragment
    implements CartListView, MasterpassUICallback, MerchantCheckoutView {

  private CartAdapter mAdapter;
  private CartPresenterInterface mPresenter;
  private TextView badgeCart;
  private TextView totalPrice;
  private TextView subtotalPrice;
  private TextView taxPrice;
  private ImageView imageCart;
  /**
   * The M items on click interface.
   */
  ItemsOnClickInterface mItemsOnClickInterface = new ItemsOnClickInterface() {
    @Override public void onAddItemsCart(Item addedItem) {
      mPresenter.addItem(addedItem);
      animateBadge();
    }

    @Override public void onRemoveItemsCart(Item removedItem) {
      mPresenter.removeItem(removedItem);
      animateBadge();
    }

    @Override public void onRemoveAllItems(Item removeAllItems) {
      mPresenter.removeAllItem(removeAllItems);
      animateBadge();
    }
  };
  private LinearLayout llButtonMasterpass;
  private LinearLayout llShippingOptions;
  private TextView tvCheckoutBottom;
  private boolean suppressShipping;
  private TextView tvPaymentMethod;
  private AlertDialog.Builder alert;
  private LinearLayout llPaymentMethod;
  private boolean isPaymentMethodEnable;
  private ImageView paymentMethodIcon;
  private String selectedPaymentMethod;

  /**
   * Instantiates a new Cart fragment.
   */
  public CartFragment() {
  }

  /**
   * New instance cart fragment.
   *
   * @return the cart fragment
   */
  public static CartFragment newInstance() {
    return new CartFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = new CartAdapter(getActivity(), new ArrayList<Item>(0), mItemsOnClickInterface);
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.start();
    MasterpassSdkCoordinator.getInstance().addFragmentListener(this);
  }

  @Override public void onDetach() {
    super.onDetach();
    MasterpassSdkCoordinator.getInstance().removeFragmentListener(this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.cart_list_view, container, false);

    imageCart = view.findViewById(R.id.toolbar_image_cart);
    totalPrice = view.findViewById(R.id.cart_total_text);
    subtotalPrice = view.findViewById(R.id.cart_subtotal_text);
    taxPrice = view.findViewById(R.id.cart_tax_text);
    llButtonMasterpass = view.findViewById(R.id.ll_masterpass_button);
    llShippingOptions = view.findViewById(R.id.ll_ship_content);
    llPaymentMethod = view.findViewById(R.id.ll_payment_method_layout);
    tvCheckoutBottom = view.findViewById(R.id.tv_checkout_bottom);
    tvPaymentMethod = view.findViewById(R.id.tv_payment_method);
    tvCheckoutBottom.setVisibility(View.GONE);
    llPaymentMethod.setVisibility(View.GONE);
    paymentMethodIcon = view.findViewById(R.id.iv_payment_method_icon);
    mPresenter.isPaymentMethodEnabled();
    tvCheckoutBottom.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (isPaymentMethodEnable) {
          mPresenter.selectedPaymentMethodCheckout(selectedPaymentMethod);
          /*if ("Payment Method".equalsIgnoreCase(tvPaymentMethod.getText().toString())) {
            alert = new AlertDialog.Builder(getActivity());
            alert.setPositiveButton(getString(R.string.ok), null);
            alert.setMessage(getString(R.string.add_payment_method_error));
            alert.show();
          } else if(MasterpassSdkCoordinator.getPairingTransactionId()!=null ){
              getPairingID();
          }else {
            mPresenter.selectedPaymentMethodCheckout(selectedPaymentMethod);
          }*/
        } else if ((MasterpassSdkCoordinator.getEnableWebCheckoutSelected()) && (
            MasterpassSdkCoordinator.getPairingTransactionId() == null
                || MasterpassSdkCoordinator.getPairingTransactionId().isEmpty())) {
          pairingCall();
        } else {
          getPairingID();
        }
      }
    });

    tvPaymentMethod.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        showPaymentMethodScreen();
      }
    });
    ListView listView = view.findViewById(R.id.items_list_view);
    listView.setAdapter(mAdapter);
    badgeCart = view.findViewById(R.id.item_list_cart_badge);
    mPresenter.showBadge();

    //TODO: This is the point to decide what SDK to load

    mPresenter.initializeMasterpassMerchant(getContext());
    return view;
  }

  @Override public void setPresenter(CartPresenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Override public void showItems(List<Item> items) {
    mAdapter.replaceData(items);
  }

  @Override public void showBadge() {

  }

  @Override public void hideBadge() {

  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    mPresenter.result(requestCode, resultCode);
  }

  @Override public void updateBadge(String totalCartCount) {
    badgeCart.setVisibility(View.VISIBLE);
    badgeCart.setText(totalCartCount);
    imageCart.setImageResource(R.drawable.shopping_cart_nav_bar_icon);
  }

  @Override public void totalPrice(String totalPrice) {
    this.totalPrice.setText(totalPrice);
  }

  @Override public void subtotalPrice(String subtotalPrice) {
    this.subtotalPrice.setText(subtotalPrice);
  }

  @Override public void taxPrice(String taxPrice) {
    this.taxPrice.setText(taxPrice);
  }

  @Override public void backToItemList() {
    this.getActivity().finish();
  }

  @Override public void showMasterpassButton(MasterpassButton masterpassButton) {
    llButtonMasterpass.setVisibility(View.VISIBLE);
    if (masterpassButton != null) {
      tvCheckoutBottom.setVisibility(View.GONE);
      LinearLayout.LayoutParams llParams =
          new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.MATCH_PARENT);
      llParams.gravity = Gravity.CENTER;
      llButtonMasterpass.addView(masterpassButton, llParams);
    } else {
      tvCheckoutBottom.setVisibility(View.VISIBLE);
    }
  }

  @Override public void showSRCButton(CheckoutButton checkoutButton) {
    llButtonMasterpass.setVisibility(View.VISIBLE);
    if (checkoutButton != null) {
      tvCheckoutBottom.setVisibility(View.GONE);
      LinearLayout.LayoutParams llParams =
          new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.MATCH_PARENT);
      llParams.gravity = Gravity.CENTER;
      llButtonMasterpass.addView(checkoutButton, llParams);
    } else {
      tvCheckoutBottom.setVisibility(View.VISIBLE);
    }
  }

  @Override public void hideMasterpassButton() {
    llButtonMasterpass.setVisibility(View.INVISIBLE);
  }

  @Override public void masterpassSdkInitialized() {
    if (!isPaymentMethodEnable
        && (MasterpassSdkCoordinator.getPairingTransactionId() == null)
        && !MasterpassSdkCoordinator.getEnableWebCheckoutSelected()) {
      mPresenter.showMasterpassButton(getContext());
    } else if ((MasterpassSdkCoordinator.getPairingTransactionId() != null
        || MasterpassSdkCoordinator.getEnableWebCheckoutSelected()) || isPaymentMethodEnable) {
      tvCheckoutBottom.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void showConfirmationScreen(MasterpassConfirmationObject masterpassConfirmationObject) {
    hideProgress();
    Bundle bundle = new Bundle();
    bundle.putSerializable(PresentationConstants.KEY_DATA_CONFIRMATION,
        masterpassConfirmationObject);
    CartConfirmationFragment cartConfirmationFragment = new CartConfirmationFragment();
    cartConfirmationFragment.setArguments(bundle);

    AddFragmentToActivity.fragmentForFragment(getActivity().getSupportFragmentManager(),
        cartConfirmationFragment, R.id.main_container);
  }

  @Override public void showConfirmationPairingScreen(
      MasterpassConfirmationObject masterpassConfirmationObject) {
    hideProgress();
    Bundle bundle = new Bundle();
    bundle.putSerializable(PresentationConstants.KEY_DATA_CONFIRMATION,
        masterpassConfirmationObject);
    bundle.putBoolean(PresentationConstants.KEY_DATA_SUPRESS_SHIPPING, suppressShipping);
    CartConfirmationPairingFragment cartConfirmationPairingFragment =
        new CartConfirmationPairingFragment();
    cartConfirmationPairingFragment.setArguments(bundle);

    AddFragmentToActivity.fragmentForFragment(getActivity().getSupportFragmentManager(),
        cartConfirmationPairingFragment, R.id.main_container);
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
    this.suppressShipping = suppressShipping;
    if (suppressShipping) {
      llShippingOptions.setVisibility(View.GONE);
    } else {
      llShippingOptions.setVisibility(View.VISIBLE);
    }
  }

  @Override public void setPaymentMethodVisibility(boolean paymentMethodEnable) {
    isPaymentMethodEnable = paymentMethodEnable;
    if (isPaymentMethodEnable) {
      mPresenter.getSelectedPaymentMethod();
      llButtonMasterpass.setVisibility(View.GONE);
      llPaymentMethod.setVisibility(View.VISIBLE);
      doVisibleCheckoutButton();
    } else if (MasterpassSdkCoordinator.getPairingTransactionId() != null
        || MasterpassSdkCoordinator.getEnableWebCheckoutSelected()) {
      llButtonMasterpass.setVisibility(View.GONE);
      doVisibleCheckoutButton();
    } else {
      llPaymentMethod.setVisibility(View.GONE);
      tvPaymentMethod.setVisibility(View.GONE);
    }
  }

  @Override public void showPaymentMethodScreen() {

    AddPaymentMethodFragment addPaymentMethodFragment = AddPaymentMethodFragment.newInstance();
    new AddPaymentMethodPresenter(UseCaseHandler.getInstance(), addPaymentMethodFragment,
        new AddPaymentMethodUseCase(), new DeletePaymentMethodUseCase(),
        new SaveSelectedPaymentMethodUseCase(MasterpassSdkCoordinator.getInstance()));

    AddFragmentToActivity.replaceFragmentWithBackStack(getActivity().getSupportFragmentManager(),
        addPaymentMethodFragment, R.id.main_container);
  }

  @Override public void setPaymentMethod(byte[] paymentMethodLogo, String paymentMethod,
      String paymentMethodLastFourDigits) {
    if (paymentMethod != null) {
      selectedPaymentMethod = paymentMethod;
      String paymentMethodName = paymentMethod + " " + paymentMethodLastFourDigits;
      tvPaymentMethod.setText(paymentMethodName);
      if (paymentMethodLogo != null) {
        Bitmap logo = BitmapFactory.decodeByteArray(paymentMethodLogo, 0, paymentMethodLogo.length);
        paymentMethodIcon.setImageBitmap(logo);
        paymentMethodIcon.setVisibility(View.VISIBLE);
      }
    }
  }

  @Override public void showError(String error) {
    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
  }

  @Override public void showError() {
    new BaseAlertDialog.Builder(getContext(), BaseAlertDialog.MEX_ERROR_DIALOG).setDialogTitle(
        getResources().getString(R.string.error))
        .setDialogMessage(getResources().getString(R.string.try_again))
        .setDialogListener(new EmptyDialogInteractionListener())
        .build()
        .show();
  }

  /**
   * Animate badge.
   */
  public void animateBadge() {
    final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bouncy);
    AnimateUtils interpolator = new AnimateUtils(0.2, 20);
    animation.setInterpolator(interpolator);
    imageCart.startAnimation(animation);
  }

  @Override public void onSDKCheckoutComplete(HashMap<String, Object> parameters) {
    showProgress();
    mPresenter.loadConfirmation(parameters, false);
  }

  @Override public void onSDKCheckoutError(MasterpassError masterpassError) {
    if (masterpassError.code() == ERROR_MEX_CHECKOUT_NOT_AVAILABLE) {
      new BaseAlertDialog.Builder(getContext(), BaseAlertDialog.MEX_ERROR_DIALOG).setDialogTitle(
          getResources().getString(R.string.went_wrong_error))
          .setDialogMessage(getResources().getString(R.string.feature_not_available))
          .setDialogListener(new EmptyDialogInteractionListener())
          .build()
          .show();
    }
  }

  @Override public void showProgress() {
    ProgressDialogFragment.showProgressDialog(getFragmentManager());
  }

  @Override public void hideProgress() {
    ProgressDialogFragment.dismissProgressDialog();
  }

  private void getPairingID() {
    mPresenter.getPairingId();
  }

  private void pairingCall() {
    try {
      mPresenter.enableWebCheckout(MasterpassSdkCoordinator.getEnableWebCheckoutSelected());
    } catch (IllegalStateException e) {
      Toast.makeText(getActivity(), R.string.sdk_reinitialize, Toast.LENGTH_LONG).show();
    }
  }

  private void doVisibleCheckoutButton() {
    if (MasterpassSdkCoordinator.isSDKInitialized()) {
      tvCheckoutBottom.setVisibility(View.VISIBLE);
    }
  }
}