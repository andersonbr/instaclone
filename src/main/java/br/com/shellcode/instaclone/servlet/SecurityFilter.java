package br.com.shellcode.instaclone.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.shellcode.instaclone.dao.AuthenticationDao;
import br.com.shellcode.instaclone.model.Authentication;
import br.com.shellcode.instaclone.model.Pessoa;
import br.com.shellcode.instaclone.util.CookieUtils;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		/**
		 * Pessoa autenticada
		 */
		Pessoa pessoa = null;
		Cookie cookie = CookieUtils.getCookie(req, "AUTHTOKEN");
		if (cookie != null) {
			String token = cookie.getValue();
			AuthenticationDao authDao = new AuthenticationDao();
			Authentication auth = authDao.buscaAutenticacaoPorToken(token);
			if (auth != null) {
				pessoa = auth.getPessoa();
			}
		}
		req.setAttribute("autenticado", pessoa);
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}

}