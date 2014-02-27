package cz.bbmri.facade;

import cz.bbmri.entities.*;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.infrastructure.*;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankFacade extends PermissionFacade {

    boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors, String bbmriPath);

    boolean updateBiobank(Biobank biobank, ValidationErrors errors, Long loggedUserId);

    boolean removeBiobank(Long biobankId, ValidationErrors errors, String bbmriPath, Long loggedUserId);

    List<Biobank> all();

    Biobank get(Long biobankId);

    BiobankAdministrator getBiobankAdministrator(Long id);

    List<Biobank> getBiobanksByUser(Long userId);

    List<Patient> getAllPatients(Long biobankId);

    List<Sample> getAllSamples(Long biobankId);

    List<SampleQuestion> getBiobankSampleRequests(Long biobankId);

    List<SampleQuestion> getNewSampleRequests(Long biobankId);

    Patient getPatient(Long patientId);

    boolean createPatient(Patient patient, Long biobankId, ValidationErrors errors);

    Infrastructure getInfrastructure(Long infrastructureId);

    Container getContainer(Long containerId);

    Rack getRack(Long rackId);

    Box getBox(Long boxId);

    boolean createInfrastructure(Long biobankId);

    boolean createContainer(Long infrastructureId, Container container, ValidationErrors errors);

    boolean createRack(Long containerId, Rack rack, ValidationErrors errors);

    boolean createStandaloneBox(Long infrastructureId, StandaloneBox box, ValidationErrors errors);

    boolean createBox(Long rackId, RackBox box, ValidationErrors errors);

    List<Patient> find(Patient patient, int requiredResults);


}
