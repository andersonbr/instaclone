package br.com.shellcode.instaclone.dao;

import java.util.List;
import java.util.Map;

public interface GenericDao<T> {

	public T create(T obj);

	public T read(Object pk);

	public T read(Object pk, Map<String, Object> hints);

	public T readByObject(T obj);

	public void merge(T obj);

	public void delete(T obj);

	public void deleteByPk(T obj);

	public long count();

	public List<T> list();

	public List<T> listOffset(Integer first, Integer max);

	public Class<T> getType();

	public Class<?> getPkType();

	public void validate(T obj);

	public Class<?> getFieldType(String field);

	public List<T> find(String field, Object obj);

	public List<T> filter(Integer first, Integer max, String sortField, String sortOrder, Map<String, Object> filters);

	public Long filterCount(int start, int size, Map<String, Object> filters);

}
