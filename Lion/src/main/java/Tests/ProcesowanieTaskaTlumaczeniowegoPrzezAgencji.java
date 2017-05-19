package Tests;

import Elements.KanbanHeader;
import Elements.Task.TaskButton;
import Elements.Task.TaskStatus;
import PageObjects.AssignePage;
import PageObjects.TaskPage.PmAgencyTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.main.KanbanPage;
import PageObjects.main.LoginPage;
import Tools.BrowserType;
import Tools.EnviromentSettings;
import Tools.TestEnviroments;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class ProcesowanieTaskaTlumaczeniowegoPrzezAgencji {

    WebDriver driver;
    String pmAgencylogin = "001-svpmgr_66551";
    String pmAgencyhaslo = "lion";

    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }


    @Test
    public void obslugaPrzezPmAgencyjnego() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        KanbanPage kanbanPage = loginPage.logInToJiraAndGoToKanban(pmAgencylogin, pmAgencyhaslo);
        kanbanPage.chooseTask(KanbanHeader.NEW, 1);
        PmAgencyTaskPage pmAgencyTaskPage = new PmAgencyTaskPage(driver);
        pmAgencyTaskPage.clickOnButton(TaskButton.ACCEPT);

        System.out.println(pmAgencyTaskPage.getStatus() + "  " + pmAgencyTaskPage.getUrl());
        Assert.assertEquals(pmAgencyTaskPage.getStatus(), TaskStatus.ACCEPTED);

        pmAgencyTaskPage.clickOnButton(TaskButton.TRANSLATION_TASK_REF);

        TaskPage task = new TaskPage(driver);
        task.clickOnButton(TaskButton.ASSIGN_TO_TRANSLATOR);

        AssignePage assignePage = new AssignePage(driver);
        assignePage.chooseAssigne();
    }


    @AfterMethod
    public void tearDown() {
        //driver.close();
    }
}
