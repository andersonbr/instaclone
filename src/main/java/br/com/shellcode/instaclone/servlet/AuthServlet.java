package br.com.shellcode.instaclone.servlet;

import java.io.IOException;
import java.security.SecureRandom;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.shellcode.instaclone.dao.EMF;
import br.com.shellcode.instaclone.dao.PessoaDao;
import br.com.shellcode.instaclone.model.Authentication;
import br.com.shellcode.instaclone.model.Pessoa;
import br.com.shellcode.instaclone.util.CookieUtils;
import br.com.shellcode.instaclone.util.Crypto;

@WebServlet(name = "AuthServlet", urlPatterns = { "/auth/*" })
public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (req.getParameter("error") != null && req.getParameter("error").equals("1")) {
			req.setAttribute("erroMsg", "Usuário ou senha incorreta");
		} else {
			req.setAttribute("erroMsg", "");
		}

		String nextJSP = "/pages/auth-form.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		try {
			dispatcher.forward(req, res);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// autenticar
		byte[] bytes = new byte[20];
		new SecureRandom().nextBytes(bytes);
		String token = Crypto.sha1(bytes);
		String user = req.getParameter("username");
		String password = req.getParameter("password");
		Pessoa pessoa = null;
		if (user != null && password != null) {
			PessoaDao pessoaDao = new PessoaDao();
			pessoa = pessoaDao.buscaPessoaNickSenha(user, password);
		}
		if (pessoa != null) {
			Authentication auth = new Authentication();
			auth.setPessoa(pessoa);
			auth.setToken(token);
			EntityManager em = EMF.get().createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(auth);
			tx.commit();
			req.setAttribute("erro", "");

			// definir Cookie
			int hora = 60 * 60;
			CookieUtils.setCookie(request, response, "AUTHTOKEN", token, 24 * hora, true);
			res.sendRedirect("/home");
		} else {
			res.sendRedirect("/auth/form?error=1");
		}
	}

}