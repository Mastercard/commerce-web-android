package com.us.masterpass.merchantapp.domain.masterpass;

import android.content.Context;
import android.content.res.Configuration;
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import java.security.SecureRandom;

/**
 * Class to handle the SDK calls and handle callbacks and communication between presenters and two
 * specific fragment that need to listen with a callback to receive new data.
 * {@link com.us.masterpass.merchantapp.presentation.fragment.CartFragment}
 *
 * Created by Sebastian Farias on 13-10-17.
 */
public class MasterpassSdkCoordinator {

    private static MasterpassSdkCoordinator sMasterpassSdkCoordinator;
    private static Context mContext;
    private static String generatedCartId;
    private static MasterpassUICallback sMasterpassUICallback;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MasterpassSdkCoordinator getInstance() {
        if (sMasterpassSdkCoordinator == null) {
            synchronized (MasterpassSdkCoordinator.class) {
                sMasterpassSdkCoordinator = new MasterpassSdkCoordinator();
            }
        }
        return sMasterpassSdkCoordinator;
    }

    /**
     * Create random string to set to the Cart Id
     *
     * @return cart id to send to the SDK
     */
    private String generateCartId() {
        SecureRandom rnd = new SecureRandom();
        String generateCartUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String generateCartDigits = "0123456789";
        int generateCartLength = 6;

        String alphanumeric = generateCartUpper + generateCartDigits;
        StringBuilder sb = new StringBuilder(generateCartLength);
        for (int i = 0; i < generateCartLength; i++) {
            sb.append(alphanumeric.charAt(rnd.nextInt(alphanumeric.length())));
        }
        generatedCartId = sb.toString();
        return sb.toString();
    }

    /**
     * Gets generated cart id.
     *
     * @return the generated cart id
     */
    public static String getGeneratedCartId() {
        return generatedCartId;
    }

    /**
     * Add fragment listener.
     *
     * @param masterpassUICallback the masterpass ui callback
     */
    public void addFragmentListener(MasterpassUICallback masterpassUICallback) {
        if (sMasterpassUICallback == null) {
            synchronized (MasterpassUICallback.class) {
                sMasterpassUICallback = masterpassUICallback;
            }
        }
    }

    /**
     * Remove fragment listener.
     *
     * @param masterpassUICallback the masterpass ui callback
     */
    public void removeFragmentListener(MasterpassUICallback masterpassUICallback) {
        synchronized (MasterpassUICallback.class) {
            sMasterpassUICallback = null;
        }
    }

}
