package com.us.masterpass.merchantapp.domain.masterpass;

import com.mastercard.mp.checkout.MasterpassError;

import java.util.HashMap;

/**
 * Created by Sebastian Farias on 11/13/17.
 */
public interface MasterpassUICallback {

    /**
     * On sdk checkout complete.
     *
     * @param parameters the parameters
     */
    void onSDKCheckoutComplete(HashMap<String, Object> parameters);

    /**
     * On sdk checkout error.
     * @param masterpassError
     */
    void onSDKCheckoutError(MasterpassError masterpassError);

}
