package com.mastercard.testapp.domain.usecase.settings;

import android.content.Context;
import androidx.annotation.NonNull;
import com.mastercard.testapp.domain.SettingsListOptions;
import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Get settings list
 */
public class GetSettingsUseCase
    extends UseCase<GetSettingsUseCase.RequestValues, GetSettingsUseCase.ResponseValue> {

  private final Context mContext;

  /**
   * Instantiates a new Get settings use case.
   *
   * @param context the context
   */
  public GetSettingsUseCase(@NonNull Context context) {
    mContext = checkNotNull(context, "CLASS TO SAVE ON SP");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    ResponseValue responseValue = new ResponseValue(SettingsListOptions.settingsList(mContext),
        SettingsListOptions.isLogged(mContext));
    getUseCaseCallback().onSuccess(responseValue);
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
    private final List<SettingsVO> mSettings;
    private final boolean isLogged;

    /**
     * Instantiates a new Response value.
     *
     * @param settings the settings
     * @param isLogged the is logged
     */
    public ResponseValue(@NonNull List<SettingsVO> settings, @NonNull boolean isLogged) {
      mSettings = checkNotNull(settings, "SETTINGS CLASS MUST EXIST");
      this.isLogged = checkNotNull(isLogged);
    }

    /**
     * Gets settings.
     *
     * @return the settings
     */
    public List<SettingsVO> getSettings() {
      return mSettings;
    }

    /**
     * Is logged boolean.
     *
     * @return the boolean
     */
    public boolean isLogged() {
      return isLogged;
    }
  }
}