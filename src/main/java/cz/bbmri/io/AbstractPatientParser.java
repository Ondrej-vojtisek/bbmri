package cz.bbmri.io;

import cz.bbmri.entity.Patient;
import cz.bbmri.entity.Sample;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class AbstractPatientParser extends AbstractParser {

    public AbstractPatientParser(String path, NamespaceContextMap namespace) throws Exception {
        super(path, namespace);
    }


    public boolean validate() {
        System.err.println("It can't be called.");
        return false;
    }

    /**
     * Return biobank institutional id.
     *
     * @return biobank institutional id or null
     */
    public String getBiobankInstitutionalId() {
        System.err.println("It can't be called.");
        return null;
    }

    public Patient getPatient() {
        System.err.println("It can't be called.");
        return null;
    }

    public List<Sample> getPatientLtsSamples() {
        System.err.println("It can't be called.");
        return null;
    }

    public List<Sample> getPatientStsSamples() {
        System.err.println("It can't be called.");
        return null;
    }



}
