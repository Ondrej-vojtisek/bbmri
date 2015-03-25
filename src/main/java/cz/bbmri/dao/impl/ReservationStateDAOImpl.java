package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ReservationStateDAO;
import cz.bbmri.entity.ReservationState;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("reservationStateDAO")
@Transactional
public class ReservationStateDAOImpl extends GenericDAOImpl<ReservationState> implements ReservationStateDAO {

    public ReservationState get(Short id) {
                      return (ReservationState) getCurrentSession().get(ReservationState.class, id);
                  }
}
