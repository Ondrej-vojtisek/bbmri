package experimental;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.webEntities.PositionDTO;
import cz.bbmri.io.MonitoringDataParser;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.3.14
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public class MonitoringParserTest {

    public static void main(String[] args) {

        System.out.println("ParseMonitoringImport");




        MonitoringDataParser parser;
        try {
            parser = new MonitoringDataParser("C:\\Users\\Ori\\Study_materials\\Diplomka\\server_data\\biobank_files\\1\\monitoring_data\\monitoring-zaplneni.xsd.xml");
        } catch (Exception ex) {
            ex.printStackTrace();
            return;


        }

     //   parser.validateDocument("C:\\Users\\Ori\\Study_materials\\Diplomka\\server_data\\biobank_files\\1\\monitoring_data\\monitoring-zaplneni.xsd.xml");

        Biobank biobank = new Biobank();
        biobank.setName("MOU");

        String biobankId = parser .getBiobankAbbreviation();

        System.out.println("BiobankId is:" + biobankId);

        for (Box box : parser.getStandaloneBoxes()) {
            System.out.println("\t" + box);

            for (PositionDTO position : parser.getBoxPositions(biobank, null, null, box)) {
                System.out.println("\t\t" + position);
            }

        }

        System.out.println();

        for (Container container : parser.getContainers()) {
            System.out.println("\t" + container);

            for (Rack rack : parser.getRacks(container)) {
                System.out.println("\t\t" + rack);

                for(Box box : parser.getRackBoxes(container, rack)){
                    System.out.println("\t\t\t" + box);

                    for (PositionDTO position : parser.getBoxPositions(biobank, container, rack, box)) {
                                  System.out.println("\t\t\t\t" + position);
                              }
                }
            }

        }

        return;

    }
}
