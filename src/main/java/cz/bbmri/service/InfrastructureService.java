package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Infrastructure;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public interface InfrastructureService extends BasicService<Infrastructure> {

    boolean create(Long biobankId);

    Infrastructure initialize(Biobank biobank);
}
