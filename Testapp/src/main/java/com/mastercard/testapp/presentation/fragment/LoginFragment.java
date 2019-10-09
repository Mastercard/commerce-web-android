package com.mastercard.testapp.presentation.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.mastercard.testapp.R;
import com.mastercard.testapp.presentation.PresentationConstants;
import com.mastercard.testapp.presentation.activity.MyAccountActivity;
import com.mastercard.testapp.presentation.presenter.LoginPresenter;
import com.mastercard.testapp.presentation.view.LoginView;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Login screen
 */
public class LoginFragment extends Fragment implements LoginView {

  private LoginPresenter mPresenter;
  private EditText mUsername;
  private EditText mPassword;
  private ProgressDialog progressDialog;
  private AlertDialog.Builder alert;
  private boolean forceSaveConfig;

  /**
   * New instance login fragment.
   *
   * @return the login fragment
   */
  public static LoginFragment newInstance() {
    return new LoginFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_view, container, false);

    Bundle arguments = getArguments();
    if (arguments != null) {
      if (arguments.containsKey(PresentationConstants.LOGIN_SAVE_CONFIG)) {
        forceSaveConfig = arguments.getBoolean(PresentationConstants.LOGIN_SAVE_CONFIG);
      } else {
        forceSaveConfig = false;
      }
    }

    mUsername = view.findViewById(R.id.username_edit_text);
    mPassword = view.findViewById(R.id.password_edit_text);

    view.findViewById(R.id.login_login).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mPresenter.doLogin(mUsername.getText().toString(), mPassword.getText().toString(),
            forceSaveConfig);
      }
    });

    progressDialog = new ProgressDialog(this.getContext());
    progressDialog.setMessage(getString(R.string.loading));
    progressDialog.setCancelable(false);

    //Initialize alert
    alert = new AlertDialog.Builder(getActivity());
    alert.setPositiveButton(getString(R.string.ok), null);
    alert.setCancelable(true);

    return view;
  }

  @Override public void setPresenter(LoginPresenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Override public void doLogin() {
    Intent data = new Intent(getContext(), MyAccountActivity.class);
    startActivity(data);
    getActivity().finish();
  }

  @Override public void alertEmpty() {
    alert.setMessage(getString(R.string.login_empty_username_or_pass));
    alert.show();
  }

  @Override public void alertWrong() {
    alert.setMessage(getString(R.string.login_wrong_credentials));
    alert.show();
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
}
