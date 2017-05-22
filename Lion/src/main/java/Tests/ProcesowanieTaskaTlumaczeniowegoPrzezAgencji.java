package Tests;

import Elements.KanbanHeader;
import Elements.Task.TaskButton;
import Elements.Task.TaskStatus;
import PageObjects.TaskPage.AssignePage;
import PageObjects.TaskPage.PmAgencyTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.KanbanPage;
import PageObjects.main.LoginPage;
import Tools.BrowserType;
import Tools.EnviromentSettings;
import Tools.Task;
import Tools.TestEnviroments;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class ProcesowanieTaskaTlumaczeniowegoPrzezAgencji {

    WebDriver driver;
    String pmAgencylogin = "001-svpmgr_66551";
    String pmAgencyhaslo = "lion";
    String translatorLogin;
    String taskURL;// ="https://aps.staging.lionbridge.com/browse/GO-36875";


    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }


    @Test(priority = 1)
    public void obslugaPrzezPmAgencyjnego() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        KanbanPage kanbanPage = loginPage.logInToJiraAndGoToKanban(pmAgencylogin, pmAgencyhaslo);
        kanbanPage.chooseTask(KanbanHeader.NEW, 1);
        PmAgencyTaskPage pmAgencyTaskPage = new PmAgencyTaskPage(driver);
        pmAgencyTaskPage.clickOnButton(TaskButton.ACCEPT);

        System.out.println(pmAgencyTaskPage.getStatus() + "  " + pmAgencyTaskPage.getUrl());
        taskURL = pmAgencyTaskPage.getUrl();
        Assert.assertEquals(pmAgencyTaskPage.getStatus(), TaskStatus.ACCEPTED);

        pmAgencyTaskPage.clickOnButton(TaskButton.TRANSLATION_TASK_REF);

        TaskPage task = new TaskPage(driver);
        task.clickOnButton(TaskButton.ASSIGN_TO_TRANSLATOR);

        AssignePage assignePage = new AssignePage(driver);
        //przekazuje login do kolejnego testu
        translatorLogin = assignePage.chooseAssigne();
        Assert.assertEquals(task.getStatus(), TaskStatus.ASSIGNED_TO_TRANSLATOR);
    }

    @Test(priority = 5)
    public void obslugaPrzezTranslatora() {
        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        DashboardPage dashboardPage = loginAsTranslator.logInToJira(translatorLogin, "lion");
        TaskPage task = dashboardPage.goToTask(taskURL);
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
