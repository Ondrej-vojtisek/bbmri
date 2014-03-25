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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.3.14
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class PatientDataParser extends AbstractParser {

    public PatientDataParser(String path) throws Exception {
        super(path);
    }

    public String getBiobankId() {

        String biobankId = null;

        try {

              biobankId = executeXPath("/patient/@biobank", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return biobankId;
    }

    public Patient getPatient() {

        String patientId = null;
        String birthYear = null;
        String birthMonth = null;
        String sex = null;
        String consent = null;

        try {

            patientId = executeXPath("/patient/@id", document);

            birthYear = executeXPath("/patient/@year", document);

            birthMonth = executeXPath("/patient/@month", document);

            sex = executeXPath("/patient/@sex", document);

            consent = executeXPath("/patient/@consent", document);

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
        GYear gYear = null;
        try {
            gYear = GYear.Factory.fromString(birthYear, NAMESPACE);

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
            // expect format --02 or --02--
            int month = Integer.parseInt(birthMonth.substring(2, 4));

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

        patient.setInstitutionId(patientId);

        return patient;
    }

    public List<Sample> getPatientLtsSamples() {
        System.out.println("GetPatientLTSSample");

        Object result = null;

        try {
            // all child nodes of LTS module
            result = executeXPathForNodeSet("/patient/LTS/*", document);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return parseNodeList((NodeList) result, false);
    }


    public List<Sample> getPatientStsSamples() {

        Object result = null;

        try {
            // all child nodes of STS module
            result = executeXPathForNodeSet("/patient/STS/*", document);

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
        String samplesNo = null;
        String availableSamplesNo = null;
        String TNM = null;
        String pTNM = null;
        String morphology = null;
        String grading = null;

        String freezeTime = null;

        try {

//            Child elements
            samplesNo = executeXPath("samplesNo", node);

            availableSamplesNo = executeXPath("availableSamplesNo", node);

            TNM = executeXPath("TNM", node);

            pTNM = executeXPath("pTNM", node);

            morphology = executeXPath("morphology", node);

            grading = executeXPath("grading", node);

            freezeTime = executeXPath("freezeTime", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }

        Tissue tissue = new Tissue();

//      Set all shared atributes
        setSharedAtributes(sample, (Sample) tissue);

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
            DateTime dt = DateTime.Factory.fromString(freezeTime, NAMESPACE);

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

        String samplesNo = null;
        String availableSamplesNo = null;

        try {

            //            Child elements
            samplesNo = executeXPath("samplesNo", node);

            availableSamplesNo = executeXPath("availableSamplesNo", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }

        Serum serum = new Serum();

        //      Set all shared atributes
        setSharedAtributes(sample, (Sample) serum);


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

        String samplesNo = null;
        String availableSamplesNo = null;

        try {

            //            Child elements

            samplesNo = executeXPath("samplesNo", node);

            availableSamplesNo = executeXPath("availableSamplesNo", node);


        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }

        Genome genome = new Genome();

        //      Set all shared atributes
        setSharedAtributes(sample, (Sample) genome);

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

        String diagnosis = null;

        try {

            //            Child elements
            diagnosis = executeXPath("diagnosis", node);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse Tissue");
            return null;
        }


        DiagnosisMaterial diagnosisMaterial = new DiagnosisMaterial();

        //      Set all shared atributes
        setSharedAtributes(sample, (Sample) diagnosisMaterial);

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
        String year = null;
        String number = null;
        String sampleId = null;

        // child elements
        String materialType = null;
        String retrieved = null;
        String takingDate = null;

        try {

            //            Attributes

            year =  executeXPath("@year", node);

            number = executeXPath("@number", node);

            sampleId = executeXPath("@sampleId", node);

            // child elements

            materialType = executeXPath("materialType", node);

            retrieved = executeXPath("retrieved", node);

            // cut time for tissue
            if (tissue) {
                takingDate = executeXPath("cutTime", node);
            } else {
                // taking date for other types
                takingDate = executeXPath("takingDate", node);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to parse sample");
            return null;
        }

        Sample sample = new Sample();
        sample.setSampleIdentification(new SampleIdentification());

        if (year != null) {
            sample.getSampleIdentification().setYear(year);
        }

        if (number != null) {
            sample.getSampleIdentification().setNumber(number);
        }

        if (sampleId != null) {
            sample.getSampleIdentification().setSampleId(sampleId);
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
            DateTime dt = DateTime.Factory.fromString(takingDate, NAMESPACE);

            // xsd:DateTime->Calendar->Date

            sample.setTakingDate(dt.getDateTime().getTime());
        }

        return sample;

    }
}
