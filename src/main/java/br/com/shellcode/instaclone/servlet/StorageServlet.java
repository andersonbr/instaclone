package br.com.shellcode.instaclone.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.shellcode.instaclone.dao.PessoaDao;
import br.com.shellcode.instaclone.model.Pessoa;
import br.com.shellcode.instaclone.storage.StorageManager;

@WebServlet(name = "ProfilePicture", urlPatterns = { "/profilepicture/*" })
public class StorageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");

		/*
		 * Exibir arquivo cloud storage
		 */
		PessoaDao dao = new PessoaDao();
		String[] urlPath = req.getRequestURI().split("/");
		if (urlPath.length == 3) {
			try {
				Integer id = Integer.parseInt(urlPath[2]);
				Pessoa p = dao.read(id);
				if (p != null && p.getFoto() != null && p.getFoto()) {
					res.setHeader("Content-type", "image/jpeg");
					String file = "profile_" + p.getId() + ".jpg";
					StorageManager.readStream(res.getOutputStream(), StorageManager.DEFAULT_BUCKET, file);
				} else {
					res.setHeader("Content-type", "image/png");
					StorageManager.readStream(res.getOutputStream(), StorageManager.DEFAULT_BUCKET, "profile.png");
				}
			} catch (Exception e) {
				res.getWriter().println("profilepicture servlet (fail): " + req.getRequestURI());
			}
		} else {
			res.getWriter().println("profilepicture servlet (fail): " + req.getRequestURI());
		}
	}

}