package Tests;

import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.Reports.Mail;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-23.
 */
public class _3_IncidentCallByEmail extends BaseTestClass {
    private String summary = "Bład zgłoszony poprzez Emaila dla potrzeby Skryptów automatycznych";

    @Test(priority = 30)
    public void callIssueViaEmail() {
        Mail mail = new Mail();
        mail.send(summary, "treść zgłoszenia");
    }

   // @Test(priority = 31)
    public void verifyThatIssueIsCreate() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        //TODO czekanie na to aż wpadnie zgłoszenie
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToFirstTaskOnList();
        Assert.assertEquals(taskPage.getSummary(), summary);
    }
}
