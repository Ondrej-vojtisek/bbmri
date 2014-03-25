package cz.bbmri.io;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.3.14
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractParser {

    protected static final String NAMESPACE = "http://www.w3.org/2001/XMLSchema";

    //    XML document - data import
    protected Document document;

    protected XPathFactory xpathFactory;

    public AbstractParser(String path) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true); // never forget this!

        DocumentBuilder builder = factory.newDocumentBuilder();

        // initiate document
        document = builder.parse(path);

        xpathFactory = XPathFactory.newInstance();

    }

    abstract String getBiobankId();

    private void logger(String xPathQuery){
      //  System.out.println("XPathQuery: " + xPathQuery);
    }

    protected Object executeXPathForNodeSet(String xPathQuery, Node node) throws XPathExpressionException {
        logger(xPathQuery);

        XPath xpath = xpathFactory.newXPath();
        XPathExpression expr = xpath.compile(xPathQuery);
        return expr.evaluate(node, XPathConstants.NODESET);
    }

    protected String executeXPath(String xPathQuery, Node node) throws XPathExpressionException {
        logger(xPathQuery);

        XPath xpath = xpathFactory.newXPath();
        XPathExpression expr = xpath.compile(xPathQuery);
        return (String) expr.evaluate(node, XPathConstants.STRING);
    }

    // tests if string not null and not empty
    protected boolean notNullnotEmpty(String s){
        if(s == null){
            return false;
        }

        return !s.isEmpty();

    }
}
