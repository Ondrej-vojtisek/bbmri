package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.entity.*;
import cz.bbmri.entity.constant.Constant;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/samplesearch/{$event}/{sampleId}")
public class SampleSearchActionBean extends AuthorizationActionBean {

    @SpringBean
    private SampleDAO sampleDAO;

    @SpringBean
    private ReservationDAO reservationDAO;

    @SpringBean
    private QuestionDAO questionDAO;

    @SpringBean
    private WithdrawDAO withdrawDAO;

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private RequestDAO requestDAO;

    // Search params
    @SpringBean
    private SexDAO sexDAO;

    @SpringBean
    private RetrievedDAO retrievedDAO;

    @SpringBean
    private MaterialTypeDAO materialTypeDAO;

    @SpringBean
    private QuantityDAO quantityDAO;

    @SpringBean
    private DiagnosisDAO diagnosisDAO;


    public Integer sexId;
    public Integer retrievedId;
    public Integer materialTypeId;
    public Integer biobankId;
    public String diagnosisKey;
    public Short available;


    // public variables for access from jQuery

    public Long reservationId;

    public Long questionId;

    public Long withdrawId;

    public Long sampleId;

    public Reservation getReservation() {
        if (reservationId == null) {
            return null;
        }

        return reservationDAO.get(reservationId);
    }

    public Question getQuestion() {
        if (questionId == null) {
            return null;
        }

        return questionDAO.get(questionId);
    }

    public Withdraw getWithdraw() {
        if (withdrawId == null) {
            return null;
        }

        return withdrawDAO.get(withdrawId);
    }

    public List<Sex> getSex() {
        return sexDAO.all();
    }

    public List<MaterialType> getMaterialTypes() {
        return materialTypeDAO.all();
    }

    public List<Retrieved> getRetrieved() {
        return retrievedDAO.all();
    }

    public List<String> getDiagnosis() {
        return diagnosisDAO.getUniqueDiagnosis();
    }


    private List<Sample> samples = new ArrayList<Sample>();

    public List<Sample> getSamples() {
        return samples;
    }

    public Resolution findSamples() {

        Biobank biobank = biobankDAO.get(biobankId);
        if (biobank == null) {

            samples = new ArrayList<Sample>();
            getContext().getResponse().setHeader("X-Stripes-Success", "true");
            return new ForwardResolution("/webpages/request/component/samples.jsp");
        }

        Retrieved retrieved = null;
        if (retrievedId != null) {
            retrieved = retrievedDAO.get(retrievedId);
        }

        Sex sex = null;
        if (sexId != null) {
            sex = sexDAO.get(sexId);
        }

        MaterialType materialType = null;
        if (materialTypeId != null) {
            materialType = materialTypeDAO.get(materialTypeId);
        }

        if (sex != null || retrieved != null || materialType != null || diagnosisKey != null) {

            samples = sampleDAO.find(biobank, retrieved, sex, materialType, diagnosisKey);
        }
        getContext().getResponse().setHeader("X-Stripes-Success", "true");
        return new ForwardResolution("/webpages/request/component/samples.jsp");
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

            return new ForwardResolution("/webpages/request/component/samples.jsp");
        }
        quantityDAO.save(quantity);

        Request request = new Request();
        request.setNumber(Request.IMPLICIT_REQUESTED_SAMPLES);
        request.setReservation(reservation);
        request.setSample(sample);

        requestDAO.create(request);

        getContext().getResponse().setHeader("X-Stripes-Success", "true");
        return new ForwardResolution("/webpages/request/component/samples.jsp");
    }

    @HandlesEvent("addToQuestion")
    public Resolution addToQuestion() {

        Sample sample = sampleDAO.get(sampleId);

        if (sample == null) {
            return new ForwardResolution(View.Sample.NOTFOUND);
        }

        Question question = questionDAO.get(questionId);

        if (question == null) {
            return new ForwardResolution(View.Question.NOTFOUND);
        }

        Quantity quantity = sample.getQuantity();

        if (quantity == null) {

            return new ForwardResolution(View.Quantity.NOTFOUND);
        }

        // check if there is enough samples
        if (!quantity.decreaseAmount(Request.IMPLICIT_REQUESTED_SAMPLES)) {

            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.RequestActionBean.notEnoughSamples"));

            return new ForwardResolution("/webpages/request/component/samples.jsp");
        }
        quantityDAO.save(quantity);

        Request request = new Request();
        request.setNumber(Request.IMPLICIT_REQUESTED_SAMPLES);
        request.setQuestion(question);
        request.setSample(sample);

        requestDAO.create(request);

        getContext().getResponse().setHeader("X-Stripes-Success", "true");
        return new ForwardResolution("/webpages/request/component/samples.jsp");
    }

    @HandlesEvent("addToWithdraw")
    public Resolution addToWithdraw() {

        Sample sample = sampleDAO.get(sampleId);

        if (sample == null) {
            return new ForwardResolution(View.Sample.NOTFOUND);
        }

        Withdraw withdraw = withdrawDAO.get(withdrawId);

        if (withdraw == null) {
            return new ForwardResolution(View.Withdraw.NOTFOUND);
        }

        Quantity quantity = sample.getQuantity();

        if (quantity == null) {

            return new ForwardResolution(View.Quantity.NOTFOUND);
        }

        // check if there is enough samples
        if (!quantity.decreaseAmount(Request.IMPLICIT_REQUESTED_SAMPLES)) {

            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.RequestActionBean.notEnoughSamples"));

            return new ForwardResolution("/webpages/request/component/samples.jsp");
        }
        quantityDAO.save(quantity);

        Request request = new Request();
        request.setNumber(Request.IMPLICIT_REQUESTED_SAMPLES);
        request.setWithdraw(withdraw);
        request.setSample(sample);

        requestDAO.create(request);

        getContext().getResponse().setHeader("X-Stripes-Success", "true");
        return new ForwardResolution("/webpages/request/component/samples.jsp");
    }




}
