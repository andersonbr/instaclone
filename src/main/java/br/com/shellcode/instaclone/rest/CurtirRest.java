package br.com.shellcode.instaclone.rest;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.shellcode.instaclone.dto.CurtirDTO;
import br.com.shellcode.instaclone.model.Pessoa;
import br.com.shellcode.instaclone.storage.DatastoreManager;
import br.com.shellcode.instaclone.util.SecurityUtils;

@Path("/curtir")
public class CurtirRest {
	
	@GET
	@Path("/status/{postId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CurtirDTO status(@CookieParam("AUTHTOKEN") Cookie authCookie, @PathParam("postId") Integer postId) {
		Pessoa pessoa = SecurityUtils.checkAuthJaxRS(authCookie);
		CurtirDTO dto = new CurtirDTO(postId, null);
		if (pessoa != null) {
			Key userKey = KeyFactory.createKey("curtidas", "user_" + pessoa.getId());
			Key curtidaKey = KeyFactory.createKey(userKey, "curtidas", "post_" + postId);
			Entity curtida = DatastoreManager.read(curtidaKey);
			if (curtida != null) {
				Boolean val = (Boolean) curtida.getProperty("curtida");
				dto.setCurtir(val);
			}
		}
		return dto;
	}

	@POST
	@Path("/set/{postId}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public CurtirDTO set(@CookieParam("AUTHTOKEN") Cookie authCookie, @PathParam("postId") Integer postId,
			@PathParam("status") String status) {
		Pessoa pessoa = SecurityUtils.checkAuthJaxRS(authCookie);
		Boolean value = null;
		if (pessoa != null) {

			Key userKey = KeyFactory.createKey("curtidas", "user_" + pessoa.getId());
			Key curtidaKey = KeyFactory.createKey(userKey, "curtidas", "post_" + postId);

			if (status.equals("t")) {
				value = true;
			} else if (status.equals("f")) {
				value = false;
			}

			// criar
			if (value != null) {
				Entity entity = new Entity(curtidaKey);
				entity.setProperty("curtida", value);
				DatastoreManager.save(entity);
			} else {
				DatastoreManager.delete(curtidaKey);
			}
		}

		CurtirDTO dto = new CurtirDTO(postId, value);
		return dto;
	}

}
