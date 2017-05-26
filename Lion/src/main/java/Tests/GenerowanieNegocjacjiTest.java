package Tests;

import Tools.Configuration.BrowserType;
import Tools.Configuration.EnviromentSettings;
import Tools.Configuration.TestEnviroments;
import Tools.JsScript;
import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskStatus;
import Web.PageObjects.Elements.Task.TaskTab;
import Web.PageObjects.TaskPage.JobTaskPage;
import Web.PageObjects.TaskPage.TaskPage;
import Web.PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import Web.PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import Web.PageObjects.main.DashboardPage;
import Web.PageObjects.main.KanbanPage;
import Web.PageObjects.main.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
public class GenerowanieNegocjacjiTest extends BaseTestClass {
    //zmienne do przekazywanie miedzy testami
    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

    //test dziala tylko na Chromie
    //powod: skrypt generujacy Joba nie dziala na innej przegladarce
    @Test(priority = 1)
    public void generowanieJoba() throws InterruptedException {
        //logujemy sie do Jiry jako Admin
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        //tworzymy skryptem Task (JOBa) i zapisujemy w danych testowych.
        String jobTask = JsScript.createTranslationJob(driver);
        //wyszukuje utworzonego Taska (JOBa)
        data.setJobTask(jobTask);
        dashboardPage.goToUrl(data.getJobTask());
        JobTaskPage jobTaskPage = new JobTaskPage(driver);
        jobTaskPage.waitForPage();
        Assert.assertEquals(jobTaskPage.getStatus(), TaskStatus.WAITING_FOR_PACKAGING, "Status Joba nie prawidłowy");
        jobTaskPage.waitForPage();
        jobTaskPage.clickOnButton(TaskButton.PROCESSING);

        //weryfikacja stanu
        Assert.assertEquals(jobTaskPage.getStatus(), TaskStatus.IN_PROCESSING);
        data.setJobTask(jobTask);

    }

    @Test(priority = 2)
    public void weryfikacjaUtworzonegoJoba() {
        System.out.println("weryfikujemy joba :" + data.getJobTask());
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        dashboardPage.goToTask(data.getJobTask());
        JobTaskPage taskJobViewPage = new JobTaskPage(driver);
        Assert.assertEquals(taskJobViewPage.getStatus(), TaskStatus.IN_PROCESSING, "JOB Nie zmienił stanu na IN PROGRESS");
        //zmienna do porownania z iloscia Taskow negocjacyjnych

        int plpCount = taskJobViewPage.plpCount();
        TranslationTabPage translationTasksPage = (TranslationTabPage) taskJobViewPage.goToTab(TaskTab.TRANSLATION_TASKS);
        Assert.assertEquals(plpCount, translationTasksPage.countTranslatorTask());
        TaskPage translationTask = translationTasksPage.goToFirstTask();
        //weryfikacja stanu Translation task
        System.out.println("Translator Task: " + translationTask.getUrl());

        Assert.assertEquals(translationTask.getStatus(), TaskStatus.WAITING_FOR_ASSIGMENT);
        //weryfikujemy czy wygenerowaly się TranslatorsPool

        Assert.assertTrue(translationTask.getTranslatorPool1Count() > 0, "Nie wygenerowano ");

        //przechodzimy na zakladke Assigments
        AssigmentsTabPage assigmentsTabPage = (AssigmentsTabPage) translationTask.goToTab(TaskTab.ASSIGMENTS);
        //weryfikujemy czy wygenerowały sie negocjacje
        Assert.assertTrue(assigmentsTabPage.getNegociationCount() > 0, "Nie zostały wygenerowane Negocjacje !");
        //przekazanie listy negocjacji do dalszeych testow
        data.setListOfAssigments(assigmentsTabPage.getNegotiations());

    }

    @Test(priority = 3)
    public void akceptacjaPrzezTranslatoraNegocjacji() {
        Assert.assertFalse(data.getListOfAssigments() == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");
        System.out.println("Wygenerowane negocjacje :" + data.getListOfAssigments().toString());

        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        KanbanPage kanbanPage = loginAsTranslator.logInToJiraAndGoToKanban(data.getListOfAssigments().get(0).getTranslator(), "lion");
        TaskPage userTask = kanbanPage.goToTask(data.getListOfAssigments().get(0).getKey());
        userTask.clickOnButton(TaskButton.ACCEPT);

        Assert.assertEquals(userTask.getStatus(), TaskStatus.ACCEPTED);
        System.out.println("Task badany : " + userTask.getUrl());
    }

    @Test(priority = 4)
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
    }


    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
