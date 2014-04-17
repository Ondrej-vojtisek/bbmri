package cz.bbmri.action.base;

import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.MyPagedListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.3.14
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public abstract class ComponentActionBean<T> extends BasicActionBean {

    private Integer page;

    private String orderParam;

    private boolean desc;

    private MyPagedListHolder<T> pagination;

    private List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();

    public List<Breadcrumb> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    protected void initiatePagination() {
        if (getPage() != null) {
            getPagination().setCurrentPage(getPage());
        }
        if (getOrderParam() != null) {
            getPagination().setOrderParam(getOrderParam());
        }
        getPagination().setDesc(isDesc());
    }

    public MyPagedListHolder<T> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<T> pagination) {
        this.pagination = pagination;
    }

    boolean isDesc() {
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
