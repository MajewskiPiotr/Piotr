package PageObjects.CustomerService;

import PageObjects.Base.PageObject;
import PageObjects.ServiceDesk.MainPage.CurrentSearchPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.KanbanPage;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-05-15.
 * KLasa reprezentuje strone logowania do aplikacji Jira
 */
public class CustomerServiceLoginPage extends PageObject {

    private String pageUrl = "";

    @FindBy(name = "os_username")
    private WebElement userName;
    @FindBy(id = "os_password")
    private WebElement password;
    @FindBy(id="login-form")
    private WebElement logIn;

    public CustomerServiceLoginPage(WebDriver driver) {
        super(driver);
    }

    private void open() {
        try {
            driver.navigate().to(TestEnviroments.CUSTOMER);
        } catch (TimeoutException ex) {
            Assert.fail("Nie udało otworzyć sie aplikacji pod adresem " + TestEnviroments.CUSTOMER);
        }
    }


    private void login(String login, String haslo) {
        open();
        try {
            userName.sendKeys(login);
            password.sendKeys(haslo + Keys.ENTER);
            //logIn.submit();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (NoSuchElementException exception) {
            Assert.fail("Nie udało sie uruchomić JIRA");
        }
    }

    public CustomerServicePage logInToCustomer() {
        login("ParkerCustomer", "12345678");
        return new CustomerServicePage(driver);
    }




}
