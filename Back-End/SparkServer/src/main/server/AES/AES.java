package main.server.AES;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES class: Provides the functionality to encrypt and decrypt passwords and messages in-app.
 * Currently uses AES SHA-512 but can be changed to adapt to any AES encryption standard easily.
 */
public class AES {

  private static SecretKeySpec secretKey;
  private static byte[] key;

  /**
   * setKey: Takes param String and utilizes it to set the secret key for hashing in the algorithm
   *
   * @param myKey
   */
  public static void setKey(String myKey) {

    MessageDigest sha = null;

    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-512");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");

    } catch (NoSuchAlgorithmException algoEx) {
      algoEx.printStackTrace();
    } catch (UnsupportedEncodingException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * encrypt: Takes the input String and secretKey to hash to SHA-512
   *
   * @param str
   * @param secret
   * @return Upon success, the returned String object is the hashed version of the input String
   *     object
   */
  public static String encrypt(String str, String secret) {

    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));

    } catch (Exception ex) {
      System.out.println("Error while encrypting: " + ex.toString());
    }
    return null;
  }

  /**
   * decrypt: Takes the input String and secretKey to decrypt the previously hashed String object
   *
   * @param str
   * @param secret
   * @return Should give back the original String object, as long as the secret is identical to the
   *     one that was used to encrypt
   */
  public static String decrypt(String str, String secret) {

    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(str)));

    } catch (Exception ex) {
      System.out.println("Error while decrypting: " + ex.toString());
    }
    return null;
  }

  /**
   * Basic Unit test to check for proper encryption and decryption Changing either the secretKey or
   * originalString should not result in a collision in the hashing.
   *
   * @param args
   */
  public static void main(String[] args) {
    final String secretKey = "passwordEncryption";

    String originalString = "test";
    String encryptedString = AES.encrypt(originalString, secretKey);
    String decryptedString = AES.decrypt(encryptedString, secretKey);

    System.out.println(originalString);
    System.out.println(encryptedString);
    System.out.println(decryptedString);
  }
}
