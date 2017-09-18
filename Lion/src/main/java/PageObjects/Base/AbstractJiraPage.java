package PageObjects.Base;

import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import core.Tools.Tools;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-06-09.
 * Page nadrzędny dla wszystkich stron dostępnych w systemie po zalogowaniu.
 */
public abstract class AbstractJiraPage extends PageObject {

    @FindBy(xpath = "//*[@id='aui-flag-container']/div/div/a")
    protected WebElement allert;


    //konstruktor
    public AbstractJiraPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnButton(TaskButton button) {
    }

    public TaskPage goToTask(String url) {
        Assert.assertFalse(url.equals(""), "przekazny numer Issue jest pusty");
        if (url.length() < 10) {
            driver.navigate().to(driver.getCurrentUrl() + "/" + url);
        } else {
            driver.navigate().to(url);
        }
        return new TaskPage(driver);
    }

    public String getNewCreatedIssueNumber() {
        Tools.waitForProcesing(1000);
        String allertTxt = allert.getText();
        System.out.println("wypisuje allertTxT :" + allertTxt);
        String newIssue = allertTxt.substring(0, allertTxt.indexOf(" "));
        return newIssue;
    }

    public DashboardPage goToDashboard() {
        return new DashboardPage(driver);
    }
    public QueQuePage goToQueQue() {
        return new QueQuePage(driver);
    }
}
