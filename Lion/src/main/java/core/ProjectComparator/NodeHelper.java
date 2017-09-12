package core.ProjectComparator;

import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Piotr Majewski on 2017-09-11.
 */
public class NodeHelper {

    public static boolean checkNodeInListByName(NodeList nodeList, String nodeName) {
        boolean isInList = false;
        for (int i = 0; i < nodeList.getLength(); i++) {

            if (nodeList.item(i).getTextContent().equals(nodeName)) {
                return true;
            }
        }
        return isInList;
    }


    //Zwracamy text w Node o podanej nazwie
    //Jako Param podajemy NodeList
    public static String getTextFromNodeInList(NodeList nodeList, String node) {
        Element element = (Element) nodeList;
        NodeList nameList = element.getElementsByTagName(node);
        if (nameList.getLength() > 1) {
            Assert.fail("Znaleziono więcej nodów o podanej nazwie :" + node);
        }
        return nameList.item(0).getTextContent();
    }

    public static Document getDocument(String path) {
        Document xmlDocument = null;
        File sourceFile = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            xmlDocument = db.parse(sourceFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlDocument.getDocumentElement().normalize();
        return xmlDocument;

    }

}
