package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Infrastructure;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface InfrastructureService  {

    void initialize(Biobank biobank);

    Infrastructure get(Long id);
}
