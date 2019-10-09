package com.mastercard.testapp.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.mastercard.mp.switchservices.HttpCallback;
import com.mastercard.mp.switchservices.MasterpassSwitchServices;
import com.mastercard.mp.switchservices.ServiceError;
import com.mastercard.mp.switchservices.checkout.PairingIdResponse;
import com.mastercard.testapp.BuildConfig;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.settings.GetSettingsUseCase;
import com.mastercard.testapp.domain.usecase.settings.SaveSettingsUseCase;
import com.mastercard.testapp.presentation.presenter.base.SettingsPresenterInterface;
import com.mastercard.testapp.presentation.view.SettingsListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public class SettingsPresenter implements SettingsPresenterInterface {

  private static final String PAIRING_TRANSACTION_ID = "PairingTransactionId";
  private final SaveSettingsUseCase mSaveSettings;
  private final GetSettingsUseCase mGetSettings;
  private final UseCaseHandler mUseCaseHandler;
  private SettingsListView mSettingsListView;

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

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void loadSettings() {
    mUseCaseHandler.execute(mGetSettings, new GetSettingsUseCase.RequestValues(),
        new UseCase.UseCaseCallback<GetSettingsUseCase.ResponseValue>() {
          @Override public void onSuccess(GetSettingsUseCase.ResponseValue response) {
            mSettingsListView.showSettings(response.getSettings(), response.isLogged());
          }

          @Override public void onError() {
          }
        });
  }

  @Override public void saveSettingsSwitch(SettingsVO settingsVO) {
    List<SettingsVO> optionsSelected = new ArrayList<>();
    optionsSelected.add(settingsVO);
    mUseCaseHandler.execute(mSaveSettings, new SaveSettingsUseCase.RequestValues(optionsSelected),
        new UseCase.UseCaseCallback<SaveSettingsUseCase.ResponseValue>() {
          @Override public void onSuccess(SaveSettingsUseCase.ResponseValue response) {
            start();
          }

          @Override public void onError() {
            mSettingsListView.loadLoginActivity();
          }
        });
  }

  @Override public void getPairingId(HashMap<String, Object> checkoutData, Context context) {
    if (checkoutData.get(PAIRING_TRANSACTION_ID) != null) {
      MasterpassSdkCoordinator.savePairingTransactionId(
          checkoutData.get(PAIRING_TRANSACTION_ID).toString());
      MasterpassSwitchServices switchServices = new MasterpassSwitchServices(BuildConfig.CLIENT_ID);
      switchServices.pairingId(checkoutData.get(PAIRING_TRANSACTION_ID).toString(),
          MasterpassSdkCoordinator.getUserId(), BuildConfig.ENVIRONMENT.toUpperCase(),
          MasterpassSdkCoordinator.getPublicKey(context), new HttpCallback<PairingIdResponse>() {
            @Override public void onResponse(PairingIdResponse response) {
              MasterpassSdkCoordinator.savePairingId(response.getPairingId());
            }

            @Override public void onError(ServiceError error) {
            }
          });
    }
  }
}
