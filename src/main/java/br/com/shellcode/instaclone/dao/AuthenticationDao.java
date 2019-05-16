package br.com.shellcode.instaclone.dao;

import javax.persistence.TypedQuery;

import br.com.shellcode.instaclone.model.Authentication;

public class AuthenticationDao extends AbstractGenericDao<Authentication> {

	public Authentication buscaAutenticacaoPorToken(String token) {
		TypedQuery<Authentication> q = getEm().createQuery("select a from Authentication a WHERE a.token = :token",
				Authentication.class);
		q.setParameter("token", token);
		Authentication res = null;
		try {
			res = q.getSingleResult();
		} catch (Exception e) {
		}
		return res;
	}

}
