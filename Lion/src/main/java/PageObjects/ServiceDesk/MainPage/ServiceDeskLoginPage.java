package PageObjects.ServiceDesk.MainPage;

import PageObjects.Base.PageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class ServiceDeskLoginPage extends PageObject {


    private String pageUrl = "";


    @FindBy(name = "os_username")
    private WebElement userName;

    @FindBy(id = "login-form-password")
    private WebElement password;

    @FindBy(id = "login-form")
    private WebElement logIn;


    public ServiceDeskLoginPage(WebDriver driver) {

        super(driver);

    }


    private void open() {

        try {

            driver.navigate().to(baseUrl + pageUrl);

        } catch (TimeoutException ex) {

            Assert.fail("Nie udało otworzyć sie aplikacji pod adresem " + baseUrl + pageUrl);

        }

    }


    public DashboardPage loginAsAdmin() {

        login("agent5", "agent5");

        return new DashboardPage(driver);


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




}