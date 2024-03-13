package com.escihu.apiescihuvirtual.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for generating RSA key pairs.
 */
public class KeyGeneratorUtility {

    /**
     * Generates a RSA key pair.
     *
     * @return a RSA key pair
     * @throws RuntimeException if the RSA algorithm is not available
     */
    public static KeyPair generateRsaKey() {

        KeyPair keyPair;

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO: Create a custom exception
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();

        return keyPair;
    }
}