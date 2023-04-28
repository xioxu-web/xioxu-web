package com.demo.common.ut;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.utils.RSAUtils;
import org.junit.Test;

import java.util.Map;

public class RsaTest{

    /**
     *  用测试生成的公钥，私钥赋值
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDD+HvBa4gHSYB7cJgL3fAqhH7q80HdmemIP6rJrbceygqK+HAie22aNGnCELFtYF1nDiLdvdBiMePB1rl43KqwmJcp0J5R66tue9JoSAlUK2QRXwJs9q2UzsDbMEbD0HsiO0vs8SkX3Yj06y3pKlIpNHDo+nmwa0s3dyr6Au72iwIDAQAB";

    private static final String PRIVATE_KEY = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMP4e8FriAdJgHtwmAvd8CqEfurzQd2Z6Yg/qsmttx7KCor4cCJ7bZo0acIQsW1gXWcOIt290GIx48HWuXjcqrCYlynQnlHrq2570mhICVQrZBFfAmz2rZTOwNswRsPQeyI7S+zxKRfdiPTrLekqUik0cOj6ebBrSzd3KvoC7vaLAgMBAAECgYEAjQEWcpZfmvatBqR+ElPPgZgyBfWf18XtvYYp6dEfaHzxVwrpDDaJJyI9UlBWiZ+DtSn7tlbkQDMrcIaCj08fVIwormWtcDzjuAied2qiHlTZy4SaNtQmSaps0sTi16u6zfUnpldt7+TwzUTZo7QHghA4apYVb2ZQ+5itlEfnMokCQQDyu2EbX5miznL71nbGzFYrEWkvGUlFzgtXikYDL9Z3AYmqbF0HjxvWZAtbVBOl6ZSFPPaamoJ1+Dt7g038JO6fAkEAzq7CU7teM3Zz2ftYk9WzuSCmOwfSPTRODaU/+Ja3EXaiK3w149WhTl+BYLDFsZpeaF0syAfYRnZlxF8ZPhtslQJBAOJNWpq3xbhpPACZkcGEjq22nNdBfvPqxt5F10JvXkczktLmFtHOVcjLG195gpwDqumRzeSUOx+bYHGmGJYFJMcCQQDCRq0tjcsPbsGZXdS2KcNlOhZnGIP7ugXAd9u47NqqFKx9WaVCQxX8GHQBkkhSGHuzeBnW8ODP4wzn2EhfA+I9AkEApXWEpAI2lmR+JhXDa1/2pkyosrFBkEyeitFASdLQGDyame5G5O1zvszgTEiLY2USOFrZJAclP3uR7776rkHsqQ==\n";


    /**
     *  生成公钥私钥
     */
    @Test
    public void generateRsaKey() {
        Map<String, String> map = RSAUtils.generateRasKey();
        System.out.println("随机生成的公钥为:" + map.get(RSAUtils.PUBLIC_KEY));
        System.out.println("随机生成的私钥为:" + map.get(RSAUtils.PRIVATE_KEY));

    }

    /**
     * 加密: Yeidauky/iN1/whevov2+ntzXJKAp2AHfESu5ixnDqH5iB7ww+TcfqJpDfkPHfb12Y0sVXw0gBHNJ4inkh7l2/SJBze3pKQU/mg3oyDokTia3JZIs+e80/iJcSfN+yA1JaqY+eJPYiBiOGAF2S6x0ynvJg/Wj0fwp2Tq3PDzRMo=
     */
    @Test
    public void testEncrypt() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "xiaoxu");
        jsonObject.put("password", "123456");
        String str = jsonObject.toJSONString();
        String encrypt = RSAUtils.encrypt(str,PUBLIC_KEY);
        System.out.println(encrypt);
    }

    @Test
    public void testDecrypt() {
        String decrypt = RSAUtils.decrypt("VlUEZhw5tj2yvXYvEQfIKixREJ3TAMSPQUXWxsAjiCK4cZiD5TcIvX92d2XLWfnsiVmyWJbC5wHh/4hQkUxdM+kQzPNWu3I1fZeOY1roC5837TLyEJVRMd71a8ioFsBfQnTHuMq9TWszKQYlv7InW8uEcPckVOKYLCIdoikJqrw=\n",
                PRIVATE_KEY);
        System.out.println(decrypt);
    }


}
