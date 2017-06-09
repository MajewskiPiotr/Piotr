package PageObjects.MainPage;

import PageObjects.Base.PageObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-05-15.
 * KLasa reprezentuje strone logowania do aplikacji Jira
 */
public class LoginPage extends PageObject {

    private String pageUrl = "\\login.jsp";

    @FindBy(name = "os_username")
    private WebElement userName;
    @FindBy(id = "login-form-password")
    private WebElement password;
    @FindBy(id = "login-form-submit")
    private WebElement logIn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private void open() {
        try {
            driver.navigate().to(baseUrl + pageUrl);
        } catch (TimeoutException ex) {
            Assert.fail("Nie udało otworzyć sie aplikacji pod adresem " + baseUrl + pageUrl);
        }
    }

    private void login(String login, String haslo) {
        open();
        try {
            userName.sendKeys(login);
            password.sendKeys(haslo);
            logIn.click();
        } catch (NoSuchElementException exception) {
            Assert.fail("Nie udało sie uruchomić JIRA");
        }
    }

    public DashboardPage logInToJira(String login, String haslo) {
        login(login, haslo);
        return new DashboardPage(driver);

    }

    public DashboardPage loginAsAdmin() {
        login("piotr.majewski", "piotr.majewski");
        return new DashboardPage(driver);

    }

    public CurrentSearchPage logInToJiraAndGoToSearch(String login, String haslo) {
        login(login, haslo);
        return new CurrentSearchPage(driver);
    }

    public KanbanPage logInToJiraAndGoToKanban(String login, String haslo) {
        login(login, haslo);
        return new KanbanPage(driver);
    }

}
