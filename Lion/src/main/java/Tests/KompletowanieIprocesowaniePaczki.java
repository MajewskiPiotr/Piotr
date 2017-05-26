package Tests;

import Tools.BrowserType;
import Tools.EnviromentSettings;
import Tools.JsScript;
import Tools.TestEnviroments;
import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskLink;
import Web.PageObjects.Elements.Task.TaskStatus;
import Web.PageObjects.Elements.Task.TaskTab;
import Web.PageObjects.TaskPage.JobTaskPage;
import Web.PageObjects.TaskPage.NegotiationTaskPage;
import Web.PageObjects.TaskPage.TaskPage;
import Web.PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import Web.PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import Web.PageObjects.main.DashboardPage;
import Web.PageObjects.main.LoginPage;
import Web.PageObjects.main.PackagePluginSettings;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-22.
 */
public class KompletowanieIprocesowaniePaczki extends BaseTestClass {




    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

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
        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        DashboardPage dashboardPage = loginAsTranslator.logInToJira(data.getListOfAssigments().get(0).getTranslator(), "lion");
        NegotiationTaskPage negociationTask = dashboardPage.goToNegotiationTask(data.getListOfAssigments().get(0).getKey());
        System.out.println("Task negocjacyjny Translatora :" + negociationTask.getUrl());
        //Akceptacja taska
        negociationTask.clickOnButton(TaskButton.ACCEPT);
        Assert.assertEquals(negociationTask.getStatus(), TaskStatus.ACCEPTED);
    }


    @Test(priority = 13)
    public void test() {
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.loginAsAdmin();
        TaskPage packageTaskPage = dashboardPage.goToTask(data.getPackageTask());
        System.out.println(packageTaskPage.getUrl());
        AssigmentsTabPage assigmentTab = (AssigmentsTabPage) packageTaskPage.goToTab(TaskTab.ASSIGMENTS);
        //weryfikacja zmiany stanow negocjacji
        Assert.assertTrue(assigmentTab.checkIsStatusChange());


    }

    @AfterMethod
    public void tearDown() {
        driver.close();

    }

}
