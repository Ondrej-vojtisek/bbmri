package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.ArchiveDAO;
import cz.bbmri.dao.DiagnosisDAO;
import cz.bbmri.entity.Archive;
import cz.bbmri.entity.Diagnosis;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("diagnosisDAO")
@Transactional
public class DiagnosisDAOImpl extends GenericDAOImpl<Diagnosis> implements DiagnosisDAO {
}
