package com.us.masterpass.merchantapp.presentation.presenter;

import android.content.Context;
import androidx.annotation.NonNull;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkInterface;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.GetPairingIdUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.InitializeSdkUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.GetSettingsDetailPaymentDataUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.RemovePairingIdUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveMasterpassPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.settings.SaveSettingsUseCase;
import com.us.masterpass.merchantapp.presentation.presenter.base.SettingsDetailPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.SettingsDetailPaymentListView;
import java.util.List;
import java.util.Map;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public class SettingsDetailPaymentPresenter implements SettingsDetailPresenterInterface {

  private SettingsDetailPaymentListView mSettingsDetailPaymentListView;
  private final GetSettingsDetailPaymentDataUseCase mGetSettingsDetailPaymentData;
  private final SaveSettingsUseCase mSaveSettings;
  private final GetPairingIdUseCase mGetPairingIdUseCase;
  private final RemovePairingIdUseCase mRemovePairingIdUseCase;
  private final UseCaseHandler mUseCaseHandler;

  private final String mOptionSelected;

  /**
   * Instantiates a new Settings detail payment presenter.
   *
   * @param useCaseHandler the use case handler
   * @param settingsDetailPaymentListView the settings detail payment list view
   * @param getSettings the get settings
   * @param saveSettings the save settings
   * @param saveMasterpassPaymentMethod the save masterpass payment method
   * @param getPairingIdUseCase the get pairing id use case
   * @param removePairingIdUseCase the remove pairing id use case
   * @param optionSelected the option selected
   */
  public SettingsDetailPaymentPresenter(@NonNull UseCaseHandler useCaseHandler,
      @NonNull SettingsDetailPaymentListView settingsDetailPaymentListView,
      @NonNull GetSettingsDetailPaymentDataUseCase getSettings,
      @NonNull SaveSettingsUseCase saveSettings,
      @NonNull SaveMasterpassPaymentMethodUseCase saveMasterpassPaymentMethod,
      @NonNull GetPairingIdUseCase getPairingIdUseCase,
      @NonNull RemovePairingIdUseCase removePairingIdUseCase, @NonNull String optionSelected) {
    mUseCaseHandler = checkNotNull(useCaseHandler, "NEVER MUST BE NULL HANDLER");
    mSettingsDetailPaymentListView =
        checkNotNull(settingsDetailPaymentListView, "NEVER MUST BE NULL VIEW");
    mGetSettingsDetailPaymentData = checkNotNull(getSettings, "NEVER MUST BE NULL USE CASE");
    mSaveSettings = checkNotNull(saveSettings, "NEVER MUST BE NULL USE CASE");
    mGetPairingIdUseCase = checkNotNull(getPairingIdUseCase, "NO NULL GET PAIRING ID FROM SERVER");
    mRemovePairingIdUseCase = checkNotNull(removePairingIdUseCase, "NO NULL REMOVE ON SP");
    mOptionSelected =
        checkNotNull(optionSelected, "NEVER MUST BE NULL USE CASE, SELECTION WAS MADE");
    mSettingsDetailPaymentListView.setPresenter(this);
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
    mUseCaseHandler.execute(mGetSettingsDetailPaymentData,
        new GetSettingsDetailPaymentDataUseCase.RequestValues(mOptionSelected),
        new UseCase.UseCaseCallback<GetSettingsDetailPaymentDataUseCase.ResponseValue>() {
          @Override
          public void onSuccess(GetSettingsDetailPaymentDataUseCase.ResponseValue response) {
            mSettingsDetailPaymentListView.showSettingDetail(response.isLogged(),
                response.hasPairing());
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
            mSettingsDetailPaymentListView.goBack();
          }

          @Override public void onError() {
            mSettingsDetailPaymentListView.showAlert();
          }
        });
  }

  @Override public void selectMasterpassPayment() {
    //selectMasterpassPayment
  }

  @Override public void initializeMasterpassMerchant(Context context) {
    InitializeSdkUseCase.initializeSdk(context, new MasterpassSdkInterface.GetFromMasterpassSdk() {
      @Override public void sdkResponseSuccess() {
        mSettingsDetailPaymentListView.enableButtonMasterpassPayment();
      }

      @Override public void sdkResponseError(String error) {
        //sdkResponseError
      }
    });
  }

  @Override public void getPairingId(Map<String, Object> checkoutData) {
    mUseCaseHandler.execute(mGetPairingIdUseCase,
        new GetPairingIdUseCase.RequestValues(checkoutData),
        new UseCase.UseCaseCallback<GetPairingIdUseCase.ResponseValue>() {
          @Override public void onSuccess(GetPairingIdUseCase.ResponseValue response) {
            mSettingsDetailPaymentListView.updateCheckBox(true);
          }

          @Override public void onError() {
            mSettingsDetailPaymentListView.updateCheckBox(false);
          }
        });
  }

  @Override public void removePairingId() {
    mUseCaseHandler.execute(mRemovePairingIdUseCase, new RemovePairingIdUseCase.RequestValues(),
        new UseCase.UseCaseCallback<RemovePairingIdUseCase.ResponseValue>() {
          @Override public void onSuccess(RemovePairingIdUseCase.ResponseValue response) {
            mSettingsDetailPaymentListView.updateCheckBox(false);
          }

          @Override public void onError() {
            //onError
          }
        });
  }
}
