package com.mastercard.testapp.presentation.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.mastercard.testapp.R;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.model.LoginObject;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.paymentMethod.AddPaymentMethodUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.DeletePaymentMethodUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.SaveSelectedPaymentMethodUseCase;
import com.mastercard.testapp.presentation.AddFragmentToActivity;
import com.mastercard.testapp.presentation.activity.ItemsActivity;
import com.mastercard.testapp.presentation.activity.LoginActivity;
import com.mastercard.testapp.presentation.presenter.AddPaymentMethodPresenter;
import com.mastercard.testapp.presentation.presenter.MyAccountPresenter;
import com.mastercard.testapp.presentation.view.MyAccountView;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

public class MyAccountFragment extends Fragment implements MyAccountView {

  private MyAccountPresenter mPresenter;
  private TextView mFirstName;
  private TextView mLastName;
  private TextView mUsername;
  private TextView mFirstNameLabel;
  private TextView mLastNameLabel;
  private TextView mUsernameLabel;
  private Button mLogoutButton;
  private ImageView mMyAccountBack;
  private Button mAddPaymentMethod;

  /**
   * New instance myAccount fragment.
   *
   * @return the myAccount fragment
   */
  public static MyAccountFragment newInstance() {
    return new MyAccountFragment();
  }

  @Override public void setPresenter(MyAccountPresenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.my_account_view, container, false);

    mUsername = view.findViewById(R.id.my_account_username);
    mFirstName = view.findViewById(R.id.my_account_firstname);
    mLastName = view.findViewById(R.id.my_account_lastname);
    mUsernameLabel = view.findViewById(R.id.my_account_username_label);
    mFirstNameLabel = view.findViewById(R.id.my_account_firstname_label);
    mLastNameLabel = view.findViewById(R.id.my_account_lastname_label);
    mLogoutButton = view.findViewById(R.id.my_account_logout);
    mMyAccountBack = view.findViewById(R.id.my_account_back);
    mAddPaymentMethod = view.findViewById(R.id.add_payment_method);

    ((TextView) view.findViewById(R.id.my_account_title)).setText(R.string.my_account_title);

    mUsernameLabel.setText(R.string.my_account_username);
    mFirstNameLabel.setText(R.string.my_account_first_name);
    mLastNameLabel.setText(R.string.my_account_last_name);
    mPresenter.isPaymentMethodEnabled();

    mMyAccountBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mPresenter.loadItemsActivity();
      }
    });

    mLogoutButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mPresenter.logoutButton();
      }
    });

    mAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mPresenter.loadPaymentMethod();
      }
    });

    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    LoginObject data = mPresenter.getSavedLoggedInData();
    mFirstName.setText(data.getFirstName());
    mLastName.setText(data.getLastName());
    mUsername.setText(data.getUsername());

    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void loadLoginActivity() {
    Intent intent = new Intent(getContext(), LoginActivity.class);
    startActivity(intent);
    getActivity().finish();
  }

  @Override public void loadItemsActivity() {
    Intent intent = new Intent(getContext(), ItemsActivity.class);
    startActivity(intent);
    getActivity().finish();
  }

  @Override public void showConfirmLogoutDialog() {
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
          mPresenter.logout();
        } else {
          dialog.dismiss();
        }
      }
    };

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(R.string.my_account_confirm_logout)
        .setPositiveButton(R.string.my_account_logout_yes, dialogClickListener)
        .setNegativeButton(R.string.my_account_logout_no, dialogClickListener)
        .show();
  }

  @Override public void showPaymentMethodScreen() {
    AddPaymentMethodFragment paymentMethodFragment = AddPaymentMethodFragment.newInstance();
    new AddPaymentMethodPresenter(UseCaseHandler.getInstance(), paymentMethodFragment,
        new AddPaymentMethodUseCase(), new DeletePaymentMethodUseCase(),
        new SaveSelectedPaymentMethodUseCase(MasterpassSdkCoordinator.getInstance()));
    AddFragmentToActivity.addFragmentWithBackStack(getActivity().getSupportFragmentManager(),
        paymentMethodFragment, R.id.main_container);
  }

  @Override public void setPaymentMethodVisibility(boolean visibility) {
    mAddPaymentMethod.setEnabled(visibility);
  }
}
