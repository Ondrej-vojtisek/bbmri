package cz.bbmri.action.base;

import cz.bbmri.entities.webEntities.MyPagedListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void initiatePagination() {
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
