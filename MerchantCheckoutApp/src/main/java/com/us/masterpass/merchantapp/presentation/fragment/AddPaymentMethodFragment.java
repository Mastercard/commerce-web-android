package com.us.masterpass.merchantapp.presentation.fragment;

/**
 * Fragment to display all the added payment methods
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mastercard.mp.checkout.MasterpassPaymentMethod;

import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.device.MerchantPaymentMethod;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.model.PaymentMethodOnClickInterface;
import com.us.masterpass.merchantapp.presentation.adapter.PaymentMethodAdapter;
import com.us.masterpass.merchantapp.presentation.presenter.AddPaymentMethodPresenter;
import com.us.masterpass.merchantapp.presentation.view.AddPaymentMethodView;
import com.us.masterpass.merchantapp.presentation.view.MerchantCheckoutView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

public final class AddPaymentMethodFragment extends Fragment
    implements AddPaymentMethodView, MerchantCheckoutView {


  private AddPaymentMethodPresenter presenter;
  private PaymentMethodAdapter adapter;
  private LinearLayout addCardLayout;
  private ArrayList<MerchantPaymentMethod> paymentMethodList;

  PaymentMethodOnClickInterface paymentMethodOnClickInterface =
      new PaymentMethodOnClickInterface() {

        @Override public void deletePaymentMethod(MerchantPaymentMethod merchantPaymentMethod) {
          presenter.deletePaymentMethod(merchantPaymentMethod);
        }

        @Override public void editPaymentMethod(MerchantPaymentMethod merchantPaymentMethod) {
          presenter.editPaymentMethod(merchantPaymentMethod);
        }

        @Override public void selectPaymentMethod(MerchantPaymentMethod merchantPaymentMethod) {
          presenter.selectPaymentMethod(merchantPaymentMethod);
        }
      };

  /**
   * Instantiates an instance of AddPaymentMethodFragment
   */
  public static AddPaymentMethodFragment newInstance() {
    return new AddPaymentMethodFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = new PaymentMethodAdapter(paymentMethodList, paymentMethodOnClickInterface);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.add_payment_method_recycler_view, container, false);
    addCardLayout = view.findViewById(R.id.add_card_image_layout);
    RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
    recyclerView.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if(!MasterpassSdkCoordinator.isSDKInitialized()) {
      presenter.initializeMasterpassMerchant();
    }else{
      masterpassSdkInitialized();
    }
    addCardLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        invokeAddPaymentFlow();
      }
    });
  }

  @Override public void setPresenter(AddPaymentMethodPresenter paymentMethodPresenter) {
    presenter = checkNotNull(paymentMethodPresenter);
  }

  @Override public void showPaymentMethod(List<MerchantPaymentMethod> paymentMethodList) {
    hideProgress();
    adapter.replaceData(paymentMethodList);
  }

  @Override public void showError(String error) {
    Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showAlertDialog() {
    new BaseAlertDialog.Builder(getContext(), BaseAlertDialog.MEX_ERROR_DIALOG).setDialogTitle
            (getResources().getString(R.string.went_wrong_error))
            .setDialogMessage(getResources().getString(R.string.feature_not_available))
            .setDialogListener(new EmptyDialogInteractionListener())
            .build()
            .show();
  }

  @Override public void masterpassSdkInitialized() {
    presenter.retrievePaymentMethods();
  }

  @Override public Context getContext() {
    return getActivity();
  }

  private void invokeAddPaymentFlow() {
    presenter.addPaymentMethod();
  }

  @Override public void paymentMethodStatus(String msg) {
    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
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
