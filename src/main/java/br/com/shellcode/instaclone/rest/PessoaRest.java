package br.com.shellcode.instaclone.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.shellcode.instaclone.dao.PessoaDao;
import br.com.shellcode.instaclone.model.Pessoa;

@Path("/pessoa")
public class PessoaRest {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> list() {
		PessoaDao dao = new PessoaDao();
		return dao.list();
	}

}
