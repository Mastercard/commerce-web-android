package com.us.masterpass.merchantapp.presentation.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.mastercard.commerce.CheckoutButton;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.ItemLocalDataSource;
import com.us.masterpass.merchantapp.data.external.ItemExternalDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassUICallback;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.ItemsOnClickInterface;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.CompleteTransactionUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.AnimateUtils;
import com.us.masterpass.merchantapp.presentation.PresentationConstants;
import com.us.masterpass.merchantapp.presentation.activity.CartActivity;
import com.us.masterpass.merchantapp.presentation.adapter.CartAdapter;
import com.us.masterpass.merchantapp.presentation.presenter.CartConfirmationPresenter;
import com.us.masterpass.merchantapp.presentation.presenter.CartPresenter;
import com.us.masterpass.merchantapp.presentation.presenter.base.CartPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.CartListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Fragment that handles the shopping cart screen
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartFragment extends Fragment implements CartListView, MasterpassUICallback {

  private CartAdapter mAdapter;

  private CartPresenterInterface mPresenter;
  private TextView badgeCart;
  private TextView totalPrice;
  private TextView subtotalPrice;
  private TextView taxPrice;
  private ImageView imageCart;
  private LinearLayout llButtonMasterpass;
  private ProgressDialog progressDialog;
  private boolean buttonVisible;
  private LinearLayout llShippingOptions;
  private TextView tvCheckoutBottom;
  private boolean suppressShipping;

  /**
   * Instantiates a new Cart fragment.
   */
  public CartFragment() {
  }

  public static CartFragment newInstance(String transactionId) {
    CartFragment cartFragment = new CartFragment();

    if (transactionId != null) {
      Bundle bundle = new Bundle();
      bundle.putString(CartActivity.TRANSACTION_ID, transactionId);
      cartFragment.setArguments(bundle);
    }

    return cartFragment;
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

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (getArguments() != null) {
      HashMap<String, String> params = new HashMap<>();
      params.put(CartActivity.TRANSACTION_ID, getArguments().getString(CartActivity.TRANSACTION_ID));
      mPresenter.getPaymentData(params, getContext());
    } else {
      mPresenter.initializeMasterpassMerchant(getContext());
    }
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
    llButtonMasterpass = view.findViewById(R.id.ll_bottom_button);
    llShippingOptions = view.findViewById(R.id.ll_ship_content);
    tvCheckoutBottom = view.findViewById(R.id.tv_src_mark);

    tvCheckoutBottom.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mPresenter.loadConfirmation(null);
      }
    });
    ListView listView = view.findViewById(R.id.items_list_view);
    listView.setAdapter(mAdapter);
    badgeCart = view.findViewById(R.id.item_list_cart_badge);

    mPresenter.showBadge();
    mPresenter.showMasterpassButton();

    progressDialog = new ProgressDialog(this.getContext());
    progressDialog.setMessage(getString(R.string.loading));
    showLoadingSpinner(true);
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

  @Override public void showMasterpassButton(CheckoutButton masterpassButton) {
    buttonVisible = true;
    llButtonMasterpass.setVisibility(View.VISIBLE);
    if (masterpassButton != null) {
      tvCheckoutBottom.setVisibility(View.GONE);
      LinearLayout.LayoutParams llParams =
          new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
              LinearLayout.LayoutParams.WRAP_CONTENT);
      llParams.gravity = Gravity.CENTER;
      llButtonMasterpass.addView(masterpassButton, llParams);
    } else {
      tvCheckoutBottom.setVisibility(View.VISIBLE);
    }
  }

  @Override public void showErrorMessage(String errorMessage) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(errorMessage);
    builder.setPositiveButton(R.string.ok, null);
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  @Override public void hideMasterpassButton() {
    llButtonMasterpass.setVisibility(View.INVISIBLE);
  }

  @Override public void masterpassSdkInitialized() {
    mPresenter.showMasterpassButton();
  }

  @Override public void showLoadingSpinner(boolean show) {
    if (show) {
      progressDialog.show();
      progressDialog.setIndeterminate(true);
      progressDialog.setCancelable(false);
    } else {
      progressDialog.dismiss();
    }
  }

  @Override
  public void showConfirmationScreen(MasterpassConfirmationObject masterpassConfirmationObject) {
    showLoadingSpinner(false);

    FragmentManager fm = getActivity().getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    Bundle bundle = new Bundle();
    bundle.putSerializable(PresentationConstants.KEY_DATA_CONFIRMATION,
        masterpassConfirmationObject);
    CartConfirmationFragment cartConfirmationFragment = new CartConfirmationFragment();
    cartConfirmationFragment.setArguments(bundle);

    CartConfirmationPresenter mCartConfirmationPresenter =
        new CartConfirmationPresenter(UseCaseHandler.getInstance(), cartConfirmationFragment,
            new GetItemsOnCartUseCase(
                ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                    ItemLocalDataSource.getInstance(this.getContext()))),
            new CompleteTransactionUseCase(MasterpassExternalDataSource.getInstance(),
                this.getContext()));

    AddFragmentToActivity.fragmentForFragment(getActivity().getSupportFragmentManager(),
        cartConfirmationFragment, R.id.main_container);
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
    this.suppressShipping = suppressShipping;
    if (suppressShipping) {
      llShippingOptions.setVisibility(View.GONE);
    } else {
      llShippingOptions.setVisibility(View.VISIBLE);
    }
  }

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

  /**
   * Animate badge.
   */
  public void animateBadge() {
    final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bouncy);
    AnimateUtils interpolator = new AnimateUtils(0.2, 20);
    animation.setInterpolator(interpolator);
    imageCart.startAnimation(animation);
  }

  @Override public void onPause() {
    super.onPause();
  }

  @Override public void onSDKCheckoutComplete(HashMap<String, Object> parameters) {
    mPresenter.loadConfirmation(parameters);
    showLoadingSpinner(true);
  }

  @Override public void onSDKCheckoutError() {

  }
}