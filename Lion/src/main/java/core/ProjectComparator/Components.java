package core.ProjectComparator;

import core.ProjectComparator.helpers.Component;
import core.ProjectComparator.helpers.IndividualFieldConfig;
import core.ProjectComparator.helpers.NodeHelper;
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
public class Components {
    public String test;
    static List<Component> componentList = new ArrayList<>();

    public static Components getComponents(File xmlFile, String nodeName) {
        Components components = new Components();

        NodeList componentNodeList = NodeHelper.getNodeFromXMLFileByTagName(xmlFile, "component");
        System.out.println("componentNodeList" + componentNodeList.getLength());
        //Wyciągamy liste dla źródła
        for (int x = 0; x < componentNodeList.getLength(); x++) {
            Node innerNode = componentNodeList.item(x);
            NodeList childNodesList = innerNode.getChildNodes();
            Component component = new Component();
            component.setName(NodeHelper.getTextFromNodeInList(childNodesList, "name"));
            component.setDesc(NodeHelper.getTextFromNodeInList(childNodesList, "description"));
            component.setDefaultAssignee(NodeHelper.getTextFromNodeInList(childNodesList, "defaultAsignee"));
            component.setComponentLead(NodeHelper.getTextFromNodeInList(childNodesList, "componentLead"));
            componentList.add(component);
        }
        System.out.println(components);
        return components;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Components that = (Components) o;

        return test != null ? test.equals(that.test) : that.test == null;
    }

    @Override
    public int hashCode() {
        return test != null ? test.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Components{" +
                "componentList=" + componentList +
                '}';
    }


}


