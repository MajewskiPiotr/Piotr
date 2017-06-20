package PageObjects.Base;

import core.Tools.Configuration.EnviromentSettings;
import core.ElementsOnPages.Task.TaskButton;
import core.ElementsOnPages.Task.TaskLink;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.Tools.Configuration.TestEnviroments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Piotr Majewski on 2017-05-16.
 * Klasa bazowa wszystkich Page objektow jakie beda tworzone
 */
public class PageObject {
    protected WebDriver driver;
    protected Wait wait;
    protected String baseUrl;

    public PageObject() {
    }

    public PageObject(WebDriver driver) {
        //pobieramy adres testowanej strony
        baseUrl = EnviromentSettings.getTestEnviroment();
        this.driver = driver;
        wait = new WebDriverWait(driver, 60);
        PageFactory.initElements(driver, this);

    }


    public void clickOnButton(TaskButton button) {
    }

    public PageObject clickInLink(TaskLink link) {
        return new PageObject(driver);
    }

    public void uruchomAplikacjeCustomer(String url){
        goToUrl(TestEnviroments.CUSTOMER + url);

    }



    public TaskPage goToTask(String url) {
        if (url.length() == 8) {
            driver.navigate().to(baseUrl + "/browse/" + url);

        } else {
            driver.navigate().to(url);
        }
        return new TaskPage(driver);
    }

    public void goToUrl(String url) {
        System.out.println("go to " + url);
        if (url.length() == 8) {
            driver.navigate().to(baseUrl + "/browse/" + url);

        } else {
            driver.navigate().to(url);
        }

   }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
