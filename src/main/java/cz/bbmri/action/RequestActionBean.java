package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.entity.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/request/{$event}/{requestId}")
public class RequestActionBean extends AuthorizationActionBean {

    @SpringBean
    private RequestDAO requestDAO;

    @SpringBean
    private ReservationDAO reservationDAO;

    @SpringBean
    private WithdrawDAO withdrawDAO;

    @SpringBean
    private SampleDAO sampleDAO;

    @SpringBean
    private QuantityDAO quantityDAO;

    @Validate(required = true, on = {"remove"})
    private Long requestId;

    @Validate(required = true, on = {"addToReservation"})
    private Long sampleId;

    private Long withdrawId;

    @Validate(required = true, on = {"addToReservation"})
    private Long reservationId;

    private Long questionId;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Long getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Long withdrawId) {
        this.withdrawId = withdrawId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @HandlesEvent("addToReservation")
    public Resolution addToReservation() {
        Sample sample = sampleDAO.get(sampleId);

        if (sample == null) {
            return new ForwardResolution(View.Sample.NOTFOUND);
        }

        Reservation reservation = reservationDAO.get(reservationId);

        if (reservation == null) {
            return new ForwardResolution(View.Reservation.NOTFOUND);
        }

        Quantity quantity = sample.getQuantity();

        if (quantity == null) {

            return new ForwardResolution(View.Quantity.NOTFOUND);
        }

        // check if there is enough samples
        if (!quantity.decreaseAmount(Request.IMPLICIT_REQUESTED_SAMPLES)) {

            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.RequestActionBean.notEnoughSamples"));

            return new ForwardResolution(ReservationActionBean.class, "detail").addParameter("id", reservationId);
        }
        quantityDAO.save(quantity);

        Request request = new Request();
        request.setNumber(Request.IMPLICIT_REQUESTED_SAMPLES);
        request.setReservation(reservation);
        request.setSample(sample);

        requestDAO.create(request);


        return new RedirectResolution(ReservationActionBean.class, "detail").addParameter("id", reservationId);
    }

    @HandlesEvent("remove")
    public Resolution remove() {

        System.err.println("requestId: " + requestId);

        Request request = requestDAO.get(requestId);

        if (request == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        short number = request.getNumber();
        Sample sample = request.getSample();

        Reservation reservation = request.getReservation();
        Withdraw withdraw = request.getWithdraw();
        Question question = request.getQuestion();

        if (reservation == null && withdraw == null && question == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        if (sample == null) {
            return new ForwardResolution(View.Sample.NOTFOUND);
        }

        Quantity quantity = sample.getQuantity();

        if (quantity == null) {
            return new ForwardResolution(View.Quantity.NOTFOUND);
        }

        // check if there is enough samples
        if (!quantity.increaseAmount(number)) {

            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.RequestActionBean.error"));

            return proceedForward(reservation, withdraw, question);
        }
        quantityDAO.save(quantity);

        requestDAO.remove(request);

        return proceedRedirect(reservation, withdraw, question);
    }

    @HandlesEvent("decrease")
    public Resolution decrease() {
        Request request = requestDAO.get(requestId);

        if (request == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        Sample sample = request.getSample();

        Reservation reservation = request.getReservation();
        Withdraw withdraw = request.getWithdraw();
        Question question = request.getQuestion();

        if (reservation == null && withdraw == null && question == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        if (sample == null) {
            return new ForwardResolution(View.Sample.NOTFOUND);
        }

        Quantity quantity = sample.getQuantity();

        if (quantity == null) {
            return new ForwardResolution(View.Quantity.NOTFOUND);
        }

        // check if there is enough samples
        if (!quantity.increaseAmount(Request.IMPLICIT_REQUESTED_SAMPLES)) {

            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.RequestActionBean.error"));

            return proceedForward(reservation, withdraw, question);
        }
        quantityDAO.save(quantity);

        request.decrease();

        // less then 1 requested - no need to preserve such request
        if(request.getNumber() < 1){
            requestDAO.remove(request);
        }else{
            requestDAO.update(request);
        }

        return proceedRedirect(reservation, withdraw, question);
    }

    @HandlesEvent("increase")
    public Resolution increase() {
        Request request = requestDAO.get(requestId);

        if (request == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        Sample sample = request.getSample();

        Reservation reservation = request.getReservation();
        Withdraw withdraw = request.getWithdraw();
        Question question = request.getQuestion();

        if (reservation == null && withdraw == null && question == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        if (sample == null) {
            return new ForwardResolution(View.Sample.NOTFOUND);
        }

        Quantity quantity = sample.getQuantity();

        if (quantity == null) {
            return new ForwardResolution(View.Quantity.NOTFOUND);
        }

        // check if there is enough samples
        if (!quantity.decreaseAmount(Request.IMPLICIT_REQUESTED_SAMPLES)) {

            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.RequestActionBean.error"));

            return proceedForward(reservation, withdraw, question);
        }
        quantityDAO.save(quantity);

        request.increase();
        requestDAO.update(request);

        return proceedRedirect(reservation, withdraw, question);
    }

    private Resolution proceedRedirect(Reservation reservation, Withdraw withdraw, Question question) {
        if (reservation != null) {
            return new RedirectResolution(ReservationActionBean.class, "detail").addParameter("id", reservation.getId());
        }

        if (withdraw != null) {
            return new RedirectResolution(WithdrawActionBean.class, "detail").addParameter("id", withdraw.getId());
        }

        if (question != null) {
            return new RedirectResolution(QuestionActionBean.class, "detail").addParameter("id", question.getId());
        }

        return new ForwardResolution(View.Request.NOTFOUND);
    }

    private Resolution proceedForward(Reservation reservation, Withdraw withdraw, Question question) {
        if (reservation != null) {
            return new ForwardResolution(ReservationActionBean.class, "detail").addParameter("id", reservation.getId());
        }

        if (withdraw != null) {
            return new ForwardResolution(WithdrawActionBean.class, "detail").addParameter("id", withdraw.getId());
        }

        if (question != null) {
            return new ForwardResolution(QuestionActionBean.class, "detail").addParameter("id", question.getId());
        }

        return new ForwardResolution(View.Request.NOTFOUND);
    }

}
