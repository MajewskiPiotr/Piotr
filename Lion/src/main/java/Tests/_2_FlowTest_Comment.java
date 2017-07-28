package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.EditIssuePage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.CommentLabel;
import core.ElementsOnPages.Task.TaskField;
import core.ElementsOnPages.Task.TaskStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */

//Test weryfikuje poprawność procesu biznesowego w aplikacji, sprawdzamy czy odpowiednio dodane komentarze są widoczne dla odpowiednich userów

/*1. Zgłaszamy Bład
2. Agent Edytuje go i nadaje SLA
3. Odbicie zgłoszenia do klienta
4. Odbicie zgłoszenid do Agenta
5. Agent weryfikuje otrzymanie Wiadomości
6.
 */
public class _2_FlowTest_Comment extends BaseTestClass {
    private String issueNr = "";
    private String slaTime = "";
    private String agentComment = "Komentarz do Customera";
    private String customerComment = "komentarz do Agenta";

    @Test(priority = 20)
    public void utworzZgloszenieByCustomer() {
        //Zalogować się do Systemu wystawionego dla klienta i zglosić błąd
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        issueNr = customerServicePage.zglosBlad("Bład zgłoszony Automatem, Scenariusz _2_FlowTest_Comment", "problem z serwisem");
        Assert.assertTrue(!issueNr.equals(""), "nie udało się utworzyć zgłoszenia w Systemie Customer");
    }

    @Test(priority = 21, dependsOnMethods = {"utworzZgloszenieByCustomer"})
    public void obslugaPrzezAgenta() {
        //zalogować się do ServiceDeska i wybrać utworzony wcześniej Task
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToTask(issueNr);
        Assert.assertEquals(issueNr, taskPage.getTaskNumber(), "nie udało się pobrać odpowiedniego zadania do testów");
        EditIssuePage editIssuePage = taskPage.edytujIssue();
        editIssuePage.issueClasification();
        slaTime = taskPage.getTextFromField(TaskField.SLA);
        Assert.assertTrue(!slaTime.equals(""), "nie udało sie poprawnie utworzyć SLA dla ISSUE");
    }

    @Test(priority = 22, dependsOnMethods = {"obslugaPrzezAgenta"})
    //odbicie zgłoszenia do Customera
    public void AgentSendMessageToCustomer() {

        //zalogować sie jako Agent i przejśc na zgłoszenie
        //wpisac Komentarz dla Klienta
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToTask(issueNr);
        Assert.assertEquals(taskPage.getStatus(), TaskStatus.WAITING_FOR_SUPPORT.getStatus(), "stan ISSUE uniemożliwia przekazanie odpowiedzi do Customera");
        taskPage.respondToCustomer(agentComment);
        Assert.assertEquals(CommentLabel.TO_CUSTOMER.getLabel(), taskPage.getLabel(agentComment), "Nie prawidłowa Labelka na komentarzu");
        Assert.assertTrue(taskPage.isPause(), "Issue nie zostało zapauzowane");
        Assert.assertEquals(taskPage.getStatus(), TaskStatus.WAITING_FOR_CUSTOMER.getStatus());
    }

    @Test(priority = 23, dependsOnMethods = {"AgentSendMessageToCustomer"})
    public void CustomerSendMessageToAgent() {
        //Zalogowac się jako Customer i przejść do zgłoszenia
        //odpowiedzieć na pytanie od Agenta.
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(issueNr);
        //weryfikujemy czy wyświetla się czas SLA
        Assert.assertTrue(customerTaskPage.isSlaExist());
        customerTaskPage.respondToAgent(customerComment);
        //weryfikujemy czy komentarz jest widoczny
        Assert.assertTrue(customerTaskPage.verifyCommentExist(agentComment), "W aplikacji Customer brak komentarza od Agenta");
        //weryfikujemy czy ustawił się prawidłowy stan zgłoszenia
        Assert.assertEquals(customerTaskPage.getStatus(), TaskStatus.WAITING_FOR_SUPPORT.getStatus());
    }

    @Test(priority = 24, dependsOnMethods = {"CustomerSendMessageToAgent"})
    public void verifyMessageFromCustomer() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToTask(issueNr);
        //weryfikujemy czy komentarz od Customera jest widoczny w SD
        Assert.assertTrue(taskPage.verifyCommentExist(customerComment), "Brak komentarza od Customera");
        //TODO weryfikacja poprawności Labelki
        //!!!!!!!!!! Jaka ma być oczekiwana Labelka
        Assert.assertEquals(taskPage.getLabel(customerComment) , CommentLabel.FROM_CUSTOMER.getLabel(), "Nie prawidłowa Labelka na komentarzu");

    }
}
