package br.com.shellcode.instaclone.util;

public class StringUtils {
	public static String capitalize(String string) {
		return capDecapitalize(string, true);
	}

	public static String decapitalize(String string) {
		return capDecapitalize(string, false);
	}

	private static String capDecapitalize(String string, boolean capitalize) {
		if (string == null || string.length() == 0) {
			return string;
		}
		char c[] = string.toCharArray();
		c[0] = (capitalize) ? Character.toUpperCase(c[0]) : Character.toLowerCase(c[0]);
		return new String(c);
	}
}