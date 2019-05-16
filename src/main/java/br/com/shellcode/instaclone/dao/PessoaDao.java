package br.com.shellcode.instaclone.dao;

import javax.persistence.TypedQuery;

import br.com.shellcode.instaclone.model.Pessoa;

public class PessoaDao extends AbstractGenericDao<Pessoa> {
	public Pessoa buscaPessoaNickSenha(String user, String password) {
		TypedQuery<Pessoa> q = getEm()
				.createQuery("select p from Pessoa p where p.nick = :username and p.senha :password", Pessoa.class);
		q.setParameter("username", user);
		q.setParameter("password", password);
		Pessoa res = null;
		try {
			res = q.getSingleResult();
		} catch (Exception e) {
		}
		return res;
	}
}
