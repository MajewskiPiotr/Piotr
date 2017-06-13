package Tests;

import PageObjects.MainPage.DashboardPage;
import PageObjects.MainPage.LoginPage;
import PageObjects.MainPage.QueQuePage;
import PageObjects.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-08.
 */
public class testowyTest extends BaseTestClass {

    @Test
    public void test() {
        LoginPage login = new LoginPage(driver);
        DashboardPage dashboardPage = login.loginAsAdmin();
        QueQuePage queQuePage = dashboardPage.goToQueQue();

        queQuePage.clickOnButton(TaskButton.HELPDESK_TEAM_A);
        System.out.println("kliknąłem");
        TaskPage taskPage = queQuePage.goToFirstTaskOnList();
        taskPage.getProductClass();

        //DashboardPage dashboardPage1 = taskPage.goToDashboard();
        //Insightpage insightpage = dashboardPage1.goToInsightPage();
        //insightpage.clickOnButton(TaskButton.SLA);
        //insightpage.switchViewToList();


    }
}
