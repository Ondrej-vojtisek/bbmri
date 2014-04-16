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
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.3.14
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
abstract class AbstractParser {

    //    XML document - data import
    Document document;

    private final String xmlFilePath;

    XPathFactory xpathFactory;

    private NamespaceContext namespaceContext;

    private String biobankPrefix;

    String getBiobankPrefix() {
        return biobankPrefix;
    }

    void setBiobankPrefix(String biobankPrefix) {
        this.biobankPrefix = biobankPrefix;
    }

    AbstractParser(String path, NamespaceContextMap contextMap) throws Exception {

        this.xmlFilePath = path;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true); // never forget this!

        DocumentBuilder builder = factory.newDocumentBuilder();

        // initiate document
        document = builder.parse(path);

        xpathFactory = XPathFactory.newInstance();

        // namespace context
        namespaceContext = contextMap;

    }

    abstract String getBiobankId();

    private void logger(String xPathQuery) {
        //  System.out.println("XPathQuery: " + xPathQuery);
    }

    Object executeXPathForNodeSet(String xPathQuery, Node node) throws XPathExpressionException {
        logger(xPathQuery);

        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(namespaceContext);
        XPathExpression expr = xpath.compile(xPathQuery);
        return expr.evaluate(node, XPathConstants.NODESET);
    }

    String executeXPath(String xPathQuery, Node node) throws XPathExpressionException {
        logger(xPathQuery);

        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(namespaceContext);
        XPathExpression expr = xpath.compile(xPathQuery);
        return (String) expr.evaluate(node, XPathConstants.STRING);
    }

    // tests if string not null and not empty
    boolean notNullnotEmpty(String s) {
        return s != null && !s.isEmpty();

    }

    boolean validateDocument(String xsdURL) {
        URL schemaFile;

        try {
            schemaFile = new URL(xsdURL);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return false;
        }
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

        return true;

    }

    //    Identifier must start with biobank prefix. Otherwise false is returned
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
