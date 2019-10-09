package com.mastercard.testapp.domain.usecase.masterpass;

import android.support.annotation.NonNull;
import com.mastercard.testapp.data.external.MasterpassDataSource;
import com.mastercard.testapp.data.external.MasterpassExternalDataSource;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import java.util.HashMap;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Get pairing id if is necessary
 */
public class GetPairingIdUseCase
    extends UseCase<GetPairingIdUseCase.RequestValues, GetPairingIdUseCase.ResponseValue> {

  private final MasterpassExternalDataSource mMasterpassExternal;

  /**
   * Instantiates a new Get pairing id use case.
   *
   * @param itemsRepository the items repository
   */
  public GetPairingIdUseCase(@NonNull MasterpassExternalDataSource itemsRepository) {
    mMasterpassExternal = checkNotNull(itemsRepository, "HANDLE CONNECTION WITH MS");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    mMasterpassExternal.getPairingId(values.checkoutData,
        new MasterpassDataSource.PairingIdCallback() {
          @Override public void onPairing() {
            getUseCaseCallback().onSuccess(null);
          }

          @Override public void onPairingError() {
            getUseCaseCallback().onError();
          }
        });
  }

  /**
   * The type Request values.
   */
  public static final class RequestValues implements UseCase.RequestValues {
    private final HashMap<String, Object> checkoutData;

    /**
     * Instantiates a new Request values.
     *
     * @param checkoutData the checkout data
     */
    public RequestValues(HashMap<String, Object> checkoutData) {
      this.checkoutData = checkoutData;
    }

    /**
     * Gets checkout data.
     *
     * @return the checkout data
     */
    public HashMap<String, Object> getCheckoutData() {
      return checkoutData;
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