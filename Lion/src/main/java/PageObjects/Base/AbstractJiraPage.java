package PageObjects.Base;

import PageObjects.ElementsOnPages.Task.TaskButton;
import PageObjects.ElementsOnPages.Task.TaskLink;
import PageObjects.TaskPage.AbstractTaskPage;
import PageObjects.TaskPage.TaskPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Piotr Majewski on 2017-06-09.
 */
public abstract class AbstractJiraPage extends PageObject {

    public AbstractJiraPage(WebDriver driver){
        super(driver);
    }

    public void clickOnButton(TaskButton button) {
    }

    public AbstractJiraPage clickInLink(TaskLink link){
        return (AbstractJiraPage) new Object();
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
