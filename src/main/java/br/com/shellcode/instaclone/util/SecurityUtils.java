package br.com.shellcode.instaclone.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import br.com.shellcode.instaclone.dao.AuthenticationDao;
import br.com.shellcode.instaclone.model.Authentication;
import br.com.shellcode.instaclone.model.Pessoa;

public class SecurityUtils {

	public static void processSecurity(HttpServletRequest req) {
		/**
		 * Pessoa autenticada
		 */
		Pessoa pessoa = null;
		Cookie cookie = CookieUtils.getCookie(req, "AUTHTOKEN");
		if (cookie != null && req.getAttribute("autenticado") == null) {
			String token = cookie.getValue();
			AuthenticationDao authDao = new AuthenticationDao();
			Authentication auth = authDao.buscaAutenticacaoPorToken(token);
			if (auth != null) {
				pessoa = auth.getPessoa();
			}
		}
		if (req.getAttribute("autenticado") == null)
			req.setAttribute("autenticado", pessoa);
	}

	public static Pessoa checkAuthJaxRS(javax.ws.rs.core.Cookie cookie) {
		/**
		 * Pessoa autenticada
		 */
		Pessoa pessoa = null;
		if (cookie != null && cookie.getValue() != null) {
			String token = cookie.getValue();
			AuthenticationDao authDao = new AuthenticationDao();
			Authentication auth = authDao.buscaAutenticacaoPorToken(token);
			if (auth != null) {
				pessoa = auth.getPessoa();
			}
		}
		return pessoa;
	}
}
