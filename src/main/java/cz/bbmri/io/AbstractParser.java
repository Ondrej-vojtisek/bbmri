package cz.bbmri.io;

import cz.bbmri.entity.BiopticalReport;
import cz.bbmri.entity.Quantity;
import cz.bbmri.entity.Retrieved;
import cz.bbmri.entity.Sex;
import org.apache.axis2.databinding.types.xsd.GYear;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Base for parsers in IO package. Provides validation and evaluates queries for xml document using xpath.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
abstract class AbstractParser {

    public static final String BIOBANK_PREFIX_SEPARATOR = "_";

    // XML document - data import
    Document document;

    // Path of file
    private final String xmlFilePath;

    XPathFactory xpathFactory;

    // Instance of http://java.sun.com/javase/6/docs/api/javax/xml/namespace/NamespaceContext.html
    // Map of prexifes and namespaces (URI)
    private NamespaceContext namespaceContext;

    // Unique biobank prefix - biobank.institutionalId
    private String biobankPrefix;

    String getBiobankPrefix() {
        return biobankPrefix;
    }

    void setBiobankPrefix(String biobankPrefix) {
        this.biobankPrefix = biobankPrefix;
    }

    /**
     * Initialize
     *
     * @param path       - path to XML file
     * @param contextMap - map of namespaces and prefixes
     * @throws Exception
     */
    AbstractParser(String path, NamespaceContextMap contextMap) throws Exception {

        this.xmlFilePath = path;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Support for XML namespace
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        // initiate document
        document = builder.parse(path);
        // initiate XPath
        xpathFactory = XPathFactory.newInstance();
        // namespace context
        namespaceContext = contextMap;

    }

    /**
     * Return biobank institutional id.
     *
     * @return biobank institutional id or null
     */
    abstract String getBiobankInstitutionalId();

    /**
     * Single point to turn off logging of XPath
     *
     * @param xPathQuery
     */
    private void logger(String xPathQuery) {
        //  System.out.println("XPathQuery: " + xPathQuery);
    }

    /**
     * Execute XPath query on given node
     *
     * @param xPathQuery
     * @param node
     * @return result of XPath on a node - Node is returned
     * @throws XPathExpressionException
     */
    Object executeXPathForNode(String xPathQuery, Node node) throws XPathExpressionException {
        logger(xPathQuery);

        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(namespaceContext);
        XPathExpression expr = xpath.compile(xPathQuery);
        return expr.evaluate(node, XPathConstants.NODE);
    }

    /**
     * Execute XPath query on given node
     *
     * @param xPathQuery
     * @param node
     * @return result of XPath on a node - NodeSet is returned
     * @throws XPathExpressionException
     */
    Object executeXPathForNodeSet(String xPathQuery, Node node) throws XPathExpressionException {
        logger(xPathQuery);

        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(namespaceContext);
        XPathExpression expr = xpath.compile(xPathQuery);
        return expr.evaluate(node, XPathConstants.NODESET);
    }

    /**
     * Execute XPath query on a given node, return String
     *
     * @param xPathQuery
     * @param node
     * @return String value of query result
     * @throws XPathExpressionException
     */
    String executeXPath(String xPathQuery, Node node) throws XPathExpressionException {
        logger(xPathQuery);

        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(namespaceContext);
        XPathExpression expr = xpath.compile(xPathQuery);
        return (String) expr.evaluate(node, XPathConstants.STRING);
    }

    /**
     * String is not null and not empty
     *
     * @param s
     * @return
     */
    boolean notNullnotEmpty(String s) {
        return s != null && !s.isEmpty();

    }

    /**
     * Validation of document against given xsdURL
     *
     * @param xsdURL - URL of XML schema
     * @return true, false (error during validation), exception if not valid
     */
    boolean validateDocument(String xsdURL) {
        URL schemaFile;

        try {
            schemaFile = new URL(xsdURL);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return false;
        }
        // Initiate source file
        Source xmlFile = new StreamSource(new File(xmlFilePath));
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        try {
            schema = schemaFactory.newSchema(schemaFile);
        } catch (SAXException ex) {
            ex.printStackTrace();
            return false;
        }

        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);

        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " XML is NOT valid");
            System.out.println("Reason: " + e.getLocalizedMessage());
            return false;
        } catch (IOException ex) {
            System.out.println(xmlFile.getSystemId() + " XML IOException");
            System.out.println("Reason: " + ex.getLocalizedMessage());
            return false;
        }
        // XML is valid
        return true;

    }

    /**
     * Checks if given identifier starts with biobankPrefix.
     *
     * @param identifier
     * @return true if starts with biobankPrefix
     */
    boolean hasPrefix(String identifier) {
        if (identifier == null) return false;

        for (int i = 0; i < getBiobankPrefix().length(); i++) {
            if (identifier.charAt(i) != getBiobankPrefix().charAt(i)) {
                return false;
            }
        }

        return true;
    }

    abstract boolean validate();

    public Short parseBirthYear(String birthYear) {
    /* Use validity check from GYear factory */
        GYear gYear;
        try {
            gYear = GYear.Factory.fromString(birthYear, XMLConstants.W3C_XML_SCHEMA_NS_URI);
            return Short.parseShort(gYear.toString());

        } catch (NumberFormatException ex) {
            logger("Birth year of imported patient is not valid");
            return null;
        }

    }


    public Short parseBirthMonth(String birthMonth) {
        if (birthMonth == null) {
            logger("BirthMonth is null");
            return null;
        }

        if (birthMonth.isEmpty()) {
            return null;
        }

        // not empty
        // empty doesn't mean error - month is not mandatory
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
            return month;
        }
    }

    public Sex parseSex(String sex) {
        if (sex.equals(Sex.MALE.getName())) {

            return Sex.MALE;

        } else if (sex.equals(Sex.FEMALE.getName())) {

            return Sex.FEMALE;

        } else if (sex.equals(Sex.UNKNOWN.getName())) {

            return Sex.UNKNOWN;

        } else {
            logger("Patient sex wasn't recognized. Patient sex was: " + sex);
            return null;
        }
    }

    public java.util.Date parseDate(String dateTime) {
        java.util.Calendar calendar;
        calendar = javax.xml.bind.DatatypeConverter.parseDateTime(dateTime);

        if(calendar == null){
            return null;
        }

        return calendar.getTime();
    }

    public Retrieved parseRetrieved(String retrieved) {
        //        Retrieved
        if (retrieved != null) {

            if (retrieved.equals(Retrieved.PREOPERATIONAL.getName())) {
                return Retrieved.PREOPERATIONAL;
            }

            if (retrieved.equals(Retrieved.OPERATIONAL.getName())) {
                return Retrieved.OPERATIONAL;
            }
            if (retrieved.equals(Retrieved.UNKNOWN.getName())) {
                return Retrieved.UNKNOWN;
            }

            if (retrieved.equals(Retrieved.POST.getName())) {
                return Retrieved.POST;
            }
        }
        return null;
    }

    public BiopticalReport parseBiopticalReport(String number, String year) {

        BiopticalReport biopticalReport = new BiopticalReport();
        if (year != null) {
            biopticalReport.setYear(Short.parseShort(year));
        }
        if (number != null) {
            biopticalReport.setNumber(Integer.parseInt(number));
        } else {
            return null;
        }

        return biopticalReport;
    }

    public Quantity parseQuantity(String availableSamplesNoImp, String samplesNoImp) {

        Quantity quantity = new Quantity();

        quantity.setAvailable(Short.parseShort(availableSamplesNoImp));
        quantity.setTotal(Short.parseShort(samplesNoImp));
        quantity.setOriginal(Short.parseShort(samplesNoImp));

        return quantity;
    }
}
