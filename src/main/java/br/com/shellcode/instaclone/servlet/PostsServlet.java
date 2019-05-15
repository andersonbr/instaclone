package br.com.shellcode.instaclone.servlet;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import br.com.shellcode.instaclone.dao.EMF;
import br.com.shellcode.instaclone.dao.PostsDao;
import br.com.shellcode.instaclone.model.Pessoa;
import br.com.shellcode.instaclone.model.Posts;
import br.com.shellcode.instaclone.storage.StorageManager;

@MultipartConfig(maxFileSize = 10 * 1024 * 1024, // max size for uploaded files
		maxRequestSize = 20 * 1024 * 1024, // max size for multipart/form-data
		fileSizeThreshold = 5 * 1024 * 1024 // start writing to Cloud Storage after 5MB
)
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
			String nextJSP = "/pages/posts-form.jsp";
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
				try {
					Integer id = Integer.parseInt(urlPath[2]);
					Posts post = dao.read(id);
					if (post != null) {
						res.setContentType("image/" + post.getExt());
						StorageManager.readStream(res.getOutputStream(), "projetocen2.appspot.com",
								post.getId() + "." + post.getExt());
					} else {
						res.getWriter().println("not found.");
					}
				} catch (Exception e) {
					res.getWriter().println("posts servlet (fail): " + req.getRequestURI());
				}
			} else {
				res.getWriter().println("posts servlet: " + req.getRequestURI());
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Part foto = request.getPart("foto");
		String ext = null;
		if (foto.getContentType().equals("image/png")) {
			ext = "png";
		} else if (foto.getContentType().equals("image/jpeg")) {
			ext = "jpg";
		}
		if (ext != null) {
			EntityManager em = EMF.get().createEntityManager();
			Pessoa pessoa = em.find(Pessoa.class, 1);
			Posts post = new Posts();
			post.setData(new Date());
			post.setExt(ext);
			post.setPessoa(pessoa);
			post.setDescricao(req.getParameter("descricao"));
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(post);
			tx.commit();
			StorageManager.saveStream(foto.getInputStream(), "projetocen2.appspot.com", post.getId() + "." + ext);
			String home = req.getContextPath() + "/home";
			res.sendRedirect(home);
		} else {
			res.getWriter().println("falha: " + req.getRequestURI() + ", " + foto.getContentType());
		}
	}

}