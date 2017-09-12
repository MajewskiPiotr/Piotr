package core.ProjectComparator;

import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-09-08.
 */
public class IssueTypeScheme {

    private String name;
    private String desc;
    private String defaultType;
    public List<String> issueTypeWithSequence = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }


    public void addToIssueType(String[] issueType) {
        for (int i = 0; i < issueType.length; i++) {
            issueTypeWithSequence.add(issueType[i]);
        }
    }

    /*Metoda zwraca obiekt IssueTypeScheme wypełniony danymi z pliku xml
    *Param - String - Path to file
      */
    public static IssueTypeScheme getIssueTypeSchemFromXMLFILE(String xmlFile, String IssueTypeSchemeName) {
        Document xmlDocument = null;
        File sourceFile = new File(xmlFile);
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
        IssueTypeScheme issueTypeScheme = new IssueTypeScheme();

        NodeList nodeList2 = xmlDocument.getElementsByTagName("issueTypeScheme");
        if (nodeList2.getLength() == 0) {
            Assert.fail("Brak issueTypeScheme w podanym pliku");
        }
        //Wyciągamy liste dla źródła
        for (int x = 0; x < nodeList2.getLength(); x++) {
            Node innerNode2 = nodeList2.item(x);
            //weryfikuje czy element jest tym który mnie interesuje tj. tym z wartościami
            if (innerNode2.getNodeType() == Node.ELEMENT_NODE && innerNode2.getChildNodes().getLength() > 1) {
                NodeList childNodesList = innerNode2.getChildNodes();
                if (NodeHelper.checkNodeInListByName(childNodesList, IssueTypeSchemeName)) {
                    issueTypeScheme.setName(NodeHelper.getTextFromNodeInList(childNodesList, "name"));
                    issueTypeScheme.setDesc(NodeHelper.getTextFromNodeInList(childNodesList, "description"));
                    issueTypeScheme.setDefaultType(NodeHelper.getTextFromNodeInList(childNodesList, "defaultType"));
                    issueTypeScheme.addToIssueType(setIssueType(childNodesList));
                    System.out.println(issueTypeScheme);
                } else {
                    Assert.fail("Nie znaleziono Issue type Scheme o podanej nazwie :");
                }
            }
        }
        return issueTypeScheme;
    }

    //Metoda zwraca liste Issue Typów z przekazanej listy Nodów
    //Jako param podajemy ChildList issueTypeScheme
    public static String[] setIssueType(NodeList nodeList) {
        Element element = (Element) nodeList;
        NodeList nameList = element.getElementsByTagName("issueType");
        String[] lista = new String[nameList.getLength()];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = nameList.item(i).getTextContent();
        }
        return lista;
    }






    @Override
    public String toString() {
        return "IssueTypeScheme{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", defaultType='" + defaultType + '\'' +
                ", issueTypeWithSequence=" + issueTypeWithSequence +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueTypeScheme that = (IssueTypeScheme) o;

        if (!name.equals(that.name)) return false;
        if (!desc.equals(that.desc)) return false;
        if (!defaultType.equals(that.defaultType)) return false;
        return issueTypeWithSequence.equals(that.issueTypeWithSequence);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + defaultType.hashCode();
        result = 31 * result + issueTypeWithSequence.hashCode();
        return result;
    }
}
