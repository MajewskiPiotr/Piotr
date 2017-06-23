package Tests;

import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import core.Reports.Mail;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-23.
 */
public class IncidentCallByEmail extends BaseTestClass{

    @Test
    public void callIssueByEmail() {
        Mail mail = new Mail();
        mail.send("Testowe zgłoszenie emailem z nowym adresem", "treść zgłoszenia");
    }


    public void verifyThatIssueIsCreate(){
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();

    }
}
