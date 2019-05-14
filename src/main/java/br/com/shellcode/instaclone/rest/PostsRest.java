package br.com.shellcode.instaclone.rest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.shellcode.instaclone.dao.PostsDao;
import br.com.shellcode.instaclone.model.Posts;
import br.com.shellcode.instaclone.util.DateParam;

@Path("/posts")
public class PostsRest {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Posts> list() {
		PostsDao dao = new PostsDao();
		return dao.list();
	}

	@GET
	@Path("/list/{inicio}/{fim}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Posts> list(@PathParam("inicio") DateParam inicio, @PathParam("fim") DateParam fim) {
		Date dtInicio = inicio.getDate();
		// final do dia
		LocalDateTime ldt = LocalDateTime.ofInstant(fim.getDate().toInstant(), ZoneId.systemDefault());
		Date dtFim = Date.from(ldt.with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
		PostsDao dao = new PostsDao();
		return dao.listEntre(dtInicio, dtFim);
	}

}
