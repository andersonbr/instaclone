package br.com.shellcode.instaclone.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.shellcode.instaclone.dao.PessoaDao;
import br.com.shellcode.instaclone.dao.PostsDao;

@WebServlet(name = "ProfileServlet", urlPatterns = { "/profile/*" })
public class ProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		String nextJSP = "/pages/profile.jsp";

		String profileMatch = req.getContextPath() + "/profile/([A-Za-z0-9\\.]+)";
		if (req.getRequestURI().matches(profileMatch)) {
			Pattern p = Pattern.compile(profileMatch);
			Matcher m = p.matcher(req.getRequestURI());
			if (m.find()) {
				String profile = m.group(1);
				PostsDao postsDao = new PostsDao();
				PessoaDao pessoaDao = new PessoaDao();
				req.setAttribute("profile", pessoaDao.buscaPessoaPorNick(profile));
				req.setAttribute("postagens", postsDao.postsDoUsuario(profile));
				RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
				try {
					dispatcher.forward(req, res);
				} catch (ServletException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);
	}

}