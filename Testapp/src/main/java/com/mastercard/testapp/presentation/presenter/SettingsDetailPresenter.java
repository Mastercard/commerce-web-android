package com.mastercard.testapp.presentation.presenter;

import android.content.Context;
import androidx.annotation.NonNull;
import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.settings.GetSettingsDetailUseCase;
import com.mastercard.testapp.domain.usecase.settings.SaveSettingsUseCase;
import com.mastercard.testapp.presentation.presenter.base.SettingsDetailPresenterInterface;
import com.mastercard.testapp.presentation.view.SettingsDetailListView;
import java.util.HashMap;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public class SettingsDetailPresenter implements SettingsDetailPresenterInterface {

  private final GetSettingsDetailUseCase mGetSettingsDetail;
  private final SaveSettingsUseCase mSaveSettings;
  private final UseCaseHandler mUseCaseHandler;
  private final String mOptionSelected;
  private SettingsDetailListView mSettingsDetailListView;

  /**
   * Instantiates a new Settings detail presenter.
   *
   * @param useCaseHandler the use case handler
   * @param settingsDetailListView the settings detail list view
   * @param getSettings the get settings
   * @param saveSettings the save settings
   * @param optionSelected the option selected
   */
  public SettingsDetailPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull SettingsDetailListView settingsDetailListView,
      @NonNull GetSettingsDetailUseCase getSettings, @NonNull SaveSettingsUseCase saveSettings,
      @NonNull String optionSelected) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "NEVER MUST BE NULL HANDLER");
    mSettingsDetailListView = checkNotNull(settingsDetailListView, "NEVER MUST BE NULL VIEW");
    mGetSettingsDetail = checkNotNull(getSettings, "NEVER MUST BE NULL USE CASE");
    mSaveSettings = checkNotNull(saveSettings, "NEVER MUST BE NULL USE CASE");
    mOptionSelected =
        checkNotNull(optionSelected, "NEVER MUST BE NULL USE CASE, SELECTION WAS MADE");
    mSettingsDetailListView.setPresenter(this);
  }

  @Override public void start() {
    loadSettingsDetail();
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void loadSettingsDetail() {
    mUseCaseHandler.execute(mGetSettingsDetail,
        new GetSettingsDetailUseCase.RequestValues(mOptionSelected),
        new UseCase.UseCaseCallback<GetSettingsDetailUseCase.ResponseValue>() {
          @Override public void onSuccess(GetSettingsDetailUseCase.ResponseValue response) {
            mSettingsDetailListView.showSettingDetail(response.getSettings());
          }

          @Override public void onError() {

          }
        });
  }

  @Override public void saveSettings(List<SettingsVO> settings) {
    mUseCaseHandler.execute(mSaveSettings, new SaveSettingsUseCase.RequestValues(settings),
        new UseCase.UseCaseCallback<SaveSettingsUseCase.ResponseValue>() {
          @Override public void onSuccess(SaveSettingsUseCase.ResponseValue response) {
            mSettingsDetailListView.goBack();
          }

          @Override public void onError() {
            mSettingsDetailListView.showAlert();
          }
        });
  }

  @Override public void selectMasterpassPayment() {

  }

  @Override public void initializeMasterpassMerchant(Context context) {

  }

  @Override public void getPairingId(HashMap<String, Object> checkoutData, Context context) {

  }

  @Override public void removePairingId() {

  }
}
