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
public class GetPairingIdUseCase extends UseCase<GetPairingIdUseCase.RequestValues, GetPairingIdUseCase.ResponseValue> {

    private final MasterpassExternalDataSource mMasterpassExternal;

    /**
     * Instantiates a new Get pairing id use case.
     *
     * @param itemsRepository the items repository
     */
    public GetPairingIdUseCase(@NonNull MasterpassExternalDataSource itemsRepository) {
        mMasterpassExternal = checkNotNull(itemsRepository, "HANDLE CONNECTION WITH MS");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        mMasterpassExternal.getPairingId(values.checkoutData,
                new MasterpassDataSource.PairingIdCallback() {
                    @Override
                    public void onPairing() {
                        getUseCaseCallback().onSuccess(null);
                    }

                    @Override
                    public void onPairingError() {
                        getUseCaseCallback().onError();
                    }
        });
    }

    /**
     * The type Request values.
     */
    public static final class RequestValues implements UseCase.RequestValues {
        private final Map<String, Object> checkoutData;

        /**
         * Instantiates a new Request values.
         *
         * @param checkoutData the checkout data
         */
        public RequestValues(Map<String, Object> checkoutData) {
            this.checkoutData = checkoutData;
        }

        /**
         * Gets checkout data.
         *
         * @return the checkout data
         */
        public Map<String, Object> getCheckoutData() {
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