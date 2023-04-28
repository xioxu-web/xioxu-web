package com.example.demo.util;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 *签名处理工具类
 * @author xiaoxu123
 */
public class MyRSAUtils {

    public static final String CHARSET = "utf-8";

    /**
     * RSA私钥签名
     * @param src 客户端传过来的原始参数
     * @param priKey 客户端私钥
     * @return
     * @throws Exception
     */
    public static String sign(String src, String priKey){
        try {
            KeyFactory rsa = KeyFactory.getInstance( "RSA" );
            byte[] pribyte = Base64.getDecoder().decode( priKey );
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec( pribyte );
            RSAPrivateKey privateKey = (RSAPrivateKey) rsa.generatePrivate(keySpec);
            Signature sign= Signature.getInstance("SHA1withRSA");
            sign.initSign(privateKey);
            sign.update(src.getBytes(MyRSAUtils.CHARSET));
            byte[] signature=sign.sign();
            return Base64.getEncoder().encodeToString(signature);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
