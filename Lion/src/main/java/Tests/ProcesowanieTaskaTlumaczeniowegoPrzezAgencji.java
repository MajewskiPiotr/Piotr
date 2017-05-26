package Tests;

import Web.PageObjects.Elements.KanbanHeader;
import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskStatus;
import Web.PageObjects.TaskPage.AssignePage;
import Web.PageObjects.TaskPage.PmAgencyTaskPage;
import Web.PageObjects.TaskPage.TaskPage;
import Web.PageObjects.main.DashboardPage;
import Web.PageObjects.main.KanbanPage;
import Web.PageObjects.main.LoginPage;
import Tools.BrowserType;
import Tools.EnviromentSettings;
import Tools.TestEnviroments;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class ProcesowanieTaskaTlumaczeniowegoPrzezAgencji extends BaseTestClass {

    String pmAgencylogin = "001-svpmgr_66551";
    String pmAgencyhaslo = "lion";


    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }


    @Test(priority = 21)
    public void obslugaPrzezPmAgencyjnego() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        KanbanPage kanbanPage = loginPage.logInToJiraAndGoToKanban(pmAgencylogin, pmAgencyhaslo);
        kanbanPage.chooseTask(KanbanHeader.NEW, 1);
        PmAgencyTaskPage pmAgencyTaskPage = new PmAgencyTaskPage(driver);
        System.out.println(" link do negocjacji : " + pmAgencyTaskPage.getUrl());

        pmAgencyTaskPage.clickOnButton(TaskButton.ACCEPT);

        Assert.assertEquals(pmAgencyTaskPage.getStatus(), TaskStatus.ACCEPTED);

        pmAgencyTaskPage.clickOnButton(TaskButton.TRANSLATION_TASK_REF);

        TaskPage task = new TaskPage(driver);
        data.setNegociationTask(task.getUrl());

        task.clickOnButton(TaskButton.ASSIGN_TO_TRANSLATOR);

        AssignePage assignePage = new AssignePage(driver);
        //przekazuje login do kolejnego testu
        data.setTranslator(assignePage.chooseAssigne());
        Assert.assertEquals(task.getStatus(), TaskStatus.ASSIGNED_TO_TRANSLATOR);
    }

    @Test(priority = 22)
    public void obslugaPrzezTranslatora() {
        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        DashboardPage dashboardPage = loginAsTranslator.logInToJira(data.getTranslator(), "lion");
        TaskPage task = dashboardPage.goToTask(data.getNegociationTask());
        task.clickOnButton(TaskButton.IN_PROGRESS);
        Assert.assertEquals(task.getStatus(), TaskStatus.IN_PROGRESS);

        task.clickOnButton(TaskButton.SELF_QA);
        Assert.assertEquals(task.getStatus(), TaskStatus.SELF_QA);
        task.clickOnButton(TaskButton.COMPLETED_TRANSLATOR);
        List<String> lista = new ArrayList<String>(); lista.add(TaskStatus.COMPLETED); lista.add(TaskStatus.READY_TO_VERIFY);
        Assert.assertTrue(lista.contains(task.getStatus()), "Status taska nieprawidlowy !");
    }


    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}