package bbmri.action.project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Attachment;
import bbmri.entities.Project;
import bbmri.entities.enumeration.AttachmentType;
import bbmri.facade.ProjectFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */

@Wizard(startEvents = {"initial", "mta"})
@UrlBinding("/project/create/{$event}/{id}")
public class CreateProjectActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private ProjectFacade projectFacade;

    @ValidateNestedProperties(value = {
            @Validate(on = {"confirmGeneral"},
                    field = "name",
                    required = true),
            @Validate(on = {"confirmGeneral"}, field = "mainInvestigator",
                    required = true),
            @Validate(on = {"confirmGeneral"}, field = "homeInstitution",
                    required = true),
            @Validate(on = {"financedConfirm"},
                    field = "fundingOrganization",
                    required = true),
            @Validate(on = {"financedConfirm"},
                    field = "approvedBy",
                    required = true),
            @Validate(on = {"financedConfirm"}, field = "approvalStorage",
                    required = true),
         /*  @Validate(on = {"financedConfirm"}, field = "created",
                    required = true), */
            @Validate(on = {"annotationConfirm"}, field = "annotation",
                    required = true)

    })
    private Project project;

    /* New project identifier. Used to connect MTA to it. */
    private Long id;

    @Validate(on = {"mtaUpload"}, required = true)
    private FileBean attachmentFileBean;

    private Attachment attachment;


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public FileBean getAttachmentFileBean() {
        return attachmentFileBean;
    }

    public void setAttachmentFileBean(FileBean attachmentFileBean) {
        this.attachmentFileBean = attachmentFileBean;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /* First step. Introduction */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("initial")
    public Resolution initial() {
        return new ForwardResolution(PROJECT_CREATE_INIT);
    }

    /* Second step. */
    @HandlesEvent("general")
    public Resolution general() {
        return new ForwardResolution(PROJECT_CREATE_GENERAL);
    }

    /* Confirm second step. */
    @HandlesEvent("confirmGeneral")
    public Resolution confirmGeneral() {
        logger.debug("Project: " + project);
        return new ForwardResolution(PROJECT_CREATE_FINANCED);
    }

    /* Third step. */
    @HandlesEvent("financed")
    public Resolution financed() {
        return new ForwardResolution(PROJECT_CREATE_FINANCED);
    }

    /* Confirm third step. */
    @HandlesEvent("financedConfirm")
    public Resolution financedConfirm() {
        logger.debug("Project: " + project);
        return new ForwardResolution(PROJECT_CREATE_ANNOTATION);
    }

    /* 4th step. */
    @HandlesEvent("annotation")
    public Resolution annotation() {
        return new ForwardResolution(PROJECT_CREATE_ANNOTATION);
    }

    /* Confirm 4th step. */
    @HandlesEvent("annotationConfirm")
    public Resolution annotationConfirm() {
        logger.debug("Project: " + project);
        return new ForwardResolution(PROJECT_CREATE_CONFIRM);
    }



    /* 5th step. */
    @HandlesEvent("confirm")
    public Resolution confirm() {
        project = projectFacade.createProject(project, getContext().getMyId());
        return new RedirectResolution(this.getClass(), "mta").addParameter("id", project.getId());
    }

    /* 6th step. */
    @HandlesEvent("mta")
    public Resolution mta() {
        return new ForwardResolution(PROJECT_CREATE_MTA);
    }

    /* 6th step. Confirm.*/
    @HandlesEvent("mtaUpload")
    public Resolution mtaUpload() {
        // TODO: obalit try catch, pridat confirm hlasky
        projectFacade.createAttachment(attachmentFileBean, AttachmentType.MATERIAL_TRANSFER_AGREEMENT, id);
        return new RedirectResolution(ProjectActionBean.class);
    }

}
