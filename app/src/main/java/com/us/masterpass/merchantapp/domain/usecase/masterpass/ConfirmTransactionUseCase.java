package com.us.masterpass.merchantapp.domain.usecase.masterpass;

import androidx.annotation.NonNull;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import java.util.Map;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class ConfirmTransactionUseCase extends UseCase<ConfirmTransactionUseCase.RequestValues, ConfirmTransactionUseCase.ResponseValue> {

    private final MasterpassExternalDataSource mMasterpassExternal;

    /**
     * Instantiates a new Confirm transaction use case.
     *
     * @param itemsRepository the items repository
     */
    public ConfirmTransactionUseCase(@NonNull MasterpassExternalDataSource itemsRepository) {
        mMasterpassExternal = checkNotNull(itemsRepository, "HANDLE CONNECTION WITH MS");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        mMasterpassExternal.getDataConfirmation(values.checkoutData,
                values.isExpressCheckoutEnable(),
                new MasterpassDataSource.LoadDataConfirmationCallback() {
            @Override
            public void onDataConfirmation(MasterpassConfirmationObject masterpassConfirmationObject,
                    boolean expressCheckoutEnable) {
                if (values.checkoutData != null) {
                    StringBuilder buf = new StringBuilder(masterpassConfirmationObject.getCardAccountNumberHidden());
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

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    /**
     * The type Request values.
     */
    public static final class RequestValues implements UseCase.RequestValues {
        private final Map<String, Object> checkoutData;
        private final boolean expressCheckoutEnable;

        /**
         * Instantiates a new Request values.
         *
         * @param checkoutData          the checkout data
         * @param expressCheckoutEnable the express checkout enable
         */
        public RequestValues(Map<String, Object> checkoutData, boolean expressCheckoutEnable) {
            this.checkoutData = checkoutData;
            this.expressCheckoutEnable = expressCheckoutEnable;
        }

        /**
         * Gets checkout data.
         *
         * @return the checkout data
         */
        public Map<String, Object> getCheckoutData() {
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