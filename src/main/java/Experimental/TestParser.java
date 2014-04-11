package experimental;

import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.io.PatientDataParser;
import cz.bbmri.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.3.14
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class TestParser {

    public static void main(String[] args) {

        System.out.println("ParsePatientImport");

        PatientDataParser parser = null;
        try {

            parser = new PatientDataParser("C:\\Users\\Ori\\Study_materials\\Diplomka\\server_data\\biobank_files\\1\\patient_data\\biobank-data.xsd.xml");
            //parser = new PatientDataParser("C:\\Users\\Ori\\Study_materials\\Diplomka\\server_data\\biobank_files\\1\\patient_data\\BBM140402162126-0001.XML");
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        System.out.println("Is valid "  + parser.validate() );

        System.out.println("Biobank Id: "  + parser.getBiobankId() );

        Patient patient = parser.getPatient();

        if (patient == null) {
            System.out.println("Patient is null");
            return;
        }
        System.out.println("Patient" + patient.toString());

        List<Sample> samples =  parser.getPatientLtsSamples();

        System.out.println("Samples: " + samples);

        return;

    }
}
