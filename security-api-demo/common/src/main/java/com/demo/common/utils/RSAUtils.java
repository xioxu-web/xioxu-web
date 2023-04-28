package com.demo.common.utils;

import com.demo.common.excepiton.RSAException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxu123
 */
@Slf4j
public class RSAUtils {

    public static final String PUBLIC_KEY = "public_key";

    public static final String PRIVATE_KEY = "private_key";

    public static Map<String, String> generateRasKey(){
        HashMap<String, String> rsa = new HashMap<>();
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen=null;
        try {
            keyPairGen= KeyPairGenerator.getInstance( "RSA" );
            keyPairGen.initialize(1024,new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥 公钥
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded() ));
            // 得到私钥字符串
            String privateKeyString  = new String(Base64.encodeBase64(privateKey.getEncoded() ));
            // 将公钥和私钥保存到Map
            rsa.put(PUBLIC_KEY,publicKeyString);
            rsa.put(PRIVATE_KEY,privateKeyString);
        } catch (NoSuchAlgorithmException e) {
            log.error("RsaUtils invoke genKeyPair failed.", e);
            throw new RSAException("RsaUtils invoke genKeyPair failed.");
        }
        return rsa;
    }

    public static String encrypt(String str, String publicKey) {
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RSAException("RsaUtils invoke encrypt failed.");
        }
    }

    public static String decrypt(String str, String privateKey) {

        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            throw new RSAException("RsaUtils invoke decrypt failed.");
        }

    }

}
