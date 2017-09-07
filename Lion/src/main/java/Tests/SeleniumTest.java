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
public class SeleniumTest extends BaseTestClass {


    @Test(priority = 1)
    public void createIssue() {
       driver.navigate().to("http:\\www.google.pl");
       System.out.println(driver.getCurrentUrl());
    }

    @Test(priority = 2, dependsOnMethods = {"createIssue"})
    public void obsluzenieIssue() {
        driver.navigate().to("http:\\www.allegro.pl");
        System.out.println(driver.getCurrentUrl());


    }

    @Test(priority = 3, dependsOnMethods = {"obsluzenieIssue"})
    public void verifyStatusInCustomer() {
        driver.navigate().to("http:\\www.poczta.wp.pl");
        System.out.println(driver.getCurrentUrl());



    }
}
