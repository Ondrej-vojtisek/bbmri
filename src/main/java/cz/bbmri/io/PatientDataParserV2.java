package cz.bbmri.io;


import cz.bbmri.entity.*;
import org.apache.axis2.databinding.types.xsd.GYear;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses import about samples stored in biobank. Imports are based on http://www.bbmri.cz/schemas/biobank/data_v2.0
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class PatientDataParserV2 extends AbstractPatientParser {

    // Reference XML schema to validate imports
    private static final String PATIENT_XSD_URL = "http://www.bbmri.cz/schemas/biobank/data_v2.xsd";

    // Default namespace of biobank data import
    private static final String DEFAULT_NAMESPACE = "http://www.bbmri.cz/schemas/biobank/data_v2";
    private static final String NAMESPACE_PREFIX = "def";

    // Simplification of XPath
    private static final String NAMESPACE_PREFIX_COLONS = NAMESPACE_PREFIX + ":";
    private static final String NAMESPACE_PREFIX_SLASHED = "/" + NAMESPACE_PREFIX_COLONS;
    private static final String ROOT_ELEMENT = NAMESPACE_PREFIX_SLASHED + "patient";

    private static final String PATIENT_ID = "id";
    private static final String PATIENT_CONSENT = "consent";
    private static final String PATIENT_YEAR = "year";
    private static final String PATIENT_MONTH = "month";
    private static final String PATIENT_SEX = "sex";
    private static final String PATIENT_BIOBANK = "biobank";
    private static final String PATIENT_SAMPLE = "sample";

    private static final String SAMPLE_YEAR = "year";
    private static final String SAMPLE_NUMBER = "number";
    private static final String SAMPLE_ID = "id";
    private static final String SAMPLE_TOTAL_ALIQUOTS = "totalAliquots";
    private static final String SAMPLE_AVAILABLE_ALIQUOTS = "availableAliquots";
    private static final String SAMPLE_MATERIAL_TYPE = "materialType";
    private static final String SAMPLE_TNM = "TNM";
    private static final String SAMPLE_PTNM = "pTNM";
    private static final String SAMPLE_MORPHOLOGY = "morphology";
    private static final String SAMPLE_GRADING = "grading";
    private static final String SAMPLE_TAKING_DATE_TIME = "takingDateTime";
    private static final String SAMPLE_FREEZE_DATE_TIME = "freezeDateTime";
    private static final String SAMPLE_RETRIEVED = "retrieved";
    private static final String SAMPLE_DIAGNOSIS = "diagnosis";
    private static final String SAMPLE_STORAGE_METHODOLOGY = "storageMethodology";
    private static final String SAMPLE_TEMPERATURE_CELSIUS = "temperatureCelsius";
    private static final String SAMPLE_METHODOLOGY = "methodology";
    private static final String SAMPLE_STS = "sts";
    private static final String SAMPLE_EXPIRATION = "expiration";
    private static final String SAMPLE_CONSERVATION_METHOD = "conservationMethod";

    public PatientDataParserV2(String path) throws Exception {

        super(path, new NamespaceContextMap(
                NAMESPACE_PREFIX, DEFAULT_NAMESPACE));
    }

    public boolean validate() {
        return validateDocument(PATIENT_XSD_URL);
    }

    public String getBiobankInstitutionalId() {

        String biobankId;

        try {

            biobankId = executeXPath(ROOT_ELEMENT + "/@" + PATIENT_BIOBANK, document);
            setBiobankPrefix(biobankId);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return biobankId;
    }

    public Patient getPatient() {

        String patientId;
        String birthYear;
        String birthMonth;
        String sex;
        String consent;

        try {

            patientId = executeXPath(ROOT_ELEMENT + "/@" + PATIENT_ID, document);

            birthYear = executeXPath(ROOT_ELEMENT + "/@" + PATIENT_YEAR, document);

            birthMonth = executeXPath(ROOT_ELEMENT + "/@" + PATIENT_MONTH, document);

            sex = executeXPath(ROOT_ELEMENT + "/@" + PATIENT_SEX, document);

            consent = executeXPath(ROOT_ELEMENT + "/@" + PATIENT_CONSENT, document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        Patient patient = new Patient();

//        CONSENT

        // We must not accept data without informed consent!
        if (!Boolean.valueOf(consent)) {
            System.err.println("Patient data with negative patient consent");
            patient.setConsent(false);
        } else {
            patient.setConsent(true);
        }

//        YEAR
        Short year = parseBirthYear(birthYear);
        if (year == null) {
            System.err.println("Birth year of imported patient is not valid");
            return null;
        } else {
            patient.setBirthYear(year);
        }

//        MONTH
        Short month = parseBirthMonth(birthMonth);
        if (month != null) {
            patient.setBirthMonth(month);
        }

//        SEX
        Sex sexValue = parseSex(sex);
        if (sex == null) {
            System.err.println("Sex parse failed");
            return null;
        } else {
            patient.setSex(sexValue);
        }

//        PATIENT IDENTIFIER FROM HOME INSTITUTION

        if (patientId == null) {
            System.err.println("Patient ID is null");
            return null;
        }

        if (getBiobankPrefix() == null) {
            System.err.println("BiobankPrefix not set");
            return null;
        }

//        Patientid must be prefixed with institutional abbreviation
        if (!hasPrefix(patientId)) {
            // set biobankId as prefix
            patient.setInstitutionalId(getBiobankPrefix() + BIOBANK_PREFIX_SEPARATOR + patientId);
        } else {
            patient.setInstitutionalId(patientId);
        }

        return patient;
    }


    /**
     * Parser v2 doesn't contain STS or LTS module. So STS is always empty and each sample is
     * for the implementation reason in LTS.
     * <p/>
     * Duration of storage is stored in StorageMethodology element.
     *
     * @return
     */
    public List<Sample> getPatientStsSamples() {
        return null;
    }

    public List<Sample> getPatientLtsSamples() {

        Object result;

        try {
            // all child nodes of LTS module
            result = executeXPathForNodeSet(ROOT_ELEMENT + NAMESPACE_PREFIX_SLASHED + PATIENT_SAMPLE, document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return parseNodeList((NodeList) result);
    }


    private List<Sample> parseNodeList(NodeList nodes) {
        System.out.println("ParseNodeList");

        List<Sample> samples = new ArrayList<Sample>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            Sample sample = null;

            sample = parseSample(node);

            if (sample != null) {
                samples.add(sample);
            }

        }

        return samples;
    }

    private Sample parseSample(Node node) {

        // attributes
        String year;
        String number;
        String sampleId;

        // child elements
        String totalAliquots;
        String availableAliquots;
        String materialTypeImp;
        String tnmImp;
        String ptnmImp;
        String morphologyImp;
        String gradingImp;
        String takingDateTimeImp;
        String freezeDateTimeImp;
        String retrievedImp;

        // diagnosis
        // storageMethodology

        try {

            //  Attributes
            year = executeXPath("@" + SAMPLE_YEAR, node);
            number = executeXPath("@" + SAMPLE_NUMBER, node);
            sampleId = executeXPath("@" + SAMPLE_ID, node);

            //  child elements
            totalAliquots = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_TOTAL_ALIQUOTS, node);
            availableAliquots = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_AVAILABLE_ALIQUOTS, node);
            materialTypeImp = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_MATERIAL_TYPE, node);
            tnmImp = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_TNM, node);
            ptnmImp = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_PTNM, node);
            morphologyImp = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_MORPHOLOGY, node);
            gradingImp = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_GRADING, node);
            takingDateTimeImp = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_TAKING_DATE_TIME, node);
            freezeDateTimeImp = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_FREEZE_DATE_TIME, node);
            retrievedImp = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_RETRIEVED, node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse sample");
            return null;
        }

        List<String> diagnosisList = parseSampleDiagnosis(node);

        // End of parsing

        Sample sample = new Sample();

        BiopticalReport biopticalReport = parseBiopticalReport(number, year);
        if (biopticalReport != null) {
            biopticalReport.setSample(sample);
            sample.setBiopticalReport(biopticalReport);
        }


        if (sampleId == null) {
            System.err.println("SampleId is null");
            return null;
        }

        if (getBiobankPrefix() == null) {
            System.err.println("BiobankPrefix not set");
            return null;
        }

        // If has prefix than ok
        if (hasPrefix(sampleId)) {
            sample.setInstitutionalId(sampleId);
        } else {
            // otherwise prepend prefix of biobank
            sample.setInstitutionalId(getBiobankPrefix() + BIOBANK_PREFIX_SEPARATOR + sampleId);
        }

        // Material Type
        if (materialTypeImp != null) {

            // Hack to not need link BiobankDAO and BiobankMaterialTypeDAO here

            MaterialType materialType = new MaterialType();
            materialType.setName(materialTypeImp);
            sample.setMaterialType(materialType);
        }

        //      Number of samples
        Quantity quantity = parseQuantity(availableAliquots, totalAliquots);

        if (quantity != null) {
            sample.setQuantity(quantity);
        }

        //        TNM
        Tnm tnm = new Tnm();

        if (tnmImp != null) {
            tnm.setClassification(tnmImp);
            sample.setTnm(tnm);
        }

        //        pTNM
        Ptnm ptnm = new Ptnm();

        if (ptnmImp != null) {
            ptnm.setClassification(ptnmImp);
            sample.setPtnm(ptnm);
        }

        //        Morphology
        Morphology morphology = new Morphology();

        if (morphologyImp != null) {
            morphology.setClassification(morphologyImp);
        } else if (gradingImp != null) {
            morphology.setGrading(Short.parseShort(gradingImp));
        }
        sample.setMorphology(morphology);


        //        Taking time
        if (takingDateTimeImp != null) {

            java.util.Date date = parseDate(takingDateTimeImp);

            if (date != null) {
                sample.setTakingDateTime(date);
            }
        }

        //        Freeze time
        if (freezeDateTimeImp != null && (!freezeDateTimeImp.isEmpty())) {

            java.util.Date date = parseDate(freezeDateTimeImp);

            if (date != null) {
                sample.setFreezeDateTime(date);
            }
        }

        Retrieved retrieved = parseRetrieved(retrievedImp);
        if (retrieved != null) {
            sample.setRetrieved(retrieved);
        }

        if (diagnosisList != null) {
            for (String s : diagnosisList) {
                Diagnosis diagnosis = new Diagnosis();
                diagnosis.setKey(s);
                diagnosis.setSample(sample);
                sample.getDiagnosis().add(diagnosis);
            }
        }
        // Storage methodology
        StorageMethodology storageMethodology = parseStorageMethodology(node);

        if (storageMethodology != null) {
            sample.setStorageMethodology(storageMethodology);
        }

        return sample;
    }


    private List<String> parseSampleDiagnosis(Node node) {

        NodeList nodes;

        try {
            // all child nodes of LTS module
            nodes = (NodeList) executeXPathForNodeSet(ROOT_ELEMENT + NAMESPACE_PREFIX_SLASHED + SAMPLE_DIAGNOSIS, node);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (nodes == null) {
            return null;
        }

        List<String> diagnosis = new ArrayList<String>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node diagnosisNode = nodes.item(i);

            if (diagnosisNode.getTextContent() != null) {
                diagnosis.add(diagnosisNode.getTextContent());
            }

        }

        return diagnosis;
    }

    private StorageMethodology parseStorageMethodology(Node sampleNode) {

        Node node;

        try {
            // all child nodes of LTS module
            node = (Node) executeXPathForNode(ROOT_ELEMENT + NAMESPACE_PREFIX_SLASHED + SAMPLE_STORAGE_METHODOLOGY, sampleNode);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (node == null) {
            return null;
        }

        String temperatureCelsius;
        String methodology;
        String sts;
        String expiration;
        String medium;

        try {

            //  child elements
            temperatureCelsius = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_TEMPERATURE_CELSIUS, node);
            methodology = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_METHODOLOGY, node);
            sts = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_STS, node);
            expiration = executeXPath(NAMESPACE_PREFIX_COLONS + SAMPLE_EXPIRATION, node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse storage methodology");
            return null;
        }


        StorageMethodology storageMethodology = new StorageMethodology();

        if (temperatureCelsius != null) {
            Float temp = Float.parseFloat(temperatureCelsius);
            if (temp != null) {
                storageMethodology.setTemperatureCelsius(temp);
            }
        }

        if (methodology != null) {
            storageMethodology.setMethology(methodology);
        }

        if (sts != null) {
            storageMethodology.setSts(Boolean.parseBoolean(sts));
        }

        if (expiration != null) {
            java.util.Date date = parseDate(expiration);

            if (date != null) {
                storageMethodology.setExpiration(date);
            }
        }

        List<String> conservationMethodList = parseConservationMethod(node);

        if (conservationMethodList != null) {
            for (String s : conservationMethodList) {
                ConservationMethod conservationMethod = new ConservationMethod();
                conservationMethod.setKey(s);
                storageMethodology.getConservationMethod().add(conservationMethod);
            }
        }

        return storageMethodology;
    }

    private List<String> parseConservationMethod(Node node) {

        NodeList nodes;

        try {
            // all child nodes of LTS module
            nodes = (NodeList) executeXPathForNodeSet(ROOT_ELEMENT + NAMESPACE_PREFIX_SLASHED + SAMPLE_CONSERVATION_METHOD, node);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        if (nodes == null) {
            return null;
        }

        List<String> conservationMethod = new ArrayList<String>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node conservationMethodNode = nodes.item(i);

            if (conservationMethodNode.getTextContent() != null) {
                conservationMethod.add(conservationMethodNode.getTextContent());
            }
        }
        return conservationMethod;
    }

}
