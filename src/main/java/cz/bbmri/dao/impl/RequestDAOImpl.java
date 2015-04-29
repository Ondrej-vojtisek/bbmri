package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.RequestDAO;
import cz.bbmri.entity.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("requestDAO")
@Transactional
public class RequestDAOImpl extends GenericDAOImpl<Request> implements RequestDAO {

    public Request get(Long id) {
        return (Request) getCurrentSession().get(Request.class, id);
    }

    private Request getBySample(Sample sample, Reservation reservation, Withdraw withdraw, Question question) {
        Criterion criterionReservation = Restrictions.eq(Request.PROP_RESERVATION, reservation);
        Criterion criterionWithdraw = Restrictions.eq(Request.PROP_WITHDRAW, withdraw);
        Criterion criterionQuestion = Restrictions.eq(Request.PROP_QUESTION, question);

        List<Request> list = null;

        if (reservation != null) {
            list = getCurrentSession().createCriteria(Request.class)
                    .add(criterionReservation)
                    .setMaxResults(1).list();
        } else if (withdraw != null) {
            list = getCurrentSession().createCriteria(Request.class)
                    .add(criterionWithdraw)
                    .setMaxResults(1).list();
        } else if (question != null) {
            list = getCurrentSession().createCriteria(Request.class)
                    .add(criterionQuestion)
                    .setMaxResults(1).list();
        }

        if(list == null){
            return null;
        }

        // Retrieve iterator from the list
        Iterator iterator = list.iterator();

        // Prepare the variable
        Request request = null;

        // If user loaded
        if (iterator.hasNext()) {

            // Store the user instance
            request = (Request) iterator.next();
        }
        return request;

    }

    @Override
    @Transactional(readOnly = false)
    public Request update(Request request) {
        getCurrentSession().update(request);
        return request;
    }

    @Override
    @Transactional(readOnly = false)
    public Request create(Request request) {
        Request requestDB = getBySample(request.getSample(),
                request.getReservation(),
                request.getWithdraw(),
                request.getQuestion());

        // Request for same sample already exists - better to update existing than create new one
        if (requestDB != null) {
            int number = requestDB.getNumber() + request.getNumber();
            requestDB.setNumber((short) number);
            getCurrentSession().saveOrUpdate(requestDB);
            return requestDB;
        }

        getCurrentSession().saveOrUpdate(request);
        return request;
    }
}
