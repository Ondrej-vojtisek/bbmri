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
 * Parses import about samples stored in biobank. Imports are based on http://www.bbmri.cz/schemas/monitoring/data.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class PatientDataParser extends AbstractParser {

    // Reference XML schema to validate imports
    private static final String PATIENT_XSD_URL = "http://www.bbmri.cz/schemas/biobank/data.xsd";

    // Default namespace of biobank data import
    private static final String DEFAULT_NAMESPACE = "http://www.bbmri.cz/schemas/biobank/data";
    private static final String NAMESPACE_PREFIX = "def";

    // Simplification of XPath
    private static final String NAMESPACE_PREFIX_COLONS = NAMESPACE_PREFIX + ":";
    private static final String NAMESPACE_PREFIX_SLASHED = "/" + NAMESPACE_PREFIX_COLONS;
    private static final String ROOT_ELEMENT = NAMESPACE_PREFIX_SLASHED + "patient";

    public static final String BIOBANK_PREFIX_SEPARATOR = "_";


    public PatientDataParser(String path) throws Exception {

        super(path, new NamespaceContextMap(
                NAMESPACE_PREFIX, DEFAULT_NAMESPACE));
    }

    public boolean validate() {
        return validateDocument(PATIENT_XSD_URL);
    }

    public String getBiobankInstitutionalId() {

        String biobankId;

        try {

            biobankId = executeXPath(ROOT_ELEMENT + "/@biobank", document);
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

            patientId = executeXPath(ROOT_ELEMENT + "/@id", document);

            birthYear = executeXPath(ROOT_ELEMENT + "/@year", document);

            birthMonth = executeXPath(ROOT_ELEMENT + "/@month", document);

            sex = executeXPath(ROOT_ELEMENT + "/@sex", document);

            consent = executeXPath(ROOT_ELEMENT + "/@consent", document);

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

        /* Use validity check from GYear factory */
        GYear gYear;
        try {
            gYear = GYear.Factory.fromString(birthYear, XMLConstants.W3C_XML_SCHEMA_NS_URI);

        } catch (NumberFormatException ex) {
            System.err.println("Birth year of imported patient is not valid");
            return null;
        }

        if (gYear != null) {
            patient.setBirthYear(Short.parseShort(gYear.toString()));
        } else {
            System.err.println("Birth year of imported patient is not valid");
            return null;
        }

//        MONTH

        if (birthMonth != null) {
            // not empty
            // empty doesn't mean error - it is not mandatory
            if (!birthMonth.isEmpty()) {
                // for format of gMonth
                if (birthMonth.length() > 2) {
                    // expect format --02 or --02--
                    birthMonth = birthMonth.substring(2, 4);
                }
                short month = Short.parseShort(birthMonth);
                // month
                if (month > 12 || month < 1) {
                    System.err.println("Birth month of imported patient is not valid");
                    return null;
                } else {
                    patient.setBirthMonth(month);
                }
            }
        }

//        SEX

        if (sex.equals(Sex.MALE.getName())) {

            patient.setSex(Sex.MALE);

        } else if (sex.equals(Sex.FEMALE.getName())) {

            patient.setSex(Sex.FEMALE);

        } else if (sex.equals(Sex.UNKNOWN.getName())) {

            patient.setSex(Sex.UNKNOWN);

        } else {

            System.err.println("Patient sex wasn't recognized. Patient sex was: " + sex);
            return null;

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

    public List<Sample> getPatientLtsSamples() {

        Object result;

        try {
            // all child nodes of LTS module
            result = executeXPathForNodeSet(ROOT_ELEMENT + NAMESPACE_PREFIX_SLASHED + "LTS/*", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return parseNodeList((NodeList) result, false);
    }


    public List<Sample> getPatientStsSamples() {

        Object result;

        try {
            // all child nodes of STS module
            result = executeXPathForNodeSet(ROOT_ELEMENT + NAMESPACE_PREFIX_SLASHED + "STS/*", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return parseNodeList((NodeList) result, true);
    }

    private List<Sample> parseNodeList(NodeList nodes, boolean sts) {
        System.out.println("ParseNodeList");

        List<Sample> samples = new ArrayList<Sample>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            Sample sample = null;

            if (node.getNodeName().equals("tissue")) {
                sample = parseTissue(node);
            }

            if (node.getNodeName().equals("serum")) {
                sample = parseSerum(node);
            }

            if (node.getNodeName().equals("genome")) {
                sample = parseGenome(node);
            }

            if (node.getNodeName().equals("diagnosisMaterial")) {
                sample = parseDiagnosisMaterial(node);
            }

            if (sample != null) {
                StorageMethodology storageMethodology = new StorageMethodology();
                storageMethodology.setSts(sts);

                sample.setStorageMethodology(storageMethodology);

                samples.add(sample);
            }

        }

        return samples;
    }


    private Sample parseTissue(Node node) {

        Sample sample = parseSharedAtributes(node, true);

        if (sample == null) {
            System.err.println("Failed to parse Tissue");
            return null;
        }

        // child elements
        String samplesNoImp;
        String availableSamplesNoImp;
        String tnmImp;
        String ptnmImp;
        String morphologyImp;
        String gradingImp;

        String freezeDateImp;

        try {

//            Child elements
            samplesNoImp = executeXPath(NAMESPACE_PREFIX_COLONS + "samplesNo", node);

            availableSamplesNoImp = executeXPath(NAMESPACE_PREFIX_COLONS + "availableSamplesNo", node);

            tnmImp = executeXPath(NAMESPACE_PREFIX_COLONS + "TNM", node);

            ptnmImp = executeXPath(NAMESPACE_PREFIX_COLONS + "pTNM", node);

            morphologyImp = executeXPath(NAMESPACE_PREFIX_COLONS + "morphology", node);

            gradingImp = executeXPath(NAMESPACE_PREFIX_COLONS + "grading", node);

            freezeDateImp = executeXPath(NAMESPACE_PREFIX_COLONS + "freezeTime", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }
        //      Number of samples
        Quantity quantity = new Quantity();

        quantity.setAvailable(Short.parseShort(availableSamplesNoImp));
        quantity.setTotal(Short.parseShort(samplesNoImp));
        quantity.setOriginal(Short.parseShort(samplesNoImp));

        sample.setQuantity(quantity);


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


//        Freeze time
        if (freezeDateImp != null) {

            java.util.Date date = parseDate(freezeDateImp);

            if (date != null) {
                sample.setFreezeDateTime(date);
            }

        }

        return sample;
    }

    private Sample parseSerum(Node node) {

        Sample sample = parseSharedAtributes(node, false);

        if (sample == null) {
            System.err.println("Failed to parse serum");
            return null;
        }

        String samplesNoImp;
        String availableSamplesNoImp;

        try {

            //            Child elements
            samplesNoImp = executeXPath(NAMESPACE_PREFIX_COLONS + "samplesNo", node);

            availableSamplesNoImp = executeXPath(NAMESPACE_PREFIX_COLONS + "availableSamplesNo", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }

        //      Number of samples
        Quantity quantity = new Quantity();

        quantity.setAvailable(Short.parseShort(availableSamplesNoImp));
        quantity.setTotal(Short.parseShort(samplesNoImp));
        quantity.setOriginal(Short.parseShort(samplesNoImp));

        sample.setQuantity(quantity);

        return sample;
    }

    private Sample parseGenome(Node node) {

        Sample sample = parseSharedAtributes(node, false);

        if (sample == null) {
            System.err.println("Failed to parse genome");
            return null;
        }

        String samplesNoImp;
        String availableSamplesNoImp;

        try {

            //            Child elements

            samplesNoImp = executeXPath(NAMESPACE_PREFIX_COLONS + "samplesNo", node);

            availableSamplesNoImp = executeXPath(NAMESPACE_PREFIX_COLONS + "availableSamplesNo", node);


        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Genome");
            return null;
        }
        //      Number of samples
        Quantity quantity = new Quantity();

        quantity.setAvailable(Short.parseShort(availableSamplesNoImp));
        quantity.setTotal(Short.parseShort(samplesNoImp));
        quantity.setOriginal(Short.parseShort(samplesNoImp));

        sample.setQuantity(quantity);

        return sample;
    }

    private Sample parseDiagnosisMaterial(Node node) {
        XPath xpath = xpathFactory.newXPath();

        Sample sample = parseSharedAtributes(node, false);

        if (sample == null) {
            System.err.println("Failed to parse diagnosisMaterial");
            return null;
        }

        String diagnosisImp;

        try {

            //            Child elements
            diagnosisImp = executeXPath(NAMESPACE_PREFIX_COLONS + "diagnosis", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Diagnosis material");
            return null;
        }

        Diagnosis diagnosis = new Diagnosis();
        if (diagnosisImp != null) {
            diagnosis.setKey(diagnosisImp);
        }

        sample.getDiagnosis().add(diagnosis);

        return sample;
    }


    private Sample parseSharedAtributes(Node node, boolean tissue) {
        // attributes
        String year;
        String number;
        String sampleId;

        // child elements
        String materialTypeImp;
        String retrieved;
        String takingDate;

        try {

            //            Attributes

            year = executeXPath("@year", node);

            number = executeXPath("@number", node);

            sampleId = executeXPath("@sampleId", node);

            // child elements

            materialTypeImp = executeXPath(NAMESPACE_PREFIX_COLONS + "materialType", node);

            retrieved = executeXPath(NAMESPACE_PREFIX_COLONS + "retrieved", node);

            // cut time for tissue
            if (tissue) {
                takingDate = executeXPath(NAMESPACE_PREFIX_COLONS + "cutTime", node);
            } else {
                // taking date for other types
                takingDate = executeXPath(NAMESPACE_PREFIX_COLONS + "takingDate", node);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse sample");
            return null;
        }

        Sample sample = new Sample();

        BiopticalReport biopticalReport = new BiopticalReport();
        if (year != null) {
            biopticalReport.setYear(Short.parseShort(year));
        }
        if (number != null) {
            biopticalReport.setNumber(Integer.parseInt(number));
        }
        biopticalReport.setSample(sample);
        sample.setBiopticalReport(biopticalReport);

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

        //        Retrieved
        if (retrieved != null) {

            if (retrieved.equals(Retrieved.PREOPERATIONAL.getName())) {
                sample.setRetrieved(Retrieved.PREOPERATIONAL);
            }

            if (retrieved.equals(Retrieved.OPERATIONAL.getName())) {
                sample.setRetrieved(Retrieved.OPERATIONAL);
            }
            if (retrieved.equals(Retrieved.UNKNOWN.getName())) {
                sample.setRetrieved(Retrieved.UNKNOWN);
            }

            if (retrieved.equals(Retrieved.POST.getName())) {
                sample.setRetrieved(Retrieved.POST);
            }

        }

        //   Taking date or cut time
        if (takingDate != null) {

            java.util.Date date = parseDate(takingDate);

            if (date != null) {
                sample.setTakingDateTime(date);
            }

        }

        if (materialTypeImp != null) {

            // Hack to not need link BiobankDAO and BiobankMaterialTypeDAO here

            MaterialType materialType = new MaterialType();
            materialType.setName(materialTypeImp);
            sample.setMaterialType(materialType);
        }

        return sample;

    }

    private java.util.Date parseDate(String dateTime) {

        java.util.Calendar calendar;
        calendar = javax.xml.bind.DatatypeConverter.parseDateTime(dateTime);

        return calendar.getTime();
    }

}
