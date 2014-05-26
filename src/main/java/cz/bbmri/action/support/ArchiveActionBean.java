package cz.bbmri.action.support;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.systemAdministration.Archive;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.ArchiveService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/archive")
public class ArchiveActionBean extends ComponentActionBean<Archive> {

    @SpringBean
    private ArchiveService archiveService;

    public ArchiveActionBean() {
        setPagination(new MyPagedListHolder<Archive>(new ArrayList<Archive>()));
        setComponentManager(new ComponentManager(ComponentManager.ARCHIVE_DETAIL));
    }

    public static Breadcrumb getBreadcrumb(boolean active) {
        return new Breadcrumb(ArchiveActionBean.class.getName(),
                "display", false, "cz.bbmri.action.support.ArchiveActionBean.archive", active);
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("display")
    @RolesAllowed({"developer", "administrator"})
    public Resolution display() {
        getBreadcrumbs().add(ArchiveActionBean.getBreadcrumb(true));

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("created");
        }
        getPagination().setEvent("display");
        getPagination().setSource(archiveService.allOrderedBy(
                getPagination().getOrderParam(),
                getPagination().getDesc()));

        return new ForwardResolution(ARCHIVE);
    }


}
