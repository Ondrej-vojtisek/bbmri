package bbmri.DAOimpl;


import bbmri.DAO.ResearcherDAO;
import bbmri.entities.Researcher;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class ResearcherDAOImpl implements ResearcherDAO {

    public void create(Researcher researcher, EntityManager em) {
        em.persist(researcher);
    }

    public void remove(Researcher researcher, EntityManager em) {
        em.remove(researcher);
    }

    public void update(Researcher researcher, EntityManager em) {
        em.merge(researcher);
    }

    public List<Researcher> getAll(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Researcher p");
        return query.getResultList();
    }

    public Researcher get(Long id, EntityManager em) {
        return em.find(Researcher.class, id);
    }

}



