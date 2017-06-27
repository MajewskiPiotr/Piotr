package PageObjects.ServiceDesk.MainPage;

import PageObjects.Base.AbstractJiraPage;
import PageObjects.ServiceDesk.TaskPage.EditIssuePage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import core.Tools.FindInTaskList;
import core.Tools.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-16.
 * KLasa reprezntuje Dashboard w aplikacji JIRA
 * widok pojawia sie po zalogowaniu sie do app.
 */
public class QueQuePage extends AbstractJiraPage {

    @FindBy(xpath = "//*[@id='pinnednav-opts-sd-queues-nav']//*[text()='Helpdesk Dispatcher']")
    private WebElement helpDesk_DISPATCHER;
    @FindBy(xpath = "//*[@id='pinnednav-opts-sd-queues-nav']//*[@title[contains(.,'TeamA')]]")
    private WebElement helpDesk_TEAM_A;
    @FindBy(xpath = "//*[@id='pinnednav-opts-sd-queues-nav']//*[@title[contains(.,'TeamB')]]")
    private WebElement helpDesk_TEAM_B;
    @FindBy(xpath = "//*[@id='pinnednav-opts-sd-queues-nav']//*[@title[contains(.,'TeamC')]]")
    private WebElement helpDesk_TEAM_C;
    @FindBy(xpath = "//*[@id='issuetable']/tbody/*")
    private List<WebElement> taskList;

    @FindBy(xpath = "//*[@id='create_link']")
    protected WebElement createButton;

    //Napis SystemDashboard
    @FindBy(className = "aui-page-header-main")
    private WebElement systemDashboard;
    @FindBy(xpath = "//*[@class='sd-agent-header']")
    private WebElement header;

    public QueQuePage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl + "/projects/DLSD/queues/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='pinnednav-opts-sd-queues-nav']//*[text()='Waiting for customer']")));
    }

    public TaskPage goToFirstTaskOnList() {
        String currentUrl = getUrl();
        driver.navigate().to(currentUrl + "/" + FindInTaskList.getTaskLink(getTaskList().get(0)));
        return new TaskPage(driver);
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {
            case HELPDESK_DISPATCHER: {
                helpDesk_DISPATCHER.click();
                //wait.until(ExpectedConditions.textToBePresentInElement(header,"Helpdesk Dispatcher"));
                break;
            }
            case HELPDESK_TEAM_A: {
                helpDesk_TEAM_A.click();
                // wait.until(ExpectedConditions.textToBePresentInElement(header,"↳ Helpdesk TeamA"));

                break;
            }
            case HELPDESK_TEAM_B: {
                helpDesk_TEAM_B.click();
                // wait.until(ExpectedConditions.textToBePresentInElement(header,"↳ Helpdesk TeamB"));

                break;
            }
            case HELPDESK_TEAM_C: {
                helpDesk_TEAM_C.click();
                //  wait.until(ExpectedConditions.textToBePresentInElement(header,"↳ Helpdesk TeamB"));

                break;
            }
        }
        Tools.waitForProcesing(2000);

    }


    private List<WebElement> getTaskList() {
        return taskList;
    }

    public EditIssuePage createIssue() {
        createButton.click();
        return new EditIssuePage(driver);
    }
}