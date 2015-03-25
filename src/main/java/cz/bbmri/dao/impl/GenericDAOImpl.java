package cz.bbmri.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public abstract class GenericDAOImpl<T> extends BaseDAOImpl {

    private Class<T> entityClass;



    GenericDAOImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Transactional(readOnly = true)
    public List<T> all() {

        return (List<T>) getCurrentSession().createCriteria(entityClass).list();
    }



    @Transactional(readOnly = false)
    public T save(T t) {
        getCurrentSession().saveOrUpdate(t);
        return t;
    }

    @Transactional(readOnly = true)
    public boolean refresh(T t) {

        // Perform the desired operation
        getCurrentSession().refresh(t);

        // Force result
        return true;
    }

    @Transactional(readOnly = true)
    public boolean evict(T t) {

        // Perform the desired operation
        getCurrentSession().evict(t);

        // Force result
        return true;
    }

    @Transactional(readOnly = true)
    public int count() {
        if (all() == null) {
            return -1;
        }

        return all().size();
    }

    @Transactional(readOnly = true)
    public List<T> allOrderedBy(String orderByParam, boolean desc) {
        if (orderByParam == null) {
            System.err.println("orderByParam was null");
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

        Criteria c = getCurrentSession().createCriteria(entityClass);
        if (desc) {
            c.addOrder(Order.desc(orderByParam));
        } else {
            c.addOrder(Order.asc(orderByParam));
        }

        return c.list();
    }

    @Transactional(readOnly = false)
    public void remove(T t){
        getCurrentSession().delete(t);
    }


}
