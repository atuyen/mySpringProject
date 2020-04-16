package com.seasolutions.stock_management.util;

import com.seasolutions.stock_management.model.exception.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;


@Log4j2
public class EncodeUtils {
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    // The following constants may be changed without breaking existing hashes.
    private static final int SALT_BYTE_SIZE = 24;
    private static final int HASH_BYTE_SIZE = 24;
    private static final int PBKDF2_ITERATIONS = 1200;
    private static final int ITERATION_INDEX = 0;
    private static final int SALT_INDEX = 1;
    private static final int PBKDF2_INDEX = 2;







    public  static  String encode(final String input){
        final char[] arrInput = input.toCharArray();

        // Generate a random salt
        final SecureRandom random = new SecureRandom();
        final byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        final byte[] hash = pbkdf2(arrInput, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }


    /**
     * compare a plane text with an encryptedText
     *
     * @param planeText    the planeText to check
     * @param encryptedText the hash of the valid password
     * @return true if the password is correct, false if not
     */
    public static boolean comparePlaneTextAndEncryptedText(final String planeText, final String encryptedText) {
        if (planeText == null || encryptedText == null) {
            return false;
        }
        return comparePlaneTextAndEncryptedText(planeText.toCharArray(), encryptedText) || encode(planeText) == encryptedText;
    }


    private static boolean comparePlaneTextAndEncryptedText(final char[] password, final String correctHash) {
        // Decode the hash into its parameters
        final String[] params = correctHash.split(":");
        final int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        final byte[] salt = fromHex(params[SALT_INDEX]);
        final byte[] hash = fromHex(params[PBKDF2_INDEX]);
        // Compute the hash of the provided password, using the same salt,
        // iteration count, and hash length
        final byte[] testHash = pbkdf2(password, salt, iterations, hash.length);

        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }


    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static   boolean slowEquals(final byte[] a, final byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }




    /**
     * Computes the PBKDF2 hash of a password.
     *
     * @param input   the plane text to encode.
     * @param salt       the salt
     * @param iterations the iteration count (slowness factor)
     * @param bytes      the length of the hash to compute in bytes
     * @return the PBDKF2 hash of the plane text
     */
    private static   byte[] pbkdf2(final char[] input, final byte[] salt, final int iterations, final int bytes) {
        try {
            final PBEKeySpec spec = new PBEKeySpec(input, salt, iterations, bytes * 8);
            final SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            log.error("Error creating password hash", ex);
            throw new InternalServerErrorException("Error creating password hash");
        }
    }


    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param array the byte array to convert
     * @return a length*2 character string encoding the byte array
     */
    private static   String toHex(final byte[] array) {
        final BigInteger bi = new BigInteger(1, array);
        final String hex = bi.toString(16);
        final int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param hex the hex string
     * @return the hex string decoded into a byte array
     */
    private static   byte[] fromHex(final String hex) {
        final byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }







}
