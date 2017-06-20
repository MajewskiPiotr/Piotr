package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */
public class FlowTest extends BaseTestClass {
    private String nrIssueFromCustomer="";

    @Test
    public void utworzZgloszenieByCustomer() {
        //Zalogować się do Systemu wystawionego dla klienta i zglosić błąd
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        nrIssueFromCustomer = customerServicePage.zglosBlad("Nie dziala MOTOOnetTurbo1","problem z serwisem");
        Assert.assertTrue(!nrIssueFromCustomer.equals(""), "nie udało się utworzyć zgłoszenia w Systemie Customer");
    }

    @Test
    public void obslugaPrzezAgenta(){
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        serviceDeskLoginPage.loginAsAdmin();

    }


}
