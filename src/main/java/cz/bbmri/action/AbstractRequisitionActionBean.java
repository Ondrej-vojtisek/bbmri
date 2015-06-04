package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.entity.Question;
import cz.bbmri.entity.Reservation;
import cz.bbmri.entity.Withdraw;

/**
 * Abstract class to enable shared module for printing sample request in Reservation, Question and Withdraw.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public abstract class AbstractRequisitionActionBean extends AuthorizationActionBean {

    public Reservation getReservation() {
        return null;
    }

    public Withdraw getWithdraw() {
        return null;
    }

    public Question getQuestion() {
        return null;
    }
}
