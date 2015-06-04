package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.ArchiveDAO;
import cz.bbmri.dao.DiagnosisDAO;
import cz.bbmri.entity.Archive;
import cz.bbmri.entity.Diagnosis;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("diagnosisDAO")
@Transactional
public class DiagnosisDAOImpl extends GenericDAOImpl<Diagnosis> implements DiagnosisDAO {


    public List<String> getUniqueDiagnosis() {
        Criteria criteria = getCurrentSession().createCriteria(Diagnosis.class);

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property("key"));
        criteria.setProjection(Projections.distinct(projList));

        List<String> diagnosisList = criteria.list();

        if (diagnosisList.isEmpty()) {
            return null;
        }

        return diagnosisList;
    }
}
