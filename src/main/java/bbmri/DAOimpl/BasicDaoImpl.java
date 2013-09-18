package bbmri.daoImpl;

import bbmri.dao.BasicDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public abstract class BasicDaoImpl<T> implements BasicDao<T> {

    @PersistenceContext
    protected EntityManager em;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Class<T> entityClass;

    public BasicDaoImpl() {
         entityClass = (Class<T>)((ParameterizedType) getClass()
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
    /*
    public boolean contains(Long id) {
               notNull(id);
               return em.find(entityClass, id) != null;
           }
           */

    public List<T> all() {
        String stringQuery = "SELECT p FROM " + entityClass.getSimpleName() + " p";
        Query query = em.createQuery(stringQuery);
        return query.getResultList();
    }

    public Integer count() {
        String stringQuery = "SELECT COUNT (p) FROM " + entityClass.getSimpleName() + " p";
        Query query = em.createQuery(stringQuery);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    public static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object");
        }
    }

}
