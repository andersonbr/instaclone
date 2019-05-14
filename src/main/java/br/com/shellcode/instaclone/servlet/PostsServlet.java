package br.com.shellcode.instaclone.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.shellcode.instaclone.dao.PostsDao;
import br.com.shellcode.instaclone.model.Posts;
import br.com.shellcode.instaclone.storage.StorageManager;

@WebServlet(name = "PostsServlet", urlPatterns = { "/posts/*" })
public class PostsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	PostsDao dao = new PostsDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (req.getRequestURI().equals("/posts/form")) {
			res.setContentType("text/html");
			res.setCharacterEncoding("UTF-8");
			String nextJSP = "/pages/index.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			try {
				dispatcher.forward(req, res);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else {
			/*
			 * Exibir foto do cloud storage
			 */
			String[] urlPath = req.getRequestURI().split("/");
			if (urlPath.length == 3) {
				Integer id = Integer.parseInt(urlPath[2]);
				Posts post = dao.read(id);
				if (post != null) {
					res.setContentType("image/" + post.getExt());
					StorageManager.readStream(res.getOutputStream(), "projetocen2.appspot.com",
							post.getId() + "." + post.getExt());
				} else {
					res.getWriter().println("not found.");
				}
			} else {
				res.getWriter().println("posts servlet: " + req.getRequestURI());
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);
	}

}