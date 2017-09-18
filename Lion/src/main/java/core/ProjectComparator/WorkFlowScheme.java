package core.ProjectComparator;

import core.ProjectComparator.helpers.MappingIssueTypes;
import core.ProjectComparator.helpers.NodeHelper;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-09-11.
 *  * Klasa reprentuje Noda WorkFlowScheme

 */
public class WorkFlowScheme {
    String name;
    String desc;
    //Lista nodów mappingIssueTypes
    List<MappingIssueTypes> listOfMappingIssueTypes = new ArrayList<>();

    private void setMappingIssueTypes(NodeList nodeList) {

        Element element = (Element) nodeList;
        NodeList mappingIssueTypesList = element.getElementsByTagName("mappingIssueTypes");


        for (int x = 0; x < mappingIssueTypesList.getLength(); x++) {
            NodeList childNodes = mappingIssueTypesList.item(x).getChildNodes();
            MappingIssueTypes mappingIssueTypes = new MappingIssueTypes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);

                if (node.getNodeName().equals("issueType")) {
                    mappingIssueTypes.setIssueType(node.getTextContent());

                }
                if (node.getNodeName().equals("workflow")) {
                    mappingIssueTypes.setWorkflow(node.getTextContent());
                }
            }
            listOfMappingIssueTypes.add(mappingIssueTypes);
        }
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

    public static WorkFlowScheme getWorkFlowSchemeFromXMLFILE(File xmlFile, String IssueTypeSchemeName) {
        WorkFlowScheme workFlowScheme = new WorkFlowScheme();
        Document xmlDocument = NodeHelper.getDocument(xmlFile);
        NodeList workflowSchemeList = xmlDocument.getElementsByTagName("workflowScheme");

        if (workflowSchemeList.getLength() == 0) {
            Assert.fail("Brak WorkFlow Scheme w podanym pliku");
        }
        //Wyciągamy liste dla źródła
        for (int x = 0; x < workflowSchemeList.getLength(); x++) {
            Node innerNode = workflowSchemeList.item(x);
            //weryfikuje czy element jest tym który mnie interesuje tj. tym z wartościami
            if (innerNode.getNodeType() == Node.ELEMENT_NODE && innerNode.getChildNodes().getLength() > 1) {
                NodeList childNodesList = innerNode.getChildNodes();
                if (NodeHelper.checkNodeInListByName(childNodesList, "JIRA Service Desk IT Support Workflow Scheme generated for Project DLSD")) {
                    workFlowScheme.setName(NodeHelper.getTextFromNodeInList(childNodesList, "name"));
                    workFlowScheme.setDesc(NodeHelper.getTextFromNodeInList(childNodesList, "description"));
                    workFlowScheme.setMappingIssueTypes(childNodesList);
                    System.out.println(workFlowScheme);
                } else {
                    Assert.fail("Nie znaleziono WorkFlow Scheme o podanej nazwie :");
                }
            }
        }
        return workFlowScheme;
    }

    @Override
    public String toString() {
        return "WorkFlowScheme{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", listOfMappingIssueTypes=" + listOfMappingIssueTypes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkFlowScheme that = (WorkFlowScheme) o;

        if (!name.equals(that.name)) return false;
        if (!desc.equals(that.desc)) return false;
        return listOfMappingIssueTypes.containsAll(that.listOfMappingIssueTypes);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + listOfMappingIssueTypes.hashCode();
        return result;
    }
}


