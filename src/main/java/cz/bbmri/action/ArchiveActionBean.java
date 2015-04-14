package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.ArchiveDAO;
import cz.bbmri.entity.Archive;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/archive/{$event}/{id}")
public class ArchiveActionBean extends AuthorizationActionBean {

    @SpringBean
    private ArchiveDAO archiveDAO;

    private Long id;

    private MyPagedListHolder<Archive> pagination = new MyPagedListHolder<Archive>(new ArrayList<Archive>());

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(AttachmentActionBean.class.getName(), "all", false, "cz.bbmri.entity.Archive.archives",
                active);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyPagedListHolder<Archive> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Archive> pagination) {
        this.pagination = pagination;
    }

    @DefaultHandler
    @HandlesEvent("all") /* Necessary for stripes security tag*/
    @RolesAllowed({"developer","admin"})
    public Resolution all() {

        getBreadcrumbs().add(ArchiveActionBean.getAllBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(archiveDAO.all());
        pagination.setEvent("all");

        return new ForwardResolution(View.Archive.ALL);
    }
}
