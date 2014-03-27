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
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.3.14
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class MonitoringDataParser extends AbstractParser {

    private static final String MONITORING_XSD_URL = "http://www.bbmri.cz/schemas/monitoring/bankoccupancy.xsd";

    public MonitoringDataParser(String path) throws Exception {
        super(path);
    }

    public String getBiobankId() {

        String biobankId = null;
        try {
            biobankId = executeXPath("/biobank/@id", document);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return biobankId;
    }

    public boolean validate(){
        return validateDocument(MONITORING_XSD_URL);
    }

    public List<Container> getContainers() {
        List<Container> containers = new ArrayList<Container>();

        NodeList nodes = null;

        try {
            nodes = (NodeList) executeXPathForNodeSet("/biobank/container", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (nodes == null) {
            System.err.println("Nodes null");
            return null;
        }

        return parseContainerList(nodes);

    }

    public List<Rack> getRacks(Container container) {
        List<Rack> racks = new ArrayList<Rack>();

        NodeList nodes = null;

        try {
            nodes = (NodeList) executeXPathForNodeSet("/biobank/container[@id='" +
                    container.getName() + "']/rack", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (nodes == null) {
            System.err.println("Nodes null");
            return null;
        }
        return parseRackList(nodes);

    }

    public List<Box> getStandaloneBoxes() {
        List<Box> boxes = new ArrayList<Box>();

        NodeList nodes = null;

        try {
            nodes = (NodeList) executeXPathForNodeSet("/biobank/box", document);

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

    public List<Box> getRackBoxes(Container container, Rack rack) {
        List<Box> boxes = new ArrayList<Box>();

        NodeList nodes = null;

        try {
            nodes = (NodeList) executeXPathForNodeSet("/" +
                    "biobank/container[@id='" + container.getName() + "']/" +
                    "rack[@id='" + rack.getName() + "']/" +
                    "box", document);

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
        System.out.println("ParseContainerList");

        List<Container> containers = new ArrayList<Container>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            Container container = null;
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
            Rack rack = null;
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
            Box box = null;
            box = parseBox(node);
            if (box == null) {
                return null;
            } else {
                boxes.add(box);
            }

        }

        return boxes;
    }

    private Container parseContainer(Node node) {
        Container container = new Container();

        String id = null;
        String location = null;
        String capacity = null;
        String tempMin = null;
        String tempMax = null;

        try {
            // attributes

            id = executeXPath("@id", node);

            // Child elements

            location = executeXPath("location", node);

            capacity = executeXPath("capacity", node);

            tempMin = executeXPath("tempMin", node);

            tempMax = executeXPath("tempMax", node);

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

        String id = null;
        String capacity = null;

        try {
            // attributes

            id = executeXPath("@id", node);

            // Child elements

            capacity = executeXPath("capacity", node);

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

        String id = null;
        String capacity = null;
        String tempMin = null;
        String tempMax = null;

        try {
            // attributes

            id = executeXPath("@id", node);

            //            Child elements

            capacity = executeXPath("capacity", node);

            tempMin = executeXPath("tempMin", node);

            tempMax = executeXPath("tempMax", node);

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

        NodeList nodes = null;

        // standalone box
        if (container == null && rack == null) {

            try {
                // /biobank[@id=...]/box[@id=...]
                nodes = (NodeList) executeXPathForNodeSet("/" +
                        "biobank[@id='" + biobank.getName() + "']/" +
                        "box[@id='" + box.getName() + "']/" +
                        "occupiedPosition", document);

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            // rack box
        } else {

            try {
                // /biobank[@id=...]/container[@id=...]/rack[@id=...]/box[@id=...]
                nodes = (NodeList) executeXPathForNodeSet("/" +
                        "biobank[@id='" + biobank.getName() + "']/" +
                        "container[@id='" + container.getName() + "']/" +
                        "rack[@id='" + rack.getName() + "']/" +
                        "box[@id='" + box.getName() + "']/" +
                        "occupiedPosition", document);

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
            PositionDTO position = null;
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

        String sampleId = null;
        String seqPosition = null;
        String row = null;
        String column = null;

        try {

            //            Child elements

            sampleId = executeXPath("sampleId", node);

            row = executeXPath("matrixPosition/@row", node);

            column = executeXPath("matrixPosition/@column", node);

            seqPosition = executeXPath("sequentialPosition/@position", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }

        if (sampleId != null) {
            position.setSampleId(sampleId);
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
            System.err.println("Sequential or matrix position is necessary");
            return null;
        }

        return position;

    }

}
