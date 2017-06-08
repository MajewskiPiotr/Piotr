package Tests.CommentTests;

import PageObjects.Elements.CommentScope;
import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskRole;
import PageObjects.TaskPage.TaskPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.LoginPage;
import Tests.BaseTest.BaseTestClass;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import core.Tools.HelpersClass.Task;
import core.Tools.JsScript;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by Piotr Majewski on 2017-06-06.
 */
public class InternalTeamCommentTest extends BaseTestClass {
    //Leanguage Owner
    private String LO = "Marianna.Kulakowska";
    private String komentarz ="Testowy komentarz" + new Date().getTime();


    @Test(priority = 311)
    public void wprowadzKomentarzDoTranslationTask() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DashboardPage dashboardPage = loginPage.loginAsAdmin();
        TaskPage translationTask = dashboardPage.goToTask("GO-35815");
        LO = translationTask.getUserFromRole(TaskRole.Language_Owner);
        System.out.println("ustawilem " + LO);
        translationTask.clickOnButton(TaskButton.COMMENT);
        translationTask.typeCommentAndSetScope(komentarz, CommentScope.InternalTeam);
    }

    @Test(priority = 411)
    public void zweryfikujJakoLO() {
        LoginPage logAsLO = new LoginPage(driver);
        logAsLO.open();
        logAsLO.loginAsAdmin();
        System.out.println("przechodze na " + LO);
        JsScript.switchUserByLogin(driver, LO);
        TaskPage task = logAsLO.goToTask("GO-35815");
        Assert.assertTrue(task.getAllComents().contains(komentarz));
    }


}

