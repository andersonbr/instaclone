package br.com.shellcode.instaclone.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name,
			boolean httpOnly) {
		setCookie(request, response, name, "", 0, httpOnly);
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie cookie = null;
		Cookie[] userCookies = request.getCookies();
		if (userCookies != null && userCookies.length > 0) {
			for (int i = 0; i < userCookies.length; i++) {
				if (userCookies[i].getName().equals(name)) {
					cookie = userCookies[i];
					return cookie;
				}
			}
		}
		return null;
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			int expiry, boolean httpOnly) {
		Cookie cookie = getCookie(request, name);
		if (cookie != null) {
			cookie.setValue(value);
		} else {
			cookie = new Cookie(name, value);
		}
		String path =  (request.getContextPath() == null || request.getContextPath().equals("")) ? "/" : request.getContextPath(); 
		cookie.setPath(path);
		cookie.setMaxAge(expiry);
		cookie.setHttpOnly(httpOnly);
		response.addCookie(cookie);
	}
}
