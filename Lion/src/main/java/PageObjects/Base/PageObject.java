package PageObjects.Base;

import PageObjects.TaskPage.NegotiationTaskPage;
import PageObjects.TaskPage.TaskPage;
import Tools.EnviromentSettings;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
public class PageObject {
    protected WebDriver driver;
    protected Wait wait;
    protected String baseUrl;

    public PageObject() {
    }

    public PageObject(WebDriver driver) {
        //pobieramy adres testowanej strony
        baseUrl = EnviromentSettings.GetTestEnviroment();
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this
        );
    }

    public void waitForProcessing() {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

    }

    public NegotiationTaskPage goToNegotiationTask(String url) {
        if (url.length() == 8) {
            driver.navigate().to(baseUrl + "/browse/" + url);

        } else {
            driver.navigate().to(url);
        }
        return new NegotiationTaskPage(driver);
    }

    public TaskPage goToTask(String url) {
        if (url.length() == 8) {
            driver.navigate().to(baseUrl + "/browse/" + url);

        } else {
            driver.navigate().to(url);
        }
        return new TaskPage(driver);
    }

    public String getUrl(){return driver.getCurrentUrl();}
}
