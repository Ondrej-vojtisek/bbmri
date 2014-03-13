package cz.bbmri.dao.impl;

import cz.bbmri.dao.BoxDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.entities.infrastructure.StandaloneBox;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.2.14
 * Time: 10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BoxDaoImpl extends BasicDaoImpl<Box> implements BoxDao {


    public List<StandaloneBox> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        Query query = null;
        String orderParam = "";
        // ORDER BY p.name


        if (orderByParam != null) {

            if(orderByParam.equals("numberOfPositions")){
                orderParam = "ORDER BY COUNT box.positions";
            }else{
                orderParam = "ORDER BY box." + orderByParam;
            }
        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        query = em.createQuery("SELECT box FROM StandaloneBox box WHERE " +
                "box.infrastructure.biobank = :biobank " +
                orderParam);
        query.setParameter("biobank", biobank);
        return query.getResultList();
    }

    public List<RackBox> getSorted(Rack rack, String orderByParam, boolean desc) {
        Query query = null;
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY box." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        query = em.createQuery("SELECT box FROM RackBox box WHERE " +
                "box.rack = :rack " +
                orderParam);
        query.setParameter("rack", rack);
        return query.getResultList();
    }
}
