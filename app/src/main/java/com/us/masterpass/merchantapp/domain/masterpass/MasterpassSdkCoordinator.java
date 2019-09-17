package com.us.masterpass.merchantapp.domain.masterpass;

import android.content.Context;
import android.text.TextUtils;

import com.us.masterpass.merchantapp.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Class to handle the SDK calls and handle callbacks and communication between presenters and two
 * specific fragment that need to listen with a callback to receive new data.
 *
 *
 * Created by Sebastian Farias on 13-10-17.
 */
public class MasterpassSdkCoordinator {

    private static MasterpassSdkCoordinator sMasterpassSdkCoordinator;
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
    private static String generateCartId() {
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

        return generatedCartId;
    }

    /**
     * Gets generated cart id.
     *
     * @return the generated cart id
     */
    public static String getGeneratedCartId() {
        if ( !TextUtils.isEmpty(generatedCartId)) {
            return generatedCartId;
        }

        return generateCartId();
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

    public static PrivateKey getPrivateKey(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream keyStoreInputStream =
                    context.getAssets().open(BuildConfig.MERCHANT_P12_CERTIFICATE);
            keyStore.load(keyStoreInputStream, BuildConfig.PASSWORD.toCharArray());
            return (PrivateKey) keyStore.getKey(BuildConfig.KEY_ALIAS,
                    BuildConfig.PASSWORD.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        return null;
    }
}