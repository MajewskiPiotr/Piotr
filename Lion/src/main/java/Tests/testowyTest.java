package Tests;

import core.ElementsOnPages.Task.TaskStatus;
import core.ElementsOnPages.Task.TaskTab;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import PageObjects.MainPage.DashboardPage;
import PageObjects.MainPage.LoginPage;
import core.JSscripts.JsScript;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-08.
 */
public class testowyTest extends BaseTestClass {
    @Test
    public void test() {
        LoginPage login = new LoginPage(driver);
        DashboardPage dashboardPage = login.loginAsAdmin();
        JsScript.switchUserByLogin(driver, "001-HU_00288888 ");
        TaskPage translationTask = dashboardPage.goToTask("GO-51472");

        TranslationTabPage assigments = (TranslationTabPage) translationTask.goToTab(TaskTab.TRANSLATION_TASKS);
        assigments.getTranslationTask(TaskStatus.ASSIGNED_TO_AG);
    }
}