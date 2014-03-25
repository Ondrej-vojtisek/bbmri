package cz.bbmri.io;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 23.3.14
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class BiobankOccupancyDataParser {

    //    XML document - data import
    private Document document;

    private XPathFactory xpathFactory;

    public BiobankOccupancyDataParser(String path) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();

        // initiate document
        document = builder.parse(path);

        xpathFactory = XPathFactory.newInstance();

    }
}
