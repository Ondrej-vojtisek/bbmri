package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.service.exceptions.DuplicitBiobankException;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankService  extends BasicService<Biobank>, PermissionService<Biobank, BiobankAdministrator> {

   Biobank create(Biobank biobank, Long administratorId) throws DuplicitBiobankException;

   Biobank eagerGet(Long id, boolean samples, boolean requestGroups, boolean sampleQuestions);

}
