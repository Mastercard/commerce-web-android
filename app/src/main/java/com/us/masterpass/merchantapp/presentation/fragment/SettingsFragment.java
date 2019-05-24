package com.us.masterpass.merchantapp.presentation.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.SettingsOnClickInterface;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.GetPairingIdUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailPaymentDataUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.RemovePairingIdUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveMasterpassPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.PresentationConstants;
import com.us.masterpass.merchantapp.presentation.SettingsConstants;
import com.us.masterpass.merchantapp.presentation.activity.LoginActivity;
import com.us.masterpass.merchantapp.presentation.adapter.SettingsAdapter;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPaymentPresenter;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsDetailPresenter;
import com.us.masterpass.merchantapp.presentation.presenter.SettingsPresenter;
import com.us.masterpass.merchantapp.presentation.view.SettingsListView;
import java.util.ArrayList;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 */
public class SettingsFragment extends Fragment implements SettingsListView {

    private SettingsAdapter mAdapter;
    private SettingsPresenter mPresenter;
    /**
     * The constant isLogged.
     */
    public static boolean isLogged;

    /**
     * New instance settings fragment.
     *
     * @return the settings fragment
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SettingsAdapter(new ArrayList<SettingsVO>(0), mSettingsOnClickInterface);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_list_view, container, false);
        ListView mListView = view.findViewById(R.id.settings_list);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void showSettings(List<SettingsVO> settings, boolean isLogged) {
        mAdapter.replaceData(settings);
        SettingsFragment.isLogged = isLogged;
    }

    @Override
    public void showSettingDetail(String title, String optionSelected) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle bundle = new Bundle();

        SettingsDetailFragment settingsDetailFragment = new SettingsDetailFragment();
        settingsDetailFragment.setArguments(bundle);

new SettingsDetailPresenter(
                UseCaseHandler.getInstance(),
                settingsDetailFragment,
                new GetSettingsDetailUseCase(SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
                new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
                optionSelected);

        bundle.putString(PresentationConstants.TITLE_SCREEN, title);
        bundle.putString(PresentationConstants.OPTION_SELECTED, optionSelected);

        ft.replace(R.id.main_container, settingsDetailFragment);
        ft.addToBackStack("DETAIL");
        ft.commit();
        fm.executePendingTransactions();
    }

    @Override
    public void showSettingPaymentDetail(String title, String optionSelected) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle bundle = new Bundle();

        SettingsDetailPaymentFragment settingsDetailPaymentFragment = new SettingsDetailPaymentFragment();
        settingsDetailPaymentFragment.setArguments(bundle);

             new SettingsDetailPaymentPresenter(
                UseCaseHandler.getInstance(),
                settingsDetailPaymentFragment,
                new GetSettingsDetailPaymentDataUseCase(SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
                new SaveSettingsUseCase(SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
                new SaveMasterpassPaymentMethodUseCase(SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
                new GetPairingIdUseCase(MasterpassExternalDataSource.getInstance()),
                new RemovePairingIdUseCase(SettingsSaveConfigurationSdk.getInstance(this.getActivity())),
                optionSelected);

        bundle.putString(PresentationConstants.TITLE_SCREEN, title);
        bundle.putString(PresentationConstants.OPTION_SELECTED, optionSelected);

        ft.replace(R.id.main_container, settingsDetailPaymentFragment);
        ft.addToBackStack("DETAIL");
        ft.commit();
        fm.executePendingTransactions();
    }

    @Override
    public void saveSettingSwitch(SettingsVO settingsVO) {
        mPresenter.saveSettingsSwitch(settingsVO);
    }

    @Override
    public void loadLoginActivity() {
        isLogged = false;
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra(PresentationConstants.LOGIN_SAVE_CONFIG, true);
        startActivity(intent);
    }


    @Override
    public void setPresenter(SettingsPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    /**
     * The M settings on click interface.
     */
    SettingsOnClickInterface mSettingsOnClickInterface = new SettingsOnClickInterface() {
        @Override
        public void onClickSettingItem(SettingsVO settingsVO) {
            String compareText = settingsVO.getName();
            switch (compareText){
                case SettingsConstants.ITEM_CARDS:
                    showSettingDetail(SettingsConstants.ITEM_CARDS, SettingsConstants.ITEM_CARDS);
                    break;
                case SettingsConstants.ITEM_LANGUAGE:
                    showSettingDetail(SettingsConstants.ITEM_LANGUAGE, SettingsConstants.ITEM_LANGUAGE);
                    break;
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
                    break;
                case SettingsConstants.ITEM_PAYMENT:
                    showSettingPaymentDetail(SettingsConstants.ITEM_PAYMENT, SettingsConstants.ITEM_PAYMENT);
                    break;
                default:
                    break;

            }
        }

        @Override
        public void loadLogin() {
            loadLoginActivity();
        }
    };
}
