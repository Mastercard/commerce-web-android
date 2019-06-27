package com.us.masterpass.merchantapp.domain.usecase.login;

import android.content.Context;
import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Check if exist a login on the application
 */
public class IsLoggedUseCase extends UseCase<IsLoggedUseCase.LoggedInRequestValues, IsLoggedUseCase.LoggedInResponseValue> {

    private final Context mContext;

    /**
     * Instantiates a new Is logged use case.
     *
     * @param context the context
     */
    public IsLoggedUseCase(@NonNull Context context) {
        mContext = checkNotNull(context, "TO GET LOCAL DATA");
    }

    @Override
    protected void executeUseCase(final LoggedInRequestValues values) {
        // This changes to true if logged in. Change boolean. Must return loggedIn data to onSuccess to fill MyPageActivity.
        if (SettingsSaveConfigurationSdk.getInstance(mContext).getIsLogged()){
            getUseCaseCallback().onSuccess(null);
        } else{
            getUseCaseCallback().onError();
        }
    }

    /**
     * The type Request values.
     */
    public static final class LoggedInRequestValues implements UseCase.RequestValues {

    }

    /**
     * The type Response value.
     */
    public static final class LoggedInResponseValue implements UseCase.ResponseValue {

    }
}