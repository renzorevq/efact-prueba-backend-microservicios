package com.renzorevilla.ms_documentos.services;

import com.renzorevilla.ms_documentos.config.CryptoConstants;
import com.renzorevilla.ms_documentos.models.Documento;
import com.renzorevilla.ms_documentos.models.ValidacionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class ValidacionService {

    @Value("${app.key.private}")
    private String llavePrivada;

    public ValidacionResponse verificarValidez(Documento documento, String firma) {
        try {
            SecretKey llave = obtenerLlave(llavePrivada);

            byte[] paquete = Base64.getDecoder().decode(firma);

            byte[] iv = new byte[CryptoConstants.IV_LENGTH];
            byte[] cifrado = new byte[paquete.length - CryptoConstants.IV_LENGTH];

            System.arraycopy(paquete, 0, iv, 0, iv.length);
            System.arraycopy(paquete, CryptoConstants.IV_LENGTH, cifrado, 0, cifrado.length);

            Cipher cipher = Cipher.getInstance(CryptoConstants.CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, llave, new GCMParameterSpec(CryptoConstants.TAG_LENGTH_BITS, iv));

            documento.getValidacion().setFirma(null);

            var hashFirmado = cipher.doFinal(cifrado);
            byte[] hashActual = obtenerHash(obtenerBytes(documento));

            boolean esValido = MessageDigest.isEqual(hashFirmado, hashActual);

            return new ValidacionResponse(esValido);
        } catch (Exception e) {
            return new ValidacionResponse(false);
        }
    }

    private SecretKey obtenerLlave(String llavePrivada) {
        try{
            byte[] llaveBytes = obtenerBytes(llavePrivada);
            byte[] hash = obtenerHash(llaveBytes);
            return new SecretKeySpec(hash, CryptoConstants.SECRET_KEY_AES_ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private byte[] obtenerBytes(String cadena) {
        try {
            return cadena.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private byte[] obtenerBytes(Documento documento) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsBytes(documento);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private byte[] obtenerHash(byte[] documentoBytes) {
        try {
            MessageDigest md = MessageDigest.getInstance(CryptoConstants.MESSAGE_DIGEST_SHA_256_ALGORITHM);
            return md.digest(documentoBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
