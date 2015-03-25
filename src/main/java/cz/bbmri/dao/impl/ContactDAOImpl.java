package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ArchiveDAO;
import cz.bbmri.dao.ContactDAO;
import cz.bbmri.entity.Archive;
import cz.bbmri.entity.Contact;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("contactDAO")
@Transactional
public class ContactDAOImpl extends GenericDAOImpl<Contact> implements ContactDAO {

    public Contact get(Long id) {
                      return (Contact) getCurrentSession().get(Contact.class, id);
                  }

}
