package com.mobicule.vodafone.apigateway.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;
import java.security.Key;



@Slf4j
@Component
public class CryptoUtil {

    @Value("${encrypt.secret}")
    private  String SECRET;


  //  private static final String SECRET = "1234567890123456";

    public  String encrypt(String str) {

      //  final String SECRET = "1234567890123456";

        try {

            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedBytes = cipher.doFinal(str.getBytes());

            String encrypted = Base64.getEncoder().encodeToString(encryptedBytes);

           // log.info("encrypted : " + encrypted);

            return encrypted;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  String decrypt(String str) {

        //final String SECRET = "1234567890123456";

        try {

            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(str));

            String decrypted = new String(decryptedBytes);

          //  log.info("decrypt : " + decrypted);

            return decrypted;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        String encryptedValue = new String(encValue);

        return new String(encryptedValue);

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
