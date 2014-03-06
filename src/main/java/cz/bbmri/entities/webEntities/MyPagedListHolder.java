package cz.bbmri.entities.webEntities;

import org.springframework.beans.support.PagedListHolder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.3.14
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class MyPagedListHolder<E> extends PagedListHolder {

    public static final int PAGE_SIZE = 10;
    public static final int MAX_PAGING_WIDTH = 6;

    private String orderParam;

    private boolean desc;

    private String event;

    public MyPagedListHolder(List<E> source) {
        super(source);
        setPageSize(PAGE_SIZE);
        setMaxLinkedPages(MAX_PAGING_WIDTH);
        setDesc(false);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getOrderParam() {
        return orderParam;
    }

    public void setOrderParam(String orderParam) {
        this.orderParam = orderParam;
    }

    public boolean getDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }


    public int getCurrentPage() {
        // getPage is indexed from 0
        return getPage() + 1;
    }

    public void setCurrentPage(int page) {
        // getPage is indexed from 0
        setPage(page - 1);
    }

    public int getMyFirstLinkedPage() {
        return getFirstLinkedPage() + 1;
    }

    public int getMyLastLinkedPage() {
        return getLastLinkedPage() + 1;
    }
}
