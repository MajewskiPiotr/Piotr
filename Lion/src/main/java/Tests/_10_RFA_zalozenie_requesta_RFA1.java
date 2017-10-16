package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import core.BaseTestClass;
import core.JSscripts.JsScript;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-22.
 */
public class _10_RFA_zalozenie_requesta_RFA1 extends BaseTestClass {

    //Scenariusz
    //https://crem.evercode.com.pl/browse/DLAB-210

    String rfaIssueNumber;// = "DLSD-661";
    String securityApproval;// = "AdminEVC2";
    String supervisor;
    String[] specliastList;

    @Test(priority = 100)
    public void createRFA() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer("u1", "u1");
        customerServicePage.goToUrl("/servicedesk/customer/portal/2");
        customerServicePage.goToUrl("/servicedesk/customer/portal/2/group/23");
        rfaIssueNumber = customerServicePage.createRFA_1("u1");
        CustomerTaskPage rfaTask = customerServiceLoginPage.goToTask(rfaIssueNumber);
        supervisor = rfaTask.getSupervisor();
        System.out.println(supervisor);
    }



    @Test(priority = 102)
    public void supervisorApproval() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
        JsScript.switchUserByLogin(driver, supervisor);
        CustomerServicePage customerServicePage = dashboardPage.goToServiceDeskPortal();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(rfaIssueNumber);
        specliastList = customerTaskPage.approveRFAasSupervisor();
        System.out.println("specjaliści :" + specliastList.length);
        System.out.println("specjalista:" + specliastList[0]);

    }

    @Test(priority = 103)
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
