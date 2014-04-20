package cz.bbmri.entities.webEntities;

import org.springframework.beans.support.PagedListHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Manager of paged listed view of entities.
 * Component extending PagedListHolder (org.springframework.beans.support.PagedListHolder) to add data about ordering
 * and to store context necessary to get data from DB.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class MyPagedListHolder<E> extends PagedListHolder {

    private static final int PAGE_SIZE = 10;
    private static final int MAX_PAGING_WIDTH = 6;


    /**
     * Which column of table was chosen to sort them
     */
    private String orderParam;

    /**
     * Order of sort ASC, DESC
     */
    private boolean desc;

    /**
     * Event definition - which event of actionBean is triggered
     */
    private String event;

    /**
     * When we don't want to have method on service layer providing sorted data by all meaningful parameter combinations
     * it is possible to set comparator to sort data collection
     */
    private Comparator comparator;

    /**
     * Context of data (e.g. biobank identifier when we want to print biobank administrators)
     */
    private Long identifier;

    /**
     * Name of identifier - part of URL
     */
    private String identifierParam;

    /**
     * If we need more than one pagination on one page it is necessary to distinguish between parameters. Otherwise
     * we would sort both tables be clicking on any header. Web param allows to create params with suffix
     * like descA, orderParamA
     */
    private String webParamDiscriminator = "";

    public MyPagedListHolder(List<E> source) {
        super(source);
        // default page size
        setPageSize(PAGE_SIZE);
        // default paging width
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
        // getPage is indexed from 0
        return getFirstLinkedPage() + 1;
    }

    public int getMyLastLinkedPage() {
        // getPage is indexed from 0
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
        // only when comparator is defined
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
