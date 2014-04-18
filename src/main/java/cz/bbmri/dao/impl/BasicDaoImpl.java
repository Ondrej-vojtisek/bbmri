package cz.bbmri.dao.impl;

import cz.bbmri.dao.simple.BasicDao;
import org.springframework.dao.DataAccessException;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Generic implementation of basic CRUD operation on type type T.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public abstract class BasicDaoImpl<T> extends BaseForDao implements BasicDao<T> {


    private Class<T> entityClass;

    // object to execute JPQL queries
    TypedQuery<T> typedQuery;

    BasicDaoImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public void create(T t) {
        notNull(t);
        em.persist(t);
    }

    public void remove(T t) {
        notNull(t);
        em.remove(t);
    }

    public void update(T t) {
        notNull(t);
        em.merge(t);
    }

    public T get(Long id) {
        notNull(id);
        return em.find(entityClass, id);
    }

    public List<T> all() {
        String stringQuery = "SELECT p FROM " + entityClass.getSimpleName() + " p";
        typedQuery = em.createQuery(stringQuery, entityClass);
        return typedQuery.getResultList();
    }

    public Integer count() {

        String stringQuery = "SELECT COUNT (p) FROM " + entityClass.getSimpleName() + " p";
        int result = 0;
        try{
            Query query = em.createQuery(stringQuery);
            result = Integer.parseInt(query.getSingleResult().toString());
        }catch(DataAccessException ex){
            // message for developer
            logger.error("" + ex.getLocalizedMessage());
            // to make bad result significant to front-end
            result = -1;
        }
        return result;
    }


    public List<T> allOrderedBy(String orderByParam, boolean desc) {
        if (orderByParam == null) {
            logger.debug("Given orderBy parameter was null;");
            return null;
        }

        // All attributes of class T
        Field[] fields = entityClass.getDeclaredFields();

        boolean result = false;

        // Is there really any attribute with the same name as orderByParam on requested object?

        for (Field f : fields) {
            result = f.getName().equals(orderByParam);
            // There is a match!
            if (result) break;
        }

        // Not any match!
        if (!result) {
            return null;
        }

        // p represents object. Must match with SELECT _ FROM ...
        orderByParam = "p." + orderByParam;

        String stringQuery = "";

        if (desc) {
            // DESC order by
            stringQuery = "SELECT p FROM " + entityClass.getSimpleName() + " p ORDER BY " + orderByParam + " DESC";
        } else {
            // default order by
            stringQuery = "SELECT p FROM " + entityClass.getSimpleName() + " p ORDER BY " + orderByParam;

        }

        typedQuery = em.createQuery(stringQuery, entityClass);

        return typedQuery.getResultList();
    }

    T getSingleResult(){
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
