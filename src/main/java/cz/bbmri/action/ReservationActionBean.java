package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.entity.*;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.RolesAllowed;
import java.util.*;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/reservation/{$event}/{id}")
public class ReservationActionBean extends AbstractRequisitionActionBean {

    @SpringBean
    private ReservationDAO reservationDAO;

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private GlobalSettingDAO globalSettingDAO;

    @SpringBean
    private ProjectDAO projectDAO;

    @SpringBean
    private QuestionDAO questionDAO;

    @SpringBean
    private RequestDAO requestDAO;

    @SpringBean
    private NotificationDAO notificationDAO;

    private Long id;

    private Long projectId;

    @ValidateNestedProperties(value = {
            @Validate(on = {"confirmAdd"}, field = "specification", required = true)
    })
    private Reservation reservation;

    private MyPagedListHolder<Reservation> pagination = new MyPagedListHolder<Reservation>(new ArrayList<Reservation>());

    private Integer biobankId;

    private List<Integer> biobanks = new ArrayList<Integer>();

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(ReservationActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Reservation.reservations", active);
    }

    public static Breadcrumb getMyBreadcrumb(boolean active) {
        return new Breadcrumb(ReservationActionBean.class.getName(), "myReservations", false, "cz.bbmri.entity.Reservation.myReservations",
                active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Reservation reservation) {
        return new Breadcrumb(ReservationActionBean.class.getName(), "detail", false, "cz.bbmri.entity.Reservation.reservation",
                active, "id", reservation.getId());
    }

    public static Breadcrumb getAddBreadcrumb(boolean active) {
        return new Breadcrumb(ReservationActionBean.class.getName(), "add", false, "cz.bbmri.entity.Reservation.add",
                active);
    }

    public Integer getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Integer biobankId) {
        this.biobankId = biobankId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        if (reservation == null) {
            if (id != null) {
                reservation = reservationDAO.get(id);
            }
        }

        return reservation;
    }

    public MyPagedListHolder<Reservation> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Reservation> pagination) {
        this.pagination = pagination;
    }

    public List<Integer> getBiobanks() {
        return biobanks;
    }

    public void setBiobanks(List<Integer> biobanks) {
        this.biobanks = biobanks;
    }

    public boolean getIsMyReservation() {
        return getReservation().getUser().equals(getLoggedUser());
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<Project> getProjects() {
        return projectDAO.getByUser(getLoggedUser());
    }

    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"developer"})
    public Resolution all() {

        getBreadcrumbs().add(ReservationActionBean.getAllBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(new ArrayList<Reservation>(reservationDAO.all()));
        pagination.setEvent("all");

        return new ForwardResolution(View.Reservation.ALL);

    }


    @HandlesEvent("myReservations")
    @RolesAllowed({"authorized"})
    public Resolution myReservations() {

        getBreadcrumbs().add(ReservationActionBean.getMyBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(new ArrayList<Reservation>(getLoggedUser().getReservation()));
        pagination.setEvent("myReservations");

        return new ForwardResolution(View.Reservation.ALL);

    }

    @HandlesEvent("detail")
    @RolesAllowed({"authorized if ${isMyReservation}", "developer", "biobank_operator"})
    public Resolution detail() {

        getReservation();

        if (reservation == null) {
            return new ForwardResolution(View.Reservation.NOTFOUND);
        }

        getBreadcrumbs().add(ReservationActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(ReservationActionBean.getDetailBreadcrumb(true, reservation));

        return new ForwardResolution(View.Reservation.DETAIL);
    }

    @HandlesEvent("add")
    @RolesAllowed({"authorized"})
    public Resolution add() {

        getBreadcrumbs().add(ReservationActionBean.getAddBreadcrumb(true));

        return new ForwardResolution(View.Reservation.ADD);

    }

    @HandlesEvent("confirmAdd")
    @RolesAllowed({"authorized"})
    public Resolution confirmAdd() {

        if (biobanks.isEmpty()) {
            getContext().getValidationErrors().
                    addGlobalError(new LocalizableError("cz.bbmri.ReservationActionBean.noBiobankSelected"));
            return new ForwardResolution(View.Reservation.ADD);
        }

        // For each selected biobank create separate reservation
        for (Integer integer : biobanks) {
            Reservation newReservation = new Reservation();
            newReservation.setSpecification(reservation.getSpecification());
            newReservation.setUser(getLoggedUser());

            Biobank biobank = biobankDAO.get(integer);

            newReservation.setBiobank(biobank);

            // Today date
            Calendar cal = Calendar.getInstance();

            // Add period for which is reservation valid
            cal.add(Calendar.MONTH, globalSettingDAO.getReservationValidity());

            newReservation.setValidation(cal.getTime());

            newReservation = reservationDAO.save(newReservation);

            // Notification for biobank users
            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.ReservationActionBean.created",
                    newReservation.getUser().getWholeName(), biobank.getAcronym());

            notificationDAO.create(biobank.getOtherBiobankUser(getLoggedUser()),
                    NotificationType.RESERVATION_DETAIL, locMsg, newReservation.getId());
        }

        return new RedirectResolution(ReservationActionBean.class, "myReservations");
    }

    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${biobankExecutor}"})
    public Resolution approve() {
        getReservation();

        if (!reservation.getIsNew()) {
            return new ForwardResolution(View.Reservation.NOTFOUND);
        }

        reservation.setReservationState(ReservationState.APPROVED);
        reservation.setLastModification(new Date());

        reservation = reservationDAO.save(reservation);

        // Notification for biobank users
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.ReservationActionBean.changeState",
                reservation.getId(), ReservationState.APPROVED);

        notificationDAO.create(reservation.getUser(),
                NotificationType.RESERVATION_DETAIL, locMsg, reservation.getId());

        return new RedirectResolution(ReservationActionBean.class, "detail").addParameter("id", reservation.getId());
    }

    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${biobankExecutor}"})
    public Resolution deny() {
        getReservation();

        if (!reservation.getIsNew()) {
            return new ForwardResolution(View.Reservation.NOTFOUND);
        }

        reservation.setReservationState(ReservationState.DENIED);
        reservation.setLastModification(new Date());

        reservation = reservationDAO.save(reservation);

        // Notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.ReservationActionBean.changeState",
                reservation.getId(), ReservationState.DENIED);

        notificationDAO.create(reservation.getUser(),
                NotificationType.RESERVATION_DETAIL, locMsg, reservation.getId());

        return new RedirectResolution(ReservationActionBean.class, "detail").addParameter("id", reservation.getId());
    }

    @HandlesEvent("confirm")
    @RolesAllowed({"biobank_operator if ${biobankExecutor}"})
    public Resolution confirm() {
        getReservation();

        if (!reservation.getIsApproved()) {
            return new ForwardResolution(View.Reservation.NOTFOUND);
        }

        reservation.setReservationState(ReservationState.CONFIRMED);
        reservation.setLastModification(new Date());

        // Expiration date is updated

        // Today date
        Calendar cal = Calendar.getInstance();

        // Add period for which is reservation valid
        cal.add(Calendar.MONTH, globalSettingDAO.getReservationValidity());

        reservation = reservationDAO.save(reservation);

        // Notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.ReservationActionBean.changeState",
                reservation.getId(), ReservationState.CONFIRMED);

        notificationDAO.create(reservation.getUser(),
                NotificationType.RESERVATION_DETAIL, locMsg, reservation.getId());

        return new RedirectResolution(ReservationActionBean.class, "detail").addParameter("id", reservation.getId());
    }

    @HandlesEvent("assignToProject")
    @RolesAllowed({"authorized"})
    public Resolution assignToProject() {
        getReservation();

        if (!reservation.getIsConfirmed()) {
            return new ForwardResolution(View.Reservation.NOTFOUND);
        }

        Project project = projectDAO.get(projectId);

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        if (!project.getIsConfirmed()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.ReservationActionBean.notConfirmed"));
            return new ForwardResolution(ReservationActionBean.class, "detail").addParameter("id", reservation.getId());
        }

        Question question = new Question();
        question.setProject(project);
        question.setQuestionState(QuestionState.APPROVED);
        question.setBiobank(reservation.getBiobank());
        question = questionDAO.save(question);

        for (Request request : reservation.getRequest()) {
            request.setReservation(null);
            request.setQuestion(question);
            requestDAO.update(request);
        }

         // Notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.ReservationActionBean.assignToProject",
                reservation.getId(), reservation.getUser(), project.getName());

        notificationDAO.create(reservation.getUser(),
               NotificationType.QUESTION_DETAIL, locMsg, question.getId());

        reservationDAO.remove(reservation);

        return new RedirectResolution(ProjectActionBean.class, "questions").addParameter("id", project.getId());
    }


}

