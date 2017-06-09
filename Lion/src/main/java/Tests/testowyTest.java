package Tests;

import PageObjects.ElementsOnPages.Task.Tab.AssignmentsType;
import PageObjects.ElementsOnPages.Task.TaskTab;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.LoginPage;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-08.
 */
public class testowyTest extends BaseTestClass {
    @Test
    public void test() {
        LoginPage login = new LoginPage(driver);
        DashboardPage dashboardPage = login.loginAsAdmin();
        TaskPage translationTask = dashboardPage.goToTask("GO-51221");

        AssigmentsTabPage assigments = (AssigmentsTabPage) translationTask.goToTab(TaskTab.ASSIGMENTS);
        assigments.getAssigmentsListByType(AssignmentsType.New_Editor_Work_Available);
    }
}
