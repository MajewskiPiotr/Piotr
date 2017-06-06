package Tests;

import PageObjects.TaskPage.JobTaskPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.LoginPage;
import Tests.BaseTest.BaseTestClass;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import core.Tools.JsScript;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-06.
 */
public class CommentUpdateTest extends BaseTestClass {


    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE2);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

    @Test(priority = 211)
    public void wprowadzKomentarzDoJobTask() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DashboardPage dashboardPage = loginPage.loginAsAdmin();
        data.setJobTask(JsScript.createTranslationJob(driver));
       JobTaskPage jobTask = dashboardPage.goToJobTask(data.getJobTask());

    }
}
