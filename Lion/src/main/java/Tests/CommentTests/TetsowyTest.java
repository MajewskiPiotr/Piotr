package Tests.CommentTests;

import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.TaskPage.JobTaskPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.LoginPage;
import Tests.BaseTest.BaseTestClass;
import core.Reports.TestData;
import core.Tools.JsScript;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-12.
 */
public class TetsowyTest extends BaseTestClass {

    @Test
    public void test() {
        TestData data = BaseTestClass.getData();
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.loginAsAdmin();
        //tworzymy skryptem Task (JOBa) i zapisujemy w danych testowych.
        String jobTask = JsScript.createTranslationJobWithParam(driver, "2017-06-21");
        //wyszukuje utworzonego Taska (JOBa)
        data.setJobTask(jobTask);
        dashboardPage.goToUrl(data.getJobTask());
        JobTaskPage jobTaskPage = new JobTaskPage(driver);
        jobTaskPage.waitForJobProcessing();
        Assert.assertEquals(jobTaskPage.getStatus(), TaskStatus.WAITING_FOR_PACKAGING, "Status Joba nie prawidłowy");
        jobTaskPage.waitForPage();
        jobTaskPage.clickOnButton(TaskButton.PROCESSING);

        //weryfikacja stanu
        Assert.assertEquals(jobTaskPage.getStatus(), TaskStatus.IN_PROCESSING);
        data.setJobTask(jobTask);
    }
}
