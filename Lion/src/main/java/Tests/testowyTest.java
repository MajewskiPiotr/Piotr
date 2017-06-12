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
        JsScript.createTranslationJob(driver);
    }
}
