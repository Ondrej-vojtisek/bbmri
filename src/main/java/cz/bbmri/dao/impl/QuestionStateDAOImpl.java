package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.QuestionStateDAO;
import cz.bbmri.entity.QuestionState;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("questionStateDAO")
@Transactional
public class QuestionStateDAOImpl extends GenericDAOImpl<QuestionState> implements QuestionStateDAO {

    public QuestionState get(Short id) {
                      return (QuestionState) getCurrentSession().get(QuestionState.class, id);
                  }
}
