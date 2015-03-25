package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.BiopticalReportDAO;
import cz.bbmri.entity.BiopticalReport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("biopticalReportDAO")
@Transactional
public class BiopticalReportDAOImpl extends GenericDAOImpl<BiopticalReport> implements BiopticalReportDAO {
}
