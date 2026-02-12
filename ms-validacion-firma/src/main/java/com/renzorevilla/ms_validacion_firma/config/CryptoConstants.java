package com.renzorevilla.ms_validacion_firma.config;

public class CryptoConstants {

    public static final int IV_LENGTH = 12;
    public static final int TAG_LENGTH_BITS = 128;
    public static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";
    public static final String SECRET_KEY_AES_ALGORITHM = "AES";
    public static final String MESSAGE_DIGEST_SHA_256_ALGORITHM = "SHA-256";

}
