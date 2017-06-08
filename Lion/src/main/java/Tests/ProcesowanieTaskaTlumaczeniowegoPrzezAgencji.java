package Tests;

import PageObjects.Elements.KanbanHeader;
import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.TaskPage.AssignePage;
import PageObjects.TaskPage.PmAgencyTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.KanbanPage;
import PageObjects.main.LoginPage;
import Tests.BaseTest.BaseTestClass;
import core.Tools.JsScript;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
@Listeners(core.Listeners.Listeners.class)
public class ProcesowanieTaskaTlumaczeniowegoPrzezAgencji extends BaseTestClass {

    String pmAgencylogin = "001-svpmgr_66551";


    @Test(priority = 21)
    public void obslugaPrzezPmAgencyjnego() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DashboardPage dashboardPage = loginPage.loginAsAdmin();
        JsScript.switchUserByLogin(driver, pmAgencylogin);
        KanbanPage kanbanPage = dashboardPage.goToKanban();

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
        DashboardPage dashboardPage = loginAsTranslator.loginAsAdmin();
        JsScript.switchUserByLogin(driver, data.getTranslator());
        TaskPage task = dashboardPage.goToTask(data.getNegociationTask());
        task.clickOnButton(TaskButton.IN_PROGRESS);
        Assert.assertEquals(task.getStatus(), TaskStatus.IN_PROGRESS);

        task.clickOnButton(TaskButton.SELF_QA);
        Assert.assertEquals(task.getStatus(), TaskStatus.SELF_QA);
        task.clickOnButton(TaskButton.COMPLETED_TRANSLATOR);
        List<String> lista = new ArrayList<String>();
        lista.add(TaskStatus.COMPLETED);
        lista.add(TaskStatus.READY_TO_VERIFY);
        Assert.assertTrue(lista.contains(task.getStatus()), "Status taska nieprawidlowy !");
        //TODO nie zawsze poprawnie zczytuje status
    }


}
