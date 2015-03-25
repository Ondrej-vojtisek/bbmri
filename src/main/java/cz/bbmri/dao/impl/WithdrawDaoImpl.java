package cz.bbmri.dao.impl;

import cz.bbmri.dao.WithdrawDAO;
import cz.bbmri.entity.Withdraw;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for class handling instances of Withdraw.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository("withdrawDAO")
@Transactional
public class WithdrawDAOImpl extends GenericDAOImpl<Withdraw> implements WithdrawDAO {

    public Withdraw get(Long id) {
          return (Withdraw) getCurrentSession().get(Withdraw.class, id);
      }

}
