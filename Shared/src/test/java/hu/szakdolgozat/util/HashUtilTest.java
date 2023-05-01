package hu.szakdolgozat.util;

import hu.szakdolgozat.util.HashUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashUtilTest {

    @Test
    void testEncrypt() {
        String originalText = "This is a test string.";
        String encryptedText = HashUtil.encrypt(originalText);

        assertNotNull(encryptedText);
        assertNotEquals(originalText, encryptedText);
    }

    @Test
    void testEncryptUresString() {
        String originalText = "";
        String encryptedText = HashUtil.encrypt(originalText);

        assertNotNull(encryptedText);
        assertNotEquals(originalText, encryptedText);
    }

    @Test
    void testEncryptEllenorzes() {
        String originalText1 = "Another test string.";
        String originalText2 = "Another test string.";

        String encryptedText1 = HashUtil.encrypt(originalText1);
        String encryptedText2 = HashUtil.encrypt(originalText2);

        assertEquals(encryptedText1, encryptedText2);
    }
}
