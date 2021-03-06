package br.com.shellcode.instaclone.storage;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DatastoreManager {
	private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static void save(Entity entity) {
		datastore.put(entity);
	}

	public static Entity read(Key key) {
		try {
			return datastore.get(key);
		} catch (EntityNotFoundException e) {
		}
		return null;
	}

	public static Key genKey(String entity, String key) {
		return KeyFactory.createKey(entity, key);
	}

	public static void delete(Key key) {
		datastore.delete(key);
	}
}
