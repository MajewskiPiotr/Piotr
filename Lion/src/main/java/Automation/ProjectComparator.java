package Automation;
/**
 * Created by Piotr Majewski on 2017-06-27.
 */


import core.ProjectComparator.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


//Test porównujący dwa pliki z ustawieniami projektu
public class ProjectComparator {
    File sourceFile;
    File destinyFile;

    @BeforeClass
    public void setUp() {
        sourceFile = new File("C:\\Users\\Piotr Majewski\\Downloads\\dump_1.xml");
        destinyFile = new File("C:\\Users\\Piotr Majewski\\Downloads\\dump_1 - Kopia.xml");
    }


    //@Test
    //Test weryfikuje zgodność IssueType Scheme pod względem zawartość
    //porównuje wszystkie jego parametry
    //!!!! po zwróceniu False zweryfikowac czy problemem nie jest kolejność IssuType-ów
    public void checkRequestType() throws ParserConfigurationException, IOException, SAXException {

        IssueTypeScheme source = IssueTypeScheme.
                getIssueTypeSchemFromXMLFILE(sourceFile, "DLSD: JIRA Service Desk Issue Type Scheme");
        IssueTypeScheme destiny = IssueTypeScheme.
                getIssueTypeSchemFromXMLFILE(destinyFile, "DLSD: JIRA Service Desk Issue Type Scheme");
        Assert.assertEquals(source, destiny, "schematy nie równe");

    }

    //@Test
    public void checkWorkflowsScheme() throws IOException, SAXException, ParserConfigurationException {
        WorkFlowScheme source = WorkFlowScheme.
                getWorkFlowSchemeFromXMLFILE(sourceFile, "JIRA Service Desk IT Support Workflow Scheme generated for Project DLSD");
        WorkFlowScheme destiny = WorkFlowScheme.
                getWorkFlowSchemeFromXMLFILE(destinyFile, "JIRA Service Desk IT Support Workflow Scheme generated for Project DLSD");
        Assert.assertEquals(source, destiny, "workflowy nie równe");
    }

   // @Test
    public void checkScreensScheme() {
        IssueTypeScreenScheme source = IssueTypeScreenScheme.
                getIssueTypeScreenScheme(sourceFile, "DLSD: JIRA Service Desk Issue Type Screen Scheme");

        IssueTypeScreenScheme destiny = IssueTypeScreenScheme.
                getIssueTypeScreenScheme(destinyFile, "DLSD: JIRA Service Desk Issue Type Screen Scheme");

        Assert.assertEquals(source, destiny, "Screen schemy nie równe");
    }

   // @Test
    public void checkFieldConfiguration() {
        FieldConfiguration source = FieldConfiguration.
                getFieldConfiguration(sourceFile, "JIRA Service Desk Field Configuration for Project DLSD");

        FieldConfiguration destiny = FieldConfiguration.
                getFieldConfiguration(sourceFile, "JIRA Service Desk Field Configuration for Project DLSD");

        Assert.assertEquals(source, destiny, "Field Configuration");
    }

   // @Test
    public void checkComponents(){
        Components source = Components.getComponents(sourceFile,"component" );
        Components destiny = Components.getComponents(destinyFile,"component" );

        Assert.assertEquals(source, destiny);

    }

}
