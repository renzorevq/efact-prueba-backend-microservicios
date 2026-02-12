package com.renzorevilla.ms_validacion_firma.services;

import com.renzorevilla.ms_validacion_firma.config.CryptoConstants;
import com.renzorevilla.ms_validacion_firma.models.Documento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class FirmaService {

    @Value("${app.key.private}")
    private String llavePrivada;

    public Documento firmarDocumento(Documento documento){

        try {
            String firma = crearFirma(documento);
            documento.getValidacion().setFirma(firma);

            return documento;
        } catch (Exception e) {
            throw new RuntimeException("No se logro firmar el documento:  " + e.getMessage());
        }
    }

    private String crearFirma(Documento documento) {

        try {
            byte[] bytes = obtenerBytes(documento);
            byte[] hash = obtenerHash(bytes);

            return encriptar(hash, llavePrivada);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear firma: " + e.getMessage());
        }

    }

    private String encriptar(byte[] hash, String llavePrivada) {
        try {
            SecretKey llave = obtenerLlave(llavePrivada);

            byte[] iv = new byte[CryptoConstants.IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(CryptoConstants.CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, llave, new GCMParameterSpec(CryptoConstants.TAG_LENGTH_BITS, iv));

            byte[] encriptado = cipher.doFinal(hash);

            byte[] paquete = new byte[iv.length + encriptado.length];

            System.arraycopy(iv, 0, paquete, 0, iv.length);
            System.arraycopy(encriptado, 0, paquete, iv.length, encriptado.length);

            return Base64.getEncoder().encodeToString(paquete);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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

    private byte[] obtenerBytes(Documento documento) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsBytes(documento);
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

    private byte[] obtenerHash(byte[] documentoBytes) {
        try {
            MessageDigest md = MessageDigest.getInstance(CryptoConstants.MESSAGE_DIGEST_SHA_256_ALGORITHM);
            return md.digest(documentoBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
