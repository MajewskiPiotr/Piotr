package PageObjects.Base;

import core.ElementsOnPages.Task.TaskButton;
import core.Tools.Configuration.EnviromentSettings;
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

    public void goToUrl(String url) {
        if (url.contains("evercode")) {
            driver.navigate().to(url);

        } else if (url.contains("project")) {
            driver.navigate().to(baseUrl + url);

        } else if (url.contains("customer")) {
            driver.navigate().to(baseUrl + url);

        } else {
            driver.navigate().to(baseUrl + "/browse/" + url);

        }

    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
