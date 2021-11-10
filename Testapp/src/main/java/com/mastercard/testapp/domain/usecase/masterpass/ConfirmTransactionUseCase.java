package com.mastercard.testapp.domain.usecase.masterpass;

import androidx.annotation.NonNull;
import com.mastercard.testapp.data.external.MasterpassDataSource;
import com.mastercard.testapp.data.external.MasterpassExternalDataSource;
import com.mastercard.testapp.domain.model.MasterpassConfirmationObject;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import java.util.HashMap;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Confirm transaction on server
 */
public class ConfirmTransactionUseCase extends
    UseCase<ConfirmTransactionUseCase.RequestValues, ConfirmTransactionUseCase.ResponseValue> {

  private final MasterpassExternalDataSource mMasterpassExternal;

  /**
   * Instantiates a new Confirm transaction use case.
   *
   * @param itemsRepository the items repository
   */
  public ConfirmTransactionUseCase(@NonNull MasterpassExternalDataSource itemsRepository) {
    mMasterpassExternal = checkNotNull(itemsRepository, "HANDLE CONNECTION WITH MS");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    mMasterpassExternal.getDataConfirmation(values.checkoutData, values.isExpressCheckoutEnable(),
        new MasterpassDataSource.LoadDataConfirmationCallback() {
          @Override
          public void onDataConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
              boolean expressCheckoutEnable) {
            if (values.checkoutData != null) {
              StringBuffer buf =
                  new StringBuffer(masterpassConfirmationObject.getCardAccountNumberHidden());
              if (masterpassConfirmationObject.getCardAccountNumberHidden().length() > 4) {
                int start = 0;
                int end = masterpassConfirmationObject.getCardAccountNumberHidden().length() - 4;
                buf.replace(start, end, "**** **** **** ");
              }
              masterpassConfirmationObject.setCardAccountNumberHidden(buf.toString());
            }
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
    private final HashMap<String, Object> checkoutData;
    private final boolean expressCheckoutEnable;

    /**
     * Instantiates a new Request values.
     *
     * @param checkoutData the checkout data
     * @param expressCheckoutEnable the express checkout enable
     */
    public RequestValues(HashMap<String, Object> checkoutData, boolean expressCheckoutEnable) {
      this.checkoutData = checkoutData;
      this.expressCheckoutEnable = expressCheckoutEnable;
    }

    /**
     * Gets checkout data.
     *
     * @return the checkout data
     */
    public HashMap<String, Object> getCheckoutData() {
      return checkoutData;
    }

    /**
     * Is express checkout enable boolean.
     *
     * @return the boolean
     */
    public boolean isExpressCheckoutEnable() {
      return expressCheckoutEnable;
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