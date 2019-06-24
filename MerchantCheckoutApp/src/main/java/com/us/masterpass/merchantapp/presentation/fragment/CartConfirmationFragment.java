package com.us.masterpass.merchantapp.presentation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.mastercard.mp.checkout.NetworkType;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.ItemLocalDataSource;
import com.us.masterpass.merchantapp.data.external.ItemExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.PresentationConstants;
import com.us.masterpass.merchantapp.presentation.adapter.CartConfirmationAdapter;
import com.us.masterpass.merchantapp.presentation.presenter.CartCompletePresenter;
import com.us.masterpass.merchantapp.presentation.presenter.CartConfirmationPresenter;
import com.us.masterpass.merchantapp.presentation.view.CartConfirmationListView;
import java.util.ArrayList;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 *
 * Confirmation screen
 */
public class CartConfirmationFragment extends Fragment implements CartConfirmationListView {

  private CartConfirmationAdapter mAdapter;
  private CartConfirmationPresenter mPresenter;
  private TextView totalPrice;
  private TextView subtotalPrice;
  private TextView taxPrice;
  private LinearLayout llShippingOptions;

  /**
   * Instantiates a new Cart confirmation fragment.
   */
  public CartConfirmationFragment() {
  }

  /**
   * New instance cart confirmation fragment.
   *
   * @return the cart confirmation fragment
   */
  public static CartConfirmationFragment newInstance() {
    return new CartConfirmationFragment();
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
    View view = inflater.inflate(R.layout.cart_confirmation_list_view, container, false);

    Bundle bundle = this.getArguments();
    final MasterpassConfirmationObject masterpassConfirmationObject =
        (MasterpassConfirmationObject) bundle.getSerializable(
            PresentationConstants.KEY_DATA_CONFIRMATION);

    totalPrice = view.findViewById(R.id.cart_total_text);
    subtotalPrice = view.findViewById(R.id.cart_subtotal_text);
    taxPrice = view.findViewById(R.id.cart_tax_text);

    ListView listView = view.findViewById(R.id.items_list_view);
    listView.setAdapter(mAdapter);

    TextView cardName = view.findViewById(R.id.cart_confimation_card_name);
    TextView cardNumber = view.findViewById(R.id.cart_confimation_card_number);
    TextView shippingAddress = view.findViewById(R.id.cart_confimation_address);
    TextView confirmationOrder = view.findViewById(R.id.cart_confirmation_order);
    ImageView cardBrandIcon = view.findViewById(R.id.cart_confirmation_card_brand_icon);
    cardName.setText(masterpassConfirmationObject.getCardBrandName());
    cardNumber.setText(masterpassConfirmationObject.getCardAccountNumberHidden());
    shippingAddress.setText(masterpassConfirmationObject.getShippingLine1()
        +" "+masterpassConfirmationObject.getShippingLine2()+'\n'+masterpassConfirmationObject
        .getShippingCity()+'\n'+masterpassConfirmationObject.getShippingSubDivision()+" "
        + " "+masterpassConfirmationObject.getPostalCode());
    confirmationOrder.setText(masterpassConfirmationObject.getCartId());
    cardBrandIcon.setImageResource(getCardBrandIcon(masterpassConfirmationObject.getCardBrandId()));
    llShippingOptions = view.findViewById(R.id.ll_ship_content);

    LinearLayout llConfirm = view.findViewById(R.id.cart_confirm_btn);
    llConfirm.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().finish();
        //mPresenter.confirmCheckout(masterpassConfirmationObject);
        //showLoadingSpinner(true);
      }
    });

    LinearLayout llCancel = view.findViewById(R.id.cart_cancel_btn);
    llCancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().finish();
      }
    });

    return view;
  }

  private int getCardBrandIcon(String cardBrandId) {
    if (NetworkType.MASTER.equalsIgnoreCase(cardBrandId)) {
      return R.drawable.mastercard_pay_with_icon;
    } else if (NetworkType.VISA.equalsIgnoreCase(cardBrandId)) {
      return R.drawable.visa_settings_icon;
    } else if (NetworkType.AMEX.equalsIgnoreCase(cardBrandId)) {
      return R.drawable.american_express_settings_icon;
    } else if (NetworkType.DISCOVER.equalsIgnoreCase(cardBrandId)) {
      return R.drawable.discover_settings_icon;
    } else if (NetworkType.MAESTRO.equalsIgnoreCase(cardBrandId)) {
      return R.drawable.logo_maestro;
    } else if (NetworkType.DINERS.equalsIgnoreCase(cardBrandId)) {
      return R.drawable.diners_club_pill;
    }
    return 0;
  }

  @Override public void showItems(List<Item> items) {
    mAdapter.replaceData(items);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //mPresenter.result(requestCode, resultCode);
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

  @Override
  public void showCompleteScreen(MasterpassConfirmationObject masterpassConfirmationObject) {
    FragmentManager fm = getActivity().getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    Bundle bundle = new Bundle();
    bundle.putSerializable(PresentationConstants.KEY_DATA_CONFIRMATION,
        masterpassConfirmationObject);
    CartCompleteFragment cartCompleteFragment = new CartCompleteFragment();
    cartCompleteFragment.setArguments(bundle);

    CartCompletePresenter mCartConfirmationPresenter =
        new CartCompletePresenter(UseCaseHandler.getInstance(), cartCompleteFragment,
            new GetItemsOnCartUseCase(
                ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                    ItemLocalDataSource.getInstance(this.getContext())), getContext()));

    AddFragmentToActivity.fragmentForFragment(getActivity().getSupportFragmentManager(),
        cartCompleteFragment, R.id.main_container);
  }

  @Override public void isSuppressShipping(boolean suppressShipping) {
    if (suppressShipping) {
      llShippingOptions.setVisibility(View.GONE);
    } else {
      llShippingOptions.setVisibility(View.VISIBLE);
    }
  }

  @Override public void expressCheckout(MasterpassConfirmationObject masterpassConfirmationObject) {

    }

  @Override public void onPause() {
    super.onPause();
  }

  @Override public void setPresenter(CartConfirmationPresenter presenter) {
    mPresenter = checkNotNull(presenter);
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

    private String validateEmptyString(String value){
        String mValue;
        mValue = value!=null ? value : "";
        return mValue;
    }

    @Override public void hideProgress() {
        ProgressDialogFragment.dismissProgressDialog();
    }
}