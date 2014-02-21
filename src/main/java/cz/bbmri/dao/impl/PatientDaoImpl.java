package cz.bbmri.dao.impl;


import cz.bbmri.dao.PatientDao;
import cz.bbmri.entities.Patient;
import org.springframework.stereotype.Repository;

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
public class PatientDaoImpl extends BasicDaoImpl<Patient> implements PatientDao {

    @SuppressWarnings("unchecked")
    public List<Patient> findPatient(Patient patient){

         logger.debug("FindPatient: patient: " + patient);

           notNull(patient);
           Query query = em.createQuery("" +
                   "SELECT p " +
                   "FROM Patient p WHERE " +
                       "(p.biobank) = :biobankParam " +
                       "AND (" +
                               "(p.institutionId) LIKE :institutionIdParam " +
                               "OR (p.birthYear) = :birthYearParam " +
                               "OR (p.birthMonth) = :birthMonthParam " +
                               "OR (p.sex) = :sexParam " +
                            ")" +
                       "ORDER BY " +
                           "CASE " +
                               "WHEN ((p.institutionId) = :institutionIdParam) THEN 100 " +
                               "WHEN (p.birthYear = :birthYearParam) THEN 70 " +
                               "WHEN (p.birthMonth = :birthMonthParam) THEN 10 " +
                               "WHEN (p.sex = :sexParam) THEN 50 " +
                               "ELSE 0 " +
                           "END " +
                       "DESC"
           );

           query.setParameter("institutionIdParam", (patient.getInstitutionId() != null ? "%" + patient.getInstitutionId() + "%"  : " "));
           query.setParameter("birthYearParam", (patient.getBirthYear() != null ? patient.getBirthYear() : "*"));
           query.setParameter("birthMonthParam", (patient.getBirthMonth() != null ? patient.getBirthMonth() : "*"));
           query.setParameter("biobankParam", patient.getBiobank());
           query.setParameter("sexParam", (patient.getSex() != null ? patient.getSex() : ""));

           return query.getResultList();
       }
}
