package Tests.BaseTest;

import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.Elements.Task.TaskTab;
import PageObjects.TaskPage.JobTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.LoginPage;
import Tests.BaseTestClass;
import core.Reports.TestData;
import core.Tools.JsScript;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-05-30.
 */
public class GenerowanieJoba {

    public static void zweryfikujJoba(WebDriver driver){
        TestData data = BaseTestClass.getData();
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
        data.setTranslationTask(translationTask.getUrl());
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



    public static void wygenerujJoba(WebDriver driver){
        TestData data = BaseTestClass.getData();
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        //tworzymy skryptem Task (JOBa) i zapisujemy w danych testowych.
        String jobTask = JsScript.createTranslationJob(driver);
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
    public static void wygenerujJobaDlaDropboxa(WebDriver driver){
        TestData data = BaseTestClass.getData();
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        //tworzymy skryptem Task (JOBa) i zapisujemy w danych testowych.
        String jobTask = JsScript.createTranslationJobForDropbox(driver);
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
