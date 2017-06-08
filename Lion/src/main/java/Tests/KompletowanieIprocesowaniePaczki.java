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
import core.Tools.JsScript;
import core.Tools.LionAssert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-22.
 */
public class KompletowanieIprocesowaniePaczki extends BaseTestClass {




    @Test(priority = 10)
    public void utworzeniePaczki() {
        LoginPage logAsAdmin = new LoginPage(driver);
        logAsAdmin.open();
        DashboardPage dashboardPage = logAsAdmin.loginAsAdmin();
        //numer wygenerowanego Joba
        String taskJob = JsScript.createTranslationJob(driver);
        data.setJobTask(taskJob);
        PackagePluginSettings packagePluginSettings = dashboardPage.goToPackegePluginSettings();
        packagePluginSettings.changeSelectOperationOnPack();
        packagePluginSettings.clickOnButton(TaskButton.EXECUTE);
        Assert.assertTrue(packagePluginSettings.isPackageResult(), "Paczka nie została wygenerowana !");
    }

    @Test(priority = 11)
    public void weryfikacjaNegocjacjiDlaPaczki() {
        LoginPage logAsAdmin = new LoginPage(driver);
        logAsAdmin.open();
        DashboardPage dashboardPage = logAsAdmin.loginAsAdmin();
        JobTaskPage taskJobPage = dashboardPage.goToJobTask(data.getJobTask());
        TranslationTabPage translationTab = (TranslationTabPage) taskJobPage.goToTab(TaskTab.TRANSLATION_TASKS);
        TaskPage taskPage = translationTab.goToFirstTask();
        TaskPage packageTaskPage = (TaskPage) taskPage.clickInLink(TaskLink.PACKAGE_REFERENCE);
        data.setPackageTask(packageTaskPage.getUrl());
        //czekam na załadowania sie wszystkich podtasków
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AssigmentsTabPage packagePageAssigmentTab = (AssigmentsTabPage) packageTaskPage.goToTab(TaskTab.ASSIGMENTS);
        Assert.assertTrue(packagePageAssigmentTab.getNegociationCount() > 0);
        //przekazywanie danych do kolejnych testów
        data.setListOfAssigments(packagePageAssigmentTab.getNegotiations());

        System.out.println("package task: " + data.getPackageTask());
    }

    @Test(priority = 12)
    public void akceptacjaPaczkiPrzezTranslatora() {
        System.out.println("user : "+data.getListOfAssigments().get(0).getTranslator());
        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        DashboardPage dashboardPage = loginAsTranslator.loginAsAdmin();
        JsScript.switchUserByLogin(driver,data.getListOfAssigments().get(0).getTranslator() );
        NegotiationTaskPage negociationTask = dashboardPage.goToNegotiationTask(data.getListOfAssigments().get(0).getKey());
        System.out.println("Task negocjacyjny Translatora :" + negociationTask.getUrl());
        //Akceptacja taska
        negociationTask.clickOnButton(TaskButton.ACCEPT);
        LionAssert.assertStatus(negociationTask.getStatus(), TaskStatus.ACCEPTED, TaskStatus.AUTOMATICALLY_ACCEPTED,"nie poprawny sta Negocjacji po zaakceptowaniu");

    }


    @Test(priority = 13)
    public void weryfikacjaZmianyStanowTaskow() {
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.loginAsAdmin();
        TaskPage packageTaskPage = dashboardPage.goToTask(data.getPackageTask());
        System.out.println(packageTaskPage.getUrl());
        AssigmentsTabPage assigmentTab = (AssigmentsTabPage) packageTaskPage.goToTab(TaskTab.ASSIGMENTS);
        //weryfikacja zmiany stanow negocjacji
        Assert.assertTrue(assigmentTab.checkIsStatusChange());


    }


}
