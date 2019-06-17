package com.us.masterpass.merchantapp.presentation.presenter;

import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.SettingsPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.SettingsListView;
import java.util.ArrayList;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public class SettingsPresenter implements SettingsPresenterInterface {

  private SettingsListView mSettingsListView;
  private final SaveSettingsUseCase mSaveSettings;
  private final GetSettingsUseCase mGetSettings;
  private final UseCaseHandler mUseCaseHandler;

  /**
   * Instantiates a new Settings presenter.
   *
   * @param useCaseHandler the use case handler
   * @param settingsListView the settings list view
   * @param saveSettings the save settings
   * @param getSettings the get settings
   */
  public SettingsPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull SettingsListView settingsListView, @NonNull SaveSettingsUseCase saveSettings,
      @NonNull GetSettingsUseCase getSettings) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "NEVER MUST BE NULL HANDLER");
    mSettingsListView = checkNotNull(settingsListView, "NEVER MUST BE NULL VIEW");
    mSaveSettings = checkNotNull(saveSettings, "NEVER MUST BE NULL USE CASE");
    mGetSettings = checkNotNull(getSettings, "NEVER MUST BE NULL USE CASE");
    mSettingsListView.setPresenter(this);
  }

  @Override public void start() {
    loadSettings();
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

  @Override public void loadSettings() {
    mUseCaseHandler.execute(mGetSettings, new GetSettingsUseCase.RequestValues(),
        new UseCase.UseCaseCallback<GetSettingsUseCase.ResponseValue>() {
          @Override public void onSuccess(GetSettingsUseCase.ResponseValue response) {
            mSettingsListView.showSettings(response.getSettings());
          }

          @Override public void onError() {
            //onError
          }
        });
  }

  @Override public void saveSettingsSwitch(SettingsVO settingsVO) {
    List<SettingsVO> optionsSelected = new ArrayList<>();
    optionsSelected.add(settingsVO);
    mUseCaseHandler.execute(mSaveSettings, new SaveSettingsUseCase.RequestValues(optionsSelected),
        new UseCase.UseCaseCallback<SaveSettingsUseCase.ResponseValue>() {
          @Override public void onSuccess(SaveSettingsUseCase.ResponseValue response) {
            //onSuccess
          }

          @Override public void onError() {
            //onError
          }
        });
  }
}
