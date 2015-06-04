package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.entity.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import java.util.List;

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
    private QuestionDAO questionDAO;

    @SpringBean
    private SampleDAO sampleDAO;

    @SpringBean
    private QuantityDAO quantityDAO;

    private Reservation reservation;

    private Question question;

    private Withdraw withdraw;

    @Validate(required = true, on = {"remove", "decrease", "increase"})
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

    public Withdraw getWithdraw() {
        if (withdraw == null) {
            if (withdrawId != null) {
                withdraw = withdrawDAO.get(withdrawId);
            }
        }

        return withdraw;
    }

    public Question getQuestion() {
        if (question == null) {
            if (questionId != null) {
                question = questionDAO.get(questionId);
            }
        }

        return question;
    }

    public Reservation getReservation() {
        if (reservation == null) {
            if (reservationId != null) {
                reservation = reservationDAO.get(reservationId);
            }
        }

        return reservation;
    }

    @HandlesEvent("remove")
    public Resolution remove() {

        if (requestId == null) {

            return new ForwardResolution(View.Request.NOTFOUND);
        }

        Request request = requestDAO.get(requestId);

        if (request == null) {

            return new ForwardResolution(View.Request.NOTFOUND);
        }

        short number = request.getNumber();
        Sample sample = request.getSample();

        reservation = request.getReservation();
        withdraw = request.getWithdraw();
        question = request.getQuestion();

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

            return new ForwardResolution("/webpages/request/component/table.jsp");
        }
        quantityDAO.save(quantity);

        requestDAO.remove(request);

        getContext().getResponse().setHeader("X-Stripes-Success", "true");

        return new ForwardResolution("/webpages/request/component/table.jsp");
    }

    @HandlesEvent("decrease")
    public Resolution decrease() {

        if (requestId == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        Request request = requestDAO.get(requestId);

        if (request == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        Sample sample = request.getSample();

        reservation = request.getReservation();
        withdraw = request.getWithdraw();
        question = request.getQuestion();

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
        // we want to decrease request - so the number of available aliquotes is increased
        if (!quantity.increaseAmount(Request.IMPLICIT_REQUESTED_SAMPLES)) {

            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.RequestActionBean.error"));

            return new ForwardResolution("/webpages/request/component/table.jsp");
        }
        quantityDAO.save(quantity);

        request.decrease();

        // less then 1 requested - no need to preserve such request
        if (request.getNumber() < 1) {
            requestDAO.remove(request);
        } else {
            requestDAO.update(request);
        }

        getContext().getResponse().setHeader("X-Stripes-Success", "true");
        return new ForwardResolution("/webpages/request/component/table.jsp");
    }

    @HandlesEvent("increase")
    public Resolution increase() {
        if (requestId == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        Request request = requestDAO.get(requestId);

        if (request == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        Sample sample = request.getSample();

        reservation = request.getReservation();
        withdraw = request.getWithdraw();
        question = request.getQuestion();

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

            return new ForwardResolution("/webpages/request/component/table.jsp");
        }
        quantityDAO.save(quantity);

        request.increase();
        requestDAO.update(request);

        getContext().getResponse().setHeader("X-Stripes-Success", "true");

        return new ForwardResolution("/webpages/request/component/table.jsp");
    }

    @HandlesEvent("refresh")
    public Resolution refresh() {

        getContext().getResponse().setHeader("X-Stripes-Success", "true");
        return new ForwardResolution("/webpages/request/component/table.jsp");
    }

    @HandlesEvent("printRequests")
    public Resolution printRequests() {

        withdraw = getWithdraw();

        question = getQuestion();

        if (withdraw == null && question == null) {
            return new ForwardResolution(View.Request.NOTFOUND);
        }

        return new ForwardResolution("/webpages/request/component/export.jsp");

    }

}
