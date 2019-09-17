package com.mastercard.mp.switchservices;

import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * A set of utility function for convenient access to certain cryptographic operations.
 */
public class CryptoUtil {
  public static final int ENCRYPTED_MESSAGE = 0;
  public static final int ENCRYPTED_AES_KEY = 1;
  public static final int INITIALIZATION_VECTOR = 2;
  private static final String PRIVATE_KEY_STORE_FILE_NAME =
      "/res/raw/mock_switch_key_store_bks.bks";
  private static final String KEY_STORE_NOT_PW = "mcpassword";
  private static final String SWITCH_KEY_ALIAS = "domain";
  private static final String SHA256 = "SHA-256";
  private static final String SHA = "SHA";
  private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
  private static final String KEY_PAIR_ALGORITHM = "RSA";
  private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
  private static final String SECRET_KEY_TYPE = "AES";
  private static final String SESSION_PUBLIC_KEY_TYPE = "X.509";
  private static final int IV_BYTES_LENGTH = 16;
  private static final int AES_BYTES_LENGTH = 128;
  private static final int KEY_SIZE = 2048;
  private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
  private static final String TAG = "MasterPass";

  private static KeyPair sessionKeyPair;
  private static KeyPair appKeyPair;

  /**
   * Hashes the message and encodes it in Base64. Uses SHA256 to generate the hash.
   *
   * @return hash encoded in Base64.
   */
  public static String getSHA256Hash(byte[] message) {
    try {
      byte[] hash = MessageDigest.getInstance(SHA256).digest(message);
      return bytesToHex(hash);
    } catch (Exception e) {
      e.printStackTrace();
      // Ignored as algorithm is a constant and not expected as parameter. This exception will never be thrown.
      return null;
    }
  }

  /**
   * Hashes the message and encodes it in Base64. Uses SHA to generate the hash.
   *
   * @return hash encoded in Base64.
   */
  public static String getSHAHash(byte[] message) {
    try {
      byte[] hash = MessageDigest.getInstance(SHA).digest(message);
      return bytesToHex(hash);
    } catch (Exception e) {
      e.printStackTrace();
      // Ignored as algorithm is a constant and not expected as parameter. This exception will never be thrown.
      return null;
    }
  }

  public static void verifySignatureWithPublicKey(String data, String publicKeyString,
      String signature) {
    boolean isVerified;
    try {
      PublicKey publicKey = stringToPublicKey(publicKeyString);
      byte[] dataInBytes = data.getBytes();
      Signature signatureEngine = Signature.getInstance(SIGNATURE_ALGORITHM);
      signatureEngine.initVerify(publicKey);
      signatureEngine.update(dataInBytes);
      isVerified = signatureEngine.verify(Base64.decode(signature, Base64.DEFAULT));
    } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
    }
  }

  /**
   * convert a string to public key using RSA
   */
  public static PublicKey stringToPublicKey(String publicKeyString) {
    PublicKey pubKey = null;
    try {
      byte[] publicBytes = Base64.decode(publicKeyString, Base64.DEFAULT);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      pubKey = keyFactory.generatePublic(keySpec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
    }

    return pubKey;
  }

  /**
   * Generates a KeyPair for use in the current session. KeyPair should be generated for each
   * transaction.
   * Public key for this KeyPair is sent to Switch as part of Authorize Mobile Checkout process.
   *
   * @return KeyPair generated for this session.
   */
  public static KeyPair getSessionKeyPair() {

    if (sessionKeyPair == null) {
      try {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        sessionKeyPair = keyPairGenerator.generateKeyPair();
      } catch (NoSuchAlgorithmException e) {
        Log.e(TAG, e.getLocalizedMessage(), e);
      }
    }

    return sessionKeyPair;
  }

  /**
   * Generate a KeyPair for use in the merchant or wallet app. KeyPair should be generated for each
   * transaction.
   *
   * @return KeyPair generated for this session.
   */
  public static KeyPair getAppKeyPair() {

    if (appKeyPair == null) {
      try {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        appKeyPair = keyPairGenerator.generateKeyPair();
      } catch (NoSuchAlgorithmException e) {
        Log.e(TAG, e.getLocalizedMessage(), e);
      }
    }

    return appKeyPair;
  }

  /**
   * @return PublicKey associated with this session. The public key is sent to Switch during
   * Authorize Mobile Checkout process.
   */
  public static PublicKey getSwitchPublicKey(@Environments.Environment String environment) {
    String switchPublicKeyPath = "";
    if (environment.equals(Environments.STAGE)
        || environment.equals(Environments.STAGE1)
        || environment.equals(Environments.STAGE2)
        || environment.equals(Environments.STAGE3)) {
      switchPublicKeyPath = "/res/raw/stage2_public_key.cer";
    } else if (environment.equals(Environments.ITF)) {
      switchPublicKeyPath = "/res/raw/itf.cer";
    } else if (environment.equals(Environments.SANDBOX)) {
      switchPublicKeyPath = "/res/raw/sandbox_public_key.cer";
    } else if (environment.equals(Environments.PRODUCTION)) {
      switchPublicKeyPath = "/res/raw/prod_public_key.cer";
    } else if (environment.equals(Environments.INT)) {
      switchPublicKeyPath = "/res/raw/prod_public_key.cer";
    }
    return getPublicKeyWithResource(switchPublicKeyPath);
  }

  private static PublicKey getPublicKeyWithResource(String path) {
    PublicKey sessionPublicKey = null;
    InputStream stream = CryptoUtil.class.getResourceAsStream(path);

    try {
      CertificateFactory factory = CertificateFactory.getInstance(SESSION_PUBLIC_KEY_TYPE);
      X509Certificate certificate = (X509Certificate) factory.generateCertificate(stream);
      sessionPublicKey = certificate.getPublicKey();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sessionPublicKey;
  }

  /**
   * Sign the message with switch private key, for the use of mock local
   *
   * @param message given the message string
   */
  public static byte[] signWithSwitchPrivateKey(String message) {
    return signWithPrivateKey(message,
        getPrivateKey(PRIVATE_KEY_STORE_FILE_NAME, SWITCH_KEY_ALIAS, KEY_STORE_NOT_PW));
  }

  /**
   * @param message Message to be signed using the current session private key. Messages are signed
   * in order to verify authenticity.
   * @return Signed string message
   */
  public static byte[] signWithSessionPrivateKey(String message) {
    return signWithPrivateKey(message, getSessionKeyPair().getPrivate());
  }

  /**
   * Sign the message with private key
   */
  public static byte[] signWithPrivateKey(String message, PrivateKey key) {
    byte[] signedMessage = null;
    try {
      Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
      signature.initSign(key);
      signature.update(message.getBytes());
      signedMessage = signature.sign();
    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
    }
    return signedMessage;
  }

  /**
   * Use this method to retrieve the Private Key from the system.
   *
   * @param filename given the BKS file name
   * @return private key
   */
  public static PrivateKey getPrivateKey(String filename, String alias, String password) {

    PrivateKey privateKey = null;

    InputStream stream = CryptoUtil.class.getResourceAsStream(filename);

    try {
      String type = KeyStore.getDefaultType();
      KeyStore switchPrivateKeyStore = KeyStore.getInstance(type);
      switchPrivateKeyStore.load(stream, password.toCharArray());

      privateKey = (PrivateKey) switchPrivateKeyStore.getKey(alias, password.toCharArray());
    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
    }

    return privateKey;
  }

  /**
   * Generate AES encryption key
   *
   * @param keySize Size of the key to be generated
   * @return Generated SecretKey
   */
  public static SecretKey getAESKey(int keySize) {
    SecretKey secretKey = null;
    try {
      KeyGenerator keyGen = KeyGenerator.getInstance(SECRET_KEY_TYPE);
      keyGen.init(keySize);

      secretKey = keyGen.generateKey();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return secretKey;
  }

  public static SecretKey getAESKey() {
    return getAESKey(AES_BYTES_LENGTH);
  }

  /**
   * Generates secure random byte array
   *
   * @param bytes Size of the byte array
   * @return random secure byte array
   */
  public static byte[] generateIV(int bytes) {
    return new SecureRandom().generateSeed(bytes);
  }

  public static byte[] generateIV() {
    return generateIV(IV_BYTES_LENGTH);
  }

  /**
   * Encrypt the AES key with the session public key. This enables switch to verify communication
   * between wallet and switch.
   *
   * @param aesKey AES key to be encrypted
   * @param sessionPublicKey Session key used to encrypt the AES key
   * @return An encrypted Base64 encoded string representation of the encrypted AES key
   */
  public static String encryptSecretKey(SecretKey aesKey, PublicKey sessionPublicKey) {
    String encryptedSecretKeyString = null;

    try {
      Cipher myCipher = Cipher.getInstance(RSA_ALGORITHM);
      myCipher.init(Cipher.ENCRYPT_MODE, sessionPublicKey);
      byte[] encryptedSecretKey = myCipher.doFinal(aesKey.getEncoded());
      encryptedSecretKeyString = Base64.encodeToString(encryptedSecretKey, Base64.DEFAULT);
    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
    }

    return encryptedSecretKeyString;
  }

  /**
   * RSA decrypt an AES key
   *
   * @throws Exception
   */
  public static SecretKey decryptSecretKey(String encryptedSecretKey, PrivateKey privateKey)
      throws Exception {
    byte[] decodedBytes = Base64.decode(encryptedSecretKey, Base64.DEFAULT);
    Cipher myCipher = Cipher.getInstance(RSA_ALGORITHM);
    myCipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptedMessageByte = myCipher.doFinal(decodedBytes);
    return new SecretKeySpec(decryptedMessageByte, SECRET_KEY_TYPE);
  }

  /**
   * AES encrypt a message
   *
   * @param message Message to be encrypted
   * @param aesKey AES key used to encrypt the message
   * @param iv Iv byte array to enable encryption
   */
  public static String encryptMessage(String message, SecretKey aesKey, byte[] iv) {
    String encryptedMessage = null;
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
    try {
      Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
      byte[] encryptedMessageByte = aesCipher.doFinal(message.getBytes());
      encryptedMessageByte = Base64.encode(encryptedMessageByte, Base64.DEFAULT);

      encryptedMessage = new String(encryptedMessageByte);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return encryptedMessage;
  }

  /**
   * AES decrypt a message
   *
   * @throws Exception
   */
  public static String decryptMessage(String encryptedMessage, SecretKey aesKey, byte[] iv) {
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
    byte[] decodedMessageByte = Base64.decode(encryptedMessage, Base64.DEFAULT);
    String decryptedMessage = null;

    try {
      Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
      decryptedMessage = new String(aesCipher.doFinal(decodedMessageByte));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return decryptedMessage;
  }

  /**
   * Convert a byte array into a hexadecimal encoded string
   *
   * @param bytes the byte array to be converted to hex string
   * @return Hex string representation of the byte array passed in
   */
  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }

  /**
   * Generate wallet app public key in PEM format that is used by MasterPass Switch
   */
  public static String getWalletAppPublicKeyInPEM() {
    PublicKey publicKey = getAppKeyPair().getPublic();
    String mod = new String(Hex.encodeHex(((RSAPublicKey) publicKey).getModulus().toByteArray()));

    String prefix = "3082010A02820101";
    String suffix = "0203010001";
    String hexKey = prefix + mod + suffix;

    String finalKey = "";
    try {
      byte[] chunked = org.apache.commons.codec.binary.Base64.encodeBase64Chunked(
          Hex.decodeHex(hexKey.toCharArray()));
      String temp = new String(chunked);
      finalKey = "-----BEGIN PUBLIC KEY-----\n" + temp + "-----END PUBLIC KEY-----";
    } catch (DecoderException e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
    }
    return finalKey;
  }

  /**
   * Returns a {@link SparseArray} composed of the following items:
   * <ul>
   * <li>Encrypted Message - can be retrieved using CryptoUtil.ENCRYPTED_MESSAGE as key. </li>
   * <li>Encrypted AES Key - can be retrieved using CryptoUtil.ENCRYPTED_AES_KEY</li>
   * <li>Initialization Vector (IV) - can be retrieved using CryptoUtil.INITIALIZATION_VECTOR</li>
   * </ul>
   *
   * @param message to be encrypted
   * @return encrypted message items
   */
  public static SparseArray<String> getEncryptedMessageItems(String message,
      @Environments.Environment String environment) {
    //Encrypt message
    SecretKey aesKey = CryptoUtil.getAESKey();
    byte[] iv = CryptoUtil.generateIV();
    String encryptedMessage = encryptMessage(message, aesKey, iv);

    //Encrypted AES key
    String encryptedAesKey =
        CryptoUtil.encryptSecretKey(aesKey, CryptoUtil.getSwitchPublicKey(environment));

    //IV String
    String initializationVector = Base64.encodeToString(iv, Base64.DEFAULT);

    //Encryption items array.
    SparseArray<String> encryptionItemsArray = new SparseArray<>(3);
    encryptionItemsArray.append(ENCRYPTED_MESSAGE, encryptedMessage);
    encryptionItemsArray.append(ENCRYPTED_AES_KEY, encryptedAesKey);
    encryptionItemsArray.append(INITIALIZATION_VECTOR, initializationVector);

    return encryptionItemsArray;
  }
}
