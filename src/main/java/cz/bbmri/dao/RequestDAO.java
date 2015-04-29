package cz.bbmri.dao;

import cz.bbmri.entity.Request;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface RequestDAO {

    Request get(Long id);

    Request create(Request request);

    Request update(Request request);

    void remove(Request request);
}
