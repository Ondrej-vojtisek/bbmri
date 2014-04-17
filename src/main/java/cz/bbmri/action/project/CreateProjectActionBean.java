package cz.bbmri.action.project;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.AttachmentType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.AttachmentService;
import cz.bbmri.service.ProjectAdministratorService;
import cz.bbmri.service.ProjectService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@HttpCache(allow = false)
@Wizard(startEvents = {"initial", "mta"})
@UrlBinding("/project/create/{$event}/{projectId}")
public class CreateProjectActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private ProjectAdministratorService projectAdministratorService;

    @SpringBean
    private AttachmentService attachmentService;

    public CreateProjectActionBean() {
        //default
        setComponentManager(new ComponentManager());
    }

    @ValidateNestedProperties(value = {
            @Validate(on = {"confirmStep2"},
                    field = "name",
                    required = true),
            @Validate(on = {"confirmStep2"}, field = "principalInvestigator",
                    required = true),
            @Validate(on = {"confirmStep2"}, field = "homeInstitution",
                    required = true),
            @Validate(on = {"confirmStep3"},
                    field = "fundingOrganization",
                    required = true),
            @Validate(on = {"confirmStep3"},
                    field = "approvedBy",
                    required = true),
            @Validate(on = {"confirmStep3"}, field = "approvalStorage",
                    required = true),
            @Validate(on = {"confirmStep3"}, field = "approvalDate",
                    required = true),
            @Validate(on = {"confirmStep4"}, field = "annotation",
                    required = true)

    })
    private Project project;

    @Validate(on = {"confirmStep6"}, required = true)
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

    /* First step. Introduction */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("initial")
    public Resolution initial() {
        return new ForwardResolution(PROJECT_CREATE_INIT);
    }

    /* Second step. */
    @HandlesEvent("confirmStep1")
    public Resolution confirmStep1() {
        return new ForwardResolution(PROJECT_CREATE_GENERAL);
    }

    /* Second step. */
    @HandlesEvent("backFromStep3")
    public Resolution backFromStep3() {
        return new ForwardResolution(PROJECT_CREATE_GENERAL);
    }

    /* Confirm second step. */
    @HandlesEvent("confirmStep2")
    public Resolution confirmStep2() {
        return new ForwardResolution(PROJECT_CREATE_FINANCED);
    }

    /* Third step. */
    @HandlesEvent("backFromStep4")
    public Resolution backFromStep4() {
        return new ForwardResolution(PROJECT_CREATE_FINANCED);
    }

    /* Confirm third step. */
    @HandlesEvent("confirmStep3")
    public Resolution confirmStep3() {
        return new ForwardResolution(PROJECT_CREATE_ANNOTATION);
    }

    /* 4th step. */
    @HandlesEvent("backFromStep5")
    public Resolution backFromStep5() {
        return new ForwardResolution(PROJECT_CREATE_ANNOTATION);
    }

    /* Confirm 4th step. */
    @HandlesEvent("confirmStep4")
    public Resolution confirmStep4() {
        return new ForwardResolution(PROJECT_CREATE_CONFIRM);
    }

    /* 5th step. */
    @HandlesEvent("confirmStep5")
    public Resolution confirmStep5() {
//        project = projectFacade.createProject(project, getContext().getMyId(),
//                getContext().getPropertiesStoragePath(),
//                getContext().getValidationErrors());
        //  return new RedirectResolution(this.getClass(), "mta").addParameter("id", project.getId());
        return new ForwardResolution(PROJECT_CREATE_MTA);
    }

    /* 6th step. */
    @HandlesEvent("backFromStep6")
    public Resolution backFromStep6() {
        return new ForwardResolution(PROJECT_CREATE_CONFIRM);
    }

    /* 6th step. Confirm.*/
    @HandlesEvent("confirmStep6")
    public Resolution confirmStep6() {

        boolean resultBool;

        if (!projectService.create(project,
                        getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "mta");
        }

        resultBool = projectAdministratorService.assignAdministrator(project.getId(),
                getContext().getMyId(),
                Permission.MANAGER,
                getContext().getValidationErrors(),
                getContext().getMyId());

        if(!resultBool){
            return new ForwardResolution(this.getClass(), "mta");
        }

        int result = attachmentService.createAttachment(attachmentFileBean,
                AttachmentType.MATERIAL_TRANSFER_AGREEMENT,
                project.getId(),
                getContext().getValidationErrors(),
                getContext().getMyId());

        if (result < 0) {
            return new ForwardResolution(PROJECT_CREATE_MTA);
        }

        if (result == 1) {
            getContext().getMessages().add(new LocalizableMessage("bbmri.action.CreateProjectActionBean.AttachmentOverwritten"));
            return new ForwardResolution(PROJECT_CREATE_MTA);
        }

        if (result == 0) {
            successMsg();
        }

        return new RedirectResolution(ProjectActionBean.class);
    }
}
