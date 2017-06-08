package Tests;

import PageObjects.Elements.KanbanHeader;
import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.Elements.Task.TaskTab;
import PageObjects.TaskPage.PmAgencyTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.KanbanPage;
import PageObjects.main.LoginPage;
import Tests.BaseTest.BaseTestClass;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import core.Tools.JsScript;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class ProcesowanieTaskaTlumaczeniowegoPrzezAgencji_Odrzucenie extends BaseTestClass {

    String pmAgencylogin = "001-svpmgr_66551";
    String pmAgencyhaslo = "lion";




    @Test(priority = 100)
    public void odrzucenieNegocjacjiPrzezPmAgencyjnego() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DashboardPage dashboardPage = loginPage.loginAsAdmin();
        JsScript.switchUserByLogin(driver,pmAgencylogin);
        KanbanPage kanbanPage = dashboardPage.goToKanban();
        kanbanPage.chooseTask(KanbanHeader.NEW, 1);
        PmAgencyTaskPage pmAgencyTaskPage = new PmAgencyTaskPage(driver);
        System.out.println(" link do negocjacji : " + pmAgencyTaskPage.getUrl());
        data.setNegociationTask(pmAgencyTaskPage.getUrl());
        pmAgencyTaskPage.clickOnButton(TaskButton.REJECT);
        Assert.assertEquals(pmAgencyTaskPage.getStatus(), TaskStatus.REJECTED, "Nie udało sie odrzucić negocjacji");

        pmAgencyTaskPage.clickOnButton(TaskButton.TRANSLATION_TASK_REF);
        TaskPage task = new TaskPage(driver);
        data.setTranslationTask(task.getUrl());
    }

    @Test(priority = 101)
    public void weryfikacjaZmianyStanowNegocjacji() {
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.loginAsAdmin();
        TaskPage task = dashboardPage.goToTask(data.getTranslationTask());
        AssigmentsTabPage assigmentTab = (AssigmentsTabPage) task.goToTab(TaskTab.ASSIGMENTS);
        Assert.assertTrue(assigmentTab.checkIsTaskRejected(), "problem ze stanami negocjacji po odrzuceniu negocjacji");
    }



}
