package br.com.shellcode.instaclone.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

public abstract class AbstractGenericDao<T> implements GenericDao<T> {

	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;

	public EntityManager getEm() {
		em = EMF.get().createEntityManager();
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@PreDestroy
	private void destroyEm() {
		if (em != null && em.isOpen())
			em.close();
	}

	public Object getPkValue(T obj) {
		Class<?> clazz = obj.getClass();
		Method[] methods = clazz.getMethods();
		Field[] fields = clazz.getDeclaredFields();
		Method method = null;
		Field field = null;
		Object result = null;
		for (Field f : fields) {
			if (f.isAnnotationPresent(Id.class)) {
				field = f;
				break;
			}
		}
		if (field == null)
			for (Method m : methods) {
				if (m.isAnnotationPresent(Id.class)) {
					method = m;
					break;
				}
			}
		try {
			if (field != null) {
				field.setAccessible(true);
				result = field.get(obj);
			} else if (method != null) {
				result = method.invoke(obj);
			}
			return result;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public T create(T obj) {
		getEm().persist(obj);
		return obj;
	}

	@Override
	public T readByObject(T obj) {
		Class<?> clazz = obj.getClass();
		Object pkValue = getPkValue(obj);
		@SuppressWarnings("unchecked")
		T ret = (T) getEm().find(clazz, pkValue);
		return ret;
	}

	/**
	 * @param pk Primary key mapped with @Id on model class.
	 * @return Model class managed by DAO
	 */
	@Override
	public T read(Object pk) {
		return getEm().find(getType(), convertedId(pk));
	}

	@Override
	public T read(Object pk, Map<String, Object> hints) {
		return getEm().find(getType(), convertedId(pk), hints);
	}

	@Override
	@Transactional
	public void merge(T obj) {
		getEm().merge(obj);
	}

	@Override
	@Transactional
	public void delete(T obj) {
		getEm().remove(obj);
	}

	@Override
	@Transactional
	public void deleteByPk(T obj) {
		T val = read(getPkValue(obj));
		getEm().remove(val);
	}

	@Override
	public long count() {
		Class<T> clazz = getType();
		if (clazz != null) {
			CriteriaBuilder cb = getEm().getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			cq.select(cb.count(cq.from(clazz)));
			return getEm().createQuery(cq).getSingleResult();
		}
		return 0;
	}

	@Override
	public List<T> list() {
		return listOffset(null, null);
	}

	@Override
	public List<T> listOffset(Integer first, Integer max) {
		return filter(first, max, null, null, null);
	}

	@Override
	public List<T> filter(Integer first, Integer max, String sortField, String sortOrder, Map<String, Object> filters) {

		Class<T> clazz = getType();
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> rootEntry = cq.from(clazz);
		CriteriaQuery<T> all = cq.select(rootEntry);
		Predicate whereFilter = genWhereFilter(filters, cb, rootEntry);

		TypedQuery<T> allQuery = null;
		if (filters != null) {
			all = (all.where(whereFilter));
		}

		/**
		 * Sort
		 */
		if (sortField != null) {
			Path<Object> sortFieldPath = rootEntry.get(sortField);
			if (sortOrder == null || sortOrder.equalsIgnoreCase("ASC")) {
				all = all.orderBy(cb.asc(sortFieldPath));
			} else {
				all = all.orderBy(cb.desc(sortFieldPath));
			}
		}
		allQuery = getEm().createQuery(all);

		/**
		 * Offset
		 */
		if (first != null)
			allQuery.setFirstResult(first);
		if (max != null)
			allQuery.setMaxResults(max);

		return allQuery.getResultList();
	}

	@Override
	public Long filterCount(int start, int size, Map<String, Object> filters) {
		Class<T> clazz = getType();
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> rootEntry = cq.from(clazz);
		Predicate whereFilter = genWhereFilter(filters, cb, rootEntry);
		cq.select(cb.count(rootEntry));
		if (whereFilter != null)
			cq.where(whereFilter);
		return getEm().createQuery(cq).getSingleResult();
	}

	private Predicate genWhereFilter(Map<String, Object> filters, CriteriaBuilder cb, Root<T> rootEntry) {
		Predicate whereFilter = null;
		/**
		 * Filters where com AND
		 */
		if (filters != null) {
			List<Predicate> filtersArray = new ArrayList<Predicate>();
			for (String k : filters.keySet()) {
				Predicate f = null;
				Object obj = filters.get(k);
				Object filter;
				// if (obj instanceof String) {
				if (rootEntry.get(k).getJavaType().equals(String.class)) {
					filter = new String("%" + obj + "%").toUpperCase();
					f = cb.like(cb.upper(rootEntry.get(k)), (String) filter);
				} else {
					filter = obj;
					f = cb.equal(rootEntry.get(k), filter);
				}
				filtersArray.add(f);
			}
			whereFilter = cb.and(filtersArray.toArray(new Predicate[filters.size()]));
		}
		return whereFilter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		ParameterizedType genericSuperclass = (ParameterizedType) getProxiedClass(this).getGenericSuperclass();
		Type[] clz = genericSuperclass.getActualTypeArguments();
		if (clz != null && clz.length > 0) {
			Type tp = clz[0];
			Class<T> clazz = null;
			try {
				clazz = (Class<T>) Class.forName(tp.getTypeName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return clazz;
		}
		return null;
	}

	protected Object convertedId(Object valueId) {
		Class<?> pkType = getPkType();
		try {
			Method declaredMethod = pkType.getDeclaredMethod("valueOf", String.class);
			return declaredMethod.invoke(null, valueId);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
		}
		return valueId;
	}

	@Override
	public Class<?> getPkType() {
		Class<T> clazz = getType();
		Method[] methods = clazz.getMethods();
		Field[] fields = clazz.getDeclaredFields();
		Method method = null;
		Field field = null;
		for (Field f : fields) {
			if (f.isAnnotationPresent(Id.class)) {
				field = f;
				break;
			}
		}
		if (field == null)
			for (Method m : methods) {
				if (m.isAnnotationPresent(Id.class)) {
					method = m;
					break;
				}
			}
		try {
			if (field != null) {
				field.setAccessible(true);
				return field.getType();
			} else if (method != null) {
				return method.getReturnType();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<?> getFieldType(String fieldName) {
		Class<T> clazz = getType();
		try {
			Field field = clazz.getDeclaredField(fieldName);
			if (field != null) {
				field.setAccessible(true);
				return field.getType();
			}
		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> find(String field, Object obj) {
		Class<T> clazz = getType();
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> rootEntry = cq.from(clazz);
		CriteriaQuery<T> all = cq.select(rootEntry);
		Object filter = null;
		Predicate where = null;
		if (obj instanceof String) {
			filter = new String("%" + obj + "%").toUpperCase();
			where = cb.like(cb.upper(rootEntry.get(field)), (String) filter);
		} else {
			filter = obj;
			where = cb.equal(rootEntry.get(field), filter);
		}
		TypedQuery<T> allQuery = getEm().createQuery(all.where(where));
		return allQuery.getResultList();
	}

	private Class<?> getProxiedClass(Object proxiedObject) {
		Class<?> proxiedClass = proxiedObject.getClass();
		if (proxiedClass.getName().matches(".*\\$Proxy\\$.*")) {
			proxiedClass = (Class<?>) proxiedClass.getGenericSuperclass();
		}
		return proxiedClass;
	}

	@Override
	public void validate(T obj) {
		Set<ConstraintViolation<T>> violations = validator.validate(obj);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}

}
