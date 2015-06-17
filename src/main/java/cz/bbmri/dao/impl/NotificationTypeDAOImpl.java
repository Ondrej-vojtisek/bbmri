package cz.bbmri.dao.impl;

import cz.bbmri.dao.NotificationDAO;
import cz.bbmri.dao.NotificationTypeDAO;
import cz.bbmri.entity.Notification;
import cz.bbmri.entity.NotificationType;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.action.LocalizableMessage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("notificationTypeDAO")
@Transactional
public class NotificationTypeDAOImpl extends GenericDAOImpl<NotificationType> implements NotificationTypeDAO {

    public NotificationType get(Integer id) {
        return (NotificationType) getCurrentSession().get(NotificationType.class, id);
    }


}
