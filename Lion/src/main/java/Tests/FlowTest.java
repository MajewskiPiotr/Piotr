package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import core.ElementsOnPages.Task.TaskStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-27.
 */

/* Scenariusz
1. utworzyć zgłoszenie _CUSTOMER
2. zklasyfikować zgłoszenie w SD _AGENT
3. utworzyć ISSUE w JiraSoftware (related issue) _AGENT
4. Zamknąć Issue w JiraSoftware _AGENT
5. zwryfikowac czy W SD stan został zmieniony
6. moze jeszcze jakaś weryfikacja w _Cutomer

 */
public class FlowTest extends BaseTestClass {

    private String issueNr = "";
    private String relatedIssue = "";

    @Test
    public void createIssue() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        issueNr = customerServicePage.zglosBlad("Bład zgłoszony Automatem, Scenariusz FlowTest", " bład został zgłoszony automatem");
    }

    @Test
    public void obsluzenieIssue() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        QueQuePage queQuePage = serviceDeskLoginPage.loginAsAgentAngGoToQueque();
        TaskPage taskPage = queQuePage.goToTask(issueNr);
        relatedIssue = taskPage.createLinkedIssue();
        TaskPage relatedIssue = taskPage.goToRelatedIssue(this.relatedIssue);
        relatedIssue.clickOnButton(TaskButton.DONE);
        QueQuePage queQuePage1 = relatedIssue.goToQueQue();
        TaskPage taskPage1 = queQuePage1.goToTask(issueNr);
        Assert.assertEquals(taskPage1.getStatus(), TaskStatus.RESOLVED.getStatus(), "Stan Issue w SD nie został ustawiony na Resolved");
    }

    @Test
    public void verifyStatusInCustomer() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(issueNr);
        Assert.assertEquals(customerTaskPage.getStatus(), TaskStatus.RESOLVED.getStatus(),"Stan Issue w Customer nie został zmienony na RESOLVED");
    }
}
