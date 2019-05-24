package com.us.masterpass.merchantapp.presentation.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.presentation.PresentationConstants;
import com.us.masterpass.merchantapp.presentation.presenter.LoginPresenter;
import com.us.masterpass.merchantapp.presentation.view.LoginView;

import static android.app.Activity.RESULT_OK;
import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        TextView mLogin = view.findViewById(R.id.login_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.doLogin(mUsername.getText().toString(),
                        mPassword.getText().toString(),
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

    @Override
    public void setPresenter(LoginPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void doLogin() {
        if (getActivity().getCallingActivity() != null) {
            Intent data = new Intent();
            getActivity().setResult(RESULT_OK, data);
        }
        getActivity().finish();
    }

    @Override
    public void alertEmpty() {
        alert.setMessage(getString(R.string.login_empty_username_or_pass));
        alert.show();
    }

    @Override
    public void alertWrong() {
        alert.setMessage(getString(R.string.login_wrong_credentials));
        alert.show();
    }

    @Override
    public void showLoadingSpinner(boolean show) {
        if (show) {
            progressDialog.show();
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        } else {
            progressDialog.dismiss();
        }
    }
}
