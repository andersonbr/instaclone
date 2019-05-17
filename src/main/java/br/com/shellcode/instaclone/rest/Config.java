package br.com.shellcode.instaclone.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class Config extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(PessoaRest.class);
		classes.add(PostsRest.class);
		classes.add(CurtirRest.class);
		return classes;
	}
}