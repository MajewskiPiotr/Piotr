package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import core.JSscripts.JsScript;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-22.
 */
public class _11_RFA_zalozenie_requesta_RFA2 extends BaseTestClass {

    //Scenariusz
    //https://crem.evercode.com.pl/browse/DLAB-210

    String rfaIssueNumber;// = "DLSD-661";
    String securityApproval;// = "AdminEVC2";
    String supervisor;
    String[] specliastList;

    @Test(priority = 110)
    public void createRFA() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer("u1", "u1");
        customerServicePage.goToUrl("/servicedesk/customer/portal/2");
        customerServicePage.goToUrl("/servicedesk/customer/portal/2/group/23");
        rfaIssueNumber = customerServicePage.createRFA_2("u1");
        CustomerTaskPage rfaTask = customerServiceLoginPage.goToTask(rfaIssueNumber);
        securityApproval = rfaTask.getSecurityApproval();
        System.out.println(securityApproval);
    }

    @Test(priority = 111)
    public void securityApproval() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
        JsScript.switchUserByLogin(driver, securityApproval);
        CustomerServicePage customerServicePage = dashboardPage.goToServiceDeskPortal();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(rfaIssueNumber);
        supervisor = customerTaskPage.approveRFAasSecurity();
        System.out.println(supervisor);
    }

    @Test(priority = 112)
    public void supervisorApproval() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
        JsScript.switchUserByLogin(driver, supervisor);
        CustomerServicePage customerServicePage = dashboardPage.goToServiceDeskPortal();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(rfaIssueNumber);
        specliastList = customerTaskPage.approveRFAasSupervisor();
        System.out.println("specjaliści :" + specliastList.length);
        System.out.println("specjalista:" + specliastList[0]);
        System.out.println("specjalista:" + specliastList[1]);

    }

    @Test(priority = 113)
    public void specialistApproval() {

        for (int i = 0; i < specliastList.length; i++) {
            System.out.println("specjalista : " + specliastList[i]);

            ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
            DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
            JsScript.switchUserByLogin(driver, specliastList[i]);

            CustomerServicePage customerServicePage = dashboardPage.goToServiceDeskPortal();
            CustomerTaskPage customerTaskPage = customerServicePage.goToTask(rfaIssueNumber);
            customerTaskPage.approvedRFA();
            System.out.println(customerTaskPage.getStatus());

        }
    }


}
