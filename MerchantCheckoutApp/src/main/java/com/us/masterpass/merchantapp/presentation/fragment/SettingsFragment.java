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
import android.widget.ListView;

import com.mastercard.mp.checkout.MasterpassError;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassUICallback;
import com.us.masterpass.merchantapp.domain.model.SettingsOnClickInterface;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.GetPairingIdUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.AddPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.DeletePaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.SaveSelectedPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailPaymentDataUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.RemovePairingIdUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveMasterpassPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.PresentationConstants;
import com.us.masterpass.merchantapp.presentation.SettingsConstants;
import com.us.masterpass.merchantapp.presentation.activity.LoginActivity;
import com.us.masterpass.merchantapp.presentation.adapter.SettingsAdapter;
import com.us.masterpass.merchantapp.presentation.presenter.AddPaymentMethodPresenter;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPaymentPresenter;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPresenter;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsPresenter;
import com.us.masterpass.merchantapp.presentation.view.SettingsListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mastercard.mp.checkout.MasterpassError.ERROR_MEX_CHECKOUT_NOT_AVAILABLE;
import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * First screen of settings, shows a settings list
 */
public class SettingsFragment extends Fragment implements SettingsListView, MasterpassUICallback {

  /**
   * The constant isLogged.
   */
  public static boolean isLogged;
  private SettingsAdapter mAdapter;
  private SettingsPresenter mPresenter;
  /**
   * The M settings on click interface.
   */
  SettingsOnClickInterface mSettingsOnClickInterface = new SettingsOnClickInterface() {
    @Override public void onClickSettingItem(SettingsVO settingsVO) {
      String compareText = settingsVO.getName();
      switch (compareText) {
        case SettingsConstants.ITEM_CARDS:
          showSettingDetail(SettingsConstants.ITEM_CARDS, SettingsConstants.ITEM_CARDS);
          break;
        case SettingsConstants.ITEM_LANGUAGE:
          showSettingDetail(SettingsConstants.ITEM_LANGUAGE, SettingsConstants.ITEM_LANGUAGE);
          break;
        //Remove comments when required to enable the currency tab in setting
        case SettingsConstants.ITEM_CURRENCY:
          showSettingDetail(SettingsConstants.ITEM_CURRENCY,SettingsConstants.ITEM_CURRENCY);
          break;
        case SettingsConstants.ITEM_DSRP:
          showSettingDetail(SettingsConstants.TITLE_DSRP, SettingsConstants.ITEM_DSRP);
          break;
        case SettingsConstants.ITEM_SUPRESS:
          saveSettingSwitch(settingsVO);
          break;
        case SettingsConstants.ITEM_EXPRESS:
          saveSettingSwitch(settingsVO);
          mAdapter.notifyDataSetChanged();
          break;
        case SettingsConstants.ITEM_ENABLE_PAYMENT_METHOD:
          saveSettingSwitch(settingsVO);
          break;
        case SettingsConstants.ENABLE_WEB_CHECKOUT:
          saveSettingSwitch(settingsVO);
          break;
        case SettingsConstants.PAIRING_ONLY:
          saveSettingSwitch(settingsVO);
          break;
        case SettingsConstants.ITEM_PAYMENT_METHOD:
          showSettingPaymentDetail(SettingsConstants.ITEM_PAYMENT_METHOD,
              SettingsConstants.ITEM_PAYMENT_METHOD);
          mAdapter.notifyDataSetChanged();
          break;
          case SettingsConstants.SUPRESS_3DS:
              saveSettingSwitch(settingsVO);
              break;
        default:
          break;
      }
    }

    @Override public void loadLogin() {
      loadLoginActivity();
    }

    @Override public void showProgress() {
      ProgressDialogFragment.showProgressDialog(getFragmentManager());
    }

    @Override public void showError() {
      new BaseAlertDialog.Builder(getContext(), BaseAlertDialog.MEX_ERROR_DIALOG).setDialogTitle
          (getResources().getString(R.string.error))
          .setDialogMessage(getResources().getString(R.string.try_again))
          .setDialogListener(new EmptyDialogInteractionListener())
          .build()
          .show();
    }
    @Override public void hideProgress() {
      ProgressDialogFragment.dismissProgressDialog();
    }

  };

  /**
   * New instance settings fragment.
   *
   * @return the settings fragment
   */
  public static SettingsFragment newInstance() {
    return new SettingsFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = new SettingsAdapter(new ArrayList<SettingsVO>(0), mSettingsOnClickInterface);
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.start();
    MasterpassSdkCoordinator.getInstance().addFragmentListener(this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.settings_list_view, container, false);
    ListView mListView = view.findViewById(R.id.settings_list);
    mListView.setAdapter(mAdapter);
    return view;
  }

  @Override public void showSettings(List<SettingsVO> settings, boolean isLogged) {
    mAdapter.replaceData(settings);
    this.isLogged = isLogged;
  }

  @Override public void showSettingDetail(String title, String optionSelected) {
    FragmentManager fm = getActivity().getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    Bundle bundle = new Bundle();

    SettingsDetailFragment settingsDetailFragment = new SettingsDetailFragment();
    settingsDetailFragment.setArguments(bundle);

    SettingsDetailPresenter mSettingsDetailPresenter =
        new SettingsDetailPresenter(UseCaseHandler.getInstance(), settingsDetailFragment,
            new GetSettingsDetailUseCase(
                SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
            new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
            optionSelected);

    bundle.putString(PresentationConstants.TITLE_SCREEN, title);
    bundle.putString(PresentationConstants.OPTION_SELECTED, optionSelected);

    ft.replace(R.id.main_container, settingsDetailFragment);
    ft.addToBackStack("DETAIL");
    ft.commit();
    fm.executePendingTransactions();
  }

  @Override public void showSettingPaymentDetail(String title, String optionSelected) {

    AddPaymentMethodFragment addPaymentMethodFragment = AddPaymentMethodFragment.newInstance();
    new AddPaymentMethodPresenter(UseCaseHandler.getInstance(), addPaymentMethodFragment,
        new AddPaymentMethodUseCase(), new DeletePaymentMethodUseCase(),
        new SaveSelectedPaymentMethodUseCase(MasterpassSdkCoordinator.getInstance()));

    Bundle bundle = new Bundle();

    SettingsDetailPaymentFragment settingsDetailPaymentFragment =
        new SettingsDetailPaymentFragment();
    settingsDetailPaymentFragment.setArguments(bundle);

    SettingsDetailPaymentPresenter mSettingsDetailPaymentPresenter =
        new SettingsDetailPaymentPresenter(UseCaseHandler.getInstance(),
            settingsDetailPaymentFragment, new GetSettingsDetailPaymentDataUseCase(
            SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
            new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
            new SaveMasterpassPaymentMethodUseCase(
                SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
            new GetPairingIdUseCase(MasterpassExternalDataSource.getInstance()),
            new RemovePairingIdUseCase(
                SettingsSaveConfigurationSdk.getInstance(this.getActivity())), optionSelected);

    bundle.putString(PresentationConstants.TITLE_SCREEN, title);
    bundle.putString(PresentationConstants.OPTION_SELECTED, optionSelected);

    AddFragmentToActivity.replaceFragmentWithBackStack(getActivity().getSupportFragmentManager(),
        addPaymentMethodFragment, R.id.main_container);
  }

  @Override public void saveSettingSwitch(SettingsVO settingsVO) {
    mPresenter.saveSettingsSwitch(settingsVO);
  }

  @Override public void loadLoginActivity() {
    isLogged = false;
    Intent intent = new Intent(getContext(), LoginActivity.class);
    intent.putExtra(PresentationConstants.LOGIN_SAVE_CONFIG, true);
    startActivity(intent);
  }

  @Override public void setPresenter(SettingsPresenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Override public void onSDKCheckoutComplete(HashMap<String, Object> parameters) {
    mPresenter.getPairingId(parameters);
  }

  @Override public void onSDKCheckoutError(MasterpassError masterpassError) {
    if (masterpassError.code() == ERROR_MEX_CHECKOUT_NOT_AVAILABLE) {
    new BaseAlertDialog.Builder(getContext(), BaseAlertDialog.MEX_ERROR_DIALOG).setDialogTitle
            (getResources().getString(R.string.went_wrong_error))
            .setDialogMessage(getResources().getString(R.string.feature_not_available))
            .setDialogListener(new EmptyDialogInteractionListener())
            .build()
            .show();
    }
  }
}
