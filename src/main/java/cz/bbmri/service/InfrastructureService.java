package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.service.simpleService.Get;

/**
 * API for handling Infrastructure
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface InfrastructureService extends Get<Infrastructure> {
    /**
     * Initialize infrastructure for given biobank.
     *
     * @param biobank - existing instance of biobank
     */
    void initialize(Biobank biobank);
}
