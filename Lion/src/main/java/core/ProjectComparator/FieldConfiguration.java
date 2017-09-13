package core.ProjectComparator;

import core.ProjectComparator.helpers.IndividualFieldConfig;
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
 * Klasa reprentuje Noda IssueTypeScreenScheme
 */
public class FieldConfiguration {
    String name;
    String desc;
    //lista nodów mappingScreenSchemesIssueTypes
    List<IndividualFieldConfig> IndividualFieldConfigList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static FieldConfiguration getFieldConfiguration(File xmlFile, String issueTypeScreenSchemeName) {
        FieldConfiguration fieldConfiguration = new FieldConfiguration();

        NodeList workflowSchemeList = NodeHelper.getNodeFromXMLFileByTagName(xmlFile,"fieldConfiguration");
        //Wyciągamy liste dla źródła
        for (int x = 0; x < workflowSchemeList.getLength(); x++) {
            Node innerNode = workflowSchemeList.item(x);
            //weryfikuje czy element jest tym który mnie interesuje tj. tym z wartościami
            if (innerNode.getNodeType() == Node.ELEMENT_NODE && innerNode.getChildNodes().getLength() > 1) {
                NodeList childNodesList = innerNode.getChildNodes();
                if (NodeHelper.checkNodeInListByName(childNodesList, issueTypeScreenSchemeName)) {
                    fieldConfiguration.setName(NodeHelper.getTextFromNodeInList(childNodesList, "name"));
                    fieldConfiguration.setDesc(NodeHelper.getTextFromNodeInList(childNodesList, "description"));
                    fieldConfiguration.setIndividualFieldConfigList(childNodesList);
                    System.out.println(fieldConfiguration);
                } else {
                    Assert.fail("Nie znaleziono Issue type Scheme o podanej nazwie :");
                }
            }
        }
        return fieldConfiguration;
    }


    private void setIndividualFieldConfigList(NodeList nodeList) {

        Element element = (Element) nodeList;
        NodeList screenSchemesIssueTypes = element.getElementsByTagName("individualFieldConfig");


        for (int x = 0; x < screenSchemesIssueTypes.getLength(); x++) {
            NodeList childNodes = screenSchemesIssueTypes.item(x).getChildNodes();
            IndividualFieldConfig individualFieldConfig = new IndividualFieldConfig();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);

                if (node.getNodeName().equals("fieldName")) {
                    individualFieldConfig.setFieldName(node.getTextContent());

                }
                if (node.getNodeName().equals("hidden")) {
                    boolean hidden = false;
                    if (node.getTextContent().equals("true")) {
                        hidden = true;
                    }
                    individualFieldConfig.setHidden(hidden);
                }
                if (node.getNodeName().equals("required")) {
                    boolean hidden = false;
                    if (node.getTextContent().equals("true")) {
                        hidden = true;
                    }
                    individualFieldConfig.setRequired(hidden);
                }
                if (node.getNodeName().equals("renderer")) {
                    individualFieldConfig.setRenderer(node.getTextContent());
                }


            }
            IndividualFieldConfigList.add(individualFieldConfig);
        }
    }

    @Override
    public String toString() {
        return "FieldConfiguration{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", IndividualFieldConfigList=" + IndividualFieldConfigList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldConfiguration that = (FieldConfiguration) o;

        if (!name.equals(that.name)) return false;
        if (!desc.equals(that.desc)) return false;
        return IndividualFieldConfigList.equals(that.IndividualFieldConfigList);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + IndividualFieldConfigList.hashCode();
        return result;
    }
}


