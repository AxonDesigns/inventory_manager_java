package com.sena.inventory_manager.services

/*import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64

@Service
class JwtUtilityService {
    fun loadPrivateKey(resource: Resource): PrivateKey{
        val keyBytes = Files.readAllBytes(Paths.get(resource.uri))
        val privateKeyPEM = String(keyBytes, StandardCharsets.UTF_8)
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace(Regex("\\s"), "")

        val decodedKey = Base64.getDecoder().decode(privateKeyPEM)
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(PKCS8EncodedKeySpec(decodedKey))
    }

    @Value("classpath:jwtKeys/public_key.pem")
    fun loadPublicKey(resource: Resource): PublicKey{
        val keyBytes = Files.readAllBytes(Paths.get(resource.uri))
        val publicKeyPEM = String(keyBytes, StandardCharsets.UTF_8)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace(Regex("\\s"), "")

        val decodedKey = Base64.getDecoder().decode(publicKeyPEM)
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(PKCS8EncodedKeySpec(decodedKey))

    }
}*/