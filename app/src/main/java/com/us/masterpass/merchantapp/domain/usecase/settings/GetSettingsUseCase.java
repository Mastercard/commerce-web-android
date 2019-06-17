package com.us.masterpass.merchantapp.domain.usecase.settings;

import android.content.Context;
import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
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
    ResponseValue responseValue = new ResponseValue(SettingsListOptions.settingsList(mContext));
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

    /**
     * Instantiates a new Response value.
     *
     * @param settings the settings
     */
    public ResponseValue(@NonNull List<SettingsVO> settings) {
      mSettings = checkNotNull(settings, "SETTINGS CLASS MUST EXIST");
    }

    /**
     * Gets settings.
     *
     * @return the settings
     */
    public List<SettingsVO> getSettings() {
      return mSettings;
    }
  }
}