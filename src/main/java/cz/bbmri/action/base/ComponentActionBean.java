package cz.bbmri.action.base;

import cz.bbmri.action.map.*;
import cz.bbmri.entity.webEntities.Breadcrumb;

import java.util.ArrayList;
import java.util.List;

/**
 * ActionBean handling general components.
 * Components: pagination - list of entity with printed amount of pages. List (shown in table) should be sortable - attributes orderParam and desc are here to define ORDER BY and ASC/DESC order
 * breadcumbs - List of breadcrumb for navigation inside application
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public abstract class ComponentActionBean extends BasicActionBean {

    private Integer page;

    private String orderParam;

    private boolean desc;

    private List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();

    public List<Breadcrumb> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public String getOrderParam() {
        return orderParam;
    }

    public void setOrderParam(String orderParam) {
        this.orderParam = orderParam;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
