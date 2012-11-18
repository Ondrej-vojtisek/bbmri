package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.ResearcherDAO;
import bbmri.DAOimpl.BiobankDAOImpl;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Biobank;
import bbmri.entities.Researcher;
import bbmri.service.BiobankService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public class BiobankServiceImpl implements BiobankService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    BiobankDAO biobankDAO;

    private BiobankDAO getBiobankDAO() {
        if (biobankDAO == null) {
            biobankDAO = new BiobankDAOImpl();
        }
        return biobankDAO;
    }

    ResearcherDAO researcherDAO;

    private ResearcherDAO getResearcherDAO() {
        if (researcherDAO == null) {
            researcherDAO = new ResearcherDAOImpl();
        }
        return researcherDAO;
    }

    public Biobank create(Biobank biobank, Long administratorId, Long ethicalCommitteeId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher adminDB = getResearcherDAO().get(administratorId, em);
        if (adminDB != null) {
            getBiobankDAO().create(biobank, em);
            biobank.setAdministrator(adminDB);
        }
         Researcher committeeDB = getResearcherDAO().get(ethicalCommitteeId, em);
        if (committeeDB != null) {
            biobank.setEthicalCommittee(committeeDB);
        }
        em.getTransaction().commit();
        em.close();
        return biobank;
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Biobank biobank = getBiobankDAO().get(id, em);
        if (biobank != null) {
            getBiobankDAO().remove(biobank, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Biobank update(Biobank biobank) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = getBiobankDAO().get(biobank.getId(), em);
        biobankDB.setAddress(biobank.getAddress());
        biobankDB.setName(biobank.getName());

        getBiobankDAO().update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

    public List<Biobank> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Biobank> researchers = getBiobankDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return researchers;
    }

    public Biobank updateAdministrator (Long biobankId, Long adminId){
         EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = getBiobankDAO().get( biobankId, em);
        Researcher researcher = getResearcherDAO().get(adminId, em);
        biobankDB.setAdministrator(researcher);

        getBiobankDAO().update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

       public Biobank updateEthicalCommittee (Long biobankId, Long committeeId){
         EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = getBiobankDAO().get( biobankId, em);
        Researcher researcher = getResearcherDAO().get(committeeId, em);
        biobankDB.setEthicalCommittee(researcher);

        getBiobankDAO().update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

}
