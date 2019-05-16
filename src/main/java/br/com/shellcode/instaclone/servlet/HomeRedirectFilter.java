package br.com.shellcode.instaclone.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.shellcode.instaclone.util.SecurityUtils;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class HomeRedirectFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String raiz = req.getContextPath() + "/$";
		String home = req.getContextPath() + "/home";
		String profileMatch = req.getContextPath() + "/@([A-Za-z0-9\\.]+)";
		if (req.getRequestURI().matches(raiz)) {
			res.sendRedirect(home);
		} else if (req.getRequestURI().matches(profileMatch)) {
			Pattern p = Pattern.compile(profileMatch);
			Matcher m = p.matcher(req.getRequestURI());
			if (m.find()) {
				String profile = m.group(1);
				String profileServlet = "/profile/" + profile;
				SecurityUtils.processSecurity(req);
				RequestDispatcher dispatcher = req.getRequestDispatcher(profileServlet);
				try {
					dispatcher.forward(req, res);
				} catch (ServletException e) {
					e.printStackTrace();
				}
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {
	}

}