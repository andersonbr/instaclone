package br.com.shellcode.instaclone.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

	public static String sha1(String string) {
		return sha1(string.getBytes());
	}

	public static String sha1(byte[] bytes) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
			md.update(bytes, 0, bytes.length);
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String userPass(String user, String passwd) {
		return Crypto.sha1(user + ":" + passwd);
	}

}
