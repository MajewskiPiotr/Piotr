package Tests;

import Tools.Configuration.BrowserType;
import Tools.Configuration.EnviromentSettings;
import Tools.Configuration.TestEnviroments;
import Tools.HelpersClass.Assigments;
import Tools.HelpersClass.Task;
import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskLink;
import Web.PageObjects.Elements.Task.TaskStatus;
import Web.PageObjects.Elements.Task.TaskTab;
import Web.PageObjects.TaskPage.TaskPage;
import Web.PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import Web.PageObjects.main.CurrentSearchPage;
import Web.PageObjects.main.DashboardPage;
import Web.PageObjects.main.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-24.
 */
public class WorkFlowEdytora_Pozytywny extends BaseTestClass {


    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

    //należy zalogowac sie na usera ktory posiada ustawiony edit level
    //W obecnej chwili ustawiam tam UI i takich taskow wyszukuje w kolejnym tescie
    @Test(priority = 30)
    public void akceptacjaIObslugaTaskaPrzezTranslatora() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        CurrentSearchPage currentSearchPage = loginPage.logInToJiraAndGoToSearch("001-HU_00588888", "lion");

        TaskPage negotiationTask = currentSearchPage.searchForNewNegociationTask();


        negotiationTask.clickOnButton(TaskButton.ACCEPT);
        negotiationTask.clickOnButton(TaskButton.TRANSLATION_TASK_REF);

        TaskPage translatortask = new TaskPage(driver);
        translatortask.clickOnButton(TaskButton.IN_PROGRESS);
        translatortask.clickOnButton(TaskButton.SELF_QA);
        translatortask.clickOnButton(TaskButton.COMPLETED_TRANSLATOR);
        data.setTranslationTask(translatortask.getUrl());

    }

    @Test(priority = 31)
    public void weryfikacjaWygenerowaniaTaskowEdytorskich() {
        System.out.println("Translation Task :" + data.getTranslationTask());
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.loginAsAdmin();
        TaskPage taskPage = dashboardPage.goToTask(data.getTranslationTask());
        AssigmentsTabPage assigmentTab = (AssigmentsTabPage) taskPage.goToTab(TaskTab.ASSIGMENTS);
        Assert.assertTrue(assigmentTab.checkEditorWorkIsPresent(), "nie wygenerowaly sie zadania edytorskie");
        data.setListOfAssigments(assigmentTab.getNewEditorWork());
    }

    @Test(priority = 32)
    public void obslugaTaskowEdytorskich() {
        Assigments editor = data.getListOfAssigments().get(0);
        LoginPage loginAsEditor = new LoginPage(driver);
        loginAsEditor.open();
        DashboardPage dashboardPage = loginAsEditor.logInToJira(editor.getTranslator(), "lion");
        System.out.println("zalogowano sie jako: " + editor.getTranslator());
        TaskPage negotiationTask = dashboardPage.goToTask(editor.getKey());
        negotiationTask.clickOnButton(TaskButton.ACCEPT);
        TaskPage translationTask = (TaskPage) negotiationTask.clickInLink(TaskLink.TRANSLATION_TASK_REF);
        //Assert.assertEquals(translationTask.getStatus(), TaskStatus.READY_TO_VERIFY);
        translationTask.clickOnButton(TaskButton.ASSIGN_TO_EDITOR);
        Assert.assertEquals(translationTask.getStatus(), TaskStatus.ASSIGNED_TO_EDITOR);
        //weryfikacja przypisania odpowiedniego edytora do taska
        Assert.assertEquals(translationTask.getEditor(), editor.getTranslator());
        //weryfikacja przypisania taska do Edytora
        Assert.assertEquals(translationTask.getEditor(), translationTask.getAssignee());
        translationTask.clickOnButton(TaskButton.COMPLETED_EDITOR);
        Assert.assertEquals(translationTask.getStatus(), TaskStatus.COMPLETED);


    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}

