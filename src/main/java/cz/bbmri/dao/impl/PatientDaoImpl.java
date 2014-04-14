package cz.bbmri.dao.impl;


import cz.bbmri.dao.PatientDao;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 8.1.14
 * Time: 14:53
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PatientDaoImpl extends BasicDaoImpl<Patient, Long> implements PatientDao {


    public List<Patient> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        String orderParam = "";
        String descString = "";
          // ORDER BY p.name DESC
        if (desc) {
            descString = descString + " DESC";
        }

        // ORDER BY p.name
        if (orderByParam != null) {
            // sort by age
            // ORDER BY patient.birthYear DESC, patient.birthMonth DESC
            if(orderByParam.equals("birthYear")){
                orderParam = "ORDER BY patient." + orderByParam + " " + descString +
                        ", patient.birthMonth" + " " + descString;
            }
            else{
                orderParam = "ORDER BY patient." + orderByParam + " " + descString;
            }
        }

        typedQuery = em.createQuery("SELECT patient FROM Patient patient WHERE " +
                "patient.biobank = :biobank " +
                orderParam, Patient.class);
        typedQuery.setParameter("biobank", biobank);
        return typedQuery.getResultList();
    }

    public List<Patient> findPatient(Patient patient) {

        notNull(patient);
        typedQuery = em.createQuery("" +
                "SELECT p " +
                "FROM Patient p WHERE " +
                "p.biobank = :biobankParam " +
//                       "AND (" +
//                               " p.institutionId LIKE :institutionIdParam " +
//                               "OR (p.birthYear = :birthYearParam OR :birthYearParam IS NULL) " +
//                               "OR (p.birthMonth = :birthMonthParam OR :birthMonthParam IS NULL) " +
//                               "OR p.sex = :sexParam " +
//                               "OR p.consent = :consentParam " +
//                            ")" +
                "ORDER BY " +
                "CASE " +
                "WHEN p.institutionId LIKE :institutionIdParam THEN 100 " +
                "WHEN p.birthYear = :birthYearParam THEN 90 " +
                "WHEN p.sex = :sexParam THEN 50 " +
                "WHEN p.birthMonth = :birthMonthParam THEN 10 " +
                "ELSE 0 " +
                "END " +
                "DESC"
        , Patient.class);

        typedQuery.setParameter("institutionIdParam", (patient.getInstitutionId() != null ?
                "%" + patient.getInstitutionId() + "%" : ""));
        typedQuery.setParameter("birthYearParam", (patient.getBirthYear() != null ? patient.getBirthYear() : null));
        typedQuery.setParameter("birthMonthParam", (patient.getBirthMonth() != null ? patient.getBirthMonth() : null));
        typedQuery.setParameter("biobankParam", patient.getBiobank());
        typedQuery.setParameter("sexParam", (patient.getSex() != null ? patient.getSex() : ""));

        return typedQuery.getResultList();
    }

    public Patient getByInstitutionalId(String id){
        notNull(id);
        typedQuery = em.createQuery("SELECT p FROM Patient p WHERE p.institutionId = :idParam", Patient.class);
        typedQuery.setParameter("idParam", id);

        return getSingleResult();
    }
}
