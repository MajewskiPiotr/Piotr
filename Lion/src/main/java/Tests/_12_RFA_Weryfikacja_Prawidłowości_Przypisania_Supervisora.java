package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.Insightpage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import core.BaseTestClass;
import core.JSscripts.JsScript;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-22.
 */
public class _12_RFA_Weryfikacja_Prawidłowości_Przypisania_Supervisora extends BaseTestClass {

    //Scenariusz
    //https://crem.evercode.com.pl/browse/DLAB-210
    String user = "u1";
    String rfaIssueNumber;// = "DLSD-661";
    String securityApproval;// = "AdminEVC2";
    String supervisor;

    @Test(priority = 120)
    public void createRFA() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer("u1", "u1");
        customerServicePage.goToUrl("/servicedesk/customer/portal/2");
        customerServicePage.goToUrl("/servicedesk/customer/portal/2/group/23");
        rfaIssueNumber = customerServicePage.createRFA_2(user);
        CustomerTaskPage rfaTask = customerServiceLoginPage.goToTask(rfaIssueNumber);
        securityApproval = rfaTask.getSecurityApproval();
        System.out.println(securityApproval);
    }

    @Test(priority = 121)
    public void securityApproval() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
        JsScript.switchUserByLogin(driver, securityApproval);
        CustomerServicePage customerServicePage = dashboardPage.goToServiceDeskPortal();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(rfaIssueNumber);
        supervisor = customerTaskPage.approveRFAasSecurity();
        System.out.println(supervisor);
    }

    @Test(priority = 122)
    public void weryfikacjaSupervisora() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
        Insightpage enova = dashboardPage.goToInsightPage("Enova");
        enova.switchViewToList();
        String supervisorName = enova.findSupervisor(user);
        Assert.assertEquals(supervisor.toLowerCase(), supervisorName.toLowerCase());
    }


}
