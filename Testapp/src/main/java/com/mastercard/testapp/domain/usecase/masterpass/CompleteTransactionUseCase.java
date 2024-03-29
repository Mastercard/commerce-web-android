package com.mastercard.testapp.domain.usecase.masterpass;

import android.content.Context;
import androidx.annotation.NonNull;
import com.mastercard.testapp.data.device.CartLocalStorage;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.data.external.MasterpassDataSource;
import com.mastercard.testapp.data.external.MasterpassExternalDataSource;
import com.mastercard.testapp.domain.Constants;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import java.util.Locale;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Complete transaction on checkout flow
 */
public class CompleteTransactionUseCase extends
    UseCase<CompleteTransactionUseCase.RequestValues, CompleteTransactionUseCase.ResponseValue> {

  private final MasterpassExternalDataSource mMasterpassExternal;
  private final Context mContext;

  /**
   * Instantiates a new Complete transaction use case.
   *
   * @param itemsRepository the items repository
   * @param context the context
   */
  public CompleteTransactionUseCase(@NonNull MasterpassExternalDataSource itemsRepository,
      @NonNull Context context) {
    mMasterpassExternal = checkNotNull(itemsRepository, "HANDLE CONNECTION WITH MS");
    mContext = checkNotNull(context, "TO GET LOCAL DATA");
  }

  @Override protected void executeUseCase(final RequestValues values) {
        /* GET CURRENCY FROM SETTINGS, AMOUNT FROM CART BOTH ON DEVICE
        completeAmount, completeCurrency;*/
    String completeCurrency =
        SettingsSaveConfigurationSdk.getInstance(mContext).getCurrencySelected();
    double completeAmount =
        CartLocalStorage.getInstance(mContext).getCartTotal(Constants.LOCAL_CART_DATASOURCE);
    String completeAmountString = String.format(Locale.US,"%.2f", completeAmount);

    values.masterpassConfirmationObject.setCompleteCurrency(completeCurrency);
    values.masterpassConfirmationObject.setCompleteAmount(completeAmountString);

    mMasterpassExternal.sendConfirmation(values.masterpassConfirmationObject,
        new MasterpassDataSource.LoadDataConfirmationCallback() {
          @Override
          public void onDataConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
              boolean expressCheckoutEnable) {
            ResponseValue responseValue = new ResponseValue(masterpassConfirmationObject);
            getUseCaseCallback().onSuccess(responseValue);
          }

          @Override public void onDataNotAvailable() {
            getUseCaseCallback().onError();
          }
        });
  }

  /**
   * The type Request values.
   */
  public static final class RequestValues implements UseCase.RequestValues {
    private final MasterpassConfirmationObject masterpassConfirmationObject;

    /**
     * Instantiates a new Request values.
     *
     * @param masterpassConfirmationObject the masterpass confirmation object
     */
    public RequestValues(MasterpassConfirmationObject masterpassConfirmationObject) {
      this.masterpassConfirmationObject = masterpassConfirmationObject;
    }

    /**
     * Gets masterpass confirmation object.
     *
     * @return the masterpass confirmation object
     */
    public MasterpassConfirmationObject getMasterpassConfirmationObject() {
      return masterpassConfirmationObject;
    }
  }

  /**
   * The type Response value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {
    private final MasterpassConfirmationObject mMasterpassConfirmationObject;

    /**
     * Instantiates a new Response value.
     *
     * @param masterpassConfirmationObject the masterpass confirmation object
     */
    public ResponseValue(MasterpassConfirmationObject masterpassConfirmationObject) {
      mMasterpassConfirmationObject = masterpassConfirmationObject;
    }

    /**
     * Gets masterpass confirmation object.
     *
     * @return the masterpass confirmation object
     */
    public MasterpassConfirmationObject getmMasterpassConfirmationObject() {
      return mMasterpassConfirmationObject;
    }
  }
}