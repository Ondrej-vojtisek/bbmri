package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.BiobankMaterialTypeDAO;
import cz.bbmri.dao.MaterialTypeDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.BiobankMaterialType;
import cz.bbmri.entity.MaterialType;
import cz.bbmri.entity.webEntities.Breadcrumb;
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
@UrlBinding("/material/{$event}/{id}")
public class MaterialTypeActionBean extends AuthorizationActionBean {

    @SpringBean
    private MaterialTypeDAO materialTypeDAO;

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private BiobankMaterialTypeDAO biobankMaterialTypeDAO;

    private Integer biobankId;

    private Integer materialTypeId;

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(MaterialTypeActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.MaterialType.materialType", active);
    }

    public Integer getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(Integer materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public Integer getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Integer biobankId) {
        this.biobankId = biobankId;
    }

    public String getBiobankMaterialType() {

        Biobank biobank = biobankDAO.get(biobankId);

        if (biobank == null) {
            return null;
        }

        MaterialType materialType = materialTypeDAO.get(materialTypeId);

        if (materialType == null) {
            return null;
        }

        List<BiobankMaterialType> biobankMaterialTypes = biobankMaterialTypeDAO.get(biobank, materialType);

        if (biobankMaterialTypes == null | biobankMaterialTypes.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (BiobankMaterialType bmt : biobankMaterialTypes) {
            sb.append(bmt.getKey());
            sb.append(", ");
        }

        if (sb.length() < 2) {
            return null;
        }

        // delete end of output
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.deleteCharAt(sb.length() - 1);

            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
        }

        return sb.toString();

    }

    public List<MaterialType> getAll() {
        return materialTypeDAO.all();
    }

    public List<Biobank> getBiobanks() {
        return biobankDAO.all();
    }

    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"developer", "administrator", "biobank_operator"})
    public Resolution all() {

        getBreadcrumbs().add(MaterialTypeActionBean.getAllBreadcrumb(true));

        return new ForwardResolution(View.MaterialType.ALL);

    }
}
