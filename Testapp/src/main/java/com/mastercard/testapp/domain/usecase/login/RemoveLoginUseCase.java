package com.mastercard.testapp.domain.usecase.login;

import android.content.Context;
import androidx.annotation.NonNull;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.domain.usecase.base.UseCase;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 * <p>
 * Perform a logout for the login user
 */
public class RemoveLoginUseCase
    extends UseCase<RemoveLoginUseCase.RequestValues, RemoveLoginUseCase.ResponseValue> {

  private final Context mContext;

  /**
   * Instantiates a new Remove login use case.
   *
   * @param context the context
   */
  public RemoveLoginUseCase(@NonNull Context context) {
    mContext = checkNotNull(context, "TO GET LOCAL DATA");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    if (SettingsSaveConfigurationSdk.getInstance(mContext).removeLogged()) {
      getUseCaseCallback().onSuccess(null);
    } else {
      getUseCaseCallback().onError();
    }
  }

  /**
   * The type Request values.
   */
  public static final class RequestValues implements UseCase.RequestValues {

  }

  /**
   * The type Response value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {

  }
}