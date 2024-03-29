package com.mastercard.testapp.presentation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mastercard.mp.checkout.MasterpassError;
import com.mastercard.testapp.R;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.masterpass.MasterpassUICallback;
import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.presentation.PresentationConstants;
import com.mastercard.testapp.presentation.activity.LoginActivity;
import com.mastercard.testapp.presentation.presenter.SettingsDetailPaymentPresenter;
import com.mastercard.testapp.presentation.view.SettingsDetailPaymentListView;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Settings screen for payment methods
 */
public class SettingsDetailPaymentFragment extends Fragment
    implements SettingsDetailPaymentListView, MasterpassUICallback {

  /**
   * The Login request.
   */
  static final int LOGIN_REQUEST = 1;
  private SettingsDetailPaymentPresenter mPresenter;
  private boolean checkIsLogged;
  private boolean isLogged;
  private boolean hasPairingId;
  private RelativeLayout mPaymentMasterpassRL;
  private CheckBox mPaymentMasterpassCB;

  /**
   * New instance settings detail payment fragment.
   *
   * @return the settings detail payment fragment
   */
  public static SettingsDetailPaymentFragment newInstance() {
    return new SettingsDetailPaymentFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter.initializeMasterpassMerchant(getContext());
    mPresenter.start();
  }

  @Override public void onResume() {
    super.onResume();
    if (checkIsLogged) {
      checkIsLogged = false;
      mPaymentMasterpassCB.setChecked(true);
      SettingsFragment.isLogged = true;
      mPresenter.selectMasterpassPayment();
    }
    MasterpassSdkCoordinator.getInstance().addFragmentListener(this);
  }

  @Override public void onDetach() {
    super.onDetach();
    MasterpassSdkCoordinator.getInstance().removeFragmentListener(this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.settings_payment_detail_view, container, false);
    checkIsLogged = false;

    TextView mTitleView = view.findViewById(R.id.settings_title);
    mPaymentMasterpassRL = view.findViewById(R.id.payment_masterpass_rl);
    mPaymentMasterpassCB = view.findViewById(R.id.payment_masterpass_check);
    mPaymentMasterpassCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isLogged) {
          if (hasPairingId) {
            mPresenter.removePairingId();
          } else {
            mPresenter.selectMasterpassPayment();
            mPaymentMasterpassCB.setChecked(true);
          }
        } else {
          if (checkIsLogged) {
            loadLoginActivity();
          }
        }
      }
    });
    mTitleView.setText(this.getArguments().getString(PresentationConstants.TITLE_SCREEN));
    enableButtonMasterpassPayment();

    return view;
  }

  @Override public void showSettingDetail(List<SettingsVO> settings) {
  }

  @Override public void showSettingDetail(boolean isLogged, boolean hasPairingId) {
    this.isLogged = isLogged;
    this.hasPairingId = hasPairingId;
    mPaymentMasterpassCB.setChecked(hasPairingId);
  }

  @Override public void goBack() {
    getActivity().getSupportFragmentManager()
        .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
  }

  @Override public void showAlert() {

  }

  @Override public void loadLoginActivity() {
    Intent intent = new Intent(getContext(), LoginActivity.class);
    startActivityForResult(intent, LOGIN_REQUEST);
  }

  @Override public void enableButtonMasterpassPayment() {
    mPaymentMasterpassRL.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (isLogged) {
          if (hasPairingId) {
            mPresenter.removePairingId();
          } else {
            mPresenter.selectMasterpassPayment();
            mPaymentMasterpassCB.setChecked(true);
          }
        } else {
          loadLoginActivity();
        }
      }
    });
  }

  @Override public void updateCheckBox(boolean enable) {
    mPaymentMasterpassCB.setChecked(enable);
    hasPairingId = enable;
  }

  @Override public void setPresenter(SettingsDetailPaymentPresenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Override public void onSDKCheckoutComplete(HashMap<String, Object> parameters) {
    mPresenter.getPairingId(parameters, getContext());
  }

  @Override public void onSDKCheckoutError(MasterpassError masterpassError) {

  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == LOGIN_REQUEST) {
      if (resultCode == RESULT_OK) {
        checkIsLogged = true;
      }
    }
  }

  //@Override public void onPairingCall() {
  //    mPresenter.selectMasterpassPayment();
  //}
}
