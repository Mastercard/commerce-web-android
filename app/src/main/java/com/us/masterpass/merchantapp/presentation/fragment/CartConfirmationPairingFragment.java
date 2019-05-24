package com.us.masterpass.merchantapp.presentation.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.ItemLocalDataSource;
import com.us.masterpass.merchantapp.data.external.ItemExternalDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.CompleteTransactionUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.ConfirmExpressTransactionUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.PresentationConstants;
import com.us.masterpass.merchantapp.presentation.adapter.CartConfirmationAdapter;
import com.us.masterpass.merchantapp.presentation.adapter.CartConfirmationPairingAdapter;
import com.us.masterpass.merchantapp.presentation.adapter.CartConfirmationPairingAddressAdapter;
import com.us.masterpass.merchantapp.presentation.presenter.CartConfirmationPresenter;
import com.us.masterpass.merchantapp.presentation.view.CartConfirmationListView;
import java.util.ArrayList;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartConfirmationPairingFragment extends Fragment
    implements CartConfirmationListView, ViewPager.OnPageChangeListener {

  private CartConfirmationAdapter mAdapter;
  private CartConfirmationPresenter mPresenter;

  private int cardVPDotsCount;
  private int addressVPDotsCount;
  private ImageView[] cardVPDots;
  private ImageView[] addressVPDots;
  /**
   * The Viewpage dots ll.
   */
  LinearLayout viewpageDotsLl;
  /**
   * The Viewpage address dots ll.
   */
  LinearLayout viewpageAddressDotsLl;
  private TextView mCardSelected;
  private ProgressDialog progressDialog;

  /**
   * Instantiates a new Cart confirmation pairing fragment.
   */
  public CartConfirmationPairingFragment() {
    //default constructor
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
    mAdapter = new CartConfirmationAdapter(new ArrayList<Item>(0));
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
    TextView mExpressCheckout = view.findViewById(R.id.tv_express_checkout_bottom);
    viewpageDotsLl = view.findViewById(R.id.card_dots_vp);
    ViewPager mViewPager = view.findViewById(R.id.card_confirmation_vp);
    viewpageAddressDotsLl = view.findViewById(R.id.address_dots_vp);
    ViewPager mViewPagerAddress = view.findViewById(R.id.address_confirmation_vp);

    mExpressCheckout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showLoadingSpinner(true);
        mPresenter.expressCheckout(masterpassConfirmationObject);
      }
    });

    CartConfirmationPairingAdapter cartConfirmationPairingAdapter =
        new CartConfirmationPairingAdapter(getContext(),
            masterpassConfirmationObject.getListPreCheckoutCard());
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
    mCardSelected.setText(
        masterpassConfirmationObject.getListPreCheckoutCard().get(0).getPreLastFour());
    masterpassConfirmationObject.setDoCheckoutCardId(
        masterpassConfirmationObject.getListPreCheckoutCard().get(0).getPreCardId());
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //onPageScrolled
      }

      @Override public void onPageSelected(int position) {
        for (int i = 0; i < cardVPDotsCount; i++) {
          cardVPDots[i].setImageDrawable(
              ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_inactive));
        }
        cardVPDots[position].setImageDrawable(
            ContextCompat.getDrawable(getContext(), R.drawable.viewpager_selector_active));
        mCardSelected.setText(
            masterpassConfirmationObject.getListPreCheckoutCard().get(position).getPreLastFour());
        masterpassConfirmationObject.setDoCheckoutCardId(
            masterpassConfirmationObject.getListPreCheckoutCard().get(position).getPreCardId());
      }

      @Override public void onPageScrollStateChanged(int state) {
        //onPageScrollStateChanged
      }
    });

    CartConfirmationPairingAddressAdapter cartConfirmationPairingAddressAdapter =
        new CartConfirmationPairingAddressAdapter(getContext(),
            masterpassConfirmationObject.getListPreCheckoutShipping());
    mViewPagerAddress.setAdapter(cartConfirmationPairingAddressAdapter);
    addressVPDotsCount = cartConfirmationPairingAddressAdapter.getCount();
    addressVPDots = new ImageView[addressVPDotsCount];
    for (int i = 0; i < cardVPDotsCount; i++) {
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
        //onPageScrolled
      }

      @Override public void onPageSelected(int position) {
        for (int i = 0; i < addressVPDotsCount; i++) {
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
        //onPageScrollStateChanged
      }
    });

    //If supress shipping is enable, the address pager will not be displayed
    ViewPager viewPagerShipping = view.findViewById(R.id.address_confirmation_vp);
    if (bundle.getBoolean(PresentationConstants.KEY_DATA_SUPRESS_SHIPPING)) {
      viewPagerShipping.setVisibility(View.GONE);
      viewpageAddressDotsLl.setVisibility(View.GONE);
    } else {
      viewPagerShipping.setVisibility(View.VISIBLE);
      viewpageAddressDotsLl.setVisibility(View.VISIBLE);
    }

    progressDialog = new ProgressDialog(this.getContext());
    progressDialog.setMessage(getString(R.string.loading));

    return view;
  }

  @Override public void showItems(List<Item> items) {
    mAdapter.replaceData(items);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //onActivityResult
  }

  @Override public void totalPrice(String totalPrice) {
    Log.d("SEBA", "TOTAL PRICE : " + totalPrice);
  }

  @Override public void subtotalPrice(String subtotalPrice) {
    Log.d("SEBA", "SUBTOTAL PRICE : " + subtotalPrice);
  }

  @Override public void taxPrice(String taxPrice) {
    Log.d("SEBA", "TOTAL TAX : " + taxPrice);
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
  public void showCompleteScreen(MasterpassConfirmationObject masterpassConfirmationObject) {

    Bundle bundle = new Bundle();
    bundle.putSerializable(PresentationConstants.KEY_DATA_CONFIRMATION,
        masterpassConfirmationObject);
    CartConfirmationFragment cartConfirmationFragment = new CartConfirmationFragment();
    cartConfirmationFragment.setArguments(bundle);

    new CartConfirmationPresenter(UseCaseHandler.getInstance(), cartConfirmationFragment,
        new GetItemsOnCartUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(this.getContext()))),
        new CompleteTransactionUseCase(MasterpassExternalDataSource.getInstance(),
            this.getContext()),
        new ConfirmExpressTransactionUseCase(MasterpassExternalDataSource.getInstance(),
            this.getContext()));

    AddFragmentToActivity.fragmentForFragment(getActivity().getSupportFragmentManager(),
        cartConfirmationFragment, R.id.main_container);
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
    //isSuppressShipping
  }

  @Override public void expressCheckout(MasterpassConfirmationObject masterpassConfirmationObject) {
    //expressCheckout
  }

  @Override public void onPause() {
    super.onPause();
  }

  @Override public void setPresenter(CartConfirmationPresenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    //onPageScrolled
  }

  @Override public void onPageSelected(int position) {
    //onPageSelected
  }

  @Override public void onPageScrollStateChanged(int state) {
    //onPageScrollStateChanged
  }
}