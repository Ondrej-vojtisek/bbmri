package cz.bbmri.facade.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.facade.exceptions.InsuficientAmountOfSamplesException;
import cz.bbmri.service.NotificationService;
import cz.bbmri.service.RequestService;
import cz.bbmri.service.SampleQuestionService;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.1.14
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
//@Controller("requestFacade")
public class RequestFacadeImpl {

//    @Autowired
//    private SampleQuestionService sampleQuestionService;
//
//    @Autowired
//    private NotificationDao notificationDao;
//
//    @Autowired
//    private RequestService requestService;
//
//    @Autowired
//    private UserService userService;
//
//    public SampleQuestion getSampleQuestion(Long sampleQuestionId) {
//        return sampleQuestionService.get(sampleQuestionId);
//    }
//

//
//
//

//
//
//

//
//

//
//    public List<SampleQuestion> getNewSampleRequests(Long biobankId) {
//        return sampleQuestionService.getSampleRequests(biobankId, RequestState.NEW);
//    }
//
//
//

//
//    public List<SampleReservation> getSampleReservations(Long userId) {
//        notNull(userId);
//
//        User userDB = userService.get(userId);
//        //   User userDB = userService.eagerGet(userId, false, false, false, false, true);
//
//        return userDB.getSampleReservations();
//    }
//


//

//
//
//    public List<SampleQuestion> getSortedSampleQuestions(Long biobankId, String orderByParam, boolean desc) {
//        if (biobankId == null) {
//            logger.debug("BiobankId can't be null");
//            return null;
//        }
//        return sampleQuestionService.getSortedSampleQuestions(biobankId, orderByParam, desc);
//    }
//

//
//    public List<SampleReservation> getSortedSampleReservation(Long userId, String orderByParam, boolean desc){
//        return sampleQuestionService.getSortedSampleReservations(userId, orderByParam, desc);
//    }
}
