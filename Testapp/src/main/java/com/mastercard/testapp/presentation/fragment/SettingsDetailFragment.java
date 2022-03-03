package com.mastercard.testapp.presentation.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.mastercard.testapp.R;
import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.presentation.PresentationConstants;
import com.mastercard.testapp.presentation.adapter.SettingsDetailAdapter;
import com.mastercard.testapp.presentation.presenter.SettingsDetailPresenter;
import com.mastercard.testapp.presentation.view.SettingsDetailListView;
import java.util.ArrayList;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Settings detail fragment, when
 */
public class SettingsDetailFragment extends Fragment implements SettingsDetailListView {

  private SettingsDetailAdapter mAdapter;
  private SettingsDetailPresenter mPresenter;
  private AlertDialog.Builder alert;
  private boolean saving;

  /**
   * New instance settings detail fragment.
   *
   * @return the settings detail fragment
   */
  public static SettingsDetailFragment newInstance() {
    return new SettingsDetailFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = new SettingsDetailAdapter(getContext(), new ArrayList<SettingsVO>());
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.start();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.settings_list_view, container, false);

    TextView mTitleView = view.findViewById(R.id.settings_title);
    mTitleView.setText(this.getArguments().getString(PresentationConstants.TITLE_SCREEN));

    ListView mListView = view.findViewById(R.id.settings_list);
    mListView.setAdapter(mAdapter);

    view.setFocusableInTouchMode(true);
    view.requestFocus();
    view.setOnKeyListener(new View.OnKeyListener() {
      @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
          if (!saving) {
            saving = true;
            mPresenter.saveSettings(mAdapter.getList());
          }

          return true;
        }
        return false;
      }
    });

    //Initialize alert
    alert = new AlertDialog.Builder(getActivity());
    alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        saving = false;
      }
    });
    alert.setCancelable(false);

    return view;
  }

  @Override public void setPresenter(SettingsDetailPresenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Override public void showSettingDetail(List<SettingsVO> settings) {
    saving = false;
    mAdapter.replaceData(settings);
  }

  @Override public void goBack() {
    getActivity().getSupportFragmentManager()
        .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
  }

  @Override public void showAlert() {
    alert.setMessage(getString(R.string.settings_cards_empty_alert));
    alert.show();
  }
}
