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
import PageObjects.main.LoginPage;
import Tests.BaseTest.BaseTestClass;
import Tests.BaseTest.GenerowanieJoba;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import core.Tools.JsScript;
import core.Tools.LionAssert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
@Listeners(core.Listeners.Listeners.class)
public class GenerowanieNegocjacjiTest extends BaseTestClass {


    //test dziala tylko na Chromie
    //powod: skrypt generujacy Joba nie dziala na innej przegladarce
    @Test(priority = 1)
    public void generowanieJoba() throws InterruptedException {
        GenerowanieJoba.wygenerujJoba(driver);

    }

    @Test(priority = 2)
    public void weryfikacjaUtworzonegoJoba() {
        System.out.println("weryfikujemy joba :" + data.getJobTask());
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        DashboardPage dashboardPage = loginAsAdmin.loginAsAdmin();
        dashboardPage.goToTask(data.getJobTask());
        JobTaskPage taskJobViewPage = new JobTaskPage(driver);
        Assert.assertEquals(taskJobViewPage.getStatus(), TaskStatus.IN_PROCESSING, "JOB Nie zmienił stanu na IN PROGRESS");
        //zmienna do porownania z iloscia Taskow negocjacyjnych

        int plpCount = taskJobViewPage.plpCount();
        TranslationTabPage translationTasksPage = (TranslationTabPage) taskJobViewPage.goToTab(TaskTab.TRANSLATION_TASKS);
        Assert.assertEquals(plpCount, translationTasksPage.countTranslatorTask());
        TaskPage translationTask = translationTasksPage.goToFirstTask();
        //weryfikacja stanu Translation task
        System.out.println("Translator Task: " + translationTask.getUrl()+ " stan "+ translationTask.getStatus());
        LionAssert.assertStatus(translationTask.getStatus(), TaskStatus.WAITING_FOR_ASSIGMENT, TaskStatus.ASSIGNED_TO_TRANSLATOR,"niepoprawny stan TranslatgionTaska podczas weryfikacji Joba ");

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
        DashboardPage kanbanPage = loginAsTranslator.loginAsAdmin();
        JsScript.switchUserByLogin(driver, data.getListOfAssigments().get(0).getTranslator());
        NegotiationTaskPage userTask = kanbanPage.goToNegotiationTask(data.getListOfAssigments().get(0).getKey());
        userTask.clickOnButton(TaskButton.ACCEPT);
        LionAssert.assertStatus(userTask.getStatus(), TaskStatus.ACCEPTED, TaskStatus.AUTOMATICALLY_ACCEPTED, "nie poprawny sta Negocjacji po zaakceptowaniu");
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
        data.setZakonczono(true);
    }



}
