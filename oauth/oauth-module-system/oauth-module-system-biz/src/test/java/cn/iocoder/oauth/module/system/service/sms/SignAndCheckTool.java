/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package cn.iocoder.oauth.module.system.service.sms;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * curl -XPOST https://open-na(sea).alipay.com/ams/api/pay/query \
 *     -H 'Content-Type: application/json; charset=UTF-8' \
 *     -H 'client-id: 112233445566' \
 *     -H 'request-time: 2019-01-01T12:08:56+08:00' \
 *     -H 'original_host: open-na.alipay.com' \
 *     -H 'signature: algorithm=RSA256,keyVersion=1,signature=crRox4Jn12cmpActYKQmlvHoEkGtSMuSI8JuHXPYCgdEMtEZXaL7mxBVHgmaDPsWx9mK5EYrEFF1adrM1H%2BN%2Fu6hFord3V5%2BuLv40T6jEEZ884S22omF04wKBk7ZpPU1B4CptxqY8kHBZM6ZIj0UdTBxqd4roYYfoBLcGmTmhcJkXdVzRhK3BP5vkuAeuwGDlZU2fBKTx2XZAAVFrhmbCrYWtl%2F6wnQh09AxUYWqVzJFwxu5tjbLAHE3jxFt6cyjrwPbbglmbP4H5sf3k3uUCmqs5F75b8xGY5Q4Z8stXsQEXyMYQDobyZFe59r9lZI93wvziQhEtpf%2Bw%2ByLojJxYg%3D%3D' \
 *     -d 'test_data'
 *
 * @author fayuan.fzw
 * @version : SignAndCheckTool.java, v 0.1 2020年06月26日 9:11 下午 fayuan.fzw Exp $
 */
public class SignAndCheckTool {
    /** name and value separator */
    private static final String NAME_VALUE_SEPARATOR      = "=";

    /** comma */
    private static final String COMMA                     = ",";

    /** algorithm */
    private static final String ALGORITHM                 = "algorithm";

    /** signature */
    private static final String SIGNATURE                 = "signature";

    /** keyVersion */
    private static final String KEY_VERSION               = "keyVersion";

    /** RSA256 */
    private static final String RSA_256                   = "RSA256";

    /** signPrivateKey */
    private static final String signPrivateKey            ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCE8DoZ2DVeubhwKxTz1Eo8fTtejvQCXqbByvvMYhKXAJuTfPRxhtLUKjQgAs6K8rNvlbOcil4p60TID2xXmzMrTQGB0OMHdN6eS2FxjAuPDVr4pyMRW3w1Z9TAeJg+HMs01wlLKyDrfV1iXu+yvi76wTiLKbbP+DzzKIVHwygwSNaqvJz/M/1SEMfUSXBhFSVXfTax3bUVDCHn4zsidBvqzzZJ3PodakUC5R9evCvEUGQSLYSQTUeDPhyxS01VX8yAMQ9fdhXY2SYVslgkaLwpsU97BodSkPbcuC7tHjLyeoWdG/TT2WZR0bUvZP5slEWvzHHVzLkXRh3Bn/ZsyWstAgMBAAECggEAdPdlN54rfzgLlu3hFpR/6O8LEKzY90jOko6FtBPKZYBMYp27+jN1RuYHTO8LSeg3hwOBsEdyiJGPyDMB0aclg4DSxaTETKhAw6hxiVV+ShSnKUbtrPUAuYCtCdhRo7zz8n8Nl0zQP0Ljqoz7fBqn/UuK8671QdEZ15ycgEKvFqOByxHVj1pdVd+ubU5xPDkWTqwsSkVjCB7NUm3Pq8dkNSBlnl/VF3AcnsvTg8tXpFhdgbX7yPRwpslDqInJaoN3JzIboR09+O3/EPvtFD4/ovQnRNON8LqBRgGiOcgK3BcWvc89l7wjftFiEyYhoyNj3wCHh/R5rpWTYMOJfNQJQQKBgQDq7Z1d5K9BCQCS3f3d6+IZpSLhi6PQJwwTRwUNr47TTrrjv7t8R0+Gw0hXDbEd6CjqYmW+W5i8vQkU8XFoKyEsDMnPYhwceHvEcEEdv04JfyvKDayXZ6EVoIfs/uRZrgvYtOn5vkHsJJ7p+PJgxR7xI9Ckr5KQpinxPoKwHdlq0QKBgQCQ3Lzu9rfOMrW8UPqUCi+lpRpd//i98GBKycl3Zp6xuTj8VSj7xwNuPRa5KVhSwB5AIstr37Qxb3Zf5fjQC2hkNiYvItAzMmW8m+yBnlaH4YG9+7CTqEm3AymTLpy05KciaPl2imBT4X/g/IuyLn0Ad9gGWMok8pbqVGfjwtyZnQKBgQDEME88/B9HRodOH0lErRqSpA+vH3hb+l/hMWwM89FosdTtIU8nCD5nommlGXASM7EHm8iv4bp507vj24YDohD/wmY+ZAZp2EwzzjLy7rHyyFr/T9AUWJxzfS5GQs7b/bcy8FJ9F9/hnut/JUD0g2nFo004Ws8hMULRjoyBG0xYIQKBgQCFIbipTSjjYgtPhISl5S6g5zlyRjsOJIn6cbSr/S2/W+CZVpKRwfbPK2lkxjCb7GBgxRl6jF8IR83cmHewi6tm3bT+ANqjEqAZVJtpDGGuA3u/ZD6FlVYZ0dg1odL3FGTd5OfPH6EjtcxWswKXwM20zm9EdflhN2mm9ed8lKs0qQKBgQCwQd4C2oXxG0otNcHHtCzcnuiB38IGzB0herz10Sgb+vJUb9g7W67+yNisTTpF1rusGIXexp8yKAff/aLkDH6ydcpiR6Qn95Y/st9rCUM5hrOccxCSHvPQo7p7WjugJn6ezmES0quNEjpqJcL6m+2aLJSut67qklHQcQffpAC1ug==";
    /** verifyPublicKey */
    private static final String verifyPublicKey          ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsw2eVcF2Com3kRJ2bsYGrTF6rqkZ6tqe/XHycqLCLMzTkn1SQJicmzAmscMEGXixtRCJ14BPHho/5f/hRqZgn866IaBxiwgxljK/JEUwgAa/3F3Ttf3XzyCX6h3H1M81P/KfvQVKlCzgljH7S9Zlb9JeJ8dL3tmF9id+9edc7tQqk8a7okS9IuBxX1LHLgONsLlS0UPifcI7qXTcZ70OeKw0vuS3Yh6fKNIMNMAHYlaEO8v96NmF2ls8OekRy3VA/rJySKwhMj2Qj1Wp/2TRa7LF79pDCCcDKxX/578l2+jOJmUXRKeGuS7TLA/nNz3FLCUw58xvVpxZvDlUtTwueQIDAQAB";
    public static void main(String[] args) throws Exception {
        String httpMethod = "POST";
        String uriWithQueryString = "/amsin/api/alipay/intl/codec/code/decodeDomestically.htm";
        String clientId = "apshopcenterac";
        String timeString = "2022-09-01T19:56:30+08:00";
        String content = "{\"codeValue\":\"https://qr.alipay.hk/281004082022042019053103000855713638\",\"httpMethod\":\"POST\"}";

        String urlEncodedSignedString = sign(httpMethod, uriWithQueryString, clientId, timeString, content, signPrivateKey);

        String signatureHeaderPayload = new StringBuilder(ALGORITHM)
                .append(NAME_VALUE_SEPARATOR)
                .append(RSA_256)
                .append(COMMA)
                .append(KEY_VERSION)
                .append(NAME_VALUE_SEPARATOR)
                .append("1")
                .append(COMMA)
                .append(SIGNATURE)
                .append(NAME_VALUE_SEPARATOR)
                .append(urlEncodedSignedString).toString();
        System.out.println("signatureHeaderPayload:\n" + signatureHeaderPayload);

        boolean res = verify(httpMethod, uriWithQueryString, clientId, timeString, content, urlEncodedSignedString, verifyPublicKey);
        if (res) {
            System.out.println("verify success.");
        }
    }

    /**
     * Sign the contents of the merchant request

     * @param httpMethod           http method                e.g., POST, GET
     * @param uriWithQueryString   query string in url        e.g., if your request url is https://open-na.alipay.com/ams/api/pay/query uriWithQueryString should be /ams/api/pay/query not https://open-na.alipay.com/ams/api/pay/query
     * @param clientId             clientId issued by Alipay  e.g., 112233445566
     * @param timeString           "request-time" in request  e.g., 2020-01-03T14:36:27+08:00
     * @param reqBody              json format request        e.g., "{"paymentRequestId":"xxx","refundRequestId":"xxx","refundAmount":{"currency":"USD","value":"123"},"extendInfo":{"":""}}"
     * @param merchantPrivateKey   your private key
     */
    public static String sign(
            String httpMethod,
            String uriWithQueryString,
            String clientId,
            String timeString,
            String reqBody,
            String merchantPrivateKey) throws Exception {
        // 1. construct the request content
        String reqContent = httpMethod + " " + uriWithQueryString + "\n" + clientId + "." + timeString + "." + reqBody;

        // 2. sign with your private key
        String originalString = signWithSHA256RSA(reqContent, merchantPrivateKey);

        // 3. encode the original String
        String encodedString = URLEncoder.encode(originalString, "UTF-8");

        // 4. return the encoded String
        return encodedString;
    }

    /**
     * Check the response of Alipay

     * @param httpMethod           http method                  e.g., POST, GET
     * @param uriWithQueryString   query string in url          e.g., if your request url is https://open-na.alipay.com/ams/api/pay/query uriWithQueryString should be /ams/api/pay/query not https://open-na.alipay.com/ams/api/pay/query
     * @param clientId             clientId issued by Alipay    e.g., 112233445566
     * @param timeString           "response-time" in response  e.g., 2020-01-02T22:36:32-08:00
     * @param rspBody              json format response         e.g., "{"acquirerId":"xxx","refundAmount":{"currency":"CNY","value":"123"},"refundFromAmount":{"currency":"JPY","value":"234"},"refundId":"xxx","refundTime":"2020-01-03T14:36:32+08:00","result":{"resultCode":"SUCCESS","resultMessage":"success","resultStatus":"S"}}"
     * @param alipayPublicKey      public key from Alipay
     */
    public static boolean verify(
            String httpMethod,
            String uriWithQueryString,
            String clientId,
            String timeString,
            String rspBody,
            String signature,
            String alipayPublicKey) throws Exception {
        // 1. construct the response content
        String responseContent = httpMethod + " " + uriWithQueryString + "\n" + clientId + "." + timeString + "." + rspBody;

        // 2. decode the signature string
        String decodedString = URLDecoder.decode(signature, "UTF-8");

        // 3. verify the response with Alipay's public key
        boolean result = verifySignatureWithSHA256RSA(responseContent, decodedString, alipayPublicKey);

        // 4. return the result
        return result;
    }


    /**
     * Generate base64 encoded signature using the sender's private key
     *
     * @param reqContent:    the original content to be signed by the sender
     * @param strPrivateKey: the private key which should be base64 encoded
     * @return
     * @throws Exception
     */
    private static String signWithSHA256RSA(String reqContent, String strPrivateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(getPrivateKeyFromBase64String(strPrivateKey));
        privateSignature.update(reqContent.getBytes("UTF-8"));
        byte[] bytes = privateSignature.sign();

        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     *
     * @param privateKeyString
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKeyFromBase64String(String privateKeyString) throws Exception{
        byte[] b1 = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * Verify if the received signature is correctly generated with the sender's public key
     *
     * @param rspContent: the original content signed by the sender and to be verified by the receiver.
     * @param signature:  the signature generated by the sender
     * @param strPk:      the public key string-base64 encoded
     * @return
     * @throws Exception
     */
    private static boolean verifySignatureWithSHA256RSA(String rspContent, String signature, String strPk) throws Exception {
        PublicKey publicKey = getPublicKeyFromBase64String(strPk);

        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(rspContent.getBytes("UTF-8"));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return publicSignature.verify(signatureBytes);
    }

    /**
     *
     * @param publicKeyString
     * @return
     */
    private static PublicKey getPublicKeyFromBase64String(String publicKeyString) throws Exception{
        byte[] b1 = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(X509publicKey);
    }
}