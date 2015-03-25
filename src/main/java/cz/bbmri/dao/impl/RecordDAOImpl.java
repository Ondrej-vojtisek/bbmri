package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.RecordDAO;
import cz.bbmri.entity.Record;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("recordDAO")
@Transactional
public class RecordDAOImpl extends GenericDAOImpl<Record> implements RecordDAO {

    public Record get(Long id) {
                      return (Record) getCurrentSession().get(Record.class, id);
                  }

}
