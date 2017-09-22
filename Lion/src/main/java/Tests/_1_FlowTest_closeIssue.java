package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-27.
 */

/* Scenariusz weryfikuje prawidłowe ustawianie stanów po zamknięciu zadanie przez JiraSoftware
1. utworzyć zgłoszenie _CUSTOMER
2. zklasyfikować zgłoszenie w SD _AGENT
3. utworzyć ISSUE w JiraSoftware (related issue) _AGENT
4. Zamknąć Issue w JiraSoftware _AGENT
5. zwryfikowac czy W SD stan został zmieniony
6. moze jeszcze jakaś weryfikacja w _Cutomer

 */
public class _1_FlowTest_closeIssue extends BaseTestClass {

    private String issueNr = "";
    private String relatedIssue = "";



    @Test(priority = 1, groups = {"regresja"})
    public void createIssue() {
        System.out.println("Selenium Test");
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        issueNr = customerServicePage.zglosBlad("Blad zgloszony Automatem, Scenariusz _1_FlowTest_closeIssue", " blad zostal zgloszony automatem");
        System.out.println("Issue number " + issueNr);

    }

    @Test(priority = 2, dependsOnMethods = {"createIssue"}, groups = {"regresja"})
    public void obsluzenieIssue() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        QueQuePage queQuePage = serviceDeskLoginPage.loginAsAgentAngGoToQueque();
        TaskPage taskPage = queQuePage.goToTask(issueNr);
        relatedIssue = taskPage.createLinkedIssue();
        TaskPage relatedIssue = taskPage.goToRelatedIssue(this.relatedIssue);
        relatedIssue.closeRelatedIssue();
        QueQuePage queQuePage1 = relatedIssue.goToQueQue();
        TaskPage taskPage1 = queQuePage1.goToTask(issueNr);
        Assert.assertEquals(taskPage1.getStatus(), TaskStatus.RESOLVED.getStatus(), "Stan Issue w SD nie został ustawiony na Resolved");
    }

    @Test(priority = 3, dependsOnMethods = {"obsluzenieIssue"}, groups = {"regresja"})
    public void verifyStatusInCustomer() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(issueNr);
        Assert.assertEquals(customerTaskPage.getStatus(), TaskStatus.RESOLVED.getStatus(), "Stan Issue w Customer nie został zmienony na RESOLVED");
    }
}
