package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ReservationDAO;
import cz.bbmri.entity.Reservation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("reservationDAO")
@Transactional
public class ReservationDAOImpl extends GenericDAOImpl<Reservation> implements ReservationDAO {

    public Reservation get(Long id) {
                      return (Reservation) getCurrentSession().get(Reservation.class, id);
                  }
}
