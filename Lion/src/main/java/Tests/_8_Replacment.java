package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.Insightpage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import core.ElementsOnPages.Task.TaskButton;
import core.ElementsOnPages.Task.TaskStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Piotr Majewski on 2017-06-22.
 */
public class _8_Replacment extends BaseTestClass {
    /**************** PTA
     1. zalogować się do ServidDeska (Portal KLienta)
     2. założyć Zgłoszenie Substitution
     3. zalogować sie do Jira
     4. Wejść do Insight
     5. zweryfikować czy ustawił się odpowiedni rekord
     6. Otworzyć SD (Portal klienta)
     7. Odzukac założone zgłoszenie
     8. zakończyć zastępstwo
     9. Sprawdzić w Insight czy odnotowano zamknięcie
     */

    String nrZgloszeniaZastepstwa;

    @Test(priority = 91)
    public void setSubstitution() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        //a potrzeby testów zastępst zmieniam URL serviceDeska na URL serviceDeska przeznaczonego do Zastępst
        customerServiceLoginPage.setCustomerURL("http://vpn.evercode.com.pl/servicedesk/customer/portal/13");
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        nrZgloszeniaZastepstwa = customerServicePage.setSubstitution("adminEVC", "customer1");
    }

    @Test(priority = 92)
    public void checkSustitutionIsInInsight() throws IOException, InterruptedException {

        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
        Insightpage insightpage = dashboardPage.goToInsightPage("Substitutions");
        insightpage.clickOnButton(TaskButton.SUBSTITUTIONS);
        insightpage.switchViewToList();
        String subsState = insightpage.findSubstitution(nrZgloszeniaZastepstwa);
        Assert.assertEquals(subsState, "on", "zastępstwo jest w błędnym stanie");
    }

    @Test(priority = 93)
    public void closeSubstitution() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        CustomerTaskPage customerTaskPage = customerServicePage.goToTask(nrZgloszeniaZastepstwa);
        customerTaskPage.closeSubstitution();
        Assert.assertEquals(customerTaskPage.getStatus(), TaskStatus.CLOSED_SUBSTIITUTON.getStatus(), "Zastępstwo nie zostało zamknięte");

    }

    @Test(priority = 94)
    public void checkSustitutionIsClosed() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
        Insightpage insightpage = dashboardPage.goToInsightPage("Substitutions");
        insightpage.clickOnButton(TaskButton.SUBSTITUTIONS);
        insightpage.switchViewToList();
        String subsState = insightpage.findSubstitution(nrZgloszeniaZastepstwa);
        Assert.assertEquals(subsState, "off", "zastępstwo nie zostało zamknięte");
    }

}
