package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.WithdrawDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.Patient;
import cz.bbmri.entity.Withdraw;
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
@UrlBinding("/withdraw/{$event}/{id}")
public class WithdrawActionBean extends AuthorizationActionBean {

    @SpringBean
    private WithdrawDAO withdrawDAO;

    @SpringBean
    private BiobankDAO biobankDAO;

    private Long id;

    private Integer biobankId;

    private Withdraw withdraw;

    private MyPagedListHolder<Withdraw> pagination = new MyPagedListHolder<Withdraw>(new ArrayList<Withdraw>());

    public static Breadcrumb getAddBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(WithdrawActionBean.class.getName(), "add", false, "" +
                "cz.bbmri.entity.Withdraw.add", active, "biobankId", biobank.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Integer biobankId) {
        this.biobankId = biobankId;
    }

    public void setWithdraw(Withdraw withdraw) {
        this.withdraw = withdraw;
    }

    public Withdraw getWithdraw() {
        if (withdraw == null) {
            if (id != null) {
                withdraw = withdrawDAO.get(id);
            }
        }

        return withdraw;
    }

    public MyPagedListHolder<Withdraw> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Withdraw> pagination) {
        this.pagination = pagination;
    }

    @HandlesEvent("add")
    @RolesAllowed({"biobank_operator if ${biobankExecutor}"})
    public Resolution add() {

        Biobank biobank = biobankDAO.get(biobankId);

        if(biobank == null){
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(WithdrawActionBean.getAddBreadcrumb(true, biobank));

        return new ForwardResolution(View.Withdraw.ADD);

    }

}

