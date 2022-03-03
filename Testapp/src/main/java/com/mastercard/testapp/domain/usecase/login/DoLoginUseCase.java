package com.mastercard.testapp.domain.usecase.login;

import android.content.Context;
import androidx.annotation.NonNull;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.data.external.MasterpassDataSource;
import com.mastercard.testapp.data.external.MasterpassExternalDataSource;
import com.mastercard.testapp.domain.model.LoginObject;
import com.mastercard.testapp.domain.usecase.base.UseCase;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 * <p>
 * Request to manage login on server, user and password requires
 */
public class DoLoginUseCase
    extends UseCase<DoLoginUseCase.RequestValues, DoLoginUseCase.ResponseValue> {

  private final MasterpassExternalDataSource mMasterpassExternal;
  private final Context mContext;

  /**
   * Instantiates a new Do login use case.
   *
   * @param masterpassExternalDataSource the masterpass external data source
   * @param context the context
   */
  public DoLoginUseCase(@NonNull MasterpassExternalDataSource masterpassExternalDataSource,
      @NonNull Context context) {
    mMasterpassExternal = checkNotNull(masterpassExternalDataSource, "HANDLE CONNECTION WITH MS");
    mContext = checkNotNull(context, "TO GET LOCAL DATA");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    if (values.username.length() > 0 && values.password.length() > 0) {

      mMasterpassExternal.doLogin(values.getUsername(), values.getPassword(),
          new MasterpassDataSource.LoadDataLoginCallback() {
            @Override public void onDataLogin(LoginObject loginObject) {
              SettingsSaveConfigurationSdk.getInstance(mContext).
                  loginSave(loginObject, values.forceSaveConfig);

              ResponseValue responseValue = new ResponseValue(loginObject, true);
              getUseCaseCallback().onSuccess(responseValue);
            }

            @Override public void onDataNotAvailable() {
              ResponseValue responseValue = new ResponseValue(null, false);
              getUseCaseCallback().onSuccess(responseValue);
            }
          });
    } else {
      getUseCaseCallback().onError();
    }
  }

  /**
   * The type Request values.
   */
  public static final class RequestValues implements UseCase.RequestValues {
    private final String username;
    private final String password;
    private final boolean forceSaveConfig;

    /**
     * Instantiates a new Request values.
     *
     * @param username the username
     * @param password the password
     * @param forceSaveConfig the force save config
     */
    public RequestValues(String username, String password, boolean forceSaveConfig) {
      this.username = username;
      this.password = password;
      this.forceSaveConfig = forceSaveConfig;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
      return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
      return password;
    }

    /**
     * Is force save config boolean.
     *
     * @return the boolean
     */
    public boolean isForceSaveConfig() {
      return forceSaveConfig;
    }
  }

  /**
   * The type Response value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {
    private final LoginObject mLoginObject;
    private final boolean loginSuccess;

    /**
     * Instantiates a new Response value.
     *
     * @param loginObject the login object
     * @param loginSuccess the login success
     */
    public ResponseValue(LoginObject loginObject, boolean loginSuccess) {
      mLoginObject = loginObject;
      this.loginSuccess = loginSuccess;
    }

    /**
     * Gets login object.
     *
     * @return the login object
     */
    public LoginObject getmLoginObject() {
      return mLoginObject;
    }

    /**
     * Is login success boolean.
     *
     * @return the boolean
     */
    public boolean isLoginSuccess() {
      return loginSuccess;
    }
  }
}