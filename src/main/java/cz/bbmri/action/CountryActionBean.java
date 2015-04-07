package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.CountryDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.Country;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.ComponentManager;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@RolesAllowed("developer")
@UrlBinding("/country/{$event}/{id}")
public class CountryActionBean extends ComponentActionBean {

    @SpringBean
    private CountryDAO countryDAO;

    private Country country;

    private Integer id;

    private MyPagedListHolder<Country> pagination = new MyPagedListHolder<Country>(new ArrayList<Country>());

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(CountryActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Country.countries", active);
    }


    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        if (country == null) {
            if (id != null) {
                country = countryDAO.get(id);
            }
        }

        return country;
    }

    public MyPagedListHolder<Country> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Country> pagination) {
        this.pagination = pagination;
    }

    public List<Country> getAll(){
        return countryDAO.all();
    }

    @DefaultHandler
    @HandlesEvent("all") /* Necessary for stripes security tag*/
    @RolesAllowed("authorized")
    public Resolution all() {

        getBreadcrumbs().add(CountryActionBean.getAllBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(countryDAO.all());
        pagination.setEvent("all");

        // TODO jak radit podle Contactu ?

        //        default ordering
        //        if (getOrderParam() == null) {
        //            setOrderParam("surname");
        //            getPagination().setOrderParam("surname");
        //            getPagination().setDesc(false);
        //        }

        return new ForwardResolution(View.Country.ALL);
    }
}
