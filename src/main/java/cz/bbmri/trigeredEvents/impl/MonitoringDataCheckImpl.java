package cz.bbmri.trigeredEvents.impl;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.infrastructure.*;
import cz.bbmri.entities.webEntities.PositionDTO;
import cz.bbmri.io.MonitoringDataParser;
import cz.bbmri.service.*;
import cz.bbmri.service.exceptions.DuplicitEntityException;
import cz.bbmri.service.impl.ServiceUtils;
import cz.bbmri.trigeredEvents.MonitoringDataCheck;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.4.14
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class MonitoringDataCheckImpl extends Basic implements MonitoringDataCheck {

    @Autowired
    private ContainerService containerService;

    @Autowired
    private RackService rackService;

    @Autowired
    private BoxService boxService;

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private SampleService sampleService;


    // TODO - update to lower frequency
    // triggers at 0:05 each day
    //@Scheduled(cron = "5 0 * * * *")
    //@Scheduled(cron = "1 * * * * *")
    public void checkBiobankMonitoringData() {
        logger("method checkBiobankMonitoringData");

        for (Biobank biobank : biobankService.all()) {

            logger.debug("Biobank: " + biobank.getName());

            // Scan all patient data files in biobank folder
            List<File> files = ServiceUtils.getFiles(storagePath + biobank.getBiobankMonitoringFolder());

            for (File file : files) {
                logger.debug("Biobank: " + biobank.getName() + " file: " + file);

                if (parseMonitoringImport(file.getPath(), biobank) != Constant.SUCCESS) {
                    logger.debug("Parse of file : " + file + " failed. ");

                    // Don't copy and remove file in case that something went wrong
                    continue;
                }

                //                copy file if parsing was correct
                //        if (FacadeUtils.copyFile(file, storagePath + biobank.getBiobankMonitoringArchiveFolder()) == SUCCESS) {
                //                delete file if copy succeeded
                //        FacadeUtils.deleteFile(file);
                //    }

            }
        }
    }

    private int parseMonitoringImport(String path, Biobank biobank) {

        logger.debug("ParseMonitoringImport");

        MonitoringDataParser parser;
        try {
            parser = new MonitoringDataParser(path);
        } catch (Exception ex) {
            logger.debug("MonitoringDataParser failed");
            ex.printStackTrace();
            return Constant.NOT_SUCCESS;
        }

        if (!parser.validate()) {
            logger.debug("Document is NOT valid. Document path was: " + path);
            return Constant.NOT_SUCCESS;
        }

        String biobankName = parser.getBiobankId();

        logger.debug("BiobankName: " + biobankName);

        if (!biobankName.equals(biobank.getName())) {
            logger.debug("Biobank identifier doesn't match");
            return Constant.NOT_SUCCESS;
        }

        // Parse standalone boxes
        for (Box box : parser.getStandaloneBoxes()) {
            Box boxDB = boxService.getBoxByName(biobank, null, box.getName());

            if (boxDB == null) {
                StandaloneBox boxNew = new StandaloneBox();
                boxNew.setCapacity(box.getCapacity());
                boxNew.setName(box.getName());
                boxNew.setTempMax(box.getTempMax());
                boxNew.setTempMin(box.getTempMin());
                try {
                    box = boxService.createStandaloneBox(biobank.getInfrastructure().getId(), boxNew);
                } catch (DuplicitEntityException ex) {
                    logger.debug("Box with this name: " + box.getName() + " is already in DB");
                    return Constant.NOT_SUCCESS;
                }
            } else {
                box.setId(boxDB.getId());
                box = boxService.update(box);
            }

            // for standalonebox container and rack is null
            if (parseBoxPositions(parser, biobank, null, null, box) != Constant.SUCCESS) {
                logger.debug("Parse positions failed");
                return Constant.NOT_SUCCESS;
            }


        }

        // Parse containers
        for (Container container : parser.getContainers()) {

            // Is the container already present in DB?
            Container containerDB = containerService.getContainerByName(biobank, container.getName());

            // container is not in DB
            if (containerDB == null) {

                if (biobank.getInfrastructure() == null) {
                    logger.debug("Infrastructure of biobank must not be null");
                    return Constant.NOT_SUCCESS;
                }

                // create it
                try {
                    container = containerService.create(biobank.getInfrastructure().getId(), container);
                } catch (DuplicitEntityException ex) {
                    logger.debug("Container with this name: " + container.getName() + " is already in DB");
                    return Constant.NOT_SUCCESS;
                }

            } else {
                // container is present
                // set id for update method to recognize that container and containerDB are the during update
                container.setId(containerDB.getId());
                container = containerService.update(container);
            }

            // Parse racks
            for (Rack rack : parser.getRacks(container)) {

                // Is the rack already present in DB?
                Rack rackDB = rackService.getRackByName(container, rack.getName());

                // rack in not in DB
                if (rackDB == null) {
                    // create it
                    try {
                        rack = rackService.create(container.getId(), rack);
                    } catch (DuplicitEntityException ex) {
                        logger.debug("Rack with this name: " + rack.getName() + " is already in DB");
                        return Constant.NOT_SUCCESS;
                    }
                } else {
                    // set id to make rack and rackDB equal during update
                    rack.setId(rackDB.getId());
                    rack = rackService.update(rack);
                }

                // Parse rackBoxes
                for (Box box : parser.getRackBoxes(container, rack)) {

                    // Is the box already present in DB?
                    RackBox boxDB = (RackBox) boxService.getBoxByName(biobank, rack, box.getName());
                    if (boxDB == null) {
                        // create new rackBox
                        RackBox rackBox = new RackBox();
                        rackBox.setCapacity(box.getCapacity());
                        rackBox.setName(box.getName());
                        rackBox.setTempMin(box.getTempMin());
                        rackBox.setTempMax(box.getTempMax());
                        try {
                            box = boxService.createRackBox(rack.getId(), rackBox);
                        } catch (DuplicitEntityException ex) {
                            logger.debug("Box with this name: " + box.getName() + " is already in DB");
                            return Constant.NOT_SUCCESS;
                        }
                    } else {
                        // set id to make box and boxDB equal during update
                        box.setId(boxDB.getId());
                        box = boxService.update(box);
                    }

                    if (parseBoxPositions(parser, biobank, container, rack, box) != Constant.SUCCESS) {
                        logger.debug("Parse positions failed");
                        return Constant.NOT_SUCCESS;
                    }
                }
            }
        }


        return Constant.SUCCESS;
    }

    private int parseBoxPositions(MonitoringDataParser parser, Biobank biobank, Container container,
                                  Rack rack, Box box) {
        // Parse positions of box
        for (PositionDTO positionDTO : parser.getBoxPositions(biobank, container, rack, box)) {

            // Is this position in DB
            Position positionDB = positionService.getByCoordinates(box,
                    positionDTO.getSequentialPosition(), positionDTO.getColumn(), positionDTO.getRow());

            Sample sampleDB = sampleService.getByInstitutionalId(positionDTO.getSampleId());

            if (sampleDB == null) {
                logger.debug("Sample identifier doesn't match");
                return Constant.NOT_SUCCESS;
            }

            // Create new position from DTO
            Position positionNew = new Position();
            positionNew.setColumn(positionDTO.getColumn());
            positionNew.setRow(positionDTO.getRow());
            positionNew.setSequentialPosition(positionDTO.getSequentialPosition());

            if (positionDB == null) {
                positionService.create(positionNew, box.getId(), sampleDB.getId());
            } else {
                positionNew.setId(positionDB.getId());
                positionService.update(positionNew);
            }
        }
        return Constant.SUCCESS;
    }
}
