package PageObjects.Base;

import core.ElementsOnPages.Task.TaskButton;
import core.ElementsOnPages.Task.TaskLink;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

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



    public TaskPage goToTask(String url) {
        if(url.length()<10) {
            driver.navigate().to(driver.getCurrentUrl() + "/" + url);
        }
        else {
            driver.navigate().to(url);
        }
        return new TaskPage(driver);
    }



    public String getUrl() {
        return driver.getCurrentUrl();
    }

    protected void wypiszListe(List<WebElement> elements) {
        for (WebElement w : elements) {
            System.out.println(w.getText());
        }

    }

}
