package com.mobicule.vodafone.apigateway.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Base64;
@Slf4j
public class Main1 {

    public static void main(String[] args) {


       String a= encrypt("{\n" +
               "    \"user\": {\n" +
               "        \"syncCheck\": \"false\",\n" +
               "        \"long\": \"0.0\",\n" +
               "        \"versionCode\": \"8431\",\n" +
               "        \"network\": \"BSNL Mobile\",\n" +
               "        \"client\": \"android\",\n" +
               "        \"etop\": \"9820154976\",\n" +
               "        \"entityId\": \"\",\n" +
               "        \"deviceModel\": \"G2299\",\n" +
               "        \"version\": \"\",\n" +
               "        \"syncDate\": \"0\",\n" +
               "        \"lat\": \"0.0\",\n" +
               "        \"checkVersion\": \"false\",\n" +
               "        \"opt2\": \"192.168.0.107\",\n" +
               "        \"opt1\": \"911601750706403\"\n" +
               "    },\n" +
               "    \"entity\": \"generateOTP\",\n" +
               "    \"type\": \"transaction\",\n" +
               "    \"queryParameterMap\": {},\n" +
               "    \"data\": [\n" +
               "        {\n" +
               "            \"imsi\": \"\",\n" +
               "            \"etopNo\": \"1111111111\"\n" +
               "        }\n" +
               "    ],\n" +
               "    \"action\": \"add\"\n" +
               "}");


        decrypt(a);
    }

    public static String encrypt(String str) {

        final String SECRET = "P@ssw0rd4Ekyc456";

        try {

            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedBytes = cipher.doFinal(str.getBytes());

            String encrypted = Base64.getEncoder().encodeToString(encryptedBytes);

            log.info("encrypted : " + encrypted);

            return encrypted;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String str) {

        final String SECRET = "P@ssw0rd4Ekyc456";

        try {

            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(str));

            String decrypted = new String(decryptedBytes);

            log.info("decrypt : " + decrypted);

            return decrypted;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
