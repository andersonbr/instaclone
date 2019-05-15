package br.com.shellcode.instaclone.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PessoaServlet", urlPatterns = { "/pessoa/*" })
public class PessoaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (req.getRequestURI().equals("/pessoa/new")) {
			res.setContentType("text/html");
			res.setCharacterEncoding("UTF-8");
			String nextJSP = "/pages/pessoas-form.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			try {
				dispatcher.forward(req, res);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else if (req.getRequestURI().equals("/pessoa/edit")) {
			/**
			 * verificar se está logado
			 */
			res.setContentType("text/html");
			res.setCharacterEncoding("UTF-8");
			String nextJSP = "/pages/pessoas-form.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			try {
				dispatcher.forward(req, res);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else {
			res.getWriter().println("posts servlet: " + req.getRequestURI());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);
	}

}