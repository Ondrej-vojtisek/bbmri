package cz.bbmri.io;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.webEntities.PositionDTO;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses import about biobank occupancy. Imports are based on http://www.bbmri.cz/schemas/monitoring/bankoccupancy.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class MonitoringDataParser extends AbstractParser {

    /* Reference XML schema for validation */
    private static final String MONITORING_XSD_URL = "http://www.bbmri.cz/schemas/monitoring/bankoccupancy.xsd";
    /* Default namespace of biobank occupancy import*/
    private static final String DEFAULT_NAMESPACE = "http://www.bbmri.cz/schemas/monitoring/bankoccupancy";
    private static final String NAMESPACE_PREFIX = "def";
    /* Simplification of XPATH queries */
    private static final String NAMESPACE_PREFIX_COLONS = NAMESPACE_PREFIX + ":";
    private static final String NAMESPACE_PREFIX_SLASHED = "/" + NAMESPACE_PREFIX_COLONS;
    private static final String ROOT_ELEMENT =  NAMESPACE_PREFIX_SLASHED + "biobank";



    public MonitoringDataParser(String path) throws Exception {
        super(path, new NamespaceContextMap(
                                  NAMESPACE_PREFIX, DEFAULT_NAMESPACE));
    }

    public String getBiobankAbbreviation() {

        String biobankId;
        try {
            biobankId = executeXPath(ROOT_ELEMENT + "/@id", document);
            setBiobankPrefix(biobankId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return biobankId;
    }

    public boolean validate(){
        return validateDocument(MONITORING_XSD_URL);
    }

    /**
     * Return all containers from given XML file
     *
     * @return list of containers or null
     */
    public List<Container> getContainers() {

        NodeList nodes;

        try {
            // Return all nodes of type container
            nodes = (NodeList) executeXPathForNodeSet(ROOT_ELEMENT + NAMESPACE_PREFIX_SLASHED + "container", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (nodes == null) {
            System.err.println("Nodes null");
            return null;
        }

        // Parse each container
        return parseContainerList(nodes);

    }

    /**
     * Return all racks of given container
     *
     * @param container
     * @return list of racks
     */
    public List<Rack> getRacks(Container container) {

        NodeList nodes;

        try {

            // get all nodes of type rack under container with specified id
            nodes = (NodeList) executeXPathForNodeSet(ROOT_ELEMENT +
                    NAMESPACE_PREFIX_SLASHED + "container[@id='" + container.getName() + "']" +
                    NAMESPACE_PREFIX_SLASHED + " rack", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (nodes == null) {
            System.err.println("Nodes null");
            return null;
        }

        // parse each rack
        return parseRackList(nodes);

    }

    /**
     * Return all standalone boxes of biobank from xml
     *
     * @return list of boxes found in nonstructured part of biobank
     */
    public List<Box> getStandaloneBoxes() {
        NodeList nodes;

        try {
            // all nodes of type box found in root node
            nodes = (NodeList) executeXPathForNodeSet(ROOT_ELEMENT +
                    NAMESPACE_PREFIX_SLASHED + "box", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (nodes == null) {
            System.err.println("Nodes null");
            return null;
        }

        // parse each standalone box
        return parseBoxNodeList(nodes);

    }

    /**
     * Return all boxes from given container and rack
     *
     * @param container
     * @param rack
     * @return list of rack boxes
     */
    public List<Box> getRackBoxes(Container container, Rack rack) {

        NodeList nodes;

        try {
            nodes = (NodeList) executeXPathForNodeSet(ROOT_ELEMENT +
                    NAMESPACE_PREFIX_SLASHED + "container[@id='" + container.getName() + "']" +
                    NAMESPACE_PREFIX_SLASHED + "rack[@id='" + rack.getName() + "']" +
                    NAMESPACE_PREFIX_SLASHED + "box", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (nodes == null) {
            System.err.println("Nodes null");
            return null;
        }

        return parseBoxNodeList(nodes);

    }


    private List<Container> parseContainerList(NodeList nodes) {

        List<Container> containers = new ArrayList<Container>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            Container container;
            container = parseContainer(node);
            if (container == null) {
                return null;
            } else {
                containers.add(container);
            }

        }

        return containers;

    }

    private List<Rack> parseRackList(NodeList nodes) {
        System.out.println("ParseRackList");

        List<Rack> racks = new ArrayList<Rack>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            Rack rack;
            rack = parseRack(node);
            if (rack == null) {
                return null;
            } else {
                racks.add(rack);
            }

        }

        return racks;
    }

    private List<Box> parseBoxNodeList(NodeList nodes) {
        System.out.println("ParseBoxNodeList");

        List<Box> boxes = new ArrayList<Box>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            Box box;
            box = parseBox(node);
            if (box == null) {
                return null;
            } else {
                boxes.add(box);
            }

        }

        return boxes;
    }

    /**
     * Parse one element of type container. Creates instance of Container by found data in XML
     *
     * @param node
     * @return instance of Container or null
     */
    private Container parseContainer(Node node) {
        Container container = new Container();

        String id;
        String location;
        String capacity;
        String tempMin;
        String tempMax;

        try {
            // attributes

            id = executeXPath("@id", node);

            // Child elements

            location = executeXPath(NAMESPACE_PREFIX_COLONS + "location", node);

            capacity = executeXPath(NAMESPACE_PREFIX_COLONS + "capacity", node);

            tempMin = executeXPath(NAMESPACE_PREFIX_COLONS + "tempMin", node);

            tempMax = executeXPath(NAMESPACE_PREFIX_COLONS + "tempMax", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Container");
            return null;
        }

        if (id != null) {
            container.setName(id);
        } else {
            System.err.println("Container id is necessary attribute");
            return null;
        }

        if (location != null) {
            container.setLocation(location);
        } else {
            System.err.println("Container location is necessary element");
            return null;
        }

        if (notNullnotEmpty(capacity)) {
            container.setCapacity(Integer.parseInt(capacity));
        } else {
            System.err.println("Container capacity is necessary element");
            return null;
        }

        if (notNullnotEmpty(tempMin)) {
            container.setTempMin(Float.parseFloat(tempMin));
        }

        if (notNullnotEmpty(tempMax)) {
            container.setTempMax(Float.parseFloat(tempMax));
        }

        return container;

    }

    private Rack parseRack(Node node) {
        Rack rack = new Rack();

        String id;
        String capacity;

        try {
            // attributes

            id = executeXPath("@id", node);

            // Child elements

            capacity = executeXPath(NAMESPACE_PREFIX_COLONS + "capacity", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Rack");
            return null;
        }

        if (id != null) {
            rack.setName(id);
        } else {
            System.err.println("Rack id is necessary attribute");
            return null;
        }

        if (notNullnotEmpty(capacity)) {
            rack.setCapacity(Integer.parseInt(capacity));
        } else {
            System.err.println("Rack capacity is necessary element");
            return null;
        }

        return rack;
    }


    private Box parseBox(Node node) {

        Box box = new Box();

        String id;
        String capacity;
        String tempMin;
        String tempMax;

        try {
            // attributes

            id = executeXPath("@id", node);

            //            Child elements

            capacity = executeXPath(NAMESPACE_PREFIX_COLONS + "capacity", node);

            tempMin = executeXPath(NAMESPACE_PREFIX_COLONS + "tempMin", node);

            tempMax = executeXPath(NAMESPACE_PREFIX_COLONS + "tempMax", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Box");
            return null;
        }

        if (id != null) {
            // name is institutional id
            box.setName(id);
        } else {
            System.err.println("Box Name is necessary attribute");
            return null;
        }

        if (notNullnotEmpty(capacity)) {
            box.setCapacity(Integer.parseInt(capacity));
        } else {
            System.err.println("Box capacity is necessary attribute");
            return null;
        }

        if (notNullnotEmpty(tempMin)) {
            box.setTempMin(Float.parseFloat(tempMin));
        }

        if (notNullnotEmpty(tempMax)) {
            box.setTempMax(Float.parseFloat(tempMax));
        }

        return box;
    }

    public List<PositionDTO> getBoxPositions(Biobank biobank, Container container, Rack rack, Box box) {

        NodeList nodes;

        // standalone box
        if (container == null && rack == null) {

            try {
                // /biobank[@id=...]/box[@id=...]
                nodes = (NodeList) executeXPathForNodeSet(
                        ROOT_ELEMENT + "[@id='" + biobank.getName() + "']" +
                        NAMESPACE_PREFIX_SLASHED + "box[@id='" + box.getName() + "']" +
                        NAMESPACE_PREFIX_SLASHED + "occupiedPosition", document);

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            // rack box
        } else {

            if(container == null){
                return null;
            }

            try {
                // /biobank[@id=...]/container[@id=...]/rack[@id=...]/box[@id=...]
                nodes = (NodeList) executeXPathForNodeSet(
                        ROOT_ELEMENT + "[@id='" + biobank.getName() + "']" +
                        NAMESPACE_PREFIX_SLASHED + "container[@id='" + container.getName() + "']" +
                        NAMESPACE_PREFIX_SLASHED + "rack[@id='" + rack.getName() + "']" +
                        NAMESPACE_PREFIX_SLASHED + "box[@id='" + box.getName() + "']" +
                        NAMESPACE_PREFIX_SLASHED + "occupiedPosition", document);

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

        }

        if (nodes == null) {
            System.err.println("Nodes null");
            return null;
        }

        return parsePositions(nodes);
    }

    private List<PositionDTO> parsePositions(NodeList nodes) {
        System.out.println("parsePositions");

        List<PositionDTO> positions = new ArrayList<PositionDTO>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {
            Node nodeItem = nodes.item(i);
            PositionDTO position;
            position = parsePosition(nodeItem);
            if (position == null) {
                return null;
            } else {
                positions.add(position);
            }
        }

        return positions;
    }

    private PositionDTO parsePosition(Node node) {
        PositionDTO position = new PositionDTO();

        String sampleId;
        String seqPosition;
        String row;
        String column;

        try {

            //            Child elements

            sampleId = executeXPath(NAMESPACE_PREFIX_COLONS + "sampleId", node);

            row = executeXPath(NAMESPACE_PREFIX_COLONS + "matrixPosition/@row", node);

            column = executeXPath(NAMESPACE_PREFIX_COLONS + "matrixPosition/@column", node);

            seqPosition = executeXPath(NAMESPACE_PREFIX_COLONS + "sequentialPosition/@position", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }

        if (sampleId != null) {
            if(getBiobankPrefix() == null){
                System.out.println("BiobankPrefix null");
                return null;
            }
            // Must have prefix of biobank if not prepend it
            if(hasPrefix(sampleId)){
                position.setSampleId(sampleId);
            }else{
                position.setSampleId(getBiobankPrefix() + sampleId);
            }

        } else {
            System.err.println("SampleId is necessary");
            return null;
        }

        // Sequential position is defined
        if (notNullnotEmpty(seqPosition)) {

            position.setSequentialPosition(Integer.parseInt(seqPosition));
            position.setColumn(null);
            position.setRow(null);
            // or matrix position is defined
        } else if (notNullnotEmpty(row) && notNullnotEmpty(column)) {
            position.setRow(Integer.parseInt(row));
            position.setColumn(Integer.parseInt(column));

            position.setSequentialPosition(null);
            // or failed
        } else {
            // at least one method of positioning must be defined
            System.err.println("Sequential or matrix position is necessary");
            return null;
        }

        return position;

    }

}
