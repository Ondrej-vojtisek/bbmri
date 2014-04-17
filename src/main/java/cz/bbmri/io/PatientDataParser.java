package cz.bbmri.io;

import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.enumeration.Retrieved;
import cz.bbmri.entities.enumeration.Sex;
import cz.bbmri.entities.sample.DiagnosisMaterial;
import cz.bbmri.entities.sample.Genome;
import cz.bbmri.entities.sample.Serum;
import cz.bbmri.entities.sample.Tissue;
import cz.bbmri.entities.sample.field.*;
import org.apache.axis2.databinding.types.xsd.DateTime;
import org.apache.axis2.databinding.types.xsd.GYear;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.List;


/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class PatientDataParser extends AbstractParser {

    private static final String PATIENT_XSD_URL = "http://www.bbmri.cz/schemas/biobank/data.xsd";
    private static final String DEFAULT_NAMESPACE = "http://www.bbmri.cz/schemas/biobank/data";
    private static final String NAMESPACE_PREFIX = "def";
    private static final String NAMESPACE_PREFIX_COLONS = NAMESPACE_PREFIX + ":";
    private static final String NAMESPACE_PREFIX_SLASHED = "/" + NAMESPACE_PREFIX_COLONS;
    private static final String ROOT_ELEMENT = NAMESPACE_PREFIX_SLASHED + "patient";


    public PatientDataParser(String path) throws Exception {

        super(path, new NamespaceContextMap(
                NAMESPACE_PREFIX, DEFAULT_NAMESPACE));
    }

    public boolean validate() {
        return validateDocument(PATIENT_XSD_URL);
    }

    public String getBiobankId() {

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
            return null;
        } else {
            patient.setConsent(true);
        }

//        YEAR

        /* Use validity check from GYear factory */
        GYear gYear;
        try {
            System.err.println("birthYear: " + birthYear);
            gYear = GYear.Factory.fromString(birthYear, XMLConstants.W3C_XML_SCHEMA_NS_URI);

        } catch (NumberFormatException ex) {
            System.err.println("Birth year of imported patient is not valid");
            return null;
        }

        if (gYear != null) {
            patient.setBirthYear(Integer.parseInt(gYear.toString()));
        } else {
            System.err.println("Birth year of imported patient is not valid");
            return null;
        }

//        MONTH

        if (birthMonth != null) {
            // for format of gMonth
            if (birthMonth.length() > 2) {
                // expect format --02 or --02--
                birthMonth = birthMonth.substring(2, 4);
            }
            int month = Integer.parseInt(birthMonth);
            // month
            if (month > 12 || month < 1) {
                System.err.println("Birth month of imported patient is not valid");
                return null;
            } else {
                patient.setBirthMonth(month);
            }
        }

//        SEX

        if (sex.equals(Sex.MALE.toString())) {
            patient.setSex(Sex.MALE);
        } else if (sex.equals(Sex.FEMALE.toString())) {
            patient.setSex(Sex.FEMALE);
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
            patient.setInstitutionId(getBiobankPrefix() + patientId);
        } else {
            patient.setInstitutionId(patientId);
        }

        return patient;
    }

    public List<Sample> getPatientLtsSamples() {
        System.out.println("GetPatientLTSSample");

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

    private List<Sample> parseNodeList(NodeList nodes, boolean lts) {
        System.out.println("ParseNodeList");

        List<Sample> samples = new ArrayList<Sample>();

        // for-each not applicable
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            if (node.getNodeName().equals("tissue")) {
                Tissue tissue = parseTissue(node);
                samples.add(tissue);
                continue;
            }

            if (node.getNodeName().equals("serum")) {
                Serum serum = parseSerum(node);
                samples.add(serum);
                continue;
            }

            if (node.getNodeName().equals("genome")) {
                Genome genome = parseGenome(node);
                samples.add(genome);
                continue;
            }

            if (node.getNodeName().equals("diagnosisMaterial")) {
                DiagnosisMaterial dm = parseDiagnosisMaterial(node, lts);
                samples.add(dm);
                continue;
            }

        }

        return samples;
    }


    private Tissue parseTissue(Node node) {

        Sample sample = parseSharedAtributes(node, true);

        if (sample == null) {
            System.err.println("Failed to parse Tissue");
            return null;
        }

        // child elements
        String samplesNo;
        String availableSamplesNo;
        String TNM;
        String pTNM;
        String morphology;
        String grading;

        String freezeTime;

        try {

//            Child elements
            samplesNo = executeXPath(NAMESPACE_PREFIX_COLONS + "samplesNo", node);

            availableSamplesNo = executeXPath(NAMESPACE_PREFIX_COLONS + "availableSamplesNo", node);

            TNM = executeXPath(NAMESPACE_PREFIX_COLONS + "TNM", node);

            pTNM = executeXPath(NAMESPACE_PREFIX_COLONS + "pTNM", node);

            morphology = executeXPath(NAMESPACE_PREFIX_COLONS + "morphology", node);

            grading = executeXPath(NAMESPACE_PREFIX_COLONS + "grading", node);

            freezeTime = executeXPath(NAMESPACE_PREFIX_COLONS + "freezeTime", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }

        Tissue tissue = new Tissue();

//      Set all shared atributes
        setSharedAtributes(sample, tissue);

//      Number of samples
        tissue.setSampleNos(new SampleNos());

        if (samplesNo != null) {
            tissue.getSampleNos().setSamplesNo(Integer.parseInt(samplesNo));
        }

        if (availableSamplesNo != null) {
            tissue.getSampleNos().setAvailableSamplesNo(Integer.parseInt(availableSamplesNo));
        }

//        TNM
        tissue.setTnm(new TNM());

        if (TNM != null) {
            tissue.getTnm().setClassification(TNM);
        }

//        pTNM
        tissue.setPtnm(new PTNM());

        if (pTNM != null) {
            tissue.getPtnm().setClassification(pTNM);
        }

//        Morphology
        tissue.setMorphology(new Morphology());
        if (morphology != null) {
            tissue.getMorphology().setClassification(morphology);
        } else if (grading != null) {
            tissue.getMorphology().setGrading(Integer.parseInt(grading));
        }


//        Freeze time
        if (freezeTime != null) {

            // xsd:DateTime
            DateTime dt = DateTime.Factory.fromString(freezeTime, XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // xsd:DateTime->Calendar->Date

            tissue.setFreezeDate(dt.getDateTime().getTime());
        }

        return tissue;
    }

    private Serum parseSerum(Node node) {

        Sample sample = parseSharedAtributes(node, false);

        if (sample == null) {
            System.err.println("Failed to parse serum");
            return null;
        }

        String samplesNo;
        String availableSamplesNo;

        try {

            //            Child elements
            samplesNo = executeXPath(NAMESPACE_PREFIX_COLONS + "samplesNo", node);

            availableSamplesNo = executeXPath(NAMESPACE_PREFIX_COLONS + "availableSamplesNo", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }

        Serum serum = new Serum();

        //      Set all shared atributes
        setSharedAtributes(sample, serum);


        //      Number of samples
        serum.setSampleNos(new SampleNos());

        if (samplesNo != null) {
            serum.getSampleNos().setSamplesNo(Integer.parseInt(samplesNo));
        }

        if (availableSamplesNo != null) {
            serum.getSampleNos().setAvailableSamplesNo(Integer.parseInt(availableSamplesNo));
        }

        return serum;
    }

    private Genome parseGenome(Node node) {

        Sample sample = parseSharedAtributes(node, false);

        if (sample == null) {
            System.err.println("Failed to parse genome");
            return null;
        }

        String samplesNo;
        String availableSamplesNo;

        try {

            //            Child elements

            samplesNo = executeXPath(NAMESPACE_PREFIX_COLONS + "samplesNo", node);

            availableSamplesNo = executeXPath(NAMESPACE_PREFIX_COLONS + "availableSamplesNo", node);


        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Genome");
            return null;
        }

        Genome genome = new Genome();

        //      Set all shared atributes
        setSharedAtributes(sample, genome);

        //      Number of samples
        genome.setSampleNos(new SampleNos());

        if (samplesNo != null) {
            genome.getSampleNos().setSamplesNo(Integer.parseInt(samplesNo));
        }

        if (availableSamplesNo != null) {
            genome.getSampleNos().setAvailableSamplesNo(Integer.parseInt(availableSamplesNo));
        }

        return genome;
    }

    private DiagnosisMaterial parseDiagnosisMaterial(Node node, boolean lts) {
        XPath xpath = xpathFactory.newXPath();

        Sample sample = parseSharedAtributes(node, false);

        if (sample == null) {
            System.err.println("Failed to parse diagnosisMaterial");
            return null;
        }

        String diagnosis;

        try {

            //            Child elements
            diagnosis = executeXPath(NAMESPACE_PREFIX_COLONS + "diagnosis", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Diagnosis material");
            return null;
        }


        DiagnosisMaterial diagnosisMaterial = new DiagnosisMaterial();

        //      Set all shared atributes
        setSharedAtributes(sample, diagnosisMaterial);

        diagnosisMaterial.setDiagnosis(new Diagnosis());
        if (diagnosis != null) {
            diagnosisMaterial.getDiagnosis().setClassification(diagnosis);
        }

        return diagnosisMaterial;
    }

    private void setSharedAtributes(Sample source, Sample dest) {
        if (source == null || dest == null) {
            System.err.println("Null arguments");
            return;
        }

        if (dest.getSampleIdentification() == null) {
            dest.setSampleIdentification(new SampleIdentification());
        }

        if (source.getSampleIdentification().getYear() != null) {
            dest.getSampleIdentification().setYear(source.getSampleIdentification().getYear());
        }

        if (source.getSampleIdentification().getNumber() != null) {
            dest.getSampleIdentification().setNumber(source.getSampleIdentification().getNumber());
        }

        if (source.getSampleIdentification().getSampleId() != null) {
            dest.getSampleIdentification().setSampleId(source.getSampleIdentification().getSampleId());
        }

        //        Material type
        if (source.getMaterialType() != null) {
            dest.setMaterialType(source.getMaterialType());
        }

        if (source.getRetrieved() != null) {
            dest.setRetrieved(source.getRetrieved());
        }

        if (source.getTakingDate() != null) {
            dest.setTakingDate(source.getTakingDate());
        }


    }

    private Sample parseSharedAtributes(Node node, boolean tissue) {
        // attributes
        String year;
        String number;
        String sampleId;

        // child elements
        String materialType;
        String retrieved;
        String takingDate;

        try {

            //            Attributes

            year = executeXPath("@year", node);

            number = executeXPath("@number", node);

            sampleId = executeXPath("@sampleId", node);

            // child elements

            materialType = executeXPath(NAMESPACE_PREFIX_COLONS + "materialType", node);

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
        sample.setSampleIdentification(new SampleIdentification());

        if (notNullnotEmpty(year)) {
            sample.getSampleIdentification().setYear(Integer.parseInt(year));
        }

        if (notNullnotEmpty(number)) {
            sample.getSampleIdentification().setNumber(Integer.parseInt(number));
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
            sample.getSampleIdentification().setSampleId(sampleId);
        }else{
            // otherwise prepend prefix of biobank
            sample.getSampleIdentification().setSampleId(getBiobankPrefix() + sampleId);
        }

        sample.setMaterialType(new MaterialType());
        if (materialType != null) {
            sample.getMaterialType().setType(materialType);
        }


        //        Retrieved
        if (retrieved != null) {

            if (retrieved.equals(Retrieved.PREOPERATIONAL.toString())) {
                sample.setRetrieved(Retrieved.PREOPERATIONAL);
            }

            if (retrieved.equals(Retrieved.OPERATIONAL.toString())) {
                sample.setRetrieved(Retrieved.OPERATIONAL);
            }
            if (retrieved.equals(Retrieved.UNKNOWN.toString())) {
                sample.setRetrieved(Retrieved.UNKNOWN);
            }

            if (retrieved.equals(Retrieved.POST.toString())) {
                sample.setRetrieved(Retrieved.POST);
            }

        }

        //   Taking date or cut time
        if (takingDate != null) {

            // xsd:DateTime
            DateTime dt = DateTime.Factory.fromString(takingDate, XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // xsd:DateTime->Calendar->Date

            sample.setTakingDate(dt.getDateTime().getTime());
        }

        return sample;

    }


}
