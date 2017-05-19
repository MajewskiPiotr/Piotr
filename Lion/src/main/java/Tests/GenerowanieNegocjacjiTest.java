package Tests;

import Elements.Task.TaskButton;
import Elements.Task.TaskStatus;
import Elements.Task.TaskTab;
import PageObjects.TaskPage.JobTaskPage;
import PageObjects.TaskPage.NegotiationTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import PageObjects.main.CurrentSearchPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.KanbanPage;
import PageObjects.main.LoginPage;
import Tools.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
public class GenerowanieNegocjacjiTest {
    WebDriver driver;
    //zmienne do przekazywanie miedzy testami
    String jobTaskURL;// = "https://aps.staging2.lionbridge.com/browse/GO-33578";
    List<Negotiations> negotiations;

    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

    //test dziala tylko na Chromie
    //powod: skrypt generujacy Joba nie dziala na innej przegladarce
    @Test(priority = 0)
    public void generowanieJoba() {
        //logujemy sie do Jiry jako Admin
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");

        //tworzymy skryptem Task (JOBa)
        try {
            JsScript.createJob(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //wyszukuje utworzonego Taska (JOBa)
        CurrentSearchPage searchPage = new CurrentSearchPage(driver);
        JobTaskPage taskJobViewPage = searchPage.searchForNewTransactionJOB();
        //na potrzeby kolejnego testu przekazuje adres do utworzonego zadania
        jobTaskURL = searchPage.getTaskNumberURL();
        //weryfikacja stanu utworzonego Taska
        Assert.assertEquals(taskJobViewPage.getStatus(), TaskStatus.WAITING_FOR_PACKAGING);

        taskJobViewPage.clickOnButton(TaskButton.PROCESSING);


        //weryfikacja stanu
        Assert.assertEquals(taskJobViewPage.getStatus(), TaskStatus.IN_PROCESSING);
    }

    @Test(priority = 1)
    public void weryfikacjaUtworzonegoJoba() {
        System.out.println(jobTaskURL);
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        dashboardPage.goToTask(jobTaskURL);
        JobTaskPage taskJobViewPage = new JobTaskPage(driver);

        //zmienna do porownania z iloscia Taskow negocjacyjnych

        int plpCount = taskJobViewPage.plpCount();
        TranslationTabPage translationTasksPage = (TranslationTabPage) taskJobViewPage.goToTab(TaskTab.TRANSLATION_TASKS);
        Assert.assertEquals(plpCount, translationTasksPage.countTranslatorTask());
        TaskPage translationTask = translationTasksPage.goToFirstTask();
        //weryfikacja stanu Translation task
        System.out.println("Translator Task: " + translationTask.getUrl());

        Assert.assertEquals(translationTask.getStatus(), TaskStatus.WAITING_FOR_ASSIGMENT);
        //weryfikujemy czy wygenerowaly się TranslatorsPool

        Assert.assertTrue(translationTask.getTranslatorPool1Count() > 0);

        //przechodzimy na zakladke Assigments
        AssigmentsTabPage assigmentsTabPage = (AssigmentsTabPage) translationTask.goToTab(TaskTab.ASSIGMENTS);
        //weryfikujemy czy wygenerowały sie negocjacje
        Assert.assertTrue(assigmentsTabPage.getNegociationCount() > 0, "Nie zostały wygenerowane Negocjacje !");
        //przekazanie listy negocjacji do dalszeych testow
        negotiations = assigmentsTabPage.getNegotiations();

    }

    @Test(priority = 2)
    public void akceptacjaPrzezTranslatoraNegocjacji() {
        Assert.assertFalse(negotiations == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");
        System.out.println(negotiations.toString());

        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        KanbanPage kanbanPage = loginAsTranslator.logInToJiraAndGoToKanban(negotiations.get(0).getTranslator(), "lion");
        NegotiationTaskPage userTask = (NegotiationTaskPage) kanbanPage.goToNegotiationTask(negotiations.get(0).getKey());
        userTask.clickOnButton(TaskButton.ACCEPT);

        Assert.assertEquals(userTask.getStatus(), TaskStatus.ACCEPTED);
        System.out.println("Task : " + userTask.getUrl());


    }

    @Test(priority = 3)
    public void weryfikacjaStanowNegocjacji() {
        Assert.assertFalse(negotiations == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");

        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        TaskPage jobTask = (TaskPage) dashboardPage.goToTask(jobTaskURL);
        TranslationTabPage translationTabPage = (TranslationTabPage) jobTask.goToTab(TaskTab.TRANSLATION_TASKS);

        Assert.assertTrue(translationTabPage.checkIsStatusChange());

        TaskPage taskViewPage = translationTabPage.goToFirstTask();
        AssigmentsTabPage pageObject = (AssigmentsTabPage) taskViewPage.goToTab(TaskTab.ASSIGMENTS);

        Assert.assertTrue(pageObject.checkIsStatusChange(), "Zmiana statusow Negocjacji");
        System.out.println("Task: " + taskViewPage.getUrl());
    }


    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
