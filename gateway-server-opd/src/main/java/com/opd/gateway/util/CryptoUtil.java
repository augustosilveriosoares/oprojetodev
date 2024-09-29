package com.opd.gateway.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class CryptoUtil {

	public static String sha256(String plaintext) throws Exception {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e.getMessage());
		}

		try {
			md.update(plaintext.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e.getMessage());
		}

		byte raw[] = md.digest();
		try {
			return new String(Base64.encodeBase64(raw), "UTF-8");
		} catch (Exception use) {
			throw new Exception(use);
		}
	}

}