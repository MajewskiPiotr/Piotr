package Tests;

import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskLink;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.Elements.Task.TaskTab;
import PageObjects.TaskPage.JobTaskPage;
import PageObjects.TaskPage.NegotiationTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.LoginPage;
import PageObjects.main.PackagePluginSettings;
import Tests.BaseTest.BaseTestClass;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import core.Tools.HelpersClass.Assigments;
import core.Tools.JsScript;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-01.
 */
public class MasoweAkceptowaniePrzezEdytora extends BaseTestClass {



    @Test(priority = 211)
    public void wygenerujJobIpaczke() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DashboardPage dashboardPage = loginPage.loginAsAdmin();
        //ustawiam link do Joba
        data.setJobTask(JsScript.createTranslationJobWithManyTasks(driver));
        PackagePluginSettings packagePluginSettings = dashboardPage.goToPackegePluginSettings();
        packagePluginSettings.changeSelectOperationOnPack();
        packagePluginSettings.clickOnButton(TaskButton.EXECUTE);
        Assert.assertTrue(packagePluginSettings.isPackageResult(), "Paczka nie została wygenerowana !");
    }

    //test pomocniczny, potrzebny aby dotrzeć do linku dla Paczki
    //link z paczką przekazywany do dalszych testów
    @Test(priority = 212)
    public void weryfikacjaNegocjacjiDlaPaczki() {
        LoginPage logAsAdmin = new LoginPage(driver);
        logAsAdmin.open();
        DashboardPage dashboardPage = logAsAdmin.loginAsAdmin();
        JobTaskPage taskJobPage = dashboardPage.goToJobTask(data.getJobTask());
        TranslationTabPage translationTab = (TranslationTabPage) taskJobPage.goToTab(TaskTab.TRANSLATION_TASKS);
        TaskPage taskPage = translationTab.goToFirstTask();
        TaskPage packageTaskPage = (TaskPage) taskPage.clickInLink(TaskLink.PACKAGE_REFERENCE);
        data.setPackageTask(packageTaskPage.getUrl());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AssigmentsTabPage assigmentTab = (AssigmentsTabPage) packageTaskPage.goToTab(TaskTab.ASSIGMENTS);
        data.setListOfAssigments(assigmentTab.getNegotiations());
    }

    @Test(priority = 213)
    public void akceptacjaIObslugaPaczkiPrzezTranslatora() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DashboardPage dashboardPage = loginPage.loginAsAdmin();
        JsScript.switchUserByLogin(driver, data.getListOfAssigments().get(0).getTranslator());
        NegotiationTaskPage negotiationTaskPage = dashboardPage.goToNegotiationTask(data.getListOfAssigments().get(0).getKey());
        negotiationTaskPage.clickOnButton(TaskButton.ACCEPT);
        negotiationTaskPage.clickOnButton(TaskButton.TRANSLATION_TASK_REF);
        TaskPage translatortask = new TaskPage(driver);
        translatortask.clickOnButton(TaskButton.IN_PROGRESS);
        translatortask.clickOnButton(TaskButton.SELF_QA);
        translatortask.clickOnButton(TaskButton.COMPLETED_TRANSLATOR);
        data.setTranslationTask(translatortask.getUrl());
        //dojdzie PopUp minimaizingRisk
    }

    @Test(priority = 214)
    public void weryfikacjaWygenerowaniaTaskowEdytorskich() {
        System.out.println("Translation Task :" + data.getTranslationTask());
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.loginAsAdmin();
        TaskPage taskPage = dashboardPage.goToTask(data.getTranslationTask());
        AssigmentsTabPage assigmentTab = (AssigmentsTabPage) taskPage.goToTab(TaskTab.ASSIGMENTS);
        Assert.assertTrue(assigmentTab.checkEditorWorkIsPresent(), "nie wygenerowaly sie zadania edytorskie");
        data.setListOfEditors(assigmentTab.getNewEditorWork());
    }

    @Test(priority = 215)
    public void obslugaTaskowEdytorskich() {
        Assigments editor = data.getListOfEditors().get(0);
        LoginPage loginAsEditor = new LoginPage(driver);
        loginAsEditor.open();
        DashboardPage dashboardPage = loginAsEditor.loginAsAdmin();
        //zmiana użytkownika na edytora
        JsScript.switchUserByLogin(driver, editor.getTranslator());
        System.out.println("zalogowano sie jako: " + editor.getTranslator());
        NegotiationTaskPage negotiationTask = dashboardPage.goToNegotiationTask(editor.getKey());
        negotiationTask.clickOnButton(TaskButton.ACCEPT);
        Assert.assertEquals(negotiationTask.getStatus(), TaskStatus.ACCEPTED);
        TaskPage translationTask = (TaskPage) negotiationTask.clickInLink(TaskLink.TRANSLATION_TASK_REF);
        translationTask.clickOnButton(TaskButton.ASSIGN_TO_EDITOR);
        negotiationTask.goToTab(TaskTab.TRANSLATION_TASKS);
        TranslationTabPage translationTabPage = new TranslationTabPage(driver);
        Assert.assertTrue(translationTabPage.countTranslatorTask() > 0, "brak translation Tasks");
        Assert.assertTrue(translationTabPage.otworzWszystkieTranslationTask(), "Timeout przy próbie skompletowania tasków");
    }



}
