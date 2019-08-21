package com.mastercard.mp.switchservices;

import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthRsaSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthUtil;
import com.google.gdata.util.common.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to assist with generating OAuth headers for switch requests
 */

class MasterpassOAuthUtil {
  private static final String OAUTH_VERSION = "1.0";
  private static final String SIGNATURE_METHOD = "RSA-SHA1";
  private static final String TAG = MasterpassOAuthUtil.class.getSimpleName();
  private static final Logger logger = Logger.getLogger(TAG);

  private MasterpassOAuthUtil() {
    //Private constructor to prevent instantiation of this class
  }

  private static OAuthParameters generateOAuthParams() {
    OAuthParameters params = new OAuthParameters();
    params.setOAuthNonce(OAuthUtil.getNonce());
    params.setOAuthTimestamp(OAuthUtil.getTimestamp());
    params.setOAuthSignatureMethod(SIGNATURE_METHOD);
    params.setOAuthType(OAuthParameters.OAuthType.THREE_LEGGED_OAUTH);
    params.addCustomBaseParameter("oauth_version", OAUTH_VERSION);

    return params;
  }

  static Map<String, String> generateOAuthHeaderParams(PrivateKey privateKey, String request,
      String url, String clientId, String serviceName, String httpMethod) throws OAuthException {
    try {
      OAuthParameters params = generateOAuthParams();
      params.setOAuthConsumerKey(clientId);
      OAuthRsaSha1Signer signer = new OAuthRsaSha1Signer(privateKey);

      if (request != null) {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hash = digest.digest(request.getBytes("UTF-8"));
        String encodedHash = Base64.encode(hash);
        params.addCustomBaseParameter("oauth_body_hash", encodedHash);

        digest.reset();
      }

      String baseString = OAuthUtil.getSignatureBaseString(url, httpMethod, params.getBaseParameters());
      String signature = signer.getSignature(baseString, params);

      params.addCustomBaseParameter("oauth_signature", signature);

      return new HashMap<>(params.getBaseParameters());
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
    }

    return null;
  }

  static String generateOAuthHeader(PrivateKey privateKey, String request, String url,
      String clientId, String serviceName, String httpMethod) throws OAuthException {
    return buildOAuthHeaderString(
        generateOAuthHeaderParams(privateKey, request, url, clientId, serviceName, httpMethod));
  }

  private static String buildOAuthHeaderString(Map<String, String> params) {
    StringBuilder headerBuilder = new StringBuilder();
    headerBuilder.append("OAuth ");

    String delimiter = "";
    for (String paramKey : params.keySet()) {
      headerBuilder.append(delimiter)
          .append(paramKey)
          .append("=\"")
          .append(OAuthUtil.encode(params.get(paramKey)))
          .append("\"");
      delimiter = ",";
    }

    String headerString = headerBuilder.toString();
    logger.log(Level.FINE, headerString);

    return headerString;
  }
}
