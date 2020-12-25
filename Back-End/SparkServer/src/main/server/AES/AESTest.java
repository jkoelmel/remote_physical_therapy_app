package main.server.AES;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {

  private String secretKey = "TestKey";
  private String testInput = "Test message";
  private String encrypted = AES.encrypt(testInput, secretKey);

  @Test
  void encryptMessage() {
    assertNotEquals(testInput, encrypted, "Encryption failed");
  }

  @Test
  void decryptMessage() {
    String decrypted = AES.decrypt(encrypted, secretKey);
    assertEquals(testInput, decrypted, "Decryption failed");
  }

  @Test
  void failDecrypt() {
    String changedKey = "ChangedKey";
    assertEquals(null, AES.decrypt(encrypted, changedKey));
  }
}
