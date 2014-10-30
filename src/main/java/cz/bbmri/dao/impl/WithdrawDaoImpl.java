package cz.bbmri.dao.impl;

import cz.bbmri.dao.WithdrawDao;
import cz.bbmri.entities.Withdraw;
import org.springframework.stereotype.Repository;

/**
 * Implementation for interface handling instances of Withdraw.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class WithdrawDaoImpl extends BasicDaoImpl<Withdraw> implements WithdrawDao {
}
