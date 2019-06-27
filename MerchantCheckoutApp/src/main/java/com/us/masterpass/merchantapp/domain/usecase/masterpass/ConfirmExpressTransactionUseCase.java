package com.us.masterpass.merchantapp.domain.usecase.masterpass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.mastercard.mp.switchservices.checkout.ExpressCheckoutRequest;
import com.us.masterpass.merchantapp.BuildConfig;
import com.us.masterpass.merchantapp.data.external.MasterpassDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.model.MasterpassConfirmationObject;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Confirm express checkout
 */
public class ConfirmExpressTransactionUseCase extends UseCase<ConfirmExpressTransactionUseCase.RequestValues, ConfirmExpressTransactionUseCase.ResponseValue> {

    private final MasterpassExternalDataSource mMasterpassExternal;
    private final Context mContext;

    /**
     * Instantiates a new Confirm express transaction use case.
     *
     * @param itemsRepository the items repository
     * @param context         the context
     */
    public ConfirmExpressTransactionUseCase(@NonNull MasterpassExternalDataSource itemsRepository,
                                            @NonNull Context context) {
        mMasterpassExternal = checkNotNull(itemsRepository, "HANDLE CONNECTION WITH MS");
        mContext = checkNotNull(context, "TO GET LOCAL DATA");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        mMasterpassExternal.expressCheckout(values.expressCheckoutRequest,
            new MasterpassDataSource.LoadDataConfirmationCallback() {
            @Override
            public void onDataConfirmation(MasterpassConfirmationObject masterpassConfirmationObject, boolean expressCheckoutEnable) {
                StringBuffer buf = new StringBuffer(masterpassConfirmationObject.getCardAccountNumberHidden());
                if (masterpassConfirmationObject.getCardAccountNumberHidden().length() > 4) {
                    int start = 0;
                    int end = masterpassConfirmationObject.getCardAccountNumberHidden().length() - 4;
                    buf.replace(start, end, "**** **** **** ");
                }
                masterpassConfirmationObject.setCardAccountNumberHidden(buf.toString());

                ResponseValue responseValue = new ResponseValue(masterpassConfirmationObject);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
            }, getPublicKey());
    }

    private PrivateKey getPublicKey() {
        PrivateKey privateKey = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream keyStoreInputStream =
                mContext.getAssets().open(BuildConfig.MERCHANT_P12_CERTIFICATE);
            keyStore.load(keyStoreInputStream, BuildConfig.PASSWORD.toCharArray());
            return (PrivateKey) keyStore.getKey(BuildConfig.KEY_ALIAS,
                BuildConfig.PASSWORD.toCharArray());
        } catch (Exception e) {
            Log.d("CartFragment", e.toString());
        }
        return null;
    }

    /**
     * The type Request values.
     */
    public static final class RequestValues implements UseCase.RequestValues {
        private final ExpressCheckoutRequest expressCheckoutRequest;

        /**
         * Instantiates a new Request values.
         *
         * @param expressCheckoutRequestObject the masterpass confirmation object
         */
        public RequestValues(ExpressCheckoutRequest expressCheckoutRequestObject) {
            this.expressCheckoutRequest = expressCheckoutRequestObject;
        }

        /**
         * Gets masterpass confirmation object.
         *
         * @return the masterpass confirmation object
         */
        public ExpressCheckoutRequest getExpressCheckoutObject() {
            return expressCheckoutRequest;
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