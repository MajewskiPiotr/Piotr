package Tests;
/**
 * Created by Piotr Majewski on 2017-06-27.
 */


import core.ProjectComparator.IssueTypeScheme;
import core.ProjectComparator.IssueTypeScreenScheme;
import core.ProjectComparator.MappingScreenSchemesIssueTypes;
import core.ProjectComparator.WorkFlowScheme;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectComparator {


    @Test
    //Test weryfikuje zgodność IssueType Scheme pod względem zawartość
    //porównuje wszystkie jego parametry
    //!!!! po zwróceniu False zweryfikowac czy problemem nie jest kolejność IssuType-ów
    public void checkRequestType() throws ParserConfigurationException, IOException, SAXException {

        IssueTypeScheme source = IssueTypeScheme.
                getIssueTypeSchemFromXMLFILE("C:\\Users\\Piotr Majewski\\Downloads\\dump_1.xml", "DLSD: JIRA Service Desk Issue Type Scheme");
        IssueTypeScheme destiny = IssueTypeScheme.
                getIssueTypeSchemFromXMLFILE("C:\\Users\\Piotr Majewski\\Downloads\\dump_1 - Kopia.xml", "DLSD: JIRA Service Desk Issue Type Scheme");
        Assert.assertEquals(source, destiny, "schematy nie równe");

    }

    @Test
    public void checkWorkflowsScheme() throws IOException, SAXException, ParserConfigurationException {
        WorkFlowScheme source = WorkFlowScheme.
                getWorkFlowSchemeFromXMLFILE("C:\\Users\\Piotr Majewski\\Downloads\\dump_1.xml", "JIRA Service Desk IT Support Workflow Scheme generated for Project DLSD");
        WorkFlowScheme destiny = WorkFlowScheme.
                getWorkFlowSchemeFromXMLFILE("C:\\Users\\Piotr Majewski\\Downloads\\dump_1 - Kopia.xml", "JIRA Service Desk IT Support Workflow Scheme generated for Project DLSD");
        Assert.assertEquals(source, destiny, "workflowy nie równe");
    }

    @Test
    public void checkScreensScheme() {
        IssueTypeScreenScheme source = IssueTypeScreenScheme.
                getWorkFlowSchemeFromXMLFILE("C:\\Users\\Piotr Majewski\\Downloads\\dump_1.xml", "DLSD: JIRA Service Desk Issue Type Screen Scheme");

        IssueTypeScreenScheme destiny = IssueTypeScreenScheme.
                getWorkFlowSchemeFromXMLFILE("C:\\Users\\Piotr Majewski\\Downloads\\dump_1.xml", "DLSD: JIRA Service Desk Issue Type Screen Scheme");

        Assert.assertEquals(source, destiny, "Screen schemy nie równe");
    }


}
