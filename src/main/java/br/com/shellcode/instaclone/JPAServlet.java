package br.com.shellcode.instaclone;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.shellcode.instaclone.dao.EMF;
import br.com.shellcode.instaclone.model.Pessoa;

@SuppressWarnings("serial")
@WebServlet(name = "JPA", urlPatterns = "/jpa")
public class JPAServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PrintWriter out = resp.getWriter();
		EntityManager em = EMF.get().createEntityManager();
		out.println("Buscando pessoa...");
		String strId = req.getParameter("id");
		if (strId.trim().length() > 0) {
			Integer id = Integer.parseInt(strId);
			try {
				Pessoa p = em.find(Pessoa.class, id);
				if (p != null) {
					out.println(p.getNome());
				} else {
					out.println("id nao encontrado... criando...");
					Pessoa newPessoa = new Pessoa();
					newPessoa.setEmail("andersonbr@gmail.com");
					newPessoa.setNome("Anderson Bezerra Calixto");
					newPessoa.setFoto("foto.jpg");
					newPessoa.setSenha("123");
					em.persist(newPessoa);
				}
			} catch (Exception e) {
			}
		}
		
		em.close();
	}
}