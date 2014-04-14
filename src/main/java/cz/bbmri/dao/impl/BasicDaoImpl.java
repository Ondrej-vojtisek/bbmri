package cz.bbmri.dao.impl;

import cz.bbmri.dao.BasicDao;
import cz.bbmri.entities.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public abstract class BasicDaoImpl<T, E> implements BasicDao<T, E> {

    @PersistenceContext
    protected EntityManager em;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Class<T> entityClass;

    protected TypedQuery<T> typedQuery;

    public BasicDaoImpl() {
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

    public T get(E id) {
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

    public static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object");
        }
    }

    public List<T> allOrderedBy(String orderByParam, boolean desc) {
        if (orderByParam == null) {
            logger.debug("Given orderBy parameter was null;");
            return null;
        }

        // All attributes of class T

        Field[] fields = entityClass.getDeclaredFields();

        // String orderByParam;

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

//    public List<T> nOrderedBy(String orderByParam, boolean desc, int number) {
//        if (orderByParam == null) {
//            logger.debug("Given orderBy parameter was null");
//            return null;
//        }
//
//        if (number <= 0) {
//            logger.debug("Requested zero or less objects from database");
//            return null;
//        }
//
//        List<T> list = allOrderedBy(orderByParam, desc).subList(0, number);
//
//        // Are there less results than requested amount? If yes - return all in DB
//
//        if(list.size() < (number -1) ){
//            return list;
//        }
//
//        // Return only requested amount
//
//        return list.subList(0, number -1);
//
//    }

    protected T getSingleResult(){
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
