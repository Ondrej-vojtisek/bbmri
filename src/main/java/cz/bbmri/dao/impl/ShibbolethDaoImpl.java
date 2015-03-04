package cz.bbmri.dao.impl;

import cz.bbmri.dao.ShibbolethDao;
import cz.bbmri.entities.Shibboleth;
import org.springframework.stereotype.Repository;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository
public class ShibbolethDaoImpl extends BasicDaoImpl<Shibboleth> implements ShibbolethDao {

    public Shibboleth get(String eppn, String targetedId, String persistentId){

  //        primary shibboleth identifier
          if(eppn != null){
              typedQuery = em.createQuery("SELECT p FROM Shibboleth p WHERE p.eppn = :eppnParam", Shibboleth.class);
              typedQuery.setParameter("eppnParam", eppn);
              return getSingleResult();
          }

  //        secondary shibboleth identifier
          else if(targetedId != null){
              typedQuery = em.createQuery("SELECT p FROM Shibboleth p WHERE p.targetedId = :targetedIdParam", Shibboleth.class);
              typedQuery.setParameter("targetedIdParam", targetedId);
              return getSingleResult();
          }

  //        ternary shibboleth identifier
          else if(persistentId != null){
              typedQuery = em.createQuery("SELECT p FROM Shibboleth p WHERE p.eppn = :persistentIdParam", Shibboleth.class);
              typedQuery.setParameter("persistentIdParam", persistentId);
              return getSingleResult();
          }

          return null;
      }
}
