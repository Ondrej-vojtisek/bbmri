package cz.bbmri.entities.webEntities;

import org.springframework.beans.support.PagedListHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.3.14
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class MyPagedListHolder<E> extends PagedListHolder {

    private static final int PAGE_SIZE = 10;
    private static final int MAX_PAGING_WIDTH = 6;

    private String orderParam;

    private boolean desc;

    private String event;

    private Comparator comparator;

    // if necessary to store context - for instance biobank or project
    private Long identifier;

    private String identifierParam;

    // if we need more pagination on one page - we need to distinguish between parameters in URL
    private String webParamDiscriminator = "";

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


    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierParam() {
        return identifierParam;
    }

    public void setIdentifierParam(String identifierParam) {
        this.identifierParam = identifierParam;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public String getWebParamDiscriminator() {
        return webParamDiscriminator;
    }

    public void setWebParamDiscriminator(String webParamDiscriminator) {
        this.webParamDiscriminator = webParamDiscriminator;
    }

    public List<E> getMyPageList() {

        // Not SQL in memory sort
        // used for smaller collections
        if (comparator != null) {
            List<E> result = getPageList();
            Collections.sort(result, comparator);

            if (desc) {
                Collections.reverse(result);
            }
            return result;
        }

        return (List<E>) getPageList();
    }


}
