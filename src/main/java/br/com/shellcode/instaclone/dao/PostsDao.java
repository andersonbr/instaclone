package br.com.shellcode.instaclone.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import br.com.shellcode.instaclone.model.Posts;

public class PostsDao extends AbstractGenericDao<Posts> {
	@Override
	public List<Posts> list() {
		TypedQuery<Posts> q = getEm().createQuery("select p from Posts p ORDER BY p.data DESC", Posts.class);
		return q.getResultList();
	}

	public List<Posts> listEntre(Date dtInicio, Date dtFim) {
		TypedQuery<Posts> q = getEm().createQuery("select p from Posts p where p.data between :inicio and :fim ORDER BY p.data DESC",
				Posts.class);
		q.setParameter("inicio", dtInicio);
		q.setParameter("fim", dtFim);
		return q.getResultList();
	}
}
