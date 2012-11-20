package bbmri.action;

import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.Researcher;
import bbmri.service.ProjectService;
import bbmri.serviceImpl.ProjectServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/ChangeAdministrator/{$event}/{researcher.id}")
public class ChangeAdministrator extends BasicActionBean {
      private ProjectService projectService;
      private List<Researcher> researchers;
      private Researcher researcher;

      public Researcher getResearcher() {return researcher;}
      public void setResearcher(Researcher researcher) {this.researcher = researcher;}

      public List<Researcher> getResearchers() {
          this.researchers = getResearcherService().getAll();
          return researchers;
      }

      public void setResearchers(List<Researcher> researchers) {this.researchers = researchers;}

      @DefaultHandler
      public Resolution zobraz() {
          return new ForwardResolution("/allProjects.jsp");
      }

       public Resolution changeAdministrator() {
          Researcher logged =   getResearcherService().changeAdministrator(getContext().getLoggedResearcher().getId(), researcher.getId());
          getContext().setLoggedResearcher(logged);
          return new ForwardResolution("/allProjects.jsp");
      }
}
