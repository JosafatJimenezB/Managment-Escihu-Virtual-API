package com.escihu.apiescihuvirtual.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
/**
 * RSAKeyProperties is a component that holds the public and private keys of an RSA key pair.
 * It uses the KeyGeneratorUtility to generate the key pair.
 */
@Component
@Getter
@Setter
public class RSAKeyProperties {
    /**
     * The public key of the RSA key pair.
     */
    private RSAPublicKey publicKey;
    /**
     * The private key of the RSA key pair.
     */
    private RSAPrivateKey privateKey;

    /**
     * Constructs a new RSAKeyProperties.
     * It generates an RSA key pair and assigns the public and private keys.
     */
    public RSAKeyProperties() {
        KeyPair pair = KeyGeneratorUtility.generateRsaKey();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }

}
