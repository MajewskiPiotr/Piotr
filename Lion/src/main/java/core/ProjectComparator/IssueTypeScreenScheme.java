package core.ProjectComparator;

import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-09-11.
 */
public class IssueTypeScreenScheme {
    String name;
    String desc;
    String defaultScheme;
    List<MappingScreenSchemesIssueTypes> mappingScreenSchemesIssueTypes = new ArrayList<>();


    public void setDefaultScheme(String defaultScheme) {
        this.defaultScheme = defaultScheme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static IssueTypeScreenScheme getWorkFlowSchemeFromXMLFILE(String xmlFile, String issueTypeScreenSchemeName) {
        IssueTypeScreenScheme issueTypeScreenScheme = new IssueTypeScreenScheme();
        Document xmlDocument = NodeHelper.getDocument(xmlFile);
        NodeList workflowSchemeList = xmlDocument.getElementsByTagName("issueTypeScreenScheme");

        if (workflowSchemeList.getLength() == 0) {
            Assert.fail("Brak Issue Type schemes w podanym pliku");
        }
        //Wyciągamy liste dla źródła
        for (int x = 0; x < workflowSchemeList.getLength(); x++) {
            Node innerNode = workflowSchemeList.item(x);
            //weryfikuje czy element jest tym który mnie interesuje tj. tym z wartościami
            if (innerNode.getNodeType() == Node.ELEMENT_NODE && innerNode.getChildNodes().getLength() > 1) {
                NodeList childNodesList = innerNode.getChildNodes();
                if (NodeHelper.checkNodeInListByName(childNodesList, issueTypeScreenSchemeName)) {
                    issueTypeScreenScheme.setName(NodeHelper.getTextFromNodeInList(childNodesList, "name"));
                    issueTypeScreenScheme.setDesc(NodeHelper.getTextFromNodeInList(childNodesList, "description"));
                    issueTypeScreenScheme.setDefaultScheme(NodeHelper.getTextFromNodeInList(childNodesList, "defaultScheme"));
                    issueTypeScreenScheme.setMappingScreenSchemesIssueTypes(childNodesList);

                    System.out.println(issueTypeScreenScheme);
                } else {
                    Assert.fail("Nie znaleziono Issue type Scheme o podanej nazwie :");
                }
            }
        }
        return issueTypeScreenScheme;
    }


    private void setMappingScreenSchemesIssueTypes(NodeList nodeList) {

        Element element = (Element) nodeList;
        NodeList screenSchemesIssueTypes = element.getElementsByTagName("mappingScreenSchemesIssueTypes");


        for (int x = 0; x < screenSchemesIssueTypes.getLength(); x++) {
            NodeList childNodes = screenSchemesIssueTypes.item(x).getChildNodes();
            MappingScreenSchemesIssueTypes mappingIssueTypes = new MappingScreenSchemesIssueTypes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);

                if (node.getNodeName().equals("issueType")) {
                    mappingIssueTypes.setIssueType(node.getTextContent());

                }
                if (node.getNodeName().equals("screenScheme")) {
                    mappingIssueTypes.setScreenScheme(node.getTextContent());
                }
            }
            mappingScreenSchemesIssueTypes.add(mappingIssueTypes);
        }
    }

    @Override
    public String toString() {
        return "IssueTypeScreenScheme{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", defaultScheme='" + defaultScheme + '\'' +
                ", mappingScreenSchemesIssueTypes=" + mappingScreenSchemesIssueTypes +
                '}';
    }
}


