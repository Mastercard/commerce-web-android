package com.mastercard.mp.switchservices.sessionKeySigning;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * SessionKeySigningResponse contains appId, appVersion, appSigningPublicKey and signature signed by switch during merchant context initialization
 */
@Root(name = "SessionKeySigningResponse") public class SessionKeySigningResponse {

    @Element(name = "AppId") private String appId;
    @Element(name = "AppVersion") private String appVersion;
    @Element(name = "AppSigningPublicKey") private String appSigningPublicKey;
    @Element(name = "SessionSignature") private String sessionSignature;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppSigningPublicKey() {
        return appSigningPublicKey;
    }

    public void setAppSigningPublicKey(String appSigningPublicKey) {
        this.appSigningPublicKey = appSigningPublicKey;
    }

    public String getSessionSignature() {
        return sessionSignature;
    }

    public void setSessionSignature(String sessionSignature) {
        this.sessionSignature = sessionSignature;
    }
}
