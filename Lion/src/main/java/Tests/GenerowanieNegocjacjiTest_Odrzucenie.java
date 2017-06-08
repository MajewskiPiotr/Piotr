package Tests;

import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.Elements.Task.TaskTab;
import PageObjects.TaskPage.JobTaskPage;
import PageObjects.TaskPage.NegotiationTaskPage;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import PageObjects.main.DashboardPage;
import PageObjects.main.KanbanPage;
import PageObjects.main.LoginPage;
import Tests.BaseTest.BaseTestClass;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import core.Tools.JsScript;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
public class GenerowanieNegocjacjiTest_Odrzucenie extends BaseTestClass {


    //test dziala tylko na Chromie
    //powod: skrypt generujacy Joba nie dziala na innej przegladarce
    @Test(priority = 60)
    public void generowanieJoba() throws InterruptedException {
        //logujemy sie do Jiry jako Admin
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.loginAsAdmin();
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

    @Test(priority = 61)
    public void weryfikacjaUtworzonegoJoba() {
        System.out.println("weryfikujemy joba :" + data.getJobTask());
        Assert.assertTrue(data.getJobTask()!=null, "Nie udało sie utworzyć Joba w poprzednim teście.");
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
        //przekazanie numeru Taska do Data
        data.setTranslationTask(translationTask.getUrl());
        //weryfikacja stanu Translation task
        System.out.println("Translator Task: " + translationTask.getUrl());

        Assert.assertEquals(translationTask.getStatus(), TaskStatus.WAITING_FOR_ASSIGMENT, "Task nie znajduje się w odpowiednim stanie");
        //weryfikujemy czy wygenerowaly się TranslatorsPool

        Assert.assertTrue(translationTask.getTranslatorPool1Count() > 0, "Nie wygenerowano ");

        //przechodzimy na zakladke Assigments
        AssigmentsTabPage assigmentsTabPage = (AssigmentsTabPage) translationTask.goToTab(TaskTab.ASSIGMENTS);
        //weryfikujemy czy wygenerowały sie negocjacje
        Assert.assertTrue(assigmentsTabPage.getNegociationCount() > 0, "Nie zostały wygenerowane Negocjacje !");
        //przekazanie listy negocjacji do dalszeych testow
        data.setListOfAssigments(assigmentsTabPage.getNegotiations());
    }

    @Test(priority = 62)
    public void odrzuceniePrzezTranslatoraNegocjacji() {
        Assert.assertFalse(data.getListOfAssigments() == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");
        System.out.println("Wygenerowane negocjacje :" + data.getListOfAssigments().toString());

        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        DashboardPage kanbanPage = loginAsTranslator.loginAsAdmin();
        JsScript.switchUserByLogin(driver, data.getListOfAssigments().get(0).getTranslator());
        NegotiationTaskPage userTask = kanbanPage.goToNegotiationTask(data.getListOfAssigments().get(0).getKey());
        userTask.clickOnButton(TaskButton.REJECT);

        Assert.assertEquals(userTask.getStatus(), TaskStatus.REJECTED);
        data.setJobTask(userTask.getUrl());
        System.out.println("Badany Task Negocjacyjny : " + userTask.getUrl());
    }

    @Test(priority = 63)
    public void weryfikacjaStanowNegocjacji() {
        Assert.assertFalse(data.getListOfAssigments() == null, "Brak danych do testu. Nie zainijowano Listy z negocjacjami");

        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");
        TaskPage jobTask = dashboardPage.goToTask(data.getTranslationTask());
        AssigmentsTabPage pageObject = (AssigmentsTabPage) jobTask.goToTab(TaskTab.ASSIGMENTS);

        Assert.assertTrue(pageObject.checkIsTaskRejected(), "Task nie został odrzucony");
    }



}
