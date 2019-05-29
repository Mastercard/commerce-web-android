package com.us.masterpass.merchantapp.presentation.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
 */
public class CartConfirmationFragment extends Fragment implements CartConfirmationListView {

    private CartConfirmationAdapter mAdapter;
    private CartConfirmationPresenter mPresenter;
    private TextView totalPrice;
    private TextView subtotalPrice;
    private TextView taxPrice;
    private ProgressDialog progressDialog;
    private LinearLayout llShippingOptions;

    /**
     * Instantiates a new Cart confirmation fragment.
     */
    public CartConfirmationFragment() {
        //public constructor
    }

    /**
     * New instance cart confirmation fragment.
     *
     * @return the cart confirmation fragment
     */
    public static CartConfirmationFragment newInstance() {
        return new CartConfirmationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CartConfirmationAdapter(new ArrayList<Item>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_confirmation_list_view, container, false);

        Bundle bundle = this.getArguments();
        final MasterpassConfirmationObject masterpassConfirmationObject =
                (MasterpassConfirmationObject) bundle.getSerializable(PresentationConstants.KEY_DATA_CONFIRMATION);

        totalPrice = view.findViewById(R.id.cart_total_text);
        subtotalPrice = view.findViewById(R.id.cart_subtotal_text);
        taxPrice = view.findViewById(R.id.cart_tax_text);

        ListView listView = view.findViewById(R.id.items_list_view);
        listView.setAdapter(mAdapter);

        TextView cardName = view.findViewById(R.id.cart_confimation_card_name);
        TextView cardNumber = view.findViewById(R.id.cart_confimation_card_number);
        TextView shippingAddress = view.findViewById(R.id.cart_confimation_address);
        TextView confirmationOrder = view.findViewById(R.id.cart_confirmation_order);
        cardName.setText(masterpassConfirmationObject.getCardBrandName());
        cardNumber.setText(masterpassConfirmationObject.getCardAccountNumberHidden());
        shippingAddress.setText(masterpassConfirmationObject.getShippingLine1());
        confirmationOrder.setText(masterpassConfirmationObject.getCartId());
        llShippingOptions = view.findViewById(R.id.ll_ship_content);

        LinearLayout llConfirm = view.findViewById(R.id.cart_confirm_btn);
        llConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirmCheckout(masterpassConfirmationObject);
                //showLoadingSpinner(true);
                getActivity().finish();
            }
        });

        LinearLayout llCancel = view.findViewById(R.id.cart_cancel_btn);
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage(getString(R.string.loading));

        return view;
    }

    @Override
    public void showItems(List<Item> items) {
        mAdapter.replaceData(items);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //onActivityResult
    }

    @Override
    public void totalPrice(String totalPrice) {
        this.totalPrice.setText(totalPrice);
    }

    @Override
    public void subtotalPrice(String subtotalPrice) {
        this.subtotalPrice.setText(subtotalPrice);
    }

    @Override
    public void taxPrice(String taxPrice) {
        this.taxPrice.setText(taxPrice);
    }

    @Override
    public void showLoadingSpinner(boolean show) {
        if (show){
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
        bundle.putSerializable(PresentationConstants.KEY_DATA_CONFIRMATION, masterpassConfirmationObject);
        CartCompleteFragment cartCompleteFragment = new CartCompleteFragment();
        cartCompleteFragment.setArguments(bundle);

        new CartCompletePresenter(
                UseCaseHandler.getInstance(),
                cartCompleteFragment,
                new GetItemsOnCartUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                        ItemLocalDataSource.getInstance(this.getContext())))
        );

        AddFragmentToActivity.fragmentForFragment(getActivity().getSupportFragmentManager(),
                cartCompleteFragment, R.id.main_container);
    }

    @Override
    public void isSuppressShipping(boolean suppressShipping) {
        if (suppressShipping){
            llShippingOptions.setVisibility(View.GONE);
        } else {
            llShippingOptions.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setPresenter(CartConfirmationPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}