package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.EditIssuePage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.Tools.Tools;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-22.
 */
public class _5_MasterIncident extends BaseTestClass {
    /* Test weryfikuje propagowanie zamykania się podrzędnych zgłoszeń
    1. Zgłosić 2 błedy jako Cutomer
    2. Jako Agent utworzyć Master Incident i do niego podłączyć uprzednio utworzone błędy.
    3. rozwiązać Master Incident i zweryfikować czy podrzedne zostały zamknięte
     */
    //dane testowe
    private String masterIncident;
    private List<String> relatedoIssue = new ArrayList<>();


    @Test(priority = 40)
    public void createIssue() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        relatedoIssue.add(customerServicePage.zglosBlad("Bład zgłoszony Automatem, Scenariusz MasterIncident_zgłoszenie1", "problem z serwisem turbo"));
        relatedoIssue.add(customerServicePage.zglosBlad("Bład zgłoszony Automatem, Scenariusz MasterIncident_zgłoszenie2", "problem z serwisem turbo"));
        relatedoIssue.add(customerServicePage.zglosBlad("Bład zgłoszony Automatem, Scenariusz MasterIncident_zgłoszenie1", "problem z serwisem turbo"));
        Tools.wypiszListe(relatedoIssue);
    }

    @Test(priority = 41, dependsOnMethods = {"createIssue"})
    public void createMasterIncident() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        QueQuePage queQuePage = serviceDeskLoginPage.loginAsAgentAngGoToQueque();
        EditIssuePage masterIncidentPage = queQuePage.createIssue();
        masterIncident = masterIncidentPage.createMasterIncident("Błąd zgłoszony automatem - Master Inciddent", "testowy opis", relatedoIssue);
        Assert.assertTrue(masterIncident != "", "nie udało się utworzyć Master Incidenta");
    }

    @Test(priority = 42, dependsOnMethods = {"createMasterIncident"})
    public void masterIncidentFlow() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        QueQuePage queQuePage = serviceDeskLoginPage.loginAsAgentAngGoToQueque();
        TaskPage masterIncidentPage = queQuePage.goToTask(masterIncident);
        masterIncidentPage.resolveIssue();
        Assert.assertTrue(masterIncidentPage.checkIsRelatedIssueIsClosed(), "podrzędne issue nie zostały zamknięte");
    }
}
