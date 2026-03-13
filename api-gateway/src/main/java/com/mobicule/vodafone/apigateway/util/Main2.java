package com.mobicule.vodafone.apigateway.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

@Slf4j
public class Main2 {


    public static void main(String[] args) throws Exception {

        String a= encryption("{\n" +
                "    \"user\": {\n" +
                "        \"syncCheck\": \"false\",\n" +
                "        \"long\": \"73.76637988\",\n" +
                "        \"network\": \"Jio\",\n" +
                "        \"versionCode\": \"8934\",\n" +
                "        \"client\": \"android\",\n" +
                "        \"etop\": \"8007626248\",\n" +
                "        \"entityId\": \"15\",\n" +
                "        \"deviceModel\": \"SM-E556B\",\n" +
                "        \"androidVersion\": \"14\",\n" +
                "        \"version\": \"12.37.1\",\n" +
                "        \"syncDate\": \"0\",\n" +
                "        \"lat\": \"18.56581332\",\n" +
                "        \"checkVersion\": \"false\",\n" +
                "        \"opt2\": \"192.168.0.106\",\n" +
                "        \"opt1\": \"1a733f2a428190ac80fb89e7ccb302c8a61d4c01e80fa90518935823806de3e7\"\n" +
                "    },\n" +
                "    \"entity\": \"verifyOTP\",\n" +
                "    \"identifier\": \"verify_otp\",\n" +
                "    \"type\": \"transaction\",\n" +
                "    \"queryParameterMap\": {\n" +
                "        \"etopNo\": \"HDBWjLS/YnmMmZPKHtPU8g==\",\n" +
                "        \"module\": \"\",\n" +
                "        \"otp\": \"0wDXfE/Id2/33llDzsZK/g==\"\n" +
                "    },\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"mobNo\": \"HDBWjLS/YnmMmZPKHtPU8g==\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"action\": \"get\"\n" +
                "}");

        log.info("encryption :"+a);

        String b= decryption("VyBYBrFYp0rNSsjQMxwzGeQEER4Ow+RVLJ6pNIfehReZONYujqX/d1D4jlElUm6HkORiJgyrcLvrwwXGi+KJKnKL0CIVItijTamUsE1JxLXG6D+jUfLgMnzRUq+apDxIzNeq5bLMtmJOrtGV3Iwg0X/KxDTn9zQsG64k0tRTETKU5Z2mQGgjj4L6OafH7kgHUmYm67UO7KlK3+iKHjvnJRnsuDfg8NLECfibYhLc19RcTc4c1ne+v/TCr13CHoV0XvkcHGEFTu/3TdjUB4A7vV2mwoH12W1k5PiDYMpWD6bp9olBxOygEsKQFSE5TW6PnniIa9+sdjp83VPIsvW9l5WQXhs4M1pIzf+toabR66fS4HCeTa9uxw4DpYTCC1IXhFTeFcg/31ZX7m4DM8zcVpB1IATbG6DqU4UVn/CR0cKJc4UqBTa7ewHoe1Q2LhytefWuVm7Z50d1F44dlzmvxX0LXN/RdNiNqzvXDIekCpQwirHZAKkNLlgU8yFcT+f/Q/WdK1cfzAZs0uy2hcvtZakwJFn2EHeP0RIclR71hF2kgu/sJyB2RfQJrclye6gqSlYvaLagkdvem3o8iqRPRJork+Kn72rkkp8Rkuc+Ua0pfUQaUMXcjeebv8d9eZLD12IOsQwm9thDuE3SBzHZGF618GnxeUCRP2v0ut/Ml1tMrcjChTTplK0v1hFqfhs3S0ILCfwvpl4FvznHFCyixuDfm1ISmZVFrSngDwKS29N2I4D4bCm1xbWnx4/FhhrroTLxle2pQkwoVe8H69ezjuI4h0KYptRdyeyosEo3LExcgRzpQfMKm2GxNLWZW9LAljuRGjXKbHX2ymv90DQlTA==");
        log.info("decry"+b);
    }

    private static final String ALGORITHM = "AES/ECB/PKCS7Padding";

    private static final byte[] keyValue = new byte[] { 's', 't', 'i', 'l', 'l', 'g', 'o', '4', 'i', 't', '*', 'm',
            'o', 'b', 'i', 'c' };

    public static String encryption(String valueToEnc) throws Exception
    {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM, "BC");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes("UTF8"));
        byte[] encryptedValue = Base64.encodeBase64(encValue);

        return new String(encryptedValue);

    }

    public static String decryption(String encryptedValue) throws Exception
    {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM, "BC");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.decodeBase64(encryptedValue.getBytes());
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }


    private static Key generateKey() throws Exception
    {
        //System.out.println(keyValue[0]);
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        // key = keyFactory.generateSecret(new DESKeySpec(keyValue));
        return key;
    }
}
