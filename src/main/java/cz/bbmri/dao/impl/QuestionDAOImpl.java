package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.QuestionDAO;
import cz.bbmri.entity.Question;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("questionDAO")
@Transactional
public class QuestionDAOImpl extends GenericDAOImpl<Question> implements QuestionDAO {

    public Question get(Long id) {
                      return (Question) getCurrentSession().get(Question.class, id);
                  }
}
