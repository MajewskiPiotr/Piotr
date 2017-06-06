package Tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.Elements.Task.TaskTab;
import PageObjects.TaskPage.NegotiationTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.LoginPage;
import Tests.BaseTest.BaseTestClass;
import Tests.BaseTest.GenerowanieJoba;
import core.Tools.JsScript;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;

/**
 * Created by Piotr Majewski on 2017-05-30.
 */
public class DropBox_Test extends BaseTestClass {

    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

    @Test(priority = 70)
    public void wygenerujTaskJobForDropbox() {
        GenerowanieJoba.wygenerujJobaDlaDropboxa(driver);
    }

    @Test(priority = 71)
    public void weryfikacjaUtworzonegoJoba() {
       GenerowanieJoba.zweryfikujJoba(driver);
    }

    @Test(priority = 72)
    public void akceptacjaPrzezTranslatoraNegocjacji() {
        Assert.assertFalse(data.getListOfAssigments() == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");
        System.out.println("Wygenerowane negocjacje :" + data.getListOfAssigments().toString());
        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        DashboardPage kanbanPage = loginAsTranslator.loginAsAdmin();
        JsScript.switchUser(driver,data.getListOfAssigments().get(0).getTranslator());
        NegotiationTaskPage userTask = kanbanPage.goToNegotiationTask(data.getListOfAssigments().get(0).getKey());
        userTask.clickOnButton(TaskButton.ACCEPT);
        Assert.assertEquals(userTask.getStatus(), TaskStatus.ACCEPTED);

        System.out.println("Task badany : " + userTask.getUrl());
    }

    @Test(priority = 73)
    public void weryfikacjaStanowNegocjacji() {
        Assert.assertFalse(data.getListOfAssigments() == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        TaskPage jobTask = dashboardPage.goToTask(data.getJobTask());
        TranslationTabPage translationTabPage = (TranslationTabPage) jobTask.goToTab(TaskTab.TRANSLATION_TASKS);
        Assert.assertTrue(translationTabPage.checkIsStatusChange());
        TaskPage taskViewPage = translationTabPage.goToFirstTask();
        AssigmentsTabPage pageObject = (AssigmentsTabPage) taskViewPage.goToTab(TaskTab.ASSIGMENTS);
        Assert.assertTrue(pageObject.checkIsStatusChange(), "Zmiana statusow Negocjacji");

        System.out.println("Translation Task : " + taskViewPage.getUrl());
        data.setZakonczono(true);
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

}
