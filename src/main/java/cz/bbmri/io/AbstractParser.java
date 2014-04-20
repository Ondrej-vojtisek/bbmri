package cz.bbmri.io;

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

    // XML document - data import
    Document document;

    // Path of file
    private final String xmlFilePath;

    XPathFactory xpathFactory;

    // Instance of http://java.sun.com/javase/6/docs/api/javax/xml/namespace/NamespaceContext.html
    // Map of prexifes and namespaces (URI)
    private NamespaceContext namespaceContext;

    // Unique biobank prefix - biobank.abbreviation
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
     * Return biobank abbreviation.
     *
     * @return biobank abbretivaion or null
     */
    abstract String getBiobankAbbreviation();

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
     * Checks if given indentifier starts with biobankPrefix.
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
}
