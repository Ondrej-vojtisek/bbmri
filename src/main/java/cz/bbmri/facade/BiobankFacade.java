package cz.bbmri.facade;

import cz.bbmri.entities.*;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.infrastructure.*;
import cz.bbmri.service.BiobankService;
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

    List<Sample> getAllSamples(Long biobankId);

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

    List<Biobank> allOrderedBy(String orderByParam, boolean desc);

    List<Patient> getSortedPatients(Long biobankId, String orderByParam, boolean desc);

    List<Container> getSortedContainers(Long biobankId, String orderByParam, boolean desc);

    List<Rack> getSortedRacks(Long biobankId, String orderByParam, boolean desc);

    List<StandaloneBox> getSortedStandAloneBoxes(Long biobankId, String orderByParam, boolean desc);

    List<RackBox> getSortedStandRackBoxes(Long rackId, String orderByParam, boolean desc);

//    void checkBiobankFolders();

 //   List<Sample> allSamplesOrderedBy(String orderByParam, boolean desc);


}
