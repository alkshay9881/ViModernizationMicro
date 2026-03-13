/**
******************************************************************************
* C O P Y R I G H T  A N D  C O N F I D E N T I A L I T Y  N O T I C E
* <p>
* Copyright © 2008-2009 Mobicule Technologies Pvt. Ltd. All rights reserved. 
* This is proprietary information of Mobicule Technologies Pvt. Ltd.and is 
* subject to applicable licensing agreements. Unauthorized reproduction, 
* transmission or distribution of this file and its contents is a 
* violation of applicable laws.
******************************************************************************
*
* @project vodafone-krcc_NEW_WEB
*/
package com.mobicule.vodafone.apigateway.util;



import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;



@Component
public class EncryptDecryptService
{
	/*static Log log = LogFactory.getLog(EncryptDecryptService.class);*/
	
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

	public static String encryptionForDb(String data)
	{

		StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();

		textEncryptor.setPassword("P@ssw0rd4Ekyc");

		String encryptedData = textEncryptor.encrypt(data);
		/*System.out.println("encrypted blood group: " + encryptedData);*/

		return encryptedData;

	}

	public static String decryptionFromDb(String data)
	{

		StandardPBEStringEncryptor textDecryptor = new StandardPBEStringEncryptor();

		textDecryptor.setPassword("P@ssw0rd4Ekyc");

		String decryptedData;

		try
		{

			decryptedData = textDecryptor.decrypt(data);
			/*System.out.println("decrypted value: " + decryptedData);*/

		}
		catch (Exception e)
		{
			decryptedData = "";
			/*log.info("The exception : "+e.getMessage());*/
		}

		return decryptedData;

	}
}