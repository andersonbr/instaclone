package br.com.shellcode.instaclone.servlet;

import java.io.IOException;

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
import br.com.shellcode.instaclone.model.Pessoa;
import br.com.shellcode.instaclone.storage.StorageManager;

@MultipartConfig(maxFileSize = 50 * 1024 * 1024, // max size for uploaded files
		maxRequestSize = 60 * 1024 * 1024, // max size for multipart/form-data
		fileSizeThreshold = 5 * 1024 * 1024 // start writing to Cloud Storage after 5MB
)
@WebServlet(name = "PessoaServlet", urlPatterns = { "/pessoa/*" })
public class PessoaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (req.getRequestURI().equals("/pessoa/form")) {
			res.setContentType("text/html");
			res.setCharacterEncoding("UTF-8");
			req.setAttribute("edit", false);
			String nextJSP = "/pages/pessoas-form.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			try {
				dispatcher.forward(req, res);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else if (req.getRequestURI().equals("/pessoa/edit")) {
			if (req.getAttribute("autenticado") != null) {
				res.setContentType("text/html");
				res.setCharacterEncoding("UTF-8");
				req.setAttribute("edit", true);
				String nextJSP = "/pages/pessoas-form.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
				try {
					dispatcher.forward(req, res);
				} catch (ServletException e) {
					e.printStackTrace();
				}
			} else {
				res.sendRedirect("/home");
			}
		} else {
			res.getWriter().println("posts servlet: " + req.getRequestURI());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Pessoa p = (Pessoa) req.getAttribute("autenticado");
		String nome = req.getParameter("nome");
		String nick = req.getParameter("nick");
		String senha = req.getParameter("senha");
		String email = req.getParameter("email");
		Part foto = req.getPart("foto");

		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Pessoa pessoa = null;
		if (p != null) {
			// editar
			pessoa = em.find(Pessoa.class, p.getId());
		} else {
			// novo
			pessoa = new Pessoa();
		}
		if (nome != null && nome.trim().length() > 0) pessoa.setNome(nome);
		if (nick != null && nick.trim().length() > 0) pessoa.setNick(nick);
		if (senha != null && senha.trim().length() > 0) pessoa.setSenha(senha);
		if (email != null && email.trim().length() > 0) pessoa.setEmail(email);
		if (foto.getSize() > 0) {
			pessoa.setFoto(true);
		}
		em.persist(pessoa);
		tx.commit();
		if (foto.getSize() > 0 && pessoa.getId() != null) {
			StorageManager.saveStream(foto.getInputStream(), StorageManager.DEFAULT_BUCKET,
					"profile_" + pessoa.getId() + ".jpg");
		}
		String home = req.getContextPath() + "/@" + nick;
		res.sendRedirect(home);
	}

}