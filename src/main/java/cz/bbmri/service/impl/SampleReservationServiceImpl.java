package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.SampleReservationService;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Transactional
@Service("sampleReservationService")
public class SampleReservationServiceImpl extends SampleQuestionServiceImpl implements SampleReservationService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private GlobalSettingDao globalSettingDao;

    @Transactional(readOnly = true)
    public List<SampleReservation> getSampleReservationsOrderedByDate() {
        return sampleQuestionDao.getSampleReservationsOrderedByDate();
    }

    @Transactional(readOnly = true)
    public List<SampleReservation> getSortedSampleReservations(Long userId, String orderByParam, boolean desc) {
        if (isNull(userId, "userId", null)) return null;

        User userDB = userDao.get(userId);
        if (isNull(userDB, "userDB", null)) return null;

        return sampleQuestionDao.getSampleReservationsSorted(userDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<SampleReservation> getSampleReservationsBySample(Long sampleId, String orderByParam, boolean desc) {
        if (isNull(sampleId, "sampleId", null)) return null;

        Sample sampleDB = sampleDao.get(sampleId);
        if (isNull(sampleDB, "sampleDB", null)) return null;

        return sampleQuestionDao.getSampleReservationsBySample(sampleDB, orderByParam, desc);
    }

    //   errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.assignReservationToProjectFailed"));
    public boolean assignReservationToProject(Long sampleReservationId, Long projectId, ValidationErrors errors) {
        notNull(errors);
        if (isNull(sampleReservationId, "sampleQuestionId", errors)) return false;
        if (isNull(projectId, "projectId", errors)) return false;

        SampleReservation sampleReservationDB = get(sampleReservationId);
        if (isNull(sampleReservationDB, "sampleReservationDB", errors)) return false;

        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", errors)) return false;

        SampleRequest sampleRequest = new SampleRequest();

        // Set values from sampleReservation
        sampleRequest.setBiobank(sampleReservationDB.getBiobank());
        sampleRequest.setCreated(sampleReservationDB.getCreated());
        sampleRequest.setSpecification(sampleReservationDB.getSpecification());
        sampleRequest.setRequestState(sampleReservationDB.getRequestState());

        // Actual date
        sampleRequest.setLastModification(new Date());

        // Association to project
        sampleRequest.setProject(projectDB);

        sampleQuestionDao.create(sampleRequest);

        for (Request request : sampleReservationDB.getRequests()) {
            request.setSampleQuestion(sampleRequest);
            requestDao.update(request);
        }

        // Delete sampleReservation
        sampleReservationDB.setBiobank(null);
        sampleQuestionDao.remove(sampleReservationDB);

        return true;
    }


    @Transactional(readOnly = true)
    public List<SampleQuestion> getSampleReservations(Long biobankId, RequestState requestState) {
        if (isNull(biobankId, "biobankId", null)) return null;
        if (isNull(requestState, "requestState", null)) return null;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        return sampleQuestionDao.getSampleReservations(biobankDB, requestState);
    }

    public boolean createSampleReservation(SampleReservation sampleReservation, Long biobankId, User user,
                                           ValidationErrors errors) {
        notNull(errors);
        if (isNull(sampleReservation, "sampleReservation", errors)) return false;
        if (isNull(biobankId, "biobankId", errors)) return false;
        if (isNull(user, "user", errors)) return false;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", errors)) return false;

        sampleReservation.setRequestState(RequestState.NEW);
        sampleReservation.setCreated(new Date());
        sampleReservation.setLastModification(new Date());
        sampleReservation.setBiobank(biobankDB);
        sampleReservation.setUser(user);

        try {
            sampleQuestionDao.create(sampleReservation);
        } catch (Exception ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.createSampleReservationFailed"));
        }

        return true;
    }

    public SampleReservation get(Long id) {
        if (isNull(id, "id", null)) return null;
        SampleQuestion sampleQuestion = sampleQuestionDao.get(id);

        if (!(sampleQuestion instanceof SampleReservation)) return null;

        return (SampleReservation) sampleQuestionDao.get(id);
    }

    public boolean close(Long Id, ValidationErrors errors) {
        notNull(errors);

        if (isNull(Id, "Id", null)) return false;
        SampleReservation sampleReservationDB = get(Id);
        if (isNull(sampleReservationDB, "sampleReservationDB", null)) return false;

        // only approved
        if (!sampleReservationDB.getRequestState().equals(RequestState.APPROVED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantCloseThisRequestState"));
            return false;
        }

        sampleReservationDB.setRequestState(RequestState.CLOSED);
        sampleReservationDB.setLastModification(new Date());
        sampleReservationDB.setValidity(validity());

        sampleQuestionDao.update(sampleReservationDB);

        return true;
    }

    private Date validity() {

        Date today = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        // add validity in months
        cal.add(Calendar.MONTH, globalSettingDao.getReservationValidity());

        // Validity one month
        return cal.getTime();
    }

    public boolean setAsExpired(Long Id) {

        if (isNull(Id, "Id", null)) return false;
        SampleReservation sampleReservationDB = get(Id);
        if (isNull(sampleReservationDB, "sampleReservationDB", null)) return false;

        // only close
        if (!sampleReservationDB.getRequestState().equals(RequestState.CLOSED)) {
            // this method is auto triggered so no need to send notification or error msg to frontend
            return false;
        }

        sampleReservationDB.setRequestState(RequestState.EXPIRED);
        sampleReservationDB.setLastModification(new Date());
        sampleReservationDB.setValidity(validity());

        sampleQuestionDao.update(sampleReservationDB);

        return true;
    }

}
