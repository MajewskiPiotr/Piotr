package PageObjects.Base;

import core.Tools.Configuration.EnviromentSettings;
import core.ElementsOnPages.Task.TaskButton;
import core.ElementsOnPages.Task.TaskLink;
import PageObjects.TaskPage.TaskPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

    public void waitForProcessing() {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

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
        if (url.length() == 8) {
            driver.navigate().to(baseUrl + "/browse/" + url);

        } else {
            driver.navigate().to(baseUrl + url);
        }
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
