package PageObjects.main;

import PageObjects.Base.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public void open() {
        driver.navigate().to(baseUrl + pageUrl);
        //TODO dodać sprawdzenia czy udalo sie odpalic aplikacje
    }


    public DashboardPage logInToJira(String login, String haslo) {
        userName.sendKeys(login);
        password.sendKeys(haslo);
        logIn.click();
        return new DashboardPage(driver);

    }

    public KanbanPage logInToJiraAndGoToKanban(String login, String haslo) {
        userName.sendKeys(login);
        password.sendKeys(haslo);
        logIn.click();
        return new KanbanPage(driver);

    }

}
