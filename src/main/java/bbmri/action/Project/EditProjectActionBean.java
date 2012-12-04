package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.Researcher;
import bbmri.service.ProjectService;
import bbmri.serviceImpl.ProjectServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/editproject/{$event}/{project.id}")
public class EditProjectActionBean extends BasicActionBean {
    @ValidateNestedProperties(value = {
            @Validate(on = {"create"}, field = "name", required = true,
                    minlength = 5, maxlength = 255),
            @Validate(on = {"create"}, field = "description", required = true,
                    minlength = 5, maxlength = 255),
            @Validate(on = {"create"}, field = "fundingOrganization", required = true,
                    minlength = 5, maxlength = 255),
    })
    private Project project;
    private ProjectService projectService;
    private List<Researcher> researchers;
    private Researcher researcher;
    private List<Long> selectedApprove;
    private List<Long> selected;
    private List<Researcher> freeResearchers;

    public List<Researcher> getFreeResearchers() {
        this.freeResearchers = getProjectService().getAllNotAssignedResearchers(getProject().getId());
        return freeResearchers;
    }

    public void setFreeResearchers(List<Researcher> freeResearchers) {
        this.freeResearchers = freeResearchers;
    }


    public List<Long> getSelectedApprove() {
        return selectedApprove;
    }

    public void setSelectedApprove(List<Long> selectedApprove) {
        this.selectedApprove = selectedApprove;
    }

    public List<Long> getSelected() {
        return selected;
    }

    public void setSelected(List<Long> selected) {
        this.selected = selected;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public List<Researcher> getResearchers() {
        this.researchers = getProjectService().getAllAssignedResearchers(project.getId());
        return researchers;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    public ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectServiceImpl();
        }
        return projectService;
    }

    public Project getProject() {
        if (project == null)
            project = getContext().getProject();
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/allProjects.jsp");
    }

    public Resolution update() {
        getProjectService().update(project);
        return new ForwardResolution("/allProjects.jsp");
    }

    public Resolution removeAll() {
        if (selected != null) {
            for (Long id : selected) {
                if (id.equals(getContext().getLoggedResearcher().getId())) {
                    /*you can't remove yourself*/
                    return new ForwardResolution("/allProjects.jsp");
                }
                getProjectService().removeResearcherFromProject(id, getProject().getId());
            }
        }
        researchers = getProjectService().getAllAssignedResearchers(getProject().getId());
        return new ForwardResolution("/allProjects.jsp");
    }

    public Resolution assignAll() {
        if (selectedApprove != null) {
            for (Long resProject : selectedApprove) {
                getProjectService().assignResearcher(resProject, getProject().getId());
            }
        }
        researchers = getProjectService().getAllAssignedResearchers(getProject().getId());
        return new ForwardResolution("/editProject.jsp");
    }


    public Resolution changeOwnership() {
        getProjectService().changeOwnership(getContext().getProject().getId(), researcher.getId());
        // ctx.setProject(project);

        return new ForwardResolution("/editProject.jsp");
    }


}
