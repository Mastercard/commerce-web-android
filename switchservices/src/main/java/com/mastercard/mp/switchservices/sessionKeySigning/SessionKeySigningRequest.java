package com.mastercard.mp.switchservices.sessionKeySigning;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Default object with common fields between request and response checkout messages.
 */
@Root(name = "SessionKeySigningRequest") public class SessionKeySigningRequest {
    @Element(name = "AppId") private String appId;
    @Element(name = "AppVersion") private String appVersion;
    @Element(name = "AppSigningPublicKey") private String appSigningPublicKey;

    /**
     * @return id from wallet application used for transaction.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter for wallet application id.
     *
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return wallet application version.
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * Setter for wallet application version.
     *
     * @param walletAppVersion
     */
    public void setAppVersion(String walletAppVersion) {
        this.appVersion = walletAppVersion;
    }

    /**
     * @return the public key used by wallet to sign messages.
     */
    public String getAppSigningPublicKey() {
        return appSigningPublicKey;
    }

    /**
     * Setter for wallet application signing public key.
     *
     * @param appSigningPublicKey
     */
    public void setAppSigningPublicKey(String appSigningPublicKey) {
        this.appSigningPublicKey = appSigningPublicKey;
    }
}
