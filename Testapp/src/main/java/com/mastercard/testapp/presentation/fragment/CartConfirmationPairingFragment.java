package com.mastercard.testapp.presentation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mastercard.mp.switchservices.checkout.ExpressCheckoutRequest;
import com.mastercard.testapp.R;
import com.mastercard.testapp.data.device.CartLocalStorage;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.data.external.EnvironmentConstants;
import com.mastercard.testapp.domain.Constants;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.presentation.AddFragmentToActivity;
import com.mastercard.testapp.presentation.PresentationConstants;
import com.mastercard.testapp.presentation.adapter.CartConfirmationAdapter;
import com.mastercard.testapp.presentation.adapter.CartConfirmationPairingAdapter;
import com.mastercard.testapp.presentation.adapter.CartConfirmationPairingAddressAdapter;
import com.mastercard.testapp.presentation.presenter.CartConfirmationPresenter;
import com.mastercard.testapp.presentation.view.CartConfirmationListView;
import com.mastercard.testapp.presentation.view.MerchantCheckoutView;
import java.util.ArrayList;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 *
 * Confirmation on pairing screen
 */
public class CartConfirmationPairingFragment extends Fragment
    implements CartConfirmationListView, ViewPager.OnPageChangeListener, MerchantCheckoutView {

  /**
   * The Viewpage dots ll.
   */
  LinearLayout viewpageDotsLl;
  /**
   * The Viewpage address dots ll.
   */
  LinearLayout viewpageAddressDotsLl;
  private CartConfirmationAdapter mAdapter;
  private CartConfirmationPresenter mPresenter;
  private TextView mExpressCheckout;
  private ViewPager mViewPager;
  private ViewPager mViewPagerAddress;
  private int cardVPDotsCount;
  private int addressVPDotsCount;
  private ImageView[] cardVPDots;
  private ImageView[] addressVPDots;
  private TextView mCardSelected;

  /**
   * Instantiates a new Cart confirmation pairing fragment.
   */
  public CartConfirmationPairingFragment() {
  }

  /**
   * New instance cart confirmation pairing fragment.
   *
   * @return the cart confirmation pairing fragment
   */
  public static CartConfirmationPairingFragment newInstance() {
    return new CartConfirmationPairingFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = new CartConfirmationAdapter(getActivity(), new ArrayList<Item>(0));
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.start();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.cart_confirmation_pairing_list_view, container, false);

    Bundle bundle = this.getArguments();
    final MasterpassConfirmationObject masterpassConfirmationObject =
        (MasterpassConfirmationObject) bundle.getSerializable(
            PresentationConstants.KEY_DATA_CONFIRMATION);

    mCardSelected = view.findViewById(R.id.cart_confimation_card_number);
    mExpressCheckout = view.findViewById(R.id.tv_express_checkout_bottom);
    viewpageDotsLl = view.findViewById(R.id.card_dots_vp);
    mViewPager = view.findViewById(R.id.card_confirmation_vp);
    viewpageAddressDotsLl = view.findViewById(R.id.address_dots_vp);
    mViewPagerAddress = view.findViewById(R.id.address_confirmation_vp);

    mExpressCheckout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showProgress();
        mPresenter.expressCheckout(
            getMasterpassExpressCheckoutObject(masterpassConfirmationObject));
      }
    });

    CartConfirmationPairingAdapter cartConfirmationPairingAdapter =
        new CartConfirmationPairingAdapter(getContext(),
            masterpassConfirmationObject.getListMasterpassPreCheckoutCardObject());
    mViewPager.setAdapter(cartConfirmationPairingAdapter);
    cardVPDotsCount = cartConfirmationPairingAdapter.getCount();
    cardVPDots = new ImageView[cardVPDotsCount];
    for (int i = 0; i < cardVPDotsCount; i++) {
      cardVPDots[i] = new ImageView(getContext());
      cardVPDots[i].setImageDrawable(
          ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_inactive));
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
              LinearLayout.LayoutParams.WRAP_CONTENT);
      params.setMargins(8, 0, 8, 0);
      viewpageDotsLl.addView(cardVPDots[i], params);
    }
    cardVPDots[0].setImageDrawable(
        ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_active));
    mCardSelected.setText(masterpassConfirmationObject.getListMasterpassPreCheckoutCardObject()
        .get(0)
        .getPreLastFour());
    masterpassConfirmationObject.setDoCheckoutCardId(
        masterpassConfirmationObject.getListMasterpassPreCheckoutCardObject()
            .get(0)
            .getPreCardId());
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        for (int i = 0; i < cardVPDotsCount; i++) {
          cardVPDots[i].setImageDrawable(
              ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_inactive));
        }
        cardVPDots[position].setImageDrawable(
            ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_active));
        mCardSelected.setText(masterpassConfirmationObject.getListMasterpassPreCheckoutCardObject()
            .get(position)
            .getPreLastFour());
        masterpassConfirmationObject.setDoCheckoutCardId(
            masterpassConfirmationObject.getListMasterpassPreCheckoutCardObject()
                .get(position)
                .getPreCardId());
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });

    if (!masterpassConfirmationObject.getListPreCheckoutShipping().isEmpty()) {
      CartConfirmationPairingAddressAdapter cartConfirmationPairingAddressAdapter =
          new CartConfirmationPairingAddressAdapter(getContext(),
              masterpassConfirmationObject.getListPreCheckoutShipping());
      mViewPagerAddress.setAdapter(cartConfirmationPairingAddressAdapter);
      addressVPDotsCount = cartConfirmationPairingAddressAdapter.getCount();
      addressVPDots = new ImageView[addressVPDotsCount];
      for (int i = 0; i < addressVPDotsCount; i++) {
        addressVPDots[i] = new ImageView(getContext());
        addressVPDots[i].setImageDrawable(
            ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_inactive));
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 0, 8, 0);
        viewpageAddressDotsLl.addView(addressVPDots[i], params);
      }
      addressVPDots[0].setImageDrawable(
          ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_active));
      masterpassConfirmationObject.setDoCheckoutShippingAddressId(
          masterpassConfirmationObject.getListPreCheckoutShipping().get(0).getPreAddressId());
      mViewPagerAddress.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override public void onPageSelected(int position) {
          for (int i = 0; i < addressVPDotsCount - 1; i++) {
            addressVPDots[i].setImageDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_inactive));
          }
          addressVPDots[position].setImageDrawable(
              ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_active));
          masterpassConfirmationObject.setDoCheckoutShippingAddressId(
              masterpassConfirmationObject.getListPreCheckoutShipping()
                  .get(position)
                  .getPreAddressId());
        }

        @Override public void onPageScrollStateChanged(int state) {

        }
      });

      if (bundle.getBoolean(PresentationConstants.KEY_DATA_SUPRESS_SHIPPING)) {
        mViewPagerAddress.setVisibility(View.GONE);
        viewpageAddressDotsLl.setVisibility(View.GONE);
      } else {
        mViewPagerAddress.setVisibility(View.VISIBLE);
        viewpageAddressDotsLl.setVisibility(View.VISIBLE);
      }
    }
    return view;
  }

  @Override public void showItems(List<Item> items) {
    mAdapter.replaceData(items);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
  }

  @Override public void totalPrice(String totalPrice) {
  }

  @Override public void subtotalPrice(String subtotalPrice) {
  }

  @Override public void taxPrice(String taxPrice) {
  }

  @Override
  public void showCompleteScreen(MasterpassConfirmationObject masterpassConfirmationObject) {

    hideProgress();
    FragmentManager fm = getActivity().getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    Bundle bundle = new Bundle();
    bundle.putSerializable(PresentationConstants.KEY_DATA_CONFIRMATION,
        masterpassConfirmationObject);
    CartConfirmationFragment cartConfirmationFragment = new CartConfirmationFragment();
    cartConfirmationFragment.setArguments(bundle);

    AddFragmentToActivity.fragmentForFragment(getActivity().getSupportFragmentManager(),
        cartConfirmationFragment, R.id.main_container);
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
  }

  @Override public void expressCheckout(MasterpassConfirmationObject masterpassConfirmationObject) {

  }

  @Override public void onPause() {
    super.onPause();
  }

  @Override public void setPresenter(CartConfirmationPresenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {

  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  ExpressCheckoutRequest getMasterpassExpressCheckoutObject(
      MasterpassConfirmationObject masterpassConfirmationObject) {
    boolean isShippingEnabled = masterpassConfirmationObject.getListPreCheckoutShipping().size() > 0
        && mViewPagerAddress.getVisibility() == View.VISIBLE;
    return new ExpressCheckoutRequest.Builder().setAmount(
        CartLocalStorage.getInstance(getContext()).getCartTotal(Constants.LOCAL_CART_DATASOURCE))
        .setCardId(masterpassConfirmationObject.getDoCheckoutCardId())
        .setCheckoutId(EnvironmentConstants.CHECKOUT_ID)
        .setCurrency(SettingsSaveConfigurationSdk.getInstance(getContext()).getCurrencySelected())
        //.setDigitalGoods(SettingsSaveConfigurationSdk.getInstance(getContext())
        //.getSupressShipping())
        .setDigitalGoods(isShippingEnabled ? false : true)
        .setPairingId(MasterpassSdkCoordinator.getPairingId())
        .setPreCheckoutTransactionId(masterpassConfirmationObject.getPreCheckoutTransactionId())
        .setShippingAddressId(
            isShippingEnabled ? masterpassConfirmationObject.getListPreCheckoutShipping()
                .get(mViewPagerAddress.getCurrentItem())
                .getPreAddressId() : null)
        //.setShippingAddressId(masterpassConfirmationObject.getDoCheckoutShippingAddressId())
        .build();
  }

  @Override public void showError() {
    new BaseAlertDialog.Builder(getContext(), BaseAlertDialog.MEX_ERROR_DIALOG).setDialogTitle(
        getResources().getString(R.string.error))
        .setDialogMessage(getResources().getString(R.string.try_again))
        .setDialogListener(new EmptyDialogInteractionListener())
        .build()
        .show();
  }

  @Override public void showProgress() {
    ProgressDialogFragment.showProgressDialog(getFragmentManager());
  }

  @Override public void hideProgress() {
    ProgressDialogFragment.dismissProgressDialog();
  }
}