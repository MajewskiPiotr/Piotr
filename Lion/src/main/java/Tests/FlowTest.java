package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.EditIssuePage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskField;
import core.ElementsOnPages.Task.TaskStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */
public class FlowTest extends BaseTestClass {
    private String nrIssueFromCustomer = "";
    private String slaTime = "";
    private String komenarzDoCustomera = "Komentarz do Customera";
    private String komentarzDoAgenta = "komentarz do Agenta";

    @Test(priority = 0)
    public void utworzZgloszenieByCustomer() {
        //Zalogować się do Systemu wystawionego dla klienta i zglosić błąd
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        nrIssueFromCustomer = customerServicePage.zglosBlad("Nie dziala TURBO", "problem z serwisem");
        Assert.assertTrue(!nrIssueFromCustomer.equals(""), "nie udało się utworzyć zgłoszenia w Systemie Customer");
    }

    @Test(priority = 1, dependsOnMethods = {"utworzZgloszenieByCustomer"})
    public void obslugaPrzezAgenta() {
        //zalogować się do ServiceDeska i wybrać utworzony wcześniej Task
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToTask(nrIssueFromCustomer);
        Assert.assertEquals(nrIssueFromCustomer, taskPage.getTaskNumber(), "nie udało się pobrać odpowiedniego zadania do testów");
        EditIssuePage editIssuePage = taskPage.edytujIssue();
        editIssuePage.sklasyfikujIssue();
        slaTime = taskPage.getTextFromField(TaskField.SLA);
        Assert.assertTrue(!slaTime.equals(""), "nie udało sie poprawnie obsluzyc ISSUE");
    }

    @Test(priority = 2)
    public void obslugaKomunikacji_Agent_Customer() {

        //zalogować sie jako Agent i przejśc na zgłoszenie
        //wpisac Komentarz dla Klienta
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToTask(nrIssueFromCustomer);
        Assert.assertEquals(taskPage.getStatus(), TaskStatus.WAITING_FOR_SUPPORT.getStatus(), "stan ISSUE uniemożliwia przekazanie odpowiedzi do Customera");
        taskPage.respondToCustomer(komenarzDoCustomera);
        Assert.assertTrue(taskPage.isPause(), "Issue nie zostało zapauzowane");
        Assert.assertEquals(taskPage.getStatus(), TaskStatus.WAITING_FOR_CUSTOMER.getStatus());

    }

    @Test(priority = 3)
    public void obslugaKomunikacjiCustomer_Agent() {
        //Zalogowac się jako Customer i przejść do zgłoszenia
        //odpowiedzieć na pytanie od Agenta.
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(nrIssueFromCustomer);
        customerTaskPage.respondToAgent(komentarzDoAgenta);
        Assert.assertTrue(customerTaskPage.zweryfikujIstnienieKomentarza(komenarzDoCustomera), "W aplikacji Customer brak komentarza od Agenta");
        Assert.assertEquals(customerTaskPage.getStatus(), TaskStatus.WAITING_FOR_SUPPORT.getStatus());
    }

    @Test(priority = 4)
    public void weryfikacjaKomentarzaOdCustomera() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToTask(nrIssueFromCustomer);
        Assert.assertTrue(taskPage.zweryfikujKomentarz(komentarzDoAgenta), "Brak komentarza od Customera");

    }

}
