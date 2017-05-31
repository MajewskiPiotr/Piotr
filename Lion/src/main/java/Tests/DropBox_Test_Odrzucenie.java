package Tests;

import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.Elements.Task.TaskTab;
import PageObjects.TaskPage.NegotiationTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.LoginPage;
import Tests.BaseTest.GenerowanieJoba;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-30.
 */
public class DropBox_Test_Odrzucenie extends BaseTestClass {

    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

    @Test(priority = 80)
    public void wygenerujTaskJobForDropbox() {
        GenerowanieJoba.wygenerujJobaDlaDropboxa(driver);
    }

    @Test(priority = 81)
    public void weryfikacjaUtworzonegoJoba() {
       GenerowanieJoba.zweryfikujJoba(driver);
    }

    @Test(priority = 82)
    public void odrzuceniePrzezTranslatoraNegocjacji() {
        Assert.assertFalse(data.getListOfAssigments() == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");
        System.out.println("Wygenerowane negocjacje :" + data.getListOfAssigments().toString());

        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        DashboardPage kanbanPage = loginAsTranslator.logInToJira(data.getListOfAssigments().get(0).getTranslator(), "lion");
        NegotiationTaskPage userTask = kanbanPage.goToNegotiationTask(data.getListOfAssigments().get(0).getKey());
        userTask.clickOnButton(TaskButton.REJECT);

        Assert.assertEquals(userTask.getStatus(), TaskStatus.REJECTED);
        System.out.println("Task badany : " + userTask.getUrl());
    }

    @Test(priority = 83)
    public void weryfikacjaStanowNegocjacji() {
        Assert.assertFalse(data.getListOfAssigments() == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");

        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        TaskPage jobTask = dashboardPage.goToTask(data.getTranslationTask());
        AssigmentsTabPage pageObject = (AssigmentsTabPage) jobTask.goToTab(TaskTab.ASSIGMENTS);

        Assert.assertTrue(pageObject.checkIsTaskRejected(), "Task nie został odrzucony");
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

}
