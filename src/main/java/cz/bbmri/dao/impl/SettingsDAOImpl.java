package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.QuestionStateDAO;
import cz.bbmri.dao.SettingsDAO;
import cz.bbmri.entity.QuestionState;
import cz.bbmri.entity.Settings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("settingsDAO")
@Transactional
public class SettingsDAOImpl extends GenericDAOImpl<Settings> implements SettingsDAO {
}
