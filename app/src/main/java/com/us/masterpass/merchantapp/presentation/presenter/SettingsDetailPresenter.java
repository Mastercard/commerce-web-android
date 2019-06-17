package com.us.masterpass.merchantapp.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.SettingsDetailPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.SettingsDetailListView;
import java.util.List;
import java.util.Map;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public class SettingsDetailPresenter implements SettingsDetailPresenterInterface {

  private SettingsDetailListView mSettingsDetailListView;
  private final GetSettingsDetailUseCase mGetSettingsDetail;
  private final SaveSettingsUseCase mSaveSettings;
  private final UseCaseHandler mUseCaseHandler;
  private final String mOptionSelected;

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
    //resume
  }

  @Override public void pause() {
    //pause
  }

  @Override public void destroy() {
    //destroy
  }

  @Override public void loadSettingsDetail() {
    mUseCaseHandler.execute(mGetSettingsDetail,
        new GetSettingsDetailUseCase.RequestValues(mOptionSelected),
        new UseCase.UseCaseCallback<GetSettingsDetailUseCase.ResponseValue>() {
          @Override public void onSuccess(GetSettingsDetailUseCase.ResponseValue response) {
            mSettingsDetailListView.showSettingDetail(response.getSettings());
          }

          @Override public void onError() {
            //onError
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
    //selectMasterpassPayment
  }

  @Override public void initializeMasterpassMerchant(Context context) {
    //initializeMasterpassMerchant
  }

  @Override public void getPairingId(Map<String, Object> checkoutData) {
    //getPairingId
  }

  @Override public void removePairingId() {
    //removePairingId
  }
}
